# Client Management System

## How to launch

Execute the following commands.

```
$ mvn -P gsp clean generate-resources
$ mvn compile waitt:run-headless
```

## Operation check

It works if you access the following URL and the customer list (JSON) is returned.

```
http://localhost:9080/client
```