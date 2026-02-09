#!/usr/bin/env python3
"""
JSON知識ファイル → 人向けMarkdown変換スクリプト（完全汎用版）

すべてのフィールドを再帰的に処理し、漏れなく変換する。
"""

import json
import sys
import os


# =============================================================================
# 特殊構造専用の変換関数
# =============================================================================

def render_methods(methods) -> list:
    """メソッド一覧をテーブルに変換"""
    lines = []

    if isinstance(methods, dict):
        lines.append("| メソッド | 説明 |")
        lines.append("|----------|------|")
        for name, desc in methods.items():
            lines.append(f"| `{name}` | {desc} |")
        lines.append("")
        return lines

    if not isinstance(methods, list):
        return lines

    has_signature = any(m.get("signature") for m in methods if isinstance(m, dict))

    if has_signature:
        lines.append("| メソッド | シグネチャ | 説明 |")
        lines.append("|----------|-----------|------|")
        for m in methods:
            if isinstance(m, dict):
                name = m.get("name", "")
                sig = m.get("signature", "")
                desc = m.get("description", "")
                important = m.get("important", "")
                if important:
                    desc = f"{desc} ⚠️ {important}"
                lines.append(f"| `{name}` | `{sig}` | {desc} |")
    else:
        lines.append("| メソッド | 説明 |")
        lines.append("|----------|------|")
        for m in methods:
            if isinstance(m, dict):
                name = m.get("name", "")
                desc = m.get("description", "")
                lines.append(f"| `{name}` | {desc} |")

    lines.append("")

    # 各メソッドの詳細
    for m in methods:
        if not isinstance(m, dict):
            continue

        # 詳細情報を持つフィールド
        detail_keys = {"parameters", "returns", "throws", "usage", "notes", "example", "comparison_rules", "behavior"}
        has_details = any(m.get(k) for k in detail_keys)

        if not has_details:
            continue

        lines.append(f"**{m.get('name', '')}**:")
        lines.append("")

        if m.get("parameters"):
            lines.append("パラメータ:")
            for param in m["parameters"]:
                if isinstance(param, dict):
                    pname = param.get("name", "")
                    ptype = param.get("type", "")
                    pdesc = param.get("description", "")
                    lines.append(f"- `{pname}` ({ptype}): {pdesc}")
            lines.append("")

        if m.get("returns"):
            lines.append(f"戻り値: {m['returns']}")
            lines.append("")

        if m.get("throws"):
            lines.append("例外:")
            for throws in m["throws"]:
                lines.append(f"- {throws}")
            lines.append("")

        if m.get("usage"):
            lines.append(f"使い方: {m['usage']}")
            lines.append("")

        if m.get("notes"):
            notes = m["notes"]
            if isinstance(notes, list):
                lines.append("注意事項:")
                for note in notes:
                    lines.append(f"- {note}")
            else:
                lines.append(f"注意事項: {notes}")
            lines.append("")

        if m.get("comparison_rules"):
            lines.append("比較ルール:")
            for rule in m["comparison_rules"]:
                lines.append(f"- {rule}")
            lines.append("")

        if m.get("behavior"):
            behavior = m["behavior"]
            if isinstance(behavior, list):
                lines.append("動作:")
                for b in behavior:
                    lines.append(f"- {b}")
            else:
                lines.append(f"動作: {behavior}")
            lines.append("")

        if m.get("example"):
            lines.append("```java")
            lines.append(m["example"])
            lines.append("```")
            lines.append("")

    return lines


def render_properties(properties: list) -> list:
    """プロパティ一覧をテーブルに変換"""
    lines = []
    if not properties:
        return lines

    lines.append("| プロパティ | 型 | 必須 | 説明 |")
    lines.append("|-----------|-----|:----:|------|")
    for p in properties:
        if isinstance(p, dict):
            name = p.get("name", "")
            ptype = p.get("type", "")
            required = "✓" if p.get("required") else ""
            desc = p.get("description", "")

            if p.get("default"):
                desc += f" (デフォルト: `{p['default']}`)"

            lines.append(f"| `{name}` | `{ptype}` | {required} | {desc} |")
    lines.append("")

    # プロパティの詳細
    for p in properties:
        if not isinstance(p, dict):
            continue

        if p.get("example"):
            lines.append(f"**{p.get('name', '')}の例**: `{p['example']}`")
            lines.append("")

        if p.get("notes"):
            lines.append(f"**{p.get('name', '')}の注記**:")
            for note in p["notes"]:
                lines.append(f"- {note}")
            lines.append("")

    return lines


def render_dependencies(dependencies) -> list:
    """依存関係リストを変換"""
    lines = []
    if not dependencies:
        return lines

    lines.append("**依存関係**:")
    lines.append("")
    for dep in dependencies:
        if isinstance(dep, dict):
            group_id = dep.get("groupId", "")
            artifact_id = dep.get("artifactId", "")
            version = dep.get("version", "")
            scope = dep.get("scope", "")
            required = dep.get("required")
            description = dep.get("description", "")

            dep_str = f"- `{group_id}:{artifact_id}`"
            if version:
                dep_str += f" {version}"
            if scope:
                dep_str += f" (scope: {scope})"
            if required is not None:
                req_str = "必須" if required else "任意"
                dep_str += f" [{req_str}]"
            if description:
                dep_str += f" - {description}"

            lines.append(dep_str)
    lines.append("")
    return lines


def render_modules(modules) -> list:
    """モジュール情報を変換"""
    lines = []
    if not modules:
        return lines

    lines.append("**モジュール**:")
    if isinstance(modules, list):
        for m in modules:
            if isinstance(m, dict):
                module_str = f"- `{m.get('groupId', '')}:{m.get('artifactId', '')}`"
                if m.get("note"):
                    module_str += f" ({m['note']})"
                lines.append(module_str)
            else:
                lines.append(f"- `{m}`")
    elif isinstance(modules, dict) and modules.get("dependencies"):
        for dep in modules["dependencies"]:
            if isinstance(dep, dict):
                module_str = f"- `{dep.get('groupId', '')}:{dep.get('artifactId', '')}`"
                if dep.get("note"):
                    module_str += f" ({dep['note']})"
                lines.append(module_str)
    lines.append("")
    return lines


def render_anti_patterns(patterns: list) -> list:
    """アンチパターンをテーブルに変換"""
    lines = []
    lines.append("| パターン | 理由 | 正しい方法 |")
    lines.append("|----------|------|------------|")
    for p in patterns:
        if isinstance(p, dict):
            pattern = p.get("pattern", "")
            reason = p.get("reason", "")
            correct = p.get("correct", "")
            lines.append(f"| {pattern} | {reason} | {correct} |")
    lines.append("")

    # 各パターンのコード例
    for p in patterns:
        if isinstance(p, dict) and p.get("example_correct"):
            lines.append(f"**{p.get('pattern', '')}の正しい例**:")
            lines.append("")
            lines.append("```java")
            lines.append(p["example_correct"])
            lines.append("```")
            lines.append("")

    return lines


def render_errors(errors: list) -> list:
    """エラー一覧をテーブルに変換"""
    lines = []
    lines.append("| 例外 | 原因 | 対処 |")
    lines.append("|------|------|------|")
    for e in errors:
        if isinstance(e, dict):
            exc = e.get("exception", "")
            cause = e.get("cause", "")
            solution = e.get("solution", "")
            lines.append(f"| `{exc}` | {cause} | {solution} |")
    lines.append("")

    # 各エラーの詳細
    for e in errors:
        if not isinstance(e, dict):
            continue

        has_details = e.get("example") or e.get("use_case") or e.get("behavior")
        if not has_details:
            continue

        lines.append(f"**{e.get('exception', '')}**:")
        lines.append("")

        if e.get("use_case"):
            lines.append(f"使用ケース: {e['use_case']}")
            lines.append("")

        if e.get("behavior"):
            lines.append(f"動作: {e['behavior']}")
            lines.append("")

        if e.get("example"):
            lines.append("```java")
            lines.append(e["example"])
            lines.append("```")
            lines.append("")

    return lines


def render_security_check_items(check_items: list) -> list:
    """セキュリティチェック項目を変換"""
    lines = []

    for item in check_items:
        item_id = item.get("id", "")
        category = item.get("category", "")
        explanation = item.get("explanation", "")
        items = item.get("items", [])

        lines.append(f"## {item_id}. {category}")
        lines.append("")

        if explanation:
            lines.append(explanation)
            lines.append("")

        if items:
            lines.append("| 種別 | 説明 | Nablarch機能 | 対応 | 参照 |")
            lines.append("|------|------|--------------|:----:|------|")
            for it in items:
                itype = it.get("type", "")
                desc = it.get("description", "")
                feature = it.get("nablarch_feature", "")
                support = it.get("nablarch_support", "")
                ref = it.get("reference", "")
                lines.append(f"| {itype} | {desc} | {feature} | {support} | {ref} |")
            lines.append("")

            # 説明がある項目を追記
            for it in items:
                if it.get("explanation"):
                    lines.append(f"**{it.get('description', '')}**:")
                    lines.append("")
                    lines.append(it["explanation"])
                    lines.append("")

        lines.append("---")
        lines.append("")

    return lines


def render_release_changes(changes: list) -> list:
    """リリース変更情報を変換"""
    lines = []

    lines.append("| No | カテゴリ | 種別 | タイトル | 影響 |")
    lines.append("|----|----------|------|----------|------|")
    for change in changes:
        no = change.get("no", "")
        category = change.get("category", "")
        ctype = change.get("type", "")
        title = change.get("title", "")
        impact = change.get("impact", "")
        lines.append(f"| {no} | {category} | {ctype} | {title} | {impact} |")
    lines.append("")

    # 詳細情報
    for change in changes:
        no = change.get("no", "")
        title = change.get("title", "")
        description = change.get("description", "")
        module = change.get("module", "")
        impact_detail = change.get("impact_detail", "")
        jira = change.get("jira", "")
        affected_version = change.get("affected_version", "")
        reference = change.get("reference", "")

        lines.append(f"### {no}. {title}")
        lines.append("")

        if description:
            lines.append(description)
            lines.append("")

        if module:
            lines.append(f"**モジュール**: {module}")
            lines.append("")

        if affected_version:
            lines.append(f"**影響バージョン**: {affected_version}")
            lines.append("")

        if impact_detail:
            lines.append(f"**影響詳細**: {impact_detail}")
            lines.append("")

        if jira:
            lines.append(f"**JIRA**: {jira}")
            lines.append("")

        if reference:
            lines.append(f"**参照**: {reference}")
            lines.append("")

        lines.append("---")
        lines.append("")

    return lines


# =============================================================================
# 完全汎用的な再帰的変換
# =============================================================================

def render_any_value(key: str, value, indent: int = 0) -> list:
    """任意の値を再帰的にMarkdownに変換"""
    lines = []
    prefix = "  " * indent

    if value is None:
        return lines

    # 文字列
    if isinstance(value, str):
        if key:
            lines.append(f"{prefix}**{key}**: {value}")
        else:
            lines.append(f"{prefix}{value}")
        lines.append("")
        return lines

    # リスト
    if isinstance(value, list):
        if key:
            lines.append(f"{prefix}**{key}**:")
            lines.append("")

        # 全て文字列の場合
        if all(isinstance(item, str) for item in value):
            for item in value:
                lines.append(f"{prefix}- {item}")
            lines.append("")
        else:
            # 複雑な構造
            for idx, item in enumerate(value):
                if isinstance(item, dict):
                    # 辞書の場合、キーと値を展開
                    if len(item) <= 3:
                        # 小さい辞書は1行で
                        for k, v in item.items():
                            if isinstance(v, (str, int, bool)):
                                lines.append(f"{prefix}- **{k}**: {v}")
                            elif isinstance(v, list) and all(isinstance(x, str) for x in v):
                                lines.append(f"{prefix}- **{k}**: {', '.join(v)}")
                            else:
                                lines.append(f"{prefix}- **{k}**:")
                                lines.extend(render_any_value("", v, indent + 1))
                    else:
                        # 大きい辞書は展開
                        lines.append(f"{prefix}- 項目 {idx + 1}:")
                        for k, v in item.items():
                            lines.extend(render_any_value(k, v, indent + 1))
                elif isinstance(item, str):
                    lines.append(f"{prefix}- {item}")
                else:
                    lines.append(f"{prefix}- {item}")
            lines.append("")
        return lines

    # 辞書
    if isinstance(value, dict):
        if key:
            lines.append(f"{prefix}**{key}**:")
            lines.append("")

        for k, v in value.items():
            lines.extend(render_any_value(k, v, indent))

        return lines

    # その他（数値、ブール値など）
    if key:
        lines.append(f"{prefix}**{key}**: {value}")
    else:
        lines.append(f"{prefix}{value}")
    lines.append("")
    return lines


def render_section_content(section_id: str, section) -> list:
    """セクション内容を完全汎用的に変換"""
    lines = []

    if not isinstance(section, dict):
        if isinstance(section, list):
            for item in section:
                if isinstance(item, str):
                    lines.append(f"- {item}")
                else:
                    lines.extend(render_any_value("", item))
            lines.append("")
        elif isinstance(section, str):
            lines.append(section)
            lines.append("")
        return lines

    # 特殊処理が必要なフィールドを最初に処理
    processed_keys = set()

    # description（常に最初）
    if section.get("description"):
        lines.append(section["description"])
        lines.append("")
        processed_keys.add("description")

    # 主要なフィールドを優先順位順に処理
    priority_fields = [
        ("purpose", lambda v: f"**目的**: {v}\n\n"),
        ("positioning", lambda v: f"**位置づけ**: {v}\n\n"),
    ]

    for field, formatter in priority_fields:
        if section.get(field):
            lines.append(formatter(section[field]))
            processed_keys.add(field)

    # 特殊構造のフィールド
    special_handlers = {
        "responsibilities": lambda v: ["**責務**:\n"] + [f"- {item}\n" for item in v] + ["\n"] if isinstance(v, list) else render_any_value("responsibilities", v),
        "external_library": lambda v: render_external_library(v),
        "modules": lambda v: render_modules(v),
        "dependencies": lambda v: render_dependencies(v),
        "properties": lambda v: render_properties(v),
        "methods": lambda v: render_methods(v),
        "features": lambda v: ["**機能**:\n"] + [f"- {item}\n" for item in v] + ["\n"] if isinstance(v, list) else render_any_value("features", v),
        "classes": lambda v: ["**classes**:\n"] + [f"- {cls}\n" for cls in v] + ["\n"],
    }

    for key, handler in special_handlers.items():
        if section.get(key):
            result = handler(section[key])
            if isinstance(result, list):
                lines.extend(result)
            else:
                lines.append(result)
            processed_keys.add(key)

    # コード例系フィールド
    code_fields = ["example", "examples", "maven_example", "gradle_example", "xml_example"]
    for field in code_fields:
        if section.get(field):
            lines.extend(render_code_field(field, section[field]))
            processed_keys.add(field)

    # 処理フロー系
    if section.get("flow"):
        lines.append("**処理フロー**:")
        lines.append("")
        for flow in section["flow"]:
            if isinstance(flow, dict):
                step = flow.get("step", "")
                desc = flow.get("description", "")
                lines.append(f"**{step}**: {desc}")
                lines.append("")
            elif isinstance(flow, str):
                lines.append(f"- {flow}")
        lines.append("")
        processed_keys.add("flow")

    # コンポーネント
    if section.get("components"):
        lines.append("**コンポーネント**:")
        lines.append("")
        for comp in section["components"]:
            if isinstance(comp, dict):
                name = comp.get("name", "")
                desc = comp.get("description", "")
                lines.append(f"- **{name}**: {desc}")

                for key, value in comp.items():
                    if key in ("name", "description"):
                        continue
                    if isinstance(value, list):
                        lines.append(f"  - {key}: {', '.join(value)}")
                    else:
                        lines.append(f"  - {key}: {value}")
        lines.append("")
        processed_keys.add("components")

    # その他全てのフィールドを汎用処理
    for key, value in section.items():
        if key in processed_keys:
            continue

        lines.extend(render_any_value(key, value))

    return lines


def render_external_library(ext_lib: dict) -> list:
    """外部ライブラリ情報を変換"""
    lines = []
    lines.append("**外部ライブラリ**:")
    lib_name = ext_lib.get("name", "")
    if ext_lib.get("version"):
        lib_name += f" {ext_lib['version']}"
    if ext_lib.get("url"):
        lines.append(f"- [{lib_name}]({ext_lib['url']})")
    else:
        lines.append(f"- {lib_name}")
    lines.append("")
    return lines


def render_code_field(field_name: str, value) -> list:
    """コード例フィールドを変換"""
    lines = []

    if isinstance(value, str):
        lines.append(f"**{field_name}**:")
        lines.append("")
        lang = "xml" if "xml" in field_name else "gradle" if "gradle" in field_name else "java"
        lines.append(f"```{lang}")
        lines.append(value)
        lines.append("```")
        lines.append("")
    elif isinstance(value, dict):
        # descriptionとcode以外のフィールドがある場合は汎用処理
        if value.get("code"):
            if value.get("title"):
                lines.append(f"**{value['title']}**:")
                lines.append("")
            if value.get("description"):
                lines.append(value["description"])
                lines.append("")
            lines.append("```java")
            lines.append(value["code"])
            lines.append("```")
            lines.append("")
        else:
            # codeフィールドがない場合は汎用出力
            if value.get("description"):
                lines.append(value["description"])
                lines.append("")
            for k, v in value.items():
                if k != "description":
                    lines.append(f"**{k}**: {v}")
                    lines.append("")
    elif isinstance(value, list):
        for ex in value:
            if isinstance(ex, dict):
                if ex.get("title"):
                    lines.append(f"**{ex['title']}**:")
                    lines.append("")
                if ex.get("description"):
                    lines.append(ex["description"])
                    lines.append("")
                if ex.get("code"):
                    lines.append("```java")
                    lines.append(ex["code"])
                    lines.append("```")
                    lines.append("")
            elif isinstance(ex, str):
                lines.append("```java")
                lines.append(ex)
                lines.append("```")
                lines.append("")

    return lines


def render_common_header(data: dict) -> list:
    """共通ヘッダー"""
    lines = []

    lines.append(f"# {data['title']}")
    lines.append("")

    overview_section = data.get("sections", {}).get("overview", {})
    lines.extend(render_section_content("overview", overview_section))

    if overview_section.get("nablarch_version"):
        lines.append(f"**対応Nablarchバージョン**: {overview_section['nablarch_version']}")
        lines.append("")

    if data.get("official_doc_urls"):
        lines.append("**公式ドキュメント**:")
        for url in data["official_doc_urls"]:
            lines.append(f"- [{data['title']}]({url})")
        lines.append("")

    lines.append("---")
    lines.append("")

    return lines


# =============================================================================
# メイン変換ロジック
# =============================================================================

def detect_knowledge_type(file_path: str, data: dict) -> str:
    """知識ファイルの種類を判定"""
    sections = data.get("sections", {})

    if "/checks/" in file_path:
        return "security"
    if "/releases/" in file_path:
        return "release"
    if file_path.endswith("overview.json"):
        return "overview"

    if "check_items" in sections:
        return "security"
    if "changes" in sections:
        return "release"

    return "generic"


def convert_json_to_markdown(data: dict, file_path: str = "") -> str:
    """JSON知識ファイルをMarkdownに変換"""
    lines = []

    knowledge_type = detect_knowledge_type(file_path, data)
    sections = data.get("sections", {})

    # セキュリティチェック
    if knowledge_type == "security":
        lines.append(f"# {data['title']}")
        lines.append("")

        if sections.get("overview"):
            ov = sections["overview"]
            if ov.get("description"):
                lines.append(ov["description"])
                lines.append("")
            if ov.get("source"):
                lines.append(f"**出典**: {ov['source']}")
                lines.append("")
            if ov.get("nablarch_support"):
                lines.append(f"> {ov['nablarch_support']}")
                lines.append("")

        if data.get("official_doc_urls"):
            lines.append("**公式ドキュメント**:")
            for url in data["official_doc_urls"]:
                lines.append(f"- [{data['title']}]({url})")
            lines.append("")

        lines.append("---")
        lines.append("")

        if sections.get("check_items"):
            lines.extend(render_security_check_items(sections["check_items"]))

        # tipsセクションの処理
        if sections.get("tips"):
            lines.append("## Tips")
            lines.append("")
            for tip in sections["tips"]:
                if isinstance(tip, dict):
                    title = tip.get("title", "")
                    description = tip.get("description", "")
                    lines.append(f"**{title}**:")
                    lines.append("")
                    lines.append(description)
                    lines.append("")
            lines.append("---")
            lines.append("")

        return "\n".join(lines)

    # リリース情報
    if knowledge_type == "release":
        lines.append(f"# {data['title']}")
        lines.append("")

        if sections.get("overview"):
            ov = sections["overview"]
            if ov.get("summary"):
                lines.append(ov["summary"])
                lines.append("")
            if ov.get("highlights"):
                lines.append("**主な変更点**:")
                for hl in ov["highlights"]:
                    lines.append(f"- {hl}")
                lines.append("")

        if data.get("official_doc_urls"):
            lines.append("**公式ドキュメント**:")
            for url in data["official_doc_urls"]:
                lines.append(f"- [{data['title']}]({url})")
            lines.append("")

        lines.append("---")
        lines.append("")

        if sections.get("changes"):
            lines.extend(render_release_changes(sections["changes"]))

        return "\n".join(lines)

    # 通常の知識ファイル
    lines.extend(render_common_header(data))

    for section_id, section in sections.items():
        if section_id == "overview":
            continue

        # anti-patterns
        if section_id in ("anti-patterns", "anti_patterns") and isinstance(section, list):
            lines.append(f"## {section_id}")
            lines.append("")
            lines.extend(render_anti_patterns(section))
            lines.append("---")
            lines.append("")
            continue

        # errors
        if section_id == "errors" and isinstance(section, list):
            lines.append(f"## {section_id}")
            lines.append("")
            lines.extend(render_errors(section))
            lines.append("---")
            lines.append("")
            continue

        # その他のセクション
        lines.append(f"## {section_id}")
        lines.append("")
        lines.extend(render_section_content(section_id, section))
        lines.append("---")
        lines.append("")

    return "\n".join(lines)


# =============================================================================
# エントリーポイント
# =============================================================================

def main():
    if len(sys.argv) < 2:
        print("Usage: python json2md.py <input.json> [output.md]")
        sys.exit(1)

    input_path = sys.argv[1]
    if len(sys.argv) >= 3:
        output_path = sys.argv[2]
    else:
        output_path = os.path.splitext(input_path)[0] + ".md"

    with open(input_path, "r", encoding="utf-8") as f:
        data = json.load(f)

    md = convert_json_to_markdown(data, input_path)

    with open(output_path, "w", encoding="utf-8") as f:
        f.write(md)

    print(f"変換完了: {input_path} → {output_path}")
    print(f"  入力: {os.path.getsize(input_path):,} bytes")
    print(f"  出力: {os.path.getsize(output_path):,} bytes")


if __name__ == "__main__":
    main()
