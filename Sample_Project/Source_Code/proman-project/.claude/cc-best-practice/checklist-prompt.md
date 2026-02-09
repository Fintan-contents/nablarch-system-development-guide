# Prompt (Instruction) Best Practices Checklist

> Cross-cutting quality checks applied when writing instruction text within CC skills and hooks.
> Used by agents to self-verify the instructions they author.

## Intent

The instruction text in a SKILL.md body or a hook's prompt field is a prompt to Claude. Claude 4 models have high instruction-following fidelity for explicit directions, but fall back to inference for ambiguous ones. This checklist verifies that instructions align with official best practices: specific, concise, and verifiable.

## Sources

- Claude 4 prompt engineering best practices (docs.anthropic.com)
- Best Practices for Claude Code (code.claude.com) — "Provide specific context" section
- Skill authoring best practices (platform.claude.com) — "Set appropriate degrees of freedom" section

---

## Checklist

### 1. Conciseness (context is a finite resource)

- [ ] **Does not explain things Claude already knows**: General knowledge like "what a PDF is" is unnecessary. Official: "Only add context Claude doesn't already have"
- [ ] **Each paragraph justifies its token cost**: Passes the "Does this paragraph justify its token cost?" test
- [ ] **Eliminates redundant introductions and explanations**: Not using 150 tokens for something expressible in 50 (see official good/bad examples)

### 2. Specificity

- [ ] **Specifies "what," "where," and "how" explicitly**:
  - NG: `add tests for foo.py`
  - OK: `write a test for foo.py covering the edge case where the user is logged out. avoid mocks.`
- [ ] **References existing patterns**: `look at how existing widgets are implemented. HotDogWidget.php is a good example.`
- [ ] **Identifies target files or directories**: Uses @file references or concrete paths

### 3. Positive-form instructions

- [ ] **Written as "do X" rather than "don't do Y"**:
  - NG: `Do not use markdown in your response`
  - OK: `Your response should be composed of smoothly flowing prose paragraphs.`

### 4. Motivation and context

- [ ] **Includes the reason why the instruction matters**: Claude 4 generalizes better when given rationale
  - NG: `NEVER use ellipses`
  - OK: `Your response will be read aloud by a text-to-speech engine, so never use ellipses since the engine won't know how to pronounce them.`

### 5. Degree of freedom

- [ ] **Sets freedom level appropriate to the task**:
  - High freedom (text instructions): Multiple approaches valid, context-dependent → general guidelines
  - Medium freedom (pseudocode or parameterized scripts): Recommended pattern exists, some variation acceptable
  - Low freedom (concrete scripts, minimal parameters): Fragile operations, consistency critical, specific procedures required
- [ ] **Applies the official analogy**: Narrow bridge (low freedom) vs open field (high freedom)

### 6. Verification criteria

- [ ] **Defines success criteria**: Test execution, lint, typecheck, screenshot comparison, etc.
  - Official: "Include tests, screenshots, or expected outputs so Claude can check itself. This is the single highest-leverage thing you can do."
- [ ] **Directs toward root cause resolution**: Asks for root-cause fixes, not error suppression

### 7. Workflow structure (for compound tasks)

- [ ] **Steps are numbered and structured**: Multi-step tasks have explicit ordering
- [ ] **Uses the checklist pattern**: For complex workflows, provides a checklist Claude can copy and track progress against
- [ ] **Includes feedback loops**: Defines "verify → fix → re-verify" cycles

### 8. Terminology consistency

- [ ] **Uses the same term for the same concept throughout**:
  - ✅ Consistent: Always uses "API endpoint"
  - ❌ Inconsistent: Mixes "API endpoint", "URL", "API route", "path"

---

## Judgment Criteria

- Mandatory (items 1–4): Must be satisfied for all instruction text
- Recommended (items 5–7): Apply based on task complexity
- Quality (item 8): Ensure consistency
