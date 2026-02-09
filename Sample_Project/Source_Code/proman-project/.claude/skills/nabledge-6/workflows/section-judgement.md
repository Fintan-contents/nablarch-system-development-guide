# Section Judgement Workflow

This workflow judges the relevance of candidate sections by reading their actual content and comparing against the user's request.

## Table of Contents

- [Overview](#overview)
- [Why this workflow is critical](#why-this-workflow-is-critical)
- [Critical constraint: Knowledge files only](#critical-constraint-knowledge-files-only)
- [Judgement process](#judgement-process)
  - [Step 1: Understand the user request](#step-1-understand-the-user-request)
  - [Step 2: Read candidate sections](#step-2-read-candidate-sections)
  - [Step 3: Judge relevance level](#step-3-judge-relevance-level)
  - [Step 4: Update pointers with accurate scores](#step-4-update-pointers-with-accurate-scores)
  - [Step 5: Sort and filter results](#step-5-sort-and-filter-results)
  - [Step 6: Return final pointers](#step-6-return-final-pointers)
- [Error handling](#error-handling)
- [Token efficiency](#token-efficiency)
- [Best practices](#best-practices)
- [Example execution](#example-execution)
- [Integration with other workflows](#integration-with-other-workflows)

## Overview

**Who executes**: Claude Code (you)

**Input**:
- User's request (natural language)
- Candidates list from keyword-search workflow

**Input format**:
```json
{
  "candidates": [
    {
      "file_path": "features/libraries/universal-dao.json",
      "section": "paging",
      "matched_hints": ["ページング", "per", "page"]
    }
  ]
}
```

**Output**: Filtered sections with relevance scores

**Tools you will use**:
- Bash tool with jq: Extract specific sections from JSON files
- None (mental): Judge relevance based on content

**Expected tool calls**: 5-10 calls (read 5-10 section contents)

**Expected output**: 5-15 sections with High/Partial relevance

**Output format**:
```json
{
  "sections": [
    {
      "file_path": "features/libraries/universal-dao.json",
      "section": "paging",
      "matched_hints": ["ページング", "per", "page"],
      "relevance": 2,
      "judgement": "High - pagination API and examples"
    }
  ],
  "summary": {
    "high_count": 1,
    "partial_count": 0,
    "none_count": 0,
    "total_tokens": "~500"
  }
}
```

**Purpose**:
1. Read actual section content from knowledge files
2. Judge relevance: High (2, directly answers), Partial (1, supporting context), None (0, not relevant)
3. Filter out None-relevance sections to reduce the final list
4. Return only relevant knowledge

**Process summary**:
1. Read each candidate section's actual content
2. Ask yourself specific questions to judge relevance
3. Assign relevance score: High (2), Partial (1), or None (0)
4. Filter out None, keep High and Partial
5. Sort by relevance (High first)

## Why this workflow is critical

Keyword matching alone is insufficient for accurate relevance judgement. A section might:
- Match keywords but address a different use case
- Contain the answer without using exact keywords
- Provide essential context not captured by keywords

**This workflow reads section content to make informed relevance judgements.**

## Critical constraint: Knowledge files only

**IMPORTANT**: Follow the knowledge-files-only constraint defined in SKILL.md when judging relevance. Base all judgements on actual section content from knowledge files (knowledge/*.json).

## Judgement process

Copy this checklist and track your progress:

```
Section Judgement Progress:
- [ ] Step 1: Understand the user request
- [ ] Step 2: Read candidate sections
- [ ] Step 3: Judge relevance level
- [ ] Step 4: Update pointers with accurate scores
- [ ] Step 5: Sort and filter results
- [ ] Step 6: Return final pointers
```

### Step 1: Understand the user request

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

**Examples**:
- Request: "ページングを実装したい"
  - Primary goal: Implement pagination feature
  - Required info: How to use pagination API, code examples, configuration
  - Context: User is implementing a new feature

- Request: "UniversalDaoでSQLエラーが出た"
  - Primary goal: Resolve SQL error
  - Required info: Error types, causes, solutions
  - Context: User encountered an error and needs troubleshooting

**Your checklist**:
- [ ] Identified primary goal
- [ ] Listed required information to answer
- [ ] Listed useful background information
- [ ] Listed irrelevant topics to filter out

**Proceed to Step 2** with clear understanding of the request.

### Step 2: Read candidate sections

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
   jq '.sections.paging' knowledge/features/libraries/universal-dao.json
   ```
   (Use the full path from skill base directory)

2. **Read the section content carefully**:
   - What topic does this section explain?
   - What methods/APIs does it document?
   - What code examples does it provide?
   - What configuration does it require?
   - What errors or constraints does it mention?

3. **Base understanding ONLY on what is written** in the section:
   - Do not supplement with external knowledge
   - Do not assume information not present in the section
   - Only use what you can see in the section content

**CRITICAL**:
- Only read the specific section (`.sections.paging`), not the entire file
- This is crucial for token efficiency

**Efficiency note**:
- If you find 5+ High-relevance sections, you may skip remaining candidates
- Rationale: 5 sections × 500 tokens = 2,500 tokens provides sufficient knowledge

**Note**: Candidates have matched_hints but NO relevance score yet. You will assign relevance in Step 3.

**Your checklist** (for each section):
- [ ] Called Bash+jq to extract section
- [ ] Read section content carefully
- [ ] Understood what the section explains
- [ ] Based understanding only on section content
- [ ] Ready to judge relevance

**Proceed to Step 3** for each section after reading.

### Step 3: Judge relevance level

**Tool**: None (judgement process)

**Input**: Section content from Step 2 + user request understanding from Step 1

**Output**: Relevance score (High=2, Partial=1, None=0)

**Action you must take**:

For each section you read, ask yourself these **judgement questions**:

**IMPORTANT**: Judge based ONLY on the section's actual content. Do not use external knowledge or assumptions about Nablarch.

#### Questions for High relevance (2 points)

Ask all 3 questions. If ALL are "Yes", assign High (2):

1. **Does this section directly address the user's primary goal?**
   - Example: User wants pagination → Section explains pagination API → Yes

2. **Can the user implement/solve their request by reading this section alone?**
   - Example: Section has `per()`, `page()` methods with examples → Yes

3. **Does this section contain specific, actionable information?**
   - Example: Method signatures, code examples, configuration → Yes

**If ALL 3 are "Yes"** → Assign relevance = 2 (High)

**If ANY is "No"** → Continue to Partial questions

**Examples**:
- Request: "ページングを実装したい" → universal-dao.json / paging section
  - Q1: Yes (pagination API), Q2: Yes (complete examples), Q3: Yes (method details) → High (2)

#### Questions for Partial relevance (1 point)

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

**Examples**:
- Request: "ページングを実装したい" → database-access.json / query section
  - Q1: Maybe (query basics), Q2: Yes (underlying mechanism), Q3: No, Q4: No → Partial (1)

#### Questions for No relevance (0 points)

If neither High nor Partial criteria met:

1. **Does this section address a completely different topic?**
   - Example: Logging when user asks about pagination → Yes → None (0)

2. **Would reading this section confuse or distract from the user's goal?**
   - Example: Advanced features when user needs basics → Yes → None (0)

**If either is "Yes"** → Assign relevance = 0 (None)

**Examples**:
- Request: "ページングを実装したい" → slf4j-adapter.json / overview section
  - Q1: Yes (completely different topic) → None (0)

**Important**: When in doubt between High and Partial, choose Partial. Be conservative with High.

**Your checklist** (for each section):
- [ ] Asked all High relevance questions
- [ ] Asked Partial relevance questions if needed
- [ ] Assigned relevance score (2, 1, or 0)
- [ ] Recorded brief judgement reasoning
- [ ] Based judgement ONLY on section content

**Proceed to Step 4** after judging all sections.

### Step 4: Update pointers with accurate scores

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

**Proceed to Step 5** with scored candidates.

### Step 5: Sort and filter results

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

**Proceed to Step 6** with final filtered list.

### Step 6: Return final pointers

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
- Cite section sources (e.g., "universal-dao.json:paging section")

**Your checklist**:
- [ ] Final sections list prepared
- [ ] Summary counts calculated
- [ ] Ready to answer user using only this knowledge

## Error handling

**If no High-relevance sections found**:
1. Return the top 5-10 Partial-relevance sections
2. Add a note: "関連する情報は限られています。より具体的な依頼をすると、精度が向上します。"
3. List available knowledge from index.toon that might be related
4. Use only knowledge files (avoid suggesting external resources or general knowledge)

**If all sections are None relevance** (no relevant knowledge found):
1. Return empty results with clear explanation
2. State explicitly: "この情報は知識ファイルに含まれていません"
3. Show related entries from index.toon with their status (created/not yet created)
4. Provide official_doc_urls for manual lookup only if user explicitly asks
5. Answer using knowledge files only (avoid using LLM training data)

**Example response when knowledge is missing**:
```
この情報は知識ファイルに含まれていません。

関連する可能性のあるトピック（index.toonより）:
- ログ出力 (not yet created)
- 入力値のチェック (not yet created)
- トランザクション管理 (not yet created)

現在利用可能な知識ファイル（17件）でカバーされていない領域です。
```

## Token efficiency

**Target**: Keep total tokens around 5,000 for the final knowledge set

**Estimation**:
- 1 section ≈ 500 tokens (average)
- Top 10 sections ≈ 5,000 tokens
- Top 15 sections ≈ 7,500 tokens

**Adjustment**:
- If context window usage exceeds 80%, reduce section count
- If High-relevance sections are abundant (20+), increase limit to 15-20 sections

## Best practices

### Use knowledge files exclusively

**Most important**: Base all judgements and answers ONLY on knowledge file content. Never supplement with external knowledge, LLM training data, or assumptions.

### Be conservative with High relevance

Only assign High (2) if the section **directly enables** the user to complete their goal. When in doubt, use Partial (1).

### Read content, not just metadata

Don't judge based solely on section IDs or titles. Read the actual content to make informed decisions.

### State when knowledge is missing

If the section doesn't contain information to answer the request, clearly state "この情報は知識ファイルに含まれていません" rather than trying to answer from general knowledge.

### Consider user expertise level

If the request seems to come from a beginner, Partial-relevance sections (background/context) may be more valuable.

### Provide transparency

Include `judgement` field with brief reasoning based on section content. This helps users trust the results and aids debugging.

## Example execution

**User request**: "ページングを実装したい"

**Input candidates** (from keyword-search - NO relevance):
```json
{
  "candidates": [
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "paging", "matched_hints": ["ページング", "per", "page"] },
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "search", "matched_hints": ["検索"] },
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "overview", "matched_hints": ["UniversalDao"] },
    { "file_id": "F2", "file_path": "features/libraries/database-access.json", "section": "query", "matched_hints": ["SQL"] }
  ]
}
```

**Step 1 - Request analysis**:
- Primary goal: Implement pagination
- Required info: Pagination API usage, examples, configuration

**Step 2-3 - Read and judge sections**:

1. **F1/paging**: (read content)
   - Contains: `UniversalDao#page()`, `per()` methods, code examples, parameter explanations
   - **Judgement: High (2)** - directly explains pagination implementation

2. **F1/search**: (read content)
   - Contains: Search methods, but not pagination-specific
   - **Judgement: Partial (1)** - related functionality, useful context

3. **F1/overview**: (read content)
   - Contains: UniversalDao basics, general CRUD operations
   - **Judgement: Partial (1)** - background knowledge, useful for understanding

4. **F2/query**: (read content)
   - Contains: SQL query basics, connection management
   - **Judgement: Partial (1)** - underlying mechanism, useful background

**Step 4-5 - Update and sort**:

**Output** (adds relevance, filters out None):
```json
{
  "sections": [
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "paging", "matched_hints": ["ページング", "per", "page"], "relevance": 2, "judgement": "High - pagination API and examples" },
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "search", "matched_hints": ["検索"], "relevance": 1, "judgement": "Partial - related search functionality" },
    { "file_id": "F1", "file_path": "features/libraries/universal-dao.json", "section": "overview", "matched_hints": ["UniversalDao"], "relevance": 1, "judgement": "Partial - DAO basics" }
  ],
  "summary": {
    "high_count": 1,
    "partial_count": 2,
    "none_count": 1,
    "total_tokens": "~1500"
  }
}
```

**Note**: F2/query section was judged as None and filtered out, making the final list shorter (3 sections instead of 4).

**Step 6 - Return**: Provide the final pointers to the user or use them to extract and present the knowledge.

## Tools reference

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

**Important**: Use the full path from skill base directory when calling Bash tool.

## Integration with other workflows

**Typical flow**:
1. User submits request
2. **keyword-search** or **intent-search** workflow generates candidates
3. **section-judgement** workflow (this one) judges relevance and filters
4. Final results (with relevance) are returned to the user

**Parallel execution** (optional):
- Run keyword-search and intent-search in parallel
- Merge candidates before calling section-judgement
- This reduces duplicates and improves coverage

**Reusability**:
- This workflow is called by both keyword-search and intent-search
- Input format is standardized (candidates list)
- Output format is consistent (sections with relevance)
