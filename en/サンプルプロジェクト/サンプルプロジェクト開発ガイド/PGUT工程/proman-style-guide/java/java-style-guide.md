# Java Coding Conventions

<!-- START doctoc -->

- [1.Introduction](#no1)
  - [1.1.Prerequisites](#no1-1)
  - [1.2.Indicators](#no1-2)
  - [1.3.Java version](#no1-3)
  - [1.4.Notation rules](#no1-4)
- [2.Package structure](#no2)
  - [2.1.Structure the package according to the stereotype in the system design](#no2-1)
- [3.Naming](#no3)
  - [3.1.Class names are nouns and name according to stereotype in the system design depending on the requirement](#no3-1)
  - [3.2.Name the method names starting with a verb](#no3-2)
  - [3.3.Name the variables with nouns](#no3-3)
  - [3.4.Name using the glossary of the data model](#no3-4)
- [4.Comments](#no4)
  - [4.1.Write Javadoc comments of Class](#no4-1)
  - [4.2.Write Javadoc comments of Field](#no4-2)
  - [4.3.Write Javadoc comments of Method](#no4-3)
  - [4.4.Provide row comments as required to help understand the code](#no4-4)
- [5.Important notes](#no5)
  - [5.1.Do not increment/decrement in the middle of a calculation formula](#no5-1)
  - [5.2.Do not use fields as temporary variables](#no5-2)
  - [5.3.Do not define fields in the subclass with the same name as that of the super class](#no5-3)
  - [5.4.Do not return null if the return value is a collection or an array](#no5-4)
  - [5.5.Do not call the self-instance method within the constructor](#no5-5)
  - [5.6.Do not call the static method by using the variable that stored the instance as a receiver](#no5-6)
  - [5.7.Do not refer to static variable that stores the instance as a receiver](#no5-7)
  - [5.8.Exception class must not be created at the discretion of the application programmer](#no5-8)
  - [5.9.Do not generate and throw instance of java.lang.Exception class](#no5-9)
  - [5.10.Do not use try-catch statement for conditional branching](#no5-10)
  - [5.11.Make sure that confidential information is not logged or serialized](#no5-11)
  - [5.12.Do not use legacy API in Java standard library](#no5-12)
  - [5.13.Do not use reflection directly](#no5-13)
  - [5.14.Do not leave the class in an inconsistent state](#no5-14)
  - [5.15.Application programmers should not create threads and perform asynchronous operation at their discretion](#no5-15)
  - [5.16.Do not create constants with same name and value](#no5-16)
- [6.Important notes](#no6)
  - [6.1.Do not implement processes that cannot be understood from the name in the method](#no6-1)
  - [6.2.Do not add multiple elements in a single method](#no6-2)
  - [6.3.Declare method arguments, return values and field types published outside the class in the interface and not in the implementation class](#no6-3)
  - [6.4.Apply the overloading of method only for the omission of options](#no6-4)
  - [6.5.Do not retain unused code](#no6-5)
  - [6.6.Do not create exceptionally large classes](#no6-6)
  - [6.7.Do not create exceptionally large methods](#no6-7)
  - [6.8.Do not create too many inner classes, static nested classes, or anonymous classes](#no6-8)
  - [6.9.Do not use casts as far as possible](#no6-9)
  - [6.10.Be careful of unboxing when calculating variables and primitive values of wrapper class](#no6-10)
  - [6.11.Use BigDecimal when calculation errors are not permitted](#no6-11)
  - [6.12.Perform sorting and aggregation in SQL and not in Java](#no6-12)
  - [6.13.Do not access the database as much as possible in loop processing](#no6-13)
  - [6.14.Check the external input values using common part](#no6-14)
  - [6.15.Use common parts class for file I/O](#no6-15)
  - [6.16.Use try-with-resources syntax when resources are required to be closed](#no6-16)
  - [6.17.Code the exceptions processing consistently according to system design of the project](#no6-17)
  - [6.18.Loop nesting should be up to double if possible](#no6-18)
- [7.Recommendations](#no7)
  - [7.1.Provide appropriate access modifier in the class, method, etc](#no7-1)
  - [7.2.As a general rule, make instance variable private](#no7-2)
  - [7.3.Use local variables by narrowing down the scope as far as possible](#no7-3)
  - [7.4.Avoid re-assignment as far as possible and use final](#no7-4)
  - [7.5.Do not change the argument state as far as possible](#no7-5)
  - [7.6.For using null as return value, consider the use of optional](#no7-6)
  - [7.7.Bind the appropriate type when using the class that captures type parameters such as collection](#no7-7)
  - [7.8.Consider writing the collection operations concisely using the Stream API](#no7-8)
  - [7.9.For process involving check exceptions throw, implement extended for syntax and not Stream API](#no7-9)
  - [7.10.Avoid using legacy for statement and consider using Stream API or extended for statement](#no7-10)
  - [7.11.Use clone method to copy the whole array](#no7-11)
  - [7.12.Use the toArray method when converting collection to array](#no7-12)
  - [7.13.Use Arrays.asList or List.of when converting an array to collection](#no7-13)
  - [7.14.Add @Override to methods when overriding method and implementing abstract method](#no7-14)
- [8.Available API](#no8)
  - [8.1.Implement using the available standard API](#no8-1)
- [9.Nablarch library](#no9)
  - [9.1.Implement by using the available Nablarch API](#no9-1)

<!-- END doctoc -->

## <a name="no1">1.Introduction</a>

These conventions explain rules that should be followed by application programmers and guidelines for writing better code in projects that develop applications using Java.

Though these conventions include some items that are based on [Nablarch Application Framework](https://nablarch.github.io/docs/LATEST/doc/index.html), most of the items can be used generically without being limited to a specific framework.

### <a name="no1-1">1.1.Prerequisites</a>

For the code covered under these conventions, it is assumed that the following three actions have been implemented:

- Formatted using code formatter [Java code formatter](./code-formatter.md)）
- Violations of the conventions that can be detected mechanically by Checkstyle are resolved [Checkstyle Guide](./staticanalysis/checkstyle/README.md)）
- The code that is evidently problematic and that is likely to cause problems later is eliminated using SpotBugs（[SpotBugs Guide](./staticanalysis/spotbugs/README.md)）

Anything that can be handled mechanically is implemented in advance, and these conventions serve as a guide for writing better code, or as code review guidelines.

SonarQube is another well-known static analysis tool, however, as compared to SonarQube which is installed on a server, Checkstyle and SpotBugs are easy to introduce, where checking is made simpler just by running Maven; hence these conventions assume that Checkstyle and SpotBugs are used.

Static analysis may be performed by means other than Checkstyle and SpotBugs depending on the project.
If required, the preconditions of these conventions can be read by replacing Checkstyle and Spotbug with SonarQube or any other static tool.

### <a name="no1-2">1.2.Indicators</a>

The following three were considered as guidelines when writing these conventions:

- Compile errors rather than runtime errors
- Reducing modifiable states
- Localization of places where states are modified

Compile errors rather than runtime errors: Bugs that can be detected as errors during compile can be fixed faster than bugs that cannot be detected without running the application.
Without relying on runtime errors, programmers can have peace of mind when developing code in which bugs can be detected as compile errors as much as possible.

Reduce modifiable states: Modifiable state indicates a field, which can be reassigned by a method such as `setter`, or which is declared in a class whose value can be changed, such as `java.lang.StringBuilder`.

```java
//Fields that can be modified by setter (modifiable state)
private String mutableState;

public void setMutableState(final String mutableState) {
    this.mutableState = mutableState;
}
```

```java
//Fields cannot be reassigned but the value can be modified (modifiable state)
private final StringBuilder mutableState = new StringBuilder();

public void appendHello(final String yourName) {
    //Value is modified
    mutableState.append("Hello ").append(yourName);
}
```

If there are modifiable states, programmers must read through the code while making mental note of the current state.
Though it differs from person to person, the amount of information that one can temporarily retain in memory is not much.
By reducing modifiable states as much as possible, the code becomes easy to maintain and it is not a burden to the reader.

Localization of places where state is modified: State may be modified by `setter`, etc., however, before and after the change in state, one must also make a mental note of the change.
By localizing the code that changes state as much as possible, the code becomes easily maintainable without causing burden to the reader.

These conventions are created by being aware of the points that programmers should pay attention to while writing code that conforms to these guidelines.
While reading through these conventions, and also while writing the code, it is more effective if one is aware of these guidelines.

### <a name="no1-3">1.3.Java version</a>

These conventions are created based on Java 8, and in some items, new functions that can be used in Java 9 and Java 10 have also been cited.

These conventions can be used for projects that use Java 8 and above.

### <a name="no1-4">1.4.Notation rules</a>

The Java version is written as Java 5 and Java 8 instead of J2SE 5 and Java SE 8 in these conventions.

At some places, code examples have been inserted between explanations of the conventions.
Examples of "prohibited code" that programmers should refrain from writing are stated in the form of single line comments starting with `//Not Okay`.
In contrast to prohibited code, there is "recommended code" that can be or should be written, the examples of which are the ones starting with `// OK`.
The code examples with neither `//Not Okay` nor `//OK` are either "recommended code" or are simply given as supplementary explanation.

In code examples, the parts except those that are required to be highlighted to programmers, have been omitted.
The omission is indicated by writing `...`.

```java
//Example of omission
public void someMethod(...) { //Description of arguments are omitted
    ... //Method body is omitted
}
```

Also, sometimes three `.` are written vertically to show that the middle part of code that extends vertically has been omitted.

```java
//Example of omission of the middle
final String text = message.getText();
.
.
.
return text;
```

In these conventions, the traditional `for` statement that is used with loop index is expressed as "legacy `for` statement".

```java
//Example of legacy for statement
for (int i = 0; i < length; i++) {
    ...
}
```

Since the code examples are in accordance with these conventions, especially the conventions given under "7. Recommendations", they have the following characteristics:

- `private` is added for a field
- `final` is added for a variable as far as possible

Refer to "7. Recommendations" for details of other conventions that have been followed.
Some code examples showing "prohibited code" do not conform to the conventions.

---

## <a name="no2">2.Package structure</a>

These are the conventions for application package structure.

### <a name="no2-1">2.1. Structure the package according to the stereotype in the system design</a>

In a business application, structure the package in accordance with the stereotypes for system design.

A simple example of package name is shown.

|Role|Naming example|
|---|---|
|Action|`com.example.action`|
|Entity|`com.example.entity`|
|Business Form|`com.example.form`|
|Validator|`com.example.validation`|
|DTO|`com.example.dto`|

---

## <a name="no3">3.Naming</a>

These are the conventions for naming classes, methods and variables.

### <a name="no3-1">3.1.Class names are nouns and name according to stereotype in the system design depending on the requirement</a>

Basically, a noun is used for Class name.

Customarily, for an "Interface that indicates giving a specific ability", `able` can be added at the end.
Even in Java standard API, many interfaces with such names are included.

- `java.lang.Appendable`
- `java.lang.AutoCloseable`
- `java.lang.Comparable`
- `java.lang.Iterable`
- `java.lang.Runnable`

Classes that are created in a business application often have roles in accordance with the stereotypes for system design.
Hence, when naming the classes, add suffixes etc. making it evident that the classes correspond to the stereotypes.

|Role|Naming rule|
|---|---|
|Action|Name of the function + `Action`|
|Entity|Physical name of the table converted to upper camel case|
|Operation base form|Name of the function + `FormBase`|
|Operation form|Name of the function + `Form`|
|Validator|Name of the function + `Validator`|
|DTO|Name of the datatype + `Dto`|
|Control class for Exclusive control|Physical name of the table converted to upper camel case + `ExclusiveControl`|
|Test|Test class name + `Test`|
|Request unit test|`Action` Class name + `RequestTest`|

### <a name="no3-2">3.2.Name the method names starting with a verb</a>

Though the method name should start with a verb as a basic rule, "Recommended Naming Rules By Function" given below also include names that do not start with a verb.

|Function|Naming rule|Example|
|---|---|---|
|Create instance|`create` + Target|`createItemCode`|
|Convert to a different type|`to` + Type to be converted to|`toString` `toItemCode`|
|Handle as a different type|`as` + Type to be handled as|`asReadLock` `asList`|
|Return if contained|`contains` + target|`containsKey`|
|Return if possible|`can` + operation|`canRead` `canEncode`|
|Return if in that state|`is` + state|`isClosed` `isEmpty`|

Though "Convert to a different type" and "Handle as a different type" appear similar, they are a little different.
As opposed to the former, in which "the type after conversion is not expected to have the properties of the base type", in the latter, "the type after conversion has a relation with the properties of the base type".
`Integer.toString` in the Java standard API falls under the former. An integer is converted to a string which is not expected to have the properties of an integer.
`Arrays.asList` falls under the latter. An array is converted to a list; however, properties of array and list are similar and when the base array is changed, the list also changes.

Even in Java standard API, many methods exist with names that do not start with verbs, such as `String.length` and '`Optional.of`.
Programmers should also refer to the Java standard API for appropriate naming conventions.

### <a name="no3-3">3.3.Name the variables with nouns</a>

Basically, for field names and local variable names, nouns are used.

In Java naming conventions, fixed names may be used customarily.

It is alright to name `java.io.InputStream` and `java.io.OutputStream` as `in` `is` and `out` `os`, respectively.

```java
try (final InputStream in = openInputStream(file)) {
    ...
}
```

It is common to name the counter used in legacy `for` statement as `i`.

```java
for (int i = 0; i < length; i++) {
    ...
}
```

It is common to name the variable that catches an exception in the `catch` block as `e`.

Variables with limited scope that are used temporarily to construct a value can have names that do not have any meaning.

```java
//Temporary variable to construct a message
//Here, the variable is named as buf that has no meaning
//Names such as sb or temp can also be used
final StringBuilder buf = new StringBuilder();
buf.append("Hello, ");
buf.append(yourName);
buf.append("!");
final String message = buf.toString();
```

### <a name="no3-4">3.4.Name using the glossary of the data model</a>

Consistent naming is possible by referring to an organized glossary.

Programmers should definitely make use of a glossary.
Prepare a glossary if an organized glossary is not available.

---

## <a name="no4">4.Comments</a>

These are the conventions for Javadoc comments and comments for explaining the processes.

### <a name="no4-1">4.1.Write Javadoc comments of Class</a>

In Javadoc comments for a class, describe the class role.

Include `@author` and `@since`, etc., depending on the project rules.

```java
/**
 * This is a class that displays the product information.
 * 
 * @author TIS
 */
public class Item {
```

### <a name="no4-2">4.2.Write Javadoc comments of Field</a>

In Javadoc comments for fields, describe the kind of attributes represented by those fields.

```java
/**
 * Product code
 */
private String code;

/**
 * Product name
 */
private String name;

/**
 * Update date and time
 */
private LocalDateTime updatedAt;
```

### <a name="no4-3">4.3.Write Javadoc comments of Method</a>

In Javadoc comments for a method, describe the outline of the process performed by that method.
Also, describe any preconditions for calling that method.

Use `@param` for description of arguments, `@return` for description of return values, and `@throws` for description of exceptions that are likely to be thrown.

In `@throws`, writing only the Japanese name of the exception class is not sufficient.
Describe the conditions under which the concerned exception is thrown.

```java
//Not Okay  This is Not Okay since only the name of the exception class is mentioned.
 ...
 * @throws ItemNotFoundException ProductNot Foundexception
 */
```

```java
//OK The conditions under which the exception is thrown are also described.
 ...
 * @throws ItemNotFoundException When the product to be updated cannot be found in the database
 */
```

For handling of `null` in arguments and return values shown below, decide which of the two is the default for the entire application in the project.

- `null` is allowed, or not allowed in a argument
- As a return value, `null` may be returned, or always a `non-null` value is returned

Also, only when there is a deviation from the default, describe to that effect in Javadoc.

For example, if it is decided as a rule that "as default, `null` will not be allowed in a argument", describe the behavior when a argument that can receive a `null` value receives `null`.

```java
/**
 * This method updates the product information.
 *
 * <p>
 * By this method, products already registered are updated.
 * If a product is not yet registered, complete the registration process first.
 * </p>
 *
 * @param code Product code
 * @param name Product name
 * @param updatedAt Updated date and time. In the case of null, system date and time is used as a default value
 * @throws ItemNotFoundException When the product to be updated cannot be found in the database
 */
public void updateItem(final String code, final String name, final LocalDateTime updatedAt)
        throws ItemNotFoundException {
    ...
}
```

### <a name="no4-4">4.4.Provide row comments as required to help understand the code</a>

Ideally it should be possible to understand the contents of a process only by reading the code, however, if any special implementation is intentionally performed due to complex logic or performance, write comments for explanation.
Write comments as single line comments starting with `//`.

---

## <a name="no5">5.Important notes</a>

Important notes are provided to reduce risky code that may cause bugs.

### <a name="no5-1">5.1.Do not increment/decrement in the middle of a calculation formula</a>

If increment/decrement is performed in the middle of a calculation formula, it becomes difficult to grasp the value of the corresponding variable.

Execute the formula first, and then perform the increment/decrement.

```java
//Not Okay
int x = ...
int y = (x++ * height) / width;
```

```java
//OK
int x = ...
int y = (x * height) / width;
x++;
```

### <a name="no5-2">5.2.Do not use fields as temporary variables</a>

In some cases, a temporary variable is introduced to hold the intermediate state in a method that sequentially processes the elements of a collection to build the required value, however, in such cases, do not use fields.

When fields are used, the number of states in a class increases.
To minimize the modifiable states in a class as much as possible, do not use fields as temporary variables.

For temporary variables, use local variables instead of fields.

```java
//Not Okay
//Fields are used as temporary variables
private List<String> itemNames;

public String collectNames(final List<Item> items) {
    this.itemNames = new ArrayList<>();
    for (Item item : items) {
        collectName(item);
    }
    return this.itemNames.stream().collect(Collectors.joining(", "));
}

private void collectName(final Item item) {
    this.itemNames.add(item.getName());
}
```

```java
//OK
public String collectNames(final List<Item> items) {
    //Local variables are used as temporary variables, and routing is performed by passing the variables to a method
    final List<String> itemNames = new ArrayList<>();
    for (Item item : items) {
        collectName(itemNames, item);
    }
    return itemNames.stream().collect(Collectors.joining(", "));
}

private void collectName(final List<String> itemNames, final Item item) {
    itemNames.add(item.getName());
}
```


### <a name="no5-3">5.3. Do not define fields in the subclass with the same name as that of the super class</a>

If fields are defined with the same names in the superclass and the subclass, then fields defined in the superclass cannot be accessed only with their names.

Fields are different from methods and cannot be overridden.

Do not define fields in the subclass with the same names used in the superclass, as this causes confusion.

```java
//Not Okay
public class SuperClass {

    //Fields that are assumed to be used in the subclass
    protected final String var;

    ...
}

public class SubClass extends SuperClass {

    private final String var;

    public String getVar() {
        return var; //SubClass.var is returned
    }

    ...
}
```

### <a name="no5-4">5.4.Do not return null if the return value is a collection or an array</a>

To indicate a state of no value, `null` may be used, however, if the return value is a collection or an array, as shown below, do not return `null` even to indicate a state of no value.

- `java.util.Collection`
- `java.util.Set`
- `java.util.List`
- `java.util.Map`

The state of no value for a collection often indicates that the collection is empty.
コSince there is a method called `isEmpty` to show that a collection is empty, there is no need to return `null`.
Also, in the case of an array, if the `length` field is `0`, then the array can be judged as empty.

If `null` is likely to be returned, `null` check must be performed by the caller and the code becomes complex.

```java
//Not Okay
public List<Item> findItems(final ItemCategory category) {
    List<Item> items = dao.findItems(category);
    if (items.isEmpty()) {
        return null;
    }
    return items;
}
```

```java
//OK
public List<Item> findItems(final ItemCategory category) {
    return dao.findItems(category);
}
```

To explicitly create and return an empty collection, use `empty<collection>` method in the `java.util.Collections` class.

For example, to return an empty list depending on the condition, instead of instantiating `java.util.ArrayList`, use `emptyList` method in the `java.util.Collections` class.

```java
//Not Okay
if (empty) {
    return new ArrayList<>();
}
```

```java
//OK
if (empty) {
    return Collections.emptyList();
}
```

In `java.util.Collections`, other than `emptyList` , depending on the type of collection, `emptyMap` and 'emptyMap' are provided.

### <a name="no5-5">5.5.Do not call the self-instance method within the constructor</a>

In a constructor, even if there is a field declared with `final`, sometimes `null` value before initialization is referenced.

Hence, when calling an instance method to reference a field from a constructor, it is necessary to pay attention to the order of initializing the field and calling the method.

```java
//Not Okay
public class Foo {

    private final String text;
    private final int length;

    public Foo(final String text) {

        //Since text is not initialized, if calculateLength is called at this stage,
        //NullPointerException is thrown.
        this.length = calculateLength();

        this.text = text;

        //calculateLength should be called at this stage after initialization of text.
        //this.length = calculateLength();
    }

    protected int calculateLength() {
        return text.length();
    }
}
```

This becomes even more complicated with when inheritance.

Look at the following Not Okay example.
Two classes are defined, namely `Foo` and `Bar`.
`Bar` inherits `Foo`.

When `Bar` is instantiated, ahead of the `Bar` constructor, the `Foo` constructor is called.
While the `Foo` constructor seemingly calls `calculateLength`, since this method is overridden in `Bar`, in reality, the `calculateLength` of 'Bar' is called.
The `calculateLength` method of `Bar` references `text`, however, since `text` is not initialized at this point, `NullPointerException` is thrown.

```java
//Not Okay
public class Foo {

    private final int length;

    public Foo() {
        //In the constructor, its own method is being called
        this.length = calculateLength();
    }

    protected int calculateLength() {
        return 0;
    }
}

class Bar extends Foo {

    protected final String text;

    public Bar(final String text) {
        this.text = text;
    }

    @Override
    protected int calculateLength() {
        //Here, NullPointerException is thrown
        return text.length();
    }
}
```

Due to the complexity, do not call own instance method from inside the constructor.

Basically, inside the constructor, only the arguments for fields should be set.
To implement a process, make sure that the process is completed inside the constructor.

```java
//OK
public class Foo {

    private final String text;
    private final int length;

    public Foo(final String text) {
        this.text = text;
        // The process is completed inside the constructor
        this.length = text.length();
    }
}
```

However, if it is required to write a very long process inside the constructor, consider methods such as implementing the process in a separate class, as shown below.

- Perform the process prior to calling the constructor and pass its result in the argument of the constructor.
- Inside the constructor, delegate the process to a separate class and use its result.

### <a name="no5-6">5.6.Do not call the static method by using the variable that stored the instance as a receiver</a>

Usually, `static` methods are called by writing code with class name as a receiver.

Though calling a `static` method by writing code with a variable to store an instance defined as a receiver is syntactically allowed, do not use such code, since this is not a common practice.

```java
//Not Okay
final String text = ...
final int value = ...
return text.valueOf(value);
```

```java
//OK
final int value = ...
return String.valueOf(value);
```

### <a name="no5-7">5.7.Do not refer to static variable that stores the instance as a receiver</a>

Usually, for referencing a `static` variable, code is written with class name as a receiver.

Though referencing a `static` variable by writing code with a variable to store an instance defined as a receiver is syntactically allowed, do not use such code, since this is not a common practice.

```java
//Not Okay
final Integer length = ...
return length.MAX_VALUE;
```

```java
//OK
return Integer.MAX_VALUE;
```

### <a name="no5-8">5.8.Exception class must not be created at the discretion of the application programmer</a>

Exception is a mechanism for interrupting the process implemented in a method and enabling non-local exit.
Exceptions should be handled in an integrated manner with proper management.

The required exception classes shall be provided by the framework and the application programmers should use the provided classes.

If it is required to create an exception class, consult the architect.

### <a name="no5-9">5.9.Do not generate and throw instance of java.lang.Exception class</a>

To explicitly throw an exception, do not just create an instance of the `java.lang.Exception` class.
Create an instance of an exception class that is in line with the business application system design and then throw the exception.

Since `java.lang.Exception` is the base class for all exceptions, the `catch` block that catches an exception cannot determine whether it is a business application exception or network exception.

Also, to throw `java.lang.Exception`, one must add `throws Exception` to the method, however, this necessitates adding `throws Exception` to the caller method as well, making it difficult to handle.

```java
//Not Okay
if (items.isEmpty()) {
    throw new Exception("Items searched by " + code + " are not found.");
}
```

```java
//OK
if (items.isEmpty()) {
    throw new ItemsNotFoundException(code);
}
```

### <a name="no5-10">5.10.Do not use try-catch statement for conditional branching</a>

`try-catch` statement is used for handling exceptions.
Use `if` statement to perform conditional branching.

When an exception is thrown, check whether it is possible to `catch` the exception, however, this check is expensive.
Particularly, if `try-catch` is used for conditional branching within a loop, there may be a notable performance degradation.

```java
//Not Okay
try {
    //API that throws an exception if there is no item for code
    service.findItem(code);
    return "Items exist";
} catch (ItemsNotFoundException e) {
    return "No items";
}
```

```java
//OK
//API that checks if item for code exists
if (service.exists(code)) {
    return "Items exist";
} else {
    return "No items";
}
```

### <a name="no5-11">5.11.Make sure that confidential information is not logged or serialized</a>

Be careful to mask confidential information such as password, so that the information is not included in the log.

Ensure not to include confidential information when serializing instances.
Here, the term "serialize" does not only imply serialization in Java, but also includes conversion to formats such as JSON and XML.

For example, in serialization of Java, the keyword `transient` is added for fields that are excluded from serialization.

```java
public class LoginForm implements Serializable {

    private String username;
    private transient String password;

    ...
}
```

Since in serialization to JSON and XML, usually a method is provided that excludes libraries from serialization, this should be handled appropriately.

### <a name="no5-12">5.12.Do not use legacy API in Java standard library</a>

The Java Standard Library has APIs that were used in the past versions but are now legacy and should not be used.
Do not use the legacy APIs given below.

|Legacy API|API that can be used alternatively|
|---|---|
|`java.lang.StringBuffer`|`java.lang.StringBuilder`|
|`java.util.Dictionary`|`java.util.Map`|
|`java.util.Enumeration`|`java.util.Iterator`|
|`java.util.Hashtable`|`java.util.HashMap`|
|`java.util.Stack`|`java.util.Deque`|
|`java.util.StringTokenizer`|`split` method of `String`<br>or `java.util.regex` package API|
|`java.util.Vector`|`java.util.ArrayList`|

Though there is a check tool for unauthorized APIs that can detect whether these legacy APIs have been used, the explanation is included in these conventions for your knowledge.

### <a name="no5-13">5.13.Do not use reflection directly</a>

The operation that can be done using the classes in `java.lang.reflect` package is called reflection. Do not use reflection directly.
Though it is possible to manipulate objects dynamically using reflection, it causes a condition where "although compilation is successful, an error occurs during runtime".

Though shared components provided by the framework and architects may use reflection internally, direct use in applications is prohibited.

Though there is a check tool for unauthorized APIs that can detect whether reflection is used, the explanation is included in these conventions so that writing of code that can pose risks can be prevented.

### <a name="no5-14">5.14.Do not leave the class in an inconsistent state</a>

When multiple related fields are defined within a class, make sure to maintain the consistency in values between these fields.

```java
//Not Okay
public class ItemList {

    private final List<Item> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void add(final Item item) {
        //At this point, since the total value changes, the totalPrice must be updated
        items.add(item);
    }

    public void save(final ItemDao dao) {
        //The total value is calculated along with save
        totalPrice = BigDecimal.ZERO;
        for (final Item item : items) {
            dao.save(item);
            totalPrice = totalPrice.add(item.getPrice());
        }
    }

    public BigDecimal getTotalPrice() {
        //When this method is called instead of calling save after add, the total value before adding the Item is returned
        return totalPrice;
    }
}
```

In the above example, when `items` is changed, it is better to change the `totalPrice` should at the same time.

```
//OK
public class ItemList {

    private final List<Item> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void add(final Item item) {
        //Since total value is calculated immediately after adding the item, the consistency of the state is maintained.
        items.add(item);
        totalPrice = totalPrice.add(item.getPrice());
    }

    public void save(final ItemDao dao) {
        for (final Item item : items) {
            dao.save(item);
        }
    }

    public BigDecimal getTotalPrice() {
        //The correct value is returned no matter when the method is called.
        return totalPrice;
    }
}
```

Without holding the total value as a state, it is also a good idea to calculate it every time in `getTotalPrice`.

```
//OK
public class ItemList {

    private final List<Item> items = new ArrayList<>();

    public void add(final Item item) {
        items.add(item);
    }

    public void save(final ItemDao dao) {
        for (final Item item : items) {
            dao.save(item);
        }
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(item -> item.getPrice())
                .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2));
    }
}
```

### <a name="no5-15">5.15.Application programmers should not create threads and perform asynchronous operation at their discretion</a>

The framework manages database connections and transactions by linking to threads.
An application programmer is not expected to create threads and carry out asynchronous processing.

Asynchronous processing is difficult even if there is no framework limitation.
If asynchronous processing becomes necessary for performance reasons, consult the architect.

### <a name="no5-16">5.16.Do not create constants with same name and value</a>

If the name and value of the constant is the same, then in some cases, a more suitable name should be given, while in other cases, there is no need to define a constant in the first place.

In the example shown below, a more suitable name should be given to the constant.
The name of the constant is the value of the linefeed code.

```java
//Not Okay
//In this case, if the line feed code is changed to LF, then the name of the constant must also be changed
private static final String CRLF = "\r\n";
```

In such cases, it is a good idea to change the name of the constant.

```java
//OK
//In this case, even if the line feed code is changed to LF, there is no need to change the name of the constant
private static final String LINE_BREAK = "\r\n";
```

In the example shown below, a constant need not be defined.
The column name of a table is defined as a constant.

```java
//Not Okay
private static final String ITEM_CODE = "ITEM_CODE";
```

If the column name changes, then the name of the constant will also change accordingly and defining the column name as a constant will not have much significance.
If another name is to be given, then the only name that can be considered is `COLUMN_01`, which is meaningless.

In this case, it is good idea not to define it as a constant.

---

## <a name="no6">6.Important notes</a>

Although not prohibited, there are items provided that programmers should pay careful attention to for preventing bugs and a negative impact on maintainability.

### <a name="no6-1">6.1.Do not implement processes that cannot be understood from the name in the method</a>

When a process that cannot be understood from the method name is implemented, the user of that method will be confused.

```java
//Not Okay
//From the method name, though it can be understood that it is a process "to get unread notification",
//in reality, "update to read status" is also being implemented.
private List<Notification> findUnreadNotifications() {
    final List<Notification> notifications = dao.findUnreadNotifications();

    //The status is being updated from unread to read
    for (Notification notification : notifications) {
        notification.setStatus(Status.ALREADY_READ);
        dao.update(notification);
    }

    return notifications;
}
```

```java
//OK
//As can be understood from the method name, the process to 'get unread notification' is being implemented
private List<Notification> findUnreadNotifications() {
    return dao.findUnreadNotifications();
}

//"Update to read status" is another method
private void updateToAlreadyRead(List<Notification> notifications) {
    for (Notification notification : notifications) {
        notification.setStatus(Status.ALREADY_READ);
        dao.update(notification);
    }
}
```

Even if programmers think that this is alright as the method will be used only by them during implementation, in the post-implementation phases such as code review and maintenance, there is possibility of others who need to see it to understand the method.

A method that is implemented should have "a name indicative of the method body", and everyone should be able to use the method without effort.

### <a name="no6-2">6.2.Do not add multiple elements in a single method</a>

Do not embed multiple elements such as "Get", "Update", and "Check" in a single method.
The name of the method in which multiple elements are embedded, tends to be abstract such as `execute` or `update`.

The application code is divided into layers such as "Presentation", "Business logic", and "Data access".
Since the abstraction level differs for each layer, though it depends on the layer, an application programmer should strive not to embed multiple elements in a single method and give a specific name to the method when writing business logic.

### <a name="no6-3">6.3.Declare method arguments, return values and field types published outside the class in the interface and not in the implementation class</a>

Use interfaces instead of implementation classes for declaring method arguments, return values and fields published outside the class.

When arguments are declared in an implementation class, if the argument type is changed, then the code that calls the method must also be changed.

```java
//Not Okay
//Method arguments are declared in the implementation class ArrayList, instead of List
public void saveItems(final ArrayList<Item> items) {
    ...
}
```

```java
//Code that calls the saveItems method
//If the arguments of saveItems are changed from ArrayList to LinkedList,
//This code must also be changed (Not Okay)
final ArrayList<Item> items = ...
dao.saveItems(items);
```

If the arguments are declared in the interface, the problems mentioned above do not arise as the implementation class is prepared by the caller.

```java
//OK
//Parameters are declared in the List interface
public void saveItems(final List<Item> items) {
    ...
}
```

Even when a return value is declared in the implementation class, if the variable type that receives the return value in the caller code is the implementation class, the caller code must be also be changed if the return value type is changed.

```java
//Not Okay
//Method arguments are declared in the implementation class ArrayList, instead of List
public ArrayList<Item> findAllItems() {
    ...
}
```

```java
//Code that calls the findAllItems method
//If the return value of findAllItems is changed from ArrayList to LinkedList,
//This code must also be changed (Not Okay)
final ArrayList<Item> allitems = dao.findAllItems();
```

Unlike arguments, the variable type declared in the caller code type can be set to the interface.
However, to avoid the possibility of declaring the variable type in the implementation class, declare the return value type in the interface as well.

```java
//OK
//Parameters are declared in the List interface
public List<Item> findAllItems() {
    ...
}
```

Since the `private` method and `private` fields are closed in the class and their impact range is narrow, they can be declared in the implementation class, if there is no strong reason to do so, declare them in the interface.

Local variables are closed in the method, and they can be declared in the implementation class since their impact range is very narrow.
When a local variable is declared using `var` that has been introduced from Java 10, it is handled as a result type that has evaluated the expression on the right.
This is synonymous with declaring the local variable type in the implementation class.

```java
//OK
public void someMethod() {

    //Since it is a local variable, it is OK even if declared in the implementation class
    ArrayList<String> localVariable = new ArrayList<>();

    //It is OK even if the local variable is declared using var that has been introduced from Java 10
    //In this case, since ArrayList is the value on the right, the local variable is handled as declared in ArrayList
    //* Note that the type parameter on the right must be bound without using the diamond operator
    var useVarKeyword = new ArrayList<String>();

    ...
}
```

### <a name="no6-4">6.4.Apply the overloading of method only for the omission of options</a>

Overloading the methods where only the sequence of arguments is swapped, and overloading the methods that take arguments of completely different types will complicate the code.

Use method overloading only when defining methods that have omitted optional arguments.
Even in such a case, design the method so that the sequence of arguments is taken into account and subsequent arguments are omitted.

```java
//Not Okay
//Overload with only change in sequence of the arguments
public void updateItem(final ItemCode code, final String name, final int version) {
    ...
}

public void updateItem(final ItemCode code, final int version, final String name) {
    ...
}

public void updateItem(final String name, final int version, final ItemCode code) {
    ...
}
```

```java
//Not Okay
//Overload that takes arguments of completely different types
public void updateItem(final ItemCode code, final String name, final int version) {
    ...
}

public void updateItem(final ItemCode code, final LocalDateTime updatedAt) {
    ...
}
```

```java
//OK
//Overload that omits optional arguments
public void updateItem(final ItemCode code, final String name, final int version) {
    LocalDateTime defaultUpdatedAt = ...
    updateItem(code, name, defaultUpdatedAt);
}

public void updateItem(final ItemCode code, final String name, final int version, final LocalDateTime updatedAt) {
    ...
}
```

### <a name="no6-5">6.5.Do not retain unused code</a>

Delete the methods and variables that were used in the processes of trial and error, performance improvement and codes used for debugging, that are no longer used.

However, the following unused codes are handled as exceptions.
Do not delete them.

- Methods and variables that must be defined because of framework constraints
- Unused methods and variables that are included in auto generated code

### <a name="no6-6">6.6.Do not create exceptionally large classes</a>

If the class is exceptionally large, it is difficult to understand the contents and maintainability will be poor.
In such cases, consider splitting the class.

Fields indicate the state of the class, but if there are many modifiable fields among them, it will be difficult to understand the entire class.
Reduce modifiable fields as much as possible or split and move them to another class.

However, do not split the following classes that function as containers for data, since the classes are created in meaningful units though they tend to have many modifiable fields.

- Entity class that maps database tables
- Form class that maps HTML forms

### <a name="no6-7">6.7.Do not create exceptionally large methods</a>

When the method is exceptionally large, it is difficult to understand the processing, and maintainability will be poor.
In such cases, consider splitting the method.

The easiest way to split is to slice each cluster into a `private` method.

Do not create too many method arguments.

### <a name="no6-8">6.8.Do not create too many inner classes, static nested classes, or anonymous classes</a>

Although the use of inner classes and `static` nested classes or anonymous classes is not explicitly prohibited, creating too many of these classes will make the code less readable.

If a large number of inner classes and `static` nested classes, or anonymous classes are required, consider if another independent class can be sliced out.

### <a name="no6-9">6.9.Do not use casts as far as possible</a>

Casting is a mechanism in which the value handled as one type is forced to be treated as a different type. It causes situations such as "passes through compile but throws an error during execution".

With generics being introduced from Java 5, there should be no problems even without the use of casting.

### <a name="no6-10">6.10.Be careful of unboxing when calculating variables and primitive values of wrapper class</a>

Converting a wrapper class such as `java.lang.Integer` to a primitive value such as `int` is called unboxing.

When wrapper class variables perform calculations with primitive values, the compiler automatically inserts the unboxing process.

```java
final Integer a = ...
final int b = ...

//intValue method call for variable a is injected by the compiler
//That is, the process is final int c = a.intValue() + b; 
final int c = a + b;
```

Though it is convenient to insert unboxing automatically, if unboxing is performed when the wrapper class variable is `null`, `java.lang.NullPointerException` is thrown.

```java
final Integer a = null;
final int b = ...

//If the variable a is null, NullPointerException is thrown by the intValue method call injected by the compiler
final int c = a + b;
```

Hence, perform coding carefully when calculating wrapper class variables and primitive values, such as performing a `null` check.

```java
final Integer a = ...
final int b = ...

if (a != null) {
    final int c = a + b;
    .
    .
    .
}
```

### <a name="no6-11">6.11.Use BigDecimal when calculation errors are not permitted</a>

Though `float` and `double` are numeric types expressed as floating point numbers, floating point calculations can be inaccurate in some cases.

Floating point numbers are used when slight errors such as coordinate calculation of GUI can be ignored. However, use `java.math.BigDecimal` when errors are not allowed, such as interest rate and discount calculations.

```java
//Not Okay
final double discountRage = 0.07;
return 1 - discountRate; //0.9299999999999999
```

```java
//OK
final BigDecimal discountRate = new BigDecimal("0.07");
return BigDecimal.ONE.subtract(discountRate); //0.93
```

Although some `BigDecimal` constructors take a `double` value, do not use them because it may cause errors similar to those caused by floating point numbers.
Use the `valueOf` method to obtain a `BigDecimal` instance from a `double` value.

```java
//Not Okay
final BigDecimal discountRate = new BigDecimal(0.07); //Instantiation by transferring a double value
return BigDecimal.ONE.subtract(discountRate); //0.929999999999999993338661852249060757458209991455078125
```

```java
//OK
final BigDecimal discountRate = BigDecimal.valueOf(0.07); //Instantiation by transferring the double value to valueOf
return BigDecimal.ONE.subtract(discountRate); //0.93
```

Also, the `equals` method of `BigDecimal` considers the value and scale to be equal if they are the same.
Hence, use the `compareTo` method to compare 2 values of `BigDecimal`.

```java
//Not Okay
final BigDecimal value = new BigDecimal("10.0"); //scale is 1
if (value.equals(BigDecimal.TEN)) { //Since the scale of BigDecimal.TEN is 0, equals returns false
    ...
}
```

```java
//OK
final BigDecimal value = new BigDecimal("10.0"); //scale is 1
if (value.compareTo(BigDecimal.TEN) == 0) { //comareTo returns 0 when the values are equal, even if the scale is different
    ...
}
```

`BigDecimal` can calculate without errors and supports many rounding modes with `java.math.RoundingMode`.

### <a name="no6-12">6.12.Perform sorting and aggregation in SQL and not in Java</a>

Perform sorting and aggregation of the data within the database in SQL rather than the Java side.

Sorting can be performed in Java with a small amount of data, but if there is a large amount of data, the performance may deteriorate.
Sorting cannot be performed in Java if data has to be acquired in little chunks from the database for processing.

Since aggregation is usually performed for a large amount of data, it causes a deterioration in performance when performed in Java.
Unlike sorting, the data before aggregation is used temporarily and the data that is required is the final aggregate result.
For this reason, perform aggregation by SQL using aggregate functions such as `SUM` and `AVR`.

### <a name="no6-13">6.13.Do not access the database as much as possible in loop processing</a>

In a business application, data obtained from the database is often processed one by one in a loop.

At this time, do not access the database inside the loop to get additional information.
It will increase the frequency of database access and cause a deterioration in performance.

As far as possible, try to obtain the required information by accessing the database once using table combinations when first obtaining the data.

### <a name="no6-14">6.14.Check the external input values using common part</a>

There are many types of input values from external applications.
Three examples are listed below.

- Form value sent from the browser
- Contents of linked files from the external system
- Message linked by MQ

Check these values according to the input specifications.
For example, for a form value sent from the browser, perform validation using Bean Validation.
Just like other values, check the input values using the function provided by the framework.

"Input values from external applications" are likely to be data acquired from the database,and the database under control should already have the trusted value checked.
Therefore, data obtained from the database is not subject to input data check.

### <a name="no6-15">6.15.Use common parts class for file I/O</a>

If an application programmer performs file input and output freely, effort is required for consistent handling of character codes and new line codes.

Based on the file format specifications stipulated in the project, the architect should create shared components for file input and output.
The application programmer should use the shared components class when performing file input and output.

Request/consult with the architect to add shared components for functions with insufficient components.

By performing file input and output using shared components, character codes and new line codes can be handled consistently.
Also, directory traversal can be prevented by checking the requested file path in shared components.

### <a name="no6-16">6.16.Use try-with-resources syntax when resources are required to be closed</a>

For Objects that use external resources such as `java.io.InputStream` and `java.io.OutputStream`, use the `try-with-resources` syntax so that there are no omissions of close.

The `try-with-resources` syntax was introduced in Java 7.

```java
//Not Okay
//Legacy writing up to Java 6
final InputStream in = openStream();
try {

    final String content = readAsString(in);

} finally {
    in.close();
}
```

```java
//OK
//try-with-resources from Java 7 onwards
try (final InputStream in = openStream()) {

    final String content = readAsString(in);

}
```

### <a name="no6-17">6.17.Code the exceptions processing consistently according to system design of the project</a>

Having a consistent exception processing method in the project is important.
Perform coding consistently in accordance with the system design of the project.

### <a name="no6-18">6.18.Loop nesting should be up to double if possible</a>

When loop nesting increases, the code readability is reduced.
Although this index is not absolute as readability is subjective, loop nesting is prescribed up to double in these conventions.

---

## <a name="no7">7.Recommended items</a>

Some recommendations have been stipulated to write better code.

### <a name="no7-1">7.1. Provide appropriate access modifier in the class, method, etc.</a>

Select appropriate access modifiers such as classes and methods where access modifiers can be provided.

The types and release range of access modifiers are shown below.

|Access modifier|Release range|
|---|---|
|`public`|Accessible from all classes|
|`protected`|Accessible from own or same package class and sub class|
|（none）|Accessible by self and from classes in the same package|
|`private`|Can be accessed only by self|

Do not use `public` for declaration without good reason and grant an access modifier to a narrow range if required.

### <a name="no7-2">7.2.As a general rule, make instance variable private</a>

Instance variable should not be exposed outside the class.
As a general rule, make the instance variable `private`.

Exceptionally, if framework constraints require to be set to other than `private`, grant appropriate access modifier.

If an instance variable is created in abstract class and referred in subclass, configure to `protected`.
Even in that case, instead of making the instance variable public arbitrarily, check if the instance variables can be made `private` by combining the methods.

### <a name="no7-3">7.3.Use local variables by narrowing down the scope as far as possible</a>

Declare the local variable close to the location of use such that its scope is as narrow as possible.

```java
//Not Okay
final String text = ...

callMethod1();
callMethod2();
callMethod3();
.
.
.
callMethodN();

//The process that uses text is introduced for the first time after the process that does not use text continued endlessly
useText(text);
```

```
//OK
callMethod1();
callMethod2();
callMethod3();
.
.
.
callMethodN();

//Use text immediately after declaration
final String text = ...
useText(text);
```

Scope of local variables are in block units.
Declare local variables that can be used only within a specific block, within the corresponding block.

```java
//Not Okay
//Even though the local variable is used only within the if statement block, it is declared outside the block
final String text = ...
if (isSuccess(result)) {
    useText(text);
}
callOtherMethod();
return;
```

```java
//OK
//The variable has been declared within the if statement block
if (isSuccess(result)) {
    final String text = ...
    useText(text);
}
callOtherMethod();
return;
```

### <a name="no7-4">7.4.Avoid re-assignment as far as possible and use final</a>

The less "changeable state" the code is in, the easier it is to grasp and understand.

Reassigning a variable means creating a "changeable state" while it is still not known.

```java
String value = "hello";

...

//When a variable is reassigned, it changes to a "changeable state", and while reading the code
//It is always necessary to pay attention to "What is the value of this variable now?" 
value = "world";
```

Reduce the information to be understood when reading the code.
For that reason, avoid reassigning of variables.

To avoid reassigning of variables, use `final`.
If `final` is added when declaring variables, those variables cannot be reassigned.

```java
final String value1 = "hello";

...

//Since reassigning is not possible, the following commented out code throws a compile error.
//value1 = "world";

//Do not re-assign to already existing variables and create new variables
final String value2 = "world";
```

Try analyzing if re-assign could not be allowed by adding `final` to not only in local variables, but also in fields.
In some cases, it is necessary to define `setter` due to framework constraints. If not, please make the field such that re-assignment is not allowed as much as possible.

### <a name="no7-5">7.5.Do not change the argument state as far as possible</a>

The more local "Change of state" the code is, the whole code tends to be easy to grasp and understand.

When the state of argument is changed, places with change of state increases.

```java
//* This is an example of code that is not recommended, and not coded this way is better
//
//After calculating consumption tax, the tax is set in the argument
//If possible, it is better for the caller to set the tax on the item just to calculate the consumption tax.
public BigDecimal calculateTax(final Item item) {
    BigDecimal tax = item.getPrice().multiply(taxRate);
    item.setTax(tax);
    return tax;
}
```

If it is a `private` method, it is closed within the class, hence the impact range is limited. However, it is recommended not to change the argument state as much as possible in methods that are made public outside of the class, such as `public` and `protected`.


### <a name="no7-6">7.6.For using null as return value, consider the use of optional</a>

There is a method of using `null` as a means to represent the state where the value is "No", and there is a method by using `java.util.Optional` introduced in Java 8.

If the return value is set to `java.util.Optional`, it is possible to express that the method may not return a value in the signature.

```java
//It can be read that a value may not be returned from the signature of the method
public Optional<String> maybeReturnValue() {
    ...
    if (...) {
        return Optional.of(value);
    }
    return Optional.empty();
}

//(since it is not optional) It can be read that a value is always returned from the method signature.
public String mustReturnValue() {
    ...
}
```

Since `null` can be expressed with the same type as when there is a value, it is not possible to express that it is a method that may not return a value in the signature and must be handled by describing it in Javadoc.

```java
//It can be read that a value may not be returned from the method signature
public String maybeReturnValue() {
    ...
    if (...) {
        return value;
    }
    return null;
}

//It cannot be read that a value is always returned from the signature of the method
public String mustReturnValue() {
    ...
}
```

By using `java.util.Optional`, it is safe to receive support from the compiler because it is possible to express by type whether the existence check of the return value should be performed by the caller of the method.
Consider configuring the return value to `java.util.Optional` in methods that may not return a value.

```java
//In the case of methods that use Optional
//It can be understood from the type that the case when there is no return value has to be considered
final Optional<String> optional = maybeReturnValue();
final String value = optional.orElse(defaultValue);
```

```java
//In the case of methods that use null
//It cannot be understood from the type that the case when there is no return value has to be considered
//* Even if null check is omitted, a compile error will not occur and error detection is delayed
String value = maybeReturnValue();
if (value == null) {
    value = defaultValue;
}
```

### <a name="no7-7">7.7.Bind the appropriate type when using the class that captures type parameters such as collection</a>

Collection arguments such as `java.util.List<E>` and `java.util.Map<K, V>` and `java.util.Optional<T>` have type parameters defined.
When using such classes, bind the appropriate type.

By binding the appropriate type, advantage of type checking by the compiler can be utilized.

```java
//Not Okay
//Not OK since the type parameter is not bound
final List raw = new ArrayList();
//Not OK since the bound type is very abstract
final List<Object> objects = new ArrayList<>();
```

```java
//OK
//OK since a specific type is bound
final List<Item> items = new ArrayList<>();

final String item = ...
items.add(item); //Throws a compile error since it does not match the bound type

final Item item = ...
items.add(item); //Passes through compile as it matches the bind type
```

### <a name="no7-8">7.8.Consider writing the collection operations concisely using the Stream AP</a>

In some cases, it is possible to process collections such as `java.util.List` or `java.util.Set` with simple code using the Stream API introduced in Java 8.

The Stream API can use methods such as `filter`, `map` and `collect` to set minor operations for each element.
Hence, it is easy to understand what process is required for each element.

|Method|Description|Code example|
|---|---|---|
|`filter`|Narrows down only the elements that meet the conditions|`stream.filter(x -> x % 2 == 0) //Narrows down only to even numbers`|
|`map`|Converts elements|`stream.map(x -> x.getClass()) //Converts to Class`|
|`collect`|Converts to`Stream` using `Collector`|`stream.collect(Collectors.joining(", ")) //Converts elements to comma separated strings`|

For other methods, check [`java.util.stream.Stream`のJavadoc](https://docs.oracle.com/javase/jp/10/docs/api/java/util/stream/Stream.html).

A code example that uses Stream API and a code example that uses the extended for statement are shown below.
In both cases, the average age is calculated from the list of employees by narrowing the list down to only those employees whose job type is programmer.

It is easier to understand what kind of process is stacked to obtain the result in the code example using the Stream API.

```java
//Code example that uses the Stream API
final List<Employee> employees = ...
final IntSummaryStatistics statistics = employees.stream()
        //Job type is narrowed down to only programmers
        .filter(emp -> emp.getJobCategory().equals(programmer))
        //Age is extracted
        .mapToInt(emp -> emp.getAge())
        //Aggregation
        .summaryStatistics();
//Average is calculated
final double average = statistics.getAverage();
```

```java
//Code example that uses the extended for syntax
final List<Employee> employees = ...
double tempAge = 0;
int tempSize = 0;
for (final Employee employee : employees) {
    //Job type is narrowed down to only programmers
    if (employee.getJobCategory().equals(programmer)) {
        //Age is extracted and added to a temporary variable
        tempAge += employee.getAge();
        //The denominator number is incremented to find the average
        tempSize++;
    }
}
//Average is calculated
final double average = tempAge / tempSize;
```

Though not always simple, since the code readability and comprehension are subjective and not mandatory, consider using the Stream API when processing collections.

### <a name="no7-9">7.9.For process involving check exceptions throw, implement extended for syntax and not Stream API</a>

Although the Stream API can write the collection operation simply, `try-catch` must be written in the lambda expression when calling a method that has been declared to throw a check exception.
The Stream API, which can be written in a simple form, becomes complicated due to `try-catch`.
Hence, use the extended `for` statement when an operation that involves throwing a check exception is included.

`java.io.IOException` is often thrown during file input and output.
Using the extended `for` statement is recommended when processing files with the collection operation.

```java
//Not Okay
final List<String> heads = files.stream()
        .map(file -> {
            //IOException may be thrown in readLine
            try (BufferedReader in = openReader(file)) {
                return in.readLine();
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        })
        .filter(head -> head != null)
        .collect(Collectors.toList());
```

```java
//OK
final List<Path> files = ...
final List<String> heads = new ArrayList<>();
for (Path file : files) {
    //IOException may be thrown in readLine
    try (BufferedReader in = openReader(file)) {
        final String head = in.readLine();
        if (head != null) {
            heads.add(head);
        }
    }
}
```

### <a name="no7-10">7.10. Avoid using legacy for statement and consider using Stream API or extended for statement</a>

Since the Stream API and extended `for` statement can be used to process the collection elements sequentially, there is usually no situation where the legacy `for` statement is used.
Avoid using the legacy `for` statement as much as possible.

### <a name="no7-11">7.11.Use clone method to copy the whole array</a>

The simplest method to copy the whole array is using the `clone` method.

```java
//OK
final Item[] values = ...
final Item[] copied = values.clone();
```

The `copyOf` method of the `java.util.Arrays` class can also be used.
This method can specify the length to be copied in the second argument.

```java
//OK
final Item[] values = ...
final Item[] copied = Arrays.copyOf(values, values.length);
```

Both `clone` and `Arrays.copyOf` perform shallow copying.
To perform deep copying, it is necessary to perform the copying process for each element while looping.

```java
//OK
final Item[] values = ...
final List<Item> temp = new ArrayList<>(values.length);
for (Item item : values) {
    temp.add(copyItem(item));
}
final Item[] copied = temp.toArray(new Item[0]);
```

As in the case shown in the example, using the Stream API introduced from Java 8 makes the code more simple.

```java
//OK
final Item[] values = ...
final Item[] copied = Arrays.stream(values)
        .map(item -> copyItem(item))
        .toArray(Item[]::new);
```

### <a name="no7-12">7.12.Use the toArray method when converting collection to array</a>

The `toArray` method is available in a collection to convert it to an array.
Use the `toArray` method instead of looping each element to create an array.

```java
//Not Okay
final List<Item> items = ...
final Item[] itemArray = new Item[items.size()];
int index = 0;
for (final Item item : items) {
    itemArray[index++] = item;
}
```

```java
//OK
final List<Item> items = ...
final Item[] itemArray = items.toArray(new Item[0]);

//The toArray method is available even in the Stream API
//Use this method to convert a Stream to an array
final Item[] itemArray = items.stream().toArray(Item[]::new);
```

In this code example, the array that is passed to the `toArray` method is initialized with a length of `0`.
Although it is also possible to initialize the `size` method of the original collection by specifying the length, there is almost no difference in performance.
Hence any initialization method can be selected, although in this code example for these conventions, it has been initialized with a length of `0` in consideration of readability.

### <a name="no7-13">7.13.Use Arrays.asList or List.of when converting an array to collection</a>

The array utility `java.util.Arrays` class has the `asList` method that converts it to a list.
Use the `asList` method of the `java.util.Arrays` class instead of looping each element to create a list.

```java
//Not Okay
final Item[] itemArray = ...
final List<Item> items = new ArrayList<>(itemArray.length);
for (final Item item : itemArray) {
    items.add(item);
}
```

```java
//OK
final Item[] itemArray = ...
final List<Item> items = Arrays.asList(itemArray);
```

Since the `of` method has been added to `java.util.List` from Java 9, it can also be used here.

```
//OK
final Item[] itemArray = ...
final List<Item> items = List.of(itemArray);
```

From Java 9, the `of` method has been added to `java.util.Set` as well.
When trying to convert from an array to `java.util.Set` thus far, it was converted to `java.util.List` once and then `java.util.Set` was generated. From Java 8, conversion is performed using the Stream API.
From Java 9, conversion can be performed with a simple code.

```java
//Conversion method up to Java 7
final Set<Item> items =  new HashSet<>(Arrays.asList(itemArray));

//Can be converted with stream API from Java 8
final Set<Item> items =  Arrays.stream(itemArray).collect(Collectors.toSet());

//Can be converted more concisely from Java 9
final Set<Item> items = Set.of(itemArray);
```

### <a name="no7-14">7.14.Add @Override to methods when overriding method and implementing abstract method</a>

When overriding the super class method in the sub class, add `@Override` to the method on the subclass side.
When `@Override` is added, the compiler checks if the method is really overridden.

```java
//Not Okay
public class SuperClass {

    public void someMethod() {
        ...
    }
}

public class SubClass extends SuperClass {

    //Method name is incorrect and is not overridden.
    //Since it passes through compile, it is difficult to notice errors.
    public void sameMethod() {
        ...
    }
}
```


```java
//OK
public class SuperClass {

    public void someMethod() {
        ...
    }
}

public class SubClass extends SuperClass {

    //If @Override is added when it is not overridden, a compile error will be thrown.
    //Errors can be noticed during compile.
    @Override
    public void sameMethod() {
        ...
    }
}
```

From Java 6, `@Override` can be used even when implementing abstract methods that are declared in the interface.
Similarly, add `@Override` when overriding superclass methods.

```java
//OK
public class SomeAction implements Runnable {

    @Override
    public void run() {
        ...
    }
}
```

---

## <a name="no8">8.Available API</a>

By carefully selecting enough APIs to develop business applications, quality can be ensured.

### <a name="no8-1">8.1.Implement using the available standard API</a>

For APIs available in the Java standard library, see [Available APIs in the Java standard library](./staticanalysis/spotbugs/spotbugs-example/spotbugs/published-config/production/JavaOpenApi.config).

Also make use of [Unauthorized API check tool](./staticanalysis/unpublished-api/README.md) to check.

---

## <a name="no9">9.Nablarch Library</a>

Nablarch has carefully selected APIs that are made public to application programmers.
Developing by focusing on these APIs can ensure quality.

### <a name="no9-1">9.1.Implement by using the available Nablarch API</a>

For the APIs that are available in the Nablarch library, see the Javadoc for application programmers.

Consult the architect to use APIs that are not described in the Javadoc because of business requirements.

Also make use of [Unauthorized API check tool](./staticanalysis/unpublished-api/README.md) to check.
