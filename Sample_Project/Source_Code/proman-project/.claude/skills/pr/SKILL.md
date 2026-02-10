---
name: pr
description: Unified orchestrator for GitHub PR operations (create/resolve/merge). Use with commands like "create PR", "respond to reviews", "merge PR". Supports subcommands (create/resolve/merge). Without PR number, automatically detects from current branch.
argument-hint: [create|resolve|merge] [PR number]
allowed-tools: Bash, Task, AskUserQuestion
---

# GitHub PR Unified Management (Orchestrator)

This skill executes three GitHub PR operations in an integrated manner:
- **create**: Create PR from current branch to main
- **resolve**: Fix → Commit → Reply to unresolved review comments
- **merge**: Merge PR and cleanup branches

Each operation executes a dedicated workflow in a separate context using the Task tool.

## Execution Flow

### 1. Argument Parsing

Parse `$ARGUMENTS` to determine mode and PR number:

```javascript
// Pattern 1: /pr → no args → Select mode with AskUserQuestion
// Pattern 2: /pr create → mode="create", pr_number=null
// Pattern 3: /pr resolve 123 → mode="resolve", pr_number=123
// Pattern 4: /pr 123 → mode="resolve", pr_number=123
// Pattern 5: /pr merge → mode="merge", pr_number=null
```

**When no arguments provided**: First search for PR from current branch to get context, then select mode with AskUserQuestion tool:
- Present recommended options based on PR search results
- Question: "Which PR operation would you like to execute?"
- Options (ordered by frequency, recommended option first):
  1. If PR exists: Show "Respond to reviews (resolve) (Recommended)" first
  2. If PR doesn't exist: Show "Create PR (create) (Recommended)" first
  3. Other options in frequency order: "Respond to reviews (resolve)", "Create PR (create)", "Merge (merge)"

### 2. Get Repository Information

```bash
gh repo view --json nameWithOwner -q .nameWithOwner
```

Output format: `owner/repo`

### 3. Auto-detect PR (when pr_number=null)

Search for PR from current branch:

```bash
current_branch=$(git branch --show-current)
gh pr list --head "$current_branch" --json number,state --jq '.[0].number'
```

**Detection Logic**:
- Result exists + pr_number=null → pr_number={detected PR number}
- Result empty + pr_number=null → Continue if mode="create", otherwise error

### 4. Execute Workflow

Execute dedicated workflow with Task tool according to mode:

#### A. create Mode

```
Task
  subagent_type: "general-purpose"
  description: "Execute PR creation flow"
  prompt: "Please create a GitHub PR following the workflow below.

{Load and expand contents of workflows/create.md}

## Input Information
- Repository: {owner/repo}
- Current Branch: {current_branch}
"
```

**Load and execute workflows/create.md**: Use Read tool to load `workflows/create.md` and include its contents in the prompt.

#### B. resolve Mode

```
Task
  subagent_type: "general-purpose"
  description: "Execute review response flow"
  prompt: "Please respond to GitHub PR review comments following the workflow below.

{Load and expand contents of workflows/resolve.md}

## Input Information
- Repository: {owner/repo}
- PR Number: {pr_number}
- Current Branch: {current_branch}
"
```

**Load and execute workflows/resolve.md**

#### C. merge Mode

```
Task
  subagent_type: "general-purpose"
  description: "Execute merge flow"
  prompt: "Please merge the GitHub PR following the workflow below.

{Load and expand contents of workflows/merge.md}

## Input Information
- Repository: {owner/repo}
- PR Number: {pr_number}
"
```

**Load and execute workflows/merge.md**

## Implementation Notes

1. **When no arguments provided**: Present recommended options based on PR search results, select mode with AskUserQuestion (user-friendly)
2. **Error Handling**: Display clear error messages when repository information cannot be retrieved or PR search fails
3. **Branch Validation**: Error if attempting to execute create from main branch
4. **Task Tool Usage**: Each workflow executes in a separate context, orchestrator only receives results

## Error Handling

| Error | Response |
|-------|----------|
| gh CLI unavailable | Guide user to run `gh auth login` |
| Cannot get git remote | Verify repository is on GitHub |
| Invalid PR number / PR not found | Verify correct PR number |
| Execute create from main branch | Guide to execute from feature/issue branch |

For detailed usage examples, see `assets/examples.md`. For reference, see `assets/reference.md`.
