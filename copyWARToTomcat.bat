@echo off
REM Build the WAR
call gradlew clean war

REM Check if WAR exists
if not exist build\libs\*.war (
    echo WAR file not found in build\libs
    exit /b 1
)

REM Copy WAR to Tomcat webapps
copy build\libs\*.war C:\apache-tomcat-10.1.49\webapps\

REM Start Tomcat
C:\apache-tomcat-10.1.49\bin\startup.bat