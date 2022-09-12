# Method to Proceed with Domain Definition

## Domain definition design document

When defining a domain, the domain is first listed in the domain definition design form.

Based on this domain definition design document, we will add domains to ER diagrams (edm files) and domain Bean (Java files).

## ER diagram

The domain added to the ER diagram is used to specify the domain of the column.
In Object Browser ER, the type and digits are determined automatically by specifying the domain.
Specifying a domain for a column ensures that the type and digits of the column that takes the same domain will always match. 
For example, if the membership number domain is `CHAR(10)`, the column with the membership number domain will always be `CHAR(10)`.

## Domain Bean

In the form class, add a definition to the domain Bean so that you can specify a domain for the item.
For more information, see [Domain Validation](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/libraries/validation/bean_validation.html#bean-validation-domain-validation).