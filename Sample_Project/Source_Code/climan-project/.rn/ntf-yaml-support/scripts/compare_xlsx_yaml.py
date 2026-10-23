#!/usr/bin/env python3
"""Excel(.xlsx) のテストデータと、変換後の YAML の SETUP_TABLE 内容を比較する（Task #3 用）。

用途:
    nablarch-testing-converter で変換した YAML が元の Excel と一致するかを、
    サンプリング比較（各ファイル最低3件）と全件比較の両方で確認する。
    標準ライブラリのみで動作する（openpyxl / PyYAML 不要）。

対象範囲:
    climan-project の ClientActionTest.xlsx が持つブロックは SETUP_TABLE のみのため、
    SETUP_TABLE（Excel）と setup_tables（YAML）を比較する。他のブロック種別を検出した場合は
    「未対応ブロック」として報告し、終了コード 2 で終わる（その場合は手作業で比較すること）。

使い方:
    python3 -I compare_xlsx_yaml.py <Excelファイル> <YAML出力ディレクトリ>
    例: python3 -I compare_xlsx_yaml.py \
          src/test/java/.../ClientActionTest.xlsx src/test/java/.../ClientActionTest

終了コード: 0=全件一致 / 1=差異あり / 2=未対応ブロックあり
"""
import re
import sys
import zipfile
import xml.etree.ElementTree as ET
from pathlib import Path

NS = {"m": "http://schemas.openxmlformats.org/spreadsheetml/2006/main",
      "r": "http://schemas.openxmlformats.org/officeDocument/2006/relationships"}
REL_NS = "{http://schemas.openxmlformats.org/package/2006/relationships}"
BLOCK_RE = re.compile(r"^(SETUP_TABLE|SETUP_FIXED|SETUP_VARIABLE|EXPECTED_\w+|RESPONSE_\w+|MESSAGE|LIST_MAP|DEFAULT)\s*=\s*(.+)$")


def col_index(ref):
    letters = re.match(r"[A-Z]+", ref).group(0)
    n = 0
    for ch in letters:
        n = n * 26 + (ord(ch) - 64)
    return n - 1


def read_xlsx(path):
    """{シート名: [[セル値(str|None), ...], ...]} を返す。"""
    z = zipfile.ZipFile(path)
    shared = []
    if "xl/sharedStrings.xml" in z.namelist():
        for si in ET.fromstring(z.read("xl/sharedStrings.xml")).findall("m:si", NS):
            shared.append("".join(t.text or "" for t in si.iter("{%s}t" % NS["m"])))
    rels = {r.get("Id"): r.get("Target") for r in ET.fromstring(z.read("xl/_rels/workbook.xml.rels")).iter(REL_NS + "Relationship")}
    sheets = {}
    for s in ET.fromstring(z.read("xl/workbook.xml")).find("m:sheets", NS):
        target = rels[s.get("{%s}id" % NS["r"])].lstrip("/")
        target = target if target.startswith("xl/") else "xl/" + target
        rows = []
        for row in ET.fromstring(z.read(target)).iter("{%s}row" % NS["m"]):
            r = int(row.get("r")) - 1
            while len(rows) <= r:
                rows.append([])
            cells = rows[r]
            for c in row.findall("m:c", NS):
                i = col_index(c.get("r"))
                v = c.find("m:v", NS)
                t = c.get("t")
                if t == "s" and v is not None:
                    val = shared[int(v.text)]
                elif t == "inlineStr":
                    val = "".join(x.text or "" for x in c.iter("{%s}t" % NS["m"]))
                else:
                    val = v.text if v is not None else None
                while len(cells) <= i:
                    cells.append(None)
                cells[i] = val
        sheets[s.get("name")] = rows
    return sheets


def is_blank(row):
    return not row or all(v is None or str(v).strip() == "" for v in row)


def excel_setup_tables(rows):
    """SETUP_TABLE を {テーブル名: [ {列: 値}, ... ]} で返す。未対応ブロック名のリストも返す。"""
    tables, unsupported = {}, []
    i = 0
    while i < len(rows):
        first = next((v for v in rows[i] if v not in (None, "")), None) if rows[i] else None
        m = BLOCK_RE.match(str(first).strip()) if first is not None else None
        if not m:
            i += 1
            continue
        kind, name = m.group(1), m.group(2).strip()
        if kind != "SETUP_TABLE":
            unsupported.append(f"{kind}={name}")
            i += 1
            continue
        header = rows[i + 1] if i + 1 < len(rows) else []
        cols = [(k, str(h).strip()) for k, h in enumerate(header) if h not in (None, "")]
        data = []
        j = i + 2
        while j < len(rows) and not is_blank(rows[j]):
            data.append({c: (rows[j][k] if k < len(rows[j]) else None) for k, c in cols})
            j += 1
        tables[name] = data
        i = j
    return tables, unsupported


def yaml_setup_tables(path):
    """converter が出力する setup_tables 形式（table / rows / キー: "値"）を読み取る。"""
    tables, cur, row = {}, None, None
    for line in Path(path).read_text(encoding="utf-8").splitlines():
        if not line.strip():
            continue
        m = re.match(r'^\s*- table:\s*"?(.*?)"?\s*$', line)
        if m:
            cur = tables.setdefault(m.group(1), [])
            row = None
            continue
        if re.match(r"^\s*rows:\s*\[\]\s*$", line) or re.match(r"^\s*rows:\s*$", line) or line.startswith("setup_tables:"):
            continue
        m = re.match(r'^(\s*)(- )?([A-Za-z0-9_]+):\s*(.*)$', line)
        if not m or cur is None:
            raise ValueError(f"{path}: 解析できない行: {line}")
        if m.group(2):
            row = {}
            cur.append(row)
        val = m.group(4).strip()
        if val in ("null", "~", ""):
            val = None
        elif val.startswith('"') and val.endswith('"'):
            val = bytes(val[1:-1], "utf-8").decode("unicode_escape").encode("latin-1").decode("utf-8")
        row[m.group(3)] = val
    return tables


def norm(v):
    return None if v is None or v == "" else str(v)


def main(xlsx, yaml_dir):
    sheets = read_xlsx(xlsx)
    total_diff, unsupported_all = 0, []
    for sheet, rows in sheets.items():
        ytables_path = Path(yaml_dir) / f"{sheet}.yaml"
        xt, unsupported = excel_setup_tables(rows)
        unsupported_all += [f"{sheet}:{u}" for u in unsupported]
        yt = yaml_setup_tables(ytables_path) if ytables_path.exists() else None
        if yt is None:
            print(f"[NG] {sheet}: YAML ファイルがありません ({ytables_path})")
            total_diff += 1
            continue
        for name in sorted(set(xt) | set(yt)):
            xr, yr = xt.get(name), yt.get(name)
            if xr is None or yr is None:
                print(f"[NG] {sheet}/{name}: テーブルが片方にしかありません (excel={xr is not None}, yaml={yr is not None})")
                total_diff += 1
                continue
            diffs = []
            if len(xr) != len(yr):
                diffs.append(f"行数 excel={len(xr)} yaml={len(yr)}")
            for k, (a, b) in enumerate(zip(xr, yr)):
                if set(a) != set(b):
                    diffs.append(f"行{k + 1} 列 excel={sorted(a)} yaml={sorted(b)}")
                for c in a:
                    if norm(a.get(c)) != norm(b.get(c)):
                        diffs.append(f"行{k + 1} {c}: excel={a.get(c)!r} yaml={b.get(c)!r}")
            status = "OK" if not diffs else "NG"
            total_diff += len(diffs)
            print(f"[{status}] {sheet}.yaml / {name}: 行数 excel={len(xr)} yaml={len(yr)}")
            for d in diffs[:20]:
                print("    " + d)
            # サンプリング（最低3件。元データが3件未満なら全件）: 先頭・中央・末尾
            if xr:
                idx = sorted({0, len(xr) // 2, len(xr) - 1}) if len(xr) >= 3 else list(range(len(xr)))
                for k in idx:
                    a, b = xr[k], yr[k] if k < len(yr) else {}
                    pairs = ", ".join(f"{c}={a.get(c)!r}/{b.get(c)!r}" for c in a)
                    ok = all(norm(a.get(c)) == norm(b.get(c)) for c in a) and set(a) == set(b)
                    print(f"    sample 行{k + 1}: {pairs} -> {'OK' if ok else 'NG'}")
            else:
                print("    sample: 元データ0件（excel・yaml とも rows 空であることを確認）")
    if unsupported_all:
        print("[未対応ブロック] " + ", ".join(unsupported_all))
    print(f"差異件数: {total_diff}")
    if total_diff:
        return 1
    return 2 if unsupported_all else 0


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print(__doc__)
        sys.exit(64)
    sys.exit(main(sys.argv[1], sys.argv[2]))
