# Unit Test Concept (Batch)

In principle, batches observe the [Nablarch standard test method](https://nablarch.github.io/docs/LATEST/doc/en/development_tools/testing_framework/guide/development_guide/05_UnitTestGuide/index.html).

Describes the deliverables created by the developer and how to test them.

| Deliverables         | Theoretical | Class | Request |
|----------------|------|------------|----------------|
| Action         |      |            | X              |
| Form           | X    | X          | X              |
| Domain class | X    |            | X              |
| SQL file    | X [^1]  |            | X              |
| Entity         |      |            | X              |
| DTO            |      |            | X              |

[^1]: Confirmation of SQL is not only the theoretical review, but also by testing SQL by the SQL Executor.

## Action

In Action class, the operation is checked with request unit test.


## Form

Same as [Unit Test Concept (Web)](./Unit_test_concept_(Web).md#form).

## Domain class

Same as [Unit Test Concept (Web)](./Unit_test_concept_(Web).md#domain-class).
## SQL file

Same as [Unit Test Concept (Web)](./Unit_test_concept_(Web).md#sql-file).
## Entity

Same as [Unit Test Concept (Web)](./Unit_test_concept_(Web).md#entity).
## DTO

Same as [Unit Test Concept (Web)](./Unit_test_concept_(Web).md#dto).