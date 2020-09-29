# Nablarch Anti-pattern

If you design and manufacture without taking into account the usage that Nablarch assumes,
it may lead to major rework, poor performance, or production failure in the worst case.

Here are some examples of actual errors.

## Web application

### Multithreaded bugs due to the misunderstanding of component lifecycle

The Nablarch [system repository](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/libraries/repository.html#repository) has DI container functionality, but the component life cycle is different from other DI containers.

The lifecycle of components managed in Nablarch's system repository is a `singleton`.
Misunderstanding the default life cycle as a `prototype` or `request` and rewriting the state of the component will affect other threads and requests that use the same component.

To ensure that such bugs are not embedded, the life cycle of the components managed by the system repository is required to be known.

When using Nablarch, there are not many cases where it is necessary to obtain components from the system repository to create normal business application code (except when creating system base parts, etc.). 
Since the component life cycle is a `singleton`, it is not used to rewrite the state of the component except for the initialization process. 
If you see a code that changes the state of a component, you need to suspect that it may be a bug due to the misunderstanding described above.

## Nablarch batch

If you do not understand how the framework works when designing and implementing batches, you may create batches having an incorrect structure. 
Even in such cases, the business requirements may be satisfied as a batch process, 
but the performance may deteriorate, or the process may end abnormally as the number of batches increase.
Such defects may not be discovered with the unit test phase that tests with a small number of items and will be discovered only when testing with a large amount of data, usually, during the end of the project. 
Understand how the framework works to avoid incorrect design or implementation.

The following is an example of incorrect implementation.

### N+1 problem

"N" in "N+1" is the number of data (SELECT hits) held by the reader created with the createReader method. 
In a Nablarch batch, the problem occurs in the handle method when SELECT is issued again based on the input data.

The performance will deteriorate as the number of objects to be processed increases. 
In SQL issued by createReader, the problem can be avoided by acquiring (JOIN) with one SQL statement.

If data can be acquired by one SQL statement, only the first SQL is necessary for data acquisition regardless of the number of processing targets. 
However, batches with N+1 problems can cause severe performance degradation.
If the number of targets to be processed is 100, 101 SQL statements will be issued, and if it is 10,000, 10,001 SQL statements will be issued.


#### Not OK example

In the example below, the revenue statement SQL is executed +1 times for the number of acquired revenue SQLs.

##### createReader method

```sql
SELECT
  REVENUE_ID,
  REVENUE_DATE
FROM
  REVENUE
WHERE REVENUE_DATE = ?
```

##### handle method

```sql
SELECT
  REVENUE_STATEMENT_ID,
  AMOUNT_OF_MONEY
FROM
  REVENUE_STATEMENT
WHERE REVENUE_ID = ?
```


#### OK example

By performing JOIN, necessary data can be acquired with one SQL statement, and SQL statements need not be issued in the handle method.

createReader
```sql
SELECT
  REVENUE.REVENUE_ID,
  REVENUE.REVENUE_DATE,
  REVENUE_STATEMENT.REVENUE_STATEMENT_ID,
  REVENUE_STATEMENT.AMOUNT_MONEY
FROM REVENUE
INNER JOIN REVENUE_STATEMENT ON REVENUE.REVENUE_ID = REVENUE_STATEMENT.REVENUE_ID
WHERE REVENUE.REVENUE_DATE = ?
```

### Loop processing not under framework control

It is an anti-pattern of "issuing a SELECT statement on its own and loop to perform registration update processing with the handle method".
Loops in the framework are committed at regular intervals. However, commit is not performed for self-loops. 
For this reason, the transaction log becomes lengthy as the number of updates increases.

#### Not OK example

``` {.java}
public Result handle(ExecutionContext context) {
   // Search execution
   SqlResultSet sqlResultSet = search("SEARCH");
   // Self-processing
   for (SqlRow row : sqlResultSet) {
       // :
       // Update processing

   }
}
```


If there is a large number of search results, a large number of UPDATE statements will be executed within a transaction.
In particular, using [NoInputDataBatchAction](https://nablarch.github.io/docs/LATEST/javadoc/nablarch/fw/action/NoInputDataBatchAction.html) to perform such a loop is a typical mistake.


In the past, there were cases where transaction control was performed on its own, such as executing a commit after a certain number of times in a loop so that a large number of records could be processed.This means that the processing performed by the framework must be re-implemented on its own, which may cause quality and productivity to decrease.


### Solution

Realize by framework management loop processing instead of self-loop processing.
In the case of the above example, the SQL issued in the handle will be performed by createReader.


## JSR352 batch


### Misuse of batchlet

This anti-pattern is the same as "loop processing not under the framework control" (Nablarch batch) described above.
The same problem occurs when Batchlet is used to implement batches that should be designed and implemented with Chunk.

As in the [batch types](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/batch/jsr352/architecture.html#jsr352-batch-type), it is necessary to understand the purpose of each batch and use them appropriately.

| Batch type | Application                                                                                           |
|--------------|------------------------------------------------------------------------------------------------|
| Batchlet     | Process that retrieves the file from an external system or completes processing with one SQL statement                             |
| Chunk        | Process that reads records from input data sources such as files and databases and executes business processes |

