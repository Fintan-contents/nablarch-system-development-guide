# Branch Deletion Workflow

This workflow deletes merged branches from local and remote repositories.

## Required Tools

- Bash
- AskUserQuestion

## Execution Steps

### 1. Determine Target Branch

**1.1 Check Arguments**

If argument is specified, use it as target.
If no argument, proceed to next step to let user select.

**1.2 Get Deletable Branches**

```bash
git branch --merged main
```

Exclude main branch from results to create list of deletable branches.

If no branches:
```
Info: No merged branches to delete.

Check merged branches:
git branch --merged main
```

**1.3 Select Target (if no argument)**

Use AskUserQuestion to select branch:

```
Question: Select branch to delete.
Header: "Delete Branch"
Options:
  - Label: "{branch1}"
    Description: "Merged"
  - Label: "{branch2}"
    Description: "Merged"
  - Label: "{branch3}"
    Description: "Merged"
```

### 2. Pre-deletion Checks

**2.1 Protect Main Branch**

If target is `main` or `master`, exit with error:
```
Error: Cannot delete main branch.
```

**2.2 Verify Merged Status**

```bash
git branch --merged main | grep "^  {branch_name}$"
```

If not merged, exit with error:
```
Error: Branch "{branch_name}" is not yet merged.

To force delete unmerged branches, use manual command:
git branch -D {branch_name}

Warning: Force deletion will lose unmerged changes.
```

**2.3 Check Current Branch**

```bash
git branch --show-current
```

If current branch is the target, switch to main:
```bash
git checkout main
```

### 3. Update Main Branch

```bash
git fetch origin main
git pull origin main
```

### 4. Delete Branch

**4.1 Delete Remote Branch**

```bash
git push origin --delete {branch_name}
```

If remote branch doesn't exist, ignore error (show warning only):
```
Warning: Remote branch "{branch_name}" is already deleted.
```

If permission error:
```
Error: Failed to delete remote branch.
Please verify you have write access to the repository.

To delete local branch only:
git branch -d {branch_name}
```

**4.2 Delete Local Branch**

```bash
git branch -d {branch_name}
```

### 5. Update Remote Branch Info

```bash
git fetch --prune
```

### 6. Display Result

```
## Branch Deletion Complete

**Deleted Branch**: {branch_name}

### Actions Performed
- Deleted remote branch 'origin/{branch_name}'
- Deleted local branch '{branch_name}'
- Switched to main branch
- Fetched latest code
```

## Error Handling

| Error | Response |
|-------|----------|
| No deletable branches | Check merged branches |
| Attempted to delete main | Display error and exit |
| Unmerged branch | Guide to manual force deletion |
| Remote branch doesn't exist | Show warning only, continue with local deletion |
| Insufficient permissions | Guide to check repository permissions |

## Important Notes

1. **No emojis**: Never use emojis unless explicitly requested by user
2. **Safety first**: Only delete merged branches, guide to manual force deletion for unmerged
3. **Main branch protection**: Refuse deletion of main branch
4. **Remote priority**: Delete remote branch first, then local branch
