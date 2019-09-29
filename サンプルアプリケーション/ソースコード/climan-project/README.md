# 顧客管理システム

## 起動方法

以下のコマンドを実行します。

```
$ mvn -P gsp clean generate-resources
$ mvn compile waitt:run-headless
```

## 動作確認

以下のURLにアクセスし、顧客一覧（JSON）が返されれば動作しています。

```
http://localhost:9080/client
```