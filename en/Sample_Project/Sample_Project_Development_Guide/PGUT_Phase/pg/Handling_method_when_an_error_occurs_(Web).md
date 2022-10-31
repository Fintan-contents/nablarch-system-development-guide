# Handling Method When an Error Occurs（Web）

## Handling of business errors

If a business error is detected during processing business logic, throw an ApplicationException.

```java
throw new ApplicationException(
        MessageUtil.createMessage(MessageLevel.ERROR, "errors.search.nothing"));
```

At this time, set the error message information to the constructor of ApplicationException.
Use MessageUtil to generate error messages.
Set MessageLevel.ERROR at the first argument of the createMessage method and set the message ID of the error message at the second argument.

Next, set the [OnError annotation](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/handlers/web_interceptor/on_error.html) on the Action class method to handle the thrown ApplicationException.
If you want use multiple OnError annotations, use the [OnErrors annotation](https://nablarch.github.io/docs/LATEST/doc/en/application_framework/application_framework/handlers/web_interceptor/on_errors.html).

```java
@OnError(type = ApplicationException.class, path = "forward:///app/project/errorRegister")
public HttpResponse confirmRegistration(HttpRequest request, ExecutionContext context) {
```

In JSPs, n:errors is used to display error messages to the user. 
An implementation example is given below.

````jsp
<n:errors filter="global"/>
````

By writing global in the filter attribute, only errors that are not associated with the input item will be output.

### Business errors associated with specific input items

To generate a business error associated with specific input items, use ValidationUtil to generate a Message.

```java
throw new ApplicationException(
        ValidationUtil.createMessageForProperty("form.sales", "errors.invalid.sales"));
```

Set the name of the input item associated with the error  at the first argument of the createMessageForProperty method and set the message ID of the error message at the second argument.

In JSPs, an error message is displayed for n:error with a matching name value.

```jsp
<n:error errorCss="message-error" name="form.sales" />
```

## Handling of non-business errors

When errors other than business errors such as exclusion control errors and database connection errors occur, the application implementation does nothing.
Transition to the common error screen is controlled on the runtime platform side.
