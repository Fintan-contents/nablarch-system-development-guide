# 単体テストの考え方（REST）

RESTでは、原則として[Nablarch標準のテスト方法](https://nablarch.github.io/docs/LATEST/doc/development_tools/testing_framework/index.html)を踏襲します。

開発者が作成する成果物とそのテスト方法を記載します。

| 成果物         | 机上 | クラス単体 | リクエスト単体 |
| -------------- | ---- | ---------- | -------------- |
| Action         | -    | -          | o              |
| Form           | o    | o          | o              |
| ドメインクラス | o    | -          | o              |
| Service        | -    | o          | o              |
| SQLファイル    | o [^1] | -          | o              |
| Entity         | -    | -          | o              |
| DTO            | -    | -          | o              |

[^1]: SQLの確認は机上確認だけでなく、SQL Executorを使った動作確認も行う。

## Action

リクエスト単体テストにて動作確認します。

## Form

[単体テストの考え方（Web）](単体テストの考え方（Web）.md#form) 同様です。

## ドメインクラス

[単体テストの考え方（Web）](単体テストの考え方（Web）.md#ドメインクラス) 同様です。

## Service

[単体テストの考え方（Web）](単体テストの考え方（Web）.md#service) 同様です。

## SQLファイル

[単体テストの考え方（Web）](単体テストの考え方（Web）.md#sqlファイル) 同様です。

## Entity

[単体テストの考え方（Web）](単体テストの考え方（Web）.md#entity) 同様です。

## DTO

[単体テストの考え方（Web）](単体テストの考え方（Web）.md#dto) 同様です。
