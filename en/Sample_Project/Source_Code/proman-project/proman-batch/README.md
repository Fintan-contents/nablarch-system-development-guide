# How to Launch a Batch Application

## Required environment

Refer to the [Development Environment Construction Guide](../../Sample_Project_Development_Guide/PGUT_Phase/Development_environment_construction_guide.md) for the required environment.

Configure the initial user and password for PostgreSQL as follows.

| user     | password|
|:---------|:--------|
| postgres | password|


## Build method

Run the following command in the proman-common module.
```
$cd proman-common
$mvn -P gsp clean generate-resources
```

Next, build the application. Run the following commands.

```
$mvn clean package
```


## Batch Launching Method


Run the following command.

```
$mvn exec:java -Dexec.mainClass=nablarch.fw.launcher.Main -Dexec.args="'-diConfig' 'classpath:batch-boot.xml' '-requestPath' 'project.ExportProjectsInPeriodAction' '-userId' 'batch_user'"
```

If the startup succeeds, N21AA002.csv will be created under the work/output directory.