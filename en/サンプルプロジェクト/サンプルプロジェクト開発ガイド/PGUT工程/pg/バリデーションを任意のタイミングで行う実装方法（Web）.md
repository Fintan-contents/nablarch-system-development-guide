# Implementation Method to Perform Validation at Any Timing（Web）

Normally, by setting @InjectForm in the action method, 
validation of input parameters is automatically performed using Form.

However, if a process has to be performed even at the time of validation error, 
there is no place to describe the process in @InjectForm. 
（As the process does not reach action when there is a validation error）

## Implementation method

`com.nablarch.example.proman.web.common.validation.FormBinder` is used.

`@InjectForm` annotation is not used.

### Example when pre-processing is to be performed regardless of the validation result

```java
@OnError(type = ApplicationException.class, path = "/WEB-INF/view/common/authentication/login.jsp")
public HttpResponse login(HttpRequest req, ExecutionContext ctx) {

    // Preprocessing that should be performed regardless of the success or failure of validation is described here.
    // ....

    // Transfer request parameter value to form.
    // Validation is performed here.
    BindingResult<LoginForm> result = FormBinder.from(req, ctx).to(LoginForm.class);

    // An exception is thrown here if a validation error occurs.
    // As a result, transitions to the transition destination specified by @OnError annotation.
    result.abortIfInvalid();

    // Gets the form.
    LoginForm form = result.getValidForm();
}
```

### If a process is to be performed even when a validation error occurs

```java
@OnError(type = ApplicationException.class, path = "/WEB-INF/view/common/authentication/login.jsp")
public HttpResponse login(HttpRequest req, ExecutionContext ctx) {

    // Transfer the value of the request parameter to the form.
    // Validation is performed here.
    BindingResult<LoginForm> result = FormBinder.from(req, ctx).to(LoginForm.class);

    // If there is a process that you want to perform only when a validation error occurs,
    // determines if the validation was successful.
    if (!result.isValid()) {
        // Describe the necessary process when a validation error occurs.
        // ...

        // Throws ApplicationException exception.
        // As a result, transitions to the transition destination specified by @OnError annotation.
        result.throwApplicationException();
    }

    // Gets the form.
    LoginForm form = result.getValidForm();
}
```
