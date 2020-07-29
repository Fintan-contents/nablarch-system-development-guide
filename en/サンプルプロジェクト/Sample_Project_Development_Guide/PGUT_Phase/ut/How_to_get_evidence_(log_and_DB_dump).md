# How to Get Evidence (Log and DB Dump)

## Preparation before obtaining evidence

### Log

Delete local log files.  
This is to make it easier to retrieve only the parts you need later.

### DB dump

#### Update the table definition list

Updates the table definition list of the data dump tool (YoyoTool) for unit test.  
Update is performed with the following procedure.

1. Open DUMP.xlsm.
1. Click on "Get table definition" at the top of the MAIN sheet screen.
1. In the displayed dialog, specify the directory where the edm file is located.  
   `<Cloned directory>\proman-project\proman-common\src\main\resources\entity`   
   Quotes "" used before and after the path are not required.
1. Change column I to "○" for the table to be acquired.   
   See `3.3.2. Evidence for Subfunction Unit Tests` of `/Sample_Project/Design_Document/A1_Project_Management_System/020_Architecture_Design/020_Development_Standard/020_Test_Standard/Unit_Test_Standard.xlsx` to find out which table you will get.  
1. **Please save DUMP.xlsm.**

#### Get data dump before test execution

1. Click "Start" at the top of the DUMP.xlsm MAIN sheet screen.
1. The dump tool window appears. Click on "Sheet Clear" in the displayed window.
1. Click on "Get DB Dump".
1. Click on DUMP Export and specify a directory to save the dump.
1. Click the "×" in the upper right corner of the dump tool to close the window.


## How to get evidence

### Log

Execute the test target.   
After executing the test target, check the log file, if it is difficult to understand the link with the test case, take measures such as splitting the log file.

### DB dump

#### Get data dump after test execution

The retrieval method is the same as [Get data dump before test execution](#get-data-dump-before-test-execution).  
Check the pre and post dump, and make sure that the records and columns are updated as expected.   
After confirmation, change the background color of the updated column to yellow and save.


## After getting the evidence

Save following the procedure of `3.3.4. How to store evidence` of `/Sample_Project/Design_Document/A1_Project_Management_System/020_Architecture_Design/020_Development_Standard/020_Test_Standard/Unit_Test_Standard.xlsx`   
