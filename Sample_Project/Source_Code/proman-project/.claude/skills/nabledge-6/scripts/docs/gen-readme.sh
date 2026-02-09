#!/bin/bash
# docs/README.md自動生成スクリプト
#
# 変換されたMarkdownファイルへのリンク一覧を含むREADMEを生成する

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DOCS_DIR="$SCRIPT_DIR/../../docs"
README_FILE="$DOCS_DIR/README.md"

# docsディレクトリの存在確認
if [ ! -d "$DOCS_DIR" ]; then
    echo "Error: docs directory not found: $DOCS_DIR" >&2
    exit 1
fi

# Markdownファイルをカウント
md_count=$(find "$DOCS_DIR" -name "*.md" -type f ! -name "README.md" | wc -l)

if [ "$md_count" -eq 0 ]; then
    echo "Warning: No markdown files found in $DOCS_DIR" >&2
    exit 1
fi

echo "Generating README.md with $md_count markdown files..."

# README.md生成
cat > "$README_FILE" <<'EOF'
# Nabledge-6 知識ドキュメント

このディレクトリには、Nabledge-6の知識ファイル（JSON）から自動変換された人向けMarkdownファイルが格納されています。

EOF

# カテゴリごとにファイルをリスト

# 概要
if [ -f "$DOCS_DIR/overview.md" ]; then
    echo "" >> "$README_FILE"
    echo "### 概要" >> "$README_FILE"
    echo "" >> "$README_FILE"
    title=$(head -n 1 "$DOCS_DIR/overview.md" | sed 's/^# //')
    echo "- [$title](overview.md)" >> "$README_FILE"
fi

# 処理方式
echo "" >> "$README_FILE"
echo "### 処理方式" >> "$README_FILE"
echo "" >> "$README_FILE"
find "$DOCS_DIR/features/processing" -name "*.md" -type f 2>/dev/null | sort | while read -r file; do
    rel_path="${file#$DOCS_DIR/}"
    filename=$(basename "$file" .md)
    # タイトルをファイルから抽出（1行目の # を除去）
    title=$(head -n 1 "$file" | sed 's/^# //')
    echo "- [$title]($rel_path)" >> "$README_FILE"
done || true

# ライブラリ
echo "" >> "$README_FILE"
echo "### ライブラリ" >> "$README_FILE"
echo "" >> "$README_FILE"
find "$DOCS_DIR/features/libraries" -name "*.md" -type f 2>/dev/null | sort | while read -r file; do
    rel_path="${file#$DOCS_DIR/}"
    title=$(head -n 1 "$file" | sed 's/^# //')
    echo "- [$title]($rel_path)" >> "$README_FILE"
done || true

# ハンドラ
echo "" >> "$README_FILE"
echo "### ハンドラ" >> "$README_FILE"
echo "" >> "$README_FILE"

echo "#### 共通ハンドラ" >> "$README_FILE"
echo "" >> "$README_FILE"
find "$DOCS_DIR/features/handlers/common" -name "*.md" -type f 2>/dev/null | sort | while read -r file; do
    rel_path="${file#$DOCS_DIR/}"
    title=$(head -n 1 "$file" | sed 's/^# //')
    echo "- [$title]($rel_path)" >> "$README_FILE"
done || true

echo "" >> "$README_FILE"
echo "#### バッチハンドラ" >> "$README_FILE"
echo "" >> "$README_FILE"
find "$DOCS_DIR/features/handlers/batch" -name "*.md" -type f 2>/dev/null | sort | while read -r file; do
    rel_path="${file#$DOCS_DIR/}"
    title=$(head -n 1 "$file" | sed 's/^# //')
    echo "- [$title]($rel_path)" >> "$README_FILE"
done || true

# ツール（NTF）
echo "" >> "$README_FILE"
echo "### ツール（NTF: Nablarch Testing Framework）" >> "$README_FILE"
echo "" >> "$README_FILE"
find "$DOCS_DIR/features/tools" -name "*.md" -type f 2>/dev/null | sort | while read -r file; do
    rel_path="${file#$DOCS_DIR/}"
    title=$(head -n 1 "$file" | sed 's/^# //')
    echo "- [$title]($rel_path)" >> "$README_FILE"
done || true

# アダプタ
echo "" >> "$README_FILE"
echo "### アダプタ" >> "$README_FILE"
echo "" >> "$README_FILE"
find "$DOCS_DIR/features/adapters" -name "*.md" -type f 2>/dev/null | sort | while read -r file; do
    rel_path="${file#$DOCS_DIR/}"
    title=$(head -n 1 "$file" | sed 's/^# //')
    echo "- [$title]($rel_path)" >> "$README_FILE"
done || true

# チェック項目
echo "" >> "$README_FILE"
echo "### チェック項目" >> "$README_FILE"
echo "" >> "$README_FILE"
find "$DOCS_DIR/checks" -name "*.md" -type f 2>/dev/null | sort | while read -r file; do
    rel_path="${file#$DOCS_DIR/}"
    title=$(head -n 1 "$file" | sed 's/^# //')
    echo "- [$title]($rel_path)" >> "$README_FILE"
done || true

# リリースノート
echo "" >> "$README_FILE"
echo "### リリースノート" >> "$README_FILE"
echo "" >> "$README_FILE"
find "$DOCS_DIR/releases" -name "*.md" -type f 2>/dev/null | sort | while read -r file; do
    rel_path="${file#$DOCS_DIR/}"
    title=$(head -n 1 "$file" | sed 's/^# //')
    echo "- [$title]($rel_path)" >> "$README_FILE"
done || true

# フッター（なし）

echo ""
echo "✅ README.md generated successfully!"
echo "   File: $README_FILE"
echo "   Total markdown files: $md_count"
echo ""
