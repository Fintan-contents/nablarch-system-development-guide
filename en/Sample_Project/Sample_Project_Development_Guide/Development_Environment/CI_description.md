# Description of CI

## Software used

- Jenkins ver. 2.60.3
- Apache JMeter ver. 5.2.1
   - It is needed for automated subfunction unit tests.  
     Install it on a server working Jenkins beforehand.

## Location of pipeline definition

- See Jenkinsfile (develop branch) under proman-project.

## Usage syntax for pipeline definitions

-  Declarative Pipeline  
   Supplementary notes: [Click here](https://jenkins.io/doc/book/pipeline/) for the types of Pipelines in Jenkins.

## Overview of Pipeline processing

Jenkins polls for changes every minute and builds when changes are made.

- Performs unit testing and static analysis on all branches
- Deploys to a demo environment for the development branch
- Test subfunction unit against the demo environment.

Performs the following in order:

- 1. Compilation and unit testing
- 2. Static analysis

After that, only the develop branch will be implemented.

- 3. Builds jar file for demo server deployment
- 4. Deploy to demo server
- 5. automatic execution of subfunction unit tests

Build results are reported to the chat.