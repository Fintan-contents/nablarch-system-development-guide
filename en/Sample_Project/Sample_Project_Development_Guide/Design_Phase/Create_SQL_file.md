# Create SQL File

In this project, SQL files are created in the design phase.

It is necessary to write pseudo-SQL in the design document, but if this is created theoretically, then sufficient quality may not be ensured. 
This is because pseudo-SQL cannot check the syntax, nor can it confirm the validity of the execution result.

Usually, the SQL file is a product of the Programming Unit testing phase, but in this project, it is positioned as a design phase product.
The designer creates the SQL while checking the validity by executing the SQL based on the common test data and test data created by themselves.

Until now, it was necessary to write SQL in a design document while theoretically thinking about SQL. 
For this reason, even if there is an ambiguous description or incorrect table or column names, it cannot be noticed until the Programming Unit testing phase, 
which increases the burden on both the designer and Programming Unit testing personnel.
Now the work can be carried out while trying out the SQL and higher quality can be expected.


To execute SQL, [SQL Exectutor](https://github.com/nablarch/sql-executor) is used. 
See README `SQL Exectutor` for instructions on the execution method.


