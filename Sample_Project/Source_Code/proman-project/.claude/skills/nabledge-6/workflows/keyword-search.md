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

**Who executes**: Claude Code (you)

**Input**: User's request (natural language)

**Output**: Candidates list for section-judgement workflow

**Strategy**: Technical axis - match keywords from the request against search hints in index.toon

**Tools you will use**:
- Read tool: Read knowledge/index.toon
- Grep tool (optional): Search for keywords in index.toon
- Bash tool with jq: Extract .index from knowledge files

**Expected tool calls**: 10-15 calls

**Expected output**: 20-30 candidate sections

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

**Tool**: None (mental process)

**Input**: User's natural language request

**Output**: List of keywords at 3 levels

**Action**: Analyze the user request and extract keywords at three levels to match the structure of index.toon hints.

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

**Your checklist**:
- [ ] Extracted technical domain keywords (e.g., データベース, バッチ, ハンドラ)
- [ ] Extracted technical component keywords (e.g., DAO, JDBC, JPA)
- [ ] Extracted functional keywords (e.g., ページング, 検索, 更新)
- [ ] Included both Japanese and English terms
- [ ] Included common abbreviations and related concepts

**Proceed to Step 2** with your keyword list.

### Step 2: Read index.toon

**Tool**: Read tool

**Input**: knowledge/index.toon file path

**Output**: 93 entries with titles, hints, and paths

**Action you must take**:

Use Read tool to load knowledge/index.toon.

**After reading**:
- You now have 93 entries loaded in context
- Each line format: `Title, hint1 hint2 ..., path.json`
  - **hints**: Space-separated search terms for matching
  - **path**: Knowledge file location
- Proceed to Step 3 with this data

**Your checklist**:
- [ ] Read tool called successfully
- [ ] 93 entries loaded
- [ ] Line format understood (Title, hints, path)

**Proceed to Step 3** with index.toon data.

### Step 3: Match keywords against hints

**Tool**: None (mental process, or optionally Grep tool)

**Input**: Keywords from Step 1 + index.toon data from Step 2

**Output**: List of files with matched hint counts

**Action you must take**:

For each of the 93 entries in index.toon:
1. Check if any keyword from Step 1 matches the hints in this entry
2. Count how many hints matched
3. Record the matched hints

**Matching rules**:
- Case-insensitive: "ページング" matches "ページング" or "PAGING"
- Partial matching allowed: "ページ" matches "ページング"
- Exact match preferred over partial match

**Example**:
- Keywords: ["ページング", "DAO", "UniversalDao"]
- Entry: "ユニバーサルDAO, データベース DAO O/Rマッパー CRUD 検索 ページング, features/libraries/universal-dao.json"
- Matched hints: ["DAO", "ページング"] (2 matches)

**Your output format** (mental or notes):
```
universal-dao.json: 5 matched hints ["データベース", "DAO", "O/Rマッパー", "検索", "ページング"]
database-access.json: 2 matched hints ["データベース", "JDBC"]
nablarch-batch.json: 1 matched hint ["バッチ"]
```

**Your checklist**:
- [ ] Checked all 93 entries
- [ ] Counted matched hints for each entry
- [ ] Recorded which hints matched

**Proceed to Step 4** with your matched files list.

### Step 4: Select candidate files

**Tool**: None (selection process)

**Input**: Matched files list from Step 3

**Output**: Top 10-15 candidate files

**Action you must take**:

1. **Sort files** by number of matched hints (descending order)
2. **Keep files with ≥1 matched hint**
3. **Select top 10-15 files** (limit rationale: 15 files × 2 sections/file = 30 sections for judgement)

**Note**: Identify candidates only. Relevance scoring happens in section-judgement workflow (Step 6).

**Your checklist**:
- [ ] Sorted by matched hint count (highest first)
- [ ] Filtered out files with 0 matches
- [ ] Selected top 10-15 files
- [ ] Ready to read section indexes

**Proceed to Step 5** with your selected files list.

### Step 5: Identify candidate sections

**Tool**: Bash tool with jq command

**Input**: Selected files from Step 4

**Output**: 20-30 candidate sections with matched hints

**Action you must take**:

For each of the 10-15 selected files:

1. **Read only the .index field** (NOT the entire file) using Bash tool with jq:
   ```bash
   jq '.index' knowledge/features/libraries/universal-dao.json
   ```

   **Tool call example**:
   ```
   Use Bash tool with command:
   jq '.index' knowledge/features/libraries/universal-dao.json
   ```
   (Use the full path from skill base directory)

2. **The index output** will look like:
   ```json
   [
     { "id": "overview", "hints": ["UniversalDao", "ユニバーサルDAO", "O/Rマッパー"] },
     { "id": "paging", "hints": ["ページング", "per", "page", "Pagination"] },
     { "id": "crud", "hints": ["登録", "更新", "削除", "insert", "update"] }
   ]
   ```

3. **Match your keywords** against section hints using same rules as Step 3

4. **Keep sections with ≥1 matched hint**

5. **Stop when you have 20-30 candidate sections total** (across all files)

**Example for one file**:
- File: universal-dao.json
- Your keywords: ["ページング", "DAO", "検索"]
- Section "paging": matched = ["ページング"] → KEEP
- Section "overview": matched = ["DAO"] → KEEP
- Section "search": matched = ["検索"] → KEEP
- Section "crud": matched = [] → SKIP

**Important**: Only read the `index` field, not the entire file. This saves tokens and improves efficiency.

**Your checklist**:
- [ ] Called Bash+jq for each selected file
- [ ] Read only .index field (efficient)
- [ ] Matched keywords against section hints
- [ ] Collected 20-30 candidate sections total
- [ ] Recorded file_path + section_id + matched_hints for each

**Proceed to Step 6** with your candidates list.

### Step 6: Call section-judgement workflow

**Tool**: None (workflow transition)

**Input**: Candidates list from Step 5

**Output**: Will come from section-judgement workflow

**Action you must take**:

1. **Build candidates list** in this format (mental or notes):
   ```json
   {
     "candidates": [
       {
         "file_path": "features/libraries/universal-dao.json",
         "section": "paging",
         "matched_hints": ["ページング", "per", "page"]
       },
       {
         "file_path": "features/libraries/universal-dao.json",
         "section": "overview",
         "matched_hints": ["DAO", "UniversalDao"]
       }
     ]
   }
   ```

2. **Transition to section-judgement workflow**:
   - Read workflows/section-judgement.md (if needed)
   - Follow its steps with your candidates list
   - Section-judgement will read actual content and judge relevance

**Your checklist**:
- [ ] Built candidates list with all required fields
- [ ] Ready to execute section-judgement workflow

**Proceed to section-judgement workflow** (workflows/section-judgement.md).

### Step 7: Return final results

**Tool**: None (this is done by section-judgement)

**Input**: Results from section-judgement workflow

**Output**: Final knowledge sections with relevance scores

**What happens**:
- Section-judgement filters out None-relevance sections
- Only High and Partial relevance sections remain
- Final list has 5-15 sections (~5,000 tokens)

**Final output structure** (from section-judgement):
```json
{
  "sections": [
    {
      "file_path": "features/libraries/universal-dao.json",
      "section": "paging",
      "matched_hints": ["ページング", "per", "page"],
      "relevance": 2,
      "judgement": "High - pagination API and examples"
    },
    {
      "file_path": "features/libraries/universal-dao.json",
      "section": "overview",
      "matched_hints": ["DAO", "UniversalDao"],
      "relevance": 1,
      "judgement": "Partial - DAO basics for context"
    }
  ],
  "summary": {
    "high_count": 1,
    "partial_count": 1,
    "total_tokens": "~1000"
  }
}
```

**Your action**:
- Receive the filtered, scored sections from section-judgement
- Use this knowledge to answer the user's question
- Answer ONLY using information from the returned sections

**Your checklist**:
- [ ] Received final sections from section-judgement
- [ ] Sections have relevance scores (High=2, Partial=1)
- [ ] Ready to answer using only this knowledge

## Output

Return the final results from section-judgement workflow (sections with relevance scores, filtered).

## Error handling

### If no keyword matches found (Step 3):

**Action**:
1. Inform user: "キーワードマッチが見つかりませんでした"
2. List your extracted keywords
3. Suggest: "より具体的な技術用語を含めてください"
4. Show available categories from index.toon

**Example response**:
```
抽出したキーワード: ["ページング", "paging"]
マッチしたファイル: 0件

利用可能なカテゴリ（index.toonより）:
- データベース関連: ユニバーサルDAO, データベースアクセス
- バッチ関連: Nablarchバッチ, データリードハンドラ
- テスト関連: 自動テストフレームワーク
```

### If too many candidates (>30 sections):

**Action**:
1. Select files with 2+ matched hints (stronger signal)
2. Limit to top 15 files and top 30 sections total
3. Pass to section-judgement for further filtering

### If section-judgement returns no results (all None):

**Action**:
1. Return the error message from section-judgement
2. Show available knowledge from index.toon
3. State clearly: "この情報は知識ファイルに含まれていません"
4. DO NOT answer from LLM training data

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

## Tools reference

### Read tool
**Use for**: Reading knowledge/index.toon, workflow files

**Example**:
```
Use Read tool with file_path: knowledge/index.toon
```

### Bash tool with jq
**Use for**: Extracting .index or .sections from JSON files

**Command format**:
```bash
jq '.index' knowledge/features/libraries/universal-dao.json
```

**Example usage**:
```
Use Bash tool with command:
jq '.index' knowledge/features/libraries/universal-dao.json
```

### Grep tool (optional)
**Use for**: Searching keywords in index.toon

**Example**:
```
Use Grep tool with:
- pattern: "ページング"
- path: "knowledge/index.toon"
- output_mode: "content"
```

**Note**: Manual matching is often more efficient than Grep for 93 entries.
