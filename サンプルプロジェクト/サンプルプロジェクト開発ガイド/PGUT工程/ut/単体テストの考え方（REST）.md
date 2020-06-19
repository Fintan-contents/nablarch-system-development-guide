# 単体テストの考え方（REST）

本プロジェクトのWebアプリケーションでは、プロジェクト独自のテスト方法を定義しています。
（[単体テストの考え方（Web）](単体テストの考え方（Web）.md)）

RESTでは、原則として[Nablarch標準のテスト方法](https://nablarch.github.io/docs/LATEST/doc/development_tools/rest_testing_framework/02_development_guide/index.html)を踏襲します。

開発者が作成する成果物とそのテスト方法を記載します。

| 成果物         | 机上 | クラス単体 | リクエスト単体 |
| -------------- | ---- | ---------- | -------------- |
| Action         | -    | -          | o              |
| Form           | o    | o          | o              |
| ドメインクラス | o    | -          | o              |
| Service        | -    | o          | o              |
| SQLファイル    | o    | -          | o              |
| Entity         | -    | -          | o              |
| DTO            | -    | -          | o              |
| routes.xml     | -    | -          | o              |

## Action

リクエスト単体テストにて動作確認します。

## Form

[単体テストの考え方（Web）](単体テストの考え方（Web）.md) 同様です。

## ドメインクラス

[単体テストの考え方（Web）](単体テストの考え方（Web）.md) 同様です。

## Service

[単体テストの考え方（Web）](単体テストの考え方（Web）.md) 同様です。

## SQLファイル

[単体テストの考え方（Web）](単体テストの考え方（Web）.md) 同様です。

## Entity

[単体テストの考え方（Web）](単体テストの考え方（Web）.md) 同様です。

## DTO

[単体テストの考え方（Web）](単体テストの考え方（Web）.md) 同様です。

### routes.xml

リクエスト単体テストで確認します。
