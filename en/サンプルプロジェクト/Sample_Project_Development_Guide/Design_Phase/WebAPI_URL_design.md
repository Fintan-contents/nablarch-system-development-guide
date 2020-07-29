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

```xml
  <get    path="/customer"     to="Customer#list" />
  <get    path="/customer/:id" to="Customer#show" />
  <post   path="/customer"     to="Customer#register" />
  <put    path="/customer/:id" to="Customer#update" />
  <delete path="/customer/:id" to="Customer#delete" />
```
