# task-3 Completion Check

## Completion Criteria

| Criterion | Self-check | Evidence | QA | QA Evidence |
|---|---|---|---|---|
| 変換対象の全てのExcelファイルに対応するYAMLファイルが生成されている | OK | 対象 Excel は `src/test/java/com/nablarch/example/climan/rest/client/ClientActionTest.xlsx` の1件（`find src -name '*.xls' -o -name '*.xlsx'`）。`MAVEN_SKIP_RC=true mvn nablarch-testing-converter:convert` → `Converted 1 file(s).` BUILD SUCCESS。全6シートに対応する YAML 6件（`ClientActionTest/setUpDb.yaml` ほか5件）が生成された | | |
| サンプリング比較でExcelとYAMLの内容が一致している | OK | `scripts/compare_xlsx_yaml.py` で全シートの SETUP_TABLE を全件比較し差異0件（未対応ブロックなし）。各ファイル3件（元データ0件のファイルは rows 空の一致を確認）のサンプリング結果は下記 Sampling Comparison のとおり全て一致 | | |
| 変換後のYAMLファイルがコミットされている | OK | YAML 6件と比較スクリプトを Task #3 のコミットに含めた（Excel は未削除） | | |

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
| ClientActionTest/setUpDb.yaml | 3（全6件） | CLIENT 行1 | 1 / テスト会社１（農業） / 01 | 1 / テスト会社１（農業） / 01 | OK |
| ClientActionTest/setUpDb.yaml | | CLIENT 行4 | 4 / テスト会社３（農業） / 01 | 4 / テスト会社３（農業） / 01 | OK |
| ClientActionTest/setUpDb.yaml | | CLIENT 行6 | 6 / テスト会社３（製造業） / 03 | 6 / テスト会社３（製造業） / 03 | OK |
| ClientActionTest/testFindNoClients.yaml | 0（元データ0件） | CLIENT | ヘッダのみ（0行） | `rows: []` | OK |
| ClientActionTest/testShowWithEmptyClientTable.yaml | 0（元データ0件） | CLIENT | ヘッダのみ（0行） | `rows: []` | OK |
| ClientActionTest/testFindIndustryCodeNoClients.yaml | 3（全5件） | CLIENT 行1 | 1 / テスト会社１（農業） / 01 | 1 / テスト会社１（農業） / 01 | OK |
| ClientActionTest/testFindIndustryCodeNoClients.yaml | | CLIENT 行3 | 3 / テスト会社２（建設業） / 02 | 3 / テスト会社２（建設業） / 02 | OK |
| ClientActionTest/testFindIndustryCodeNoClients.yaml | | CLIENT 行5 | 5 / テスト会社３（建設業） / 02 | 5 / テスト会社３（建設業） / 02 | OK |
| ClientActionTest/testFindCodeAndNameUpperLimit.yaml | 3（全1001件） | CLIENT 行1 | 1 / テスト会社１ / 01 | 1 / テスト会社１ / 01 | OK |
| ClientActionTest/testFindCodeAndNameUpperLimit.yaml | | CLIENT 行501 | 501 / テスト会社５０１ / 01 | 501 / テスト会社５０１ / 01 | OK |
| ClientActionTest/testFindCodeAndNameUpperLimit.yaml | | CLIENT 行1001 | 1001 / テスト会社１００１ / 02 | 1001 / テスト会社１００１ / 02 | OK |
| ClientActionTest/testFindOverUpperLimit.yaml | 3（全1001件） | CLIENT 行1 | 1 / テスト会社１ / 01 | 1 / テスト会社１ / 01 | OK |
| ClientActionTest/testFindOverUpperLimit.yaml | | CLIENT 行501 | 501 / テスト会社５０１ / 01 | 501 / テスト会社５０１ / 01 | OK |
| ClientActionTest/testFindOverUpperLimit.yaml | | CLIENT 行1001 | 1001 / テスト会社１００１ / 01 | 1001 / テスト会社１００１ / 01 | OK |
（値は CLIENT_ID / CLIENT_NAME / INDUSTRY_CODE。サンプルは先頭・中央・末尾。加えてスクリプトで全件比較し差異0件）

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
