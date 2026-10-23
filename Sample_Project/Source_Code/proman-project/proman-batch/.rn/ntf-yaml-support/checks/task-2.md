# task-2 Completion Check

## Completion Criteria

| Criterion | Self-check | Evidence | QA | QA Evidence |
|---|---|---|---|---|
| `pom.xml` に `nablarch-testing-yaml:1.0.0-SNAPSHOT` が test スコープで追加されている | OK | proman-batch の `pom.xml` の `nablarch-testing-junit5` の直後に `<scope>test</scope>` で追加（親 `proman-project/pom.xml` は未変更）。`mvn dependency:resolve` の出力に `nablarch-testing-yaml:jar:1.0.0-SNAPSHOT:test` | | |
| `pom.xml` の `<build><plugins>` に `nablarch-testing-converter:1.0.0-SNAPSHOT` がビルドプラグインとして追加されている | OK | proman-batch の `<build><plugins>` の `maven-assembly-plugin` の後・`jacoco-maven-plugin` の前に追加。plugin直下 `<configuration>` に from=xls / to=yaml / input・output=`${project.basedir}/src/test/java` / overwrite=true（nablarch-example-batch と同内容）。`<executions>` なし（CLI で `convert` を実行）。親 pom の `<pluginManagement>` は未変更 | | |
| `YamlTestDataParser` が全フェーズで有効になる設定ファイルに、`dateTimeInterpreter` → `compositeInterpreter` の2つのみの `interpreters` で追加されている | OK | 判定: 非該当（テストは `BatchRequestTestSupport` 系で、testShots の diConfig は `batch-boot.xml`。Excel のブロックは `LIST_MAP=testShots`・`SETUP_TABLE`・`EXPECTED_VARIABLE`（出力 CSV）のみで、応答電文 `response_*` / sendSync はない。src に `response_` / `sendSync` / `MessageSender` の記述なし。SETUP はアクション実行前、EXPECTED の検証は実行後に行われるため、実行中に testDataParser は参照されない）。参考実装 nablarch-example-batch と同様に `src/test/resources/unit-test.xml` に定義し、`batch-boot.xml` 側への集約は行わない。位置は `dbInfo` 定義と `nablarch/test/test-data.xml`・`test-transaction.xml` の import の後（`masterDataRestorer` の前）。`dbInfo` 指定、`testDataReader` 指定なし、`interpreters` 内 `<list>` に2つのみを直接記載 | | |
| `mvn dependency:resolve` が成功し、converter プラグインの `convert` ゴールが `mvn help:describe` で解決できる | OK | `mvn validate` / `mvn dependency:resolve` とも BUILD SUCCESS。`mvn help:describe -Dplugin=com.nablarch.framework:nablarch-testing-converter:1.0.0-SNAPSHOT` で Goal Prefix `nablarch-testing-converter`、`nablarch-testing-converter:convert` を表示。pom.xml / unit-test.xml は XML として整形式 | | |

## QA Expert Review

| Aspect | Verdict | Evidence / Improvement |
|---|---|---|
| Meaningful tests/verification | | |
| Edge case coverage | | |

## Expert Reviews (code changes only)

### Language Expert

| Aspect | Verdict | Evidence / Improvement |
|---|---|---|
| Best practices | | |
| Codebase style consistency | | |
| GWT test format | | |

### Software-engineering Expert

| Aspect | Verdict | Evidence / Improvement |
|---|---|---|
| Separation of concerns | | |
| System integrity | | |
| Maintainability | | |

## Overall Verdict

- Self-check: OK
- QA:
- Language expert:
- Software-engineering expert:
- Ready for user review:
