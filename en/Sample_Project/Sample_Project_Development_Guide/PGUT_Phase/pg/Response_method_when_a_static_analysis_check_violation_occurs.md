# Response Method When a Static Analysis Check Violation Occurs

## Principle concept

Follows the operation guide of the coding convention.

- [Checkstyle operation guide](../../PGUT_Phase/proman-style-guide/java/staticanalysis/checkstyle/docs/Ops-Rule.md)
- [SpotBugs operation guide](../../PGUT_Phase/proman-style-guide/java/staticanalysis/spotbugs/docs/Ops-Rule.md)
- [ArchUnit operation guide](../../PGUT_Phase/proman-style-guide/java/staticanalysis/archunit/docs/Ops-Rule.md)

## Specific measures for this project

In principle, resolve all warnings. 
If the warning cannot be resolved or there is a reason that it is better not to resolve the warning forcefully, 
request for exclusion with the following procedure.

- The developer issues a Redmine ticket stating the exclusion request and changes the person in charge to the architect
- The architect checks the contents
  - If the description is valid, the architect sets the Checkstyle, SpotBugs, ArchUnit exclusion and returns the ticket to the developer
  - If the content is not appropriate, sends the ticket back to the developer
- The developer confirms that the exclusion has been completed and closes the ticket

### Checkstyle exclusion setting method

In this project, "exclusion setting by line comment" is performed. 
This is because the detailed exclusion configuration are not possible in the case of "exclusion settings using the exclusion configuration file",
and unintended violations may be excluded.

``` java
   private int counter_;  // SUPPRESS CHECKSTYLE #12345
```

Write the Redmine ticket number at the end of the comment.
This allows the reason and background of the exclusion in Redmine tickets to be tracked.

### SpotBugs exclusion setting method

Perform "Exclusion by filter file". 
Since the exclusion setting uses method units, split the method in advance to minimize the exclusion range.
If large methods are included in exclusion settings, then other violations may be included and can be the cause for oversight.

When describing the exclusion settings, enter the Redmine ticket number in the comment.

``` xml
    <!-- #12345 -->
    <Bug pattern="UPU_UNPUBLISHED_API_USAGE"/>
    <Class name="com.example.Foobar"/>
    <Method name="baz" params="java.lang.String, int"/>
```

### Change the settings of unauthorized API

If there are APIs that should be added to the whitelist in the project using the configuration of unauthorized APIs, add them to the configuration file.
In particular, if libraries other than Nablarch are used in the project, authorized API will be required to be changed.

However, if only a specific class uses the API, do not add it to the whitelist and perform exclusion settings of SpotBugs. 
Adding such APIs to the whitelist makes it available to any class.

### How to Exclude ArchUnit Test Targets

In this project, "exclude target class setting" is performed for the test.
This is because in ArchUnit, targets are narrowed down by classes, methods, constructors, fields, etc. under the target package, but the targets that can be generically excluded for these are classes.

Also, the settings in the exclusion configuration file (`archunit_ignore_patterns.txt`) are not used in this project as they will be ignored in all tests.

Note that the method to specify the test target is different between the case of a class and other cases.

If the test target is a class (including layers), by specifying the class name in the `doNotBelongToAnyOf` method, you can exclude the target class and its internally defined classes from being tested.

``` java
@ArchTest
public static final ArchRule actionClassMustInheritFromBatchAction. =
        ArchRuleDefinition.classes()
        .that().haveSimpleNameEndingWith("Action")
        .and().doNotBelongToAnyOf(
            PromanExampleAction.class,    // #12345
            PromanServiceAction.class)    // #12346
        .should().beAssignableTo(BatchAction.class);
```

To exclude fields, methods, etc. defined within a specific class, you can use the `areNotDeclaredIn` method.

``` java
@ArchTest
public static final ArchRule methodsThatTakeDaoContextAsAnArgumentMustBePackagePrivate =
        ArchRuleDefinition.methods()
        .that().haveRawParameterTypes(DaoContext.class)
        .and().areNotDeclaredIn(PromanExamAction.class) // #1234
        .should().bePackagePrivate();
```

And in any case, should write only excluded class in one line and write Redmine ticket number in line comment.
In this way, can trace the reason why excluded class and the reason why excluded it by Redmine ticket.