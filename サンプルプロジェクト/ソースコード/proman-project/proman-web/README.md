# アプリケーションの起動とログイン方法

## 動作環境

動作環境は[開発環境構築ガイド](../../../サンプルプロジェクト開発ガイド/PGUT工程/開発環境構築ガイド.md)を参照してください。

PostgreSQLの初期ユーザーとパスワードは以下の通りに設定します。

| ユーザー| パスワード |
|:------|:--------|
| postgres | password|


## 起動方法

proman-projectで以下のコマンドを実行してください。
```
$cd proman-common
$mvn install
```

proman-commonモジュールで以下のコマンドを実行してください。
```
$cd proman-common
$mvn -P gsp clean generate-resources
```

proman-webディレクトリに入り、jetty-maven-pluginを実行し、Jettyを起動させます。以下のコマンドを実行してください。

```
$cd proman-web
$mvn jetty:run
```

起動成功後、以下のURLでログイン画面を表示してください。

<http://localhost:9088/>

以下のログインID、パスワードでログインできます。

| ログインID | パスワード |
| :--------- | :--------- |
| 10000001   | pass123-   |