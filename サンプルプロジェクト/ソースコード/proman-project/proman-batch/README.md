# バッチアプリケーションの起動方法

## 動作環境

動作環境は[開発環境構築ガイド](../../../サンプルプロジェクト開発ガイド/PGUT工程/開発環境構築ガイド.md)を参照してください。

PostgreSQLの初期ユーザーとパスワードは以下の通りに設定します。

| ユーザー| パスワード |
|:------|:--------|
| postgres | password|


## ビルド方法

proman-commonモジュールで以下のコマンドを実行しておいてください。
```
$cd proman-common
$mvn -P gsp clean generate-resources
```

次に、アプリケーションをビルドします。以下のコマンドを実行してください。

```
$mvn clean package
```


## バッチ起動方法


以下のコマンドを実行します。

```
$mvn exec:java -Dexec.mainClass=nablarch.fw.launcher.Main -Dexec.args="'-diConfig' 'classpath:batch-boot.xml' '-requestPath' 'project.ExportProjectsInPeriodAction' '-userId' 'batch_user'"
```

起動に成功すると、work/outputディレクトリの下にN21AA002.csvが作成されます。