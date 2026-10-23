# climan-project NTF YAML 対応 steering

## Overview
- 目的・背景: NTF（Nablarch Testing Framework）のAI対応として、AIが読み書きできないバイナリ形式のExcelテストデータをYAML形式に移行する（背景資料: https://github.com/Fintan-contents/nablarch-system-development-guide/pull/211）。
  - `pom.xml` に `nablarch-testing-yaml`（test 依存）と `nablarch-testing-converter`（ビルドプラグイン）を追加する
  - `YamlTestDataParser`（`testDataParser` 上書き）を `testDataParser` が参照される全フェーズで有効になる設定ファイルに追加する
  - 全テスト用ExcelをYAMLへ変換し（サンプリングで内容一致を確認）、Excelを削除する
  - Excelなし・YAMLテストデータで全テストがパスする
- 対象プロジェクト: climan-project（`nablarch-system-development-guide/Sample_Project/Source_Code/climan-project`。顧客管理システム、RESTful ウェブサービス）
- ブランチ: `NAB-682`（`develop` から作成済み。push しない）
- 変更範囲: climan-project 配下のみ（proman-project・ガイド文書・リポジトリ直下は変更しない）
- 指示書: `.rn/ntf-yaml-support/ntf-yaml-support-instructions_climan_V1.6.md`（原本: `/home/k10949/instructions/ntf-yaml-support-instructions_climan_V1.6.md`）

## Build / Test
- 確認元: `climan-project/README.md`、`climan-project/Jenkinsfile`
- 前提 DB: PostgreSQL `localhost:5432/postgres`（スキーマ `client`、ユーザー `client`/`password`。`src/env/dev/resources/env.config`）。H2 ではない
- 前提 JDK: Java 21（`pom.xml` の `java.version=21`、Jenkinsfile も JDK21）
- ローカル環境固有の注意:
  - `~/.mavenrc` が `JAVA_HOME` を JDK 17 に固定しているため、**mvn は `MAVEN_SKIP_RC=true` を付けて実行**する（mise の JDK 21.0.2 で動く）。ユーザー承認済み
  - 親 POM `com.nablarch.archetype:nablarch-archetype-parent:6-NEXT-SNAPSHOT` が `~/.m2` に無かったため、`/home/k10949/workspace/nablarch-single-module-archetype/nablarch-archetype-parent` で `mvn -N install` してインストールした（ユーザー承認済み。リポジトリ外の環境整備）
- 手順（対象プロジェクトのディレクトリで実行）:
  1. エンティティ生成・DB準備: `MAVEN_SKIP_RC=true mvn -P gsp clean generate-resources`
  2. テスト: `MAVEN_SKIP_RC=true mvn test`（Excel 削除後の最終確認は `mvn clean test`）
- ベースラインのテスト件数: 25件（`ClientActionTest` 19 / `ClientServiceTest` 6）

## Scripts
- （作成時に用途を追記する）

## Tasks

### #1: ベースライン（developと同一状態）を確認し、全PASSを確認する
**Purpose**: `NAB-682` ブランチ上で対象プロジェクト配下が `develop` と同一内容であることを確認し、その状態で全テストがパスすることを確認する。steering.md と指示書のコピーを配置する。
**Prerequisites**: none
**Steps**:
- [x] `NAB-682` ブランチであることを確認する
- [x] 作業ツリーに未コミットの変更が無いことを確認する
- [x] `git diff --stat develop -- Sample_Project/Source_Code/climan-project` が空であることを確認する
- [x] README・Jenkinsfile を確認し、ビルド・テストの実行手順を把握する
- [x] PostgreSQL が起動していることを確認する
- [x] steering.md と scripts の初版を作成する
- [x] 指示書を `.rn/ntf-yaml-support/` 配下へコピーし、原本と同一内容であることを確認する
- [x] `mvn -P gsp clean generate-resources` を実行する
- [x] `mvn test` で全テストがパスすることを確認する（25件）
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
**Purpose**: `pom.xml` に `nablarch-testing-yaml`（test 依存）と `nablarch-testing-converter`（ビルドプラグイン）を追加し、`YamlTestDataParser` を `testDataParser` が参照される全フェーズで有効になる設定ファイル（想定: `src/test/resources/unit-test.xml`）に追加する。参考実装: nablarch-example-rest（`NAB-682`）。
**Prerequisites**: #1
**Steps**:
- [x] `pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT`（test scope）を追加する
- [x] `pom.xml` の `<build><plugins>` に `nablarch-testing-converter:1.0.0-SNAPSHOT` を追加する（from=xls / to=yaml / input・output=`${project.basedir}/src/test/java` / overwrite=true）
- [x] 応答電文を読む同期送信系テストに該当するかを判定する（非該当）
- [x] `YamlTestDataParser` を配置する（`src/test/resources/unit-test.xml`）（`interpreters` は `dateTimeInterpreter` → `compositeInterpreter` の2つのみ）
- [x] `mvn dependency:resolve` と `mvn help:describe` で依存・プラグインの解決を確認する
- [ ] 変更したファイルをコミットする
- [ ] self-check / QA / language / software-engineering expert review
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
- [ ] 対象 Excel の所在を確認する（事前調査: `ClientActionTest.xlsx` の1件）
- [ ] 同名 `.xls` / `.xlsx` の併存を確認する
- [ ] converter README の手順で全対象 Excel を YAML 変換する
- [ ] サンプリング比較（各ファイル最低3件）を行い `checks/task-3.md` に記録する
- [ ] YAML をコミットする（Excel はまだ削除しない）
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
- [ ] `mvn clean test` が BUILD SUCCESS（25件）になることを確認する
- [ ] テストログで YamlTestDataParser が使われていることを確認する
- [ ] self-check / QA / language / software-engineering expert review
- [ ] user review（全 task 完了後にまとめて実施）
**Completion criteria**:
- `mvn test` が BUILD SUCCESS で終了する（全テストパス、Excel なし、件数が Task #1 と同数）
- テストログで YamlTestDataParser が使われていることが確認できる
