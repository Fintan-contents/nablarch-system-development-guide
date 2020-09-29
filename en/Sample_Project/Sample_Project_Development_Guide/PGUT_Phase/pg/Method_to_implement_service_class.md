# Method to Implement Service Class

The service class implements business logic. 
This section describes the topics common to all processing methods for the service class.

## Implementation policy of service class

An implementation that can replace the DB access part with the stub when the unit is tested is assumed.
As a method, the DaoContext class is initialized with the constructor of the Service class and used to access the DB.

The DaoContext class is used by Nablarch inside the UniversalDao class. 
This method is used because UniversalDao class is used without instantiation and cannot be replaced during unit test.

## Method to implement service class

### Implementing the constructor

An implementation example is given below.

````java
public class ProjectService  {
    private final DaoContext universalDao;

    //※1
    public ProjectService() {
        //※2
        this(DaoFactory.create());
    }

    //※3
    ProjectService(DaoContext universalDao) {
        this.universalDao = universalDao;
    }
// omitted below
}
````
[*]1 In production code, use this constructor to initialize an instance of the Service class.<br>
[*]2 DaoFactory class is a class that exists in the proman-common project.  
[*]3 In the test code, use this constructor to initialize an instance of the Service class.

### Implementation of DB access using DaoContext

An implementation example is given below.

````java
public class ProjectService  {
    private final DaoContext universalDao;
    //Omitted
    public List<Organization> findAllDivision() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DIVISION");
    }
// omitted below
}
````

## Method to use service class

### Production code

Instantiate with a no-argument constructor.

An implementation example is given below.

````java
public class ProjectSearchAction {
    //Omitted
    public HttpResponse search(HttpRequest request, ExecutionContext context) {
        //Omitted
        ProjectService searchService = new ProjectService();
        EntityList<ProjectWithOrganizationSearchResultDto> searchList
            = searchService.findProjectWithOrganizationByCondition(searchCondition);
        //Omitted
    }
    //Omitted
}
````


### Test code

Instantiate by stubbing the DaoContext class using a constructor with argument. 

An implementation example is given below.

````java
public class ProjectServiceTest {

    private ProjectService sut;
    @Test
    public void test() {
        sut = new ProjectService(new DaoStub() {
            @Override
            public <T> T findById(Class<T> entityClass, Object... id) {
                Organization organization = new Organization();
                organization.setOrganizationName("aaa");
                return (T) organization;
            }
        });
        Organization organization = sut.findOrganizationById(1);
        assertThat(organization.getOrganizationName(), is("aaa"));
    }
}
````
