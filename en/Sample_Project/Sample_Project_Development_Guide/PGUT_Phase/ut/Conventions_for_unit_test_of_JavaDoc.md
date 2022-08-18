# Conventions for Unit Test of Javadoc

## Javadoc for class
Write "Test for < Name of class to test >.".

### Example

```
/**
 * Test for ProjectService.
 */
class ProjectServiceTest {
    // ...
}
```

## Javadoc for method
Include comments that explain what the test checks.

### Example

```
class ProjectServiceTest {

    private ProjectService sut;
    
    /**
     * Test whether one search process can be called successfully.
     */
    @Test
    void test() {
        sut = new ProjectService(new DaoStub() {
            @Override
            public <T> T findById(Class<T> entityClass, Object... id) {
                Organization organization = new Organization();
                organization.setOrganizationName("aaa");
                return (T) organization;
             }
        });

        //Execution target test
        Organization organization = sut.findOrganizationById(1);
        assertThat(organization.getOrganizationName(), is("aaa"));
    }
}
```
