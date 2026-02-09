---
name: nabledge-6
description: Provides structured knowledge about Nablarch 6 framework (batch processing, RESTful web services, handlers, libraries, tools). Use when developing Nablarch applications, implementing features, reviewing code, investigating errors, or answering questions about Nablarch 6 APIs and patterns.
---

# Nabledge-6: Nablarch 6 Knowledge Base

Structured knowledge base for Nablarch 6 framework, covering batch processing and RESTful web services.

## What this skill provides

**Coverage**: Batch, REST, handlers, libraries (UniversalDao, DB access, validation, file I/O, business date), testing (NTF), adapters, security

**Use cases**: Code understanding, feature implementation, code review, error investigation, test data, documentation

## How to use

### Important constraint: Knowledge files only

**CRITICAL**: Always answer using ONLY the information in knowledge files (knowledge/*.json).

- **DO NOT use** LLM training data or general knowledge about Nablarch
- **DO NOT access** official websites or external resources during answers
- **DO NOT guess** or infer information not present in knowledge files
- **If knowledge is missing**: Clearly state "この情報は知識ファイルに含まれていません" and list related available knowledge

**Why this constraint**: Knowledge files contain verified, accurate information extracted from official documentation. LLM training data may be outdated or incomplete. Official sites are difficult to navigate.

### Quick search

Search for knowledge by keywords or technical terms:

```
"Nablarchでページングを実装したい"
"UniversalDaoの使い方"
"トランザクション管理ハンドラのエラー対処"
```

The skill automatically:
1. Searches the knowledge index by keywords
2. Identifies relevant sections
3. Judges relevance to your request
4. Returns the most relevant knowledge (top 10 sections, ~5,000 tokens)
5. **Answers using ONLY information from knowledge files**

### Knowledge structure

**Knowledge files** (JSON format):
- `knowledge/features/`: Handlers, libraries, processing methods, tools, adapters
- `knowledge/checks/`: Security checklist, public API list, deprecated features
- `knowledge/releases/`: Release notes

**Index** (TOON format):
- `knowledge/index.toon`: 93 entries with ~650 search hints

**Human-readable** (auto-generated):
- `docs/`: Markdown version of knowledge files for human verification

## Search workflows

The skill uses two parallel search approaches:

1. **keyword-search**: Technical axis (index.toon hints matching)
2. **intent-search**: Purpose axis (category → file filtering)

See [workflows/keyword-search.md](workflows/keyword-search.md) for the search process.

After finding candidates, [workflows/section-judgement.md](workflows/section-judgement.md) judges the relevance of each section to your request.

## Knowledge file structure

Each knowledge file (JSON):
- `official_doc_urls`: Source URLs for verification
- `index`: Section-level search hints
- `sections`: Structured knowledge by topic

See `knowledge/features/libraries/universal-dao.json` for an example.

## Advanced usage

### Manual search

Search the index manually:

```bash
grep -i "ページング" knowledge/index.toon
```

### Read specific knowledge

Extract only the section you need (avoids reading entire file):

```bash
jq '.sections.paging' knowledge/features/libraries/universal-dao.json
```

### Browse human-readable version

Open markdown files in `docs/` directory for easier reading:

```bash
ls docs/features/libraries/
cat docs/features/libraries/universal-dao.md
```

## Token efficiency

**Index**: ~5,000-7,000 tokens (TOON format, 40-50% reduction vs JSON)

**Search results**: ~5,000 tokens (top 10 sections, 500 tokens each)

**Total**: ~10,000-12,000 tokens (5-6% of 200k context window)

## Version information

**Target version**: Nablarch 6u2 / 6u3

**Out of scope**:
- Jakarta Batch
- Resident batch (table queue)
- Web applications (JSP/UI)
- Messaging (MOM)

## Quality assurance

**Knowledge accuracy**: Average 97.3/100 points (verified against official documentation)

**Coverage**: 17 files created, 43 files planned (total 60 files)

**Source**: Official documentation (https://nablarch.github.io/docs/), Fintan system development guide

## Limitations

### Knowledge coverage

**Not yet created** knowledge files show "not yet created" in index.toon (76 out of 93 entries).

**When knowledge is missing**:
1. Clearly state: "この情報は知識ファイルに含まれていません"
2. List related available knowledge that might help
3. Show the entry from index.toon with "not yet created" status
4. **DO NOT** attempt to answer from LLM training data or general knowledge
5. **ONLY IF EXPLICITLY REQUESTED**: Provide official_doc_urls for manual reference

**Current coverage** (17 files):
- Nablarch batch processing basics
- Core handlers (DB connection, transaction, data read)
- Core libraries (UniversalDao, database access, file path, business date, data bind)
- Testing framework (NTF) basics
- SLF4J adapter
- Security checklist
- Release notes (6u3)

### Verification

**Human verification**: Check `docs/` directory for human-readable versions. All knowledge includes `official_doc_urls` showing the source.

**Accuracy**: Average 97.3/100 points (verified against official RST documentation)

## Feedback

If knowledge is inaccurate or missing, please:
1. Check `official_doc_urls` in the knowledge file for the source
2. Verify against official documentation
3. Report discrepancies to the knowledge maintainer

## References (for manual lookup only)

**IMPORTANT**: These references are for human users to manually look up information NOT in knowledge files. Do not access or fetch these during answers.

- [Nablarch Official Documentation](https://nablarch.github.io/docs/LATEST/doc/) - Use when knowledge file says "not yet created"
- [Fintan System Development Guide](https://fintan.jp/page/252/) - Patterns and anti-patterns
- [Nablarch Example Batch](https://github.com/nablarch/nablarch-example-batch) - Code examples
- [Nablarch Example REST](https://github.com/nablarch/nablarch-example-rest) - REST examples
