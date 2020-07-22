# Method to Check Coding Conventions

This section is a guide to check the coding rules on your local PC.  
Please follow this before requesting a review.

## Format
- [Java code formatter](https://github.com/nablarch-development-standards/nablarch-style-guide/blob/master/java/code-formatter.md)  
  Please set the formatter according to the explanation.

## Check coding conventions
Check using one of the following:
- [CheckStyle-IDEA - Plugins | JetBrains](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea)  
  The rules used for checking are located in /proman-project/tools/static-analysis/checkstyle.
- Maven

### Method to run Checkstyle in Maven
The check can be performed with the following command.

```sh
mvn checkstyle:check
```

If there is a check violation, the following points are output to the console:

```
[INFO] --- maven-checkstyle-plugin:3.0.0:check (default-cli) @ checkstyle-example ---
[INFO] There is 1 error reported by Checkstyle 6.18 with C:\example\checkstyle-example/checkstyle/nablarch-checkstyle.xml ruleset.
[ERROR] src\main\java\com\example\App.java:[9,5] (javadoc) JavadocMethod: Javadoc コメントがありません。
```

If all checks are passed, only one line is output to the console:

```
[INFO] --- maven-checkstyle-plugin:3.0.0:check (default-cli) @ checkstyle-example ---
```



## Detecting codes with potential bugs
### Method to run SpotBugs in Maven
The check can be performed with the following command.

```sh
mvn spotbugs:check
```

If there is a check violation, the following points are output to the console:

```
[INFO] --- spotbugs-maven-plugin:3.1.3:check (default-cli) @ spotbugs-example ---
[INFO] BugInstance size is 1
[INFO] Error size is 0
[INFO] Total bugs: 1
[INFO] String objects are compared using == and !=. com.example.App.main(String[]) [com.example.App] applicable location App.java:[line 9] ES_COMPARING_STRINGS_WITH_EQ
```

If all checks are passed, they are output to the console as follows:

```
[INFO] --- spotbugs-maven-plugin:3.1.3:check (default-cli) @ spotbugs-example ---
[INFO] BugInstance size is 0
[INFO] Error size is 0
[INFO] No errors/warnings found
```
