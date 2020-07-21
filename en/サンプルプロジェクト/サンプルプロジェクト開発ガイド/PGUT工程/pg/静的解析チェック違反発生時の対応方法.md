# Response Method When a Static Analysis Check Violation Occurs

## Principle concept

Follows the operation guide of the coding convention.

- [Checkstyle operation guide](../../PGUT工程/proman-style-guide/java/staticanalysis/checkstyle/docs/Ops-Rule.md)
- [SpotBugs operation guide](../../PGUT工程/proman-style-guide/java/staticanalysis/spotbugs/docs/Ops-Rule.md)

## Specific measures for this project

In principle, resolve all warnings. 
If the warning cannot be resolved or there is a reason that it is better not to resolve the warning forcefully, 
request for exclusion with the following procedure.

- The developer issues a Redmine ticket stating the exclusion request and changes the person in charge to the architect
- The architect checks the contents
  - If the description is valid, the architect sets the Checkstyle, SpotBugs exclusion and returns the ticket to the developer
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
