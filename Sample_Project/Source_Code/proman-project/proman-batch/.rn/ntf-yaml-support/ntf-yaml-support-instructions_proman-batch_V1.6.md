# NTF YAML 対応 指示書（proman-batch）

/home/k10949/workspace/nablarch-system-development-guide リポジトリ内の **Sample_Project/Source_Code/proman-project/proman-batch** モジュールに対して、以下の `#Goal` 以降の内容を実行してください。

※本指示書は汎用テンプレート `ntf-yaml-support-instructions_web_V1.6.md` を、nablarch-system-development-guide のサンプルプロジェクト（proman-project の proman-batch モジュール）向けに書き換えたものです。同リポジトリの climan-project 向け指示書（`ntf-yaml-support-instructions_climan_V1.6.md`）と**同じブランチ・同じ作業ツリー・同じ PostgreSQL**を使うため、**両指示書は並列実行せず、1つずつ順番に実行**してください（どちらを先に実行してもよい）。

以降、特に断りのない相対パスは**対象モジュールのディレクトリ** `Sample_Project/Source_Code/proman-project/proman-batch`（以下「対象プロジェクト」）を基点とする。Git 操作はリポジトリルート `/home/k10949/workspace/nablarch-system-development-guide` で行ってよい。

## 運用ルール

- steering.md / checks/task-N.md は、本指示書の **「## MDテンプレート（章立て・構成）」** に記載したテンプレート（章立て・構成）に従って作成すること（隣接プロジェクトを参照しなくても作成できる）。内容は対象プロジェクト向けに調整する。
- ビルド・テストの実行コマンドはプロジェクトにより異なるため、**作業前に対象プロジェクトの README（`proman-project/proman-batch/README.md`。親の `proman-project/README.md` も参照）と `proman-project/Jenkinsfile` を確認し、正しいビルド・テスト手順を steering.md に記載**したうえで、以降はその手順に従って実行すること（本指示書中の `mvn` コマンドは一例であり、README の記載を優先する）。
- 何かあれば **task毎に各MDファイル（steering.md含む）を更新しながら**進めること。
- user review について: **順調に進んでいる場合は、task 毎の user review を都度実施せず、全ての task 完了後にまとめて実施する**（各 task 完了時は結果を記録しつつ連続して次の task へ進めてよい）。ただし次のいずれかに該当する場合は、その時点で作業を止めてユーザーに確認すること: (1) self-check / 各エキスパートレビューの Verdict が OK でない（NG がある）、(2) テストが FAILURE / BUILD FAILURE となった、(3) その他エラー・想定外の事象が発生した、(4) サンプリング比較で差異が見つかった。これらに該当する場合は修正を試みず（差異時は修正せず）状況を報告して停止すること。
- **本指示書自体も成果物として配置する**: Task #1 で、本指示書ファイルを対象プロジェクトの `.rn/ntf-yaml-support/` 配下へ**同じファイル名でコピー**し（例: `cp /home/k10949/instructions/ntf-yaml-support-instructions_proman-batch_V1.6.md proman-project/proman-batch/.rn/ntf-yaml-support/`）、`cmp` 等で原本と同一内容であることを確認したうえで Task #1 のコミットに含めること。steering.md の Overview には、配置したコピーのパスと原本のパスを記載する。
- チャット（ユーザーへの応答）は日本語で表示すること。
- QA / language / software-engineering の各レビューは、**Agent ツールで `subagent_type: general-purpose`・`model: Opus` を指定してアドホック起動し、該当エキスパートのペルソナを付与**して実施すること（Opus であればバージョンは問わない。Agent ツールの `model` パラメータには `opus` を指定する）。
  - software-engineering レビューでの**重複指摘はMDへの記載不要**（ユーザー指示）。
- check ファイル・steering.md の記載レベルは、本指示書の「## MDテンプレート（章立て・構成）」に従うこと（ユーザー指示）。**全 task の `checks/task-N.md` を同一フォーマット（表形式）で作成する**。具体的には:
  - 見出しは `# task-N Completion Check` とする。
  - **Completion Criteria** は `| Criterion | Self-check | Evidence | QA | QA Evidence |` の5列の表とする。Self-check 列・QA 列には `OK` / `NG` を記載し、QA Evidence には QA エキスパートが独立に確認した事実を記載する。**エビデンス列には日付を記載しない**（事実のみを簡潔に書く）。
  - **QA Expert Review** は `| Aspect | Verdict | Evidence / Improvement |` の表とし、Aspect は `Meaningful tests/verification` / `Edge case coverage` の2行とする。
  - **Expert Reviews (code changes only)** は、コード変更なしのタスクは `N/A — no code changes in this task.` と記載する。コード変更ありは次の小節を設け、それぞれ `| Aspect | Verdict | Evidence / Improvement |` の表とする:
    - **### Language Expert**: `Best practices` / `Codebase style consistency` / `GWT test format`（該当しない観点は Verdict を `N/A`）
    - **### Software-engineering Expert**: `Separation of concerns` / `System integrity` / `Maintainability`
  - **Overall Verdict** は箇条書きで `Self-check` / `QA` / `Language expert` / `Software-engineering expert` / `Ready for user review` を記載する（コード変更なしのタスクは Language / Software-engineering を `N/A`。NG があった場合は `()` で概要を追記）。
  - steering.md の `# Tasks` の章立て・記載レベルは**本指示書の `# Tasks`** に合わせる（Purpose / Prerequisites / Steps / Completion criteria）。Task 毎に内容が異なる点に注意。

## 作成するMDのファイル構成

対象プロジェクト（`Sample_Project/Source_Code/proman-project/proman-batch`）配下に作成する（proman-project 直下ではない）。

```
.rn/ntf-yaml-support/ntf-yaml-support-instructions_proman-batch_V1.6.md（本指示書のコピー。Task #1 で配置）
.rn/ntf-yaml-support/steering.md
.rn/ntf-yaml-support/checks/task-1.md
.rn/ntf-yaml-support/checks/task-2.md
.rn/ntf-yaml-support/checks/task-3.md
.rn/ntf-yaml-support/checks/task-4.md
.rn/ntf-yaml-support/checks/task-5.md
```

チェック等で Script 作成時（Python 等）は、**何のためのスクリプトなのかを steering.md と task-N.md（N は該当 taskNo）に記載**し、作成した Script は `.rn/ntf-yaml-support/scripts/` 配下に格納すること。

## MDテンプレート（章立て・構成）

隣接プロジェクトを参照しなくても、以下の章立て・構成をテンプレートとして steering.md / checks/task-N.md を作成できる。`<...>` は対象プロジェクト向けに埋める。

### steering.md のテンプレート

```markdown
# <プロジェクト名> NTF YAML 対応 steering

## Overview
- 目的・背景（Goal / Acceptance criteria の要約）
- 対象プロジェクト名（リポジトリ内のパスも記載）
- 指示書: `.rn/ntf-yaml-support/<本指示書のファイル名>.md`（原本: `<原本のパス>`）

## Build / Test
- README で確認したビルド・テストの実行手順（プロファイル・generate-resources の要否・テストコマンド・前提となる DB）を明記する

## Tasks
（本指示書の `# Tasks` と同じ章立て・記載レベル。Task 毎に内容を調整する）

### #N: <タスク名>
**Purpose**: <目的>
**Prerequisites**: <前提タスク or none>
**Steps**:
- [ ] <手順>（task 進捗に応じて [x] へ更新する）
**Completion criteria**:
- <完了条件>
```

### checks/task-N.md のテンプレート

```markdown
# task-N Completion Check

## Completion Criteria

| Criterion | Self-check | Evidence | QA | QA Evidence |
|---|---|---|---|---|
| <完了条件> | OK / NG | <事実のみを簡潔に。日付は記載しない> | OK / NG | <QA が独立に確認した事実> |

## QA Expert Review

| Aspect | Verdict | Evidence / Improvement |
|---|---|---|
| Meaningful tests/verification | OK / NG | <所見・改善点> |
| Edge case coverage | OK / NG | <所見・改善点> |

## Expert Reviews (code changes only)

（コード変更なしのタスクは `N/A — no code changes in this task.` と記載し、以下の小節は省略する）

### Language Expert

| Aspect | Verdict | Evidence / Improvement |
|---|---|---|
| Best practices | OK / NG / N/A | <所見・改善点> |
| Codebase style consistency | OK / NG / N/A | <所見・改善点> |
| GWT test format | OK / NG / N/A | <所見・改善点> |

### Software-engineering Expert

| Aspect | Verdict | Evidence / Improvement |
|---|---|---|
| Separation of concerns | OK / NG | <所見・改善点> |
| System integrity | OK / NG | <所見・改善点> |
| Maintainability | OK / NG | <所見・改善点> |

（software-engineering レビューでの重複指摘は記載不要）

## Overall Verdict

- Self-check: OK / NG
- QA: OK / NG
- Language expert: OK / NG / N/A
- Software-engineering expert: OK / NG / N/A
- Ready for user review: Yes / No
（NG があった場合は各行に `()` で概要を追記）
```

#### task固有セクション（該当タスクのみ追記）

Task #3 の `checks/task-3.md` には、上記テンプレートに加えて次のセクションを追記する（サンプリング比較・併存チェックの詳細を記録するため）。

```markdown
## Sampling Comparison（task-3 のみ）
| YAML ファイル | 比較件数 | レコードキー/行 | Excel 値 | YAML 値 | 一致 |
| --- | --- | --- | --- | --- | --- |
| <path> | 3（元データが3件未満なら全件） | <key/行> | <value> | <value> | OK / NG |
（差異があれば原因を調査し、修正せずユーザーへ報告して停止する）

## .xls/.xlsx 併存チェック（該当時のみ）
- 併存の有無: <あり / なし>
- 差分確認結果の保存先: `.rn/ntf-yaml-support/...`（POMなし一時ディレクトリで個別変換した比較結果）
- 未使用側（通常 `.xlsx`）の削除: <変換前に削除済み / 該当なし> → ユーザーへ報告・判断待ち
```

---

# Goal

対象プロジェクト：proman-batch（`nablarch-system-development-guide/Sample_Project/Source_Code/proman-project/proman-batch`。プロジェクト管理システムのバッチモジュール、Nablarch バッチ）

※proman-project の他モジュール（proman-common / proman-web / proman-jmeter）は本指示書の対象外。

NTF（Nablarch Testing Framework）のAI対応として、AIが読み書きできないバイナリ形式のExcelテストデータをYAML形式に移行する。

背景資料: https://github.com/Fintan-contents/nablarch-system-development-guide/pull/211

# Acceptance criteria

- `NAB-682` ブランチ上で（コミット履歴を維持したまま）全作業が行われている（ブランチの新規作成は行わない）
- 作業開始時に、対象プロジェクト配下が `develop` と同一内容であることを確認済みである（`git diff --stat develop -- Sample_Project/Source_Code/proman-project/proman-batch` が空）
- 作業前に、`develop` と同一状態（ベースライン）で対象プロジェクトの既存の全テストがパスすることを確認済みである
- 対象プロジェクトの `pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT` が test スコープの依存として追加されている
- 対象プロジェクトの `pom.xml` に `nablarch-testing-converter:1.0.0-SNAPSHOT` が `<build><plugins>` のビルドプラグイン（plugin直下 `<configuration>` に変換パラメータを設定し、CLIで `convert` ゴールを実行）として追加されている（test依存ではない。converter は Maven プラグインであり、テスト実行時には不要で XLS→YAML 変換時のみ使用するため。nablarch-example-batch / web の設定に準拠。親 `proman-project/pom.xml` ではなく proman-batch の `pom.xml` に追加する）
- テスト実行中に `testDataParser` が参照される全フェーズで YAML が有効になるよう、`YamlTestDataParser` コンポーネント（`testDataParser`）が適切な設定ファイルに追加されている（本モジュールは Nablarch バッチのリクエスト単体テストであり、通常は `src/test/resources/unit-test.xml`。Rules 参照）。`interpreters` プロパティは公式ドキュメント nablarch-document の `development_tools/testing_framework/setup/testdata.html`「テストデータの形式をYAMLに変更する」の設定例に合わせ、コンポーネント内に `dateTimeInterpreter` → `compositeInterpreter` の順で2つのみを直接記載する
- 変換対象の全てのテスト用Excelファイルがそれぞれ対応するYAMLファイルに変換されている（サンプリングでExcelとYAMLの内容が一致することを確認済み）
- 変換後、変換対象だったExcelファイルが全て削除されている
- YAMLテストデータを使った状態で対象プロジェクトの全テストがパスしている
- 対象プロジェクト以外（親 `proman-project/pom.xml`、proman-common / proman-web / proman-jmeter、climan-project、ガイド文書など）のファイルを変更していない

# Assumptions

- リポジトリ `/home/k10949/workspace/nablarch-system-development-guide` は `develop` からクローン済みで、`develop` から作成した `NAB-682` ブランチが存在する（作成済み。リモートへの push はしない）
- `NAB-682` ブランチには、climan-project 向け指示書による YAML 移行コミットが先に積まれている場合がある（その場合も対象プロジェクト配下は `develop` と同一のはずであり、climan 側のコミットには手を触れない）
- `nablarch-testing-yaml:1.0.0-SNAPSHOT` と `nablarch-testing-converter:1.0.0-SNAPSHOT` はローカルMavenリポジトリ（`~/.m2`）に存在する（確認済み）
- テスト実行には **PostgreSQL**（`localhost:5432/postgres`、スキーマ `public`、ユーザー `postgres`/`password`。`src/env/dev/resources/env.config` 参照）を使用する。PostgreSQL はローカルで起動済みである（H2 ではない）
- proman-batch は proman-common（Entity 等）に依存するマルチモジュール構成のため、テスト前に README の手順で次を実施する必要がある: `proman-project` で `mvn -N install`（親 pom のインストール）→ `proman-common` で `mvn -P gsp clean generate-resources`（DB テーブル作成・エンティティ生成）と `mvn install` → `proman-batch` で `mvn test`。これらは proman-common / 親 pom の**ファイルを変更しない**ビルド操作であり、実施してよい
- JDK / Maven / PostgreSQL などビルド・テスト実行環境は整備済みである
- `develop` ブランチは存在する前提とする
- YAMLファイルの出力先は `src/test/java` 配下（Excelと同じ場所）とし、`nablarch.test.resource-root` の設定変更は不要
- 変換ツール（`nablarch-testing-converter`）の実行手順は `/home/k10949/workspace/nablarch-testing-converter/README.md` に従う
- 事前調査時点の変換対象 Excel は次の **1ファイル（.xlsx）**。同名 `.xls` との併存はなし（実施時に改めて所在を確認する）
  - `src/test/java/com/nablarch/example/proman/batch/project/ExportProjectsInPeriodActionRequestTest.xlsx`（シート: `setUpDb` / `testNormalEnd`）
- `GeneralCodingRuleForBatchTest`（ArchUnit）は Excel を使わないが、全テストの一部として実行・パスを確認する
- 参考実装: 同様の YAML 対応済みの `/home/k10949/workspace/nablarch-example-batch`（`NAB-682` ブランチ）の `pom.xml` / `src/test/resources/unit-test.xml`

# Rules

- commit every change; one completion marker per task
- push は行わない（コミットのみ実施し、リモートへの push は最後にユーザーが行う）
- テストクラス（`.java`）は変更しない
- 変更は対象プロジェクト（`Sample_Project/Source_Code/proman-project/proman-batch`）配下に限定する。親 `proman-project/pom.xml`、proman-common / proman-web / proman-jmeter、climan-project、ガイド文書・リポジトリ直下のファイルは変更しない
- `git add` は対象プロジェクトのパスを指定して行う（`git add -A` でリポジトリ全体を追加しない）。コミット前に `git status` で対象外の変更が含まれていないことを確認する
- 既存の `NAB-682` ブランチ上で作業する（ブランチの新規作成はしない。コミット履歴を維持する）
- **過去のコミット履歴は維持する（書き換え禁止）**: `git rebase` / `git commit --amend` / `git reset --hard` / force push などで既存コミットを改変・削除してはならない。作業は常に**新しいコミットとして既存履歴の上に積み上げる**。
- **ベースライン化は「確認」のみ**: `NAB-682` は `develop` から作成済みのため、ベースラインコミット（`develop` 一致ツリーを積むコミット）は作らない。`git diff --stat develop -- Sample_Project/Source_Code/proman-project/proman-batch` が空であることを確認するだけとする。※ `git read-tree -u --reset develop` や `git checkout develop -- .` のようにリポジトリ全体を `develop` に揃える操作は、先行して積まれた climan-project の作業を消すため**禁止**。差分がある場合は修正せずユーザーに報告して停止する
- steering.md は各タスクで更新し、当該タスクのコミットに含める
- ビルド・テストの実行コマンドは**対象プロジェクトの README を優先**する（`mvn -P gsp clean generate-resources && mvn test` の順序は一例）。コマンドは対象プロジェクトのディレクトリで実行する
- テストはExcelファイルが存在しない状態で最終パスを確認する
- YAML変換後はサンプリングでExcelとYAMLの内容を比較してから削除する
- サンプリング比較で差異が見つかった場合は修正せずユーザーに報告して停止する
- `YamlTestDataParser` の `interpreters` は、公式ドキュメント nablarch-document の `development_tools/testing_framework/setup/testdata.html`「テストデータの形式をYAMLに変更する」の設定例に合わせて記載する。
  - `interpreters` プロパティの中に `<list>` を直接記載する（名前付きの `<list name="yamlInterpreters">` を別に定義して参照する書き方はしない）。
  - `interpreters` には `dateTimeInterpreter` と `compositeInterpreter` の2つのみを指定する（他の interpreter は追加しない）。
  - `dbInfo` プロパティに `dbInfo` を指定する。`testDataReader` は指定しない（`YamlTestDataParser` は YAML ファイルを直接読み込むため使用しない）。
  - **定義順は `dateTimeInterpreter` → `compositeInterpreter` とする**（上記設定例、および NTF 標準 `nablarch/test/test-data-interpreter.xml` と同じ前後関係）。`compositeInterpreter` は値に `${...}` を含むとそこで解釈を終え後続へ委譲しないため、先に置くと `${systemTime}` などのシステム時刻記法が `dateTimeInterpreter` に渡らず、未解釈の文字列のまま期待値比較されテストが失敗する。
  - 例（testdata.html の設定例と同じ）:
    ```xml
    <!-- YamlTestDataParser でtestDataParserを上書き -->
    <component name="testDataParser" class="nablarch.test.core.reader.YamlTestDataParser">
      <property name="dbInfo" ref="dbInfo"/>
      <property name="interpreters">
        <list>
          <component-ref name="dateTimeInterpreter"/>
          <component-ref name="compositeInterpreter"/>
        </list>
      </property>
    </component>
    ```
- `YamlTestDataParser` の設定は「どのファイルに書くか」を固定せず、**テスト実行中に `testDataParser` が参照される全フェーズで YAML リーダーが有効になる**ように配置する。
  - セットアップ/検証フェーズ … テスト設定リポジトリ（通常 `unit-test.xml`）
  - アクション実行フェーズ … テスト対象アクションを起動する diConfig（例: `*-boot.xml`）で `SystemRepository` を再初期化する構成の場合のみ考慮が必要。
  - **判定**: 実行中にモック等が `testDataParser` 経由でテストデータを読むか。具体的には「応答電文（`response_*` / sendSync の受信電文）を持つ同期送信系テスト」は該当。（通常の web/batch/REST は非該当で `unit-test.xml` だけで足りる）
  - **本モジュールの事前調査結果**: `ExportProjectsInPeriodActionRequestTest` は `PromanBatchRequestTestSupport`（バッチのリクエスト単体テスト）で、プロジェクト一覧を CSV ファイルに出力するバッチ。メッセージングの応答電文は無いため**非該当と想定**し、`src/test/resources/unit-test.xml` に定義する（参考実装 nablarch-example-batch と同じ。Task #2 で改めて判定し、根拠を checks/task-2.md に記録する）。なお `unit-test.xml` は `batch-boot.xml`（`src/test/resources` 版）を import しているが、非該当のため boot 側への集約は行わない。
  - `unit-test.xml` 内の配置位置は、`nablarch/test/test-data.xml` の import より後、かつ `dbInfo` コンポーネント定義より後とする（proman-batch の `unit-test.xml` では `dbInfo` 定義の後に `nablarch/test/test-data.xml` の import があるため、`nablarch/test/test-transaction.xml` の import の直後（`masterDataRestorer` の前）が目安）。
  - 本番用の設定（`src/main/resources` 配下の `batch-boot.xml` / `resident-batch-boot.xml` 等）には**入れない**（テスト専用設定は `src/test/resources` 側のみ）。

# Tasks

### #1: ベースライン（developと同一状態）を確認し、全PASSを確認する

**Purpose**: `NAB-682` ブランチ上で対象プロジェクト配下が `develop` と同一内容であることを確認し、その状態で対象プロジェクトの全テストがパスすることを確認する。あわせて steering.md と本指示書のコピーを配置する。

**Prerequisites**: none

**Steps**:

- [ ] `NAB-682` ブランチであることを確認する（`git branch --show-current`。新規作成はしない）
- [ ] 作業ツリーに未コミットの変更が無いことを確認する（`git status`。ある場合は報告して停止）
- [ ] `develop` ブランチの存在を確認し、`git diff --stat develop -- Sample_Project/Source_Code/proman-project/proman-batch` が空であることを確認する（差分がある場合は報告して停止。リポジトリ全体を `develop` に揃える操作はしない）
- [ ] 対象プロジェクトの README・Jenkinsfile を確認し、ビルド・テストの実行手順（親 pom の `mvn -N install`、proman-common の `generate-resources` / `install`、プロファイル、PostgreSQL の前提）を把握する
- [ ] PostgreSQL が起動していることを確認する（例: `pg_isready -h localhost -p 5432`）
- [ ] steering.md と scripts の初版を作成する（steering.md には README で確認したビルド・テスト手順を明記する）
- [ ] 本指示書を `.rn/ntf-yaml-support/` 配下へ同じファイル名でコピーし、原本と同一内容であることを確認する（steering.md の Overview にコピーと原本のパスを記載する）
- [ ] README で確認した手順に従い親 pom のインストールとエンティティクラス生成・DB準備を行う（例: `proman-project` で `mvn -N install` → `proman-common` で `mvn -P gsp clean generate-resources` → `mvn install`）。ファイルの変更が発生していないことを `git status` で確認する
- [ ] README で確認した手順でテストを実行し、全テストがパスすることを確認する（例: `proman-batch` で `mvn test`。失敗があれば報告して止まる）。実行テスト件数（Tests run）も記録する
- [ ] 結果を `checks/task-1.md` に記録する（self-check列のみ）
- [ ] 変更ファイル（check ファイル、steering.md、配置した指示書のコピー）をコミットする
- [ ] self-check (OK/NG per completion criterion, record in checks/task-1.md)
- [ ] QA expert review (Agent: general-purpose / Opus)
- [ ] user review

**Completion criteria**:

- `NAB-682` ブランチ上で作業しており、コミット履歴が維持されている（ブランチの再作成・rebase・amend・hard reset を行っていない）
- 作業開始時点で対象プロジェクト配下が `develop` と同一内容である（`git diff --stat develop -- <対象プロジェクト>` が空）
- `mvn test` が全テストパスで終了している（BUILD SUCCESS）
- 本指示書のコピーが `.rn/ntf-yaml-support/` 配下に原本と同一内容で配置され、コミットされている

### #2: 設定変更 — pom.xml と unit-test.xml にYAML対応を追加

**Purpose**: 対象プロジェクトの `pom.xml` に `nablarch-testing-yaml`（test 依存）を追加し、`nablarch-testing-converter` を `<build><plugins>` のビルドプラグイン（plugin直下 `<configuration>` に変換パラメータを設定し、CLIで `convert` ゴールを実行）として追加し、`unit-test.xml` に `YamlTestDataParser` コンポーネントの設定を追加する。`YamlTestDataParser` の設定（`interpreters` を含む）は公式ドキュメント nablarch-document の `development_tools/testing_framework/setup/testdata.html`「テストデータの形式をYAMLに変更する」の設定例に合わせる（pom.xml は参考実装 nablarch-example-batch のYAML対応設定を参考にする）。converter はテスト実行時には不要で XLS→YAML 変換時のみ使う Maven プラグインのため、test 依存ではなくビルドプラグインとして宣言する。設定の配置先は Rules の「`testDataParser` が参照される全フェーズで有効化」方針に従う。

**Prerequisites**: #1

**Steps**:

- [ ] `pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT`（test scope）を追加する（既存の `nablarch-testing*` 依存の近くに置く）
- [ ] `pom.xml` の `<build><plugins>` に `nablarch-testing-converter:1.0.0-SNAPSHOT` をビルドプラグインとして追加する（`<configuration>` に from=xls / to=yaml / input=`${project.basedir}/src/test/java` / output=`${project.basedir}/src/test/java` / overwrite=true を設定。nablarch-example-batch 準拠）。親 `proman-project/pom.xml` の `<pluginManagement>` 等は変更しない。構成が参考実装と異なる場合は既存構成に合わせて配置し、判断根拠を checks/task-2.md に記録する
- [ ] 本テストが「アクションを diConfig で起動し、実行中にモック等が `testDataParser` 経由でテストデータ（応答電文など）を読む」構成かを判定する（応答電文 `response_*` / sendSync の有無で判断。事前調査では非該当想定）
- [ ] 判定結果に応じて `YamlTestDataParser`（`testDataParser` 上書き）を配置する（非該当: `src/test/resources/unit-test.xml`。該当: アクション起動 diConfig 側。Rules 参照）。`interpreters` は testdata.html の設定例に合わせ、`interpreters` プロパティ内の `<list>` に `dateTimeInterpreter` → `compositeInterpreter` の順で2つのみを直接記載する（順番の理由と記載例は Rules 参照）。※ 定義順は `test-data.xml` の import より後・`dbInfo` 定義より後に置き参照解決を担保する
- [ ] `mvn validate` / `mvn dependency:resolve` などで pom とyaml依存が解決できること、`mvn help:describe -Dplugin=com.nablarch.framework:nablarch-testing-converter:1.0.0-SNAPSHOT` で converter プラグインの `convert` ゴールが解決できることを確認する
- [ ] 変更したファイルをコミットする
- [ ] self-check (OK/NG per completion criterion, record in checks/task-2.md)
- [ ] QA expert review (Agent: general-purpose / Opus)
- [ ] language expert review (Agent: general-purpose / Opus)
- [ ] software-engineering expert review (Agent: general-purpose / Opus)
- [ ] user review

**Completion criteria**:

- `pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT` が test スコープで追加されている
- `pom.xml` の `<build><plugins>` に `nablarch-testing-converter:1.0.0-SNAPSHOT` がビルドプラグイン（plugin直下 `<configuration>` に変換パラメータを設定し、CLIで `convert` ゴールを実行）として追加されている
- `testDataParser` コンポーネント（`YamlTestDataParser`）が、`testDataParser` が参照される全フェーズで有効になる設定ファイルに追加されている（配置先は Rules の方針に従う）。`interpreters` は testdata.html の設定例どおり、`interpreters` プロパティ内の `<list>` に `dateTimeInterpreter` → `compositeInterpreter` の順で2つのみが記載されている
- `mvn dependency:resolve` が成功し（yaml依存の解決エラーなし）、converter プラグインの `convert` ゴールが `mvn help:describe` で解決できる

### #3: ExcelテストデータをYAMLに変換

**Purpose**: `nablarch-testing-converter` を使って ExcelのテストデータをYAML形式に変換し、各テストのソースツリー内に配置して、変換内容をサンプリングで確認する。

**Prerequisites**: #2

**Steps**:

- [ ] 対象 Excel ファイルの所在を確認する（`find src -name '*.xls' -o -name '*.xlsx'`。事前調査では `ExportProjectsInPeriodActionRequestTest.xlsx` の1件。件数・ファイルが異なる場合は記録し、存在しない場合は変換対象なしとしてユーザーに報告し停止する）
- [ ] **同名の `.xls` と `.xlsx` が併存していないか確認する**。併存する場合、converter は "same-name Excel books coexist" エラーで変換を拒否する（1テストクラス＝1 Excel ブック）。NTF の `PoiXlsReader` は `.xls` を優先解決するため `.xlsx` はシャドウされた未使用ファイル。内容が異なる可能性があるため、両者をPOMなし一時ディレクトリで個別にYAML変換して差分を確認し（比較結果を `.rn/.../` 配下に保存）、未使用側（通常 `.xlsx`）を**変換前に削除**してからユーザーに報告・判断を仰ぐこと。確認・削除の結果は `checks/task-3.md` の **「## .xls/.xlsx 併存チェック」** セクションに記録する（事前調査では併存なし）。
- [ ] `/home/k10949/workspace/nablarch-testing-converter/README.md` の手順に従い、全ての対象ExcelファイルをYAML変換する（対象プロジェクトのディレクトリで実行する）
- [ ] 変換後、各YAMLファイルについてExcelの内容とサンプリングで比較し、一致を確認する（各ファイル最低3件。元データが3件未満の場合は元データ件数分）。`ExportProjectsInPeriodActionRequestTest` は `setUpDb` と `testNormalEnd` のシートを持つため、`setUpDb` のDBデータに加え、`testNormalEnd` のテストショット定義・期待出力ファイル（CSV）等からもサンプルを取る
- [ ] サンプリング比較結果を `checks/task-3.md` の **「## Sampling Comparison」** セクション（テンプレートの task固有セクション参照）に記録する（差異があれば原因を調査しユーザーに報告し、修正せずに止める）
- [ ] 変換したYAMLファイルをコミットする（Excelはまだ削除しない）
- [ ] self-check (OK/NG per completion criterion, record in checks/task-3.md)
- [ ] QA expert review (Agent: general-purpose / Opus)
- [ ] user review

**Completion criteria**:

- 変換対象の全てのExcelファイルに対応するYAMLファイルが生成されている
- サンプリング比較（各ファイル最低3件、元データが3件未満の場合は元データ件数分）でExcelとYAMLの内容が一致している（差異があれば原因を調査しユーザーに報告し、修正せずに止める）
- 変換後のYAMLファイルがコミットされている

### #4: Excelを削除する

**Purpose**: 変換対象だった Excel を削除してコミットする。

**Prerequisites**: #3

**Steps**:

- [ ] 変換対象となった Excel ファイルを削除する（`git rm`）
- [ ] 削除結果（対象プロジェクト配下の残存Excelが0件であること）を `checks/task-4.md` に記録する（Excelなしでの `mvn clean test` 実行とその結果の確認は Task #5 で行う。Task #4 では記載しない）
- [ ] Excelファイル削除をコミットする
- [ ] self-check (OK/NG per completion criterion, record in checks/task-4.md)
- [ ] QA expert review (Agent: general-purpose / Opus)
- [ ] user review

**Completion criteria**:

- 変換対象だった全 Excel ファイルが削除されている
- 削除がコミットされている

### #5: YAMLテストデータで全テストをパスさせる

**Purpose**: Excelなしで対象プロジェクトの全テストがパスすることを確認する。

**Prerequisites**: #4

**Steps**:

- [ ] README で確認した手順で（必要なら proman-common を install 済みの状態で）`proman-batch` にて `mvn clean test` を実行し、BUILD SUCCESS になることを確認する（`clean` により `target/test-classes` に旧 Excel が残っていないこと。実行テスト件数が Task #1 と一致することも確認する）
- [ ] テストログで YamlTestDataParser が実際に使われていることを確認する
- [ ] self-check (OK/NG per completion criterion, record in checks/task-5.md)
- [ ] QA expert review (Agent: general-purpose / Opus)
- [ ] language expert review (Agent: general-purpose / Opus)
- [ ] software-engineering expert review (Agent: general-purpose / Opus)
- [ ] user review

**Completion criteria**:

- `mvn test` が BUILD SUCCESS で終了する（全テストパス、Excel ファイルなし、実行テスト件数が Task #1 と同数）
- テストログで YamlTestDataParser が実際に使われていることが確認できる
