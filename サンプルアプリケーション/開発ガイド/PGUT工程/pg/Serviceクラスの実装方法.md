# Serviceクラスの実装方法

Serviceクラスは業務ロジックを実装するクラスです。  
ここではServiceクラスについて、全処理方式共通のトピックを記載します。

## Serviceクラスの実装方針

ユニットテスト時にDBアクセス部分をスタブに差し替え可能な実装とします。  
その手段として、DaoContextクラスをServiceクラスのコンストラクタで初期化し、それを用いてDBアクセスを行います。

DaoContextクラスは、NablarchがUniversalDaoクラスの内部で使用しているクラスです。 
UniversalDaoクラスはインスタンス化せずに使うクラスであり、ユニットテスト時の差し替えが不可能であるため、この手法をとります。

## Serviceクラスの実装方法

### コンストラクタの実装

実装例を示します。

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
// 以下略
}
````
※1 プロダクションコードでは、このコンストラクタを使用してServiceクラスのインスタンスを初期化します。  
※2 DaoFactoryクラスはproman-commonプロジェクトに存在するクラスです。  
※3 テストコードでは、このコンストラクタを使用してServiceクラスのインスタンスを初期化します。

### DaoContextを使用したDBアクセスの実装

実装例を示します。

````java
public class ProjectService  {
    private final DaoContext universalDao;
    //中略
    public List<Organization> findAllDivision() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DIVISION");
    }
// 以下略
}
````

## Serviceクラスの使用方法

### プロダクションコード

引数無しコンストラクタでインスタンス化します。

実装例を示します。

````java
public class ProjectSearchAction {
    //中略
    public HttpResponse search(HttpRequest request, ExecutionContext context) {
        //中略
        ProjectService searchService = new ProjectService();
        EntityList<ProjectWithOrganizationSearchResultDto> searchList
            = searchService.findProjectWithOrganizationByCondition(searchCondition);
        //中略
    }
    //中略
}
````


### テストコード

引数有りコンストラクタでDaoContextクラスをスタブに差し替えてインスタンス化します。

実装例を示します。

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
