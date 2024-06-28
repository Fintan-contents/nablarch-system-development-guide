# How to run ArchUnit with Maven

ArchUnit can be run as a JUnit test by running the `mvn test`.

## Place a filter file to exclude checks

## Include ArchUnit dependencies

Edit `pom.xml` to add ArchUnit as a dependency.

```xml
<dependency>
    <groupId>com.tngtech.archunit</groupId>
    <artifactId>archunit-junit5</artifactId>
    <version>1.0.1</version>
    <scope>test</scope>
</dependency>
```

## Running tests with Maven

The tests can be performed with the following commands.

```sh
mvn test
```

If there is a violation, the test and the target of the violation will be output as follows.

```
[ERROR] ExampleRuleTest.Actionがpublicであること  Time elapsed: 0.409 s  <<< FAILURE!
java.lang.AssertionError: 
Architecture Violation [Priority: MEDIUM] - Rule 'classes that have simple name ending with 'Action' should be public' was violated (1 times):
Class <com.example.ExampleAction> does not have modifier PUBLIC in (ExampleAction.java:0)
	at com.tngtech.archunit.lang.ArchRule$Assertions.assertNoViolation(ArchRule.java:94)
	at com.tngtech.archunit.lang.ArchRule$Assertions.check(ArchRule.java:86)
	...
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
