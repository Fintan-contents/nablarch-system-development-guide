# PR Creation Workflow

This workflow creates a PR from the current branch to main.

## Required Tools

- Bash
- Read

## Execution Steps

### 1. Pre-flight Checks

**1.1 Verify Current Branch**

```bash
git branch --show-current
```

If current branch is `main` or `master`, exit with error:
```
Error: Cannot create PR from main branch.
Please create a feature/issue branch first.
```

**1.2 Get Default Branch**

```bash
default_branch=$(gh repo view --json defaultBranchRef -q .defaultBranchRef.name)
```

**1.3 Check Commit History**

```bash
git log "$default_branch"..HEAD --oneline
```

If no commits exist, exit with error:
```
Error: No new commits from {default_branch}.
Please commit your changes first.
```

**1.4 Verify Remote Push**

```bash
git status
```

If "Your branch is ahead of" or "have diverged" appears, push is needed:
```bash
git push -u origin "$(git branch --show-current)"
```

If push fails (rejected):
```bash
git pull --rebase origin "$(git branch --show-current)"
git push
```

### 2. Generate PR Title and Description

**2.1 Get Commit History and Diff**

```bash
git log "$default_branch"..HEAD --format="%s"
git diff "$default_branch"...HEAD --stat
```

**2.2 Generate Title and Description**

Analyze commit history and diff, generate in the following format:

**Title**: Summarize main changes (within 70 characters)
- Example: "feat: Add user authentication feature"
- Example: "fix: Fix session timeout on login"

**Description**:
```markdown
## Summary
{Describe purpose and content of changes in 1-3 sentences}

## Changes
{List main changes as bullet points}

## Testing
- [ ] Manual testing completed
- [ ] Tests added/updated (if needed)

🤖 Generated with [Claude Code](https://claude.com/claude-code)
```

### 3. Create PR

Create PR with generated title and description:

```bash
gh pr create \
  --title "{generated_title}" \
  --body "{generated_description}" \
  --base "$default_branch" \
  --head "$current_branch"
```

### 4. Display Result

```
## PR Creation Complete

**PR**: {pr_url}
**Branch**: {source_branch} → {target_branch}
**Title**: {title}

Please request review from reviewers.
```

## Error Handling

| Error | Response |
|-------|----------|
| Execute from main branch | Guide to execute from feature/issue branch |
| No commits | Guide to commit changes first |
| Push failure | `git pull --rebase` and retry push |
| Authentication error | Authenticate with `gh auth login` |

## Notes

1. **Emoji Usage**: Do not use emojis unless user explicitly requests them
2. **GitHub Permissions**: Requires Write or higher permissions
3. **Title Quality**: Generate appropriate title if commit messages are inadequate
4. **HEREDOC Usage**: Use HEREDOC for multi-line PR body to ensure correct formatting

### HEREDOC Usage Example

```bash
gh pr create \
  --title "feat: Add user authentication" \
  --body "$(cat <<'EOF'
## Summary
Added user authentication feature.

## Changes
- Implemented login form
- Added session management

🤖 Generated with [Claude Code](https://claude.com/claude-code)
EOF
)" \
  --base main \
  --head feature/auth
```
