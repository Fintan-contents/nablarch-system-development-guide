# How to Build

## Required environment

Refer to the [Development Environment Construction Guide](../../Sample_Project_Development_Guide/PGUT_Phase/Development_environment_construction_guide.md) for the required environment.

Configure the initial user and password for PostgreSQL as follows.

| user     | password|
|:---------|:--------|
| postgres | password|

## How to build

Run the following command.
```
$mvn -P gsp clean generate-resources
```

Note: Be sure to run the above command before launching the application.
