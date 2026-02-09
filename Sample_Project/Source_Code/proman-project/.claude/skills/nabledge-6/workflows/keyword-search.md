# Keyword Search Workflow

This workflow searches the knowledge index (index.toon) using keyword matching to find relevant files and sections.

## Table of Contents

- [Overview](#overview)
- [Search process](#search-process)
  - [Step 1: Extract keywords from user request](#step-1-extract-keywords-from-user-request)
  - [Step 2: Read index.toon](#step-2-read-indextoon)
  - [Step 3: Match keywords against hints](#step-3-match-keywords-against-hints)
  - [Step 4: Select candidate files](#step-4-select-candidate-files)
  - [Step 5: Identify candidate sections](#step-5-identify-candidate-sections)
  - [Step 6: Call section-judgement workflow](#step-6-call-section-judgement-workflow)
  - [Step 7: Return final results](#step-7-return-final-results)
- [Output](#output)
- [Error handling](#error-handling)
- [Notes](#notes)
- [Example execution](#example-execution)

## Overview

**Input**: User's request (natural language)

**Output**: `pointers` object containing candidate files and sections with matched hints

**Strategy**: Technical axis - match keywords from the request against search hints in index.toon

## Search process

Copy this checklist and track your progress:

```
Keyword Search Progress:
- [ ] Step 1: Extract keywords from user request
- [ ] Step 2: Read index.toon
- [ ] Step 3: Match keywords against hints
- [ ] Step 4: Select candidate files
- [ ] Step 5: Identify candidate sections
- [ ] Step 6: Call section-judgement workflow
- [ ] Step 7: Return final results with relevance
```

### Step 1: Extract keywords from user request

Identify keywords at three levels to match the structure of index.toon hints.

**Three-level keyword extraction**:

1. **Technical domain** (技術領域): Broad category
   - Examples: データベース, バッチ, ハンドラ, Web, REST, テスト, ファイル, ログ, トランザクション, セキュリティ, メッセージング

2. **Technical component** (技術要素): Specific technologies, libraries, patterns
   - Examples: DAO, JDBC, JPA, Bean Validation, JSON, XML, JSP, HTTP, SQL, O/Rマッパー

3. **Functional** (機能): Specific features, operations, methods
   - Examples: ページング, 検索, 登録, 更新, 削除, 接続, コミット, ロールバック, 変換, 出力

**Examples**:
- Request: "ページングを実装したい"
  - Technical domain: ["データベース", "database", "DB"]
  - Technical component: ["DAO", "UniversalDao", "O/Rマッパー", "JPA"]
  - Functional: ["ページング", "paging", "検索", "search", "per", "page", "limit", "offset"]

- Request: "UniversalDaoの使い方"
  - Technical domain: ["データベース", "database"]
  - Technical component: ["DAO", "UniversalDao", "O/Rマッパー", "JPA", "JDBC"]
  - Functional: ["CRUD", "検索", "登録", "更新", "削除", "insert", "update", "delete", "select"]

- Request: "トランザクション管理のエラー"
  - Technical domain: ["データベース", "database", "トランザクション", "transaction"]
  - Technical component: ["ハンドラ", "handler"]
  - Functional: ["コミット", "commit", "ロールバック", "rollback", "エラー", "error", "例外", "exception"]

- Request: "バッチでファイルを読み込みたい"
  - Technical domain: ["バッチ", "batch", "ファイル", "file"]
  - Technical component: ["ハンドラ", "handler", "DataReader"]
  - Functional: ["読み込み", "read", "入力", "input", "データ処理"]

**Critical**: Always extract technical domain level keywords. Without them, you will miss relevant files in index.toon.

**Tip**: Include both Japanese and English terms, common abbreviations, and related concepts at all three levels.

### Step 2: Read index.toon

Read the knowledge index:

```bash
cat knowledge/index.toon
```

Each line: `Title, hint1 hint2 ..., path.json`
- **hints**: Space-separated search terms for matching
- **path**: Knowledge file location

### Step 3: Match keywords against hints

For each file in index.toon:
1. Check if any of your keywords match the hints
2. Count the number of matched hints
3. Record which hints matched

**Matching rules**:
- Case-insensitive matching
- Partial matching allowed (e.g., "ページ" matches "ページング")
- Exact matches have higher weight than partial matches

### Step 4: Select candidate files

For each file in index.toon:
1. Count the number of matched hints
2. Keep files with at least 1 matched hint

Sort files by number of matched hints (descending).

**Limit**: Select top 10-15 files (rationale: section-judgement can efficiently process 20-30 sections. 15 files × 2 sections/file = 30 sections).

**Note**: Identify candidates only. Scoring happens in section-judgement workflow (Step 6).

### Step 5: Identify candidate sections

For each selected file:

1. Extract only the `index` field (avoids reading entire file):
   ```bash
   jq '.index' knowledge/features/libraries/universal-dao.json
   ```

2. The index contains section-level hints:
   ```json
   [
     { "id": "overview", "hints": ["UniversalDao", "ユニバーサルDAO"] },
     { "id": "paging", "hints": ["ページング", "per", "page", "offset"] },
     { "id": "crud", "hints": ["登録", "更新", "削除", "insert", "update"] }
   ]
   ```

3. Match keywords against section hints using the same rules as Step 3

4. Keep sections with at least 1 matched hint

**Important**: Only read the `index` field, not the entire file. This saves tokens and improves efficiency.

**Limit**: Collect up to 20-30 candidate sections total (rationale: section-judgement will filter to 10-15 final sections; starting with 20-30 provides enough candidates while keeping processing efficient).

**Note**: Identify candidates that match keywords only. Relevance judgement happens in Step 6 (section-judgement workflow).

### Step 6: Call section-judgement workflow

Build a candidates list and pass to section-judgement:

```json
{
  "candidates": [
    {
      "file_id": "F1",
      "file_path": "features/libraries/universal-dao.json",
      "section": "paging",
      "matched_hints": ["ページング", "per", "page"]
    },
    {
      "file_id": "F1",
      "file_path": "features/libraries/universal-dao.json",
      "section": "search",
      "matched_hints": ["検索", "search"]
    },
    {
      "file_id": "F2",
      "file_path": "features/libraries/database-access.json",
      "section": "query",
      "matched_hints": ["SQL"]
    }
  ]
}
```

**Call section-judgement workflow** with this candidates list.

See [workflows/section-judgement.md](workflows/section-judgement.md) for the judgement process.

### Step 7: Return final results

Section-judgement workflow will:
1. Read each section's content
2. Judge relevance (High/Partial/None)
3. Filter out None-relevance sections
4. Return final results with relevance scores

**Final output structure**:
```json
{
  "sections": [
    {
      "file_id": "F1",
      "file_path": "features/libraries/universal-dao.json",
      "section": "paging",
      "matched_hints": ["ページング", "per", "page"],
      "relevance": 2,
      "judgement": "High - contains pagination API and examples"
    },
    {
      "file_id": "F1",
      "file_path": "features/libraries/universal-dao.json",
      "section": "search",
      "matched_hints": ["検索", "search"],
      "relevance": 1,
      "judgement": "Partial - related search functionality"
    }
  ],
  "summary": {
    "high_count": 1,
    "partial_count": 1,
    "total_tokens": "~1500"
  }
}
```

Return this final result to the user.

## Output

Return the final results from section-judgement workflow (sections with relevance scores, filtered).

## Error handling

**If no keyword matches found** (Step 3):
1. Return empty candidates: `{"candidates": []}`
2. Suggest alternative keywords or broader search terms
3. Consider using intent-search workflow as fallback

**If too many candidates** (>30 sections):
1. Select files with 2+ matched hints (stronger signal)
2. Limit to top 15 files and top 30 sections total
3. Pass to section-judgement for further filtering

**If section-judgement returns no results** (all None):
1. Return the error message from section-judgement
2. Show available knowledge from index.toon
3. State clearly: "この情報は知識ファイルに含まれていません"

## Notes

- This workflow focuses on technical axis (keyword matching)
- For purpose-oriented search, use intent-search workflow in parallel
- The two workflows can be merged to reduce duplicates and improve coverage
- **This workflow includes relevance judgement** via section-judgement workflow (Step 6)
- Final output always has relevance scores and filters out None-relevance sections
- Expect section-judgement to filter many candidates as "None" (this is normal behavior)

## Example execution

**User request**: "ページングを実装したい"

**Step 1 - Keywords** (three levels):
- Technical domain: ["データベース", "database", "DB"]
- Technical component: ["DAO", "UniversalDao", "O/Rマッパー", "JPA"]
- Functional: ["ページング", "paging", "検索", "search", "per", "page", "limit", "offset"]

**Step 2 - Index read**: (93 entries loaded)

**Step 3 - Matches**:
- universal-dao.json: hints matched = ["データベース", "DAO", "O/Rマッパー", "検索", "ページング"] (5 hints)
- database-access.json: hints matched = ["データベース", "SQL"] (2 hints)
- nablarch-batch.json: hints matched = ["データ処理"] (1 hint)

**Step 4 - Select candidates**:
- F1: universal-dao.json (5 matched hints: ["データベース", "DAO", "O/Rマッパー", "検索", "ページング"])
- F2: database-access.json (2 matched hints: ["データベース", "SQL"])

**Step 5 - Identify sections**:
- F1 → paging section: matched = ["ページング", "paging", "検索", "per", "page"]
- F1 → search section: matched = ["検索", "search"]
- F1 → overview section: matched = ["DAO", "UniversalDao", "データベース"]
- F2 → query section: matched = ["SQL", "データベース"]

**Step 6 - Build candidates list**:
```json
{
  "candidates": [
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "paging", "matched_hints": ["ページング", "per", "page"] },
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "search", "matched_hints": ["検索"] },
    { "file_id": "F2", "file_path": "features/libraries/database-access.json", "section": "query", "matched_hints": ["SQL"] }
  ]
}
```

**Step 6 - Call section-judgement**: Pass candidates to section-judgement workflow.

**Step 7 - Final output** (from section-judgement):
```json
{
  "sections": [
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "paging", "matched_hints": ["ページング", "per", "page"], "relevance": 2, "judgement": "High - pagination API and examples" }
  ],
  "summary": {
    "high_count": 1,
    "partial_count": 0,
    "total_tokens": "~500"
  }
}
```

**Note**: search and query sections were judged as None by section-judgement and filtered out.
