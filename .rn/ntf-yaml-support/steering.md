# Goal

NTF（Nablarch Testing Framework）のAI対応として、既存のExcelテストデータをYAML形式に移行する。
具体的には、`nablarch-testing-converter` を使って `proman-batch` と `climan-project` の Excelテストデータ（`.xlsx`）をYAMLに変換し、`nablarch-testing-yaml` を用いた設定変更のみでサンプルアプリ（proman-project・climan-project）の全テストがパスすることを確認する。

# Acceptance criteria

- `proman-batch` の `ExportProjectsInPeriodActionRequestTest` が、Excelテストデータではなく変換後のYAMLテストデータを使って実行され、パスする
- `climan-project` の `ClientActionTest` が、Excelテストデータではなく変換後のYAMLテストデータを使って実行され、パスする
- テストクラス本体（`.java` ファイル）の変更はなく、設定ファイルと依存関係の変更のみで移行が完結している
- `proman-web` を含む全モジュールの既存テストがパスしている（リグレッションなし）
- 変換済みのYAMLファイルがリポジトリにコミットされている
- `nablarch-testing-yaml` の依存が各モジュールの `pom.xml` に追加されている
- `unit-test.xml` または追加の設定ファイルで `YamlTestDataParser` が使われるよう設定変更されている

# Assumptions

- `nablarch-testing-yaml:1.0.0-SNAPSHOT` および `nablarch-testing-converter:1.0.0-SNAPSHOT` はローカルの Maven リポジトリに既にインストール済み（`mvn install` 済み）
- 変換ツール（`TestDataConverter`）はMojoが未整備のため、プログラム的に呼び出すか、別途変換用コードを実行する必要がある
- YAMLファイルの出力先は `target/generated-test-yaml` ではなく、`src/test/java` 配下（Excelと同じ場所）に置き、`nablarch.test.resource-root` の設定は `src/test/java` を指すようにする（または変換済みYAMLをリポジトリにコミットする方針）
- サンプルアプリのテストはローカルのH2またはPostgreSQLで動作する（既存の `unit-test.xml` が参照するDBに依存）
- 変更対象はこのリポジトリ（`nablarch-system-development-guide`）のみ。`nablarch-testing-yaml` や `nablarch-testing-converter` のソースは変更しない
- `nablarch-testing-converter` の `TestDataConverter.convert()` をスタンドアロンで呼び出すためのミニマルなJavaプログラムを作成するか、Maven exec plugin で実行する

# Rules

- commit and push every change; one completion marker per task
- このリポジトリ（`/home/tie303177/work/kiyotis/nablarch-system-development-guide`）のファイルのみ編集する。他リポジトリは読み取り専用
- テストクラス（`.java`）は変更しない
- ブランチ `ntf-yaml-support` で作業し、全変更をPRに含める
- Mavenのビルドは `Sample_Project/Source_Code/proman-project` または `Sample_Project/Source_Code/climan-project` の配下で実行する

# Tasks

### #1: Excelテストデータ（xlsx）をYAMLに変換してリポジトリに配置する

**Purpose**: `TestDataConverter` を使って2つのExcelテストデータをYAMLに変換し、各テストのソースツリー内に配置する。

**Prerequisites**: none

**Steps**:

- [ ] `nablarch-testing-converter` の `TestDataConverter.convert()` API を確認し、変換実行方法を決定する（Maven exec plugin による一時的な変換プログラム、またはGroovy/Javaスクリプト）
- [ ] `proman-batch` の `ExportProjectsInPeriodActionRequestTest.xlsx` をYAMLに変換する
- [ ] `climan-project` の `ClientActionTest.xlsx` をYAMLに変換する
- [ ] 変換済みのYAMLファイルを各テストクラスと同じディレクトリ（`src/test/java/...`）に配置する
- [ ] 変換済みYAMLファイルをgitに追加する
- [ ] self-check (OK/NG per completion criterion, record in checks/task-1.md)
- [ ] QA expert review (subagent)
- [ ] language expert review (subagent)
- [ ] software-engineering expert review (subagent)
- [ ] user review

**Completion criteria**:

- `proman-batch` の `ExportProjectsInPeriodActionRequestTest.yaml`（またはディレクトリ）が `src/test/java/com/nablarch/example/proman/batch/project/` 配下に存在する
- `climan-project` の `ClientActionTest.yaml`（またはディレクトリ）が `src/test/java/com/nablarch/example/climan/rest/client/` 配下に存在する
- 変換されたYAMLが `ntf-testdata-yaml-schema.json` スキーマに対して有効である（変換ツールが検証済み）
- gitステータスで変換済みYAMLがtracked filesとして存在する

### #2: pom.xml に nablarch-testing-yaml 依存を追加する

**Purpose**: `proman-batch` と `climan-project` の `pom.xml` に `nablarch-testing-yaml` をtest依存として追加する。

**Prerequisites**: none（#1と並行可能だが、#3の前に完了が必要）

**Steps**:

- [ ] `proman-batch/pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT` をtest scopeで追加する
- [ ] `climan-project/pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT` をtest scopeで追加する
- [ ] `mvn dependency:resolve -Dclassifier=test` などで依存が解決できることを確認する
- [ ] self-check (OK/NG per completion criterion, record in checks/task-2.md)
- [ ] QA expert review (subagent)
- [ ] software-engineering expert review (subagent)
- [ ] user review

**Completion criteria**:

- `proman-batch/pom.xml` に `nablarch-testing-yaml` のtest依存が追加されている
- `climan-project/pom.xml` に `nablarch-testing-yaml` のtest依存が追加されている
- `mvn -f proman-batch/pom.xml dependency:resolve` が成功する
- `mvn -f climan-project/pom.xml dependency:resolve` が成功する

### #3: unit-test.xml を更新して YamlTestDataParser を使うよう設定する

**Purpose**: 各プロジェクトのテスト用コンポーネント設定を変更し、`testDataParser` に `YamlTestDataParser` を使うよう切り替える。

**Prerequisites**: #1, #2

**Steps**:

- [ ] `nablarch-testing-yaml` の `unit-test-yaml.xml` と `unit-test-yaml.config` のパターンを確認する
- [ ] `proman-batch/src/test/resources/unit-test.xml`（またはオーバーライドファイル）に `YamlTestDataParser` の設定を追加し、`nablarch.test.resource-root` を変換済みYAMLの配置先に合わせる
- [ ] `climan-project/src/test/resources/unit-test.xml`（またはオーバーライドファイル）に同様の設定を追加する
- [ ] `interpreters` として `yamlInterpreters`（`QuotationTrimmer` を含まない）が使われるよう設定する
- [ ] self-check (OK/NG per completion criterion, record in checks/task-3.md)
- [ ] QA expert review (subagent)
- [ ] language expert review (subagent)
- [ ] software-engineering expert review (subagent)
- [ ] user review

**Completion criteria**:

- `proman-batch/src/test/resources/unit-test.xml`（または関連設定ファイル）に `testDataParser` として `YamlTestDataParser` が定義されている
- `climan-project/src/test/resources/unit-test.xml`（または関連設定ファイル）に同様の定義がある
- `nablarch.test.resource-root` が変換済みYAMLファイルのルートを指している

### #4: 全テストをパスさせる

**Purpose**: `proman-project` および `climan-project` の全テストを実行し、YAML移行後も全テストがパスすることを確認する。

**Prerequisites**: #1, #2, #3

**Steps**:

- [ ] `proman-project` でテストを実行する（`mvn test`）
- [ ] `climan-project` でテストを実行する（`mvn test`）
- [ ] テスト失敗があれば原因を調査して修正する（設定やYAMLの問題に限る）
- [ ] 全テストがパスすることを確認する
- [ ] self-check (OK/NG per completion criterion, record in checks/task-4.md)
- [ ] QA expert review (subagent)
- [ ] user review

**Completion criteria**:

- `proman-project` の `mvn test` が BUILD SUCCESS で終了する
- `climan-project` の `mvn test` が BUILD SUCCESS で終了する
- `ExportProjectsInPeriodActionRequestTest` および `ClientActionTest` がYAMLテストデータを使って実行され、パスしている（テストログで確認）

# Decisions

# State

- **Status**: not suspended
- **Date**: 2026-06-24
- **Last completed**: (none)
- **Next**: #1 ExcelテストデータをYAMLに変換してリポジトリに配置する
- **Notes**: nablarch-testing-converter に CLI/Mojo 未整備。TestDataConverter.convert() を Maven exec plugin 経由で呼び出す方針が有力。
