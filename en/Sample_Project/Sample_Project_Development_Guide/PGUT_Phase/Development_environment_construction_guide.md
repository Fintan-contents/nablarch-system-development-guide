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

| Item                                          | Name                 | Version, edition, etc.              | Supply source                                                 | Supplement                                                   |
| --------------------------------------------- | -------------------- | ----------------------------------- | ------------------------------------------------------------- | ------------------------------------------------------------ |
| JDK                                           | AdoptOpenJDK         | OpenJDK 8 (LTS) HotSpot Windows x64 | https://adoptopenjdk.net/                                     |                                                              |
| RDBMS                                         | PostgreSQL           | PostgreSQL Portable v10.4.1         | https://github.com/garethflowers/postgresql-portable/releases |                                                              |
| Build tools                                   | Apache Maven         | 3.6.1                               | https://maven.apache.org/                                     |                                                              |
| IDE                                           | IntelliJ             | Community Edition                   | https://www.jetbrains.com/idea/                               |                                                              |
| Version control tools                         | Git                  | 2.21.0 for Windows                  | https://git-scm.com/                                          |                                                              |
| Browser                                       | Chrome               | -                                   | https://www.google.com/intl/ja/chrome/                        |                                                              |
| Data modeling tool                            | SI Object Browser ER | SI Object Browser ER 18             | https://products.sint.co.jp/siob/trial                        | Use in-house license                                         |
| Window size specification tool                | Sizer                | Sizer v3.34                         | http://www.brianapps.net/sizer/                               |                                                              |
| Data dump tool for unit test                  | YoyoTool             | 3.1-SNAPSHOT                        | [Sample_Project/Design_Document/A1_Project_Management_System/080_Tools/DUMP_3.1-SNAPSHOT.zip](../../Design_Document/A1_Project_Management_System/080_Tools/DUMP_3.1-SNAPSHOT.zip) | Initialized for this project. Be sure to get it from here.   |
| Request recording tool for regression testing | Apache JMeter        | 5.6.3                               | http://jmeter.apache.org/download_jmeter.cgi                  |                                                              |


## Development environment construction supplementary notes
Describes tools that require supplementary installation.

### Window size specification tool (Sizer)
After installation, the following setting is specified.

#### Register window size
Right-click the sizer icon in the task tray to display the menu. 
Select the configure sizer.
Window size described in `5. Basic Design Guidelines for the screen` of `\Sample Project\Design Document\A1_Project Management System\020_Architecture Design\020_Development Standard\010_Design Standard\UI Standard (Screen).xlsx` will be set.

### Request recording tool for regression testing(JMeter)
After installation, configure the following settings.

#### Chrome shortcut for using JMeter proxies
JMeter is used as the HTTP proxy to record requests in the subfunction unit tests, but because setting up an HTTP proxy for every subfunction unit test would be too much work, we will create a shortcut with the proxy set up in advance.
Also, configure the CSS and JavaScript to run in secret mode to avoid using the cache.

1. Create a shortcut for Chrome
2. Right-click on the shortcut and edit the link as follows
   ```
   "[Chrome's installation directory]\chrome.exe" --proxy-bypass-list= --proxy-server=localhost:8888 --incognito
   ```

#### Initialization of Japanese localization batch

Possible to switch to Japanese after launching, but can't save configuration. Therefore, will translate it into Japanese in the following way.

Save and edit the [start-up-jmeter.bat](./ut/Subfunction_Unit_Test_Tool/start-up-jmeter.bat) to the desired location. (JMeter's own log is saved in the same location as the bat file).
Set the JMeter save/extraction directory to `JMETER_HOME` as shown below.
```
set JMETER_HOME=C:\tool\apache-jmeter-5.6.3
```

#### Proxy configuration
If running under a proxy, edit the file after `jmeter.bat` as follows.
```
start %JMETER_HOME%\bin\jmeter.bat -H "[Host name]" -P "[Port]" -u "[User name]" -a "[Password]" -N "127.0.0.1|localhost"
```