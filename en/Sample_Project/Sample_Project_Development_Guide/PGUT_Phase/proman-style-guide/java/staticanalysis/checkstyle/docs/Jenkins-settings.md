# Method to Collect Checkstyle Execution Results in Jenkins

This document guides how to collect Checkstyle execution results after building a project with Jenkins.

The contents of this document have been verified with Jenkins 2.401.1 and Warnings Next Generation Plugin 10.2.0

## Install Warnings Next Generation Plugin on Jenkins

First install the Warnings Next Generation Plugin on Jenkins.

Open "Manage Jenkins" from the menu and select "Plugins" under "System Configuration".

Search for "Warnings Next Generation Plugin" in "Available plugins", check "Warnings Next Generation Plugin" and click "Install without restart".

## Collect the check results

Open the job's "Configure" and add "Invoke top-level Maven targets" to "Build Steps".
Add `checkstyle:checkstyle` to your "Goals" to create a check result.

![](./assets/jenkins-checkstyle-build.png)

Add "Record compiler warnings and static analysis results" to "Post-build Actions" to collect check results.
Select "CheckStyle" in "Tool" to collect CheckStyle check results.

![](./assets/jenkins-checkstyle-postbuild.png)

This completes the configuration for collecting check results.
Check results will now be collected when build is implemented.

When the check results are collected, you will see "CheckStyle Warnings" in the menu.
Open "CheckStyle Warnings" to see the details of the latest results and the history of violation counts.

![](./assets/jenkins-checkstyle-warnings.png)
