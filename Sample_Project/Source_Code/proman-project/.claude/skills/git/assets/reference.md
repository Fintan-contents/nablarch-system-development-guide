# Git Skill - Technical Reference

## Branch Naming Conventions

### Prefixes

| Prefix | Purpose | Example |
|--------|---------|---------|
| `add-` | New feature | `add-user-auth` |
| `fix-` | Bug fix | `fix-login-page` |
| `refactor-` | Refactoring | `refactor-api-layer` |
| `update-` | Existing feature improvement | `update-user-settings` |
| `docs-` | Documentation | `docs-setup-guide` |
| `test-` | Test addition/modification | `test-auth-logic` |
| `chore-` | Build, config changes | `chore-update-deps` |

### Naming Rules

1. All lowercase
2. Only alphanumeric and hyphens
3. Required prefix indicating work type
4. Concise: 3-5 words, max 50 chars
5. Descriptive: Purpose should be clear

## Commit Message Format

### Structure

```
{type}: {concise description conveying purpose/intent}

Co-Authored-By: Claude Opus 4.6 <noreply@anthropic.com>
```

### Commit Types

| Type | Description | Example |
|------|-------------|---------|
| `feat` | New feature | `feat: Add user authentication feature` |
| `fix` | Bug fix | `fix: Fix session timeout on login` |
| `refactor` | Refactoring | `refactor: Improve error handling in API layer` |
| `update` | Improvement to existing feature | `update: Improve user settings UI` |
| `docs` | Documentation | `docs: Add setup guide to README` |
| `test` | Tests | `test: Add authentication tests` |
| `chore` | Build/config | `chore: Update dependencies` |

### Message Guidelines

1. **First line**: Target 50 chars (max 70)
2. **Language**: Follow project requirements (check `.claude/rules/commit-rules.md`)
3. **Focus**: Convey "why" not just "what"
4. **Style**: Use clear action verbs
5. **Scope**: No technical details in title

## Sensitive File Detection

### Detected Patterns

**Environment files**:
- `.env`, `.env.*`

**Credentials**:
- `*credentials*`, `*secret*`, `*password*`
- `config/credentials.yml.enc`, `config/secrets.yml`

**Keys**:
- `*.key`, `*.pem`, `*.p12`, `*.pfx`
- `id_rsa`, `id_rsa.pub`

**Database config**:
- `config/database.yml`, `database.yml`

**Other**:
- `.npmrc`, `.pypirc`, `.dockercfg`, `.docker/config.json`

### Detection Behavior

1. Display warning
2. Auto-exclude from commit
3. Continue with other files

## Worktree Concepts

### What is a Worktree?

Worktrees allow working with different branches of the same repository in separate directories simultaneously.

### Benefits

1. **Parallel work**: Develop multiple features concurrently
2. **Emergency response**: Fix urgent bugs without disrupting current work
3. **Review handling**: Address review comments while continuing main work

### Directory Structure

```
/home/user/work/
├── nab-agents/              # Main worktree (main branch)
├── nab-agents-add-feature/  # Worktree 1 (add-feature branch)
└── nab-agents-fix-bug/      # Worktree 2 (fix-bug branch)
```

### Path Naming Convention

```
{parent_dir}/{repo_name}-{branch_name}
```

### Lifecycle

1. **Create**: `/git worktree-create` - Creates new branch from main and worktree at specified path
2. **Work**: Normal Git operations (`/git commit`, etc.)
3. **Delete**: `/git worktree-delete` - Removes worktree directory and optionally branch

### Limitations

1. Cannot use same branch in multiple worktrees
2. Disk space: Files duplicated for each worktree
3. `.git` directory: Shared with main repository

## Best Practices

### Branch Management

1. **Consistent naming**: Use unified prefixes across team
2. **Regular cleanup**: Delete merged branches periodically
3. **Branch from main**: Always create working branches from main
4. **Small branches**: One purpose per branch

### Commit Management

1. **One purpose per commit**: Don't mix multiple purposes
2. **Commit frequently**: At each milestone
3. **Always push**: Push immediately after commit
4. **Exclude sensitive info**: Add to `.gitignore`

### Worktree Management

1. **Clear purpose**: Define why worktree is needed
2. **Delete when done**: Don't leave unused worktrees
3. **Consider disk space**: Large projects consume significant space
4. **Clean up branches**: Remove branches when deleting worktrees

### Team Development

1. **Shared strategy**: Align on branch naming and workflow
2. **Quality messages**: Write commit messages others can understand
3. **Self-review**: Check diff before creating PRs
4. **Merge regularly**: Don't let branches diverge for too long

### Safety

1. **Avoid force operations**: Never use `-D`, `--force` (skill doesn't use them either)
2. **Backup important changes**: Create backup branches before major changes
3. **Run tests**: Execute related tests before committing
4. **Incremental changes**: Split large changes into multiple commits
