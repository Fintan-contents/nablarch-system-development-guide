# WebAPIのURL設計

本プロジェクトでは単純なCRUDのみの提供であり、公開範囲も社内システム内部のみであるため、
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

```xml
  <get    path="/customer"     to="Customer#list" />
  <get    path="/customer/:id" to="Customer#show" />
  <post   path="/customer" to="Customer#register" />
  <put    path="/customer/:id" to="Customer#update" />
  <delete path="/customer/:id" to="Customer#delete" />
```
