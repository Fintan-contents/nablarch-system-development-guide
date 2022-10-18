@echo off
set JMETER_LANGUAGE=-Duser.language="ja" -Duser.region="JP"
set JMETER_HOME=C:\apache-jmeter-5.2.1
start %JMETER_HOME%\bin\jmeter.bat
