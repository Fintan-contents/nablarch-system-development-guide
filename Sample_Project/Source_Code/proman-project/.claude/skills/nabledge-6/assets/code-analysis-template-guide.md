# Code Analysis Template Guide

This guide explains how to use the code analysis documentation template.

## Template File

**Location**: `.claude/skills/nabledge-6/assets/code-analysis-template.md`

The template provides a structured format for the generated documentation. See the template file for the complete structure with placeholders.

## Template Sections

1. **Header**: Target name, generation date/time, analysis duration, modules
2. **Overview**: Purpose and high-level architecture
3. **Architecture**: Mermaid class diagram + component summary table
4. **Flow**: Processing flow description + Mermaid sequence diagram
5. **Components**: Detailed analysis for each component
   - File location with relative link
   - Role description
   - Key methods with line references
   - Dependencies
   - Nablarch knowledge excerpts
   - Key implementation points
6. **Nablarch Framework Usage**: Framework-specific usage patterns
7. **References**: Links to source files, knowledge files, official docs

## Key Features

- **Mermaid diagrams**: Class diagram (relationships) and sequence diagram (timeline)
- **Relative links**: Links to source files and knowledge files use relative paths
- **Line references**: Method locations include line number ranges (e.g., `L42-58`)
- **Knowledge excerpts**: Relevant Nablarch knowledge quoted from knowledge files
- **Structured format**: Consistent sections across all analyses

## Placeholders

Replace the following placeholders with actual content (using `{{variable}}` format):

### Header Section

- `{{target_name}}`: Name of analyzed code/feature (e.g., "LoginAction", "ログイン機能")
- `{{generation_date}}`: Current date in YYYY-MM-DD format (e.g., "2026-02-10")
- `{{generation_time}}`: Current time in HH:MM:SS format (e.g., "14:30:15")
- `{{analysis_duration}}`: Analysis duration text (e.g., "約2分", "約30秒")
- `{{target_description}}`: One-line description of the target
- `{{modules}}`: Affected modules (e.g., "proman-web, proman-common")

### Overview Section

- `{{overview_content}}`: Purpose and high-level architecture

### Architecture Section

- `{{dependency_graph}}`: Mermaid classDiagram syntax (class names only, show relationships)
- `{{component_summary_table}}`: Markdown table of components

### Flow Section

- `{{flow_content}}`: Request/response flow description text
- `{{flow_sequence_diagram}}`: Mermaid sequenceDiagram syntax (processing flow with timeline)

### Components Section

- `{{components_details}}`: Detailed analysis for each component (numbered sections)

### Nablarch Framework Usage Section

- `{{nablarch_usage}}`: Framework-specific usage patterns

### References Section

- `{{source_files_links}}`: List of source file links
- `{{knowledge_files_links}}`: List of knowledge file links

## Usage Instructions

### Step 1: Read the Template

Read the template file to understand the structure:

```bash
Read: .claude/skills/nabledge-6/assets/code-analysis-template.md
```

### Step 2: Build Content for Each Placeholder

Based on analysis results from workflow Steps 0-5, build content for each placeholder:

1. **Header placeholders**: Use current timestamp and calculated duration
2. **Overview**: Summarize purpose and architecture
3. **Architecture diagrams**: Generate Mermaid classDiagram (class names only)
4. **Flow diagrams**: Generate Mermaid sequenceDiagram (with phases)
5. **Components**: Write detailed analysis with line references
6. **Nablarch usage**: Extract framework usage patterns
7. **References**: Build relative file path links

### Step 3: Replace Placeholders

Replace all `{{variable}}` placeholders with actual content.

### Step 4: Write Output File

Use Write tool to create the documentation file:

```
file_path: work/YYYYMMDD/code-analysis-<target>.md
content: [Generated documentation with placeholders replaced]
```

## Example Placeholder Values

### Example: ExportProjectsInPeriodAction

```
{{target_name}} = "ExportProjectsInPeriodAction"
{{generation_date}} = "2026-02-10"
{{generation_time}} = "14:30:15"
{{analysis_duration}} = "約2分"
{{target_description}} = "期間内プロジェクト一覧出力バッチアクション"
{{modules}} = "proman-batch"
```

## Tips

### Diagram Generation

**Class Diagram**:
- Keep it simple: class names only
- Use `--|>` for inheritance (extends/implements)
- Use `..>` for dependencies (uses/creates)
- Mark framework classes with `<<Nablarch>>` stereotype

**Sequence Diagram**:
- Use `participant` to define actors/components
- Use `->>` for synchronous calls
- Use `-->>` for return values
- Use `alt`/`else` for error handling
- Use `loop` for repetitive operations
- Add `Note` to explain phases

### Link Generation

Use relative paths from the output file location:

```
Output: work/20260210/code-analysis-login-action.md
Source: proman-web/src/main/java/com/nablarch/example/proman/web/action/LoginAction.java
Link: ../../proman-web/src/main/java/com/nablarch/example/proman/web/action/LoginAction.java
```

## See Also

- **Template File**: `assets/code-analysis-template.md`
- **Workflow**: `workflows/code-analysis.md`
- **Skill Definition**: `SKILL.md`
