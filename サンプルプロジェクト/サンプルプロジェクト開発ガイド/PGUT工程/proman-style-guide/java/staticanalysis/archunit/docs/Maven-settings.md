# MavenでのArchUnitの実行方法

ArchUnitは`mvn test`を実行することで、JUnitのテストとして実行が可能です。

## チェックを除外するフィルターファイルを配置する

除外設定ファイルでテスト対象を除外したい場合は、[こちら](Ops-Rule.md#%E9%99%A4%E5%A4%96%E8%A8%AD%E5%AE%9A%E3%83%95%E3%82%A1%E3%82%A4%E3%83%AB%E3%81%AB%E8%A8%98%E8%BC%89%E3%81%97%E3%81%99%E3%81%B9%E3%81%A6%E3%81%AE%E3%82%A2%E3%83%BC%E3%82%AD%E3%83%86%E3%82%AF%E3%83%81%E3%83%A3%E3%83%86%E3%82%B9%E3%83%88%E3%81%8B%E3%82%89%E9%99%A4%E5%A4%96%E3%81%99%E3%82%8B) を参照して対応してください。

除外設定ファイルを使用しない場合は、次の手順に進んでください。

## ArchUnitの依存を組み込む

`pom.xml` を編集し、依存関係にArchUnitを追加します。

```xml
<dependency>
    <groupId>com.tngtech.archunit</groupId>
    <artifactId>archunit-junit5</artifactId>
    <version>1.0.1</version>
    <scope>test</scope>
</dependency>
```

## Mavenでテストを実施する

テストは次のコマンドで実施できます。

```sh
mvn test
```

もし違反がある場合は、以下のようにテスト内容と違反した対象が表示されます。

```
[ERROR] ExampleRuleTest.Actionがpublicであること  Time elapsed: 0.409 s  <<< FAILURE!
java.lang.AssertionError: 
Architecture Violation [Priority: MEDIUM] - Rule 'classes that have simple name ending with 'Action' should be public' was violated (1 times):
Class <com.example.ExampleAction> does not have modifier PUBLIC in (ExampleAction.java:0)
	at com.tngtech.archunit.lang.ArchRule$Assertions.assertNoViolation(ArchRule.java:94)
	at com.tngtech.archunit.lang.ArchRule$Assertions.check(ArchRule.java:86)
	...
```

すべてのテストに合格した場合は、次のようにコンソールに出力されます。

```sh

[INFO] Results:
[INFO]
[INFO] Tests run: xx, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
