# Hooks Best Practices Checklist

> Used by agents to verify quality and security when creating or improving CC hooks.

## Intent

Hooks are shell commands or LLM prompts that run automatically at specific points in Claude Code's lifecycle. While CLAUDE.md instructions are advisory, hooks are deterministic and guaranteed to execute every time. The official documentation states "USE AT YOUR OWN RISK" — security is paramount.

**When to use hooks**: Actions that must happen every time with zero exceptions.
**When NOT to use hooks**: Guidelines that apply situationally → use CLAUDE.md or skills instead.

## Sources

- Hooks reference (docs.anthropic.com)
- Best Practices for Claude Code (code.claude.com) — "Set up hooks" section
- Claude Code sandboxing (anthropic.com/engineering)

---

## Checklist

### 1. Suitability assessment

- [ ] **Is a hook the right mechanism?**: Must this action happen "every time with zero exceptions"? If only sometimes needed, a CLAUDE.md rule is sufficient
  - ✅ Hook-appropriate: Run lint after every file edit, block writes to the migrations/ folder
  - ❌ Not hook-appropriate: "Write tests whenever possible" (advisory rule)

### 2. Event selection

- [ ] **Is the correct event chosen for the purpose?**:
  - `PreToolUse`: Before tool execution (can block) — file write restrictions, dangerous command blocking
  - `PostToolUse`: After tool execution (verify/log) — post-edit lint/format, logging
  - `UserPromptSubmit`: Pre-process user input — context injection, prompt validation
  - `Stop` / `SubagentStop`: On completion — notifications, summary generation
  - `SessionStart`: On session start — environment initialization, context injection
  - `PreCompact`: Before compaction — backup, preservation of critical information
  - `Notification`: On permission request — notification forwarding
- [ ] **Is the matcher set correctly?**:
  - Exact match: `Bash` — Bash tool only
  - Regex: `Edit|Write` — matches either
  - Wildcard: `*` — all tools
  - MCP: `mcp__server__tool` — specific MCP tool

### 3. I/O design

- [ ] **Are exit codes implemented correctly?**:
  - `0`: Success. stdout shown only in transcript mode (exceptions: UserPromptSubmit/SessionStart inject into context)
  - `2`: Blocking error. stderr is automatically fed back to Claude
  - Other: Non-blocking error. stderr shown to user, execution continues
- [ ] **Is structured JSON output formatted correctly (if used)?**: `{ "continue": true, "stopReason": "...", "suppressOutput": true }`
- [ ] **Is stdout/stderr usage correct?**: Claude only sees stderr from exit code 2 (except UserPromptSubmit)

### 4. Security (mandatory)

- [ ] **Validates and sanitizes input**: Does not blindly trust JSON data from stdin
- [ ] **Shell variables are quoted**: Uses `"$VAR"`, never unquoted `$VAR`
- [ ] **Blocks path traversal**: Checks file paths for `..`
- [ ] **Uses absolute paths**: Leverages `$CLAUDE_PROJECT_DIR`, not relative paths
- [ ] **Skips sensitive files**: Does not access `.env`, `.git/`, private keys, etc.

### 5. Execution characteristics

- [ ] **Timeout is configured**: Default is 60 seconds. Adjust with the timeout field for heavy processing
- [ ] **Is idempotent**: No side-effect accumulation when the same hook runs multiple times
- [ ] **No race conditions with parallel execution**: Matching hooks run in parallel. Principle: one hook per tool (when modifying updatedOutput)
- [ ] **Understands session-start snapshot behavior**: Setting changes are not auto-reflected during a session. Requires `/hooks` menu review

### 6. Type selection

- [ ] **Command vs prompt type is chosen appropriately**:
  - `command`: Deterministic processing (lint, file checks, formatting) — when reproducibility is needed
  - `prompt`: LLM-based evaluation (code quality judgment, security review) — when judgment is needed

### 7. Hooks in skill frontmatter (if applicable)

- [ ] **Scoped to the skill's lifecycle**: Automatically cleaned up when the skill ends
- [ ] **Understands Stop → SubagentStop conversion**: Stop hooks are converted to SubagentStop when running in a subagent

---

## Judgment Criteria

- Mandatory (items 1, 4): Must be satisfied for all hooks
- Mandatory (items 2, 3): Directly affects correctness of behavior
- Recommended (items 5, 6, 7): Improves operational quality
