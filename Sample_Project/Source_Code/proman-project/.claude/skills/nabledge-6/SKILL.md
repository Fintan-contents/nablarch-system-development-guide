---
name: nabledge-6
description: Provides structured knowledge about Nablarch 6 framework (batch processing, RESTful web services, handlers, libraries, tools) and code analysis capabilities. Use when developing Nablarch applications, implementing features, reviewing code, investigating errors, answering questions about Nablarch 6 APIs and patterns, or analyzing existing code to understand its structure and dependencies.
---

# Nabledge-6: Nablarch 6 Knowledge Base

Structured knowledge base for Nablarch 6 framework, covering batch processing and RESTful web services.

## What this skill provides

**Knowledge Coverage**: Batch, REST, handlers, libraries (UniversalDao, DB access, validation, file I/O, business date), testing (NTF), adapters, security

**Code Analysis**: Dependency tracing, component decomposition, architecture visualization, documentation generation

**Use cases**:
- **Knowledge search**: Learn Nablarch APIs, patterns, configurations, troubleshooting
- **Code analysis**: Understand existing code structure, dependencies, and design
- **Development support**: Feature implementation, code review, error investigation

## How to use

### Basic usage

**Interactive mode** (recommended for first-time users):
```
nabledge-6
```
Shows friendly greeting and lets you choose between knowledge search and code analysis.

**Direct knowledge search**:
```
nabledge-6 "Your question about Nablarch"
```

**Direct code analysis**:
```
nabledge-6 code-analysis
```

See [Quick usage](#quick-usage) section for examples.

### Important constraint: Knowledge files only (for knowledge search)

**CRITICAL**: Always answer using ONLY the information in knowledge files (knowledge/*.json).

- **DO NOT use** LLM training data or general knowledge about Nablarch
- **DO NOT access** official websites or external resources during answers
- **DO NOT guess** or infer information not present in knowledge files
- **If knowledge is missing**: Clearly state "この情報は知識ファイルに含まれていません" and list related available knowledge

**Why this constraint**: Knowledge files contain verified, accurate information extracted from official documentation. LLM training data may be outdated or incomplete. Official sites are difficult to navigate.

## Error Handling Policy

**General principle**:
- Always inform user clearly when something goes wrong
- Provide actionable next steps
- Never fail silently

**Knowledge not found** (Knowledge Search workflow):
- Message: "この情報は知識ファイルに含まれていません"
- List related available knowledge from index.toon
- Show "not yet created" entries if applicable
- DO NOT use LLM training data or general knowledge to answer

**Target code not found** (Code Analysis workflow):
- Message: "指定されたコードが見つかりませんでした"
- Show search patterns used (e.g., `**/*LoginAction.java`)
- Ask for clarification:
  - More specific file name
  - Confirm module (web/batch/common)
  - Provide full file path

**Workflow execution failure**:
- Inform user which step failed (e.g., "Step 2: 依存関係分析中にエラーが発生しました")
- Show error details if available
- Suggest retry or alternative approach

**Output file already exists** (Code Analysis workflow):
- Ask user using AskUserQuestion:
  - Option 1: "上書きする" → Overwrite
  - Option 2: "別名で保存" → Append timestamp (e.g., `code-analysis-login-action-2.md`)
  - Option 3: "キャンセル" → Stop workflow

**Dependency analysis too complex** (Code Analysis workflow):
- Ask user to narrow scope:
  - "依存関係が複雑です。特定のコンポーネントに絞りますか？"
  - Suggest limiting to direct dependencies only
- Provide partial analysis with note: "以下は主要な依存関係のみを示しています"

## How Claude Code should execute this skill

**CRITICAL**: When this skill is invoked, Claude Code MUST execute workflows manually. The skill does NOT automatically process - Claude must follow the workflow steps using tools.

### Execution Process

When you (Claude Code) receive this skill prompt, follow these steps:

#### Step 0: Check arguments and determine workflow

**Input**: Skill invocation with optional arguments

**Action**: Determine which workflow to execute based on arguments

**Decision tree**:

1. **No arguments** (`nabledge-6`):
   - Proceed to Step 1: User-friendly greeting and choice

2. **Argument: "code-analysis"** (`nabledge-6 code-analysis`):
   - Skip to Code Analysis Workflow (Step 3)

3. **Other text arguments** (`nabledge-6 <question>`):
   - Treat argument as user question
   - Skip to Knowledge Search Workflow (Step 2)

#### Step 1: User-friendly greeting (no arguments case only)

**Tool**: AskUserQuestion

**Action**: Show friendly greeting and ask what user wants to do

**Message to show in question field**:
```
Nablarch 6のことなら何でもお答えします。

以下のようなことが可能です:
- Nablarchの機能や使い方について質問する
  例: UniversalDaoの使い方、バッチ処理の実装方法、トランザクション管理
- 既存コードの構造を理解するためのドキュメントを生成する
  例: LoginActionの構造、プロジェクト全体のアーキテクチャ

何をお手伝いしましょうか？
```

**Tool call structure**:

Use AskUserQuestion with:
```json
{
  "questions": [
    {
      "question": "Nablarch 6のことなら何でもお答えします。\n\n以下のようなことが可能です:\n- Nablarchの機能や使い方について質問する\n  例: UniversalDaoの使い方、バッチ処理の実装方法、トランザクション管理\n- 既存コードの構造を理解するためのドキュメントを生成する\n  例: LoginActionの構造、プロジェクト全体のアーキテクチャ\n\n何をお手伝いしましょうか？",
      "header": "選択",
      "multiSelect": false,
      "options": [
        {
          "label": "Nablarchの機能や使い方を知りたい",
          "description": "UniversalDao、バッチ処理、ハンドラ等の使い方を検索します"
        },
        {
          "label": "既存コードの構造を理解したい",
          "description": "コードの依存関係を分析してドキュメントを生成します"
        }
      ]
    }
  ]
}
```

**After user choice**:
- Option 1 selected ("Nablarchの機能や使い方を知りたい") → Proceed to Step 2 (Knowledge Search)
- Option 2 selected ("既存コードの構造を理解したい") → Proceed to Step 3 (Code Analysis)
- Other (free text input) → Treat as knowledge search question, proceed to Step 2 with user's text

---

#### Step 2: Knowledge Search Workflow

Execute when user wants to search Nablarch knowledge.

**Inform user**:
```
Nablarch知識ベースを検索します
```

**Execute workflows**:

1. **keyword-search workflow** (10-15 tool calls)
   - Follow `workflows/keyword-search.md`
   - Read knowledge/index.toon
   - Extract keywords (3 levels: domain, component, functional)
   - Match against index.toon hints
   - Select top 10-15 files
   - Read .index field from each file using `jq '.index' <file>`
   - Match keywords against section hints
   - Build candidates list (20-30 sections)

2. **section-judgement workflow** (5-10 tool calls)
   - Follow `workflows/section-judgement.md`
   - Read each candidate section using `jq '.sections.<section>' <file>`
   - Judge relevance: High (2), Partial (1), None (0)
   - Filter out None relevance
   - Sort by relevance (High first)
   - Limit to top 10-15 sections (~5,000 tokens)

3. **Answer using knowledge files only**
   - Extract information from High and Partial sections
   - Format as clear, structured answer
   - **ONLY use information from knowledge files**
   - Cite sources (e.g., "universal-dao.json:paging section")
   - DO NOT supplement with LLM training data or general knowledge

4. **Handle missing knowledge** (if needed)
   - State clearly: "この情報は知識ファイルに含まれていません"
   - List related available knowledge from index.toon
   - Show "not yet created" entries
   - DO NOT answer from LLM training data

**Tools**:
- Read: knowledge/index.toon, workflow files
- Grep (optional): Search index.toon
- Bash with jq: Extract .index and .sections from JSON files
- NO Task tool: Execute workflows directly

**Example**:
```
User: "UniversalDaoでページングはどうしたらよい？"

1. Read index.toon → 93 entries
2. Keywords: ["ページング", "paging", "UniversalDao", "DAO", "per", "page"]
3. Match → universal-dao.json (5 hints)
4. Read index → find "paging" section
5. Read section → contains per(), page() methods
6. Judge: High relevance
7. Answer using section content

Total: ~10 tool calls, ~5,000 tokens
```

---

#### Step 3: Code Analysis Workflow

Execute when user wants to analyze existing code.

**Entry conditions**:
1. User selected Option 2 ("既存コードの構造を理解したい") from Step 1
2. Skill invoked with `nabledge-6 code-analysis`

**Inform user**:
```
既存コードを分析してドキュメントを生成します
```

**If target code not specified yet**:

Use AskUserQuestion to ask:
```json
{
  "questions": [
    {
      "question": "どのコードを分析しますか？\n\n以下のような指定が可能です:\n- 特定のクラス（例: LoginAction）\n- 機能全体（例: ログイン機能）\n- パッケージ（例: web.action配下全て）\n- モジュール（例: proman-batch全体）",
      "header": "対象指定",
      "multiSelect": false,
      "options": [
        {
          "label": "特定のクラスを指定",
          "description": "クラス名を入力してください（例: LoginAction）"
        },
        {
          "label": "機能全体を指定",
          "description": "機能名を入力してください（例: ログイン機能）"
        },
        {
          "label": "パッケージを指定",
          "description": "パッケージパスを入力してください（例: web.action）"
        },
        {
          "label": "モジュール全体を指定",
          "description": "モジュール名を入力してください（例: proman-batch）"
        }
      ]
    }
  ]
}
```

Then proceed to workflow with user's specification.

**Execute workflow**:

Follow `workflows/code-analysis.md` (30-50 tool calls):

0. **Record start time**
   - Note current timestamp for duration calculation

1. **Identify target code**
   - Parse user request (class/feature/package)
   - Use Glob/Grep to find files
   - Ask clarifying questions if needed

2. **Analyze dependencies**
   - Read target files
   - Extract dependencies from imports and usage
   - Classify: project code / Nablarch / libraries
   - Build dependency graph

3. **Decompose components**
   - Categorize by role (Action/Form/Entity/Handler)
   - Identify Nablarch framework components
   - Extract key concepts

4. **Search Nablarch knowledge**
   - For each Nablarch component, execute keyword-search workflow
   - Collect relevant knowledge sections

5. **Generate documentation**
   - Build Mermaid class diagram (class names only, show relationships)
   - Build Mermaid sequence diagram (processing flow with timeline)
   - Write overview, flow (with sequence diagram), components sections
   - Include Nablarch knowledge excerpts
   - Add relative file path links

6. **Output file**
   - Calculate analysis duration from Step 0 timestamp
   - Propose path: `work/YYYYMMDD/code-analysis-<target>.md`
   - Apply template from `assets/code-analysis-template.md`
   - Fill placeholders: generation_date, generation_time, analysis_duration
   - Write file
   - Inform user of completion

**Tools**:
- Read: Source files, knowledge files
- Glob: Find files by pattern
- Grep: Search for class usages
- Bash with jq: Search Nablarch knowledge
- Write: Generate documentation file

**Example**:
```
User: "LoginActionを理解したい"

0. Record start time: 2026-02-10 14:28:30
1. Find: LoginAction.java
2. Analyze: LoginForm, SystemAccountEntity, UniversalDao dependencies
3. Decompose: Action, Form, Entity, Nablarch components
4. Search: UniversalDao, Bean Validation knowledge
5. Generate: Class diagram, sequence diagram, component details, flow description
6. Output: work/20260210/code-analysis-login-action.md (duration: 約2分)

Total: ~40 tool calls, 1 documentation file
```

---

### Workflow Files

- `workflows/keyword-search.md`: Keyword-based knowledge search
- `workflows/section-judgement.md`: Relevance judgement for knowledge sections
- `workflows/code-analysis.md`: Existing code analysis and documentation generation

### Template Files

- `assets/code-analysis-template.md`: Documentation template for code analysis output
- `assets/code-analysis-template-guide.md`: Template usage guide (sections, placeholders, evaluation criteria)

### Quick usage

**Interactive mode** (recommended for first-time users):

```
nabledge-6
```

Claude will show a friendly greeting and ask what you want to do:
- Choose "Nablarchの機能や使い方を知りたい" for knowledge search
- Choose "既存コードの構造を理解したい" for code analysis

This mode is best when you're not sure which workflow to use.

---

**Direct knowledge search** (for quick queries about Nablarch APIs, patterns, configurations):

```
nabledge-6 "Nablarchでページングを実装したい"
nabledge-6 "UniversalDaoの使い方"
nabledge-6 "トランザクション管理ハンドラのエラー対処"
```

When Claude Code executes knowledge search:
1. Search the knowledge index by keywords (using keyword-search workflow)
2. Identify relevant sections by reading their content
3. Judge relevance based on actual section content (using section-judgement workflow)
4. Return the most relevant knowledge (top 10 sections, ~5,000 tokens)
5. **Answer using ONLY information from knowledge files**

**Note**: 10-20 tool calls, manual workflow execution.

---

**Direct code analysis** (when you know you need code analysis):

```
nabledge-6 code-analysis
```

Then specify target code when asked (or provide directly):
- "LoginActionを理解したい"
- "proman-batchモジュール全体の構造を教えて"
- "Formクラスの設計パターンを知りたい"

When Claude Code executes code analysis:
1. Identify target code (files/classes)
2. Analyze dependencies and trace relationships
3. Decompose into components (Action/Form/Entity/Handler)
4. Search relevant Nablarch knowledge for framework components
5. Generate structured documentation with Mermaid diagrams
6. Output to `work/YYYYMMDD/code-analysis-<target>.md`

**Note**: 30-50 tool calls, generates 1 documentation file.

### Knowledge structure

**Knowledge files** (JSON format):
- `knowledge/features/`: Handlers, libraries, processing methods, tools, adapters
- `knowledge/checks/`: Security checklist, public API list, deprecated features
- `knowledge/releases/`: Release notes

**Index** (TOON format):
- `knowledge/index.toon`: 93 entries with ~650 search hints

**Human-readable** (auto-generated):
- `docs/`: Markdown version of knowledge files for human verification

## Workflows

The skill provides workflows for different use cases:

### Knowledge Search Workflows

1. **keyword-search**: Technical axis (index.toon hints matching)
2. **intent-search**: Purpose axis (category → file filtering)

See [workflows/keyword-search.md](workflows/keyword-search.md) for the search process.

After finding candidates, [workflows/section-judgement.md](workflows/section-judgement.md) judges the relevance of each section to your request.

### Code Analysis Workflow

3. **code-analysis**: Analyze existing code and generate structured documentation

See [workflows/code-analysis.md](workflows/code-analysis.md) for the process.

This workflow helps understand existing code by:
- Tracing dependencies from target code
- Decomposing into components
- Searching relevant Nablarch knowledge (using keyword-search workflow)
- Generating comprehensive documentation with Mermaid diagrams
- Providing links to source files and knowledge files

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
