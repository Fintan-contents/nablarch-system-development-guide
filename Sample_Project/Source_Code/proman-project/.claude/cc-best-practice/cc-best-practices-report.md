# Claude Code Feature Best Practices — Checklist Investigation Report

## Purpose

Create checklists that the CIYA cc-skill can use for self-checking when creating or improving the following Claude Code components, based on official best practices:

- **CLAUDE.md** — creation and improvement
- **CC Skills** — creation and improvement (git operations, gh operations, etc.)
- **CC Hooks** — creation and improvement

The consumer is an agent. Humans only provide feedback via PR reviews.
The prompt checklist applies cross-cuttingly when writing instruction text within skills and hooks.

## Sources Investigated

| # | Source | Type | Retrieval |
|---|--------|------|-----------|
| 1 | Skill authoring best practices (platform.claude.com) | Official docs | Full web_fetch |
| 2 | Best Practices for Claude Code (code.claude.com) | Official docs | User-provided full text |
| 3 | Best practices for agentic coding (anthropic.com/engineering) | Official blog | Full web_fetch |
| 4 | Equipping agents with Agent Skills (anthropic.com/engineering) | Official blog | Full web_fetch |
| 5 | Effective context engineering (anthropic.com/engineering) | Official blog | Extracted from search |
| 6 | Claude 4 prompt engineering best practices (docs.anthropic.com) | Official docs | Extracted from search |
| 7 | Hooks reference (docs.anthropic.com) | Official docs | Extracted from search |
| 8 | Subagents documentation (docs.anthropic.com) | Official docs | Extracted from search |
| 9 | How Anthropic teams use Claude Code (PDF) | Official case study | Extracted from search |

## Results: Checklists

| Category | File | Summary |
|----------|------|---------|
| Prompts (cross-cutting) | [checklist-prompt.md](./checklist-prompt.md) | Instruction quality checks for skill/hook content |
| Skills | [checklist-skills.md](./checklist-skills.md) | SKILL.md structure, progressive disclosure, workflows, evaluation |
| Hooks | [checklist-hooks.md](./checklist-hooks.md) | Event selection, I/O, security, idempotency |
| CLAUDE.md | [checklist-claude-md.md](./checklist-claude-md.md) | Conciseness, universality, placement strategy, continuous improvement |

## Checklist Evaluation

| Category | Fit | Rationale |
|----------|-----|-----------|
| Prompts | ★★★★☆ | Some subjectivity remains in judging "specific enough." Mitigated with OK/NG examples |
| Skills | ★★★★★ | Official checklist exists at the end of Source 1; extended from that base. Ideal for structural verification |
| Hooks | ★★★★★ | JSON structure, exit codes, and security rules are well-defined and machine-verifiable |
| CLAUDE.md | ★★★☆☆ | Judging "concise enough" is hard to automate. Mitigated with quantitative criteria and the ✅❌ table |

## Creation Notes per Checklist

### Prompts

- **Input**: Source 2 (CC official best practices), Source 6 (Claude 4 prompt best practices)
- **Decisions**: Source 2 states "context window is the most important resource to manage" → oriented toward balancing conciseness with specificity. Incorporated Claude 4-specific traits: improved instruction-following, think/ultrathink triggers, positive-form instructions
- **Approach**: Structured for agents checking the quality of instructions they write inside skills/hooks. Excluded human-facing prompt examples
- **Evaluation**: The official Before/After tables mapped directly to judgment criteria
- **Additions**: Added "degree of freedom" concept (high/medium/low) from Source 1

### Skills

- **Input**: Source 1 (Skill authoring best practices, full text), Source 4 (Skills engineering blog), Source 2 (CC best practices, skills section)
- **Decisions**: Source 1 is highly detailed and includes an official checklist at the end. Used it as the base, integrating Source 4's "progressive disclosure" principle and Source 2's "CLAUDE.md vs skills" distinction
- **Approach**: Followed the official checklist's 3 categories (Core quality / Code and scripts / Testing), restructured for the cc-skill creation context. Reflected specific official guidelines: name/description design, 500-line limit, 1-level reference depth, feedback loops
- **Evaluation**: Highest coverage thanks to the existing official checklist. Also incorporated the "start with evaluations" development flow
- **Additions**: Added CC-specific frontmatter options like `context: fork`

### Hooks

- **Input**: Source 7 (Hooks reference), Source 2 (CC best practices, hooks section)
- **Decisions**: Source 2 distinguishes "CLAUDE.md instructions are advisory; hooks are deterministic and guaranteed" → centered the design principle on hooks being for actions that must happen every time with zero exceptions. Source 7's "USE AT YOUR OWN RISK" warning and security practices became mandatory items
- **Approach**: Structured around 13 event types with selection criteria, precise exit code implementation, 5 security items, and prompt vs command type selection
- **Evaluation**: The official reference structure mapped almost directly to check criteria
- **Additions**: Added checks for hooks defined within skill frontmatter

### CLAUDE.md

- **Input**: Source 2 (CC best practices, CLAUDE.md section), Source 3 (agentic coding blog, sections 1a-1b)
- **Decisions**: Source 2 is the most practical. Core elements: the ✅Include / ❌Exclude table, the warning "Bloated CLAUDE.md files cause Claude to ignore your actual instructions!", and the "Treat CLAUDE.md like code" principle. Source 3 supplemented with placement strategy, `#` key for immediate additions, and team commits
- **Approach**: Directly adopted the ✅❌ table as judgment criteria. Turned the "Would removing this cause Claude to make mistakes?" test into a checklist item
- **Evaluation**: The official ✅❌ table provides clear judgment criteria. However, "conciseness" ultimately requires operational judgment
- **Additions**: Added @import syntax and /init command usage
