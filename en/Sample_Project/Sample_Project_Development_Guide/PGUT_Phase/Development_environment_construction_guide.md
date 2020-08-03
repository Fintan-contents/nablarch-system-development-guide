# Development Environment Construction Guide

## Prerequisites

### PC specifications for development

| Required/Recommended | Item     | Spec.              |
| --------- | -------- | --------------------- |
| Required      | OS       | Windows 10 Pro 64bit  |
| Required      | CPU      | Core i7-8550U or higher    |
| Required      | Memory   | 8GB or more              |
| Required      | Office   | Microsoft Office 2010 |
| Recommended      | Monitor   | Full HD or higher           |
| Recommended      | Disc | SSD                   |


## Development environment construction procedure

Install the following software.

| Item                           | Name                 | Version, edition, etc.           | Supply source                                      | Supplement                                                         |
| ------------------------------ | -------------------- | ----------------------------------- | ------------------------------------------- | ------------------------------------------------------------ |
| JDK                            | AdoptOpenJDK         | OpenJDK 8 (LTS) HotSpot Windows x64 | https://adoptopenjdk.net/                   |                                                              |
| RDBMS                          | PostgreSQL           | PostgreSQL Portable v10.4.1         | https://gareth.flowers/postgresql-portable/ |                                                              |
| Build tools                   | Apache Maven         | 3.6.1                               | https://maven.apache.org/                   |                                                              |
| IDE                            | IntelliJ             | Community Edition                   | https://www.jetbrains.com/idea/             |                                                              |
| Version control tools           | Git                  | 2.21.0 for Windows                  | https://git-scm.com/                        |                                                              |
| Browser                       | Chrome               | -                                   | https://www.google.com/intl/ja/chrome/      |                                                              |
| Data modeling tool         | SI Object Browser ER | SI Object Browser ER 18             | (Use in-house license)                      |                                                              |
| Window size specification tool     | Sizer                | Sizer v3.34                         | http://www.brianapps.net/sizer/             |                                                              |
| Screen hard copy tool         | SnapCrab for Windows | SnapCrab 1.1.3                      | https://www.fenrir-inc.com/jp/snapcrab/     |                                                              |
| Data dump tool for unit test | YoyoTool             | 3.1-SNAPSHOT                        | `80_tool\DUMP_3.1-SNAPSHOT.zip`           | Initialized for this project. Be sure to get it from here. |


## Development environment construction supplementary notes
Describes tools that require supplementary installation.

### Window size specification tool (Sizer)
After installation, the following setting is specified.

#### Register window size
Right-click the sizer icon in the task tray to display the menu. 
Select the configure sizer.
Window size described in `5. Basic Design Guidelines for the screen` of `\Sample Project\Design Document\A1_Project Management System\020_Architecture Design\020_Development Standard\010_Design Standard\UI Standard (Screen).xlsx` will be set.

### Screen hard copy tool (SnapCrab for Windows)
After installation, the following detailed setting is specified.

#### Capture save location
Select the specified save destination and specify the working directory where screenshots are stored when a screen hard copy of the unit test is obtained. 
You can save the screenshots in any directory. 

#### Image format
Select JPEG full color.

#### Others
Uncheck the sound checkbox as it will be played during each capture.