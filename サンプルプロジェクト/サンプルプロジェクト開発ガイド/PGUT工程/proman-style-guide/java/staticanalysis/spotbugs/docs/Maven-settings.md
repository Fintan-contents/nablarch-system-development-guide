# MavenでのSpotBugsの実行方法

この文書ではMavenでSpotBugsの実行をするための設定方法と実行方法をガイドします。

なお、この文書の内容はMaven 3.6.3で動作検証を行っています。

## チェックを除外するフィルターファイルを配置する

プロジェクトのディレクトリ以下の任意の場所へ[フィルターファイル](../spotbugs-example/spotbugs/spotbugs_exclude_for_production.xml)を配置してください。
ここでは `${basedir}/spotbugs/spotbugs_exclude_for_production.xml` へ配置したと仮定します。

## 使用不許可APIチェックツールの設定ファイルを配置する

プロジェクトのディレクトリ以下の任意の場所へ使用不許可APIチェックツールの設定ファイルを配置してください。
ここでは `${basedir}/spotbugs/published-config/production` ディレクトリに下記の設定ファイルを配置したと仮定します。

- [JavaOpenApi.config](../spotbugs-example/spotbugs/published-config/production/JavaOpenApi.config)
- [NablarchApiForProgrammer.config](../spotbugs-example/spotbugs/published-config/production/NablarchApiForProgrammer.config)

## SpotBugs Maven Pluginを組み込む

`pom.xml` を編集します。
`spotbugs-maven-plugin` の設定を追加して、フィルターファイルのパスと使用不許可APIチェックツールの設定ファイルを配置したディレクトリのパスを記載してください。
また、必要に応じてヒープサイズも指定してください。

設定を追加する場所は`build`要素直下の`plugins`要素の直下です。

```xml
<plugin>
  <groupId>com.github.spotbugs</groupId>
  <artifactId>spotbugs-maven-plugin</artifactId>
  <version>4.5.0.0</version>
  <dependencies>
    <dependency>
      <groupId>com.github.spotbugs</groupId>
      <artifactId>spotbugs</artifactId>
      <version>4.5.0</version>
    </dependency>
  </dependencies>
  <configuration>
    <xmlOutput>true</xmlOutput>
    <!-- チェックを除外するフィルターファイル -->
    <excludeFilterFile>spotbugs/spotbugs_exclude_for_production.xml</excludeFilterFile>
    <!-- 使用不許可APIチェックツールの設定ファイル -->
    <jvmArgs>-Dnablarch-findbugs-config=spotbugs/published-config/production</jvmArgs>
    <!-- ヒープサイズが足りない場合は増やすことも可能 -->
    <maxHeap>1024</maxHeap>
    <plugins>
      <plugin>
        <groupId>com.nablarch.framework</groupId>
        <artifactId>nablarch-unpublished-api-checker</artifactId>
        <version>1.0.0</version>
      </plugin>
    </plugins>
  </configuration>
</plugin>
```

フィルターファイルが`pom.xml`へ記載されたパスに存在しない場合、後述する方法でチェックを実施しようとすると次のようなエラーが出ます。

```
[ERROR] Could not find resource 'spotbugs/spotbugs_exclude_for_production.xml'. -> [Help 1]
```

また、使用不許可APIチェックツールの設定ファイルを配置したディレクトリが`pom.xml`へ記載されたパスに存在しない場合、後述する方法でチェックを実施しようとすると次のような例外がスローされます。

```
[java] Caused by: java.lang.RuntimeException: Config file directory doesn't exist.Path=[spotbugs/published-config/production]
```

このようなエラー・例外が出た場合は`pom.xml`に記載されたパスと実際のファイル配置を確認してください。

## Mavenでチェックを実施する

チェックは次のコマンドで実施できます。

```sh
mvn spotbugs:check
```

※チェックの際はtarget配下にclassファイルが出力されている必要があります。classファイルが出力されていない場合は以下のコマンドを実行してください。

```sh
mvn compile
```

もしチェック違反がある場合は次のように指摘内容がコンソールに出力されます。

```
[INFO] --- spotbugs-maven-plugin:4.5.0.0:check (default-cli) @ spotbugs-example ---
[INFO] BugInstance size is 1
[INFO] Error size is 0
[INFO] Total bugs: 1
[INFO] String オブジェクトを == や != を使用して比較しています。com.example.App.main(String[]) [com.example.App] 該当箇所 App.java:[line 9] ES_COMPARING_STRINGS_WITH_EQ
```

すべてのチェックをパスした場合は次のようにコンソールに出力されます。

```
[INFO] --- spotbugs-maven-plugin:4.5.0.0:check (default-cli) @ spotbugs-example ---
[INFO] BugInstance size is 0
[INFO] Error size is 0
[INFO] No errors/warnings found
```
