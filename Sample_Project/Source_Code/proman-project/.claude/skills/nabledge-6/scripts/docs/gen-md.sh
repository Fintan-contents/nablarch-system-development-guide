#!/bin/bash
# JSON知識ファイル一括Markdown変換スクリプト
#
# Step3の変換ルールに従い、知識ファイルを人向けMarkdownに変換する
# 入力: ../../knowledge/**/*.json
# 出力: ../../docs/**/*.md

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CONVERTER="$SCRIPT_DIR/json2md.py"
INPUT_DIR="$SCRIPT_DIR/../../knowledge"
OUTPUT_DIR="$SCRIPT_DIR/../../docs"

# 前提条件チェック
if [ ! -f "$CONVERTER" ]; then
    echo "Error: Converter script not found: $CONVERTER" >&2
    echo "  Expected: $CONVERTER" >&2
    exit 1
fi

if [ ! -d "$INPUT_DIR" ]; then
    echo "Error: Input directory not found: $INPUT_DIR" >&2
    exit 1
fi

# Python3チェック
if ! command -v python3 &> /dev/null; then
    echo "Error: python3 not found in PATH" >&2
    exit 1
fi

# 出力ディレクトリ作成
echo "Creating output directory: $OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR"

# JSON ファイル一覧を配列に格納
mapfile -t json_files < <(find "$INPUT_DIR" -name "*.json" -type f | sort)
total=${#json_files[@]}

if [ "$total" -eq 0 ]; then
    echo "Warning: No JSON files found in $INPUT_DIR" >&2
    exit 0
fi

echo "Found $total JSON files to convert"
echo ""

# 変換カウンタとエラーカウンタ
success_count=0
error_count=0
declare -a failed_files

# 一括変換
for json_file in "${json_files[@]}"; do
    # 相対パスを取得
    rel_path="${json_file#$INPUT_DIR/}"

    # 出力パスを決定（.json → .md）
    md_file="$OUTPUT_DIR/${rel_path%.json}.md"

    # ディレクトリが存在することを確認
    md_dir="$(dirname "$md_file")"
    mkdir -p "$md_dir"

    # 変換実行
    current=$((success_count + error_count + 1))
    echo "[$current/$total] Converting: $rel_path"

    if python3 "$CONVERTER" "$json_file" "$md_file" > /dev/null 2>&1; then
        success_count=$((success_count + 1))
    else
        error_count=$((error_count + 1))
        failed_files+=("$rel_path")
        echo "  ⚠️  Conversion failed" >&2
    fi
done

echo ""
echo "=========================================="
echo "Conversion Summary"
echo "=========================================="
echo "  Total files:      $total"
echo "  Success:          $success_count"
echo "  Failed:           $error_count"
echo "  Output directory: $OUTPUT_DIR"
echo ""

if [ "$error_count" -gt 0 ]; then
    echo "Failed files:"
    for failed in "${failed_files[@]}"; do
        echo "  - $failed"
    done
    echo ""
fi

if [ "$success_count" -gt 0 ]; then
    echo "Verification suggestions:"
    echo "  1. Review representative files:"
    echo "     - $OUTPUT_DIR/features/libraries/universal-dao.md"
    echo "     - $OUTPUT_DIR/features/processing/nablarch-batch.md"
    echo "     - $OUTPUT_DIR/checks/security.md"
    echo ""
    echo "  2. Check converted file count:"
    echo "     find $OUTPUT_DIR -name '*.md' -type f | wc -l"
    echo ""

    # README.md生成
    echo "Generating docs/README.md..."
    if [ -x "$SCRIPT_DIR/gen-readme.sh" ]; then
        "$SCRIPT_DIR/gen-readme.sh"
    else
        echo "  ⚠️  Warning: gen-readme.sh not found or not executable" >&2
    fi
fi

# エラーがあった場合は非ゼロで終了
if [ "$error_count" -gt 0 ]; then
    exit 1
fi
