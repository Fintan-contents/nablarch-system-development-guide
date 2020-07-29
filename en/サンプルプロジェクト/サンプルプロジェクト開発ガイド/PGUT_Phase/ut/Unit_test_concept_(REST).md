# Unit Test Concept (REST)

The unit test of REST in this project is essentially the same as the [Web](./単体テストの考え方（Web）.md).
JSP is excluded as it is not registered in REST.

The method of the subfunction unit test is described as it is different.

## Subfunction unit test

[curl（cURL）](https://curl.haxx.se/) instead of a browser is used for the subfunction unit test of REST.
This is followed due to the following reasons.

- Reproducibility
  - Saving the text makes it easy to reproduce the request
- Ease of implementation
  - Information is readily available and many developers are already proficient
  - Available as standard on Windows and can be used immediately
  - The number of APIs in this project is small, and large-scale test tools are not required
- Continuation
  - Widely used, unlikely to not be available in the future


## Reference: Test automation concept

In the case of a normal web application, there are points that humans need to check visually (whether the layout is off).

For REST, automation of testing is relatively easy as there are no verification points that require such visual observation.

In this project, since the number of WebAPIs to be provided is small and the processing is not complicated, E2E tests were not introduced,
and the manual test was selected for the subfunction unit test.


