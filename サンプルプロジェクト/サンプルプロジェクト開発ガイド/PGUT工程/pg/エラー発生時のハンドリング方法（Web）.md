# エラー発生時のハンドリング方法（Web）

## 更新時の排他制御エラー

### 排他制御エラー発生時に起きること

DaoContext#updateがjavax.persistence.OptimisticLockExceptionをスローします。

### ハンドリング内容

アプリ実装では何もしません。  
共通エラー画面への遷移は基盤側で制御します。

## 1件取得で存在するはずのデータが取得できなかった場合

### 1件取得で存在するはずのデータが取得できなかった場合に起きること

DaoContext#findByIdがnablarch.common.dao.NoDataExceptionをスローします。

### ハンドリング内容

アプリ実装では何もしません。  
共通エラー画面への遷移は基盤側で制御します。

## 検索で検索結果が0件の場合

### 検索結果が0件の場合に起きること

DaoContext#findAllBySqlFileが空のリストを返します。

### ハンドリング内容

Serviceクラスは何もせず、Actionクラスに対して空のリストを返します。 
ActionクラスのメソッドでApplicationExceptionをスローします。 
実装例を示します。MessageLevelとメッセージIDは実装例に合わせてください。

````java
throw new ApplicationException(
        MessageUtil.createMessage(MessageLevel.ERROR, "errors.search.nothing"));
````

JSPではn:errorsを使用してエラーメッセージをユーザーに表示します。 
実装例を示します。

````java
<n:errors filter="global"/>
````

filter属性にglobalを記載することで、入力項目と紐づかないエラーのみが出力されます。
