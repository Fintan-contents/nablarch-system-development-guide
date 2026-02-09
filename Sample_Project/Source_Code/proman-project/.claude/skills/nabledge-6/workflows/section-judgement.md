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

**Input**:
- User's request (natural language)
- Candidates list from keyword-search or intent-search workflow

**Input format**:
```json
{
  "candidates": [
    {
      "file_id": "F1",
      "file_path": "features/libraries/universal-dao.json",
      "section": "paging",
      "matched_hints": ["ページング", "per", "page"]
    }
  ]
}
```

**Output**: Sections with relevance scores and filtered results

**Output format**:
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
    }
  ],
  "summary": {
    "high_count": 1,
    "partial_count": 0,
    "total_tokens": "~500"
  }
}
```

**Purpose**:
1. Read actual section content from knowledge files
2. Judge relevance: High (directly answers), Partial (supporting context), None (not relevant)
3. Filter out None-relevance sections to reduce the final list
4. Return only relevant knowledge

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

Analyze the request to identify:
- **Primary goal**: What does the user want to achieve?
- **Required information**: What knowledge is needed to answer?
- **Context**: What background knowledge is assumed?

**Examples**:
- Request: "ページングを実装したい"
  - Primary goal: Implement pagination feature
  - Required info: How to use pagination API, code examples, configuration
  - Context: User is implementing a new feature

- Request: "UniversalDaoでSQLエラーが出た"
  - Primary goal: Resolve SQL error
  - Required info: Error types, causes, solutions
  - Context: User encountered an error and needs troubleshooting

### Step 2: Read candidate sections

For each candidate in the candidates list:

**Note**: Candidates have matched_hints but NO relevance score yet. You will assign relevance in Step 3.

1. Extract only the target section using jq (avoids reading entire file):
   ```bash
   jq '.sections.paging' knowledge/features/libraries/universal-dao.json
   ```

2. Read the section content carefully:
   - What does this section explain?
   - What code examples or configurations does it provide?
   - What use cases does it cover?
   - What errors or constraints does it mention?

**CRITICAL**:
- Base your understanding ONLY on what is written in the section. Do not supplement with external knowledge or assumptions.
- Only read the specific section (`.sections.paging`), not the entire file. This is crucial for token efficiency.

**Efficiency tip**: Process candidates in the order provided. If you've found 5+ High-relevance sections (rationale: 5 sections × 500 tokens = 2,500 tokens provides sufficient knowledge for most requests), you may skip reading remaining candidates.

### Step 3: Judge relevance level

For each section, determine its relevance using these criteria.

**IMPORTANT**: Judge based ONLY on the section's actual content. Do not use external knowledge or assumptions about Nablarch.

#### High relevance (2 points)

The section **directly answers** the user's request.

**Criteria** (all must be true):
- The section's content addresses the primary goal
- Reading this section enables the user to implement/solve the request
- The section contains specific information (API usage, code examples, configuration, error solutions)

**Examples**:
- Request: "ページングを実装したい" → universal-dao.json / paging section (contains pagination API usage and examples)
- Request: "トランザクションのロールバック方法" → transaction-management-handler.json / rollback section (explains rollback mechanism)

#### Partial relevance (1 point)

The section **provides supporting context** but doesn't directly answer the request.

**Criteria** (at least one must be true):
- The section explains prerequisite concepts
- The section provides related functionality
- The section helps understand the broader context
- The section is useful after implementing the primary solution

**Examples**:
- Request: "ページングを実装したい" → database-access.json / query section (explains underlying query mechanism, useful background)
- Request: "UniversalDaoの使い方" → nablarch-batch.json / responsibility section (explains where to use UniversalDao in architecture)

#### No relevance (0 points)

The section **does not relate** to the user's request.

**Criteria**:
- The section addresses a completely different topic
- Reading this section does not help with the request

**Examples**:
- Request: "ページングを実装したい" → slf4j-adapter.json / overview section (logging has no relation to pagination)
- Request: "UniversalDaoの使い方" → file-path-management.json / setup section (file paths are unrelated to DAO)

### Step 4: Update pointers with accurate scores

Add relevance scores based on actual content review.

**Judgement adds**:
- `relevance` field (2=High, 1=Partial, 0=None)
- `judgement` field (reasoning for transparency)
- Filters out relevance=0 sections
- `summary` with counts

**Example**: 3 input candidates → 1 High (paging), 1 Partial (overview), 1 None (query, filtered) → 2 final sections

### Step 5: Sort and filter results

1. **Sort sections** by relevance (descending):
   - All High (2) sections first
   - Then Partial (1) sections
   - Then None (0) sections (usually filtered out)

2. **Filter out None (0) relevance** sections:
   - Remove sections with relevance = 0
   - Keep only High and Partial sections

3. **Apply limits**:
   - Return top 10-15 sections (rationale: 10 sections × 500 tokens = 5,000 tokens, 2.5% of 200k context)
   - Extend to 20 sections if High-relevance abundant (rationale: 20 × 500 = 10k tokens, still <5% of context)
   - Include Partial sections up to 10 total if fewer than 5 High-relevance sections (rationale: maintain minimum knowledge coverage)

4. **Update file relevance**:
   - Recalculate each file's relevance as the **maximum relevance** of its sections
   - Example: If file F1 has sections with relevance [2, 2, 1, 0], then file relevance = 2

### Step 6: Return final pointers

Return the updated `pointers` object with accurate relevance scores and sorted sections.

**Final structure**:
```json
{
  "files": [
    {
      "id": "F1",
      "path": "features/libraries/universal-dao.json",
      "relevance": 2,
      "matched_hints": ["ページング", "検索"],
      "section_count": 3
    }
  ],
  "sections": [
    {
      "file_id": "F1",
      "section": "paging",
      "relevance": 2,
      "matched_hints": ["ページング", "per", "page"],
      "judgement": "High - contains pagination API, code examples, and configuration"
    },
    {
      "file_id": "F1",
      "section": "search",
      "relevance": 2,
      "matched_hints": ["検索"],
      "judgement": "High - explains search methods with examples"
    },
    {
      "file_id": "F1",
      "section": "overview",
      "relevance": 1,
      "matched_hints": ["UniversalDao"],
      "judgement": "Partial - provides DAO basics, useful background"
    }
  ],
  "summary": {
    "high_count": 2,
    "partial_count": 1,
    "none_count": 0,
    "total_tokens": "~5000"
  }
}
```

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
