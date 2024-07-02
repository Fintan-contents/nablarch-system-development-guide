# ユニットテストのJavadocに関する規約

## クラスのJavadoc

「< テスト対象のクラス名 >のテスト。」と記載してください。

### 例

```
/**
 * ProjectServiceのテスト。
 */
class ProjectServiceTest {
    // ...
}
```

## メソッドのJavadoc
何を確認するテストか分かるコメントを記載してください。

### 例

```
class ProjectServiceTest {

    private ProjectService sut;
    
    /**
     * 1件検索処理が正常に呼び出し可能かテストをする。
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

        //テスト対象実行
        Organization organization = sut.findOrganizationById(1);
        assertThat(organization.getOrganizationName(), is("aaa"));
    }
}
```
