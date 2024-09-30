# Client Management System (Climan)

This is the source code of the "Client management system".  
(**Cli**ent **Man**agement System is abbreviated as **Climan**)

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
$ mvn jetty:run
```

## Verify operation

It works if you access the following URL and the customer list (JSON) is returned.

```
http://localhost:9080/client
```