# Commit and Push Workflow

This workflow commits changes and pushes them to the remote repository.

## Required Tools

- Bash
- AskUserQuestion

## Execution Steps

### 1. Pre-flight Checks

**1.1 Check Current Branch**

```bash
git branch --show-current
```

If on `main` or `master`, display warning (not an error):
```
Warning: You are about to commit directly to the main branch.
It is recommended to work on a feature branch.

Do you want to continue?
```

**1.2 Check for Changes**

```bash
git status --porcelain
```

If no changes, exit with error:
```
Error: No changes to commit.

Check current status:
git status
```

### 2. Analyze Changes

**2.1 Get Staged and Unstaged Changes**

```bash
git status
git diff HEAD --stat
git diff HEAD
```

**2.2 Detect Sensitive Files**

Detect the following patterns:
- `.env`, `.env.*`
- `*credentials*`, `*secret*`, `*password*`
- `*.key`, `*.pem`
- `config/database.yml`, `config/secrets.yml`

If sensitive files detected, display warning:
```
Warning: The following sensitive files were detected:
- {file1}
- {file2}

These files will be excluded from the commit.
```

**2.3 Determine Files to Commit**

Create file list excluding sensitive files.

### 3. Generate Commit Message

**3.1 Analyze Changes**

Determine from diff and file names:
- **Change type**: feat, fix, refactor, update, docs, test, chore
- **Change target**: Main subject of changes (from file names and diff)
- **Change purpose**: Inferred from diff content

**3.2 Generate Message**

Follow project commit rules (`.claude/rules/commit-rules.md`):

```
{type}: {concise description conveying purpose/intent}

Co-Authored-By: Claude Opus 4.6 <noreply@anthropic.com>
```

**Type Examples**:
- `feat: Add user authentication feature`
- `fix: Fix session timeout on login`
- `refactor: Improve error handling in API layer`
- `update: Improve user settings UI`
- `docs: Add setup guide to README`

**Generation Rules**:
- First line: Target 50 chars (max 70)
- Follow project language requirements (check `.claude/rules/commit-rules.md`)
- Convey "why" not just "what"
- Use clear action verbs ("add", "fix", "improve")
- No technical details in title

**Project-Specific Commit Rules** (from `.claude/rules/commit-rules.md`):
1. **Split by purpose**: Each commit = one purpose (feature/fix/refactor)
2. **Write clearly**: Describe why the change was made
3. **Language**: Follow project requirements for commit message language
4. **Format**: `<type>: <summary>`
5. **Test code**: Can be included in same commit as implementation
6. **Always push**: Push immediately after commit to prevent work loss

### 4. Execute Commit

**4.1 Stage Files**

Stage files individually, excluding sensitive files:

```bash
git add {file1} {file2} {file3} ...
```

**Important**: Never use `git add -A` or `git add .` to prevent accidental sensitive file commits.

**4.2 Commit**

Use HEREDOC format:

```bash
git commit -m "$(cat <<'EOF'
{commit_message}
EOF
)"
```

### 5. Push to Remote

**5.1 Push**

```bash
git push -u origin {current_branch}
```

**5.2 Handle Push Failures**

If rejected (remote has new commits):

```bash
git pull --rebase origin {current_branch}
```

If rebase conflicts occur:
```
Error: Conflicts detected.
Please resolve the following files manually:
{conflict_files}

After resolution:
git add {resolved_files}
git rebase --continue
git push
```

If rebase succeeds, push again:
```bash
git push
```

### 6. Display Result

```
## Commit Complete

**Branch**: {current_branch}
**Commit Message**: {commit_message_first_line}
**Changed Files**: {file_count} files

Changes have been pushed to remote.
```

## Error Handling

| Error | Response |
|-------|----------|
| No changes | Guide to edit files before running |
| Sensitive files detected | Auto-exclude and continue (show warning) |
| Conflicts detected | Guide to manual resolution |
| Push failed | Rebase and retry push |
| Rebase failed | Guide to manual resolution |

## Important Notes

1. **No emojis**: Never use emojis unless explicitly requested by user
2. **Sensitive file protection**: Auto-detect and exclude, display warning
3. **Message quality**: Follow project rules - convey purpose/intent
4. **Safe staging**: Never use `git add .`, specify files individually
5. **Always push**: Project rule requires immediate push after commit
