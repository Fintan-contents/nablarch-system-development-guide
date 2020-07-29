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
- [PGUT phase](#PGUT-phase)
  - [Application engineer](#Application-engineer)
    - [PGUT (common)](#PGUT-common)
    - [PGUT（Web）](#PGUT-Web)
    - [PGUT (batch)）](#PGUT-batch)
    - [PGUT（REST）](#PGUT-REST)
- [Team development environment](#Team-development-environment)

Describes how to proceed with the development of the sample project.
If you are engaged in developing a sample project, please read this development guide carefully and proceed with development according to this development guide.

This development guide is maintained by the architect.
If you have any questions or doubts about this development guide, consult the architect.

This development guide covers from design to the PGUT phase.

## Flow of development

See below for the development flow.

- [Flow of development using Nablarch](../設計書/Flow_of_development_using_Nablarch.xlsx?raw=true)

Work contents and deliverables are shown by the combination of phase and role.
Check your work and deliverables for your role.

Guide is described for each role and task shown in the development flow.

## Requirements definition phase

### Requirements definer

#### Screen mockup creation

- [Placement location of screen mockup](../設計書/A1_プロジェクト管理システム/010_要件定義/020_画面モックアップ)
- [Screen mockup creation guide](要件定義工程/Screen_mockup_creation_guide.md)
- [UI standard](../設計書/A1_プロジェクト管理システム/020_方式設計/020_開発標準/010_設計標準)
  - Please refer to UI standard when creating screen mockups.
- [Design document format](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/030_%E8%A8%AD%E8%A8%88%E3%83%89%E3%82%AD%E3%83%A5%E3%83%A1%E3%83%B3%E3%83%88/010_%E3%83%95%E3%82%A9%E3%83%BC%E3%83%9E%E3%83%83%E3%83%88)
  - Screen list and screen transition diagram are created at the time of screen mockup creation.

## Design phase

### Designer

#### Design (common)

- [Placement location of design document](../設計書/A1_プロジェクト管理システム/030_アプリ設計)
- [Design document format](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/030_%E8%A8%AD%E8%A8%88%E3%83%89%E3%82%AD%E3%83%A5%E3%83%A1%E3%83%B3%E3%83%88/010_%E3%83%95%E3%82%A9%E3%83%BC%E3%83%9E%E3%83%83%E3%83%88)
  - Use the format provided by the Nablarch development standard.
- [Test preparation in the design phase](設計工程/Test_preparation_in_the_design_phase.md)（Unit test case design）
  - Create test data and test cases during the design phase. For more information, click the link above.
- [Create SQL file](設計工程/Create_SQL_file.md)（SQL validation）
  - Create SQL in the design phase. For more information, click the link above.

#### Screen design

- [Method to take a screenshot](設計工程/Method_to_take_a_screenshot.md)

#### Web service design

- [WebAPI URL design](設計工程/WebAPI_URL_design.md)

### Data modeler

#### Data definition

- [Method to proceed with domain definition](設計工程/Method_to_proceed_with_domain_definition.md)
- [Method to proceed with code design](設計工程/Method_to_proceed_with_code_design.md)

#### DB design

- [DB design standards](../設計書/A1_プロジェクト管理システム/020_方式設計/020_開発標準/010_設計標準)
  - Refer to both logical and physical designs.

## PGUT phase

### Application engineer

#### PGUT (common)

- Progress method
  - [PG/UT work completion condition checklist](PGUT工程/checklist/PGUT_work_completion_condition_checklist.xlsx?raw=true)
    - Please work according to the introduction example sheet of condition checklist for PG/UT work completion.
  - [Deliverable self-checklist for programmers](PGUT工程/checklist/Deliverable_self-checklist_for_programmers.xlsx?raw=true)
    - Use the files from the above links for the self-checks that in the case study sheet.
- Rules
  - [Version management rules](PGUT工程/Version_management_rules.md)
  - [Package configuration](PGUT工程/pg/Package_configuration.md)
  - [Naming convention for coding](PGUT工程/pg/Naming_convention_for_coding.md)
  - [Conventions for unit test of JavaDoc](PGUT工程/ut/Conventions_for_unit_test_of_JavaDoc.md)
  - [Response method when a static analysis check violation occurs](PGUT工程/pg/Response_method_when_a_static_analysis_check_violation_occurs.md)
- Working method
  - [Development environment construction guide](PGUT工程/Development_environment_construction_guide.md)
  - [Method to check coding conventions](PGUT工程/pg/Method_to_check_coding_conventions.md)
- Implementation method
  - [Method to implement service class](PGUT工程/pg/Method_to_implement_service_class.md)
- Test method
  - [How to get evidence (log and DB dump)](PGUT工程/ut/How_to_get_evidence_(log_and_DB_dump).md)

#### PGUT (Web)
- [Application configuration (Web)](設計工程/Application_configuration_(Web).md)
- [Unit test concept (Web)](PGUT工程/ut/Unit_test_concept_(Web).md)
- [Implementation method for general processing (Web)](PGUT工程/pg/Implementation_method_for_general_processing_(Web).md)
- [Implementation method to perform validation at any timing (Web)](PGUT工程/pg/Implementation_method_to_perform_validation_at_any_timing_(Web).md)
- [Handling method when an error occurs (Web)](PGUT工程/pg/Handling_method_when_an_error_occurs_(Web).md)
- [Test method of subfunction unit test (Web)](PGUT工程/ut/Test_method_of_subfunction_unit_test_(Web).md) 
- [How to get evidence (screen)](PGUT工程/ut/How_to_get_evidence_(screen).md)

#### PGUT (batch)
- [Application configuration (batch)](設計工程/Application_configuration_(batch).md)
- [Unit test concept (batch)](PGUT工程/ut/Unit_test_concept_(batch).md)
- [Implementation method for general processing (batch)](PGUT工程/pg/Implementation_method_for_general_processing_(batch).md)

#### PGUT (REST)
- [Application Configuration (REST)](設計工程/Application_Configuration_(REST).md)
- [Unit test concept (REST)](PGUT工程/ut/Unit_test_concept_(REST).md)

## Team development environment

> ＜For those referring to the sample project＞  
> Since the team development environment (Redmine, Jenkins, etc.) of the sample project is not disclosed, the following is not linked. 
> Links are provided to each tool so that development members can access it immediately.

- Redmine
  - It is used to manage tasks, issues and defects.
  - It is also used to manage requests between teams, such as improvement requests to the arche team and changes to static checks.
- NEXUS
  - Used to manage third-party JARs and applications released during the testing phase.
- Jenkins
  - This is used for CI. See [CI description](開発環境/CI_description.md) for contents of CI.
- Demo server
  - This server is used to deploy the application for demonstration.
