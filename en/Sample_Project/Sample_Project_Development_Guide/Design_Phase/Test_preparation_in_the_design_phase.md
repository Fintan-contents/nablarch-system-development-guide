# Test Preparation in the Design Phase

In the V-shaped model, test cases in the unit test phase are carried out in the design phase. Still, in conventional projects, 
it is seen in some instances that test cases and test data creation in unit test phase are left to the discretion of the person in charge of the Programming Unit testing phase.

This causes the following problems:

- Because the developer does not have business knowledge, the focus on formal testing (if the design and Programming Unit testing personnel are different)
  - They will end up focusing on program-level white box testing and coverage
- Each person creates similar test data
  - Data which is meaningless business-wise will be generated in large amounts
  - There will be a significant impact on test data when database changes

For addressing these issues, this project defines unit test preparation in the design phase as a task for the designer. 
Specifically, carry out the following task.

## Creating common test data

Business experts create test data that can be used commonly in each business.
For example, in the case of credit card business, member (primary members, family members) and card variations (general cards, gold cards) are prepared.

This provides test data that is meaningful to business and improves the quality of the tests. 
Even if a change occurs in the database, the effect is minimized because there is only one type of common test data.

This kind of test data creation is required even after the integration test, which leads to efficiency after the integration test.


## Creation of test case by designers

Each designer uses the common test data described above to create test cases for subfunction unit tests.
This is expected to create a business test case based on the design document.

For example, in the case of withdrawing the subscription, the test cases will be covered considering the subscription withdrawal conditions for each member and whether there are any claims.

