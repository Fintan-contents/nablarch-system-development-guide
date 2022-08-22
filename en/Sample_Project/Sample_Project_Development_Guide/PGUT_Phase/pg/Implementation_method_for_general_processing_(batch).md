# Implementation Method for General Processing（Batch）

## Batch with DB as input

### Implementation method of input data reading section

- Inherit and implement `BatchAction<SqlRow>`.
- Use DatabaseRecordReader for reader.

Generate a statement as follows and set it in DatabaseRecordReader.
````java
    /**
     * {@inheritDoc}
     * {@link DatabaseRecordReader} that reads data to be output to file is generated
     */
    @Override
    public DataReader<SqlRow> createReader(ExecutionContext ctx) {

        int count = countByStatementSql("GET_OUTPUT_FILE_DATA");
        writeLog("M000000001", count);

        DatabaseRecordReader reader = new DatabaseRecordReader();
        SqlPStatement statement = getSqlPStatement("GET_OUTPUT_FILE_DATA");
        reader.setStatement(statement);
        return reader;

    }
````

## Batch with file as input

### Implementation method of input data reading section

- The reader inherits and implements `DataReader<form class>`.
- The action inherits and implements `BatchAction<form class>`.
- For specific implementation, refer to [Create batch to register files in DB](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/batch/nablarch_batch/getting_started/nablarch_batch/index.html).

## Batch output

### Batch to output file
- Use the file path management function for file path management.   
  [Nablarch application framework reference manual 7.5. File path management](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/libraries/file_path_management.html#file-path-management)
- Use data binding for file output.   
  [Nablarch application framework reference manual 7.4.1. Data binding](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/libraries/data_io/data_bind.html)
- Opening files (Generate ObjectMapper) should be done in the overridden BatchActionBase#initialize.  
- Closing files (close the ObjectMapper) should be done in the overridden BatchActionBase#terminate.
