# 顧客管理システム（climan）

これは「顧客管理システム」のソースコードです。  
（**Cli**ent **Man**agement Systemを略して、`climan`と名付けています）

## 動作環境

動作環境は[開発環境構築ガイド](../../サンプルプロジェクト開発ガイド/PGUT工程/開発環境構築ガイド.md)を参照してください。

PostgreSQLの初期ユーザーとパスワードは以下の通りに設定します。

| ユーザー| パスワード |
|:------|:--------|
| postgres | password|


## 起動方法

以下のコマンドを実行します。

```
$ mvn -P gsp clean generate-resources
$ mvn compile
$ mvn jetty:run
```

## 動作確認

以下のURLにアクセスし、顧客一覧（JSON）が返されれば動作しています。

```
http://localhost:9080/client
```