# SQL Style Guide

Here we provide coding conventions to help you code SQL.
This coding convention is based on [the Nablarch Application Framework database access](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/libraries/database_management.html).
However, the majority of the conventions are Nablarch-independent, and the Nablarch dependencies are limited to the following

- 2.1.1. Exceptions when using extended functions in Nablarch application framework
- 2.2. Format of SQL statement
    - Only one sentence of  "Write from SQL_ID to "=" in a single line." is assumed to be Nablarch. Excluding this sentence, it can be used for frameworks other than Nablarch.
- 2.2.1. Structure of SQL file
- 4.7. When value specified for the condition value or update value is fixed
    - The convention itself does not depend on Nablarch, but the SQL in the example uses Nablarch's own named bind parameters. It can be diverted by modifying the example.
- 4.8. Notes on $if syntax

If you use other frameworks, please review the above items and create a coding convention that matches the framework you use.

- [SQL coding conventions.docx](./SQL_Coding_Conventions.docx?raw=true)
