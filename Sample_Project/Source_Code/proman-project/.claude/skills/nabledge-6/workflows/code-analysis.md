# Code Analysis Workflow

This workflow analyzes existing code, traces dependencies, and generates structured documentation to help understand the codebase.

## Table of Contents

- [Overview](#overview)
- [Process flow](#process-flow)
  - [Step 1: Identify target code](#step-1-identify-target-code)
  - [Step 2: Analyze dependencies](#step-2-analyze-dependencies)
  - [Step 3: Decompose components](#step-3-decompose-components)
  - [Step 4: Search Nablarch knowledge](#step-4-search-nablarch-knowledge)
  - [Step 5: Generate documentation](#step-5-generate-documentation)
  - [Step 6: Output file](#step-6-output-file)
- [Output template](#output-template)
- [Error handling](#error-handling)
- [Best practices](#best-practices)
- [Example execution](#example-execution)

## Overview

**Who executes**: Claude Code (you)

**Purpose**: Help users understand existing code by:
1. Identifying target code scope
2. Tracing dependencies
3. Decomposing into components
4. Searching relevant Nablarch knowledge
5. Generating comprehensive documentation

**Input**: User's request (target code specification)

**Output**: Structured documentation file (Markdown + Mermaid diagrams)

**Tools you will use**:
- Read tool: Read source files
- Grep tool: Search for class usages and dependencies
- Glob tool: Find related files
- Bash with jq: Execute keyword-search workflow for Nablarch knowledge
- Write tool: Generate documentation file

**Expected tool calls**: 30-50 calls (depending on code complexity)

**Expected output**: 1 documentation file (~3,000-10,000 tokens)

## Process flow

Copy this checklist and track your progress:

```
Code Analysis Progress:
- [ ] Step 0: Record start time
- [ ] Step 1: Identify target code
- [ ] Step 2: Analyze dependencies
- [ ] Step 3: Decompose components
- [ ] Step 4: Search Nablarch knowledge
- [ ] Step 5: Generate documentation
- [ ] Step 6: Output file
```

### Step 0: Record start time

**Tool**: None (internal tracking)

**Input**: None

**Output**: Start timestamp

**Action you must take**:

Record the workflow start time for duration calculation in Step 6.

**Implementation**:
- Note the current timestamp when you begin the workflow
- This will be used to calculate `{{analysis_duration}}` in the output template
- Store internally: `start_time = [current_timestamp]`

**Example**:
- Start time: 2026-02-10 14:28:30

**Your checklist**:
- [ ] Recorded start timestamp

**Proceed to Step 1**.

### Step 1: Identify target code

**Tool**: AskUserQuestion (if needed), Read, Glob

**Input**: User's request with target code specification

**Output**: List of target files with paths

**Action you must take**:

1. **Parse user request** to understand target scope:
   - Specific class (e.g., "LoginAction")
   - Specific feature (e.g., "ログイン機能")
   - Package (e.g., "web.action配下")
   - Multiple components

2. **Ask clarifying questions** if scope is unclear:
   ```
   Use AskUserQuestion to clarify:
   - Which specific files/classes?
   - Which modules (web/batch/common)?
   - How deep should dependency analysis go?
   ```

3. **Find target files**:
   - Use Glob to find matching files: `**/*LoginAction.java`
   - Use Grep if searching by content: `class LoginAction`
   - Record full file paths

**Example**:
- User request: "LoginActionを理解したい"
- Target files found:
  - `proman-web/src/main/java/com/nablarch/example/proman/web/action/LoginAction.java`

**Your checklist**:
- [ ] Understood user's target scope
- [ ] Asked clarifying questions if needed
- [ ] Found all target files with full paths
- [ ] Confirmed target list with user (if ambiguous)

**Proceed to Step 2** with target file list.

### Step 2: Analyze dependencies

**Tool**: Read, Grep, Glob

**Input**: Target files from Step 1

**Output**: Dependency graph (mental model or data structure)

**Action you must take**:

1. **Read target files**:
   ```
   Use Read tool for each target file
   ```

2. **Extract direct dependencies** from imports and usage:
   - `import` statements → External dependencies
   - Field types → Direct class dependencies
   - Method parameters/return types → Interface dependencies
   - Method calls → Behavioral dependencies

3. **Classify dependencies**:
   - **Project code** (proman-*): Trace further
   - **Nablarch framework**: Note for knowledge search
   - **JDK/Jakarta EE**: Note but don't trace
   - **Third-party libraries**: Note but don't trace

4. **Determine trace depth** based on user expectation:
   - Ask user if unclear: "どこまで依存関係を辿りますか？"
   - Default: Trace project code until reaching:
     - Nablarch framework classes
     - Entity classes (data objects)
     - Utility classes with no further dependencies

5. **Build dependency graph** (mental model):
   ```
   LoginAction
   ├─→ LoginForm (Form, validation)
   ├─→ SystemAccountEntity (Entity, data)
   ├─→ UniversalDao (Nablarch, database access)
   └─→ ExecutionContext (Nablarch, request context)
   ```

**Example depth control**:
- User request: "LoginActionの概要を知りたい"
  - Depth: Direct dependencies only (1 level)
- User request: "ログイン機能全体を理解したい"
  - Depth: Trace all project code until reaching framework/entities

**Your checklist**:
- [ ] Read all target files
- [ ] Extracted direct dependencies
- [ ] Classified dependencies (project/framework/library)
- [ ] Determined appropriate trace depth
- [ ] Built dependency graph (mental model)

**Proceed to Step 3** with dependency graph.

### Step 3: Decompose components

**Tool**: None (analysis)

**Input**: Dependency graph from Step 2

**Output**: Component list with categories

**Action you must take**:

1. **Categorize components** by role in architecture:
   - **Action/Controller**: Request handlers (e.g., LoginAction)
   - **Form**: Input validation, data binding (e.g., LoginForm)
   - **Entity**: Database entities (e.g., SystemAccountEntity)
   - **Service/Logic**: Business logic (if exists)
   - **Utility**: Helper classes, common functions
   - **Handler**: Nablarch handlers (e.g., TransactionManagementHandler)
   - **Configuration**: XML, properties files

2. **Identify Nablarch framework components** used:
   - UniversalDao → Database access
   - ValidationUtil → Input validation
   - ExecutionContext → Request context
   - Handler chain → Request processing pipeline

3. **Extract key concepts** for knowledge search:
   - Technical terms: DAO, トランザクション, ハンドラ
   - Operations: 検索, 登録, 更新, バリデーション
   - Patterns: CRUD, pagination, error handling

**Component list format**:
```
Components:
1. Action Layer
   - LoginAction (Controller, request handler)
   → Nablarch: ExecutionContext, HttpResponse

2. Form Layer
   - LoginForm (Input validation, data binding)
   → Nablarch: Bean Validation, Domain validation

3. Entity Layer
   - SystemAccountEntity (Database entity)
   → Nablarch: Table annotation, UniversalDao

4. Nablarch Components (for knowledge search)
   - UniversalDao (database access)
   - Bean Validation (input validation)
   - TransactionManagementHandler (transaction control)
```

**Your checklist**:
- [ ] Categorized all components by role
- [ ] Identified Nablarch framework components
- [ ] Extracted key concepts for knowledge search
- [ ] Built component list with categories

**Proceed to Step 4** with component list.

### Step 4: Search Nablarch knowledge

**Tool**: Read (index.toon), Bash with jq (keyword-search workflow)

**Input**: Nablarch components and key concepts from Step 3

**Output**: Relevant knowledge sections

**Action you must take**:

For each Nablarch component identified in Step 3:

1. **Execute keyword-search workflow**:
   - Follow `workflows/keyword-search.md` process (see SKILL.md Step 2 for details)
   - Use component name + technical terms as keywords
   - Example: For "UniversalDao" → keywords: ["UniversalDao", "DAO", "データベース", "CRUD"]
   - Example: For "Bean Validation" → keywords: ["Bean Validation", "バリデーション", "検証", "入力チェック"]

2. **Execute section-judgement workflow**:
   - Follow `workflows/section-judgement.md` process (see SKILL.md Step 2 for details)
   - Judge relevance of each section
   - Keep only High and Partial relevance sections

3. **Collect knowledge** for documentation:
   - API usage patterns
   - Configuration requirements
   - Code examples
   - Error handling
   - Best practices

**Efficiency note**:
- Collect High-relevance sections only (5-10 sections per component)
- Skip components with no relevant knowledge found
- Don't spend excessive time on Partial-relevance sections

**Your checklist** (for each component):
- [ ] Executed keyword-search workflow (Step 2 from SKILL.md)
- [ ] Executed section-judgement workflow (Step 2 from SKILL.md)
- [ ] Collected relevant knowledge sections
- [ ] Noted key APIs, patterns, configurations

**Proceed to Step 5** with collected knowledge.

### Step 5: Generate documentation

**Tool**: None (content generation)

**Input**:
- Target code (Step 1)
- Dependency graph (Step 2)
- Component list (Step 3)
- Nablarch knowledge (Step 4)

**Output**: Structured documentation content

**Action you must take**:

1. **Build dependency diagram** (Mermaid classDiagram format):
   ```mermaid
   classDiagram
       class LoginAction
       class LoginForm
       class SystemAccountEntity
       class UniversalDao
       class BatchAction {
           <<Nablarch>>
       }

       LoginAction --|> BatchAction : extends
       LoginAction ..> LoginForm : validates
       LoginAction ..> SystemAccountEntity : queries
       LoginAction ..> UniversalDao : uses
   ```

   **Key points for dependency diagram**:
   - Use `classDiagram` syntax (NOT `graph TD`)
   - Show class names only (NO methods/fields)
   - Purpose: Show participants and relationships (high-level overview)
   - Show inheritance with `--|>` (solid arrow with closed head)
   - Show dependencies with `..>` (dashed arrow)
   - Mark framework classes with `<<Nablarch>>` stereotype
   - Keep it simple and focused on relationships

2. **Write overview section**:
   - Purpose of target code
   - High-level architecture
   - Key responsibilities

3. **Write flow description with sequence diagram** (before component details):
   - **MUST include Mermaid sequence diagram**
   - Request/response flow (or batch processing flow)
   - Data transformation steps
   - Error handling branches
   - **Rationale**: Understanding the flow first makes component details easier to comprehend

   **Sequence diagram template**:
   ```mermaid
   sequenceDiagram
       participant User
       participant Action as LoginAction
       participant Form as LoginForm
       participant DB as Database

       User->>Action: HTTP Request
       Action->>Form: validate input
       Form-->>Action: validation result
       alt validation success
           Action->>DB: query
           DB-->>Action: result
           Action-->>User: success response
       else validation error
           Action-->>User: error response
       end
   ```

   **Key points for sequence diagram**:
   - Use `participant` to define actors/components
   - Use `->>` for synchronous calls (solid arrow)
   - Use `-->>` for return values (dashed arrow)
   - Use `alt`/`else` for conditional branches (error handling)
   - Use `loop` for repetitive operations
   - Use `Note over` or `Note right of` to explain complex logic
   - Show processing phases with notes

4. **Write component details** for each component:
   - Component name and role
   - Key methods/fields
   - Dependencies (what it uses)
   - Nablarch knowledge (how it uses framework)
   - File path with relative link

**Important notes**:
- **Include Nablarch knowledge**: Embed relevant API usage, configuration examples, and best practices from knowledge files within flow and component sections
- **Add source references**: Use relative file paths (e.g., `proman-web/src/main/java/.../LoginAction.java`) and line number ranges (e.g., `:42-58`)

**Follow CLAUDE.md guidelines**:
- Keep explanations concise
- Focus on "why" not just "what"
- Use Japanese for descriptions
- Include only necessary information

**Your checklist**:
- [ ] Built Mermaid class diagram (class names only)
- [ ] Built Mermaid sequence diagram (with phases)
- [ ] Wrote overview section
- [ ] Wrote flow description with sequence diagram
- [ ] Wrote component details with links
- [ ] Included relevant Nablarch knowledge (within flow and components)
- [ ] Added source references (relative paths)

**Proceed to Step 6** with documentation content.

### Step 6: Output file

**Tool**: Write

**Input**: Documentation content from Step 5

**Output**: Documentation file

**Action you must take**:

1. **Determine output path**:
   - Default proposal: `work/YYYYMMDD/code-analysis-<target-name>.md`
   - Ask user if they want different location
   - Example: `work/20260210/code-analysis-login-action.md`

2. **Calculate analysis duration**:
   - Calculate elapsed time from Step 0 start time to now
   - Format as Japanese text (e.g., "約2分", "約30秒", "約1分30秒")
   - Examples:
     - 0-59 seconds: "約N秒"
     - 60-119 seconds: "約1分" or "約1分N秒"
     - 120+ seconds: "約N分"

3. **Apply output template**:
   - Template file: `.claude/skills/nabledge-6/assets/code-analysis-template.md`
   - Fill in placeholders with actual content:
     - `{{generation_date}}`: Current date (YYYY-MM-DD format, e.g., "2026-02-10")
     - `{{generation_time}}`: Current time (HH:MM:SS format, e.g., "14:30:15")
     - `{{analysis_duration}}`: Calculated duration text (e.g., "約2分")
     - Other placeholders: See [Output template](#output-template) section

4. **Write file** using Write tool:
   ```
   Use Write tool with:
   - file_path: work/20260210/code-analysis-login-action.md
   - content: Generated documentation
   ```

5. **Inform user**:
   - Show output file path
   - Show brief summary (component count, knowledge sections used, duration)

**Your checklist**:
- [ ] Determined output path (proposed to user)
- [ ] Calculated analysis duration
- [ ] Applied output template with all placeholders
- [ ] Wrote file using Write tool
- [ ] Informed user of completion

**Example duration calculation**:
- Start time: 2026-02-10 14:28:30
- End time: 2026-02-10 14:30:45
- Duration: 135 seconds = 2 minutes 15 seconds
- Duration text: "約2分"

**Done!** Workflow completed.

## Output template

**Template file**: `.claude/skills/nabledge-6/assets/code-analysis-template.md`

**Template guide**: `.claude/skills/nabledge-6/assets/code-analysis-template-guide.md`

The template provides a structured format for the generated documentation. For detailed information about template sections, placeholders, and usage instructions, see the template guide.

### Quick Reference

**Template sections**:
1. Header (date/time, duration, modules)
2. Overview
3. Architecture (class diagram + table)
4. Flow (description + sequence diagram)
5. Components (detailed analysis)
6. Nablarch Framework Usage
7. References

**Key placeholders**:
- `{{generation_date}}`, `{{generation_time}}`, `{{analysis_duration}}`
- `{{dependency_graph}}` (classDiagram), `{{flow_sequence_diagram}}` (sequenceDiagram)
- `{{components_details}}`, `{{nablarch_usage}}`

**See template guide for**:
- Complete placeholder list
- Diagram generation tips
- Best practice evaluation criteria
- Link generation rules

## Error handling

**See SKILL.md "Error Handling Policy" section for comprehensive error handling guidelines.**

This workflow follows the general error handling principles defined in SKILL.md. Key scenarios:

- **Target code not found**: Follow "Target code not found" policy in SKILL.md
- **Dependency analysis too complex**: Follow "Dependency analysis too complex" policy in SKILL.md
- **Output file already exists**: Follow "Output file already exists" policy in SKILL.md
- **No Nablarch knowledge found**: Note in documentation, proceed with code analysis only

Always inform user clearly when something goes wrong and provide actionable next steps.

## Best practices

### Scope management

- Start with narrow scope, expand if needed
- Ask user before expanding beyond initial request
- Clearly document scope boundaries in output

### Dependency tracing

- Stop at framework boundaries (Nablarch, Jakarta EE)
- Stop at Entity classes (pure data objects)
- Focus on project-specific code, not library internals

### Knowledge integration

- Only use knowledge from knowledge files
- Cite sources clearly (file + section)
- Don't supplement with external knowledge or assumptions

### Documentation quality

- Keep explanations concise and focused
- Use diagrams for complex relationships
- Provide actionable information, not just descriptions
- Link to sources for deep dives

### File organization

- Use consistent naming: `code-analysis-<target>.md`
- Store in work/YYYYMMDD/ following CLAUDE.md
- Include generation date and target scope in header

## Example execution

**User request**: "LoginActionを理解したい"

### Step 1: Identify target code

- Target: LoginAction.java
- Found: `proman-web/src/main/java/com/nablarch/example/proman/web/action/LoginAction.java`

### Step 2: Analyze dependencies

Read LoginAction.java:
- Imports: LoginForm, SystemAccountEntity, UniversalDao, ExecutionContext
- Dependency graph:
  ```
  LoginAction
  ├─→ LoginForm
  ├─→ SystemAccountEntity
  ├─→ UniversalDao
  └─→ ExecutionContext
  ```

### Step 3: Decompose components

Components identified:
1. LoginAction (Action) - Request handler
2. LoginForm (Form) - Input validation
3. SystemAccountEntity (Entity) - Database entity
4. UniversalDao (Nablarch) - Database access
5. ExecutionContext (Nablarch) - Request context

### Step 4: Search Nablarch knowledge

Execute keyword-search for:
- UniversalDao → universal-dao.json:overview, crud sections
- Bean Validation → data-bind.json:validation section
- ExecutionContext → (not yet created in knowledge base)

### Step 5: Generate documentation

Build documentation with:
- Mermaid dependency diagram
- Component details table
- Flow description
- Nablarch knowledge excerpts

### Step 6: Output file

Output: `work/20260210/code-analysis-login-action.md`

**Summary**:
- 4 components analyzed
- 2 Nablarch knowledge sections integrated
- 1 dependency diagram created

**Done!**

## Tools reference

### Read tool
Read source files and knowledge files.

### Glob tool
Find files by pattern (e.g., `**/*Action.java`).

### Grep tool
Search for class names, imports, method calls.

### Bash with jq
Execute keyword-search workflow steps:
```bash
jq '.index' knowledge/features/libraries/universal-dao.json
jq '.sections.overview' knowledge/features/libraries/universal-dao.json
```

### Write tool
Write final documentation file.

### AskUserQuestion
Clarify scope, depth, output location.
