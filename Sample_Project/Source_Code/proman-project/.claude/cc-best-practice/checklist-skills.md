# Skills Best Practices Checklist

> Used by agents to verify quality when creating or improving CC skills.
> Extended from the official checklist at the end of Skill authoring best practices.

## Intent

The core design principles of Agent Skills are **progressive disclosure** and **conciseness**. The context window is a shared resource — skills compete with everything else (system prompt, conversation history, other skill metadata, user requests). This checklist verifies that a skill functions effectively within these constraints.

## Sources

- Skill authoring best practices (platform.claude.com) — all sections + official checklist at the end
- Equipping agents with Agent Skills (anthropic.com/engineering)
- Best Practices for Claude Code (code.claude.com) — Skills section

---

## Checklist

### 1. Metadata (frontmatter)

- [ ] **Name follows naming conventions**: Lowercase, digits, and hyphens only; max 64 characters; no XML tags; reserved words "anthropic"/"claude" prohibited
- [ ] **Name uses gerund form (recommended)**: `processing-pdfs`, `analyzing-spreadsheets`, etc. Avoids vague names like `helper`, `utils`
- [ ] **Description is written in third person**: Injected into the system prompt, so first/second person is invalid
  - ✅ `Processes Excel files and generates reports`
  - ❌ `I can help you process Excel files`
- [ ] **Description includes both "what it does" and "when to use it"**: This is the selection criterion when choosing from 100+ skills
  - OK: `Extract text and tables from PDF files, fill forms, merge documents. Use when working with PDF files or when the user mentions PDFs, forms, or document extraction.`
- [ ] **Description is under 1024 characters**
- [ ] **disable-model-invocation is set appropriately**: Set to true for workflows with side effects (deploy, PR creation, etc.)

### 2. Progressive disclosure

- [ ] **SKILL.md body is under 500 lines**: Split into separate files if exceeded
- [ ] **Three-tier structure is followed**:
  - Tier 1: name/description → always loaded into system prompt
  - Tier 2: SKILL.md body → loaded when skill is triggered
  - Tier 3: Additional files → loaded only when needed
- [ ] **References stay 1 level deep**: SKILL.md → additional file is OK. Additional file → yet another file is NG (Claude may `head -100` for partial reads)
- [ ] **Mutually exclusive contexts are separated**: Information never needed simultaneously (e.g., finance.md / sales.md / product.md) goes in separate files
- [ ] **Reference files over 100 lines have a table of contents**: Enables Claude to understand the full scope during partial previews

### 3. Content quality

- [ ] **Does not explain things Claude already knows**: "Claude is already very smart. Only add context Claude doesn't already have."
- [ ] **Does not contain time-dependent information**: No "until August 2025, use old API" — use an "old patterns" section instead
- [ ] **Terminology is consistent**: Same term for the same concept throughout
- [ ] **Examples are concrete in input/output format**: Specific examples are more effective than abstract explanations
- [ ] **Does not present too many choices**: Provide one default and an escape hatch for exceptions

### 4. Workflows and feedback loops

- [ ] **Compound tasks are broken into steps**: Numbered steps with explicit ordering
- [ ] **Uses the checklist pattern (for complex tasks)**: Provides a format Claude can copy and track progress against
- [ ] **Includes feedback loops**: Defines "verify → fix → re-verify" cycles (e.g., run validator → fix errors → re-run)
- [ ] **Conditional branches are clear**: "Creating new content? → Creation workflow / Editing existing? → Editing workflow"

### 5. Code and scripts (if applicable)

- [ ] **Scripts handle errors explicitly, not delegating to Claude**: "Solve, don't punt" — explicit error handling
- [ ] **No magic numbers**: All constants have documented rationale ("voodoo constants" avoidance)
- [ ] **Dependencies are listed**: Documented in SKILL.md and verified as available
- [ ] **Script execution intent is clear**: Distinguishes "run this" from "read this as reference"
- [ ] **No Windows paths**: Always use forward slashes (`scripts/helper.py`)
- [ ] **Generates verifiable intermediate outputs**: Plan-validate-execute pattern; validate JSON plan etc. before applying changes

### 6. CC-specific options (if applicable)

- [ ] **`context: fork` is used appropriately**: Set for skills that should run in isolated context. Not appropriate for guideline-only skills
- [ ] **`agent` field is appropriate**: Choose from Explore (read-only), Plan, general-purpose, or custom agents
- [ ] **`allowed-tools` is set (if needed)**: Restricts tools while the skill is active
- [ ] **If hooks are defined in frontmatter, has the hooks checklist also been applied?**

### 7. Evaluation and iteration

- [ ] **Evaluation scenarios were created first**: "Build evaluations BEFORE writing extensive documentation" — minimum 3 test scenarios
- [ ] **Tested with real tasks**: Verified with actual tasks, not just test scenarios
- [ ] **Claude's exploration path was observed**: Checked for unexpected file read order, oversights, excessive dependencies, ignored files
- [ ] **Tested with all target models**: Verified behavioral differences across Haiku / Sonnet / Opus

---

## Judgment Criteria (aligned with official checklist)

### Mandatory (Core quality)
- Description is specific and keyword-rich
- Description includes both "what" and "when"
- SKILL.md body is under 500 lines
- Additional details in separate files (when needed)
- No time-dependent information
- Consistent terminology
- Concrete examples
- References stay 1 level deep
- Appropriate use of progressive disclosure
- Clear workflow steps

### Recommended (Code and scripts)
- Scripts self-solve errors
- Explicit error handling
- No magic numbers
- Dependencies documented and verified
- Script documentation present
- No Windows paths
- Verification steps included
- Feedback loops present

### Testing
- Minimum 3 evaluation scenarios
- Tested with Haiku / Sonnet / Opus
- Tested with real tasks
- Team feedback incorporated (if applicable)
