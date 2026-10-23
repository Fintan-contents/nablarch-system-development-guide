# proman-batch NTF YAML 対応 steering

## Overview
- 目的・背景: NTF（Nablarch Testing Framework）のAI対応として、AIが読み書きできないバイナリ形式のExcelテストデータをYAML形式に移行する（背景資料: https://github.com/Fintan-contents/nablarch-system-development-guide/pull/211）。
  - `pom.xml` に `nablarch-testing-yaml`（test 依存）と `nablarch-testing-converter`（ビルドプラグイン）を追加する
  - `YamlTestDataParser`（`testDataParser` 上書き）を `testDataParser` が参照される全フェーズで有効になる設定ファイルに追加する
  - 全テスト用ExcelをYAMLへ変換し（サンプリングで内容一致を確認）、Excelを削除する
  - Excelなし・YAMLテストデータで全テストがパスする
- 対象プロジェクト: proman-batch（`nablarch-system-development-guide/Sample_Project/Source_Code/proman-project/proman-batch`。プロジェクト管理システムのバッチモジュール、Nablarch バッチ）
- ブランチ: `NAB-682`（`develop` から作成済み。climan-project の移行コミットが先に積まれている。push しない）
- 変更範囲: proman-batch 配下のみ（親 `proman-project/pom.xml`、proman-common / proman-web / proman-jmeter、climan-project、ガイド文書・リポジトリ直下は変更しない）
- 指示書: `.rn/ntf-yaml-support/ntf-yaml-support-instructions_proman-batch_V1.6.md`（原本: `/home/k10949/instructions/ntf-yaml-support-instructions_proman-batch_V1.6.md`）

## Build / Test
- 確認元: `proman-project/proman-batch/README.md`、`proman-project/README.md`、`proman-project/proman-common/README.md`、`proman-project/Jenkinsfile`
- 前提 DB: PostgreSQL `localhost:5432/postgres`（スキーマ `public`、ユーザー `postgres`/`password`。`src/env/dev/resources/env.config`）。H2 ではない
- 前提 JDK: Java 21（親 `proman-project/pom.xml` の `java.version=21`）
- ローカル環境固有の注意（climan 作業時にユーザー承認済み）:
  - `~/.mavenrc` が `JAVA_HOME` を JDK 17 に固定しているため、**mvn は `MAVEN_SKIP_RC=true` を付けて実行**する（mise の JDK 21.0.2 で動く）
  - 親 POM `com.nablarch.archetype:nablarch-archetype-parent:6-NEXT-SNAPSHOT` は `~/.m2` にインストール済み
- 手順（README / Jenkinsfile 準拠）:
  1. 親 pom のインストール: `proman-project` で `MAVEN_SKIP_RC=true mvn -N install`
  2. エンティティ生成・DB準備: `proman-common` で `MAVEN_SKIP_RC=true mvn -P gsp clean generate-resources`
  3. proman-common のインストール: `proman-common` で `MAVEN_SKIP_RC=true mvn install`
  4. テスト: `proman-batch` で `MAVEN_SKIP_RC=true mvn test`（Excel 削除後の最終確認は `mvn clean test`）
  - 1〜3 はファイルを変更しないビルド操作（`git status` で確認済み）
- ベースラインのテスト件数: 3件（`ExportProjectsInPeriodActionRequestTest` 1 / `GeneralCodingRuleForBatchTest` 2）

## Scripts
- `scripts/compare_xlsx_yaml.py`（Task #3）: Excel(.xlsx) と変換後 YAML を全ブロック・全セルで比較し、各ブロックの先頭・中央・末尾の3件（3件未満は全件）をサンプル表示する。対応ブロックは LIST_MAP / SETUP_TABLE（グループ付き含む）/ EXPECTED_VARIABLE。生値（RAW）比較と、NTF の Excel 読み込み時の解釈（NullInterpreter・QuotationTrimmer・大文字化・コメント行除外・ディレクティブ trim と可変長ファイルの既定値）を適用した比較を行い、後者の不一致があれば終了コード1。xlsx は標準ライブラリで読み（ふりがな rPh は除外）、YAML は PyYAML（YAML 1.1 の真偽値解釈を無効化）で読む。実行: `python3 -I .rn/ntf-yaml-support/scripts/compare_xlsx_yaml.py <xlsx> <YAMLディレクトリ>`

## Tasks

### #1: ベースライン（developと同一状態）を確認し、全PASSを確認する
**Purpose**: `NAB-682` ブランチ上で対象プロジェクト配下が `develop` と同一内容であることを確認し、その状態で全テストがパスすることを確認する。steering.md と指示書のコピーを配置する。
**Prerequisites**: none
**Steps**:
- [x] `NAB-682` ブランチであることを確認する
- [x] 作業ツリーに未コミットの変更が無いことを確認する
- [x] `git diff --stat develop -- Sample_Project/Source_Code/proman-project/proman-batch` が空であることを確認する
- [x] README・Jenkinsfile を確認し、ビルド・テストの実行手順を把握する
- [x] PostgreSQL が起動していることを確認する
- [x] steering.md と scripts の初版を作成する
- [x] 指示書を `.rn/ntf-yaml-support/` 配下へコピーし、原本と同一内容であることを確認する
- [x] 親 pom のインストール、proman-common の `generate-resources` / `install` を実行する（ファイル変更なし）
- [x] proman-batch で `mvn test` を実行し全テストがパスすることを確認する（3件）
- [x] 結果を `checks/task-1.md` に記録する
- [x] 変更ファイルをコミットする
- [x] self-check
- [x] QA expert review
- [ ] user review（全 task 完了後にまとめて実施）
**Completion criteria**:
- `NAB-682` ブランチ上で作業しており、コミット履歴が維持されている
- 作業開始時点で対象プロジェクト配下が `develop` と同一内容である
- `mvn test` が全テストパスで終了している（BUILD SUCCESS）
- 指示書のコピーが `.rn/ntf-yaml-support/` 配下に原本と同一内容で配置され、コミットされている

### #2: 設定変更 — pom.xml と unit-test.xml にYAML対応を追加
**Purpose**: proman-batch の `pom.xml` に `nablarch-testing-yaml`（test 依存）と `nablarch-testing-converter`（ビルドプラグイン）を追加し、`YamlTestDataParser` を `testDataParser` が参照される全フェーズで有効になる設定ファイル（想定: `src/test/resources/unit-test.xml`）に追加する。参考実装: nablarch-example-batch（`NAB-682`）。
**Prerequisites**: #1
**Steps**:
- [x] `pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT`（test scope）を追加する
- [x] `pom.xml` の `<build><plugins>` に `nablarch-testing-converter:1.0.0-SNAPSHOT` を追加する（from=xls / to=yaml / input・output=`${project.basedir}/src/test/java` / overwrite=true）
- [x] 応答電文を読む同期送信系テストに該当するかを判定する（非該当）
- [x] `YamlTestDataParser` を配置する（`src/test/resources/unit-test.xml`）（`interpreters` は `dateTimeInterpreter` → `compositeInterpreter` の2つのみ）
- [x] `mvn dependency:resolve` と `mvn help:describe` で依存・プラグインの解決を確認する
- [x] 変更したファイルをコミットする
- [x] self-check / QA / language / software-engineering expert review
- [ ] user review（全 task 完了後にまとめて実施）
**Completion criteria**:
- `pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT` が test スコープで追加されている
- `pom.xml` の `<build><plugins>` に `nablarch-testing-converter:1.0.0-SNAPSHOT` がビルドプラグインとして追加されている
- `YamlTestDataParser` が全フェーズで有効になる設定ファイルに、`dateTimeInterpreter` → `compositeInterpreter` の順で2つのみの `interpreters` で追加されている
- `mvn dependency:resolve` が成功し、converter プラグインの `convert` ゴールが `mvn help:describe` で解決できる

### #3: ExcelテストデータをYAMLに変換
**Purpose**: `nablarch-testing-converter` で Excel を YAML に変換し、サンプリングで内容一致を確認する。
**Prerequisites**: #2
**Steps**:
- [x] 対象 Excel の所在を確認する（`ExportProjectsInPeriodActionRequestTest.xlsx` の1件）
- [x] 同名 `.xls` / `.xlsx` の併存を確認する（併存なし）
- [x] converter README の手順で全対象 Excel を YAML 変換する（`mvn nablarch-testing-converter:convert`。YAML 2件）
- [x] サンプリング比較（各ファイル最低3件）を行い `checks/task-3.md` に記録する（全929セルを比較し NTF 解釈後の不一致0件。表記差46件は NTF の解釈で説明できる）
- [x] YAML をコミットする（Excel はまだ削除しない）
- [ ] self-check / QA expert review
- [ ] user review（全 task 完了後にまとめて実施）
**Completion criteria**:
- 変換対象の全Excelに対応するYAMLが生成されている
- サンプリング比較でExcelとYAMLの内容が一致している
- YAMLがコミットされている

### #4: Excelを削除する
**Purpose**: 変換対象だった Excel を削除してコミットする。
**Prerequisites**: #3
**Steps**:
- [ ] 変換対象の Excel を `git rm` で削除する
- [ ] 残存Excelが0件であることを `checks/task-4.md` に記録する
- [ ] 削除をコミットする
- [ ] self-check / QA expert review
- [ ] user review（全 task 完了後にまとめて実施）
**Completion criteria**:
- 変換対象だった全 Excel が削除されている
- 削除がコミットされている

### #5: YAMLテストデータで全テストをパスさせる
**Purpose**: Excelなしで全テストがパスすることを確認する。
**Prerequisites**: #4
**Steps**:
- [ ] `mvn clean test` が BUILD SUCCESS（3件）になることを確認する
- [ ] テストログで YamlTestDataParser が使われていることを確認する
- [ ] self-check / QA / language / software-engineering expert review
- [ ] user review（全 task 完了後にまとめて実施）
**Completion criteria**:
- `mvn test` が BUILD SUCCESS で終了する（全テストパス、Excel なし、件数が Task #1 と同数）
- テストログで YamlTestDataParser が使われていることが確認できる
