# Handling Method When an Error Occurs（Web）

## Exclusive control error when updating

### What happens when an exclusive control error occurs

DaoContext#update throws javax.persistence.OptimisticLockException.

### Handling contents

Nothing is done in the application implementation. 
Transition to the common error screen is controlled on the base side.

## When the data that should exist in one acquisition could not be acquired

### What happens when data that should exist in one acquisition could not be acquired

DaoContext#findById throws nablarch.common.dao.NoDataException.

### Handling contents

Nothing is done in the application implementation.
Transition to the common error screen is controlled on the base side.

## When the search result is 0 in the search

### What happens when the search result is 0

DaoContext#findAllBySqlFile returns an empty list.

### Handling contents

The Service class does nothing and returns an empty list for the Action class. 
Throw ApplicationException with Action class method.
An implementation example is given below. Match MessageLevel and message ID to the implementation example.

````java
throw new ApplicationException(
        MessageUtil.createMessage(MessageLevel.ERROR, "errors.search.nothing"));
````

In JSPs, n:errors is used to display error messages to the user. 
An implementation example is given below.

````java
<n:errors filter="global"/>
````

By writing global in the filter attribute, only errors that are not associated with the input item will be output.
