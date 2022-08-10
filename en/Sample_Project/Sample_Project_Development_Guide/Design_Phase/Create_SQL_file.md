# Create SQL File

In this project, SQL files are created in the design phase.

It is necessary to write pseudo-SQL in the design document, but if this is created theoretically, then sufficient quality may not be ensured. 
This is because pseudo-SQL cannot check the syntax, nor can it confirm the validity of the execution result.

Usually, the SQL file is a product of the Programming and Unit testing phase, but in this project, it is positioned as a design phase product.
The designer creates the SQL while checking the validity by executing the SQL based on the common test data and test data created by themselves.

Until now, it was necessary to write SQL in a design document while theoretically thinking about SQL. 
For this reason, even if there is an ambiguous description or incorrect table or column names, it cannot be noticed until the Programming and Unit testing phase, 
which increases the burden on both the designer and Programming and Unit testing personnel.
In this project, the policy is to check the operation of SQL at the design stage, which is expected to improve quality.

See [Naming Convention for Coding](../PGUT_Phase/pg/Naming_convention_for_coding.md#sql-file) for SQL file names, locations, and SQL ID naming conventions.

To execute SQL, [SQL Exectutor](https://github.com/nablarch/sql-executor) is used. 
See README `SQL Exectutor` for instructions on the execution method.


