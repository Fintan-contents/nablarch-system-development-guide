# Changelog

All significant changes to this project will be documented in this file.

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
