# ArchUnit operation guide

This is a guide for operating ArchUnit in an actual project.

The term "architecture violation" is used this section to mean "violates the architecture rule being tested using ArchUnit".

## Basic Principles

### Principle 1: Resolve all architecture violations

Code that violates the architecture defined in the project is a breeding ground for bugs, and can lead to an unintentional increase in the scope of influence during modifications.  
If the architecture is violated, automated tests will fail. If a test failure is left unattended, not only the architecture violation will be missed, but also the logic problem will be missed and the quality of the source code will not be maintained. Please make sure that all tests succeed.

### Principle 2: Don't reduce the quality of code to eliminate architecture violations

Developers should take action when they find architectural violations, but they should not compromise the quality of the source code by fixing it to avoid the violation.

If Developers are not sure how to deal with it, please consult with experts or project architects for appropriate measures.

### Principle 3: If there is a valid reason for an architectural violation, exclude it from the check

If there is a valid reason, will exclude source code that is an architecture violation from the check.

If the project's architect receives an application from a developer and determines that it is reasonable
Exclude the relevant sections from the checklist.

This operation will prevent violations from being left unattended and developers from being forced to avoid them.

## Notes on Test Execution

### About the classes being tested

ArchUnit allows you to filter the classes included in the classpath/module path by package.

Obviously, classes that are not part of the classpath/module path cannot be tested.

This is the case if have separate modules for each processing method, such as batch and web apps.

If want to test for either module, consider creating a module for ArchUnit testing that relies on both of these.

### About the time it takes to execute the test

Due to the increase in the number of target classes and other factors, the test execution may take a lot of time.

If this is not an unacceptable amount of time, you should consider creating a module for ArchUnit's testing or exclude it from the test runtime.

## Exception registration rule

### How to Exclude Architecture Violations

There are two main methods to exclude architecture violations from testing.

- Describe the exclusion target in the test code.
- Describe in the exclusion configuration file and exclude it from all architecture tests.

If make an exclusion setting, can follow the history later by doing the following.

- Describe the issue management system's issue management number in the comments of the source code.
- Include in the commit comments in the version control system.

### Configuration of the exclusion target class (Describe the exclusion target in the test code)

After specifying the test target, additional conditions can be specified by using the `and` method.

By specifying the class name in the `doNotBelongToAnyOf` method, you can exclude the target class and its internally defined classes from being tested.

``` java
@ArchTest
public static final ArchRule actionClassMustInheritFromBatchAction. =
        ArchRuleDefinition.classes()
        .that().haveSimpleNameEndingWith("Action")
        .and().doNotBelongToAnyOf(PromanExampleAction.class, PromanServiceAction.class) // #12345,12346
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

### Configuring of the exclusion package (Describe the exclusion target in the test code)

You can exclude target packages from testing by specifying them in the `resideOutsideOfPackage` method.
However, you should consider the package structure beforehand, as packages are subject to specific strings.

In the following example, exclude packages containing `common`.

```java
@ArchTest
public  static  final ArchRule noClassesUsingNoDataExceptionInNonInstrumentalPackages =
        ArchRuleDefinition.noClasses()
        .that().resideOutsideOfPackage("..common..") // #1234
        .should().dependOnClassesThat().areAssignableTo(NoDataException.class);
```

If you want to test only a specific package, you can use the `resideInAPackage` method.

### Exclusions by custom rules (Describe the exclusion target in the test code)

Custom rules can be implemented to allow for more granular exclusions.
Refer to the [ArchUnit User Guide](https://www.archunit.org/userguide/html/000_Index.html#_creating_custom_rules) for more information.

### Describe in the exclusion configuration file and exclude it from all architecture tests.

Create the `archunit_ignore_patterns.txt` under `src/test/resources` of the module for architecture testing, and write regular expressions to exclude them.
Can ignore violations of class `some.pkg.LegacyService` by writing the following.

```
# 1234
.*some\.pkg\.LegacyService.*
```

In the file, you can include comments by prefixing them with `#`.

#### Note

Exclusions made in this way will be excluded from all architecture tests.
To minimize the number of tests to be excluded, try to exclude them as much as possible using the aforementioned "Describe the exclusion target in the test code" method.
