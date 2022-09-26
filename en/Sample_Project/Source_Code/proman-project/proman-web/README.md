# Starting the Application and Login Method

## Required environment

Refer to the [Development Environment Construction Guide](../../Sample_Project_Development_Guide/PGUT_Phase/Development_environment_construction_guide.md) for the required environment.

Configure the initial user and password for PostgreSQL as follows.

| user     | password|
|:---------|:--------|
| postgres | password|


## Launching method

Run the following command in proman-project.
```
$cd proman-project
$mvn -N install
```

Run the following command in the proman-common module.
```
$cd proman-common
$mvn -P gsp clean generate-resources
$mvn install
```

Go into the proman-web directory and run jetty-maven-plugin and start Jetty. Run the following command.

```
$cd proman-web
$mvn jetty:run
```

After successful startup, please display the login screen with the following URL.

<http://localhost:9088/>

You can log in with the following login ID and password.

| Login ID | Password | PM |
| :--------- | :--------- | :--------- |
| 10000001   | pass123-   | ÅZ   |
| 10000002   | pass123-   | ÅZ   |
| 10000003   | pass123-   | Å~   |
| 10000004   | pass123-   | Å~   |
| 10000005   | pass123-   | Å~   |
