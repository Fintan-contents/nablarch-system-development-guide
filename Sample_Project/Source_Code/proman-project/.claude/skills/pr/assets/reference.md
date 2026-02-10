# GitHub PR Skill - Detailed Reference

This document describes the detailed technical specifications and workflows of the pr skill.

## Table of Contents

1. [GitHub CLI Terminology](#github-cli-terminology)
2. [Retrieving Repository Information](#retrieving-repository-information)
3. [Review Comment Processing Details](#review-comment-processing-details)
4. [Error Handling Details](#error-handling-details)
5. [Permission Requirements](#permission-requirements)

## GitHub CLI Terminology

### Review Related
- **Review**: A review on a PR (APPROVE, REQUEST_CHANGES, COMMENT)
- **Review Thread**: A thread of review comments
- **Comment**: Individual comment
- **Resolved/Unresolved**: Resolved/unresolved status
- **Outdated**: Comments on old commits

### PR Related
- **Number**: PR number (e.g., #123)
- **State**: PR state (OPEN, CLOSED, MERGED)
- **Mergeable**: Mergeability (MERGEABLE, CONFLICTING, UNKNOWN)
- **ReviewDecision**: Review decision (APPROVED, CHANGES_REQUESTED, REVIEW_REQUIRED)

## Retrieving Repository Information

### Retrieving Basic Information

```bash
# Get repository in owner/repo format
gh repo view --json nameWithOwner -q .nameWithOwner
# Example output: "octocat/Hello-World"

# Get default branch
gh repo view --json defaultBranchRef -q .defaultBranchRef.name
# Example output: "main"

# Get repository URL
gh repo view --json url -q .url
# Example output: "https://github.com/octocat/Hello-World"
```

### Retrieving PR Information

```bash
# Get PR information (JSON format)
gh pr view 123 --json number,title,state,headRefName,baseRefName,url,mergeable,reviewDecision

# Example output:
{
  "number": 123,
  "title": "Add new feature",
  "state": "OPEN",
  "headRefName": "feature/new-feature",
  "baseRefName": "main",
  "url": "https://github.com/octocat/Hello-World/pull/123",
  "mergeable": "MERGEABLE",
  "reviewDecision": "APPROVED"
}
```

### Retrieving PR List

```bash
# Search for PR of current branch
gh pr list --head "$(git branch --show-current)" --json number,state

# Filter by state
gh pr list --state open --json number,title
gh pr list --state merged --json number,title
```

## Review Comment Processing Details

### Retrieving Review Threads

```bash
# Get review threads
gh pr view 123 --json reviewThreads
```

Response structure:
```json
{
  "reviewThreads": [
    {
      "id": "RT_abc123",
      "isResolved": false,
      "isOutdated": false,
      "path": "src/main.js",
      "line": 42,
      "comments": [
        {
          "id": "RC_def456",
          "body": "Please improve this logic",
          "author": {
            "login": "reviewer"
          },
          "createdAt": "2026-02-10T10:00:00Z"
        }
      ]
    }
  ]
}
```

### Filtering Conditions

Extracting unresolved review comments:

```javascript
const unresolvedComments = reviewThreads.filter(thread => {
  // Must be unresolved
  if (thread.isResolved !== false) return false;

  // Must not be a comment on an old commit
  if (thread.isOutdated !== false) return false;

  // Must be a comment on a file (not a general comment)
  if (!thread.path) return false;

  // Must have comments
  if (!thread.comments || thread.comments.length === 0) return false;

  return true;
});
```

### Replying to Comments

**Comment on entire PR**:
```bash
gh pr comment 123 --body "Fixed. Please review."
```

**Reply to specific review comment** (advanced operation):
```bash
# Using GitHub API
gh api \
  "repos/{owner}/{repo}/pulls/123/comments/{comment_id}/replies" \
  -f body="Fixed. Please review."
```

### Resolving Review Threads

GitHub CLI does not have a direct resolve feature, so use the following methods:

1. **Request reviewer to resolve** (recommended)
   - Report completion of fix in comment
   - Reviewer resolves the thread

2. **Use GitHub API** (advanced operation)
   ```bash
   # Thread ID required
   gh api \
     "repos/{owner}/{repo}/pulls/123/threads/{thread_id}" \
     -X PATCH \
     -f resolved=true
   ```

## Error Handling Details

### Handling Push Failures

```bash
# When push is rejected
git push
# error: failed to push some refs to 'origin'

# Solution 1: Rebase
git pull --rebase origin "$(git branch --show-current)"
git push

# Solution 2: Merge (if there are conflicts)
git pull origin "$(git branch --show-current)"
# Resolve conflicts
git add .
git commit
git push

# Solution 3: Force push (use with caution)
# Not recommended for PRs under review
git push --force-with-lease
```

### Handling Merge Conflicts

```bash
# Check for conflicts before merge
git fetch origin main
git merge-base --is-ancestor origin/main HEAD
echo $?  # If not 0, merge is needed

# Resolve conflicts
git merge origin/main
# Manually resolve conflicts
git add .
git commit -m "Resolve merge conflicts"
git push
```

### Authentication Errors

```bash
# Check GitHub CLI authentication status
gh auth status

# Authentication (via browser)
gh auth login

# Authentication (via token)
gh auth login --with-token < token.txt

# Required scopes:
# - repo: Full access
# - read:org: Read organization information
# - workflow: GitHub Actions (optional)
```

### Handling Branch Deletion Failures

```bash
# When local branch deletion fails
git branch -d feature/my-branch
# error: The branch is not fully merged.

# Verify: Check if merge is complete
git log main..feature/my-branch
# If no output, it's merged

# Force delete
git branch -D feature/my-branch
```

## Permission Requirements

### Permissions Required for PR Creation

| Operation | Minimum Permission | Description |
|------|---------|------|
| Push to branch | Write | Write access to repository |
| Create PR | Write | Permission to create PR |

### Permissions Required for Review Comment Response

| Operation | Minimum Permission | Description |
|------|---------|------|
| View review comments | Read | View PR and comments |
| Add comments | Write | Add comments to PR |
| Push to branch | Write | Push fix content |
| Resolve thread | Write | Reviewer or PR author |

### Permissions Required for Merge

| Operation | Minimum Permission | Description |
|------|---------|------|
| Merge PR | Write | Depends on repository settings |
| Delete branch | Write | Delete remote branch |

**Note**: Merging to protected branches requires additional settings (required number of reviews, check requirements, etc.).

## Performance Optimization

### Batch Processing

When processing multiple review comments, consider the following:

1. **Cache file reads**: If there are multiple comments on the same file, reuse once loaded
2. **Batch commits**: Combine related fixes into one commit
3. **Optimize pushes**: After making multiple fixes, push only once at the end

### Utilizing JSON Output

```bash
# Get only necessary fields (using jq filter)
gh pr view 123 --json number,state,headRefName -q '{number, state, head: .headRefName}'

# Process arrays
gh pr list --json number,title --jq '.[] | select(.title | contains("feat"))'
```

## Troubleshooting

### Skill Does Not Start

1. Verify GitHub CLI is properly installed: `gh --version`
2. Verify authentication is complete: `gh auth status`
3. Verify `allowed-tools` contains necessary tools

### PR Not Found

1. Verify current branch is correct: `git branch --show-current`
2. Verify pushed to remote: `git log @{u}..HEAD`
3. Verify PR actually exists on GitHub: `gh pr list`

### Cannot Retrieve Review Comments

1. Verify PR number is correct
2. Verify PR is opened
3. Verify you have permissions (Read or higher)

### Cannot Push Commit

1. Check branch protection rules
2. Verify remote is up to date: `git fetch && git status`
3. Check for conflicts: `git pull --rebase`

## Best Practices

1. **Start with small fixes**: Handle simple fixes first, defer complex comments
2. **Run tests**: Always run related tests after fixes
3. **Commit messages**: Summarize the content of review feedback
4. **Ask clear questions**: When in doubt, ask specific questions
5. **Backup**: For important changes, create local backup branch before merge

## Advanced GitHub CLI Features

### Retrieving Custom Fields

```bash
# Get custom fields via GraphQL
gh api graphql -f query='
  query($owner: String!, $repo: String!, $number: Int!) {
    repository(owner: $owner, name: $repo) {
      pullRequest(number: $number) {
        reviewThreads(first: 100) {
          nodes {
            id
            isResolved
            path
            line
            comments(first: 10) {
              nodes {
                body
                author {
                  login
                }
              }
            }
          }
        }
      }
    }
  }
' -f owner=octocat -f repo=Hello-World -F number=123
```

### Webhook Configuration

```bash
# Configure webhook for PR events
gh api repos/{owner}/{repo}/hooks \
  -f name=web \
  -f config[url]=https://example.com/webhook \
  -f events[]=pull_request \
  -f events[]=pull_request_review
```

### Actions Integration

```bash
# Check workflows related to PR
gh run list --workflow=CI --json status,conclusion

# Rerun specific workflow
gh run rerun 123456
```

## References

- [GitHub CLI Official Documentation](https://cli.github.com/manual/)
- [GitHub API v4 (GraphQL)](https://docs.github.com/graphql)
- [GitHub PR Review API](https://docs.github.com/rest/pulls/reviews)
