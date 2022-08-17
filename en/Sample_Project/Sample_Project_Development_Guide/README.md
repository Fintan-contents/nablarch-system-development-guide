# Sample Project Development Guide

- [Flow of development](#Flow-of-development)
- [Requirements definition phase](#Requirements-definition-phase)
  - [Requirements definer](#Requirements-definer)
    - [Screen mockup creation](#Screen-mockup-creation)
- [Design phase](#Design-phase)
  - [Designer](#Designer)
    - [Design (common)](#Design-common)
    - [Screen design](#Screen-design)
    - [Web service design](#Web-service-design)
  - [Data modeler](#Data-modeler)
    - [Data definition](#Data-definition)
    - [DB design](#DB-design)
- [Programming and Unit testing phase](#Programming-and-Unit-testing-phase)
  - [Application engineer](#Application-engineer)
    - [Programming and Unit testing (common)](#Programming-and-Unit-testing-common)
    - [Programming and Unit testing (Web)](#Programming-and-Unit-testing-Web)
    - [Programming and Unit testing (batch)](#Programming-and-Unit-testing-batch)
    - [Programming and Unit testing (REST)](#Programming-and-Unit-testing-REST)
- [Team development environment](#Team-development-environment)

Describes how to proceed with the development of the sample project.
If you are engaged in developing a sample project, please read this development guide carefully and proceed with development according to this development guide.

This development guide is maintained by the architect.
If you have any questions or doubts about this development guide, consult the architect.

This development guide covers from the Requirements definition phase to the Programming and Unit testing phase.

## Flow of development

See below for the development flow.

- [Flow of development using Nablarch](../Design_Document/Flow_of_development_using_Nablarch.xlsx?raw=true)

Work contents and deliverables are shown by the combination of phase and role.
Check your work and deliverables for your role.

Guide is described for each role and task shown in the development flow.

## Requirements definition phase

### Requirements definer

#### Screen mockup creation

- [Placement location of screen mockup](../Design_Document/A1_Project_Management_System/010_Requirements_Definition/020_Screen_Mockup)
- [Screen mockup creation guide](Requirements_Definition_Phase/Screen_mockup_creation_guide.md)
- [UI standard](../Design_Document/A1_Project_Management_System/020_Architecture_Design/020_Development_Standards/010_Design_Standards)
  - Please refer to UI standard when creating screen mockups.
- [Design document format](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/030_%E8%A8%AD%E8%A8%88%E3%83%89%E3%82%AD%E3%83%A5%E3%83%A1%E3%83%B3%E3%83%88/010_%E3%83%95%E3%82%A9%E3%83%BC%E3%83%9E%E3%83%83%E3%83%88)(Only Japanese Edition)
  - Screen list and screen transition diagram are created at the time of screen mockup creation.

## Design phase

### Designer

#### Design (common)

- Placement location of design document
  - [Placement location of design document for Project Management System](../Design_Document/A1_Project_Management_System/030_Application_Design)
  - [Placement location of design document for Client Management System](../Design_Document/B1_Client_Management_System/030_Application_Design)
- [Design document format](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/030_%E8%A8%AD%E8%A8%88%E3%83%89%E3%82%AD%E3%83%A5%E3%83%A1%E3%83%B3%E3%83%88/010_%E3%83%95%E3%82%A9%E3%83%BC%E3%83%9E%E3%83%83%E3%83%88)(Only Japanese Edition)
  - Use the format provided by the Nablarch development standard.
- [Test preparation in the design phase](Design_Phase/Test_preparation_in_the_design_phase.md)（Unit test case design）
  - Create test data and test cases during the design phase. For more information, click the link above.
- [Create SQL file](Design_Phase/Create_SQL_file.md)（SQL validation）
  - Create SQL in the design phase. For more information, click the link above.

#### Screen design

- [Method to take a screenshot](Design_Phase/Method_to_take_a_screenshot.md)

#### Web service design

- [WebAPI URL design](Design_Phase/WebAPI_URL_design.md)

### Data modeler

#### Data definition

- [Method to proceed with domain definition](Design_Phase/Method_to_proceed_with_domain_definition.md)
- [Method to proceed with code design](Design_Phase/Method_to_proceed_with_code_design.md)

#### DB design

- [DB design standards](../Design_Document/A1_Project_Management_System/020_Architecture_Design/020_Development_Standards/010_Design_Standards)
  - Refer to both logical and physical designs.

## Programming and Unit testing phase

### Application engineer

#### App implementation (common)

- Progress method
  - [Programming and Unit testing work completion condition checklist](PGUT_Phase/checklist/PGUT_work_completion_condition_checklist.xlsx?raw=true)
    - Please work according to the introduction example sheet of condition checklist for Programming and Unit testing work completion.
  - [Deliverable self-checklist for programmers](PGUT_Phase/checklist/Deliverable_self-checklist_for_programmers.xlsx?raw=true)
    - Use the files from the above links for the self-checks that in the case study sheet.
- Rules
  - [Version management rules](PGUT_Phase/Version_management_rules.md)
  - [Package configuration](PGUT_Phase/pg/Package_configuration.md)
  - [Naming convention for coding](PGUT_Phase/pg/Naming_convention_for_coding.md)
  - [Response method when a static analysis check violation occurs](PGUT_Phase/pg/Response_method_when_a_static_analysis_check_violation_occurs.md)
- Working method
  - [Development environment construction guide](PGUT_Phase/Development_environment_construction_guide.md)
  - [Method to check coding conventions](PGUT_Phase/pg/Method_to_check_coding_conventions.md)
- Implementation method
  - [Method to implement service class](PGUT_Phase/pg/Method_to_implement_service_class.md)

#### App implementation (Web)

- [Application configuration (Web)](Design_Phase/Application_Configuration_(Web).md)
- [Implementation method for general processing (Web)](PGUT_Phase/pg/Implementation_method_for_general_processing_(Web).md)
- [Implementation method to perform validation at any timing (Web)](PGUT_Phase/pg/Implementation_method_to_perform_validation_at_any_timing_(Web).md)
- [Handling method when an error occurs (Web)](PGUT_Phase/pg/Handling_method_when_an_error_occurs_(Web).md)

#### App implementation (batch)

- [Application configuration (batch)](Design_Phase/Application_configuration_(batch).md)
- [Implementation method for general processing (batch)](PGUT_Phase/pg/Implementation_method_for_general_processing_(batch).md)

#### App implementation (REST)

- [Application Configuration (REST)](Design_Phase/Application_Configuration_(REST).md)

#### Class unit test preparation and implementation (common)

- Progress method
  - [Programming and Unit testing work completion condition checklist](PGUT_Phase/checklist/PGUT_work_completion_condition_checklist.xlsx?raw=true)
  - [Deliverable self-checklist for programmers](PGUT_Phase/checklist/Deliverable_self-checklist_for_programmers.xlsx?raw=true)
- Rules
  - [Conventions for unit test of JavaDoc](PGUT_Phase/ut/Conventions_for_unit_test_of_JavaDoc.md)

#### Class unit test preparation and implementation (Web)

- [Unit test concept (Web)](PGUT_Phase/ut/Unit_test_concept_(Web).md)

#### Class unit test preparation and implementation (batch)

- [Unit test concept (batch)](PGUT_Phase/ut/Unit_test_concept_(batch).md)

#### Class unit test preparation and implementation (REST)

- [Unit test concept (REST)](PGUT_Phase/ut/Unit_test_concept_(REST).md)


#### Subfunction unit test implementation (common)

- Progress method
  - [Programming and Unit testing work completion condition checklist](PGUT_Phase/checklist/PGUT_work_completion_condition_checklist.xlsx?raw=true)
- Test method
  - [How to get evidence (log and DB dump)](PGUT_Phase/ut/How_to_get_evidence_(log_and_DB_dump).md)

#### Subfunction unit test implementation (Web)

- [Unit test concept (Web)](PGUT_Phase/ut/Unit_test_concept_(Web).md)
- [Test method of subfunction unit test (Web)](PGUT_Phase/ut/Test_method_of_subfunction_unit_test_(Web).md) 
- [Test Method of Automated Subfunction Unit Test (Web)](PGUT_Phase/ut/Test_method_of_automated_subfunction_unit_test_(Web).md)

#### Subfunction unit test implementation (batch)

- [Deliverable self-checklist for programmers](PGUT_Phase/checklist/Deliverable_self-checklist_for_programmers.xlsx?raw=true)
- [Unit test concept (batch)](PGUT_Phase/ut/Unit_test_concept_(batch).md)
- [Conventions for unit test of JavaDoc](PGUT_Phase/ut/Conventions_for_unit_test_of_JavaDoc.md)


## Team development environment

> ＜For those referring to the sample project＞  
> Since the team development environment (Redmine, Jenkins, etc.) of the sample project is not disclosed, the following is not linked. 
> It is recommended to provide links to each tool in the actual project so that development members can quickly access each tool.

- Redmine
  - It is used to manage tasks, issues and defects.
  - It is also used to manage requests between teams, such as improvement requests to the arche team and changes to static checks.
- NEXUS
  - Used to manage third-party JARs and applications released during the testing phase.
- Jenkins
  - This is used for CI. See [CI description](Development_Environment/CI_description.md) for contents of CI.
- Demo server
  - This server is used to deploy the application for demonstration.
