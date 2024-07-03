# Method to Run Checkstyle in Maven

This document will guide you on how to configure and run Checkstyle with Maven.

The contents of this document have been verified with Maven 3.9.2.

## Incorporate Checkstyle configuration file into the project

Include [standard-checkstyle.xml](../checkstyle-example/checkstyle/standard-checkstyle.xml) in any folder under the project directory. 
Assume that it is placed in `${basedir}/checkstyle/standard-checkstyle.xml`.

Then place [header.txt](../checkstyle-example/checkstyle/header.txt) and [suppressions.xml](../checkstyle-example/checkstyle/suppressions.xml) in the directory where `standard-checkstyle.xml` is placed.
`header.txt` is used to check whether a predetermined header, such as license information, is listed in the source file. 
Replace the contents of `header.txt` with the header determined by the project.

Once these files are saved, edit `pom.xml`.
Add the `maven-checkstyle-plugin` configuration and describe the file path of the configuration file.

The location to add the configuration is directly under `build`, which is directly under `plugins`.

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-checkstyle-plugin</artifactId>
  <version>3.3.0</version>
  <dependencies>
    <dependency>
      <groupId>com.puppycrawl.tools</groupId>
      <artifactId>checkstyle</artifactId>
      <version>10.12.0</version>
    </dependency>
  </dependencies>
  <configuration>
    <!-- Path to the Checkstyle configuration file -->
    <configLocation>${basedir}/checkstyle/standard-checkstyle.xml</configLocation>
    <propertyExpansion>config_loc=${basedir}/checkstyle/</propertyExpansion>
  </configuration>
</plugin>
```

If the configuration file does not exist in the path described in `pom.xml`, the following error will be seen when checked by the method described below.

```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-checkstyle-plugin:3.3.0:check (default-cli) on project checkstyle-example: Failed during checkstyle execution: Unable to find configuration file at location: C:\example\checkstyle-example/checkstyle/standard-checkstyle.xml: Could not find resource 'C:\example\checkstyle-example/checkstyle/standard-checkstyle.xml'. -> [Help 1]
```

If you see this error, check the file path described in `pom.xml` and the actual location where the file is saved.

## Perform checks with Maven

The check can be performed with the following command.

```sh
mvn checkstyle:check
```

If there is a check violation, the following points are output to the console.

```
[INFO] --- maven-checkstyle-plugin:3.3.0:check (default-cli) @ checkstyle-example ---
[INFO] There is 1 error reported by Checkstyle 10.12.0 with C:\example\checkstyle-example/checkstyle/standard-checkstyle.xml ruleset.
[ERROR] src\main\java\com\example\App.java:[9,5] (javadoc) JavadocMethod: Javadoc コメントがありません。
```

If all checks are passed, only one line is output to the console.

```
[INFO] --- maven-checkstyle-plugin:3.3.0:check (default-cli) @ checkstyle-example ---
```
