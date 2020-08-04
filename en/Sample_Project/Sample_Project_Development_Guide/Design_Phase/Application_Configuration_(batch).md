# Application Configuration (Batch)

The batch application configuration in this project is the same as [Nablarch batch application](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/batch/nablarch_batch/index.html).

In [application configuration (Web)](Application_Configuration_(Web).md),
configuration has been defined in this project to improve the testability, 
but in the case of batch, the application configuration has not been independently defined in the project.

The reasons for determining that there is no need to change from the standard configuration of Nablarch are as follows:

- Requirements of this project can be satisfied with the same configuration as before
- Test method is different from the Web (described later)

Request unit tests are used in batches. 
For batches, request unit tests are more efficient than manual tests.
In the batch, except for a part of the form, rest is to the request unit test, and the processing under action will be covered in the request unit test.

When testing in this way, deploying a Service does not improve testability just by adding more classes. 
Therefore, the standard configuration of Nablarch was adopted without deploying services in batch.
