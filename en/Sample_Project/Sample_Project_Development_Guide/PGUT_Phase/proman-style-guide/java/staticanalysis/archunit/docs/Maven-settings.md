# How to run ArchUnit with Maven

ArchUnit can be run as a JUnit test by running the `mvn test`.

## Place a filter file to exclude checks

## Include ArchUnit dependencies

Edit the `pom.xml`.

The place to add the configuration is directly under the `dependencies` element.

```xml
  <dependencies>
    <dependency>
      <groupId>com.tngtech.archunit</groupId>
      <artifactId>archunit-junit4</artifactId>
      <version>0.13.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
```

## Running tests with Maven

The tests can be performed with the following commands.

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

If you pass all the tests, the output as follows.

```sh

[INFO] Results:
[INFO]
[INFO] Tests run: xx, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```
