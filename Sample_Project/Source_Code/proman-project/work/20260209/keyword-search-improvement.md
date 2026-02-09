# keyword-search.md 改善案

## 主要な変更点

1. **各ステップに「実行するツール」を明記**
2. **Claudeに対する直接的な指示形式に変更**（"You should" → "Execute this command"）
3. **ツール呼び出しの具体例を追加**
4. **判断基準を明確化**

---

## Overviewセクションの改善

**変更前:**
```markdown
**Input**: User's request (natural language)
**Output**: `pointers` object containing candidate files and sections with matched hints
**Strategy**: Technical axis - match keywords from the request against search hints in index.toon
```

**変更後:**
```markdown
**Who executes**: Claude Code (you)
**Input**: User's request (natural language)
**Output**: Candidates list for section-judgement workflow
**Strategy**: Technical axis - match keywords from the request against search hints in index.toon

**Tools you will use**:
- Read tool: Read knowledge/index.toon
- Grep tool (optional): Search for keywords in index.toon
- Bash tool with jq: Extract .index from knowledge files

**Expected tool calls**: 10-15 calls
**Expected output**: 20-30 candidate sections
```

---

## Step 1の改善（キーワード抽出）

**追加部分（手順の前に）:**

```markdown
### Step 1: Extract keywords from user request

**Tool**: None (mental process)
**Input**: User's natural language request
**Output**: List of keywords at 3 levels

**Action**: Analyze the user request and extract keywords at three levels.
```

**既存内容はそのまま維持**し、最後に以下を追加：

```markdown
**Your checklist**:
- [ ] Extracted technical domain keywords (e.g., データベース, バッチ, ハンドラ)
- [ ] Extracted technical component keywords (e.g., DAO, JDBC, JPA)
- [ ] Extracted functional keywords (e.g., ページング, 検索, 更新)
- [ ] Included both Japanese and English terms
- [ ] Included common abbreviations and related concepts

**Proceed to Step 2** with your keyword list.
```

---

## Step 2の改善（index.toon読み込み）

**変更前:**
```markdown
### Step 2: Read index.toon

Read the knowledge index:

```bash
cat knowledge/index.toon
```
```

**変更後:**
```markdown
### Step 2: Read index.toon

**Tool**: Read
**Input**: knowledge/index.toon
**Output**: 93 entries with file titles, hints, and paths

**Action**: Execute this tool call:

<function_calls>
<invoke name="Read">
<parameter name="file_path">/path/to/.claude/skills/nabledge-6/knowledge/index.toon