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
- [ClassTypeParameterName](#classtypeparametername)
- [ConstantName](#constantname)
- [EmptyCatchBlock](#emptycatchblock)
- [EqualsAvoidNull](#equalsavoidnull)
- [EqualsHashCode](#equalshashcode)
- [FallThrough](#fallthrough)
- [FileLength](#filelength)
- [FileTabCharacter](#filetabcharacter)
- [GenericWhitespace](#genericwhitespace)
- [Header](#header)
- [HiddenField](#hiddenfield)
- [HideUtilityClassConstructor](#hideutilityclassconstructor)
- [IllegalCatch](#illegalcatch)
- [IllegalThrows](#illegalthrows)
- [IllegalType](#illegaltype)
- [Indentation](#indentation)
- [InnerAssignment](#innerassignment)
- [InterfaceTypeParameterName](#interfacetypeparametername)
- [JavadocMethod](#javadocmethod)
- [JavadocType](#javadoctype)
- [JavadocVariable](#javadocvariable)
- [LambdaParameterName](#lambdaparametername)
- [LeftCurly](#leftcurly)
- [LineLength](#linelength)
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
- [NoWhitespaceAfter](#nowhitespaceafter)
- [NoWhitespaceBefore](#nowhitespacebefore)
- [PackageDeclaration](#packagedeclaration)
- [PackageName](#packagename)
- [ParameterName](#parametername)
- [RedundantImport](#redundantimport)
- [RightCurly](#rightcurly)
- [SimplifyBooleanExpression](#simplifybooleanexpression)
- [StaticVariableName](#staticvariablename)
- [StringLiteralEquality](#stringliteralequality)
- [TodoComment](#todocomment)
- [TypeName](#typename)
- [UnusedImports](#unusedimports)
- [UpperEll](#upperell)
- [VisibilityModifier](#visibilitymodifier)
- [WhitespaceAfter](#whitespaceafter)
- [WhitespaceAround](#whitespacearound)
- [WriteTag](#writetag)

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

- https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Object.html#equals-java.lang.Object-
- https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Object.html#hashCode--


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

## FileTabCharacter

```xml
 <module name="FileTabCharacter"/>
```

Check for tab characters in the source code.

Do not use tab characters and use spaces (OK).
If there are tab characters, then it will be Not OK.

Please follow the rules to unify your coding style.

```java
    // Indent using tabs (Not OK)
	System.out.println("This line is indented with tabs");

    // Indent using spaces (OK)
    System.out.println("This line is indented with spaces");

```

The advantage of using spaces instead of tabs is that you do not need to explicitly set the tab width in the editor.


## GenericWhitespace

```xml
<module name="GenericWhitespace"/>
```

Check for whitespace around generic parentheses.

Please do not put a space after the generic parenthesis `<` and before `>` (OK).
If there is an unnecessary space, then it will be Not OK.

Please follow the rules to unify your coding style.

```java
        // An unnecessary space is added after and before the generic parentheses < and > (Not Ok).
        List< String > bad = new ArrayList<>();
        Map< String, Integer > badToo = new HashMap<>();

        // There are no unnecessary spaces after and before the generic parentheses < and > (OK).
        List<String> good = new ArrayList<>();
        Map<String, Integer> goodToo = new HashMap<>();
```


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

## IllegalType

```xml
<module name="IllegalType">
  <property name="severity" value="error"/>
  <property name="tokens" value="METHOD_DEF,PARAMETER_DEF,VARIABLE_DEF"/>
  <property name="illegalClassNames" value="java.util.Hashtable, java.util.HashSet, java.util.HashMap, java.util.ArrayList, java.util.LinkedList, java.util.LinkedHashMap, java.util.LinkedHashSet, java.util.TreeSet, java.util.TreeMap, java.util.Vector, java.util.IdentityHashMap, java.util.WeakHashMap, java.util.EnumMap, java.util.concurrent.ConcurrentHashMap, java.util.concurrent.CopyOnWriteArrayList, java.util.concurrent.CopyOnWriteArraySet, java.util.EnumSet, java.util.PriorityQueue, java.util.concurrent.ConcurrentLinkedQueue, java.util.concurrent.LinkedBlockingQueue, java.util.concurrent.ArrayBlockingQueue, java.util.concurrent.PriorityBlockingQueue, java.util.concurrent.DelayQueue, java.util.concurrent.SynchronousQueue"/>
</module>
```

Check that the specified type has not been used.

Do not use the classes listed below as variable types, return types, or parameter types.

- `java.util.Hashtable`
- `java.util.HashSet`
- `java.util.HashMap`
- `java.util.ArrayList`
- `java.util.LinkedList`
- `java.util.LinkedHashMap`
- `java.util.LinkedHashSet`
- `java.util.TreeSet`
- `java.util.TreeMap`
- `java.util.Vector`
- `java.util.IdentityHashMap`
- `java.util.WeakHashMap`
- `java.util.EnumMap`
- `java.util.concurrent.ConcurrentHashMap`
- `java.util.concurrent.CopyOnWriteArrayList`
- `java.util.concurrent.CopyOnWriteArraySet`
- `java.util.EnumSet`
- `java.util.PriorityQueue`
- `java.util.concurrent.ConcurrentLinkedQueue`
- `java.util.concurrent.LinkedBlockingQueue`
- `java.util.concurrent.ArrayBlockingQueue`
- `java.util.concurrent.PriorityBlockingQueue`
- `java.util.concurrent.DelayQueue`
- `java.util.concurrent.SynchronousQueue`

If these are used as variable types, return value types, and parameter types, the result will be Not OK.
Use the interfaces of these classes instead (OK).

All of the types listed here are specific classes.
This rule is applied for designing around interfaces rather than specific classes.


## Indentation

```xml
<module name="Indentation">
  <property name="caseIndent" value="0"/>
</module>
```

Check that the indentation matches the rule.

Write the indentation so that it matches the rules (OK).
If there is an indent that does not match the rule, then it will be Not OK.

Please follow the rules to unify your coding style.

```java
    /**
     * Not OK example of indentation.
     *
     * @param number Number
     * @return Numeric value after conversion
     * @throws IllegalArgumentException if argument is 0
     */
    public int invalidExample(int number)
    throws IllegalArgumentException {   // Not OK because the indent of throws does not match the rule

    int ret;    // Not OK because indent of statement in method does not match the rule

        switch (number) {
            // Below, the indent of switch and case does not match the rule, so Not OK
            case 0:
                throw new IllegalArgumentException("argument 'number' must not be zero.");

            default:
                ret = number + 1;
                break;
        }
        return ret;
    }

    /**
     * OK example of indentation.
     *
     * @param number Number
     * @return Numeric value after conversion
     * @throws IllegalArgumentException if argument is 0
     */
    public int validExample(int number)
            throws IllegalArgumentException {   // the indent of throws matches the rule OK

        int ret;    // OK because indent of statement in method matches the rule

        switch (number) {
        // Below, the indent of switch and case matches the rule, so Not OK
        case 0:
            throw new IllegalArgumentException("argument 'number' must not be zero.");

        default:
            ret = number + 1;
            break;
        }
        return ret;
    }
```

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

## JavadocMethod

```xml
    <module name="JavadocMethod">
      <property name="severity" value="error"/>
      <property name="allowUndeclaredRTE" value="true"/>
    </module>
```

Check Javadoc comments of the method.

Make sure to include Javadoc comments in the method (OK).
If there is no Javadoc comment, it will be Not OK.

Javadoc comments are important information for code readers.

In the above setting, you can write the unchecked exception `@throws` tag even if there is no `throws` declaration in the method (it will be Okay).



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
<module name="LocalVariableName"/>
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
## LeftCurly

```xml
    <module name="LeftCurly"/>
```


Check the placement of curly brace (`{`) at the beginning of the code block.

Be sure to put the curly brace (`{`) at the end of the line (OK).
If a curly brace is not placed at the end of the line, then it will be Not OK.

Please follow the rules to unify your coding style.


```java
        boolean condition = true;
        if (condition)  // Brace is not put at the end of the line (Not OK).
        {

        } else
        {               // Brace is not put at the end of the line (Not OK).

        }

        try
        {

        } catch (IllegalArgumentException e)
        {               // Brace is not put at the end of the line (Not OK).

        } finally
        {               // Brace is not put at the end of the line (Not OK).

        }
```

## LineLength

```xml
<module name="LineLength">
  <property name="ignorePattern" value="^import"/>
  <property name="max" value="150"/>
  <property name="tabWidth" value="4"/>
</module>
```

Check the number of characters in one line.

Limit the number of characters per line to 150 (OK).
If one line exceeds 150 characters, it will be Not OK.

Except for the line where the `import` declaration is written.


Too many characters per line reduce code readability.

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

## NoWhitespaceAfter


```xml
    <module name="NoWhitespaceAfter">
      <property name="severity" value="info"/>
      <property name="tokens" value="BNOT,DEC,INC,LNOT"/>
    </module>
```

Check that there is no space after a particular token (such as `~` `!` `++` (prefix)).

Do not put spaces after these tokens (OK).
If there is a space, then it will be Not OK.

Please follow the rules to unify your coding style.

## NoWhitespaceBefore

```xml
    <module name="NoWhitespaceBefore">
      <property name="severity" value="info"/>
    </module>
```

Check that there is no space before a particular token (`,` `;`, etc.).

Do not put spaces after these tokens (OK).
If there is a space, then it will be Not OK.

Please follow the rules to unify your coding style.


```java
        // There is a space before the semicolon that is not required (Not OK).
        int i = 0 ;

        // There is a space before the comma that is not required (Not OK).
        List<String> list = Arrays.asList("foo", "bar", "buz");
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

## RedundantImport

```xml
<module name="RedundantImport"/>
```

Check for redundant `import`.

Delete the redundant `import` statement (OK).
If there are two or more overlapping `import` or `import` of a class in the `java.lang` package, and then `import` of a class in the same package, it will be Not OK.

Redundant descriptions reduce the readability of the code.

## RightCurly


```xml
    <module name="RightCurly"/>
```


Check the placement of curly braces (`}`) at the end of the `if-else` and `try-catch-finally` code blocks.

Place the curly braces (`}`) at the end of the code block on the same line as the next statement (OK).
If there is a curly brace placed on a separate line from the next statement, then the result is Not OK.

Please follow the rules to unify your coding style.


```java

        boolean condition = true;
        if (condition) {

        }   // There is an unnecessary line break between } and else (Not OK).
        else {

        }

        try {

        }   // There is an unnecessary line break between } and catch (Not OK).
        catch (IllegalArgumentException e) {

        }   // There is an unnecessary line break between } and finally (Not OK).
        finally {

        }
```


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
<module name="TypeName">
  <property name="severity" value="error"/>
  <property name="tokens" value="CLASS_DEF"/>
</module>
<module name="TypeName">
  <property name="severity" value="error"/>
  <property name="tokens" value="INTERFACE_DEF"/>
</module>
```

Check the name of the class interface.

Ensure that the name of class/interface satisfies the following rule (OK):

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

## WhitespaceAfter

```xml
<module name="WhitespaceAfter">
  <property name="severity" value="info"/>
</module>
```

Check for white spaces after certain symbols or keywords.

Ensure that the following rules are satisfied (OK):

- If there is a description enumerated with `,`, such as calling a method having multiple arguments, there must be a space after `,`
- When writing an expression separated by `;` like a legacy `for` statement, there must be a space after `;`
- A cast expression has a type that is enclosed in parentheses, but there must be a space after the closing parenthesis
- There must be a space after the next keyword
  - `if`
  - `else`
  - `while`
  - `do`
  - `for`

If these conditions are not met, then it will be Not OK.

Please follow the rules to unify your coding style.

## WhitespaceAround

```xml
<module name="WhitespaceAround">
  <property name="severity" value="info"/>
  <property name="tokens" value="ASSIGN,BAND,BAND_ASSIGN,BOR,BOR_ASSIGN,BSR_ASSIGN,BXOR,BXOR_ASSIGN,COLON,DIV,DIV_ASSIGN,EQUAL,GE,GT,LAND,LE,LITERAL_DO,LITERAL_ELSE,LITERAL_FOR,LITERAL_IF,LITERAL_WHILE,LOR,LT,MINUS,MINUS_ASSIGN,MOD,MOD_ASSIGN,NOT_EQUAL,PLUS,PLUS_ASSIGN,QUESTION,SL,SL_ASSIGN,SR,SR_ASSIGN,STAR,STAR_ASSIGN"/>
</module>
```

Check for whitespace before and after the operator.

Place a space before and after operators such as `+` and `*`, and assignment operators such as `=` `+=` `*=` (OK).
If this condition is not met, it will be Not OK.

Please follow the rules to unify your coding style.

## WriteTag

```xml
    <module name="WriteTag">
      <property name="tag" value="@author"/>
      <property name="tagFormat" value="\S"/>
      <property name="tagSeverity" value="ignore"/>
    </module>
```

Check that the `@author` tag exists in the Javadoc comment for the type (class, interface, enum and annotation).

Different tags can be supported by copying each `WriteTag` element and rewriting the `tag` property.
Set as needed in the project.

```java
/**
 * There is an author tag and the value is set (OK).
 * 
 * @author example
 */
public class WriteTagExample {
}

/**
 * There is no author tag (Not OK).
 * 
 */
interface Ng1WriteTagExample {
}

/**
 * There is an author tag but no value (Not OK).
 * 
 * @author
 */
interface Ng2WriteTagExample {
}
```
