---
name: git
description: Unified Git orchestrator for branch management, commits, and worktree operations. Supports branch-create, commit, branch-delete, worktree-create, worktree-delete subcommands.
argument-hint: [branch-create|commit|branch-delete|worktree-create|worktree-delete] [args]
allowed-tools: Bash, Task, AskUserQuestion, Read
---

# Git Orchestrator Skill

This skill orchestrates five Git operations:
- **branch-create**: Create working branch from main
- **commit**: Commit and push changes with conventional commit format
- **branch-delete**: Delete merged branches
- **worktree-create**: Create new worktree
- **worktree-delete**: Delete existing worktree

Each operation delegates to a specialized workflow using the Task tool.

## Execution Flow

### 1. Parse Arguments

Extract mode from `$ARGUMENTS`:

```
/git                         → No args → AskUserQuestion for mode
/git branch-create           → mode="branch-create"
/git commit                  → mode="commit"
/git branch-delete [branch]  → mode="branch-delete", target_branch optional
/git worktree-create         → mode="worktree-create"
/git worktree-delete [path]  → mode="worktree-delete", target_path optional
/git worktree                → mode="worktree" → AskUserQuestion for create/delete
```

**If no args**: Use AskUserQuestion to select mode:
- Question: "Which Git operation do you want to perform?"
- Options (by frequency):
  1. "Commit & Push (commit)" - Commit changes and push to remote
  2. "Create Branch (branch-create)" - Create working branch from main
  3. "Delete Branch (branch-delete)" - Delete merged branch
  4. "Worktree Operations (worktree)" - Create or delete worktree

**If worktree mode**: Ask sub-mode:
- Question: "Select worktree operation"
- Options:
  1. "Create (worktree-create)" - Create new worktree
  2. "Delete (worktree-delete)" - Delete worktree

### 2. Get Current State

```bash
git branch --show-current
git status --porcelain
```

Pass this context to workflows.

### 3. Execute Workflow

Delegate to specialized workflow via Task tool:

#### A. branch-create Mode

```
Task
  subagent_type: "general-purpose"
  description: "Execute branch creation workflow"
  prompt: "Follow the workflow to create a working branch.

{Read and include workflows/branch-create.md}

## Input Context
- Current branch: {current_branch}
- Working tree state: {git_status}
"
```

#### B. commit Mode

```
Task
  subagent_type: "general-purpose"
  description: "Execute commit and push workflow"
  prompt: "Follow the workflow to commit and push changes.

{Read and include workflows/commit.md}

## Input Context
- Current branch: {current_branch}
- Working tree state: {git_status}
"
```

#### C. branch-delete Mode

```
Task
  subagent_type: "general-purpose"
  description: "Execute branch deletion workflow"
  prompt: "Follow the workflow to delete merged branch.

{Read and include workflows/branch-delete.md}

## Input Context
- Current branch: {current_branch}
- Target branch: {target_branch} (if specified)
"
```

#### D. worktree-create Mode

```
Task
  subagent_type: "general-purpose"
  description: "Execute worktree creation workflow"
  prompt: "Follow the workflow to create worktree.

{Read and include workflows/worktree-create.md}

## Input Context
- Current directory: {current_dir}
"
```

#### E. worktree-delete Mode

```
Task
  subagent_type: "general-purpose"
  description: "Execute worktree deletion workflow"
  prompt: "Follow the workflow to delete worktree.

{Read and include workflows/worktree-delete.md}

## Input Context
- Target path: {target_path} (if specified)
"
```

## Implementation Notes

1. **No args**: Use AskUserQuestion for user-friendly mode selection
2. **Error handling**: Display clear error messages when git commands fail
3. **Task tool usage**: Workflows execute in separate context; orchestrator receives results only
4. **Safety first**: Never force destructive operations; guide users to manual commands when needed

## Error Handling

| Error | Response |
|-------|----------|
| Not a git repository | Verify current directory is a git repository |
| Invalid subcommand | Use AskUserQuestion for mode selection |

For detailed examples, see `assets/examples.md`. For technical reference, see `assets/reference.md`.
