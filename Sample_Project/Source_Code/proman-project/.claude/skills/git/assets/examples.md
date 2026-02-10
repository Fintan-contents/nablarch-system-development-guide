# Git Skill - Usage Examples

## Basic Workflows

### 1. Create Working Branch

**Scenario**: Create new feature branch from main

```bash
# Verify on main branch
git branch --show-current
# main

# Create working branch
/git branch-create
```

**Interaction**:
```
Q: What will you implement or fix in this branch?
→ Select "New Feature"

Q: Please provide details.
→ Enter "user authentication feature"

Q: Select branch name.
→ Select "add-user-auth" (Recommended)
```

**Output**:
```
## Branch Creation Complete

**Branch Name**: add-user-auth
**Base Branch**: main

You can now start working.
Use `/git commit` to commit changes.
```

### 2. Commit and Push

**Scenario**: Commit changes with conventional commit format

```bash
# Make changes
# (write code)

# Commit
/git commit
```

**Output**:
```
## Commit Complete

**Branch**: add-user-auth
**Commit Message**: feat: Add user authentication feature
**Changed Files**: 3 files

Changes have been pushed to remote.
```

### 3. Delete Merged Branch

**Scenario**: Delete branch after merge

```bash
/git branch-delete
```

**Interaction**:
```
Q: Select branch to delete.
→ Select "add-user-auth" (Merged)
```

**Output**:
```
## Branch Deletion Complete

**Deleted Branch**: add-user-auth

### Actions Performed
- Deleted remote branch 'origin/add-user-auth'
- Deleted local branch 'add-user-auth'
- Switched to main branch
- Fetched latest code
```

### 4. Create Worktree

**Scenario**: Create worktree for urgent bug fix

```bash
# From main repository
/git worktree-create
```

**Interaction**:
```
Q: What will you implement or fix in this worktree?
→ Select "Bug Fix"

Q: Please provide details.
→ Enter "login page bug"

Q: Select branch name.
→ Select "fix-login-page"

Q: Create worktree at the following path. OK?
→ Select "Yes, create it"
```

**Output**:
```
## Worktree Creation Complete

**Path**: <workspace-path>/nab-agents-fix-login-page
**Branch**: fix-login-page
**Base Branch**: main

### Move to Worktree
cd <workspace-path>/nab-agents-fix-login-page

You can now start working.
Use `/git commit` to commit changes.
```

### 5. Delete Worktree

**Scenario**: Delete worktree after completing work

```bash
/git worktree-delete
```

**Interaction**:
```
Q: Select worktree to delete.
→ Select "<workspace-path>/nab-agents-fix-login-page"

Q: Branch "fix-login-page" is merged. Delete this branch too?
→ Select "Yes, delete it"
```

**Output**:
```
## Worktree Deletion Complete

**Deleted Worktree**: <workspace-path>/nab-agents-fix-login-page
**Branch**: fix-login-page

### Actions Performed
- Deleted worktree '<workspace-path>/nab-agents-fix-login-page'
- Deleted local branch 'fix-login-page'
- Deleted remote branch 'origin/fix-login-page'
```

## Advanced Scenarios

### 6. Auto-exclude Sensitive Files

**Scenario**: Commit includes .env file

```bash
/git commit
```

**Output**:
```
Warning: The following sensitive files were detected:
- .env
- config/secrets.yml

These files will be excluded from the commit.

## Commit Complete

**Branch**: add-feature
**Commit Message**: feat: Add new feature
**Changed Files**: 5 files (2 sensitive files excluded)
```

### 7. Delete Worktree with Uncommitted Changes

**Scenario**: Worktree has uncommitted changes

```bash
/git worktree-delete <workspace-path>/nab-agents-experimental
```

**Interaction**:
```
Q: Worktree has uncommitted changes. Deletion will lose these changes. Delete anyway?

Changes:
M  src/experimental.ts
?? src/test.ts

→ Select "Yes, delete it" or "No, cancel"
```

### 8. Auto-recovery from Push Failure

**Scenario**: Remote has new commits, push fails

```bash
/git commit
```

**Output**:
```
Commit complete. Pushing to remote...
Remote has new commits. Rebasing and retrying push...

## Commit Complete

**Branch**: add-feature
**Commit Message**: feat: Add new feature
**Changed Files**: 3 files

Changes have been pushed to remote.
```

## Error Handling Examples

### Not on Main Branch

```bash
git checkout feature-branch
/git branch-create
```

**Output**:
```
Error: Working branches must be created from the main branch.
Current branch: feature-branch

To switch to main:
git checkout main
```

### Unmerged Branch Deletion Attempt

```bash
/git branch-delete unmerged-branch
```

**Output**:
```
Error: Branch "unmerged-branch" is not yet merged.

To force delete unmerged branches, use manual command:
git branch -D unmerged-branch

Warning: Force deletion will lose unmerged changes.
```

### No Changes to Commit

```bash
/git commit
```

**Output**:
```
Error: No changes to commit.

Check current status:
git status
```

### Branch Name Already Exists

```bash
/git branch-create
# → Select "add-feature" → Already exists
```

**Output**:
```
Error: Branch "add-feature" already exists.

Use a different name or delete the existing branch:
git branch -d add-feature
```

## Complete Development Workflow

### Full Cycle: Feature Development to Merge

```bash
# 1. Create working branch
/git branch-create
# → "New Feature" → "user profile feature" → "add-user-profile"

# 2. Implement feature
# (write code)

# 3. Commit
/git commit
# → Auto-committed and pushed

# 4. Create MR
/mr create
# → MR created

# 5. Handle review comments
/mr resolve
# → Fixed and replied

# 6. Merge
/mr merge
# → Merged

# 7. Delete branch
/git branch-delete
# → Branch deleted
```

### Parallel Work with Worktree

```bash
# Main repository: developing feature-a
cd <workspace-path>/nab-agents
# Branch: add-feature-a

# Create worktree for urgent bug fix
/git worktree-create
# → fix-critical-bug worktree created

# Move to worktree and fix bug
cd <workspace-path>/nab-agents-fix-critical-bug
# (fix bug)
/git commit
/mr create
/mr merge

# Delete worktree
/git worktree-delete
# → Worktree deleted

# Return to main repository and continue feature-a
cd <workspace-path>/nab-agents
# (continue feature-a development)
```

## Team Development Tips

1. **Branch naming**: Use consistent prefixes (`add-`, `fix-`, `refactor-`, `docs-`)
2. **Commit granularity**: One purpose per commit; split large changes
3. **Worktree usage**:
   - Urgent bug fixes while preserving current work
   - Parallel development of multiple features
   - Review response without interrupting main work
4. **Branch cleanup**: Use `/git branch-delete` regularly after merges
