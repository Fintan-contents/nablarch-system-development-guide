# Asynchronous Operation in Nablarch

In web applications of Nablarch, asynchronous operation can be achieved by combining "resident batches".

For example, assume that a web application is taking too long to process a single request.

In this case, asynchronous operation can be realized by using a resident batch as follows.

- Web application accepts the request, registers data necessary for subsequent processing in DB, and returns the response
- Resident batch detects data registration to DB and processes the data


![sequence](./nablarch-async-pattern.png)
