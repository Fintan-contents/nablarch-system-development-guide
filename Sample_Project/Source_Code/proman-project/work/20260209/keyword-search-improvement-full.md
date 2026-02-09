# keyword-search.md 完全改善案

## 主要な変更点

1. **各ステップに「実行するツール」を明記**
2. **Claudeに対する直接的な指示形式に変更**
3. **ツール呼び出しの具体例を追加**
4. **判断基準を明確化**

---

## 改善後のStep-by-Step（主要部分のみ）

### Step 2: Read index.toon（改善版）

**Tool**: Read tool
**Input**: knowledge/index.toon file path
**Output**: 93 entries with titles, hints, and paths

**Action you must take**:
```
Use Read tool to load knowledge/index.toon
```

**After reading**:
- You now have 93 entries loaded in context
- Each line format: `Title, hint1 hint2 ..., path.json`
- Proceed to Step 3 with this data

**Your checklist**:
- [ ] Read tool called successfully
- [ ] 93 entries loaded
- [ ] Line format understood (Title, hints, path)

---

### Step 3: Match keywords against hints（改善版）

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

---

### Step 4: Select candidate files（改善版）

**Tool**: None (selection process)
**Input**: Matched files list from Step 3
**Output**: Top 10-15 candidate files

**Action you must take**:

1. **Sort files** by number of matched hints (descending order)
2. **Keep files with ≥1 matched hint**
3. **Select top 10-15 files** (limit rationale: 15 files × 2 sections/file = 30 sections for judgement)

**Your checklist**:
- [ ] Sorted by matched hint count (highest first)
- [ ] Filtered out files with 0 matches
- [ ] Selected top 10-15 files
- [ ] Ready to read section indexes

**Proceed to Step 5** with your selected files list.

---

### Step 5: Identify candidate sections（改善版）

**Tool**: Bash tool with jq command
**Input**: Selected files from Step 4
**Output**: 20-30 candidate sections with matched hints

**Action you must take**:

For each of the 10-15 selected files:

1. **Read only the .index field** (NOT the entire file):
   ```bash
   jq '.index' knowledge/features/libraries/universal-dao.json
   ```

   **Tool call example**:
   ```
   Use Bash tool with command:
   jq '.index' /path/to/.claude/skills/nabledge-6/knowledge/features/libraries/universal-dao.json
   ```

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

**Your checklist**:
- [ ] Called Bash+jq for each selected file
- [ ] Read only .index field (efficient)
- [ ] Matched keywords against section hints
- [ ] Collected 20-30 candidate sections total
- [ ] Recorded file_path + section_id + matched_hints for each

**Proceed to Step 6** with your candidates list.

---

### Step 6: Call section-judgement workflow（改善版）

**Tool**: None (workflow transition)
**Input**: Candidates list from Step 5
**Output**: Will come from section-judgement workflow

**Action you must take**:

1. **Build candidates list** in this format:
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
   - Read workflows/section-judgement.md
   - Follow its steps with your candidates list
   - Section-judgement will read actual content and judge relevance

**Your checklist**:
- [ ] Built candidates list with all required fields
- [ ] Ready to execute section-judgement workflow

**Proceed to section-judgement workflow** (workflows/section-judgement.md).

---

### Step 7: Return final results（改善版）

**Tool**: None (this is done by section-judgement)
**Input**: Results from section-judgement workflow
**Output**: Final knowledge sections with relevance scores

**What happens**:
- Section-judgement filters out None-relevance sections
- Only High and Partial relevance sections remain
- Final list has 5-15 sections (~5,000 tokens)

**Your action**:
- Receive the filtered, scored sections from section-judgement
- Use this knowledge to answer the user's question
- Answer ONLY using information from the returned sections

---

## Error handling（改善版）

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

---

## Tools reference（新規セクション）

### Read tool
**Use for**: Reading knowledge/index.toon, workflow files
**Example**:
```
Read tool: knowledge/index.toon
```

### Bash tool with jq
**Use for**: Extracting .index or .sections from JSON files
**Example**:
```
Bash: jq '.index' knowledge/features/libraries/universal-dao.json
```

### Grep tool (optional)
**Use for**: Searching keywords in index.toon
**Example**:
```
Grep: pattern="ページング", path="knowledge/index.toon", output_mode="content"
```

**Note**: Manual matching is often more efficient than Grep for 93 entries.
