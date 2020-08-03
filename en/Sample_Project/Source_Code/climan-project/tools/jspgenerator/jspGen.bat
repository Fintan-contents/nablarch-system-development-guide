@echo off

cd /d %~dp0

set NAF_JAR=lib/nablarch.jar
set NTF_JAR=lib/nablarch-tfw.jar
set NTB_JAR=lib/nablarch-toolbox.jar
set CP=%NAF_JAR%;%NTF_JAR%;%NTB_JAR%;.
set CONF=file:./jspGen.config


"%JAVA_HOME%\bin\java" -classpath %CP% nablarch.tool.jspgenerator.JspGenerator %1 %CONF% > jspGen.log 2>&1
