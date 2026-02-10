# Section Judgement Workflow

This workflow judges the relevance of candidate sections by reading their actual content and comparing against the user's request.

## Table of Contents

- [Overview](#overview)
- [Why this workflow is critical](#why-this-workflow-is-critical)
- [Judgement process](#judgement-process)
  - [Step 1: Read candidate sections and judge relevance](#step-1-read-candidate-sections-and-judge-relevance)
  - [Step 2: Sort, filter, and return results](#step-2-sort-filter-and-return-results)
- [Error handling](#error-handling)
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

Keyword matching alone is insufficient for accurate relevance judgement. This workflow reads actual section content to make informed judgements based ONLY on knowledge files (knowledge/*.json).

## Judgement process

### Step 1: Read candidate sections and judge relevance

**Tools**: Bash with jq

**Action**: For each candidate (start with first 5-10):

1. Extract the specific section using jq:
   ```bash
   jq '.sections.paging' knowledge/features/libraries/universal-dao.json
   ```

2. Read the section content carefully and understand what it explains

3. Judge relevance based ONLY on section content (no external knowledge)

**Relevance criteria** (judge based ONLY on section content):

**High (2)**: Section directly addresses user's goal AND contains actionable information (methods, examples, configuration)
- Example: User wants pagination → Section has per(), page() methods with examples → High

**Partial (1)**: Section provides prerequisite knowledge, related functionality, or useful context
- Example: User wants pagination → Section explains UniversalDao basics → Partial

**None (0)**: Section addresses different topic or would confuse/distract from goal
- Example: User wants pagination → Section about logging → None

**Tip**: When in doubt between High and Partial, choose Partial. Be conservative with High.

**Efficiency**: Stop after finding 5+ High-relevance sections (sufficient knowledge).

### Step 2: Sort, filter, and return results

**Action**:

1. Add relevance score and judgement to each candidate
2. Filter out None (0) relevance sections
3. Sort by relevance: High (2) first, then Partial (1)
4. Limit to 10-15 sections (or 20 if many High sections)
5. Return final results:

```json
{
  "sections": [
    {
      "file_path": "features/libraries/universal-dao.json",
      "section": "paging",
      "relevance": 2,
      "judgement": "High - pagination API and examples"
    }
  ],
  "summary": {
    "high_count": 1,
    "partial_count": 0,
    "none_count": 2
  }
}
```

**Answer the user**: Extract information from returned sections ONLY (no external knowledge). Cite sources.

## Error handling

**No High-relevance found**: Return top 5-10 Partial sections with note "関連する情報は限られています".

**All None relevance**: State "この情報は知識ファイルに含まれていません", show related entries from index.toon. DO NOT use LLM training data.

## Best practices

- **Knowledge files only**: Base all judgements and answers ONLY on knowledge file content
- **Conservative with High**: Only assign High (2) if section directly enables user's goal
- **Read content**: Don't judge based on section IDs/titles alone
- **State when missing**: Use "この情報は知識ファイルに含まれていません" when knowledge is absent
- **Transparency**: Include judgement field with brief reasoning

## Example execution

**Request**: "ページングを実装したい"

**Input**: 4 candidates (universal-dao/paging, universal-dao/search, universal-dao/overview, database-access/query)

**Step 1**: Read each section and judge:
- universal-dao/paging: High (2) - has per(), page() methods with examples
- universal-dao/search: None (0) - not pagination-specific
- universal-dao/overview: Partial (1) - DAO basics for context
- database-access/query: None (0) - underlying mechanism, not needed

**Step 2**: Filter None, sort by relevance, return 2 sections (High + Partial) with judgement reasons

## Integration with other workflows

This workflow is called by keyword-search and intent-search workflows to judge relevance and filter candidates. Input: candidates list. Output: scored sections with High/Partial relevance.
