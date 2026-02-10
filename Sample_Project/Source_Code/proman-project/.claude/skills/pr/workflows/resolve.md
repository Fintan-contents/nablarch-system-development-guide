# Review Response Workflow

This workflow performs Fix → Commit → Reply for unresolved review comments.

## Required Tools

- Bash
- Read
- Edit
- Write
- AskUserQuestion
- Skill (for git commit)

## Execution Steps

### 1. Get PR Information

**1.1 Get PR Information**

```bash
gh pr view "$pr_number" --json number,title,state,headRefName,baseRefName,url
```

**1.2 Verify PR State**

Verify PR is `OPEN`. Exit with error if `CLOSED` or `MERGED`.

**1.3 Verify Branch**

Get current branch:
```bash
current_branch=$(git branch --show-current)
```

If current branch differs from `headRefName`, confirm with AskUserQuestion:
```
Current branch '{current_branch}' differs from PR source branch '{headRefName}'.
Would you like to checkout '{headRefName}'?
```

Options:
- "Yes, checkout" → `git checkout {headRefName}`
- "No, continue as is"

### 2. Get Unresolved Review Comments

**2.1 Get Review Threads**

```bash
gh pr view "$pr_number" --json reviewThreads
```

**2.2 Filtering**

Extract only comments meeting all these conditions:
- `thread.isResolved === false`
- `thread.isOutdated === false`
- `thread.path !== null` (comments on files)

If 0 unresolved comments:
```
## Review Response Complete

**PR**: {pr_url}

No unresolved comments.
```

Exit.

### 3. Process Each Comment

For each unresolved comment, execute the following:

**3.1 Analysis**

Get comment information:
- `comment.body`: Comment body
- `comment.path`: File to fix
- `comment.line`: Line number (if exists)
- `comment.author.login`: Reviewer name

Use Read tool to load file and check relevant section.

**3.2 Decision**

Select one of the following:

1. **Fix needed and content is clear**
   - Proceed autonomously to fix
   - Example: "Fix typo", "Change variable name"

2. **Unclear points**
   - Reply with question
   - Example: "Please improve performance" → "Which specific part is a concern?"

3. **Disagree/Not actionable**
   - Skip and report
   - Example: "Completely rethink architecture" → Defer to user judgment

4. **Difficult to judge**
   - Confirm with AskUserQuestion
   - Options: Fix / Ask question / Skip

**3.3 Fix, Commit, Push (When fixing)**

**3.3.1 Fix File**

Fix file with Edit or Write tool.

If multiple files need fixing, fix all before creating a single commit.

**3.3.2 Commit and Push with git Skill**

Execute git skill's commit subcommand using Skill tool:

```
Skill
  skill: "git"
  args: "commit"
```

Git skill automatically executes:
- Analyze changed files
- Generate commit message (format: `fix: {summary of review feedback}`)
- Staging (git add)
- Commit
- Push

**3.3.3 Get Commit SHA**

```bash
commit_sha=$(git rev-parse HEAD)
repo_url=$(gh repo view --json url -q .url)
```

**3.4 Reply**

**For fixes**:

```bash
gh pr comment "$pr_number" --body "$(cat <<'EOF'
Fixed

**Commit**: {repo_url}/commit/{commit_sha}

{Brief description of fix}

Co-Authored-By: Claude (jp.anthropic.claude-sonnet-4-5-20250929-v1:0) <noreply@anthropic.com>
EOF
)"
```

**For questions**:

```bash
gh pr comment "$pr_number" --body "$(cat <<'EOF'
Please clarify

{Question content}

Co-Authored-By: Claude (jp.anthropic.claude-sonnet-4-5-20250929-v1:0) <noreply@anthropic.com>
EOF
)"
```

**Note**: `gh pr comment` adds a comment to the overall PR. Direct replies to specific review comments require advanced operations using `gh api`.

### 4. Resolve Review Comments

After fixing, mark review thread as resolved:

```bash
# gh CLI does not have direct resolve functionality, so request reviewer to resolve
echo "Please request reviewer to resolve threads"
```

### 5. Summary

After processing all comments, display summary:

```
## PR Review Response Complete

**PR**: {pr_url}

### Results
- Fixed and replied: {n} items
- Asked questions and replied: {n} items
- Skipped: {n} items

Please request reviewer to resolve threads
```

## Error Handling

| Error | Response |
|-------|----------|
| PR not found | Verify correct PR number |
| PR closed/merged | Specify opened PR |
| Push failure | `git pull --rebase` and retry push |
| Conflict | Manually resolve conflict then retry |
| Authentication error | Authenticate with `gh auth login` |

## Notes

1. **Emoji Usage**: Do not use emojis unless user explicitly requests them
2. **GitHub Permissions**: Requires Write or higher for commenting and pushing
3. **Autonomous Judgment**: Respond autonomously when fix content is clear; use AskUserQuestion only when judgment is difficult
4. **Test Execution**: Run related tests if they exist after fixing
5. **Multiple Files**: When one comment requires multiple file fixes, fix all before creating a single commit
6. **Thread Resolution**: GitHub CLI lacks direct resolve functionality, so request reviewer resolution

## Advanced Operation: Reply to Specific Comments

To reply directly to specific review comments, use GitHub API:

```bash
# Get review comment ID (from reviewThreads)
comment_id="{thread.comments[0].id}"

# Add reply
gh api \
  "repos/{owner}/{repo}/pulls/{pr_number}/comments/$comment_id/replies" \
  -f body="Fixed. Please review."
```

However, PR-level comments are usually sufficient, so use this as needed.
