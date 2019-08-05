# ユニットテストのJavaDocに関する規約

## メソッドのJavaDoc
何を確認するテストか分かるコメントを記載してください。

例：
```
public class ProjectServiceTest {

    private ProjectService sut;
    /**
     * 1件検索処理が正常に呼び出し可能かテストをする。
     * <p>
     * 例外が発生しなければ正常に呼び出されたと判定する。
     * </p>
     */
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

        //テスト対象実行
        Organization organization = sut.findOrganizationById(1);
        assertThat(organization.getOrganizationName(), is("aaa"));
    }
}
```