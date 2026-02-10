# GitHub PR Skill - Usage Examples

This document introduces practical usage examples of the pr skill.

## Basic Usage Examples

### 1. Create PR

#### Scenario: Implement login functionality on feature/add-login branch and create PR

```bash
# Switch to working branch
git checkout feature/add-login

# Commit changes (assuming already completed)
git add .
git commit -m "feat: Add login functionality"

# Create PR
/pr create
```

**Execution Details**:
1. Check current branch (feature/add-login)
2. Analyze commit history and auto-generate title and description
3. Push to remote (if necessary)
4. Create PR on GitHub

**Example Output**:
```
## PR Created

**PR**: https://github.com/owner/repo/pull/123
**Branch**: feature/add-login → main
**Title**: Add login functionality

Please request a review from reviewers.
```

### 2. Auto-Detection PR Creation

#### Scenario: Execute without arguments, automatically create if PR doesn't exist

```bash
# Changes already made on working branch
/pr
```

**Execution Details**:
1. Search for PR of current branch
2. PR doesn't exist → Automatically switch to create mode
3. Create PR

### 3. Review Comment Response

#### Scenario: Respond to review comments on current branch's PR

```bash
# Execute on branch with review comments
/pr
```

**Execution Details**:
1. Search for PR of current branch
2. PR exists → Automatically switch to resolve mode
3. Retrieve unresolved review comments
4. Analyze each comment, fix → commit → reply

**Example Output**:
```
## PR Review Response Complete

**PR**: https://github.com/owner/repo/pull/123

### Results
- Fixed and replied: 3 items
- Questioned and replied: 1 item
- Skipped: 0 items

Please request reviewer to resolve threads
```

### 4. Respond to Specific PR Review Comments

#### Scenario: Specify PR number to respond to review comments

```bash
# Execute from any branch
/pr 123
```

Or

```bash
/pr resolve 123
```

**Execution Details**:
1. Retrieve information for PR #123
2. If current branch differs from PR source branch, confirm checkout
3. Respond to unresolved review comments

### 5. Merge

#### Scenario: Merge PR after review completion

```bash
# Execute on PR source branch
/pr merge
```

Or

```bash
# Specify PR number
/pr merge 123
```

**Execution Details**:
1. Check PR status (OPEN, MERGEABLE, review approved)
2. Check CI/CD check status
3. Final confirmation with user
4. Merge PR
5. Delete remote branch (automatic on GitHub side)
6. Delete local branch and switch to main

**Example Output**:
```
## Merge Complete

**PR**: https://github.com/owner/repo/pull/123
**Branch**: feature/add-login → main

### Actions Performed
- Merged PR
- Deleted remote branch 'feature/add-login' (automatically deleted by GitHub)
- Deleted local branch 'feature/add-login'
- Switched to branch 'main'
- Fetched latest code

Good work!
```

## Advanced Usage Examples

### 6. Handle Multiple Review Comments

#### Scenario: 5 review comments exist, each requiring different response

**Comment 1**: Clear fix instruction
```
Reviewer: "Error handling is missing here"
→ Claude automatically fixes, commits, and replies
```

**Comment 2**: Unclear instruction
```
Reviewer: "Please improve performance"
→ Claude replies with question: "Which specific part's performance is concerning?"
```

**Comment 3**: Difficult instruction to address
```
Reviewer: "Please completely review the architecture"
→ Claude confirms with user: "How should this feedback be addressed?"
  - Fix / Ask question / Skip
```

**Comment 4 & 5**: Fixes to same file
```
→ Claude combines both fixes into a single commit
```

### 7. Branch Switch Required Case

#### Scenario: Respond to specific PR from another branch

```bash
# Currently on main branch
git branch --show-current
# main

# Execute review comment response for PR #123
/pr 123
```

**Execution Details**:
1. Detect that PR #123's source branch is `feature/fix-bug`
2. Detect difference from current branch (main)
3. Confirm with AskUserQuestion:
   ```
   Current branch 'main' differs from PR source branch 'feature/fix-bug'.
   Checkout 'feature/fix-bug'?

   - Yes, checkout
   - No, continue as-is
   ```
4. Select "Yes" → Execute `git checkout feature/fix-bug`
5. Continue review comment response

### 8. Error Response Examples

#### Case 1: Attempted to create PR from main branch

```bash
git checkout main
/pr create
```

**Output**:
```
Error: Cannot create PR from main branch.
Please create a feature/issue branch before executing.
```

#### Case 2: Attempting to create PR without commits

```bash
git checkout feature/empty-branch
/pr create
```

**Output**:
```
Error: No new commits from main.
Please commit changes before executing.
```

#### Case 3: Attempting to merge when not mergeable

```bash
/pr merge 123
```

**Output**:
```
Error: PR is not in a mergeable state

Please check the following:
- PR is OPEN
- No merge conflicts (mergeable: CONFLICTING)
- Required review approvals obtained
- All CI/CD checks successful
```

## Real Workflow Examples

### Full Cycle: Feature Development to Merge

```bash
# 1. Create new feature branch
git checkout -b feature/user-profile

# 2. Implement feature
# (write code)

# 3. Commit
git add .
git commit -m "feat: Add user profile feature"

# 4. Create PR
/pr create
# → PR created: https://github.com/owner/repo/pull/150

# 5. Reviewer comments
# (review performed on GitHub)

# 6. Respond to review comments
/pr resolve
# → 3 fixes implemented and replies completed

# 7. Reviewer approves
# (approved on GitHub)

# 8. Merge
/pr merge
# → Merge complete, branch cleanup complete
```

### Team Development Tips

1. **Self-check before review**:
   ```bash
   # Check code yourself once after PR creation
   gh pr view --web
   ```

2. **Priority during review comment response**:
   - Start with simple fixes (typos, formatting, etc.)
   - Defer complex fixes, ask questions as needed

3. **Pre-merge confirmation**:
   ```bash
   # Check CI/CD check status
   gh pr checks

   # Merge
   /pr merge
   ```

## GitHub-Specific Feature Examples

### Create Draft PR

```bash
# Create as draft PR (for sharing before review)
gh pr create --draft --title "WIP: Developing new feature" --body "Still work in progress"
```

### Specify Reviewers

```bash
# Specify reviewers when creating PR
gh pr create --reviewer "@octocat,@github"
```

### Add Labels

```bash
# Add labels to PR
gh pr edit 123 --add-label "bug,priority:high"
```

### Set Milestone

```bash
# Set milestone for PR
gh pr edit 123 --milestone "v2.0"
```

## CI/CD Integration Examples

### 1. Handling Test Failures

```bash
# Check PR check status
gh pr checks 123

# Re-run specific workflow
gh run rerun 456789

# Run tests locally
npm test
```

### 2. Auto-Merge Configuration

```bash
# Auto-merge after checks succeed
gh pr merge 123 --auto --squash --delete-branch
```

## Customization Examples

### Project-Specific PR Template

By placing `.github/pull_request_template.md` in the project, you can set a default template for PR creation:

```markdown
## Summary
<!-- Describe the purpose and content of changes -->

## Changes
- [ ] Feature addition
- [ ] Bug fix
- [ ] Refactoring

## Testing
- [ ] Unit tests added
- [ ] Manual testing completed

## Review Points
<!-- Describe points that require special attention -->

## Related Issues
Closes #
```

### Automatic Test Execution

Customize to automatically run tests after review response:

```bash
# Customize workflows/resolve.md
# Add the following after fixes:
echo "Running related tests..."
npm test -- --related "$modified_file"
```

### Slack Notification Integration

Configure Slack notifications using GitHub Actions or Webhooks:

```yaml
# .github/workflows/pr-notification.yml
name: PR Notification
on:
  pull_request:
    types: [opened, ready_for_review]
jobs:
  notify:
    runs-on: ubuntu-latest
    steps:
      - name: Send Slack notification
        uses: slackapi/slack-github-action@v1
        with:
          payload: |
            {
              "text": "New PR: ${{ github.event.pull_request.title }}"
            }
```

## Troubleshooting Examples

### Authentication Error

```bash
# Check authentication status
gh auth status

# Re-authenticate
gh auth login

# Check token permissions
gh auth status -t
```

### PR Not Found

```bash
# List all PRs
gh pr list --state all

# Search for PR of specific branch
gh pr list --head feature/my-branch
```

### Merge Conflict

```bash
# Fetch latest from base branch
git fetch origin main

# Merge base branch
git merge origin/main

# Resolve conflicts
# (manual fix)

# Commit and push
git add .
git commit -m "Resolve merge conflicts"
git push
```

## Best Practices

1. **Small PRs**: Keep changes small and easy to review
2. **Clear Titles**: PR titles should clearly express the changes
3. **Detailed Description**: Explain why the change is necessary
4. **Add Tests**: Always add tests corresponding to changes
5. **Continuous Communication**: Value dialogue with reviewers

For detailed customization methods, refer to the Claude Code official documentation.
