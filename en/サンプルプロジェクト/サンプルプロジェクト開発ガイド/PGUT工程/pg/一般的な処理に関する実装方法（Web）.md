# Implementation Method for General Processing（Web）

## Transferring data between screens

### Between subfunctions

- Loose coupling between subfunctions. Do not pass via SessionUtil.
  For example, the transition from the project details screen to the search/delete screen will GET/POST the project ID and search the DB again for project information.
- Parameters are passed using Form.   
  Even if there is only one parameter, create a Form class and do not retrieve the value directly from HttpRequest#getParamMap ().
- Be sure to get the value via validation even if the value is not directly input by the user.   
  This is because it is easy to modify the value sent from the browser.

### Within subfunctions

- Use SessionUtil to share values between screens.
- Do not store Form in Session but store DTO or Entity.  
  Form is linked to a specific screen and is not suitable for sharing between screens.

## Session

### Selection criteria for session store

Use the default (DB store) unless specified by the architect.

### Removal timing

Delete at the following timing.
- During the initial display at the start of subfunction
- At the timing when objects set in the session are no longer required in subsequent processing such as confirmation

### If the session object is not found

As a result of using the browser's back button, the object that is assumed to exist may not exist in the session.  For example, when you click the back button on the confirmation screen.  
In this case, SessionKeyNotFoundException will be thrown when `SessionUtil#get(ExecutionContext, java.lang.String)`  is called, but do nothing in implementation of the application. Catches the exception on the base side and returns a 400 response to the user.

## Screen switching

### Data to be displayed when transitioning by paging
- Each time the paging button is pressed, it is searched from the DB.  
  That is, do **not** store search results obtained from the DB in the session.

### Transition with the back button

#### Form class data used for screen display

Restore from DTO or Entity stored in session.

#### Master system data used for screen display

Search again from the DB after each return.   
In other words, do **not** store the master data obtained from the DB in the session.


### Transition to screen for confirmation of data

#### Transition patterns covered by this section

The following transition patterns are targeted.
```
Input confirmation screen
↓
(Confirmation process (no screen))
↓
Completion screen
```

In this case, the specification should include:
- Use redirection to display completion screen
- Implement double submission prevention for confirm button

##### Implementation method to display completion screen

Implementation is done with Action class.  
Implementation method for the `Confirmation process→(redirect)→completion screen display` part is shown below.

````java

    @OnDoubleSubmission
    public HttpResponse create(HttpRequest request, ExecutionContext context) {
        //Omitted registration process

        //Redirect by specifying status code 303 (302 if no status code is specified)
        return new HttpResponse(303, "redirect://completionOfCreation");
    }

    public HttpResponse completionOfCreation(HttpRequest request, ExecutionContext context) {
        //Display completion screen
        return new HttpResponse("/WEB-INF/view/project/completionOfCreation.jsp");
    }
````


##### Implementation method to prevent dupulicate form submission

Implementation is required to be done for the methods of JSP and Action class.

###### Implementation method on JSP side

Implement using allowDoubleSubmission attribute of custom tag of button and useToken attribute of form tag.   
See below for the implementation method.   
https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/tag.html#tag-double-submission

###### Implementation method on action class side

Add OnDoubleSubmission annotation.
````java
@OnDoubleSubmission
public HttpResponse register(HttpRequest req, ExecutionContext ctx) {
    // omitted.
}
````

The path attribute of @ OnDoubleSubmission is not required to be attached, since the base will transition to a specific page when a double submission occurs.
