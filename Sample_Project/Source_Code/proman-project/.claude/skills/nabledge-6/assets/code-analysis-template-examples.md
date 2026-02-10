# Code Analysis Template Examples

This document contains detailed examples for generating code analysis documentation. Reference these examples when following the code-analysis workflow.

## Component Summary Table

**Purpose**: Provide a high-level overview of all components in the Architecture section.

**Format**: Markdown table with 4 columns

**Columns**:
- **Component**: Component name (class, file, or module)
- **Role**: Brief description of purpose (5-10 words)
- **Type**: Component type (Action, Form, Entity, DTO, SQL, Handler, Util, etc.)
- **Dependencies**: Key dependencies (comma-separated)

**Example 1: Batch Action**

```markdown
| Component | Role | Type | Dependencies |
|-----------|------|------|--------------|
| ExportProjectsInPeriodAction | 期間内プロジェクト一覧CSV出力 | Action | DatabaseRecordReader, ObjectMapper, FilePathSetting |
| ProjectDto | プロジェクト情報データ転送オブジェクト | DTO | なし |
| FIND_PROJECT_IN_PERIOD | 期間内プロジェクト検索クエリ | SQL | なし |
```

**Example 2: Web Action**

```markdown
| Component | Role | Type | Dependencies |
|-----------|------|------|--------------|
| LoginAction | ログイン認証処理 | Action | LoginForm, SystemAccountEntity, UniversalDao |
| LoginForm | ログイン入力検証 | Form | なし |
| SystemAccountEntity | システムアカウント情報 | Entity | なし |
```

**Tips**:
- Keep Role descriptions concise (avoid full sentences)
- Use Japanese for Role descriptions
- For "なし" (no dependencies), write "なし" instead of leaving blank
- Include all major components (3-10 components typically)

---

## Nablarch Usage with Important Points

**Purpose**: Explain how Nablarch components are used with practical guidance for developers.

**Structure** (for each component):
1. **クラス名**: Full class name
2. **説明**: Brief description (1-2 sentences)
3. **使用方法**: Code example (Java snippet)
4. **重要ポイント**: Critical points with emoji prefixes
5. **このコードでの使い方**: How it's used in analyzed code
6. **詳細**: Link to knowledge base

**Important Points Prefixes**:
- ✅ **Must do**: Critical actions that must be performed
- ⚠️ **Caution**: Gotchas, limitations, common mistakes
- 💡 **Benefit**: Why use this, advantages, design philosophy
- 🎯 **When to use**: Use cases, scenarios, applicability
- ⚡ **Performance**: Performance considerations, optimization tips

**Example 1: ObjectMapper**

```markdown
### ObjectMapper

**クラス**: `nablarch.common.databind.ObjectMapper`

**説明**: CSVやTSV、固定長データをJava Beansとして扱う機能を提供する

**使用方法**:
\`\`\`java
// 生成
ObjectMapper<ProjectDto> mapper = ObjectMapperFactory.create(ProjectDto.class, outputStream);

// 書き込み
mapper.write(dto);

// クローズ
mapper.close();
\`\`\`

**重要ポイント**:
- ✅ **必ず`close()`を呼ぶ**: バッファをフラッシュし、リソースを解放する（`terminate()`で実施）
- ⚠️ **大量データ処理時**: メモリに全データを保持しないため、大量データでも問題なく処理可能
- ⚠️ **型変換の制限**: `EntityUtil`と同様に、複雑な型変換が必要な項目は個別設定が必要
- 💡 **アノテーション駆動**: `@Csv`, `@CsvFormat`でフォーマットを宣言的に定義できる
- 💡 **保守性の高さ**: フォーマット変更時はアノテーションを変更するだけで対応可能

**このコードでの使い方**:
- `initialize()`でProjectDto用のObjectMapperを生成（Line 25-28）
- `handle()`で各レコードを`mapper.write(dto)`で出力（Line 52）
- `terminate()`で`mapper.close()`してリソース解放（Line 60）

**詳細**: [データバインド](../../.claude/skills/nabledge-6/docs/features/libraries/data-bind.md)
```

**Example 2: BusinessDateUtil**

```markdown
### BusinessDateUtil

**クラス**: `nablarch.core.date.BusinessDateUtil`

**説明**: システム全体で統一された業務日付を取得する機能を提供する

**使用方法**:
\`\`\`java
// デフォルト区分の業務日付
String bizDate = BusinessDateUtil.getDate();
// → "20260210"（yyyyMMdd形式）

// 区分別の業務日付
String batchDate = BusinessDateUtil.getDate("batch");
\`\`\`

**重要ポイント**:
- 💡 **システム横断の日付統一**: `System.currentTimeMillis()`や`LocalDate.now()`ではなく、これを使うことでバッチ処理と画面処理で同じ業務日付を共有できる
- ✅ **必ずDatabaseRecordReaderのパラメータに変換**: 取得した文字列は`java.sql.Date`に変換してSQLパラメータに設定する
- 🎯 **いつ使うか**: 日付ベースの検索条件、レポート生成、ファイル名の日付部分など
- ⚠️ **設定が必要**: システムリポジトリに業務日付テーブルまたは固定値を設定する必要がある

**このコードでの使い方**:
- `createReader()`で業務日付を取得（Line 38）
- `java.sql.Date`に変換してSQLパラメータに設定（Line 42-43）
- プロジェクトの開始日・終了日との比較条件として使用

**詳細**: [業務日付管理](../../.claude/skills/nabledge-6/docs/features/libraries/business-date.md)
```

**Tips**:
- Include 3-5 important points per component
- Focus on practical, actionable information
- Use emoji prefixes consistently
- Line references should be approximate (e.g., Line 25-28)

---

## File Links with Line References

**Purpose**: Link to source files with specific line number ranges for easy navigation.

**Format**: `[FileName.java:StartLine-EndLine](relative/path/to/FileName.java)`

**Path calculation**:
- Output location: `work/20260210/code-analysis-xxx.md`
- Source location: `proman-batch/src/main/java/.../Action.java`
- Relative path: `../../proman-batch/src/main/java/.../Action.java`

**Example 1: Component Details**

```markdown
### 1. ExportProjectsInPeriodAction

**File**: [ExportProjectsInPeriodAction.java:15-120](../../proman-batch/src/main/java/com/nablarch/example/proman/batch/project/ExportProjectsInPeriodAction.java)

**Role**: 期間内プロジェクト一覧出力の都度起動バッチアクションクラス

**Key Methods**:
- `initialize()` [:20-28] - ファイル出力先の準備とObjectMapper生成
- `createReader()` [:30-42] - DatabaseRecordReaderを生成してSQLを設定
- `handle()` [:44-58] - 1レコードをProjectDtoに変換してCSV出力
- `terminate()` [:60-65] - ObjectMapperをクローズ
```

**Example 2: Multiple Files**

```markdown
### 2. ProjectDto

**File**: [ProjectDto.java:1-85](../../proman-batch/src/main/java/com/nablarch/example/proman/batch/project/ProjectDto.java)

**Role**: 期間内プロジェクト一覧出力用のデータ転送オブジェクト

**Annotations**:
- `@Csv` [:10] - CSV出力項目順序とヘッダー定義
- `@CsvFormat` [:11-15] - CSV詳細設定（区切り文字、改行コード、文字コード）
```

**Tips**:
- Use `:StartLine-EndLine` for class/method ranges
- Use `[:LineNumber]` for single-line annotations
- Line numbers should be accurate (check with Read tool)
- Relative paths must be correct (use `../../` to go up from work/YYYYMMDD/)

---

## Source Files Links

**Purpose**: List all source files analyzed with descriptions.

**Format**: Bullet list with links and brief descriptions

**Example**:

```markdown
### Source Files

- [ExportProjectsInPeriodAction.java](../../proman-batch/src/main/java/com/nablarch/example/proman/batch/project/ExportProjectsInPeriodAction.java) - 期間内プロジェクト一覧出力バッチアクション
- [ProjectDto.java](../../proman-batch/src/main/java/com/nablarch/example/proman/batch/project/ProjectDto.java) - CSV出力用データ転送オブジェクト
- [ExportProjectsInPeriodAction.sql](../../proman-batch/src/main/resources/com/nablarch/example/proman/batch/project/ExportProjectsInPeriodAction.sql) - 期間内プロジェクト検索SQL
```

**Tips**:
- Include all major source files (typically 3-10 files)
- Use relative paths from output location
- Keep descriptions brief (5-10 words)
- Order by importance (Action → DTO → SQL → Config)

---

## Knowledge Base Links

**Purpose**: Link to relevant knowledge base files in `.claude/skills/nabledge-6/docs` for detailed information.

**Format**: Bullet list with links and descriptions of what's covered

**Path structure**:
- Output: `work/20260210/code-analysis-xxx.md`
- Knowledge base: `.claude/skills/nabledge-6/docs/features/libraries/xxx.md`
- Relative path: `../../.claude/skills/nabledge-6/docs/features/libraries/xxx.md`

**Example**:

```markdown
### Knowledge Base (Nabledge-6)

- [Nablarchバッチ処理](../../.claude/skills/nabledge-6/docs/features/processing/nablarch-batch.md) - BatchActionの詳細、DB to FILEパターン、都度起動バッチ
- [データバインド](../../.claude/skills/nabledge-6/docs/features/libraries/data-bind.md) - ObjectMapperの詳細仕様、@Csvアノテーション、フォーマット設定
- [業務日付管理](../../.claude/skills/nabledge-6/docs/features/libraries/business-date.md) - BusinessDateUtilの使い方、区分管理、テーブル設定
- [ファイルパス管理](../../.claude/skills/nabledge-6/docs/features/libraries/file-path-management.md) - FilePathSettingの設定方法、論理名と物理パスのマッピング
- [UniversalDao](../../.claude/skills/nabledge-6/docs/features/libraries/universal-dao.md) - データベースアクセスAPI、CRUD操作、トランザクション管理
```

**Tips**:
- Include 3-8 knowledge base links
- Describe what topics are covered in each file
- Use relative paths from output location
- Order by relevance to analyzed code

---

## Official Documentation Links

**Purpose**: Link to official Nablarch documentation for authoritative reference.

**Format**: Bullet list with absolute URLs

**Example**:

```markdown
### Official Documentation

- [Nablarchバッチ処理](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/batch/index.html)
- [データバインド](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/data_io/data_bind.html)
- [業務日付管理](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/system_utility/business_date.html)
- [ファイルパス管理](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/file_path_management.html)
- [UniversalDao](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/database/universal_dao.html)
```

**Tips**:
- Include 3-8 official documentation links
- Use full URLs (https://nablarch.github.io/docs/LATEST/doc/...)
- Match the topics covered in Knowledge Base Links
- Order by relevance to analyzed code
