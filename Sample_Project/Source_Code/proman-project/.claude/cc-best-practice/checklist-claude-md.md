# CLAUDE.md Best Practices Checklist

> Used by agents to verify quality when creating or improving CLAUDE.md files.

## Intent

CLAUDE.md is automatically loaded into context at the start of every session. Since it affects all sessions, it should contain **only information that cannot be inferred from code**, written **concisely**. Official warning: "Bloated CLAUDE.md files cause Claude to ignore your actual instructions!"

Treat CLAUDE.md like code: review it when things go wrong, prune it regularly, and test changes by observing whether Claude's behavior actually shifts.

## Sources

- Best Practices for Claude Code (code.claude.com) — "Write an effective CLAUDE.md" section
- Best practices for agentic coding (anthropic.com/engineering) — sections 1a–1b

---

## Checklist

### 1. Content (aligned with the official ✅Include / ❌Exclude table)

- [ ] **Includes what should be included**:
  - ✅ Bash commands Claude can't guess
  - ✅ Code style rules that differ from defaults
  - ✅ Testing instructions and preferred test runners
  - ✅ Repository etiquette (branch naming, PR conventions)
  - ✅ Architectural decisions specific to the project
  - ✅ Developer environment quirks (required env vars, etc.)
  - ✅ Common gotchas or non-obvious behaviors
- [ ] **Excludes what should be excluded**:
  - ❌ Anything Claude can figure out by reading code
  - ❌ Standard language conventions Claude already knows
  - ❌ Detailed API documentation (link to docs instead)
  - ❌ Information that changes frequently
  - ❌ Long explanations or tutorials
  - ❌ File-by-file descriptions of the codebase
  - ❌ Self-evident practices like "write clean code"

### 2. Conciseness test

- [ ] **Applied the "Would removing this cause Claude to make mistakes?" test to each line**: If no, that line is unnecessary
- [ ] **Moved task-specific content to skills**: Domain knowledge and specialized workflows belong in skills, not CLAUDE.md
- [ ] **No signs of instruction being ignored**: If CLAUDE.md rules are being ignored, the file is likely too long and rules are getting buried

### 3. Instruction effectiveness

- [ ] **Important instructions have emphasis markers**: "IMPORTANT", "YOU MUST", etc. to improve adherence
- [ ] **Claude does not repeatedly ask questions answered in CLAUDE.md**: If it does, phrasing may be ambiguous
- [ ] **Claude does not act against CLAUDE.md instructions**: If it does, the file is too long or the instruction is too weak
- [ ] **Identified rules that should become hooks**: "If Claude already does something correctly without the instruction, delete it. If Claude keeps ignoring a rule, convert it to a hook."

### 4. Placement strategy

- [ ] **Placement matches the intended purpose**:
  - `~/.claude/CLAUDE.md`: Personal settings across all sessions
  - `./CLAUDE.md`: Project root (recommended: commit to git)
  - `./CLAUDE.local.md`: Local only (.gitignore it)
  - Parent directories: For monorepos with hierarchical root + root/foo structure
  - Child directories: Claude loads on demand when working in those directories
- [ ] **Uses @import syntax for external file references (if needed)**: `@README.md`, `@docs/git-instructions.md`, etc.
- [ ] **Committed to git for team sharing**

### 5. Continuous improvement

- [ ] **Started from a /init-generated baseline**: Leverages automatic detection of build systems, test frameworks, and code patterns
- [ ] **Uses the `#` key for immediate additions**: Reflects rules noticed during coding immediately
- [ ] **Reviews CLAUDE.md when problems occur**: "Treat CLAUDE.md like code: review it when things go wrong"
- [ ] **Prunes regularly**: Removes obsolete rules, consolidates duplicates

---

## Judgment Criteria

- Mandatory (items 1, 2): Fundamental CLAUDE.md quality. Aligned with the official include/exclude criteria
- Important (item 3): Instruction effectiveness. Verified through Claude's actual behavior
- Recommended (items 4, 5): Placement and operational optimization
