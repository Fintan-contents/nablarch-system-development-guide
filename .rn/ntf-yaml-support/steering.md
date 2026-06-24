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
- 推測で作業しない。各アプリのREADMEやドキュメントを先に確認してから進める

## ビルド・テスト手順（READMEより）

### 共通
- JDK: OpenJDK 21（`JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64`）
- PostgreSQL 16: `docker start postgres16` で起動（postgres/password）

### proman-project
READMEに従い、以下の順序で実行する（`Sample_Project/Source_Code/proman-project` 配下）：
```
cd proman-project && mvn -N install
cd proman-common && mvn -P gsp clean generate-resources && mvn install
cd proman-web または proman-batch で mvn test
```
※ proman-common の gsp が entity 生成を行うため、proman-batch/web のテスト前に必須

### climan-project
READMEに従い、以下の順序で実行する（`Sample_Project/Source_Code/climan-project` 配下）：
```
mvn -P gsp clean generate-resources  ← entity 生成（com.nablarch.example.climan.entity）
mvn test
```
※ gsp プロファイルがエンティティクラスを生成するため、先に実行が必須

# Tasks

### #1: 現状のビルド・テストで全PASSを確認する

**Purpose**: 移行前のベースラインとして、proman-project と climan-project のビルドとテストが全てパスすることを確認する。

**Prerequisites**: none

**Steps**:

- [x] `proman-project` で `mvn test` を実行する
- [x] `climan-project` で `mvn test` を実行する
- [x] 全テストがパスすることを確認する（失敗があれば報告して止まる）
- [x] self-check (OK/NG per completion criterion, record in checks/task-1.md)
- [x] QA expert review (subagent)
- [ ] user review

**Completion criteria**:

- `proman-project` の `mvn test` が BUILD SUCCESS で終了する
- `climan-project` の `mvn test` が BUILD SUCCESS で終了する

### #2: pom.xml に nablarch-testing-yaml / nablarch-testing-converter の依存を追加する

**Purpose**: `proman-batch` と `climan-project` の `pom.xml` に `nablarch-testing-yaml` と `nablarch-testing-converter` をtest依存として追加する。

**Prerequisites**: #1

**Steps**:

- [ ] `proman-batch/pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT` と `nablarch-testing-converter:1.0.0-SNAPSHOT` をtest scopeで追加する
- [ ] `climan-project/pom.xml` に同様に追加する
- [ ] `mvn dependency:resolve` で依存が解決できることを確認する
- [ ] self-check (OK/NG per completion criterion, record in checks/task-2.md)
- [ ] QA expert review (subagent)
- [ ] software-engineering expert review (subagent)
- [ ] user review

**Completion criteria**:

- `proman-batch/pom.xml` に `nablarch-testing-yaml` と `nablarch-testing-converter` のtest依存が追加されている
- `climan-project/pom.xml` に同様の依存が追加されている
- 各モジュールの `mvn dependency:resolve` が成功する

### #3: ExcelテストデータをYAMLに変換してリポジトリに配置する

**Purpose**: `TestDataConverter` を使って2つのExcelテストデータをYAMLに変換し、各テストのソースツリー内に配置する。

**Prerequisites**: #2

**Steps**:

- [ ] `proman-batch` の `ExportProjectsInPeriodActionRequestTest.xlsx` をYAMLに変換する
- [ ] `climan-project` の `ClientActionTest.xlsx` をYAMLに変換する
- [ ] 変換済みのYAMLファイルを各テストクラスと同じディレクトリ（`src/test/java/...`）に配置する
- [ ] 変換済みYAMLファイルをgitに追加してコミットする
- [ ] self-check (OK/NG per completion criterion, record in checks/task-3.md)
- [ ] QA expert review (subagent)
- [ ] user review

**Completion criteria**:

- `proman-batch` の変換済みYAMLが `src/test/java/com/nablarch/example/proman/batch/project/` 配下に存在する
- `climan-project` の変換済みYAMLが `src/test/java/com/nablarch/example/climan/rest/client/` 配下に存在する
- 変換されたYAMLがスキーマに対して有効である（変換ツールが検証済み）
- 変換済みYAMLがgitでtracked filesとして存在する

### #4: 設定変更してYAMLテストデータで全テストをパスさせる

**Purpose**: `unit-test.xml` 等の設定を変更して `YamlTestDataParser` に切り替え、全テストがパスすることを確認する。失敗があれば原因を調査して報告する。

**Prerequisites**: #3

**Steps**:

- [ ] `proman-batch/src/test/resources/unit-test.xml`（またはオーバーライドファイル）に `YamlTestDataParser` の設定を追加し、`nablarch.test.resource-root` を変換済みYAMLの配置先に合わせる
- [ ] `climan-project/src/test/resources/unit-test.xml`（または関連設定ファイル）に同様の設定を追加する
- [ ] `interpreters` として `yamlInterpreters`（`QuotationTrimmer` を含まない）が使われるよう設定する
- [ ] `proman-project` で `mvn test` を実行する
- [ ] `climan-project` で `mvn test` を実行する
- [ ] テスト失敗があれば原因を調査して報告する（設定・YAML内容の問題に限り修正する）
- [ ] self-check (OK/NG per completion criterion, record in checks/task-4.md)
- [ ] QA expert review (subagent)
- [ ] language expert review (subagent)
- [ ] software-engineering expert review (subagent)
- [ ] user review

**Completion criteria**:

- `proman-batch/src/test/resources/unit-test.xml`（または関連設定ファイル）に `testDataParser` として `YamlTestDataParser` が定義されている
- `climan-project/src/test/resources/unit-test.xml`（または関連設定ファイル）に同様の定義がある
- `nablarch.test.resource-root` が変換済みYAMLファイルのルートを指している
- `proman-project` の `mvn test` が BUILD SUCCESS で終了する
- `climan-project` の `mvn test` が BUILD SUCCESS で終了する
- `ExportProjectsInPeriodActionRequestTest` および `ClientActionTest` がYAMLテストデータで実行されパスしている（テストログで確認）

# Decisions

# State

- **Status**: paused
- **Date**: 2026-06-24
- **Last completed**: #1 現状のビルド・テストで全PASSを確認する
- **Next**: #2 pom.xml に nablarch-testing-yaml / nablarch-testing-converter の依存を追加する
- **Notes**: タスク #1 完了・PR作成済み（https://github.com/kiyotis/nablarch-system-development-guide/pull/1）。ユーザーレビュー待ち。次は proman-batch/pom.xml と climan-project/pom.xml に nablarch-testing-yaml:1.0.0-SNAPSHOT と nablarch-testing-converter:1.0.0-SNAPSHOT を test scope で追加する。両ライブラリはローカルの Maven リポジトリにインストール済み。ビルド前に docker start postgres16 と JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64 の設定が必要。
