# Worktree Creation Workflow

This workflow creates a new worktree for parallel work.

## Required Tools

- Bash
- AskUserQuestion

## Execution Steps

### 1. Get Current Directory Info

**1.1 Get Current Directory and Repository Name**

```bash
pwd
basename $(pwd)
```

- Current directory: `<workspace-path>/nab-agents`
- Repository name: `nab-agents`

**1.2 Get Parent Directory**

```bash
dirname $(pwd)
```

- Parent directory: `<workspace-parent-path>`

### 2. Propose Branch Names

**2.1 Ask Work Purpose**

Use AskUserQuestion to understand work purpose:

```
Question: What will you implement or fix in this worktree?
Header: "Work Type"
Options:
  - Label: "New Feature"
    Description: "Implement a new feature"
  - Label: "Bug Fix"
    Description: "Fix an existing bug"
  - Label: "Refactoring"
    Description: "Improve code structure"
  - Label: "Documentation"
    Description: "Update documentation"
```

**2.2 Ask for Details**

Based on selected work type, ask for details (free text):
```
Please provide details about "{work_type}".
Examples: user authentication, login page bug, API layer refactoring, etc.
```

**2.3 Generate Branch Names**

Generate 3 branch name candidates from the details:

**Generation Rules**:
- Prefix: `add-` (feature), `fix-` (bug), `refactor-` (refactor), `docs-` (docs)
- Body: Keywords from details joined with `-`
- All lowercase, alphanumeric and hyphens only

**2.4 Select Branch Name**

Use AskUserQuestion to select from candidates:

```
Question: Select branch name.
Header: "Branch Name"
Options:
  - Label: "{candidate1}"
    Description: "Recommended"
  - Label: "{candidate2}"
    Description: ""
  - Label: "{candidate3}"
    Description: ""
```

If user selects "Other", accept free text input.

**2.5 Check for Duplicates**

```bash
git branch --list "{branch_name}"
```

If exists, exit with error:
```
Error: Branch "{branch_name}" already exists.

Use a different name or delete the existing branch:
git branch -d {branch_name}
```

### 3. Determine Worktree Path

**3.1 Generate Path**

```bash
parent_dir=$(dirname $(pwd))
repo_name=$(basename $(pwd))
worktree_path="${parent_dir}/${repo_name}-${branch_name}"
```

Example:
- Current: `<workspace-path>/nab-agents`
- Worktree: `<workspace-path>/nab-agents-<branch-name>`

**3.2 Check Path Existence**

```bash
test -e {worktree_path}
```

If exists, exit with error:
```
Error: Path "{worktree_path}" already exists.

Use a different branch name or delete the existing directory.
```

**3.3 Confirm Path**

Use AskUserQuestion to confirm path:

```
Question: Create worktree at the following path. OK?
Header: "Confirm Path"
Options:
  - Label: "Yes, create it"
    Description: "{worktree_path}"
  - Label: "No, cancel"
    Description: ""

Display info:
Path: {worktree_path}
Branch: {branch_name}
Base: main
```

### 4. Create Worktree

**4.1 Update Main Branch**

```bash
git fetch origin main
git pull origin main
```

**4.2 Create Worktree**

```bash
git worktree add -b {branch_name} {worktree_path} main
```

If creation fails:
```
Error: Failed to create worktree.

Please verify:
- Sufficient disk space
- Write permissions to the path
- Valid branch name
```

### 5. Display Result

```
## Worktree Creation Complete

**Path**: {worktree_path}
**Branch**: {branch_name}
**Base Branch**: main

### Move to Worktree
cd {worktree_path}

You can now start working.
Use `/git commit` to commit changes.
```

## Error Handling

| Error | Response |
|-------|----------|
| Branch name exists | Guide to use different name or delete existing |
| Path exists | Guide to use different name or delete existing directory |
| Failed to update main | Guide to resolve conflicts |
| Insufficient permissions | Guide to check write permissions |
| Insufficient disk space | Guide to check disk space |

## Important Notes

1. **No emojis**: Never use emojis unless explicitly requested by user
2. **Path naming convention**: `{parent_dir}/{repo_name}-{branch_name}`
3. **Branch name quality**: Generate appropriate names from user input
4. **Base branch**: Always branch from main
