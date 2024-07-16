# Preparation of Setup Guide for Development Environment


Guides are prepared to help developers setup a local development environment.

The main contents of the descriptions are as follows.

- Prerequisites
  - PC specifications required for development
  - OS version
- Software to be installed
  - Supply source
  - Version, edition
- If special settings are required, the setting method and setting values
  - Proxy settings, etc.

Since the installation procedure given in the official Website is often sufficient, 
detailed steps such as screenshots are not required. 
It is created based on the presence of special procedures and the proficiency of the project members.


The main software that may be used are as follows.

| Item                  | Product example                |
|------------------------|-----------------------------|
| JDK                    | AdoptOpenJDK, OracleJDK     |
| RDBMS                  | PostgreSQL, Oracle Database |
| Build tools           | Maven, Gradle               |
| IDE                    | Eclipse, IntelliJ           |
| Version control tools   | Git, SVN                    |
| Browser            | Chrome                      |
| Data modeling tool | Object Browser ER           |

-----

The image of the actual guide is shown below.

# xxx Project development environment construction guide

## Prerequisites

### PC specifications for development


| Required/Recommended | Item     | Spec              |
|-----------|----------|-----------------------|
| Required      | OS       | Windows 10 Pro 64bit  |
| Required      | CPU      | Core i7-8550U or higher    |
| Required      | Memory   | 8GB or more              |
| Required      | Office   | Microsoft Office 2010 |
| Recommended      | Monitor   | Full HD or higher           |
| Recommended      | Disc | SSD                   |


## Development environment construction procedure

| Item                  | Name                 | Version, edition, etc.  | Supply source                                                                                                            |
|-----------------------|----------------------|-------------------------|--------------------------------------------------------------------------------------------------------------------------|
| JDK                   | Eclipse Temurin      | OpenJDK 21              | https://adoptium.net/temurin/                                                                                            |
| RDBMS                 | PostgreSQL           | 16                      | https://www.postgresql.org/download/windows/ <br> (The actual project should be guided so that the version can be fixed) |
| Build tools           | Apache Maven         | 3.9.8                   | https://maven.apache.org/                                                                                                |
| IDE                   | IntelliJ             | Community Edition       | https://www.jetbrains.com/idea/                                                                                          |
| Version control tools | Git                  | Git for Windows         | https://gitforwindows.org/                                                                                               |
| Browser               | Chrome               | -                       | https://www.google.com/intl/ja/chrome/                                                                                   |
| Data modeling tool    | SI Object Browser ER | SI Object Browser ER 23 | https://products.sint.co.jp/ober/trial <br> (Use in-house license)                                                       |

