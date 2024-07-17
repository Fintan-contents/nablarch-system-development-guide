# Method to Check Coding Conventions

This section is a guide to check the coding rules on your local PC.  
Please follow this before requesting a review.

## Format
- [Java code formatter](https://github.com/Fintan-contents/coding-standards/blob/main/en/java/code-formatter.md)  
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
[INFO] --- spotbugs-maven-plugin:4.5.0.0:check (default-cli) @ spotbugs-example ---
[INFO] BugInstance size is 1
[INFO] Error size is 0
[INFO] Total bugs: 1
[INFO] String objects are compared using == and !=. com.example.App.main(String[]) [com.example.App] applicable location App.java:[line 9] ES_COMPARING_STRINGS_WITH_EQ
```

If all checks are passed, they are output to the console as follows:

```
[INFO] --- spotbugs-maven-plugin:4.5.0.0:check (default-cli) @ spotbugs-example ---
[INFO] BugInstance size is 0
[INFO] Error size is 0
[INFO] No errors/warnings found
```

## Detecting possible architecture violations

### How to run ArchUnit tests with Maven

Run the test in proman-arch-test.
proman-arch-test relies on proman-web and proman-batch to perform architectural testing for all modules in this project.
Before running it, please run `mvn install` with proman-common, proman-web and proman-batch to create the jar file for the test target.
(To perform this test, have also created a jar file on the proman-web.)

The test is performed by the following command.

```sh
mvn test
```

If there is a violation, the test and the target of the violation will be output as follows.

```
[ERROR] Failures:
[ERROR]   ServiceClassRuleTest.DaoContextをfieldに持つ場合private_finalであるがstaticじゃないこと Architecture Violation [Priority: MEDIUM] - Rule 'fields that have raw type nablarch.common.dao.DaoContext and are declared in classes that have simple name ending with 'Service' should be final and should not be private and should be static' was violated (2 times):
Field <com.nablarch.example.proman.web.project.ProjectService.universalDao> does not have modifier STATIC in (ProjectService.java:0)
Field <com.nablarch.example.proman.web.project.ProjectService.universalDao> has modifier PRIVATE in (ProjectService.java:0)
```

If you pass all the tests, the output is printed to the console as follows.

```sh

[INFO] Results:
[INFO]
[INFO] Tests run: xx, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
