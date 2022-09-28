# Package Configuration

Packages are organized in the following hierarchy.

| Package hierarchy | Description                     | Example:                         |
| -------------- | ------------------------ | -------------------------- |
| 1～3           | An identifier representing the system     | com.nablarch.example(fixed) |
| 4              | Identifier representing subsystem | proman、climan             |
| 5              | Identifiers representing the module   | web、batch、common、rest         |
| 6              | Identifier representing function         | login、project等           |

Packages that represent class responsibilities (Action, Form, etc.) are not used.  
An example of class placement is shown below.

![Placement example](images/package-structure-example.png)