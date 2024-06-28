# Checkstyle Rule Explanation

In this section, you will learn about the Checkstyle rules described in the configuration file that is prepared.

The following terms are used in this section.

- OK: It indicates that you have complied with the CheckyStyle rules described in the configuration file.
- Not OK: It indicates that you have not complied with the CheckyStyle rules described in the configuration file. The importance of the rule (error, warning, etc.) does not matter.

The table of contents is given below.

<!-- START doctoc -->

- [AbstractClassName](#abstractclassname)
- [ArrayTypeStyle](#arraytypestyle)
- [AvoidStarImport](#avoidstarimport)
- [AvoidStaticImport](#avoidstaticimport)
- [CatchParameterName](#catchparametername)
- [ClassTypeParameterName](#classtypeparametername)
- [ConstantName](#constantname)
- [CyclomaticComplexity](#cyclomaticcomplexity)
- [EmptyCatchBlock](#emptycatchblock)
- [EqualsAvoidNull](#equalsavoidnull)
- [EqualsHashCode](#equalshashcode)
- [FallThrough](#fallthrough)
- [FileLength](#filelength)
- [Header](#header)
- [HiddenField](#hiddenfield)
- [HideUtilityClassConstructor](#hideutilityclassconstructor)
- [IllegalCatch](#illegalcatch)
- [IllegalThrows](#illegalthrows)
- [InnerAssignment](#innerassignment)
- [InterfaceTypeParameterName](#interfacetypeparametername)
- [MissingJavadocType](#missingjavadoctype)
- [MissingJavadocMethod](#missingjavadocmethod)
- [JavadocMethod](#javadocmethod)
- [JavadocType](#javadoctype)
- [JavadocVariable](#javadocvariable)
- [LambdaParameterName](#lambdaparametername)
- [LocalFinalVariableName](#localfinalvariablename)
- [LocalVariableName](#localvariablename)
- [MemberName](#membername)
- [MethodLength](#methodlength)
- [MethodName](#methodname)
- [MethodTypeParameterName](#methodtypeparametername)
- [MissingSwitchDefault](#missingswitchdefault)
- [ModifiedControlVariable](#modifiedcontrolvariable)
- [NeedBraces](#needbraces)
- [NoFinalizer](#nofinalizer)
- [PackageDeclaration](#packagedeclaration)
- [PackageName](#packagename)
- [ParameterName](#parametername)
- [RecordComponentName](#recordcomponentname)
- [RecordTypeParameterName](#recordtypeparametername)
- [RedundantImport](#redundantimport)
- [SimplifyBooleanExpression](#simplifybooleanexpression)
- [StaticVariableName](#staticvariablename)
- [StringLiteralEquality](#stringliteralequality)
- [TodoComment](#todocomment)
- [TypeName](#typename)
- [UnusedImports](#unusedimports)
- [UnusedLocalVariable](#unusedlocalvariable)
- [UpperEll](#upperell)
- [VisibilityModifier](#visibilitymodifier)

<!-- END doctoc -->

## AbstractClassName

```xml
<module name="AbstractClassName">
  <property name="format" value="^(Abstract.*|.*FormBase)$"/>
</module>
```

Check the name of the abstract class.

Ensure that the name of the abstract class satisfies one of the following rules (OK):

- Start with `Abstract`
- End with `FormBase`

If these conditions are not met, it will be Not OK.

Please follow the rules to unify your coding style.


## ArrayTypeStyle

```xml
<module name="ArrayTypeStyle"/>
```

Check the declaration style of the array.

Use Java style for array declaration (OK).
C style declarations will be Not OK.

Please follow the rules to unify your coding style.

```java
// Java style declaration with parentheses after the element type as follows is OK.
String[] array = ...

// C style declaration with parentheses after the variable name as follows is Not OK.
String array[] = ...
```

## AvoidStarImport


```xml
    <module name="AvoidStarImport"/>
```

Check that `*` is not used in the `import` statement.

Do not use `*` in the `import` statement, and import one class at a time (OK).
If `*` is used in the `import` statement, then it will be Not OK.

If `*` is used in the `import` statement, even classes that are not originally used will be the target of `import`.
Therefore, if there are changes or additions to the package being `imported`, the names will be more likely to clash.

```java
// Although only List and ArrayList are used, the whole java.util package is imported (Not OK).
import java.util.*;

    ///// omitted /////
    
    public void example() {
        List<String> list = new ArrayList<>();
    }
}

```

## AvoidStaticImport

```xml
    <module name="AvoidStaticImport">
      <property name="severity" value="warning"/>
    </module>
```

Check that there are no static import.

Do not use static import, and describe it as `<class name>.<member name>` (OK).
If there is a static import statement, then it will be Not OK.

If you overuse static import, you may mistake external method calls for local method calls,
and can be misleading to the reader of the code.

```java
import static java.lang.System.out;

    ///// omitted /////
    
    /**
     * Example code of AvoidStaticImport.
     */
    public void example() {
        // static import is used (Not OK)
        out.println("Hello World!");

        // static import is not used (OK)
        System.out.println("Hello World!");
    }
}
```

It is possible to exclude the static import of specified classes from the check target. See below for details.

- http://checkstyle.sourceforge.net/config_imports.html#AvoidStaticImport

## CatchParameterName

```xml
<module name="CatchParameterName">
  <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
</module>
```

Check the name of the catch parameter.

Ensure that the name of the catch parameter satisfies the following rule (OK):

- Start with a lowercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

## ClassTypeParameterName

```xml
<module name="ClassTypeParameterName"/>
```

Check the name of the type parameter bound to the class.

Ensure that the name of the type parameter satisfies the following conditions (OK):

- Consist of one uppercase alphabet

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.


```java
/**
 * Type parameter is one uppercase alphabetic character (OK).
 */
public class ClassTypeParameterNameExample<T> {
}

/**
 * Names with more than 2 characters (Not OK).
 */
class NgClassTypeParameterNameExample1<FOO> {
}

/**
 * Lowercase alphabet name (Not OK).
 */
class NgClassTypeParameterNameExample2<t> {
}
```

## ConstantName

```xml
<module name="ConstantName">
  <property name="severity" value="error"/>
  <property name="format" value="^[A-Z](_?[A-Z0-9]+)*$"/>
</module>
```

Check the name of the `static final` field.


Ensure that the `static final` field names satisfies the following rule (OK):

- Start with an uppercase letter, followed by underscores, uppercase letters and Arabic numerals

If this condition is not met, it will be Not OK.

## CyclomaticComplexity

```xml
<module name="CyclomaticComplexity">
  <property name="max" value="10"/>
</module>
```

Measure and check the cyclical complexity of method.

From cyclic complexity, we can consider the state of the code as follows.

| Value   | Description                     |
| ------- | ------------------------------- |
| 1 - 10  | Simple procedure, little risk   |
| 11 - 20 | More complex, moderate risk     |
| 21 - 50 | Complex, high risk              | 
| 50 -    | Untestable code, very high risk |

Cyclic complexity should not exceed the allowable value (OK).
If cyclomatic complexity exceeds the allowed value, it will be Not OK.

```java
public int example(int a, int b, int c, String str) {
    if (a == 1) {                       // +1 (1)
        return 1;
    } else if (a == b && a == c) {      // +2 (3)
        if (b < 1) {                    // +1 (4)
            return 2;
        }
    }
    try {
        int d = Integer.parseInt(str);
        if (a == d) {                   // +1 (5)
            return switch (d) {
                case 2 -> 20;           // +1 (6)
                case 3 -> 30;           // +1 (7)
                case 4 -> 40;           // +1 (8)
                default -> 99;          // +1 (9)
            };
        }
    } catch (NumberFormatException e) { // +1 (10)
        throw new IllegalArgumentException(e);
    }
    return a < 0 ? -1 : 1;              // +1 (11)
}
```

## EmptyCatchBlock


```xml
    <module name="EmptyCatchBlock"/>
```

Check that there are no empty `catch` clauses.

If there is no processing in the `catch` section, please describe the reason why processing is not required with a comment (OK).
If there is an empty `catch` clause, then it will be Not OK.

An empty `catch` clause may have been written just to eliminate compile errors.
This eliminates exceptions and errors that should be handled properly making it difficult to determine the cause when a failure occurs.
To be able to distinguish between `catch` clauses that do not require processing and `catch` clauses to avoid errors,
Include a good reason in the `catch` section why no action is required.

In the default setting, if any comment is described in the `catch` section as follows, then it is OK.

```java
    public void example() {

        try {
            Files.readAllLines(new File("hoge.txt").toPath());
        // Empty catch clause (Not OK).
        } catch (IOException e) {
        }


        try {
            Files.readAllLines(new File("hoge.txt").toPath());
        } catch (IOException e) {
            // If you put a comment in the catch clause, it will not be a violation of the Checkstyle (OK).
        }
    }
```

## EqualsAvoidNull

```xml
    <module name="EqualsAvoidNull">
      <property name="severity" value="error"/>
    </module>
```

Check how variables and literals are compared by the `equals` method.

When comparing variables and literals with the `equals` method, keep the literal on the left side (OK).
Conversely, if the right side is a variable and the left side is a literal, the result is Not OK.

If the variable is on the left, a `NullPointerException` is thrown if the variable is `null`.
Placing the literal on the left side avoids the `NullPointerException`.

```java
//OK as the literal is the receiver of the equals method
if ("literal".equals(value1)) {
    System.out.println("equality");
}

//Not OK because the variable is a receiver of equals
if (value1.equals("literal")) {
    System.out.println("equality");
}

//OK between variables
if (value1.equals(value2)) {
    System.out.println("equality");
}
```

## EqualsHashCode


```xml
    <module name="EqualsHashCode">
      <property name="severity" value="warning"/>
    </module>
```

Check that the `equals` and `hashCode` methods are paired.

The `equals` method or the `hashCode` method should not override either or both (OK).
Not OK if only one is overridden.

In general, there are many cases where the `equals` method is overridden but not the `hashCode` method.
This typically leads to performance degradation when retrieving elements from the collection.

See below for overriding the `equals` and `hashCode` methods.

- https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#equals(java.lang.Object)
- https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#hashCode()


## FallThrough

```xml
    <module name="FallThrough"/>
```

Check that there is no FallThrough in the `switch` statement.

If there is at least one statement in `case`, please break explicitly (OK).
If a `case` having at least one statement falls through, then it is Not OK.

Avoid such fallthroughs, as they can easily introduce bugs that are not easy to understand.

```java
    public void badExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
            // Processing of gold members...
            bonus = 1000;
        case SILVER:
            // Fallthrough of case GOLD: (forgot to break caused the bug).
            // Processing for silver members ...
            bonus = 100;
            break;
        default:
            // Processing in other cases...
            bonus = 10;
        }
    }
```

FallThrough without statements in `case` is acceptable (OK).

```java
    public void notBadExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
        case SILVER:
            // Processing for gold or silver members ...
            bonus = 500;
            break;
        default:
            // Processing in other cases...
            bonus = 10;
        }
    }
```


## FileLength

```xml
  <module name="FileLength">
    <property name="severity" value="error"/>
  </module>
```

Check the number of lines in the file.

Make sure that the number of lines in a Java file is no more than 2000 lines (OK).
If the number of lines exceeds 2000, then it will be Not OK.

A Java file that is too large is expected to have a class with a large number of fields or a large number of methods.
Such classes are cumbersome and less maintainable.

When coding, try to keep your classes compact.


## Header

```xml
  <module name="Header">
    <property name="headerFile" value="${config_loc}header.txt"/>
    <property name="severity" value="warning"/>
  </module>
```

Check for the specified header at the beginning of the file.

The content to be written in the header is set by specifying the file with the `headerFile` property.
This is the rule to use if you want to include a common header, such as the system name and copyright, in all files without omission.


## HiddenField

```xml
    <module name="HiddenField">
      <property name="severity" value="error"/>
      <property name="ignoreConstructorParameter" value="true"/>
      <property name="ignoreSetter" value="true"/>
    </module>
```

Check if a local variable is hiding a field.

Make sure that the name of the local variable is not the same as the name of the field.
If a local variable hides a field, there is a risk of causing problems such as, the code written to refer to a field is referring to a local variable.

```java
public class HiddenFieldExample {

    private String foo;

    //Using the same name as the field for the constructor argument is OK
    public HiddenFieldExample(String foo) {
        this.foo = foo;
    }

    //Using the same name as the field for the argument of setter is OK
    public void setFoo(String foo) {
        this.foo = foo;
    }

    //Method other than setter is Not OK for the argument with the same name as the field
    public void method1(String foo) {
    }

    public void method2() {
        //Local variable with the same name as the field is Not OK (NG)
        String foo = "NG";
    }
}
```


## HideUtilityClassConstructor

```xml
    <module name="HideUtilityClassConstructor">
      <property name="severity" value="error"/>
    </module>
```

Check whether the constructor of the utility class has been published.

A utility class has only `static` methods and `static` fields.
Since instantiation is not required for the utility class, the constructor should be `private`.

```java
public class HideUtilityClassConstructorExample {

    /**
     * Constructor is published for classes of the same package (Not OK)
     *
     */
    HideUtilityClassConstructorExample() {
        //nop
    }

    public static long count(final char c, final String s) {
        ...
    }
}
```
## IllegalCatch

```xml
<module name="IllegalCatch">
  <property name="severity" value="error"/>
  <property name="illegalClassNames" value="java.lang.Exception, java.lang.Throwable, java.lang.RuntimeException, java.lang.Error"/>
</module>
```

Check that the specified exception/error has not been `caught`.

Do not `catch` the following exceptions/errors in your application.

- `java.lang.Exception`
- `java.lang.Throwable`
- `java.lang.RuntimeException`
- `java.lang.Error`

If `catch` is used for these, then it will be Not OK.

These are generic and `catch` for these should be handled by the framework.
If you want to `catch` the exception in your application, `catch` a more specific type (OK).

## IllegalThrows

```xml
<module name="IllegalThrows">
  <property name="severity" value="error"/>
  <property name="illegalClassNames" value="java.lang.Exception, java.lang.Throwable, java.lang.RuntimeException, java.lang.Error"/>
</module>
```

Check that the specified exception/error is not declared in `throws`.

Do not `throw` the following exceptions/errors in your application.

- `java.lang.Exception`
- `java.lang.Throwable`
- `java.lang.RuntimeException`
- `java.lang.Error`

If these are declared in `throws`, then it will be Not OK.

These are generic and lack the information to identify the cause of the exception or error.
If an exception is `thrown` in the application, be sure to select a specific type and declare the specific type in `throws` (OK).


## InnerAssignment

```xml
<module name="InnerAssignment">
  <property name="tokens" value="ASSIGN"/>
</module>
```

Check that assignments to variables are made at the top level.

Make assignments to variables at the top level, and not in the middle of an expression (OK).
Substitution in the middle of the expression is Not OK.

If an assignment is made in the middle of an expression, it will be difficult to identify where the assignment is made, which will reduce readability.

```java
// Variable i has been assigned at the top level (OK)
int i = 2;
String s = Integer.toString(i);

// Variable i has been assigned in the expression (Not OK)
int i;
String s = Integer.toString(i = 2);
```

## InterfaceTypeParameterName

```xml
<module name="InterfaceTypeParameterName"/>
```

Check the name of the type parameter bound to the interface.

Ensure that the name of the type parameter satisfies the following conditions (OK):

- Consist of one uppercase alphabet

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.


```java
/**
 * Type parameter is one uppercase alphabetic character (OK).
 */
public interface InterfaceTypeParameterNameExample<T> {
}

/**
 * Names with more than 2 characters (Not OK).
 */
interface NgInterfaceTypeParameterNameExample1<FOO> {
}

/**
 * Lowercase alphabet name (Not OK).
 */
interface NgInterfaceTypeParameterNameExample2<t> {
}
```

## MissingJavadocType

Check for the existences of Javadoc comments of class and enumeration type.

Make sure to include Javadoc comments (OK).
Javadoc comments are important information for code readers.

```xml
    <module name="MissingJavadocType">
      <property name="scope" value="private"/>
      <property name="severity" value="error"/>
    </module>
```

##  MissingJavadocMethod

Check for the existences of Javadoc comments of method.

Make sure to include Javadoc comments (OK).
Javadoc comments are important information for code readers.

```xml
    <module name="MissingJavadocMethod">
      <property name="scope" value="private"/>
      <property name="severity" value="error"/>
    </module>
```

## JavadocMethod

Check the Javadoc comment contents of the method.

Please include @param and @return in your Javadoc comments (OK).
If it is not written, it will be not OK.


```java
    /**
     * Double the given number.
     *
     * There is a Javadoc comment, but @param, @return are missing (Not OK).
     *
     * @throws tag can be described even if there is no throws declaration in the method
     * (this does not violate the checkstyle)
     *
     * @throws IllegalArgumentException If the argument is negative
     */
    public int noParamAndReturnTag(int number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
        return number * 2;
    }
```

## JavadocType

```xml
    <module name="JavadocType">
      <property name="severity" value="error"/>
    </module>
```

Check Javadoc comments for types (classes, interfaces, enums, annotations).

Make sure to include Javadoc comments in the type (OK).
If there is no Javadoc comment, it will be Not OK.

The Javadoc comment for types should include an overview of the type's features and notes on its specifications.
This is important information for source code readers.

```java
/**
 * Example code of JavadocType.
 *
 * @author example
 * @param <T> An example of a type parameter
 * @since 1.0.0
 */
public class JavadocTypeExample<T> {
}

class NgJavadocTypeExample1 { // There is no javadoc comment (Not OK).
}

/**
 * There is a Javadoc comment, but there is no @param for type parameter T (Not OK).
 * 
 * @author example
 */
class NgJavadocTypeExample2<T> {
}
```


## JavadocVariable

```xml
    <module name="JavadocVariable">
      <property name="severity" value="info"/>
      <property name="scope" value="public"/>
    </module>
```

Check Javadoc comments for member variables.

Write Javadoc comment in `public` member variable (OK).
If there is no Javadoc comment, it will be Not OK.

Javadoc comments are important information for code readers.

In the above settings, variables in the `public` scope will be checked.

```java

    public static final String HELLO = "こんにちは";    // There is no javadoc comment (Not OK).

    /** Goodbye greeting */
    public static final String GOODBYE = "さようなら";  // There is a javadoc comment (OK).
```


## LambdaParameterName

```xml
<module name="LambdaParameterName"/>
```

Check the name of the lambda expression argument.

Ensure that the name of the lambda expression argument satisfies the following conditions (OK):

- Start with a lowercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

```java
// An uppercase alphabet has been used at the beginning (Not OK).
Function<String, String> ng1 = BadName -> "NG";

// Underscore is used (Not OK).
Function<String, String> ng2 = bad_name -> "NG";

// Name is according to the rules (OK).
Function<String, String> ok = goodName -> "OK";
```


## LocalFinalVariableName


```xml
    <module name="LocalFinalVariableName"/>
```

Check for `final` qualified local variable names.

Name `final` qualified local variable names using lowerCamelCase (OK).
If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

```java
    public void example() {
        // The final qualified local variable is named with UPPER_SNAKE_CASE (Not OK).
        final String VERY_IMPORTANT_MESSAGE = "Hello";

        // The final qualified local variable is named with lowerCamelCase (OK).
        final String veryImportantMessage = "Goodbye";
    }
```

## LocalVariableName


```xml
<module name="LocalVariableName"/>
```

Check the name of the local variable.

Ensure that the name of the local variable satisfies the following conditions (OK):

- Start with a lowercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.


```java
/**
 * Not OK because the first letter is a capital letter
 */
private static String BadName;

/**
 * Not OK due to underscore
 */
private static String bad_name;

/**
 * OK because the name is according to the rules
 */
private static String goodName;
```

## MemberName

```xml
<module name="MemberName"/>
```

Check the name of the instance field.

Ensure that the name of the instance field satisfies the following rule (OK):

- Start with a lowercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

```java
/**
 * Not OK because the first letter is a capital letter
 */
private String BadName;

/**
 * Not OK due to underscore
 */
private String bad_name;

/**
 * OK because the name is according to the rules
 */
private String goodName;
```

## MethodLength

```xml
    <module name="MethodLength">
      <property name="severity" value="error"/>
    </module>
```

Check the number of lines in the method.

Make sure that the number of lines for one method is less than 150 (OK).
If there are methods with more than 150 lines, then it will be Not OK.

Though the number of lines in a method is not the only measure of quality, methods that are too large can still be improved.

When coding, try to keep your method compact.

Note that the number of lines in the method is counted not only in the body block but also in declarations.

## MethodName

```xml
<module name="MethodName">
  <property name="severity" value="error"/>
</module>
```

Check the name of the method.

Ensure that the name of the method satisfies the following rule (OK):

- Start with a lowercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

## MethodTypeParameterName

```xml
<module name="MethodTypeParameterName"/>
```

Check the name of the type parameter bound to the method.

Ensure that the name of the type parameter satisfies the following conditions (OK):

- Consist of one uppercase alphabet

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.


```java
/**
 * OK example of MethodTypeParameterName.
 * 
 * @param <T> Type parameter is one uppercase alphabetic character (OK).
 */
public <T> void okMethod() {
}

/**
 * Not OK example of MethodTypeParameterName.
 * 
 * @param <FOO> Name with more than 2 characters (Not OK).
 * 
 */
public <FOO> void ngMethod1() {
}

/**
 * Not OK example of MethodTypeParameterName.
 * 
 * @param <t> Lowercase alphabet name (Not OK).
 * 
 */
public <t> void ngMethod2() {
}
```

## MissingSwitchDefault

```xml
    <module name="MissingSwitchDefault"/>
```

Check that the `switch` statement has a `default` label.

Make sure to include the `default` label in the `switch` statement (OK).
If there is no `default` label, it will be Not OK.

The intention is to prevent bugs caused by forgetting to write defaults and omission of default cases.

```java
//There is default (OK)
switch (value) {
case 0:
    System.out.println("0");
    break;
case 1:
    System.out.println("1");
    break;
default:
    System.out.println("other");
    break;
}

//no default (Not OK)
switch (value) {
case 0:
    System.out.println("0");
    break;
case 1:
    System.out.println("1");
    break;
case 2:
    System.out.println("2");
    break;
}
```

## ModifiedControlVariable

```xml
<module name="ModifiedControlVariable"/>
```

Check that the value of the loop counter has not been changed in the block of the `for` statement.

Change the loop counter in a legacy `for` statement in a `for` statement and not in a block (OK).
In the legacy `for` statement, if the value of the loop counter is changed in the statement block, the result is Not OK.

Changing the loop counter in a statement block makes it difficult to follow the flow of processing and reduces readability.


```java
// The value of the loop counter has not been changed in the statement block (OK)
for (int i = 0; i < size; i++) {
    System.out.println(i);
}

// The value of the loop counter is changed in the statement block (Not OK)
for (int i = 0; i < size; i++) {
    i++;
    System.out.println(i);
}
```

## NeedBraces

```xml
<module name="NeedBraces">
  <property name="severity" value="error"/>
</module>
```

Check that there are curly braces around the code block.

For `if` and `for` statements, enclose the statement in `{` and `}` to form a block (OK).
If a block is formed, it will be Not OK.

Please follow the rules to unify your coding style.

```java
// Because the statement is in a block OK
if (foo) {
    doMethod();
}

// Statement is not block Not OK
if (foo)
    doMethod();
```

## NoFinalizer


```xml
    <module name="NoFinalizer">
      <property name="severity" value="error"/>
    </module>
```

Check the `finalize` method.

Do not override the `finalize` method (OK).
Not OK if the `finalize` method is overridden.


Various problems have been pointed out with finalizer overrides.
- https://www.jpcert.or.jp/java-rules/met12-j.html

Normally, there is no need to override the finalizer.
Use `java.lang.AutoCloseable` or `try-with-resources` for releasing some resources.

```java
    // The finalize method has been overridden (Not OK).
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        // (Resource release processing)
    }
```


## PackageDeclaration

```xml
<module name="PackageDeclaration">
  <property name="severity" value="error"/>
</module>
```

Check if there are any package declarations.

Include the package declaration in the class interface (OK).
Avoid package declarations, that is, do not define class interfaces for the default package. Not OK if class/interface is defined in the default package.

Classes in the default package cannot be imported from classes in other packages.
Do not use the default package, as there is no merit of using except for simple usage such as in samples.

## PackageName

```xml
<module name="PackageName">
  <property name="severity" value="error"/>
  <property name="format" value="^[a-z]+(\.[a-z_][a-z0-9_]*)*$"/>
</module>
```

Check the name of the package.

Ensure that the name of the package satisfies the following rules (OK).

- Start with a lowercase letter, followed by periods, lowercase letters, underscores and Arabic numerals
- A lowercase letter or underscore immediately after the period

If these conditions are not met, then it will be Not OK.

Please follow the rules to unify your coding style.

## ParameterName

```xml
<module name="ParameterName"/>
```

Check the name of the parameter.

Ensure that the name of the parameter satisfies the following rule (OK):

- Start with a lowercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

```java
 * @param BadName Not OK because the first letter is an uppercase alphabet
 * @param bad_name Not OK as underscore is present
 * @param goodName OK because the name is according to the rules
 */
public void example(String BadName, String bad_name, String goodName) {
```

## RecordComponentName

```xml
<module name="RecordComponentName"/>
```

Check the name of the record component.

Ensure that the name of the record component satisfies the following rule (OK):

- Start with a lowercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

## RecordTypeParameterName

```xml
<module name="RecordTypeParameterName"/>
```

Check the name of the type parameter bound to the record class.

Ensure that the name of the type parameter satisfies the following conditions (OK):

- Consist of one uppercase alphabet

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

## RedundantImport

```xml
<module name="RedundantImport"/>
```

Check for redundant `import`.

Delete the redundant `import` statement (OK).
If there are two or more overlapping `import` or `import` of a class in the `java.lang` package, and then `import` of a class in the same package, it will be Not OK.

Redundant descriptions reduce the readability of the code.


## SimplifyBooleanExpression

```xml
<module name="SimplifyBooleanExpression"/>
```

Check for redundant `boolean` expressions.

Do not use redundant `boolean` expressions.
If there is a redundant expression as shown below, it will be Not OK.

```java
b == true
b || true
!false
```

These redundant expressions can be replaced with the following simple expressions (OK).

```java
b
true
true
```

## StaticVariableName

```xml
<module name="StaticVariableName"/>
```

Check the name of the field that is `static` and no `final`.

Ensure that the name of the field that is `static` and no `final` satisfies the following rule (OK):

- Start with a lowercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

```java
/**
 * Not OK because the first letter is a capital letter
 */
private static String BadName;

/**
 * Not OK due to underscore
 */
private static String bad_name;

/**
 * OK because the name is according to the rules
 */
private static String goodName;
```

## StringLiteralEquality

Check how strings are compared.

Use the `equals` method to compare strings (OK).

Do not compare strings with `==` or `!=`.
If string comparisons have been used, then it is Not OK.


When a string comparison is performed using a comparison operator, unexpected behavior may occur
because the objects are compared based on whether the strings are equivalent, not on whether they are equal.

```xml
    <module name="StringLiteralEquality">
      <property name="severity" value="error"/>
    </module>
```

```java
        // Strings are being compared with == (Not OK).
        if (s == "something") {
            // matched!
        }

        // Strings are compared using equals (OK).
        if (s.equals("something")) {
            // matched!
        }
```


## TodoComment

```xml
    <module name="TodoComment">
      <property name="severity" value="info"/>
      <property name="format" value="TODO\s+"/>
    </module>
```

Check that there are no TODO comments in the source code comments.

Ensure that there is no omission of the TODO comment or omission of deletion of the processed comment (OK).
Existence of a TODO comment is a violation.

The purpose is not to ban TODO comments. The original purpose of this check is to eliminate the omission of TODO comments.

```java
    // There is a TODO comment in the source code.
    // TODO processing is complicated and needs refactoring later!
    doSomeComplexProcess();

```


## TypeName

```xml
<module name="TypeName"/>
```

Check the name of class, interface, enum, annotation, record.

Verify that the name meets the following rules (OK):

- Start with a uppercase letter, followed by lowercase and uppercase letters, and Arabic numerals

If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.


## UnusedImports

```xml
    <module name="UnusedImports"/>
```

Check for unused `import` statements.

Delete the unused `import` statements (OK).
If unused `import` statement are present, then it will be Not OK.

Unused `import` statements have no effect on operation,
such statements should be deleted as it may cause the reader of the code to misunderstand the class dependencies.

```java
// Unused import statement (NG).
import java.math.BigDecimal;

// Used import statement
import java.util.regex.Pattern;

public class UnusedImportsExample {

    private static Pattern alphabet = Pattern.compile("^[a-zA-Z]+$");
```

## UnusedLocalVariable

```xml
    <module name="UnusedLocalVariable"/>
```

Check for unused local variables.

Delete the unused local variables (OK).
If unused local variables are present, then it will be Not OK.

Unused local variables have no effect on operation,
such statements should be deleted as extra burden on the reader of the code .

## UpperEll



```xml
    <module name="UpperEll"/>
```

Check for integer literal characters.

Use uppercase `L` as a suffix for integer literals (OK).
If lowercase `l` is used, then it will be Not OK.

Uppercase `L` should be used because it is difficult to distinguish lowercase `l` from `1`.

```java
        // Lower case `I` is used as a as a suffix for integer literals (Not OK).
        long bad = 1l;

        // Uppercase 'L' has been used for integer literal suffix (OK).
        long good = 1L;
```


## VisibilityModifier

```xml
<module name="VisibilityModifier">
  <property name="severity" value="info"/>
</module>
```

Check the visibility of class members.

All fields should be `private` except for `static final` constants (OK).
In the case of a field that is not `private`, then it will be Not OK.

This rule is intended to enforce compliance with encapsulation.
