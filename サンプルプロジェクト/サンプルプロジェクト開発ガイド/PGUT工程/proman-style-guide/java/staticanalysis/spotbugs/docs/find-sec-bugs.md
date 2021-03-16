# Find Security Bugsの利用方法

Find Security BugsはSpotBugsのプラグインです。
`pom.xml`にプラグインの設定をするだけで利用可能です。

チェックの除外方法やチェックの実施方法は[MavenでのSpotBugsの実行方法](./Maven-settings.md)、[JenkinsでのSpotBugs実行結果収集方法](./Jenkins-settings.md)を参照してください。

## SpotBugs Maven Plugin に Find Security Bugs pluginを組み込む

`pom.xml`を編集します。

```xml
<plugin>
  <groupId>com.github.spotbugs</groupId>
  <artifactId>spotbugs-maven-plugin</artifactId>
  <version>3.1.3</version>
  <!-- 中略 -->
  <configuration>
    <!-- 中略 -->
    <plugins>
      <plugin>
        <groupId>com.nablarch.framework</groupId>
        <artifactId>nablarch-testing</artifactId>
        <version>1.2.0</version>
      </plugin>
      <!-- 以下を追加 -->
      <plugin>
        <groupId>com.h3xstream.findsecbugs</groupId>
        <artifactId>findsecbugs-plugin</artifactId>
        <version>1.11.0</version>
      </plugin>
    </plugins>
  </configuration>
</plugin>
```
