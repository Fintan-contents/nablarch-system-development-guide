# section-judgement.md 完全改善案

## 主要な変更点

1. **判断プロセスを「質問ベース」に変更**（Claude が自問自答できる形式）
2. **各ステップに「実行するツール」を明記**
3. **判断基準を具体的な質問形式に変更**
4. **ツール呼び出しの具体例を追加**

---

## Overview（改善版）

**Who executes**: Claude Code (you)
**Input**: Candidates list from keyword-search workflow
**Output**: Filtered sections with relevance scores

**Tools you will use**:
- Bash tool with jq: Extract specific sections from JSON files
- None (mental): Judge relevance based on content

**Expected tool calls**: 5-10 calls (read 5-10 section contents)
**Expected output**: 5-15 sections with High/Partial relevance

**Process summary**:
1. Read each candidate section's actual content
2. Ask yourself specific questions to judge relevance
3. Assign relevance score: High (2), Partial (1), or None (0)
4. Filter out None, keep High and Partial
5. Sort by relevance (High first)

---

## Step 1: Understand the user request（改善版）

**Tool**: None (analysis)
**Input**: User's original request
**Output**: Clear understanding of what user needs

**Action you must take**:

Ask yourself these questions:

1. **What does the user want to achieve?**
   - Example: "ページングを実装したい" → User wants to implement pagination

2. **What information would directly answer this?**
   - Example: Pagination API methods, parameters, code examples

3. **What background knowledge might help?**
   - Example: Understanding of database queries, UniversalDao basics

4. **What information is NOT needed?**
   - Example: Unrelated features like logging, file I/O

**Your checklist**:
- [ ] Identified primary goal
- [ ] Listed required information to answer
- [ ] Listed useful background information
- [ ] Listed irrelevant topics to filter out

**Proceed to Step 2** with clear understanding of the request.

---

## Step 2: Read candidate sections（改善版）

**Tool**: Bash tool with jq
**Input**: Candidates list (file paths + section IDs)
**Output**: Actual section content loaded in context

**Action you must take**:

For each candidate (start with first 5-10, can stop early if enough High-relevance found):

1. **Extract the specific section** using jq:
   ```bash
   jq '.sections.paging' knowledge/features/libraries/universal-dao.json
   ```

   **Tool call example**:
   ```
   Use Bash tool with command:
   jq '.sections.paging' /path/to/.claude/skills/nabledge-6/knowledge/features/libraries/universal-dao.json
   ```

2. **Read the section content carefully**:
   - What topic does this section explain?
   - What methods/APIs does it document?
   - What code examples does it provide?
   - What configuration does it require?
   - What errors or constraints does it mention?

3. **Base understanding ONLY on what is written** in the section
   - Do not supplement with external knowledge
   - Do not assume information not present in the section
   - Only use what you can see in the section content

**Efficiency note**:
- If you find 5+ High-relevance sections, you may skip remaining candidates
- Rationale: 5 sections × 500 tokens = 2,500 tokens provides sufficient knowledge

**Your checklist** (for each section):
- [ ] Called Bash+jq to extract section
- [ ] Read section content carefully
- [ ] Understood what the section explains
- [ ] Based understanding only on section content
- [ ] Ready to judge relevance

**Proceed to Step 3** for each section after reading.

---

## Step 3: Judge relevance level（改善版）

**Tool**: None (judgement process)
**Input**: Section content from Step 2 + user request understanding from Step 1
**Output**: Relevance score (High=2, Partial=1, None=0)

**Action you must take**:

For each section you read, ask yourself these **judgement questions**:

### Questions for High relevance (2 points)

Ask all 3 questions. If ALL are "Yes", assign High (2):

1. **Does this section directly address the user's primary goal?**
   - Example: User wants pagination → Section explains pagination API → Yes

2. **Can the user implement/solve their request by reading this section alone?**
   - Example: Section has `per()`, `page()` methods with examples → Yes

3. **Does this section contain specific, actionable information?**
   - Example: Method signatures, code examples, configuration → Yes

**If ALL 3 are "Yes"** → Assign relevance = 2 (High)
**If ANY is "No"** → Continue to Partial questions

### Questions for Partial relevance (1 point)

Ask these questions. If ANY is "Yes", assign Partial (1):

1. **Does this section explain prerequisite concepts needed to understand the solution?**
   - Example: UniversalDao basics before using pagination → Yes

2. **Does this section explain related functionality that provides useful context?**
   - Example: General search methods when user asks about pagination → Maybe

3. **Would reading this section help the user understand the broader context?**
   - Example: Architecture explanation when implementing a feature → Yes

4. **Is this section useful AFTER implementing the primary solution?**
   - Example: Error handling after implementing pagination → Yes

**If ANY is "Yes"** → Assign relevance = 1 (Partial)
**If ALL are "No"** → Assign relevance = 0 (None)

### Questions for No relevance (0 points)

If neither High nor Partial criteria met:

1. **Does this section address a completely different topic?**
   - Example: Logging when user asks about pagination → Yes → None (0)

2. **Would reading this section confuse or distract from the user's goal?**
   - Example: Advanced features when user needs basics → Yes → None (0)

**If either is "Yes"** → Assign relevance = 0 (None)

**Important**: When in doubt between High and Partial, choose Partial. Be conservative with High.

**Your checklist** (for each section):
- [ ] Asked all High relevance questions
- [ ] Asked Partial relevance questions if needed
- [ ] Assigned relevance score (2, 1, or 0)
- [ ] Recorded brief judgement reasoning
- [ ] Based judgement ONLY on section content

---

## Step 4: Update pointers with accurate scores（改善版）

**Tool**: None (data structure creation)
**Input**: Relevance scores from Step 3
**Output**: Updated candidates list with scores

**Action you must take**:

Transform your candidates list by adding:
- `relevance` field: 2 (High), 1 (Partial), or 0 (None)
- `judgement` field: Brief reason (for transparency)

**Example transformation**:

**Before** (from keyword-search):
```json
{
  "file_path": "features/libraries/universal-dao.json",
  "section": "paging",
  "matched_hints": ["ページング", "per", "page"]
}
```

**After** (your judgement added):
```json
{
  "file_path": "features/libraries/universal-dao.json",
  "section": "paging",
  "matched_hints": ["ページング", "per", "page"],
  "relevance": 2,
  "judgement": "High - contains per() and page() methods with code examples"
}
```

**Your checklist**:
- [ ] Added relevance score to each candidate
- [ ] Added judgement reasoning to each candidate
- [ ] Based scores only on section content read in Step 2

---

## Step 5: Sort and filter results（改善版）

**Tool**: None (sorting/filtering process)
**Input**: Scored candidates from Step 4
**Output**: Final filtered list

**Action you must take**:

1. **Filter out None (0) relevance** sections:
   - Remove all sections with relevance = 0
   - Keep only High (2) and Partial (1) sections

2. **Sort by relevance** (descending):
   - All High (2) sections first
   - Then Partial (1) sections
   - Within same relevance, keep original order

3. **Apply limits**:
   - Target: 10-15 sections (~5,000 tokens)
   - If many High sections: can extend to 20 sections (~10,000 tokens)
   - If few High sections: include Partial sections to reach 10 total

4. **Count summary**:
   - high_count: Number of High relevance sections
   - partial_count: Number of Partial relevance sections
   - none_count: Number of None relevance sections (filtered out)

**Your checklist**:
- [ ] Filtered out all None (0) sections
- [ ] Sorted High (2) first, then Partial (1)
- [ ] Limited to 10-20 sections total
- [ ] Counted high, partial, none sections

---

## Step 6: Return final pointers（改善版）

**Tool**: None (final output)
**Input**: Sorted and filtered sections from Step 5
**Output**: Final results for answering user

**Action you must take**:

Return the final sections list with this structure:

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
      "matched_hints": ["UniversalDao"],
      "relevance": 1,
      "judgement": "Partial - DAO basics for context"
    }
  ],
  "summary": {
    "high_count": 1,
    "partial_count": 1,
    "none_count": 2,
    "total_tokens": "~1000"
  }
}
```

**Now answer the user's question**:
- Extract information from High and Partial sections
- Format as clear, structured answer
- **ONLY use information from the sections** (no external knowledge)
- Cite section sources (e.g., "universal-dao.json:334")

**Your checklist**:
- [ ] Final sections list prepared
- [ ] Summary counts calculated
- [ ] Ready to answer user using only this knowledge

---

## Error handling（改善版）

### If no High-relevance sections found:

**Action**:
1. Check if you have Partial-relevance sections
2. If yes: Return top 5-10 Partial sections
3. Add note: "関連する情報は限られています"
4. Answer using Partial sections, clearly state limitations

**Example response**:
```
直接的な回答は知識ファイルに含まれていませんが、関連する情報を提供します：

[Use Partial sections to provide context]

注：より具体的な情報が必要な場合は、異なる検索キーワードで再試行してください。
```

### If all sections are None relevance (no relevant knowledge):

**Action**:
1. State clearly: "この情報は知識ファイルに含まれていません"
2. Show related entries from index.toon
3. Show which entries are "not yet created"
4. **DO NOT answer from LLM training data**

**Example response**:
```
この情報は知識ファイルに含まれていません。

関連する可能性のあるトピック（index.toonより）:
- ログ出力 (not yet created)
- 入力値のチェック (not yet created)

現在利用可能な知識ファイル（17件）でカバーされていない領域です。
```

---

## Best practices（改善版）

### 1. Read content, not just metadata
**Bad**: Judging "paging" section as High because section ID contains "paging"
**Good**: Reading the section, confirming it has per(), page() methods and examples

### 2. Be conservative with High relevance
**Principle**: Only assign High if section DIRECTLY enables user to complete their goal
**When in doubt**: Choose Partial instead of High

### 3. Use knowledge files exclusively
**Critical**: Base ALL judgements on actual section content
**Never**: Supplement with external knowledge, LLM training data, or assumptions

### 4. Ask yourself the judgement questions
**Process**: Go through the 3 High questions first
**Then**: If not High, go through the 4 Partial questions
**Finally**: Assign None if neither applies

### 5. State clearly when knowledge is missing
**If None relevance**: Say "この情報は知識ファイルに含まれていません"
**Do not**: Try to answer from general knowledge

---

## Tools reference（新規セクション）

### Bash tool with jq
**Use for**: Extracting specific sections from knowledge files
**Command format**:
```bash
jq '.sections.SECTION_ID' knowledge/PATH/TO/FILE.json
```

**Examples**:
```bash
# Extract paging section
jq '.sections.paging' knowledge/features/libraries/universal-dao.json

# Extract overview section
jq '.sections.overview' knowledge/features/libraries/universal-dao.json

# Extract transaction section
jq '.sections.transaction' knowledge/features/handlers/common/transaction-management-handler.json
```

**Why jq**: Extracts only the needed section, not entire file (token efficiency)
