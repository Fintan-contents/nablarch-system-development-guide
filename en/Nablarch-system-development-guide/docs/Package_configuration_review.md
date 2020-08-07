# Package Configuration Study

## Concept of package configuration

When it comes to the package configuration there is "no certain way to do this", but there are basic concepts.
Consider package configuration according to the project characteristics.

Basically, it is desirable to decide on a package structure that facilitates the division of labor.
If you divide up the project so that the scope of responsibility for the deliverables is clear, such as assigning a member in charge to each package in consideration of the project structure, it will be easier to proceed with development.


The following 2 ways (or a combination) can be used to consider the package configuration.

- Division by business function
- Division by class role


As an example, the "member management system" has the following business functions.

| Business name   | Package name |
|----------|--------------|
| Enrollment     | enrollment       |
| Attribute change | settings       |
| Point | point        |

## Example of division by business function

When divided by business function, it is as follows.

- com.example.member
  - enrollment
    - XXXAction
    - XXXForm
    - XXXDto
    - YYYAction
    - YYYForm
    - YYYDto
    - ZZZService
  - settings
    - (omitted)
  - point
    - (omitted)



When development is carried out in a project, work is often assigned by grouping the business functions. 
For example, assigned as follows:

- Company A is in charge of xxx subsystem
- B is in charge of yyy function

In this case, dividing a package into business functions makes it easier to divide labor and parallel development because only that member's work is stored under the package.
For example, if an unused class is under the package, the person in charge of it is responsible for reviewing the need and removing it.

However, if segregation of a package is too big, development becomes more difficult as too many classes are stored in one package.
In such a case, the number of classes in 1 package can be reduced by subdividing the package into multiple hierarchies.
 For example, if the function of "enrollment" business expands, create a hierarchy like　`enrollment/review` or `enrollment/campaign`


## Example of division by class role

Division according to class role is as follows.

- com.example.member
  - action
    - XXXAction
    - YYYAction
    - (omitted)
  - form
    - XXXForm
    - YYYForm
    - (omitted)
  - dto
    - XXXDto
    - YYYDto
    - (omitted)
  - service
    - ZZZService
    - (omitted)

The so-called layered architecture is easier to achieve with this configuration.
This is not very common, but sometimes the work is divided by layers rather than by business functions. 
(such as presentations and domains)
In such cases, it is easier to divide the package according to class role.

If the system size is too large, development becomes difficult due to too many classes in the package. 
The problem cannot be solved with this package configuration, and large projects should avoid this configuration.


### Example of division by class role - business function

The above two can also be combined.


- com.example.member
  - action
    - enrollment
      - XXXAction
      - YYYAction
    - settings
      - (omitted)
    - point
      - (omitted)
  - form
    - enrollment
      - XXXForm
      - YYYForm
    - settings
      - (omitted)
    - point
      - (omitted)
  - dto
      - XXXDto
      - YYYDto
    - settings
      - (omitted)
    - point
      - (omitted)
  - service
    - enrollment
      - ZZZService
    - settings
      - (omitted)
    - point
      - (omitted)

In this case, the Action classes are aggregated into a single package. Since `com.example.member.action` can be specified as the root package, it is easier to make entries in the [routes.xml](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/adaptors/router_adaptor.html).
    

### Example of division by business function - class role

At first glance it looks good, but it is difficult to handle with [routes.xml](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/adaptors/router_adaptor.html) of Nablarch because it allows multiple action packages. 
There is a disadvantage that if you try to forcibly use this method, the URL length will be longer.

- com.example.member
  - enrollment
    - action
      - XXXAction
      - YYYAction
    - form
      - XXXForm
      - YYYForm
    - dto
      - XXXDto
      - YYYDto
    - service
      - ZZZService
  - settings
    - action
    - (omitted)
  - point
    - (omitted)


