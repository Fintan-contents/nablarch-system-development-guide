#!/usr/bin/env python3
"""Excel(.xlsx) のテストデータと、変換後の YAML を全件比較し、サンプルを表示する（Task #3 用）。

用途:
    nablarch-testing-converter で変換した YAML が元の Excel と一致するかを、
    全データ行・全列の比較と、各ブロックのサンプリング表示（最低3件。3件未満は全件）で確認する。

対象ブロック（proman-batch の ExportProjectsInPeriodActionRequestTest.xlsx に含まれるもの）:
    LIST_MAP=<id>            ↔ list_maps
    SETUP_TABLE[[g]]=<table> ↔ setup_tables（group_id 付き）
    EXPECTED_VARIABLE[[g]]=<path> ↔ expected_files（type: variable）
    これ以外のブロックを Excel で検出した場合や、YAML に未対応のトップレベルキーがある場合は
    「未対応」として報告する（終了コード 1）。

比較は2段階で行う:
    RAW: Excel セルの生値と YAML 値の完全一致
    NTF: Excel 値に NTF が Excel 読み込み時に行う解釈を適用した値と YAML 値の一致
         - データ値: NullInterpreter（"null" → null）、QuotationTrimmer（半角 " または全角 ” で囲まれた外側1層を除去）
         - テーブル名・カラム名: 大文字化（TableData と同じ）
         - LIST_MAP: 先頭セルが "//" で始まる行はコメント行として除外、空セルは空文字
         - ディレクティブ値: DataFile#setDirective と同じく trim()。
           可変長ファイルは NTF が既定で file-type=Variable / field-separator="," を設定するため、
           Excel に記載がなければこの既定値を補って比較する（defaultDirectives 未設定が前提）
    RAW 不一致かつ NTF 一致のものは「表記差（意味は同一）」として件数を報告する。

実装メモ:
    - xlsx は標準ライブラリ（zipfile + xml）で読む。sharedStrings のふりがな（rPh）は値に含めない。
    - YAML は PyYAML で読むが、YAML 1.1 の真偽値解釈（`no:` キーが False になる等）を無効化する。
      nablarch-testing-yaml は YAML 1.2（snakeyaml-engine）で読むため。

使い方:
    python3 -I compare_xlsx_yaml.py <Excelファイル> <YAML出力ディレクトリ>
終了コード: 0=NTF 解釈で全件一致 / 1=差異・未対応あり
"""
import re
import sys
import zipfile
import xml.etree.ElementTree as ET
from pathlib import Path

import yaml

NS = {"m": "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
      "r": "http://schemas.openxmlformats.org/officeDocument/2006/relationships"}
M = "{%s}" % NS["m"]
REL_NS = "{http://schemas.openxmlformats.org/package/2006/relationships}"
BLOCK_RE = re.compile(r"^([A-Z_]+)(?:\[(.+?)\])?\s*=\s*(.+)$")
SUPPORTED = {"LIST_MAP", "SETUP_TABLE", "EXPECTED_VARIABLE"}
KNOWN_BLOCK_PREFIX = ("LIST_MAP", "SETUP_", "EXPECTED_", "RESPONSE_", "MESSAGE")
YAML_KEYS = {"list_maps", "setup_tables", "expected_files"}


# ---------------------------------------------------------------- Excel
def col_index(ref):
    n = 0
    for ch in re.match(r"[A-Z]+", ref).group(0):
        n = n * 26 + (ord(ch) - 64)
    return n - 1


def text_of(elem):
    """<si>/<is> のテキスト。ふりがな（rPh）は除外する。"""
    parts = []
    t = elem.find("m:t", NS)
    if t is not None:
        parts.append(t.text or "")
    for r in elem.findall("m:r", NS):
        rt = r.find("m:t", NS)
        if rt is not None:
            parts.append(rt.text or "")
    return "".join(parts)


def decode_ooxml_escape(s):
    return re.sub(r"_x([0-9A-Fa-f]{4})_", lambda m: chr(int(m.group(1), 16)), s)


def read_xlsx(path):
    """{シート名: [[セル値(str|None), ...], ...]} を返す。"""
    with zipfile.ZipFile(path) as z:
        shared = []
        if "xl/sharedStrings.xml" in z.namelist():
            shared = [text_of(si) for si in ET.fromstring(z.read("xl/sharedStrings.xml")).findall("m:si", NS)]
        rels = {r.get("Id"): r.get("Target")
                for r in ET.fromstring(z.read("xl/_rels/workbook.xml.rels")).iter(REL_NS + "Relationship")}
        sheets = {}
        for s in ET.fromstring(z.read("xl/workbook.xml")).find("m:sheets", NS):
            target = rels[s.get("{%s}id" % NS["r"])].lstrip("/")
            target = target if target.startswith("xl/") else "xl/" + target
            rows = []
            for row in ET.fromstring(z.read(target)).iter(M + "row"):
                r = int(row.get("r")) - 1
                while len(rows) <= r:
                    rows.append([])
                cells = rows[r]
                for c in row.findall("m:c", NS):
                    i = col_index(c.get("r"))
                    v, t = c.find("m:v", NS), c.get("t")
                    if t == "s" and v is not None:
                        val = shared[int(v.text)]
                    elif t == "inlineStr":
                        is_ = c.find("m:is", NS)
                        val = text_of(is_) if is_ is not None else ""
                    else:
                        val = v.text if v is not None else None
                    while len(cells) <= i:
                        cells.append(None)
                    cells[i] = decode_ooxml_escape(val) if isinstance(val, str) else val
            sheets[s.get("name")] = rows
    return sheets


def cell(row, i):
    return row[i] if i < len(row) else None


def is_blank(row):
    return not row or all(v is None or str(v).strip() == "" for v in row)


def first_cell(row):
    return cell(row, 0)


def trim_tail(values):
    values = list(values)
    while values and (values[-1] is None or values[-1] == ""):
        values.pop()
    return values


def parse_excel_blocks(rows):
    """シートからブロックを取り出す。(blocks, unsupported) を返す。"""
    blocks, unsupported = [], []
    i = 0
    while i < len(rows):
        head = first_cell(rows[i]) if rows[i] else None
        m = BLOCK_RE.match(str(head).strip()) if isinstance(head, str) else None
        if not m or not m.group(1).startswith(KNOWN_BLOCK_PREFIX):
            i += 1
            continue
        kind, group, ident = m.group(1), m.group(2), m.group(3).strip()
        j = i + 1
        body = []
        while j < len(rows) and not is_blank(rows[j]):
            body.append(rows[j])
            j += 1
        if kind not in SUPPORTED:
            unsupported.append(f"{kind}{'[' + group + ']' if group else ''}={ident}")
        else:
            blocks.append({"kind": kind, "group": group, "id": ident, "body": body})
        i = j
    return blocks, unsupported


# ---------------------------------------------------------------- YAML
class Yaml12Loader(yaml.SafeLoader):
    """YAML 1.1 の真偽値（yes/no/on/off）解釈を無効化した SafeLoader。"""


Yaml12Loader.yaml_implicit_resolvers = {
    k: [(tag, rx) for tag, rx in v if tag != "tag:yaml.org,2002:bool"]
    for k, v in yaml.SafeLoader.yaml_implicit_resolvers.items()
}


def load_yaml(path):
    with open(path, encoding="utf-8") as f:
        return yaml.load(f, Loader=Yaml12Loader) or {}


# ---------------------------------------------------------------- 比較
def ntf_value(v):
    """NullInterpreter + QuotationTrimmer 相当。"""
    if v is None:
        return None
    if v == "null":
        return None
    # NTF の QuotationTrimmer と同じく、半角 " または全角 ” で囲まれていれば外側1層を外す
    if len(v) >= 2 and ((v[0] == '"' and v[-1] == '"') or (v[0] == "”" and v[-1] == "”")):
        return v[1:-1]
    return v


class Result:
    def __init__(self):
        self.ng, self.raw_diff, self.cells = [], 0, 0

    def check(self, where, raw, ntf, actual):
        self.cells += 1
        if ntf != actual:
            self.ng.append(f"{where}: excel={raw!r} (NTF解釈={ntf!r}) yaml={actual!r}")
            return "NG"
        if raw != actual:
            self.raw_diff += 1
            return "OK(表記差)"
        return "OK"


def sample_indexes(n):
    return list(range(n)) if n < 3 else sorted({0, n // 2, n - 1})


def compare_list_map(block, ylist, res, out):
    header = trim_tail(block["body"][0])
    rows = [r for r in block["body"][1:] if not (isinstance(first_cell(r), str) and first_cell(r).startswith("//"))]
    skipped = len(block["body"]) - 1 - len(rows)
    y = next((x for x in ylist if x.get("id") == block["id"]), None)
    label = f"LIST_MAP={block['id']}"
    if y is None:
        res.ng.append(f"{label}: YAML に無い")
        return
    yrows = y.get("rows") or []
    out.append(f"  {label}: excel={len(rows)}行（コメント行 {skipped} 行を除外） yaml={len(yrows)}行")
    if len(rows) != len(yrows):
        res.ng.append(f"{label}: 行数 excel={len(rows)} yaml={len(yrows)}")
    for k, (r, yr) in enumerate(zip(rows, yrows)):
        if set(yr) != set(header):
            res.ng.append(f"{label} 行{k + 1}: 列 excel={header} yaml={list(yr)}")
        marks = []
        for ci, col in enumerate(header):
            raw = cell(r, ci)
            raw = "" if raw is None else raw
            ntf = ntf_value(raw)
            ntf = "" if ntf is None and raw == "" else ntf
            marks.append((col, raw, yr.get(col), res.check(f"{label} 行{k + 1} {col}", raw, ntf, yr.get(col))))
        if k in sample_indexes(len(rows)):
            out.append(f"    sample 行{k + 1}: " + ", ".join(f"{c}={a!r}/{b!r}" for c, a, b, _ in marks[:4])
                       + f" ... -> {'OK' if all(m[3].startswith('OK') for m in marks) else 'NG'}")


def compare_setup_table(block, ylist, res, out):
    header = [str(h).strip() for h in trim_tail(block["body"][0])]
    rows = block["body"][1:]
    tname = block["id"].upper()
    def matches(x):
        if str(x.get("table")) != tname:
            return False
        return str(x.get("group_id")) == block["group"] if block["group"] else "group_id" not in x
    y = next((x for x in ylist if matches(x)), None)
    label = f"SETUP_TABLE{'[' + block['group'] + ']' if block['group'] else ''}={block['id']}"
    if y is None:
        res.ng.append(f"{label}: YAML に対応ブロック無し")
        return
    yrows = y.get("rows") or []
    out.append(f"  {label}: excel={len(rows)}行 yaml={len(yrows)}行（テーブル名 {block['id']!r}→{tname!r}）")
    if len(rows) != len(yrows):
        res.ng.append(f"{label}: 行数 excel={len(rows)} yaml={len(yrows)}")
    ucols = [h.upper() for h in header]
    for k, (r, yr) in enumerate(zip(rows, yrows)):
        if set(yr) != set(ucols):
            res.ng.append(f"{label} 行{k + 1}: 列 excel={ucols} yaml={list(yr)}")
        marks = []
        for ci, col in enumerate(ucols):
            raw = cell(r, ci)
            marks.append((col, raw, yr.get(col), res.check(f"{label} 行{k + 1} {col}", raw, ntf_value(raw), yr.get(col))))
        if k in sample_indexes(len(rows)):
            out.append(f"    sample 行{k + 1}: " + ", ".join(f"{c}={a!r}/{b!r}" for c, a, b, _ in marks)
                       + f" -> {'OK' if all(m[3].startswith('OK') for m in marks) else 'NG'}")


def compare_expected_variable(block, ylist, res, out):
    body = block["body"]
    label = f"EXPECTED_VARIABLE{'[' + block['group'] + ']' if block['group'] else ''}={block['id']}"
    y = next((x for x in ylist if str(x.get("path")) == block["id"] and str(x.get("group_id")) == str(block["group"])), None)
    if y is None:
        res.ng.append(f"{label}: YAML に対応ブロック無し")
        return
    # ディレクティブ行（2列のキー/値）→ レコード（record_type 行 + 型行 + データ行）
    directives, k = {}, 0
    while k < len(body) and len(trim_tail(body[k])) == 2 and first_cell(body[k]) not in (None, ""):
        if k + 1 < len(body) and first_cell(body[k + 1]) in (None, ""):
            break  # 次行が型行ならこの行はレコード定義
        directives[str(first_cell(body[k])).strip()] = cell(body[k], 1)
        k += 1
    rec_head, types, data = body[k], body[k + 1], body[k + 2:]
    ntf_dirs = {key: str(v).strip() for key, v in directives.items()}
    ntf_dirs.setdefault("file-type", "Variable")
    ntf_dirs.setdefault("field-separator", ",")
    ydirs = {key: str(v) for key, v in (y.get("directives") or {}).items()}
    out.append(f"  {label}: directives excel={directives} → NTF解釈={ntf_dirs} / yaml={ydirs}")
    for key in sorted(set(ntf_dirs) | set(ydirs)):
        res.check(f"{label} directive {key}", directives.get(key), ntf_dirs.get(key), ydirs.get(key))
    recs = y.get("records") or []
    if len(recs) != 1:
        res.ng.append(f"{label}: YAML records 数={len(recs)}（想定 1）")
        return
    rec = recs[0]
    res.check(f"{label} record_type", first_cell(rec_head), first_cell(rec_head), rec.get("record_type"))
    names = trim_tail(rec_head[1:])
    tlist = trim_tail(types[1:])
    yfields = rec.get("fields") or []
    if [f.get("name") for f in yfields] != names or [f.get("type") for f in yfields] != tlist:
        res.ng.append(f"{label}: フィールド定義が不一致 excel={list(zip(names, tlist))} yaml={yfields}")
    else:
        res.cells += 2 * len(names)
    yrows = rec.get("rows") or []
    out.append(f"    fields={len(names)}列（名前・型とも一致を確認） rows excel={len(data)} yaml={len(yrows)}")
    if len(data) != len(yrows):
        res.ng.append(f"{label}: 行数 excel={len(data)} yaml={len(yrows)}")
    for r_i, (r, yr) in enumerate(zip(data, yrows)):
        marks = []
        for ci, name in enumerate(names):
            raw = cell(r, ci + 1)
            marks.append((name, raw, yr[ci] if ci < len(yr) else "<欠落>",
                          res.check(f"{label} 行{r_i + 1} {name}", raw, ntf_value(raw), yr[ci] if ci < len(yr) else "<欠落>")))
        if len(yr) != len(names):
            res.ng.append(f"{label} 行{r_i + 1}: 列数 excel={len(names)} yaml={len(yr)}")
        if r_i in sample_indexes(len(data)):
            out.append(f"    sample 行{r_i + 1}: " + ", ".join(f"{c}={a!r}/{b!r}" for c, a, b, _ in marks[:3])
                       + f" ... -> {'OK' if all(m[3].startswith('OK') for m in marks) else 'NG'}")
    if not data:
        out.append("    sample: 元データ0件（excel・yaml とも rows 空）")


def main(xlsx, yaml_dir):
    sheets = read_xlsx(xlsx)
    res, unsupported = Result(), []
    ydir = Path(yaml_dir)
    yaml_files = {p.stem for p in ydir.glob("*.yaml")}
    if yaml_files != set(sheets):
        res.ng.append(f"シートと YAML ファイルの過不足: sheets={sorted(sheets)} yaml={sorted(yaml_files)}")
    for sheet, rows in sheets.items():
        out = [f"[{sheet}.yaml]"]
        blocks, unsup = parse_excel_blocks(rows)
        unsupported += [f"{sheet}:{u}" for u in unsup]
        ydoc = load_yaml(ydir / f"{sheet}.yaml") if (ydir / f"{sheet}.yaml").exists() else {}
        for key in set(ydoc) - YAML_KEYS:
            unsupported.append(f"{sheet}.yaml: 未対応キー {key}")
        n_excel = {"LIST_MAP": 0, "SETUP_TABLE": 0, "EXPECTED_VARIABLE": 0}
        for b in blocks:
            n_excel[b["kind"]] += 1
            if b["kind"] == "LIST_MAP":
                compare_list_map(b, ydoc.get("list_maps") or [], res, out)
            elif b["kind"] == "SETUP_TABLE":
                compare_setup_table(b, ydoc.get("setup_tables") or [], res, out)
            else:
                compare_expected_variable(b, [x for x in (ydoc.get("expected_files") or []) if x.get("type") == "variable"], res, out)
        n_yaml = {"LIST_MAP": len(ydoc.get("list_maps") or []), "SETUP_TABLE": len(ydoc.get("setup_tables") or []),
                  "EXPECTED_VARIABLE": len(ydoc.get("expected_files") or [])}
        if n_excel != n_yaml:
            res.ng.append(f"{sheet}: ブロック数 excel={n_excel} yaml={n_yaml}")
        out.insert(1, f"  ブロック数 excel={n_excel} yaml={n_yaml}")
        print("\n".join(out))
    if unsupported:
        res.ng.append("未対応: " + ", ".join(unsupported))
    print(f"\n比較セル数: {res.cells} / 表記差（RAW 不一致・NTF 解釈で一致）: {res.raw_diff} / 不一致: {len(res.ng)}")
    for n in res.ng[:50]:
        print("  [NG] " + n)
    return 1 if res.ng else 0


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print(__doc__)
        sys.exit(64)
    sys.exit(main(sys.argv[1], sys.argv[2]))
