# ArchUnit Commentary

ArchUnit is a library for testing your architecture.
The tests you implement will vary depending on the architectural design of the project.
Here is a description of how ArchUnit can be used to describe frequently occurring test patterns in Nablarch.

Refer to the [ArchUnit User Guide -> What to Check](https://www.archunit.org/userguide/html/000_Index.html#_what_to_check) for information on what can be checked with ArchUnit.

## About basic contents


### Basic format

The following format is basic.

```
classes that ${PREDICATE} should ${CONDITION}
```

This checks that a class that is `${PREDICATE}` becomes `${CONDITION}`.

The test code based on this format is shown below.
The check content is "The class whose class name ends in `Action` is `public`".

```java
ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action").should().bePublic();
```

If this check is done and the class name ends in `Action` but it is a package private,  in violation.

By the way, if the above content is a test that can be executed by JUnit4, it would look like the following.

```java
 
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman")
public class ActionRuleTest {

  @ArchTest
  public static final ArchRule ActionMustBePublic = ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action").should().bePublic();

}
```

#### Note

This rule checks if the class name ends with `Action`.

If the class name does not end in `Action`, it is not checked, of course.

### Subject to check

In the above example, classes are checked, but other classes such as methods, constructors, fields, etc. can also be checked.

Can also check that "no such class exists" by using `ArchRuleDefinition.noClasses()` etc.

### Check range

ArchUnit specifies a test scope by specifying `@AnalyzeClasses` in the test class.

The following is an example of a package check.

```java
@AnalyzeClasses(packages = "com.nablarch.example")
public class ExampleRuleTest {
    // omitted
}
```

This does a range check on the `com.nablarch.example` package on the classpath/module path.


## Frequently used checks

Here are three frequently used checks, along with some code examples.

- Declaration check
- Dependency check
- Layer check

### Declaration check

The declaration check involves checking classes, methods, field qualifiers, types, etc.

For example, the following example checks that the `DaoContext` field is private and final, but not static.

It should be noted that if the qualifier does not have to be given at the time of declaration, it should include a check "not xx". (The following example checks that it is not static.)

```java
ArchRuleDefinition.fields().that().haveRawType(DaoContext.class)
                .should().bePrivate()
                .andShould().beFinal()
                .andShould().notBeStatic();
```

In the following example, if the class name ends in Action, we check that it extends BatchAction.

```java
ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action")
                .should().beAssignableTo(BatchAction.class);
```

The last example checks that there is no class that throws an `IOException`.

Only the methods which have `throws` declared can be included here.
Run-time exceptions where `throws` is not declared cannot be checked.

```java
ArchRuleDefinition.noMethods().should().declareThrowableOfType(IOException.class);
```

### Dependency check

The dependency check checks dependencies between classes.

In the first example, it checks that classes in the service package must not depend on classes in the form package.

Since it's a negation, it uses `noClasses()` to check that there are no such classes.  
(Please refer to [this](https://javadoc.io/doc/com.tngtech.archunit/archunit/latest/com/tngtech/archunit/base/PackageMatcher.html) for information about the package designations.)

```java
ArchRuleDefinition.noClasses().that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..form..");
```

### Layer check

Layer checks are written in a special format that does not fit into the [Basic Format](#Basic-format).

Define the layer names, for example, by package, and check the dependencies between the defined layers.

For example, here is an example when the following three layers exist.

- Action package to place the controller
- A service package that contains logic that is only used by the controller
- A form package that transfers screen input to the controller

Only the action package can depend on other packages, and the dependency on its own package is also prohibited.

```java
Architectures.layeredArchitecture()
    .layer("Action").definedBy("..action..")
    .layer("Service").definedBy("..service..")
    .layer("Form").definedBy("..form..")
    .whereLayer("Action").mayNotBeAccessedByAnyLayer()
    .whereLayer("Service").mayOnlyBeAccessedByLayers("Action")
    .whereLayer("Form").mayOnlyBeAccessedByLayers("Action");
```

After defining each layer name in the package, check from which layer each is accessible.

## If this is not possible with the APIs provided by ArchUnit

By implementing custom rules, you can perform checks that are not possible with the provided API.  
For more information, please refer to the [ArchUnit User Guide](https://www.archunit.org/userguide/html/000_Index.html#_creating_custom_rules).