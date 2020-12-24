# WebAPI URL Design

This project provides only simple CRUD, and since the scope of publication is only within the in-house system, 
rules for URL design are minimized.


| Processing             | HTTP method | URL                  |
|------------------|--------------|----------------------|
| Search             | GET          | `/<Resource name>`      |
| Acquisition by ID specification | GET          | `/<Resource name>/<ID>` |
| Registration             | POST         | `/<Resource name>`      |
| Update (*)          | PUT          | `/<Resource name>/<ID>` |
| Delete (*)          | DELETE       | `/<Resource name>/<ID>` |



*: Not used in this project.


## Routing example

Specific examples of routing settings are given below.
In this project, use [routing by Path annotation in JAX-RS](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/adaptors/router_adaptor.html#mapping-in-jax-rs-path-annotation).


```java
@Path("/client")
public class ClientAction {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> list(HttpRequest request, ExecutionContext context) {
      // Omitted
    }

    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client show(HttpRequest request, ExecutionContext context) {
      // Omitted
    }

    @POST
    @Valid
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse register(ClientForm form) {
      // Omitted
    }

    @PUT
    @Path("/{clientId}")
    @Valid
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse update(ClientForm form) {
      // Omitted
    }

    @DELETE
    @Path("/{clientId}")
    public HttpResponse delete(HttpRequest request, ExecutionContext context) {
      // Omitted
    }
}
```