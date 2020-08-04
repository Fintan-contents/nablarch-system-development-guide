# Package Configuration Study

## Concept of package configuration

When it comes to the package configuration there is "no certain way to do this", but there are basic concepts.
Consider package configuration according to the project characteristics.

If there is a constraint on the URL of the system, it is better to first design the URL and decide the package configuration that can realize the URL.
This is because when the routing is configured in Nablarch, in some cases the URLs are constrained depending on the package configuration.

For intranet systems, especially when there are no constraints on the URL, it is preferable to decide the package configuration considering the project structure.


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

In this case, only the deliverables of the assignee are stored under the package, making the division of labor and parallel development easier. 
For example, if classes that are not used are present under a package, the assignee for that package is responsible. 
If there are multiple assignees for a single package, the responsibility for the deliverables may be ambiguous.

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

Since `com.example.member.action` can be specified as the root package, it is easier to make entries in the [routes.xml](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/adaptors/router_adaptor.html).
    

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


