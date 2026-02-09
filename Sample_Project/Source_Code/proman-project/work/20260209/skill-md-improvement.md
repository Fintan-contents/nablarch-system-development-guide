# SKILL.md 改善案

## 追加するセクション（How to useの直後に配置）

```markdown
## How Claude Code should execute this skill

**CRITICAL**: When this skill is invoked, Claude Code MUST execute the search workflow manually. The skill does NOT automatically search - Claude must follow the workflow steps using tools.

### Execution Process

When you (Claude Code) receive this skill prompt, follow these steps:

#### Step 1: Acknowledge workflow execution (0 tool calls)
- Inform user: "Nablarch知識ベースを検索します（keyword-searchワークフローを実行）"
- DO NOT ask for user confirmation - proceed immediately to Step 2

#### Step 2: Execute keyword-search workflow (10-15 tool calls)
Follow `workflows/keyword-search.md` using these tools:

1. **Read knowledge/index.toon** using Read tool
2. **Extract keywords** from user request (3 levels: domain, component, functional)
3. **Match keywords** against index.toon hints using Grep tool or manual matching
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
4. Cite file paths and section IDs (e.g., "universal-dao.json:334")
5. DO NOT supplement with LLM training data or general knowledge

#### Step 5: Handle missing knowledge (if needed)
If no relevant sections found:
1. State clearly: "この情報は知識ファイルに含まれていません"
2. List related available knowledge from index.toon
3. Show entries with "not yet created" status
4. DO NOT answer from LLM training data

### Tools to Use

- **Read**: Read knowledge/index.toon, workflow files
- **Grep**: Search index.toon for keyword matches (optional, can match manually)
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
4. Read jq '.index' universal-dao.json → find "paging" section
5. Read jq '.sections.paging' universal-dao.json → contains per(), page() methods
6. Judge: High relevance (directly answers question)
7. Answer using only paging section content

Total: ~10 tool calls, ~5,000 tokens
```

---

## 変更するセクション

### Quick searchセクションを以下に置き換え

**変更前:**
```markdown
The skill automatically:
1. Searches the knowledge index by keywords
2. Identifies relevant sections
3. Judges relevance to your request
4. Returns the most relevant knowledge (top 10 sections, ~5,000 tokens)
5. **Answers using ONLY information from knowledge files**
```

**変更後:**
```markdown
When Claude Code executes this skill, it will:
1. Search the knowledge index by keywords (using keyword-search workflow)
2. Identify relevant sections by reading their content
3. Judge relevance based on actual section content (using section-judgement workflow)
4. Return the most relevant knowledge (top 10 sections, ~5,000 tokens)
5. **Answer using ONLY information from knowledge files**

Note: This is a manual process requiring 10-20 tool calls. Claude Code will execute the workflows step by step.
```
