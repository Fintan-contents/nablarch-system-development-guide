# Test Method of Automated Subfunction Unit Test (Web)

This chapter describes a tool that uses JUnit to automatically execute the subfunction unit tests described in [Test Method of Subfunction Unit Test (Web)](Test_method_of_subfunction_unit_test_(Web).md).

## Notes on integration into CI
This automated tool is provided to integrate subfunction unit testing into CI.

However, this method using JMeter tends to increase the execution time.
Be aware of the execution time when integrating this test into CI.

If the execution time becomes unacceptably long, consider cutting it out as a stand-alone task and running it as needed, rather than integrating it into an iterative CI.


## Place of the tool
The tool for automatic execution are provided in the following places in the project management system.

[proman-jmeter](../../../Source_Code/proman-project/proman-jmeter)

## Run a sample scenario
The `proman-jmeter` provides two sample subfunction unit test scenarios.

1. register new project
2. update the project

This section describes how to make the two sample scenarios run in practice.

### Launch the project management system
For information on how to launch the project management system, please refer to the project management system [README](../../../Source_Code/proman-project/proman-web/README.md).

### Run the tool
Before running the tool, must set the local JMeter installation directory to the tool.

Open the file [proman-jmeter/src/test/resources/env.properties](../../../Source_Code/proman-project/proman-jmeter/src/test/resources/env.properties)  with a text editor and set the `jmeter.home` value to the local JMeter installation directory.

```properties
（omitted）
jmeter.home=[Configure the JMeter installation directory here]
（omitted）
```

After configuring JMeter, go to the `proman-jmeter` directory on the command line and execute the following command.

```
$cd proman-jmeter
$mvn clean test
```

If the test is successful, you should see the following on the command line.

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## How it works and how to use it in detail
For more information on how the tool works and how to use it in detail, please refer to the tool's [README](../../../Source_Code/proman-project/proman-jmeter/README.md).

## To be used in a project other than the sample project
This automation tool does not depend on the project management system.
Therefore, only the `proman-jmeter` can be cut out and used in another project.

If you want to use this tool in a project other than the sample project, extract the `proman-jmeter` and use it as follows.

1. copy the `proman-jmeter` directory to an arbitrary directory
    - Change the directory name if necessary
2. Modify `pom.xml`.
    - Modify `<groupId>`, `<artifactId>`, and `<version>` in `pom.xml` to match your project.
3. Remove the sample test scenario.
    - Please remove sample test scenarios (`WA10201`, `WA10203`) under `src/test/resources`.
4. put in place the test scenarios to be used in the project.
    - For the process to create a new test scenario, refer to "How to create a test scenario" in [README](../../../Source_Code/proman-project/proman-jmeter/README.md) of `proman-jmeter`.
