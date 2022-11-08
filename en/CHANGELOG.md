# Changelog

All significant changes to this project will be documented in this file.

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
