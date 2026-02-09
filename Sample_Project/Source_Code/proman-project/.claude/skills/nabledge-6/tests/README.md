# Nabledge-6 Test Scenarios

## Overview

This directory contains test scenarios for validating the nabledge-6 skill workflow execution.

## Files

| File | Format | Purpose |
|------|--------|---------|
| `scenarios.json` | JSON | Machine-readable test scenarios for automated evaluation |
| `scenarios.md` | Markdown | Human-readable test scenarios for manual testing |
| `README.md` | Markdown | This file - usage instructions |

## Test Scenarios

**Total**: 25 scenarios (5 per category)

**Categories**:
- **handlers**: Transaction management, DB connection, data reading
- **libraries**: UniversalDao, database access, file path, business date, data bind
- **tools**: NTF (test framework), assertions, test data
- **processing**: Nablarch batch architecture and implementation
- **adapters**: SLF4J logging adapter

## Scenario Structure

Each scenario contains:

```json
{
  "id": "unique-id",
  "category": "handlers|libraries|tools|processing|adapters",
  "file": "path/to/knowledge-file.json",
  "question": "User's question in Japanese",
  "expected_keywords": ["keyword1", "keyword2", ...],
  "expected_sections": ["section1", "section2"],
  "relevance": "high|partial"
}
```

## Evaluation Criteria

### 1. Workflow Execution ✓
- keyword-search workflow executed
- section-judgement workflow executed
- Appropriate tool calls (Read, Bash+jq)

### 2. Keyword Matching (≥80%)
- Expected keywords present in answer
- Related technical terms used correctly

### 3. Section Relevance ✓
- Correct sections identified
- High-relevance sections prioritized
- None-relevance sections filtered out

### 4. Knowledge File Only ✓
- No LLM training data used
- No external knowledge supplemented
- Only knowledge file content used

### 5. Token Efficiency (5,000-15,000 tokens)
- No unnecessary full-file reads
- Section-level extraction working

### 6. Tool Call Efficiency (10-20 calls)
- Read: index.toon (1 call)
- Bash+jq: .index extraction (5-10 calls)
- Bash+jq: .sections extraction (5-10 calls)

## Usage

### Manual Testing

Test individual scenarios by asking the question:

```
"データリードハンドラでファイルを読み込むにはどうすればいいですか？"
```

**Expected behavior**:
1. Message: "keyword-searchワークフローを実行します"
2. Read index.toon
3. Bash+jq for section extraction
4. Answer contains expected keywords
5. Answer uses only knowledge file content

### Automated Testing (Future)

Create an evaluation agent that:

1. Loads `scenarios.json`
2. For each scenario:
   - Executes the question
   - Monitors workflow execution
   - Validates keyword matching
   - Checks section relevance
   - Measures token usage
   - Counts tool calls
3. Generates evaluation report

**Evaluation agent prompt template**:

```
Execute the test scenario and evaluate the response:

Question: {question}
Expected Keywords: {expected_keywords}
Expected Sections: {expected_sections}

Evaluation checklist:
- [ ] Workflow execution (keyword-search + section-judgement)
- [ ] Keyword matching (≥80%)
- [ ] Section relevance (expected sections identified)
- [ ] Knowledge file only (no external knowledge)
- [ ] Token efficiency (5,000-15,000 tokens)
- [ ] Tool call efficiency (10-20 calls)

Score each criterion and provide overall pass/fail.
```

## Example Test Case

### handlers-001: データリードハンドラの使い方

**Question**:
```
データリードハンドラでファイルを読み込むにはどうすればいいですか？
```

**Expected Workflow**:
1. Extract keywords: ["データリードハンドラ", "ファイル", "読み込み", "DataReader"]
2. Read index.toon → match "data-read-handler.json"
3. Bash+jq '.index' data-read-handler.json → find relevant sections
4. Bash+jq '.sections.overview' → read content
5. Judge relevance: High (directly answers question)
6. Answer using only section content

**Expected Answer Content**:
- DataReadHandler explanation
- DataReader interface usage
- File reading configuration
- Code examples or method signatures

**Expected Keywords in Answer** (≥4/5):
- ✓ DataReadHandler
- ✓ DataReader
- ✓ ファイル読み込み
- ✓ データ入力
- ✓ レコード処理

## Success Metrics

A test scenario passes if:

- [ ] Workflow executed correctly (both keyword-search and section-judgement)
- [ ] ≥80% of expected keywords present in answer
- [ ] Relevant sections identified and used
- [ ] Answer uses only knowledge file content (no LLM training data)
- [ ] Token usage: 5,000-15,000 tokens
- [ ] Tool calls: 10-20 calls

## Known Limitations

### Not Yet Created Knowledge Files

Some expected sections may not exist in knowledge files yet. In these cases:

**Expected behavior**:
- Message: "この情報は知識ファイルに含まれていません"
- List related available knowledge
- Show "not yet created" entries from index.toon
- No answer from LLM training data

**Test scenarios affected**:
- Any scenario with expected_sections that don't exist in the actual knowledge files

### Section Name Variations

Expected section names are estimates based on typical knowledge file structure. Actual section IDs may differ.

**If section names don't match**:
- Workflow should still find related sections by keyword matching
- Evaluate based on content relevance, not exact section ID match

## Contributing

To add new test scenarios:

1. Choose a knowledge file from `knowledge/features/`
2. Read the file's `.index` field to see available sections
3. Create a question that maps to a specific section
4. List expected keywords from that section
5. Add to `scenarios.json` with unique ID
6. Update `scenarios.md` for human readability

**ID format**: `{category}-{number:03d}`
- Example: `handlers-006`, `libraries-006`

## Maintenance

### When Knowledge Files Change

If knowledge files are updated:

1. Review affected scenarios in `scenarios.json`
2. Update `expected_keywords` if section content changed
3. Update `expected_sections` if section IDs changed
4. Re-run tests to verify scenarios still pass

### When Workflow Changes

If keyword-search or section-judgement workflows change:

1. Update `evaluation_criteria` in `scenarios.json`
2. Update success metrics in this README
3. Re-run all scenarios to establish new baseline

## Version History

- **1.0.0** (2026-02-09): Initial test scenarios created
  - 25 scenarios across 5 categories
  - JSON and Markdown formats
  - Evaluation criteria defined
