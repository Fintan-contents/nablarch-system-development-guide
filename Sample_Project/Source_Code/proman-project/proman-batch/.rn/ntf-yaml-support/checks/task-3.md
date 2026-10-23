# task-3 Completion Check

## Completion Criteria

| Criterion | Self-check | Evidence | QA | QA Evidence |
|---|---|---|---|---|
| 変換対象の全てのExcelファイルに対応するYAMLファイルが生成されている | OK | 対象 Excel は `src/test/java/com/nablarch/example/proman/batch/project/ExportProjectsInPeriodActionRequestTest.xlsx` の1件。`MAVEN_SKIP_RC=true mvn nablarch-testing-converter:convert` → `Converted 1 file(s).` BUILD SUCCESS（WARN なし）。全2シートに対応する `ExportProjectsInPeriodActionRequestTest/setUpDb.yaml`・`testNormalEnd.yaml` を生成。ブロック数も一致（setUpDb: SETUP_TABLE 2 / testNormalEnd: LIST_MAP 1・SETUP_TABLE 6・EXPECTED_VARIABLE 6） | | |
| サンプリング比較でExcelとYAMLの内容が一致している | OK | `scripts/compare_xlsx_yaml.py` で全ブロック・全セル（929セル）を比較し、NTF 解釈後の不一致は0件。サンプリング（各ブロック先頭・中央・末尾。3件未満は全件）は下記 Sampling Comparison のとおり全て一致。生の値が異なり NTF 解釈で一致する「表記差」46件は、すべて NTF の Excel 読み込み時の解釈で説明できる（内訳は下記）ため、差異ではないと判断した | | |
| 変換後のYAMLファイルがコミットされている | OK | YAML 2件と比較スクリプトを Task #3 のコミットに含めた（Excel は未削除） | | |

## QA Expert Review

| Aspect | Verdict | Evidence / Improvement |
|---|---|---|
| Meaningful tests/verification | | |
| Edge case coverage | | |

## Expert Reviews (code changes only)

N/A — no code changes in this task.

## Sampling Comparison（task-3 のみ）
| YAML ファイル | 比較件数 | レコードキー/行 | Excel 値 | YAML 値 | 一致 |
| --- | --- | --- | --- | --- | --- |
| setUpDb.yaml | 3（全24件） | ORGANIZATION 行1 | 1 / 組織1 / null | 1 / 組織1 / null | OK |
| setUpDb.yaml | | ORGANIZATION 行13 | 13 / 組織13 / null | 13 / 組織13 / null | OK |
| setUpDb.yaml | | ORGANIZATION 行24 | 24 / 組織24 / null | 24 / 組織24 / null | OK |
| setUpDb.yaml | 3（全24件） | PROJECT 行1 | 1 / プロジェクト１ / 01 / 01 / 2020-01-01 / 2120-01-01 / … / 1000 / 1 | 同左 | OK |
| setUpDb.yaml | | PROJECT 行13 | 13 / プロジェクト１３ / 2 / 03 / 2020-12-31 / 2030-01-01 / … / 1000 / 1 | 同左 | OK |
| setUpDb.yaml | | PROJECT 行24 | 24 / プロジェクト２４ / 02 / 03 / 2019-01-31 / 2019-02-28 / … / 1000 / 1 | 同左 | OK |
| testNormalEnd.yaml | 3（全5件。コメント行 `//6` は NTF と同じく除外） | testShots no=1 | 対象データ1件。プロジェクト開始日付=業務日付 / 0 / setUpTable=1 / expectedFile=1 / batch-boot.xml | 同左 | OK |
| testNormalEnd.yaml | | testShots no=3 | 対象データ0件。…一日後） / 0 / setUpTable=3 / expectedFile=3 | 同左 | OK |
| testNormalEnd.yaml | | testShots no=5 | 改行を含む3行の description / 0 / setUpTable=5 / expectedFile=5 | 同左（改行も一致） | OK |
| testNormalEnd.yaml | 1×6（各1件のため全件） | SETUP_TABLE[1]〜[6] BUSINESS_DATE | 01 / 20190131, 20190228, 20190130, 20190301, 20210301, 20210301 | 同左 | OK |
| testNormalEnd.yaml | 全件（1件） | EXPECTED_VARIABLE[1] 行1 | 24 / プロジェクト２４ / 02 / … | 同左 | OK |
| testNormalEnd.yaml | 全件（1件） | EXPECTED_VARIABLE[2] 行1 | 24 / プロジェクト２４ / 02 / … | 同左 | OK |
| testNormalEnd.yaml | 0（元データ0件） | EXPECTED_VARIABLE[3]・[4] | フィールド定義のみ（0行） | `rows: []` | OK |
| testNormalEnd.yaml | 3（全19件） | EXPECTED_VARIABLE[5] 行1 | 1 / プロジェクト１ / 01 / … | 同左 | OK |
| testNormalEnd.yaml | | EXPECTED_VARIABLE[5] 行10 | 16 / プロジェクト１６ / 02 / … | 同左 | OK |
| testNormalEnd.yaml | | EXPECTED_VARIABLE[5] 行19 | 20 / プロジェクト２０ / 02 / … | 同左 | OK |
| testNormalEnd.yaml | 全件（1件） | EXPECTED_VARIABLE[6] 行1 | 6 / プロジェクト６ / 06 / … | 同左 | OK |
（サンプルに加え、スクリプトで全929セルを比較し NTF 解釈後の不一致0件。各 EXPECTED_VARIABLE のフィールド定義（名前・型 13列）も全一致。比較ログ: スクリプト実行結果）

### 表記差（生の値は異なるが、NTF の解釈では一致）46件の内訳
| 箇所 | 件数 | Excel（生値） | YAML | 一致と判断した根拠 |
| --- | --- | --- | --- | --- |
| SETUP_TABLE organization.UPPER_ORGANIZATION / project.NOTE・SALES_AMOUNT | 26 | `null`（文字列） | `null` | NTF の NullInterpreter が Excel の "null" を null に解釈する |
| EXPECTED_VARIABLE[5] 備考・売上高 | 2 | `null`（文字列） | `null` | 同上 |
| EXPECTED_VARIABLE[1]〜[6] の quoting-delimiter | 6 | `"` + CR（`"\r`） | `"` | NTF の `DataFile#setDirective` が値を `trim()` するため、Excel でも `"` になる |
| EXPECTED_VARIABLE[1]〜[6] の file-type | 6 | 記載なし | `Variable` | NTF の `DataFile` が EXPECTED_VARIABLE に `file-type=Variable` を自動設定する |
| EXPECTED_VARIABLE[1]〜[6] の field-separator | 6 | 記載なし | `,` | NTF の `VariableLengthFile` の既定値が `,`（`defaultDirectives` はプロジェクト・既定設定とも未設定） |
（ほかに、テーブル名・カラム名の大文字化（`organization`→`ORGANIZATION` 等）は NTF の TableData と同じ扱いのため、比較時に大文字化して照合した）

## .xls/.xlsx 併存チェック（該当時のみ）
- 併存の有無: なし（同名の `.xls` / `.xlsx` は存在しない）
- 差分確認結果の保存先: 該当なし
- 未使用側（通常 `.xlsx`）の削除: 該当なし

## Overall Verdict

- Self-check: OK
- QA:
- Language expert: N/A
- Software-engineering expert: N/A
- Ready for user review:
