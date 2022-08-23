# エラー発生時のハンドリング方法（Web）

## 業務エラーのハンドリング

業務処理中に業務エラーを検知した場合は、ApplicationExceptionをスローします。

```java
throw new ApplicationException(
        MessageUtil.createMessage(MessageLevel.ERROR, "errors.search.nothing"));
```

このとき、ApplicationExceptionのコンストラクタにはエラーメッセージの情報を設定します。
エラーメッセージの生成には、MessageUtilを使用してください。
createMessageメソッドの第一引数にはMessageLevel.ERRORを指定し、第二引数にはエラーメッセージのメッセージIDを指定します。

次に、スローされたApplicationExceptionをハンドリングするため、Actionクラスのメソッドに[OnErrorアノテーション](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/handlers/web_interceptor/on_error.html)を設定します。
複数の例外をハンドリングしたい場合は、[OnErrorsアノテーション](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/handlers/web_interceptor/on_errors.html)を設定してください。

```java
@OnError(type = ApplicationException.class, path = "forward:///app/project/errorRegister")
public HttpResponse confirmRegistration(HttpRequest request, ExecutionContext context) {
```

JSPではn:errorsを使用してエラーメッセージをユーザーに表示します。
実装例を示します。

````jsp
<n:errors filter="global"/>
````

filter属性にglobalを記載することで、入力項目と紐づかないエラーのみが出力されます。

### 特定の入力項目に紐づく業務エラー

特定の入力項目に紐づく業務エラーを発生させる場合は、ValidationUtilを使ってMessageを生成します。

```java
throw new ApplicationException(
        ValidationUtil.createMessageForProperty("form.sales", "errors.invalid.sales"));
```

createMessageForPropertyメソッドの第一引数にはエラーを紐づける入力項目のnameを指定し、第二引数にはエラーメッセージのメッセージIDを指定します。

JSPでは、nameの値が一致するn:errorにエラーメッセージが表示されます。

```jsp
<n:error errorCss="message-error" name="form.sales" />
```

## 業務エラー以外のエラーのハンドリング

排他制御エラーやデータベース接続エラーなど業務エラー以外のエラーが発生した場合、アプリ実装では何もしません。
共通エラー画面への遷移は基盤側で制御します。
