# Changelog

All significant changes to this project will be documented in this file.

## 3.6 (2024-09-30)

- Improve the style guide
  - Based on [the Style Guide Revisions from Version 3.2 to 5.0](https://github.com/Fintan-contents/coding-standards/blob/main/CHANGELOG.md), the following corrections was made.
    - Unauthorized API check tool
      - With the support of Jakarta EE, we have changed to refer to the latest version of Nablarch's configuration file as a sample configuration file.
    - JavaScript Style guide
      - The acronyms of specification in Java EE used in the Readme changed to the Jakarta EE specification name.
    - JSP Style guide
      - The acronyms of specification in Java EE used in the Readme and title of coding convention changed to the Jakarta EE specification name.
      - For the acronyms of specification in Java EE used in the coding convention, the reading to the Jakarta EE specification is noted.
    - Java coding conventions
      - Java version upgraded to 17.
      - New rules have been added due to Java version upgrade.
      - Some rules have been modified due to Java version upgrade.
    - Java code formatter
      - Added instructions on how to configure IntelliJ IDEA and Eclipse.
      - Reviewed configuration file samples and recreated them for the latest version of IDE.
      - Added setting in EditorConfig.
    - Checkstyle guide
      - CheckStyle version upgraded to 10.12.0.
      - New rules have been added due to CheckStyle version upgrade.
      - Reviewed configuration file and deleted some rules.
      - Reviewed configuration file and modified the severity of each rule to error.
      - Some rules related to formatting have been removed in consideration of the use of Java code formatter configuration file samples.
      - Java of CheckStyle sample project version upgraded to 17.
      - Source code of CheckStyle sample project has been modified due to modification of configuration file.
      - Plugin used to collect execution results in Jenkins is no longer supported, so changed plugin to use.
    - SpotBugs guide
      - SpotBugs version upgraded to 4.7.3.
      - Java of SpotBugs sample project version upgraded to 17.
      - Sample code for standard detectable bugs has been added to SpotBugs sample project.
      - Plugin used to collect execution results in Jenkins is no longer supported, so changed plugin to use.
    - Unauthorized API check tool
      - Java of sample project version upgraded to 17.
      - Authorized API of the Java standard library has been modified due to Java version upgrade.
      - Sample configuration file has been placed.

- Improve the sample project

  - Nablarch version upgraded to 6u2.
  - Removed JSP auto generation tool not provided in Nablarch 6.
  - ArchUnit version upgraded to 1.0.1.
  - In the "Client Management System (Climan)", HTTP Access Log Handler was replaced by HTTP Access Log (for RESTful Web Service) Handler.

- Improve the design documents
  - Based on [the corrections made in the Nablarch Development Standards Version 2.3](https://github.com/nablarch-development-standards/nablarch-development-standards/blob/master/en/CHANGELOG.md), the following corrections was made.
    - The validation provided by Nablarch standard in the domain definition document of the sample project was changed to the validation function compliant with Validation compliant with Jakarta Bean Validation of Jakarta EE (Bean Validation).
    - Removed change history from unit test standards.

- README.md

  - Since Nablarch 6 series is based on the Jakarta EE 10 assumption, we have added a new section "The reading into the Jakarta EE specification in this document".

## 3.5 (2022-10-31)

- Improve the sample project

  - Corrected build method and launching method described in the "Project Management System (Proman)" description.
  - Inconsistencies between the design document and implementation have been corrected.

## 3.4 (2022-03-31)

- Improve the style guide
  
  - SpotBugs maven plugin version upgraded to 4.5.0.0.
    This is to increase the number of detectable bugs by keeping up with the latest version.
    (SpotBugs4.X also experimentally supports Java 11 and newer.)
  - Replaced with the unpublised API checker that is compatible with SpotBugs 4.X.
  
- Improve the sample project

  - SpotBugs maven plugin version upgraded to 4.5.0.0.
  - Replaced with the unpublised API checker that is compatible with SpotBugs 4.X.
  - Nablarch version upgraded to 5u21.
  - javax.ws.rs-api upgrade to 2.1.1.
  - JUnit version upgrade to 5.8.2.

## 3.3 (2021-03-31)
### Updates
#### Change
- Improve the sample project
  - CheckStyle version upgraded to 8.29.  
    This was because the version was outdated and there was a possibility that an XXE attack could be launched by loading invalid XML.
  - JUnit version upgraded to 4.13.1.  
    This is to apply the [Security fix: TemporaryFolder now limits access to temporary folders on Java 1.7 or later](https://github.com/junit-team/junit4/blob/HEAD/doc/ReleaseNotes4.13.1.md#security-fix-temporaryfolder-now-limits-access-to-temporary-folders-on-java-17-or-later) .
  - jackson-databind version upgraded to 2.10.5.1.  
    This is to apply the [CVE-2020-25649](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2020-25649) issue fix. The sample project does not use any features affected by CVE-2020-25649, but upgraded it to avoid accidentally using an older version for reference.
  - Added a user guide for Find Security Bugs.

## 3.2 (2020-12-23)
### Updates
#### Change
- Updated the English edition. Contents are the same as the Japanese version 3.1.

## 3.1 (2020-09-29)
### Updates
#### Change
- Improve the sample project
  - In the "Nablarch Style Guide", "How to run ArchUnit with Maven" section, described how to "Place filter files to exclude checks".
  - The anti-CSRF function has been disabled in the profile for the local development environment (development). This is because the anti-CSRF function is not necessary when testing in the local development environment.
  - The unit test of REST API added in Nablarch5u18 is applied to the customer management system. In addition, "Unit Test Concept (REST)" in "Sample Project Development Guide" has been updated.
  - Routing by Path annotation of JAX-RS, which was added in Nablarch5u18, was applied to the customer management system.

## 3.0 (2020-08-14)
### Updates
#### Add
- English version is now available
  - The content is the same as the Japanese version 2.2.

## 2.2 (2020-06-05)
### Updates
#### Change
- Sample Project
  - The anti CSRF feature added in Nablarch5u17 has been applied to the project management system.
- Nablarch Function Security Matrix
  - The supplemental description of the CSRF countermeasures has been changed to match the CSRF countermeasures added in Nablarch5u17.

## 2.1 (2020-04-28)
### Updates
#### Change
- Nablarch Function Security Matrix
  - Added supplementary explanation of CSRF measures in Nablarch5u16.

## 2.0 (2020-03-31)
### Updates
#### Add
- Nablarch Function Security Matrix
  - The Nablarch System Development Guide has been updated with a Nablarch security compatibility table that can be referenced during method design.
- Add features to the sample project
  - The project management system, which is available as a sample project, has a project search function and a project update function.
- Improving the testing process
  - Added automation of regression tests using JMeter.
- Adding ArchUnit test code
  - With the addition of the ArchUnit Implementation Guide to the Nablarch Style Guide, tests using ArchUnit have been added to the sample project.

## 1.0 (2019-09-30)
### Updates
- First Version Public
