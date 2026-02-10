# Branch Creation Workflow

This workflow creates a working branch from the main branch.

## Required Tools

- Bash
- AskUserQuestion

## Execution Steps

### 1. Pre-flight Checks

**1.1 Verify Current Branch**

```bash
git branch --show-current
```

If not on `main`, exit with error:
```
Error: Working branches must be created from the main branch.
Current branch: {current_branch}

To switch to main:
git checkout main
```

**1.2 Verify Clean Working Tree**

```bash
git status --porcelain
```

If uncommitted changes exist, exit with error:
```
Error: You have uncommitted changes.
Please commit or stash changes before proceeding.

Check changes:
git status

Stash changes:
git stash
```

**1.3 Update Remote**

```bash
git fetch origin main
git pull origin main
```

If pull fails (conflicts):
```
Error: Failed to update main branch.
Please resolve conflicts before proceeding.
```

### 2. Propose Branch Names

**2.1 Ask Work Purpose**

Use AskUserQuestion to understand work purpose:

```
Question: What will you implement or fix in this branch?
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

**Examples**:
- Details: "Add user authentication feature"
  - Candidate 1: `add-user-auth`
  - Candidate 2: `add-authentication`
  - Candidate 3: `user-auth-feature`

- Details: "Fix login page bug"
  - Candidate 1: `fix-login-page`
  - Candidate 2: `fix-login-bug`
  - Candidate 3: `login-page-fix`

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

### 3. Create Branch

Create branch with selected name:

```bash
git checkout -b {branch_name}
```

### 4. Display Result

```
## Branch Creation Complete

**Branch Name**: {branch_name}
**Base Branch**: main

You can now start working.
Use `/git commit` to commit changes.
```

## Error Handling

| Error | Response |
|-------|----------|
| Not on main branch | Guide to switch to main |
| Uncommitted changes | Guide to commit or stash |
| Failed to update main | Guide to resolve conflicts |
| Branch name exists | Guide to use different name or delete existing |

## Important Notes

1. **No emojis**: Never use emojis unless explicitly requested by user
2. **Branch name quality**: Generate appropriate names from user input
3. **Safety**: Protect main branch, check duplicates, verify clean working tree
