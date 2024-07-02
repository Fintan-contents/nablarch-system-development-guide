# MavenでのCheckstyleの実行方法

この文書ではMavenでCheckstyleの実行をするための設定方法と実行方法をガイドします。

なお、この文書の内容はMaven 3.5.2で動作検証を行っています。

## Checkstyleの設定ファイルをプロジェクトへ組み込む

プロジェクトのディレクトリ以下の任意の場所へ[nablarch-checkstyle.xml](../checkstyle-example/checkstyle/nablarch-checkstyle.xml)を配置してください。
ここでは`${basedir}/checkstyle/nablarch-checkstyle.xml`に配置したと仮定します。

次に`nablarch-checkstyle.xml`を配置したディレクトリへ[header.txt](../checkstyle-example/checkstyle/header.txt)と[suppressions.xml](../checkstyle-example/checkstyle/suppressions.xml)も配置してください。
`header.txt`はライセンス情報など、決められたヘッダーがソースファイルに記載されているかチェックするためのものです。
`header.txt`の内容はプロジェクトで決められたヘッダーに書き換えてください。

これらのファイルを配置したら `pom.xml` を編集します。
`maven-checkstyle-plugin` の設定を追加して、設定ファイルのパスを記載してください。

設定を追加する場所は`build`要素直下の`plugins`要素の直下です。

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-checkstyle-plugin</artifactId>
  <version>3.0.0</version>
  <dependencies>
    <dependency>
      <groupId>com.puppycrawl.tools</groupId>
      <artifactId>checkstyle</artifactId>
      <version>8.11</version>
    </dependency>
  </dependencies>
  <configuration>
    <!-- Checkstyle設定ファイルへのパス -->
    <configLocation>${basedir}/checkstyle/nablarch-checkstyle.xml</configLocation>
    <propertyExpansion>config_loc=${basedir}/checkstyle/</propertyExpansion>
  </configuration>
</plugin>
```

設定ファイルが`pom.xml`へ記載されたパスに存在しない場合、後述する方法でチェックを実施しようとすると次のようなエラーが出ます。

```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-checkstyle-plugin:3.0.0:check (default-cli) on project checkstyle-example: Failed during checkstyle execution: Unable to find configuration file at location: C:\example\checkstyle-example/checkstyle/nablarch-checkstyle.xml: Could not find resource 'C:\example\checkstyle-example/checkstyle/nablarch-checkstyle.xml'. -> [Help 1]
```

このようなエラーが出た場合は`pom.xml`に記載されたパスと実際のファイル配置を確認してください。

## Mavenでチェックを実施する

チェックは次のコマンドで実施できます。

```sh
mvn checkstyle:check
```

もしチェック違反がある場合は次のように指摘内容がコンソールに出力されます。

```
[INFO] --- maven-checkstyle-plugin:3.0.0:check (default-cli) @ checkstyle-example ---
[INFO] There is 1 error reported by Checkstyle 6.18 with C:\example\checkstyle-example/checkstyle/nablarch-checkstyle.xml ruleset.
[ERROR] src\main\java\com\example\App.java:[9,5] (javadoc) JavadocMethod: Javadoc コメントがありません。
```

すべてのチェックをパスした場合はコンソールに出力されるのは次の1行だけとなります。

```
[INFO] --- maven-checkstyle-plugin:3.0.0:check (default-cli) @ checkstyle-example ---
```
