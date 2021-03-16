# How to use Find Security Bugs

Find Security Bugs is the SpotBugs plugin.
You can use it by simply configuring the plugin in `pom.xml`.

For information on how to exclude checks and how to perform checks, see [Method to Run SpotBugs in Maven](./Maven-settings.md) and [Method to Collect SpotBugs Execution Results in Jenkins](./Jenkins-settings.md).

## Incorporate the Find Security Bugs plugin into the SpotBugs Maven Plugin

Edit `pom.xml`.

```xml
<plugin>
  <groupId>com.github.spotbugs</groupId>
  <artifactId>spotbugs-maven-plugin</artifactId>
  <version>3.1.3</version>
  <!-- omission  -->
  <configuration>
    <!-- omission  -->
    <plugins>
      <plugin>
        <groupId>com.nablarch.framework</groupId>
        <artifactId>nablarch-testing</artifactId>
        <version>1.2.0</version>
      </plugin>
      <!-- Add the following -->
      <plugin>
        <groupId>com.h3xstream.findsecbugs</groupId>
        <artifactId>findsecbugs-plugin</artifactId>
        <version>1.11.0</version>
      </plugin>
    </plugins>
  </configuration>
</plugin>
```
