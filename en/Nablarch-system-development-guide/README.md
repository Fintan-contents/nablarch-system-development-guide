# Nablarch System Development Guide

## Contents

- [Preface](#Preface)
  - [Purpose of this document](#Purpose-of-this-document)
  - [Who should read this document?](#Who-should-read-this-document?)
  - [Notes on reading](#Notes-on-reading)
- [Overall picture](#Overall-picture)
- [Project plan](#Project-plan)
- [Requirements definition phase](#Requirements-definition-phase)
  - [Requirement definition](#Requirement-definition)
  - [Architecture design](#architecture-design)
  - [Checking the project deliverables](#Checking-the-project-deliverables)
  - [Test plan](#Test-plan)
  - [Creating a development guide for a project](#Creating-a-development-guide-for-a-project)
- [Design phase](#Design-phase)
  - [Standardization](#Standardization)
- [Programming Unit testing phase](#Programming-Unit-testing-phase)
  - [Initial build of Nablarch project](#Initial-build-of-Nablarch-project)
  - [Setting up the team development environment](#Setting-up-the-team-development-environment)
  - [Preparation of setup guide for development environment](#Preparation-of-setup-guide-for-development-environment)
  - [Establishment of development standards](#Establishment-of-development-standards)
- [Integration test phase](#Integration-test-phase)
  - [Preparation for integration test (communication confirmation)](#Preparation-for-integration-test-communication-confirmation)
- [System test phase](#System-test-phase)
- [Maintenance operation](#Maintenance-operation)


## Preface

This document indicates as to what should be done before and during development, and what should be referred to, by engineers engaged in system development using Nablarch. The document describes the method considered ideal for most projects to proceed with activities that are considered necessary for system development using Nablarch.
Given the characteristics of most projects using Nablarach, the development process assumes waterfall development.
Before starting the project, review this document and study how to proceed with the development.

### Purpose of this document

This document aims to achieve the following.

* Ensure that the content provided by Nablarch (development standards, check tools, etc.) is used effectively
* Clear description of the most suitable method combining the contents of [Fintan](https://fintan.jp/) and Nablarch
* Clear description of how to proceed optimally from external design, which is a specialized area of Nablarch, to Programming Unit testing
* Demonstration of the effective way of testing with Nablarch
* Enabling a person who is not familiar with Nablarch (experienced in application development) to carry out development preparation and promotion confidently using the shortest route
* Guide persons having experience using other frameworks (Xenlon, Seaser, Spring) on the correct usage of Nablarch

### Who should read this document?

This document is intended for engineers (especially IT architects and project managers) who want to start system development using Nablarch.

### Notes on reading

This document contains many links. If you go on clicking the links while reading the document at the beginning,
it will become challenging to get the overall picture as there are many linked documents. At first, when reading the document, just understand that ‘there are such links’ without clicking each of them and read the document up to the end.


## Overall picture

The deliverables and tasks required for project development are diverse.
The following diagram shows the development flow in a sample project assuming system development of medium-scale. 
Use this diagram as a reference when studying the development flow of the project (Initially, you need not understand all the elements in the diagram in the first go. It is enough to grasp that there are deliverables and tasks in every process.)

- [Flow of development using Nablarch](../Sample_Project/Design_Document/Flow_of_development_using_Nablarch.xlsx?raw=true)


## Project plan

This is a sheet for assigning roles so that the tasks necessary for waterfall development are not left out. The sheet aims at eliminating differences in the understanding of the developers, early detection of risks, and preventing problems from getting worse.

- [Role sharing sheet for waterfall development](https://fintan.jp/?p=1326)(Only Japanese Edition)



## Requirements definition phase


### Requirement definition

These guidelines summarize the method of defining business/system requirements, deliverables, utilization techniques, and know-how at a systematic and practical level.
- [Requirements definition framework](https://fintan.jp/?p=233)(Only Japanese Edition)


This document gives knowledge and techniques for defining practical requirements.
- [Requirements definition basic training text](https://fintan.jp/?p=1389)(Only Japanese Edition)



#### Non-functional requirements

If non-functional requirements such as performance requirements, security requirements, etc. are not fixed, then even if you carry out architecture design, the design would be considered incomplete.

For defining non-functional requirements, refer to the Non-functional Requirement Grade provided by IPA.

- [Non-functional requirement grade](https://www.ipa.go.jp/english/sec/reports/20101222.html)


### Architecture design

Carry out the architecture design based on the following sample.

- [Sample document of application architecture design](https://fintan.jp/?p=1323)(Only Japanese Edition)

However, since non-functional requirements of the project have to be incorporated into architecture design, the architecture design should not be completed by modifying the sample. Completing the architecture design document by copying or modifying the sample, leads to mistakes such as the omission of the necessary non-functional requirements and inclusion of system design that is not required.

Instead of copying the sample, extract the parts that can be reused and referred from the sample, and complete the architecture design document of the project with those parts. Prepare headings for the architecture design based on the non-functional requirements of the project, refer to the parts of the sample that can be used as a reference, wherever it is required to review project-specific requirements, mention the results of the review, and complete the system design document of the project.

Attention is required especially for performance and security requirements since they are not covered in the above sample.
Either add to the description in the architecture design sample or create a separate document such as “Architecture design document (security design)”.

For security design, refer to IPA's "How to Create a Secure Website". Design so that all items in the "セキュリティ実装 チェックリスト(Security Implementation Checklist)" will be "根本的解決(fundamental solutions)".  
To assist in the security design, a "Nablarch function security matrix" is provided to determine if the items in the checklist can be supported by the features provided in Nablarch. Please set up the project so that the features provided by Nablarch will be enabled, and consider how to deal with the items where the features are not provided.  
The "OWASP Top 10" is also helpful, although there is some overlap with the IPA's "安全なウェブサイトの作り方(How to Create a Secure Website)".

- [How to make a secure website](https://www.ipa.go.jp/security/english/third.html#websecurity)
- [Nablarch function security matrix](../Sample_Project/Design_Document/Nablarch_function_security_matrix.xlsx?raw=true)
- [OWASP Top10](https://github.com/OWASP/Top10)

#### Nablarch pattern collection

Common patterns used in Nablarch, as well as anti-patterns that you should not use (but tend to use), are introduced here.
If you design and program using the wrong method, it may lead to significant rework, poor performance, or production failure in the worst case. 
We recommend that you read this section before starting the design.

- [Nablarch pattern collection](./docs/nablarch-patterns/README.md)


### Checking the project deliverables

If you have not used Nablarch in a project before, 
refer to the sample project to check the actual project deliverables. 
In particular, it will be efficient to refer to the following points.

- Development guide prepared for the project
- Unit test specifications customized for the project
- Correspondence between the design document and source code

Checking the above makes it easier to grasp the image of the upcoming work.

- [Sample project](../Sample_Project)


#### Notes on sample projects

Don't divert the deliverables from the sample project without any consideration.

The deliverables of the sample project are based on the requirements and characteristics of the sample project 
that we have created by customizing the design standards of Nablarch.

Since no two projects have the same requirements and characteristics,
if you copy and reuse the deliverables without thinking, you will always find parts that do not match with your project.
In such cases, the following problems occur:
- Inclusion of unnecessary rules in the project reduces productivity
- Quality cannot be ensured because of omission of necessary test viewpoints from the project
- Unclear descriptions, for which no one can explain the reason or background.

Make sure to create new standards and guides based on your own project requirements and characteristics. 
When doing so, use the sample project for reference, and not as a source for copying.


### Test plan


The contents describe the topics that should be examined during the overall test plan of an application development project.
- [Overall test planning guide](https://fintan.jp/?p=251)(Only Japanese Edition)


This catalog organizes the test types and viewpoints that can be used in application development.
- [Test type and viewpoint catalog](https://fintan.jp/?p=45)(Only Japanese Edition)

#### Consideration of testing policy

In general, there is a trade-off between quality and cost in testing.
The testing policy needs to be tailored to the characteristics of the project.

The test method that actively uses the [Nablarch Testing Framework](https://nablarch.github.io/docs/LATEST/doc/development_tools/testing_framework/index.html) has a problem that the test data creation and maintenance are expensive (hereinafter referred to as "Heavy type").
The [Sample Project](../Sample_Project) uses a lightweight test method that reduces the number of automated test maintenance man-hours without sacrificing quality as much as possible (hereinafter referred to as the "Lightweight type").

It is necessary to consider whether to adopt “Heavy type” or “Lightweight type” in the project depending on the system scale and the amount of change.

##### Heavy type

It creates test classes and test data and executes automated tests.
Can check not only the database and files, but also the log output, request scope, and other internal system conditions in detail.
However, due to the large number of deliverables and check points to be created, the amount of revisions that need to be made when changes occur is higher.

**[Conditions suitable for Heavy type]**
- large scale
- Fewer changes.
- Fine-grained regression tests are needed.

The ability to run most of the paths in automated tests makes it ideal for situations where highly accurate regression testing is required.
It is especially useful for regression testing in large projects when upgrading frameworks, libraries, middleware, etc.
For more information on Heavy type testing, refer to the following page.

- [Heavy type test Standards](https://github.com/nablarch-development-standards/nablarch-development-standards/blob/master/en/020_Application_Development_Standards/020_Test_Standards/Unit_Test_Standards.xlsx?raw=true)

##### Lightweight type

For points to be checked by manual testing, save labor by omitting automated testing as much as possible.
In general, when automated testing is reduced, the cost of regression testing rises, but provide a mechanism to maintain regression testing at low cost.
The regression test can be run based on manual browser operation records.

**[Conditions suitable for Lightweight type]**
- small to medium scale
- Lots of changes

For more information on Lightweight type testing, refer to the following page.

- [Lightweight Type test Standards](../Sample_Project/Design_Document/A1_Project_Management_System/020_Architecture_Design/020_Development_Standards/020_Test_Standards/Unit_Test_Standard.xlsx?raw=true)
- [Unit Test Concept (Web) using Lightweight Type](../Sample_Project\Sample_Project_Development_Guide\PGUT_Phase\ut\Unit_test_concept_(Web).md)


### Creating a development guide for a project

Create a guide summarizing the information needed by developers to proceed with the project.

For an example of how to create a guide, refer to the following in the sample project:
- [Sample project development guide](../Sample_Project/Sample_Project_Development_Guide)


## Design phase

### Standardization

Proceed with the standardization work such that it is in time for the start of each phase.
Customize your project based on the following standards.

- [Nablarch application design standards](https://fintan.jp/?p=1145)

In particular, refer to the Application Development Standards (*1).

These contain important design standard documents such as DB design and UI standards.

In particular, UI standard needs to be deployed before starting the full-scale design phase.
If you proceed with the design without any standard, the UI, such as screen layouts, will vary with each designer and each counter customer.
(Example: Position of buttons, whether to display confirmation and completion screens, unnecessarily rich UI functions).
To later unify these features, customer approval may be required and the features may need to be modified in parallel.
It may not be possible to withdraw the UI after it has been presented. In this case, there is a risk that the UI needs to be created individually in Programming Unit testing phase and the required man-hours will exceed the estimate.
Reference：[UI standard customization](docs/UI_standard_customization.md)

For an example of how to customize, refer to the following in the sample project:
- [Design standard](../Sample_Project/Design_Document/A1_Project_Management_System/020_Architecture_Design/020_Development_Standards/010_Design_Standards)
- [Test standard](../Sample_Project/Design_Document/A1_Project_Management_System/020_Architecture_Design/020_Development_Standards/020_Test_Standards)

For the package configuration of the application, refer to ["Package Configuration Review"](docs/Package_configuration_review.md).


*1: [Application development standards](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/020_アプリケーション開発標準)(Only Japanese Edition)



#### Unit test standards

The types of tests to be performed depend significantly on the characteristics of your project.
Refer to the following items when preparing the test standards.

- [Examination of test items](docs/Examination_of_test_items.md)

The Unit Test Standards under [Nablarch Application Design Standard](https://fintan.jp/?p=1145) adopt a Heavy type (\*1) testing strategy for large projects.
On the other hand, the sample project adopts a Lightweight type (\*1) testing strategy.

*1: see [Consideration of testing policy](#consideration-of-testing-policy)

## Programming Unit testing phase


### Initial build of Nablarch project 

Perform the initial construction of the project using Nablarch.
Please refer here.

- [Initial build of Nablarch project](./docs/Initial_build_of_Nablarch_project.md)

### Setting up the team development environment

Set up an environment for performing development in teams, such as Git repositories and chats.

- [Setting up the team development environment](./docs/Setting_up_the_team_development_environment.md)

### Preparation of setup guide for development environment

Prepare a setup guide for the development environment to be used by application developers.
Please refer here.

- [Preparation of setup guide for development environment](./docs/Preparation_of_setup_guide_for_development_environment.md)


### Establishment of development standards

Establish development standards for the Programming Unit testing phase.
Refer to the following documents.

These are coding conventions that programmers must adhere to, to improve quality, productivity, and maintainability.
- [Nablarch coding conventions](https://fintan.jp/?p=1145)


This is a checklist for developers to confirm the completion conditions of PG/UT work. 
- [PG/UT work completion condition checklist](https://fintan.jp/?p=1367)(Only Japanese Edition)


This is a checklist for developers to self-check simple coding mistakes such as violations of coding conventions, etc.
- [Deliverables self-check list for programmers](https://fintan.jp/?p=1369)(Only Japanese Edition)



## Integration test phase

### Preparation for integration test (communication confirmation)

Before starting the integration test (Programming Unit testing phase), change the settings for the integration test environment and confirm the communication.

Many differences occur when migrating from the local development environment to the integration test environment in the Programming Unit testing phase. 
(Example: Difference in the file path, whether mock is used). 
For this reason, it is necessary by allotting extra time for carrying out the settings for the integration test environment.
If communication is not confirmed adequately before the integration test,
errors frequently occur in the base part immediately after starting the test, which may delay the progress of the entire test.

First, identify the cases from the architecture design document.
For example, if there is a description of "authentication", then there is a case confirming that the authentication function is working.
Next, for each environment, check if the environment differs with respect to the previous phase.

Examples are shown below.

| Perspective           | UT->IT | IT->ST | ST->Production | Description                                           |
|----------------|--------|--------|----------|------------------------------------------------|
| Authentication           | Yes     | No     | Yes       | Common test data for the entire system is used from the IT phase |
| Report output       | No     | Yes     | No       | For licensing reasons, the form is output from ST       |
| File I/O | Yes     | No     | No       | Path specification method differs depending on the differences in OS             |
| File transfer   | Yes     | Yes     | Yes       | IP address must be changed for each environment                 |
| Batch         | Yes     | No     | Yes       | Use shell script from the IT phase               |
| 　 　：        |        |        |          |                                                |

For parts with differences, change the settings according to the environment.

Confirm the communication mainly at places where there are differences.
In particular, functions that have a significant impact on overall testing (testing cannot be done if these functions stop working) are tested on a priority basis.


## System test phase

(This will be added after receiving feedback from the project)

## Maintenance operation

(This will be added after receiving feedback from the project)

