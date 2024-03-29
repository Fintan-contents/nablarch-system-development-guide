# Naming Convention for Coding

## URL
The URL is determined once the method name of the Action class is determined, and not need be determined individually.   
(The method name of action class will be described later)

Supplementary notes:  
The link between the method name of the Action class and URL is performed in the following configuration file.   
`proman-web/src/main/resources/routes.xml`

Supplementary notes:
For details about Web service URLs, see [WebAPI URL Design](../../Design_Phase/WebAPI_URL_design.md).

## JSP
### Naming the deployment location

- `src/main/webapp/WEB-INF/view/` + Function name  
  Example：`src/main/webapp/WEB-INF/view/project`

### File name

| JSP types | Naming conventions                                     | Example:                       |
| --------- | -------------------------------------------- | ------------------------ |
| Screen for each process | word expressing processing + .jsp | create.jsp, update.jsp |
| Verification screen  | confirmationOf + word expressing processing + .jsp | confirmationOfUpdate.jsp |
| Completion screen  | completionOf + word expressing processing + .jsp   | completionOfUpdate.jsp   |


## SQL file

### Naming the deployment location
- `src/main/resources` + Package name of Entity or DTO that stores the search results  
   Example： `src/main/resources/com/nablarch/example/proman/entity/`

### File name

| Naming conventions                               | Example          |
| -------------------------------------- | ----------- |
| Entity class name or DTO class name + .sql | Project.sql |

### SQL ID

#### Search
| Case | Naming conventions                                                                                                | Example                                           |
| ---- | ------------------------------------------------------------------------------------------------------- | -------------------------------------------- |
| Normal | FIND_ + table name + BY_ + search condition column                                                      | FIND_CLIENT_BY_CLIENT_ID                     |
| When joining two tables | FIND_ + Main table name + WITH + Secondary table name + BY_ + Search condition columns  | FIND_PROJECT_WITH_ORFANIZATION_BY_PROJECT_ID |
| When there are many search condition columns | FIND_ + Keyword that expresses what you want to search                           | FIND_AUTHORIZED_REQUEST                      |


## Session key name

| Naming conventions                                                     | Example                        |
| ------------------------------------------------------------ | ------------------------- |
| Action class name (first lowercase letter) + Entity class name or DTO class name | projectCreateActionProject |



## Java source
### Class name

| Class         | Naming conventions                             | Example                  | Remarks               |
| ------------- | ------------------------------------ | ------------------- | ------------------ |
| Action class  | Words that express business directly + Action        | ProjectCreateAction | Create by system functional design units |
| Form class    | See "Details of form class name"       | ProjectCreateForm   |                    |
| Dto class     | See "Details of DTO class name"        | -                   |                    |
| Service class | Function name + Service                | ProjectService | Create by function units |

#### Details of form class name

| Application                                                                 | Naming conventions                     | Example                       |
| -------------------------------------------------------------------- | ---------------------------- | ------------------------ |
| Form for storing parameters when transitioning from other functions (search list screen, etc.) | Subfunction name + InitialForm | ProjectUpdateInitialForm |
| Form that stores the data entered in the input screen                               | Input screen name + Form    | ProjectUpdateForm        |


#### Details of DTO class name

| Application                                       | Naming conventions                                                       | Example                         |
| ------------------------------------------ | -------------------------------------------------------------- | -------------------------- |
| Store in a table that joins multiple tables | Exclude "FIND" at the beginning of the SQL ID and make it a camel case + DTO | ProjectWithOrganizationDTO |
| For data transfer                               | Keyword that expresses data + DTO                             | -                          |


### Method name
#### Action class
| Processing           | Naming conventions                    |
| -------------- | --------------------------- |
| Initial display       | index                       |
| Enter update       | enterUpdate                 |
| Confirm update       | confirmUpdate               |
| Update           | update                      |
| Registration input       | enterRegistration           |
| Confirm registration       | confirmRegistration         |
| Registration           | register                    |
| List display       | list                        |
| Show details       | show                        |
| Confirm deletion       | confirmDeletion             |
| Delete           | delete                      |
| Other than the above | Verb + noun, or verb only (e.g. backToIndex) |


#### Service class

- If only executing one SQL
  - If simple CRUD
    - See [Name of method to perform a simple CRUD](#name-of-method-to-perform-a-simple-crud).
  - If not simple CRUD
    - Make the SQL ID camel-cased (e.g. findAuthorizedRequest).
- Other than above
  - Based on the business meaning, name the method with "verb + noun" (e.g. searchProject for a method for the search screen).

##### Name of method to perform a simple CRUD

Using "CRUD verb + table name" as a basis, name the table as follows.

- Get 1 table Primary key search
  - find + Table name + ByID
- 1 new entry registration
  - insert + Table name
- 1 update
  - update + Table name
- 1 item delete
  - delete + Table name
