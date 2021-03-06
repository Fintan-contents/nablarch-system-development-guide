# バリデーションを任意のタイミングで行う実装方法（Web）

通常、アクションのメソッドに@InjectFormを設定しておくことで、
Formを使って入力パラメータのバリデーションが自動で行われます。

しかし、バリデーションエラー時にも何か処理をする必要がある場合、
@InjectFormでは処理を記述する箇所がありません。
（バリデーションエラー時は、アクションまで処理が来ないため）

## 実装方法

`com.nablarch.example.proman.web.common.validation.FormBinder`を使用します。

`@InjectForm`アノテーションは使用しません。

### バリデーションの成功失敗に関わらず行っておきたい前処理が存在する場合の例

```java
@OnError(type = ApplicationException.class, path = "/WEB-INF/view/common/authentication/login.jsp")
public HttpResponse login(HttpRequest req, ExecutionContext ctx) {

    // バリデーションの成功失敗に関わらず行っておきたい前処理をここに記載します。
    // ....

    // Formにリクエストパラメータの値を移送します。
    // ここでバリデーションが実行されます。
    BindingResult<LoginForm> result = FormBinder.from(req, ctx).to(LoginForm.class);

    // バリデーションエラーが発生した場合、ここで例外が送出されます。
    // その結果、@OnErrorアノテーションで指定された遷移先に遷移します。
    result.abortIfInvalid();

    // Formを取得します。
    LoginForm form = result.getValidForm();
}
```

### バリデーションエラー時にも何か処理を行う必要がある場合

```java
@OnError(type = ApplicationException.class, path = "/WEB-INF/view/common/authentication/login.jsp")
public HttpResponse login(HttpRequest req, ExecutionContext ctx) {

    // Formにリクエストパラメータの値を移送します。
    // ここでバリデーションが実行されます。
    BindingResult<LoginForm> result = FormBinder.from(req, ctx).to(LoginForm.class);

    // バリデーションエラーが発生したときにだけ行いたい処理がある場合、
    // バリデーションに成功したか判定を行います。
    if (!result.isValid()) {
        // バリデーションエラー時に必要な処理をここに記載します。
        // ...

        // 例外ApplicationExceptionを送出します。
        // その結果、@OnErrorアノテーションで指定された遷移先に遷移します。
        result.throwApplicationException();
    }

    // Formを取得します。
    LoginForm form = result.getValidForm();
}
```
