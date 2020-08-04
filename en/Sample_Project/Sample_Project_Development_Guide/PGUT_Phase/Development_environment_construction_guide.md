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
| Data modeling tool                            | SI Object Browser ER | SI Object Browser ER 18             | (Use in-house license)                                        |                                                              |
| Window size specification tool                | Sizer                | Sizer v3.34                         | http://www.brianapps.net/sizer/                               |                                                              |
| Screen hard copy tool                         | SnapCrab for Windows | SnapCrab 1.1.3                      | https://www.fenrir-inc.com/jp/snapcrab/                       |                                                              |
| Data dump tool for unit test                  | YoyoTool             | 3.1-SNAPSHOT                        | `80_tool\DUMP_3.1-SNAPSHOT.zip`                               | Initialized for this project. Be sure to get it from here.   |
| Request recording tool for regression testing | Apache JMeter        | 5.2.1                               | http://jmeter.apache.org/download_jmeter.cgi                  |                                                              |


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

Save and edit the [JMeter起動用バッチ.bat(JMeter startup batch.bat)](./ut/Subfunction_Unit_Test_Tool/JMeter起動用バッチ.bat) to the desired location. (JMeter's own log is saved in the same location as the bat file).
Set the JMeter save/extraction directory to `JMETER_HOME` as shown below.
```
set JMETER_HOME=C:\tool\apache-jmeter-5.2.1
```

#### Proxy configuration
If running under a proxy, edit the file after `jmeter.bat` as follows.
```
start %JMETER_HOME%\bin\jmeter.bat -H "[Host name]" -P "[Port]" -u "[User name]" -a "[Password]" -N "127.0.0.1|localhost"
```