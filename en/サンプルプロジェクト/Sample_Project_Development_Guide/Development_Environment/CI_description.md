# Description of CI

## Software used

- Jenkins ver. 2.60.3

## Location of pipeline definition

- See Jenkinsfile (develop branch) under proman-project.

## Usage syntax for pipeline definitions

-  Declarative Pipeline  
   Supplementary notes: [Click here](https://jenkins.io/doc/book/pipeline/) for the types of Pipelines in Jenkins.

## Overview of Pipeline processing

Jenkins polls for changes every minute and builds when changes are made.

- Performs unit testing and static analysis on all branches
- Deploys to a demo environment for the development branch

Performs the following in order:

- １. Compilation and unit testing
- ２. Static analysis

After that, only the develop branch will be implemented.

- ３. Builds jar file for demo server deployment
- ４. Deploy to demo server


Build results are reported to the chat.