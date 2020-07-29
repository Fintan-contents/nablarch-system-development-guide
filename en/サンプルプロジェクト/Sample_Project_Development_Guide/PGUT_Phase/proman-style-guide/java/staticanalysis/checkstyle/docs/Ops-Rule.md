# Checkstyle operation guide

This guide is for using Checkstyle in an actual project.

Here, the term CheckStyle "violation" is used to mean "violates the CheckyStyle rules in the configuration file". * The importance of the rule (error, warning, etc.) does not matter.

## Basic concept

### Principle 1: All Checkstyle warnings should be resolved

Resolve all violations of the Checkstyle rules set in the project.
If you operate in a way that implicitly accepts violations, rule violations will be displayed constantly,
and you will not be motivated to correct the Checkstyle violations.

The goal is to always maintain the status without any violations.

### Principle 2: Do not reduce the quality to eliminate the Checkstyle violations

Developers must take action when they find Checkstyle violations,
but they must not reduce the quality of the source code when making modifications to avoid the violations.

In such cases, consult with an expert or project architect to get the appropriate solution.

Do not try to avoid Checkstyle violations at your own discretion.
Checkstyle is for improving (and not reducing) the code quality and reducing code quality to avoid this is a step in the wrong direction.


###  Principle 3: If there is a Checkstyle violation for a good reason, remove it from the check

If there is a good reason, exclude the source code that is in violation of the Checkstyle from being checked.

If the project architect receives a request from the developer and determines that it is appropriate, 
the relevant part is removed from the check.

This operation prevents violations from being neglected or forcibly avoiding violations by developers.

## Exception registration rules

### Method of avoiding Checkstyle violations

There are several ways to remove the parts with Checkstyle violation from the check target.
(Refer to [Checkstyle -Filters](http://checkstyle.sourceforge.net/config_filters.html) for details)

The following two will be explained here.

- Exclusion configuration using the exclusion configuration file ([SuppressionFilter](http://checkstyle.sourceforge.net/config_filters.html#SuppressionFilter))
- Exclusion setting using the line comment ([SuppressWithNearbyCommentFilter](http://checkstyle.sourceforge.net/config_filters.html#SuppressWithNearbyCommentFilter))


### Exclusion configuration using the exclusion configuration file

In the exclusion configuration using the exclusion configuration file, the source codes to be excluded are listed.
This method has an advantage that architects can easily manage them centrally as the source code to be excluded is collected in one configuration file.

Exclusion requests from developers are received and managed by an issue management system such as Redmine.
If the architect decides that the exclusion request is valid and decides to exclude, the exclusion settings are added to the configuration file.

The following items are described in the configuration file.

- Target file (regular expression)
- Target check items (regular expressions)

The following are examples of the description in exclusion configuration files.

``` xml
<suppressions>

  <!-- Automatically generated code is excluded -->
  <suppress checks=".*" files=".*Entity.java$" />

  <!-- Exclusion setting with #12345 -->
  <suppress checks=".*" files="Foo.java" />
  
</suppressions>
```

When setting up exclusions, you can track the history later with the following steps.

- Write the issue management number of the issue management system in the XML comment
- Describe in the commit comment of the version management system

#### Description method in configuration files

Write the path of the exclusion configuration file in the Checkstyle configuration file.
An example of placing two files in the same directory is shown below.

``` xml
  <module name="SuppressionFilter">
    <property name="file" value="${config_loc}/suppressions.xml"/>
    <property name="optional" value="false"/>
  </module>
```

`${config_loc}` is interpreted as "the directory in which the Checkstyle configuration file is located" when using the Eclipse Checkstyle and IntelliJ Checkstyle-IDEA plug-ins.

When launching from Maven, see [Guide on how to execute Checkstyle with Maven](./Maven-settings.md).

#### Note

In this method, exclusion is performed in file units and cannot be used for detailed exclusions such as method units.
Therefore, try to avoid as many exclusions as possible.
It is necessary to take measures such as rules that allow only unavoidable items to be excluded, and abolishing items which are difficult to observe realistically.

Exclusion is also possible by specifying the line number, but the line number method is not recommended because the line numbers will change after corrections.


### Exclusion setting using the line comment

By adding a specific comment to the part of the source code violating the Checkstyle,
the corresponding line can be excluded from Checkstyle.


In the following example, an underscore is used for the variable name, which is normally considered to violate the Checkstyle, 
but is excluded from Checkstyle because a line comment has been used to exclude it.

``` java
   private int counter_;  // SUPPRESS CHECKSTYLE #12345
```

Exclusion requests from developers are received and managed by an issue management system such as Redmine.
If the Architect decides that the exclusion request is appropriated for exclusion, 
the developer includes the issue management number of the issue management system in the line comment.

The following are examples of the description in Checkstyle configuration files.
In the following example, the corresponding line is excluded from the check by writing a comment such as "// Exclusion setting with SUPPRESS CHECKSTYLE # 12345".


``` xml
    <!-- Checkstyle exclusion with a comment -->
    <module name="SuppressWithNearbyCommentFilter">
      <!--
        The corresponding line can be excluded from Checkstyle by writing a comment in a specific format.
        Comment example: // Exclusion setting with SUPPRESS CHECKSTYLE #12345
       -->
      <property name="commentFormat" value="SUPPRESS CHECKSTYLE #\d+"/>
    </module>
```

The advantage of this method is that it is easy to understand which parts of the source code have been excluded.
This method excludes by pinpointing the parts which are in violation and excluded parts can be minimized.

On the other hand, the disadvantage that management is difficult as the exclusions are described directly in the source code.
For example, exclusion settings are also copied when the source code is copied,
and unintended code may assigned exclusion settings.

It is necessary to devise methods such as issuing one issue ticket for each exclusion setting to avoid duplicate issue management numbers.
(if grep is used, duplicate issue management numbers will be considered to be incorrect).


## Combination with code formatter

Some Checkstyle violations can be resolved automatically by running the code formatter (indents, spaces, etc.).
Configure such that consistency is maintained in both the code formatter configuration and Checkstyle configuration.
You can reduce violations by setting the formatter to run automatically when committing to the version control system.
