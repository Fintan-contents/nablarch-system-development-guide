#!/bin/bash
# JSON→Markdown変換の検証スクリプト
#
# 全てのJSON/MDペアについて変換品質をチェックする

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
KNOWLEDGE_DIR="$SCRIPT_DIR/../../knowledge"
DOCS_DIR="$SCRIPT_DIR/../../docs"

# カラーコード
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# カウンター
total_files=0
passed=0
failed=0

declare -a failed_files

echo "=========================================="
echo "JSON → Markdown Conversion Verification"
echo "=========================================="
echo ""

# JSONファイルを取得
mapfile -t json_files < <(find "$KNOWLEDGE_DIR" -name "*.json" -type f | sort)

for json_file in "${json_files[@]}"; do
    total_files=$((total_files + 1))

    # 相対パス取得
    rel_path="${json_file#$KNOWLEDGE_DIR/}"
    md_file="$DOCS_DIR/${rel_path%.json}.md"

    # ファイル名表示
    echo "[$total_files] Checking: $rel_path"

    # 基本チェック
    has_error=0

    # 1. MDファイルが存在するか
    if [ ! -f "$md_file" ]; then
        echo -e "  ${RED}✗ Markdown file not generated${NC}"
        has_error=1
        failed_files+=("$rel_path: MD file missing")
        continue
    fi

    # 2. JSONが読めるか
    if ! json_title=$(jq -r '.title' "$json_file" 2>/dev/null); then
        echo -e "  ${RED}✗ Invalid JSON format${NC}"
        has_error=1
        failed_files+=("$rel_path: Invalid JSON")
        continue
    fi

    # 3. タイトルが変換されているか
    md_title=$(head -n 1 "$md_file" | sed 's/^# //')
    if [ "$json_title" != "$md_title" ]; then
        echo -e "  ${RED}✗ Title mismatch${NC}"
        echo "    JSON: $json_title"
        echo "    MD:   $md_title"
        has_error=1
        failed_files+=("$rel_path: Title mismatch")
    fi

    # 4. セクション数をチェック
    json_sections=$(jq -r '.sections | keys[]' "$json_file" 2>/dev/null | wc -l)
    md_h2_count=$(grep -c "^## " "$md_file" || true)

    # セクション数の差分をチェック
    section_diff=$((json_sections - md_h2_count))

    # セキュリティチェックは2セクション→11セクションに展開されるので特別扱い
    if [[ "$rel_path" == "checks/security.json" ]]; then
        # セキュリティチェックはcheck_items配列が展開されるため、セクション数チェックをスキップ
        :
    elif [ "$section_diff" -gt 2 ] || [ "$section_diff" -lt -1 ]; then
        echo -e "  ${RED}✗ Section count difference: JSON=$json_sections, MD=$md_h2_count (diff=$section_diff)${NC}"
        has_error=1
        failed_files+=("$rel_path: Section count mismatch")
    fi

    # 5. official_doc_urlsが変換されているか
    has_official_urls=$(jq -r '.official_doc_urls | length' "$json_file" 2>/dev/null)
    if [ "$has_official_urls" != "null" ] && [ "$has_official_urls" -gt 0 ]; then
        if ! grep -qF "**公式ドキュメント**:" "$md_file"; then
            echo -e "  ${RED}✗ Official doc URLs not found in MD${NC}"
            has_error=1
            failed_files+=("$rel_path: Official doc URLs missing")
        fi
    fi

    # 6. indexが変換されていないか（AI用のため変換すべきでない）
    if grep -q '"index"' "$json_file" 2>/dev/null; then
        if grep -q "## index" "$md_file"; then
            echo -e "  ${RED}✗ Index section found in MD (should not be converted)${NC}"
            has_error=1
            failed_files+=("$rel_path: Index section should not be in MD")
        fi
    fi

    # 7. ファイルサイズチェック（空でないか）
    md_size=$(stat -f%z "$md_file" 2>/dev/null || stat -c%s "$md_file" 2>/dev/null)
    if [ "$md_size" -lt 100 ]; then
        echo -e "  ${RED}✗ MD file too small (${md_size} bytes)${NC}"
        has_error=1
        failed_files+=("$rel_path: MD file too small")
    fi

    # 8. JSONの全ての値がMDに存在するかチェック（index, search_hints, idを除く）
    # JSONから全てのテキスト値を抽出（indexとsearch_hintsとidを除外）
    json_values=$(jq -r '
        # indexとsearch_hintsとidフィールドを削除
        del(.index) | del(.search_hints) | del(.id) |
        # 再帰的に全ての文字列値を抽出
        .. |
        select(type == "string") |
        select(length > 0)
    ' "$json_file" 2>/dev/null | sort -u)

    # 各値がMarkdownファイルに存在するかチェック
    missing_values=()
    while IFS= read -r value; do
        # 空行をスキップ
        [ -z "$value" ] && continue

        # Markdownファイルに値が存在するかチェック
        # grepが'--'で始まる文字列をオプションと認識しないよう、'--'セパレーターを使用
        if ! grep -qF -- "$value" "$md_file"; then
            missing_values+=("$value")
        fi
    done <<< "$json_values"

    # 見つからない値がある場合は失敗
    if [ "${#missing_values[@]}" -gt 0 ]; then
        echo -e "  ${RED}✗ Some JSON values not found in MD (${#missing_values[@]} values)${NC}"
        if [ "${#missing_values[@]}" -le 5 ]; then
            # 5個以下なら全て表示
            for val in "${missing_values[@]}"; do
                # 長い値は省略表示
                if [ "${#val}" -gt 60 ]; then
                    echo "    - ${val:0:60}..."
                else
                    echo "    - $val"
                fi
            done
        else
            # 多い場合は最初の3つだけ表示
            for i in {0..2}; do
                val="${missing_values[$i]}"
                if [ "${#val}" -gt 60 ]; then
                    echo "    - ${val:0:60}..."
                else
                    echo "    - $val"
                fi
            done
            echo "    ... and $((${#missing_values[@]} - 3)) more"
        fi
        has_error=1
        failed_files+=("$rel_path: ${#missing_values[@]} JSON values not found in MD")
    fi

    # 結果判定
    if [ "$has_error" -eq 1 ]; then
        failed=$((failed + 1))
    else
        passed=$((passed + 1))
        echo -e "  ${GREEN}✓ Passed${NC}"
    fi

    echo ""
done

# README.mdのリンクカウントチェック
echo "=========================================="
echo "README Link Count Verification"
echo "=========================================="
echo ""

README_FILE="$DOCS_DIR/README.md"

if [ -f "$README_FILE" ]; then
    # README内の.mdファイルへのリンク数をカウント
    readme_link_count=$(grep -o '\[.*\](.*\.md)' "$README_FILE" | wc -l | tr -d ' ')

    # 実際の.mdファイル数をカウント（README.md自体は除外）
    actual_md_count=$(find "$DOCS_DIR" -name "*.md" -type f ! -name "README.md" | wc -l | tr -d ' ')

    echo "README links to .md files: $readme_link_count"
    echo "Actual .md files in docs:  $actual_md_count"

    if [ "$readme_link_count" -eq "$actual_md_count" ]; then
        echo -e "${GREEN}✓ Link count matches file count${NC}"
    else
        echo -e "${RED}✗ Link count mismatch!${NC}"
        echo -e "  ${RED}Missing $((actual_md_count - readme_link_count)) links in README${NC}"
        failed=$((failed + 1))
        failed_files+=("README.md: Link count mismatch ($readme_link_count links vs $actual_md_count files)")
    fi
else
    echo -e "${RED}✗ README.md not found${NC}"
    failed=$((failed + 1))
    failed_files+=("README.md: File not found")
fi

echo ""

# サマリー表示
echo "=========================================="
echo "Verification Summary"
echo "=========================================="
echo -e "Total files:    $total_files"
echo -e "${GREEN}Passed:         $passed${NC}"
echo -e "${RED}Failed:         $failed${NC}"
echo ""

# エラーの詳細
if [ "$failed" -gt 0 ]; then
    echo "Failed files:"
    for fail in "${failed_files[@]}"; do
        echo -e "  ${RED}✗${NC} $fail"
    done
    echo ""
    exit 1
fi

echo -e "${GREEN}All files converted successfully!${NC}"
