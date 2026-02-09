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

## How Claude Code should execute this skill

**CRITICAL**: When this skill is invoked, Claude Code MUST execute the search workflow manually. The skill does NOT automatically search - Claude must follow the workflow steps using tools.

### Execution Process

When you (Claude Code) receive this skill prompt, follow these steps:

#### Step 1: Acknowledge workflow execution (0 tool calls)

Inform user briefly:
```
Nablarch知識ベースを検索します（keyword-searchワークフローを実行）
```

**DO NOT** ask for user confirmation - proceed immediately to Step 2.

#### Step 2: Execute keyword-search workflow (10-15 tool calls)

Follow `workflows/keyword-search.md` using these tools:

1. **Read knowledge/index.toon** using Read tool
2. **Extract keywords** from user request (3 levels: domain, component, functional)
3. **Match keywords** against index.toon hints (manual matching or Grep tool)
4. **Select top 10-15 files** with most matched hints
5. **Read .index field only** from each file using Bash with jq:
   ```bash
   jq '.index' knowledge/features/libraries/universal-dao.json
   ```
6. **Match keywords against section hints** to find candidate sections (20-30 sections)
7. **Build candidates list** with file paths, section IDs, and matched hints

#### Step 3: Execute section-judgement workflow (5-10 tool calls)

Follow `workflows/section-judgement.md`:

1. **Read each candidate section** using Bash with jq:
   ```bash
   jq '.sections.paging' knowledge/features/libraries/universal-dao.json
   ```
2. **Judge relevance** for each section based on actual content:
   - High (2): Directly answers user request
   - Partial (1): Provides supporting context
   - None (0): Not relevant (filter out)
3. **Sort by relevance** (High first, then Partial)
4. **Limit to top 10-15 sections** (~5,000 tokens)

#### Step 4: Answer using knowledge files only (0 tool calls)

1. Extract information from High and Partial relevance sections
2. Format as a clear, structured answer
3. **ONLY use information from knowledge files**
4. Cite file paths and section IDs (e.g., "universal-dao.json:paging section")
5. DO NOT supplement with LLM training data or general knowledge

#### Step 5: Handle missing knowledge (if needed)

If no relevant sections found:
1. State clearly: "この情報は知識ファイルに含まれていません"
2. List related available knowledge from index.toon
3. Show entries with "not yet created" status
4. DO NOT answer from LLM training data

### Tools to Use

- **Read**: Read knowledge/index.toon, workflow files
- **Grep** (optional): Search index.toon for keyword matches (can match manually)
- **Bash with jq**: Extract .index and .sections from JSON files
- **NO Task tool**: Execute workflows directly, do not delegate to agents

### Workflow Files

- `workflows/keyword-search.md`: Detailed steps for keyword-based search
- `workflows/section-judgement.md`: Detailed steps for relevance judgement

### Example Execution Flow

User request: "UniversalDaoでページングはどうしたらよい？"

1. Read knowledge/index.toon → 93 entries
2. Extract keywords: ["ページング", "paging", "UniversalDao", "DAO", "データベース", "per", "page"]
3. Match against index.toon → universal-dao.json matches 5 hints
4. Read `jq '.index' universal-dao.json` → find "paging" section
5. Read `jq '.sections.paging' universal-dao.json` → contains per(), page() methods
6. Judge: High relevance (directly answers question)
7. Answer using only paging section content

**Total**: ~10 tool calls, ~5,000 tokens

### Quick search

Search for knowledge by keywords or technical terms:

```
"Nablarchでページングを実装したい"
"UniversalDaoの使い方"
"トランザクション管理ハンドラのエラー対処"
```

When Claude Code executes this skill, it will:
1. Search the knowledge index by keywords (using keyword-search workflow)
2. Identify relevant sections by reading their content
3. Judge relevance based on actual section content (using section-judgement workflow)
4. Return the most relevant knowledge (top 10 sections, ~5,000 tokens)
5. **Answer using ONLY information from knowledge files**

**Note**: This is a manual process requiring 10-20 tool calls. Claude Code will execute the workflows step by step.

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
