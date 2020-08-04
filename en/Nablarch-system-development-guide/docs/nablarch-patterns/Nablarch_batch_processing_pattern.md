# Nablarch Batch Processing Pattern

## Classification by startup method

[Nablarch batches](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/batch/nablarch_batch/index.html) are divided into two types depending on the startup method:

- On-demand batch
- Resident batch

## Classification by input/output

In addition to this classification, the batches can be divided into the following three patterns by the input and output combination.

- FILE to DB
- DB to DB
- DB to FILE

The combinations are as follows.

|          | FILE to DB | DB to DB | DB to FILE |
|----------|------------|----------|------------|
| On-demand | ○         | ○       | ○         |
| Resident     | ✕          | ○       | △         |

- ○：Use
- △：Possible in terms of the mechanism but is not used normally
- ✕：Do not use

(Resident batches typically monitor the database, so there is no combination with FILE to DB)


### FILE to DB

- This batch program is used to import files received from outside to the system.
- A batch program is created to import the files.
- The import destination is not a business table but a temporary storage table.
- Temporary tables have one-to-one columns mapped with file layout.

In FILE to DB batch, INSERT of files into temporary tables is performed without adding any business processes to the possible extent.
This provides the following benefits:

- After incorporating the program into the DB, the powerful features of RDB can be used.
  - Transaction
  - SQL
     - Conventional matching can be replaced with SQL JOIN
     - Conventional control break processing can be replaced with GROUP BY in some cases
- File import function of Nablarch (optional) can be used
  - If a failure occurs when importing files, importing can be resumed
  
  
### DB to DB

- The input is each record of the result set of the SELECT statement.
- Receives the data of one record and updates the DB.
- All updates are performed under the same transaction, so there is no inconsistency in the event of a failure.

### DB to FILE

- The input is each record of the result set of the SELECT statement.
- Receives one record of data and writes the file. (usually one line)

Although the DB is transaction-managed, file writing is not managed, so there may be inconsistencies when a failure occurs.


### Combinations other than the above (FILE to FILE)

For example, a common batch process is to "match two files while outputting one file."
In this case, it means FILE to FILE, but this form is not used in Nablarch batches. 
After importing each file into the DB, the same processing can be performed by JOIN in SQL.

Although it is possible to use Nablarch batches to perform file processing such as matching and control breaks, which are common in mainframe batches, there are problems such as the complexity of batch programs and difficulty in determining how much to handle in a file and how much in a DB.
Instead, using a combination of the above patterns will make each batch simpler, easier to design, and less likely to include bugs.


## Important points

### Moving/copying files

When performing FILE to DB or DB to FILE, moving or copying files in the batch process is not included.

The following batch processes are assumed to be implemented.

1. Move files to the specified directory (initialization process)
2. Read the file moved in step 1 and register it in the DB (main process)

If the process fails in step 2, an operation to return the input file to its original directory is necessary before re-executing.

By moving and copying the files separately, the following advantages can be obtained.

- Re-execution becomes easier
- Unit test of file import batch becomes easier
- Since moving and copying of files are not implemented, unit tests for moving and copying are unnecessary.


