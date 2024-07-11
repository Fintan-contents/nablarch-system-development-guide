# WebAPIのURL設計

本プロジェクトでは単純なCRUDのみの提供であり、公開範囲も社内のみであるため、
URL設計のルールは最小限とします。


| 処理             | HTTPメソッド | URL                  |
|------------------|--------------|----------------------|
| 検索             | GET          | `/<リソース名>`      |
| ID指定による取得 | GET          | `/<リソース名>/<ID>` |
| 登録             | POST         | `/<リソース名>`      |
| 更新(*)          | PUT          | `/<リソース名>/<ID>` |
| 削除(*)          | DELETE       | `/<リソース名>/<ID>` |



*: 本プロジェクトでは使用しない。


## ルーティング例

ルーティング設定の具体例を以下に示します。
本プロジェクトでは[JAX-RSのPathアノテーションによるルーティング](https://nablarch.github.io/docs/LATEST/doc/application_framework/adaptors/router_adaptor.html#jax-rspath)を使用します。

```java
@Path("/client")
public class ClientAction {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> list(HttpRequest request, ExecutionContext context) {
      // 省略
    }

    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client show(HttpRequest request, ExecutionContext context) {
      // 省略
    }

    @POST
    @Valid
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse register(ClientForm form) {
      // 省略
    }

    @PUT
    @Path("/{clientId}")
    @Valid
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse update(ClientForm form) {
      // 省略
    }

    @DELETE
    @Path("/{clientId}")
    public HttpResponse delete(HttpRequest request, ExecutionContext context) {
      // 省略
    }
}
```