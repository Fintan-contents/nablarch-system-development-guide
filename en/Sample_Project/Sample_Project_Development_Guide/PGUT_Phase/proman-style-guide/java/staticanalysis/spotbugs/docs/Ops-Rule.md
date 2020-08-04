# SpotBugs operation guide

## Basic concept

### Principle 1: All SpotBugs warnings will be resolved

SpotBugs detects either bug-embedded code or dangerous code that is likely to cause problems later.
Allowing warnings is synonymous with accepting the risk of a bug or potential problem and should not be done.

The goal is to always maintain the status without any warnings.

### Principle 2: Do not reduce the quality to eliminate SpotBugs warnings

Developers must take action when they find SpotBugs warning, but they must not reduce the quality of the source code when making modifications to avoid the warnings.

In such cases, consult with an expert or project architect to get the appropriate solution.

Do not try to avoid SpotBugs warnings at your own discretion. SpotBugs is for improving (and not reducing) the code quality, and reducing code quality to avoid this is a step in the wrong direction.

### Principle 3: If there is a SpotBugs warning for a good reason, remove it from the check

As mentioned in Principle 1, SpotBugs warnings basically indicate the risk of a bug or potential problem.
Therefore, it is difficult to think of a good reason to exclude it from the check.

However, automatically generated source code may be excluded from the check.
If SpotBugs warning appears in the auto-generated source code, then the tool that performs the automatic generation needs to be corrected.

If you want to exclude the "Unauthorized API Check Tool" that operates as a SpotBugs plug-in, you can exclude it from the check target.
Even in such cases, the exclusion settings should be localized so that only specific classes are excluded, instead of a wide range, such as the entire package.

## Method to exclude from check

### Exclusion method using filter files

SpotBugs uses an XML file called [Filter File](http://spotbugs.readthedocs.io/ja/latest/filter.html) to exclude certain classes from being checked. 
If SpotBugs is installed according to [How to run SpotBugs with Maven](./Maven-settings.md), the filter file is located in `spotbugs/spotbugs_exclude_for_production.xml`.

Exclusion requests from developers are received and managed by an issue management system such as Redmine.
If the architect decides that the exclusion request is valid and decides to exclude, the exclusion settings are added to the filter file.

The following 2 are described in the filter file.

- Target class (or target package)
- Type of bug to be detected

An example of the filter file is shown below.

```xml
<FindBugsFilter>

  <!-- Exclusion settings for auto-generated source code -->
  <Match>
    <Or>
      <!-- Exclusion settings when auto-generated source code is output to a specific package -->
      <Package name="com.example.generated"/>
      <!-- Exclusion settings when the class name of the auto-generated source code has regularity -->
      <Class name="~com\.example\.Generated.+"/>
    </Or>
  </Match>

  <!-- Settings to exclude unauthorized API check -->
  <Match>
    <!-- Exclude a specific method from being checked for usage of unauthorized API -->
    <Bug pattern="UPU_UNPUBLISHED_API_USAGE"/>
    <Class name="com.example.Foobar"/>
    <Method name="baz" params="java.lang.String, int"/>
  </Match>

</FindBugsFilter>
```

If there is a `Bug` element directly below the `Match` element, the specified bug is excluded from being detected.
If there is no `Bug` element, all bugs that SpotBugs can detect are excluded.

If there is a `Package` or `Class` element directly below the `Or` element, the package and class to be excluded from the check are specified.
Package classes that exactly match the `name` attribute value is excluded.
The `name` attribute value is a regular expression if it starts with a tilde (`~`).

`Method` element is available in the setting example for excluding the unauthorized API check, and it is set to be excluded from the check for each method.

For more details, see [Filter File Description on Official Website](http://spotbugs.readthedocs.io/ja/latest/filter.html).

When setting up exclusions, you can track the history later with the following steps.

- Write the issue management number of the issue management system in the XML comment
- Describe in the commit comment of the version management system

### Permit the use of a specific API with the Unauthorized API Check Tool

A configuration file can be modified in the Unauthorized API Check Tool to allow the use of specific APIs.

Review the APIs that failed the unauthorized API check, and if the APIs are required for development, discuss with the project architect.
If the architect finds that the API is required for development, then edit the configuration file of the Unauthorized API Check Tool to allow the use of the API.

See the explanation of [Unauthorized API Check Tool](../../unpublished-api/README.md#Method-to-write-the-configuration-file).

