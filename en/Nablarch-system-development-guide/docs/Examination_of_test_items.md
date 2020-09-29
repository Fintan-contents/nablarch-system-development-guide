# Examination of Test Items

The test items should be defined for each phase to ensure that there is no variation in execution content of tests depending on the person in charge.
However, increasing the number of items to improve the quality, increases the cost, makes detection of bugs more difficult, and may cause deterioration of QCD.

This section describes the concept to select the items to be tested.

## Collecting test viewpoints

First, create a unit test specification format for the project by referring to the [test type and test viewpoint catalog](https://fintan.jp/?p=45)(Only Japanese Edition).

It does not mean having more test items is better. 
More test items will make it easier to ensure quality, but will affect cost and delivery time. 
Therefore, it is necessary to set test items that meet the project QCD goals.

## Extraction of test viewpoint

Initially, prepare test viewpoints from the viewpoint catalog to avoid oversight,
but exclude those that are not relevant to the project.
For example, if your project does not have requirements for batch processing or sending emails, 
exclude these viewpoints.

The required viewpoints have to be selected here. 
Think about "what are the mistakes that a manufacturer can make, which will lead to the detection of bugs from this test viewpoint?" looking at the test viewpoint. 
As a result, if following are the test viewpoints, consider omitting them.

- When there is no chance that the test will find a bug, or the probability is very low and not worth the cost.
- When the bug can be detected by other activities.
  - When the bug can be detected in reviews.
  - When the bug can be detected by the test in the next phase.


For example, when realizing the layout using `jsp:include` in the screen confirmation item,
checking the individual common areas (headers and footers) can be omitted.
This is because if the coder engages in development according to the project development standard, then there are no factors that will embed bugs in the process of creating the screen.

If the coder creates the screen without following the project development standards, the common areas may not be implemented correctly.
However, you can check if the coding is according to the standard by reviewing the source code, 
and if the project development standards are developed properly, there is no reason for the coders to code in their own way.

In this way, it is possible to determine that checking the common parts can be omitted from the following viewpoints.

- Considering the project characteristics, bugs are less likely to be embedded.
- Bugs can be detected in other activities.
- Even if there is a bug, the effect is minor.

Instead considering a test viewpoint with a closed unit test, project productivity can be improved with a viewpoint based on the project characteristics.
