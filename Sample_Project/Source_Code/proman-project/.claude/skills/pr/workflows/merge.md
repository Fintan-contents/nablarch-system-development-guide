# Merge Workflow

This workflow merges the PR and cleans up branches.

## Required Tools

- Bash
- AskUserQuestion
- Skill (for git branch-delete)

## Execution Steps

### 1. Get and Verify PR Information

**1.1 Get PR Information**

```bash
gh pr view "$pr_number" --json number,title,state,headRefName,baseRefName,url,mergeable,reviewDecision
```

**1.2 Check Mergeability**

Verify all of the following:
- `state === "OPEN"`
- `mergeable === "MERGEABLE"`
- `reviewDecision === "APPROVED"` (depends on project settings)

If any condition is not met, display error message and exit:

```
Error: PR cannot be merged

Please verify:
- PR is OPEN
- No merge conflicts (mergeable: {actual_status})
- Required review approvals obtained (reviewDecision: {actual_value})
- All CI/CD checks passing
```

**1.3 Verify CI/CD Checks**

```bash
gh pr checks "$pr_number"
```

Display warning if any checks are failing.

**1.4 User Confirmation**

Final confirmation with AskUserQuestion:
```
Merge the following PR. Is this OK?

**PR**: {pr_url}
**Title**: {pr_title}
**Branch**: {headRefName} → {baseRefName}
```

Options:
- "Yes, merge"
- "No, cancel"

### 2. Execute Merge

If user approves, execute merge:

```bash
gh pr merge "$pr_number" \
  --squash \
  --delete-branch
```

Option descriptions:
- `--squash`: Squash merge (default) - combine all commits into one
- `--delete-branch`: Automatically delete remote branch after merge

**Merge Strategy Options**:
- `--squash`: Squash merge (combine all commits into one)
- `--merge`: Create merge commit
- `--rebase`: Rebase and merge

Choose appropriate method based on project settings.

### 3. Local Branch Cleanup

Execute git skill's branch-delete subcommand using Skill tool:

```
Skill
  skill: "git"
  args: "branch-delete {headRefName}"
```

Git skill automatically executes:
- Switch to base branch (if needed)
- Update base branch (fetch + pull)
- Verify remote branch deletion (warning only if already deleted)
- Delete local branch
- Update remote branch info (fetch --prune)

### 4. Display Result

```
## Merge Complete

**PR**: {pr_url}
**Branch**: {headRefName} → {baseRefName}

### Actions Performed
- Merged PR
- Deleted remote branch '{headRefName}' (automatically deleted by GitHub)
- Deleted local branch '{headRefName}'
- Switched to branch '{baseRefName}'
- Fetched latest code

Good work!
```

## Error Handling

| Error | Response |
|-------|----------|
| PR not found | Verify correct PR number |
| state !== "OPEN" | PR already closed or merged |
| mergeable !== "MERGEABLE" | Resolve merge conflicts then retry |
| reviewDecision !== "APPROVED" | Obtain required review approvals |
| Check failure | Fix CI/CD then retry |
| No merge permission | Verify Write or higher permissions |
| Branch deletion failure | Force delete with `git branch -D` |

## Notes

1. **Emoji Usage**: Do not use emojis unless user explicitly requests them
2. **GitHub Permissions**: Requires Write or higher for PR merge (depends on repository settings)
3. **Protected Branches**: For protected base branches, additional permission settings (required review count, check requirements, etc.) are needed
4. **Merge Strategy**: Choose from `--squash`, `--merge`, or `--rebase` based on project policy
5. **Remote Branch Deletion**: `--delete-branch` automatically deletes remote branch on GitHub side

## Merge Strategy Selection

Choose merge strategy based on project policy:

### Squash Merge (Recommended)
```bash
gh pr merge "$pr_number" --squash --delete-branch
```
- **Benefit**: Simplified commit history
- **Use case**: General cases like feature development, bug fixes

### Merge Commit
```bash
gh pr merge "$pr_number" --merge --delete-branch
```
- **Benefit**: Preserves full commit history
- **Use case**: When detailed commit history should be retained

### Rebase Merge
```bash
gh pr merge "$pr_number" --rebase --delete-branch
```
- **Benefit**: Linear commit history
- **Use case**: When avoiding merge commits

## Auto-merge Configuration

To automatically merge when merge conditions are met:

```bash
gh pr merge "$pr_number" --auto --squash --delete-branch
```

This will automatically merge once required checks and reviews are complete.
