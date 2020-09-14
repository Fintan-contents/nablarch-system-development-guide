# Client Management System

## Required environment

Refer to the [Development Environment Construction Guide](../../Sample_Project_Development_Guide/PGUT_Phase/Development_environment_construction_guide.md) for the required environment.

Configure the initial user and password for PostgreSQL as follows.

| user     | password|
|:---------|:--------|
| postgres | password|


## How to launch

Execute the following commands.

```
$ mvn -P gsp clean generate-resources
$ mvn compile waitt:run-headless
```

## Verify operation

It works if you access the following URL and the customer list (JSON) is returned.

```
http://localhost:9080/client
```