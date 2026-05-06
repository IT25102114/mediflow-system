@REM Maven Wrapper script for Windows
@REM This downloads Maven if not present and runs it

@echo off
setlocal

set MAVEN_PROJECTBASEDIR=%~dp0
set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_PROPERTIES="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties"

@REM Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome
set JAVA_EXE=java.exe
goto checkJava

:findJavaFromJavaHome
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
if exist "%JAVA_EXE%" goto execute

:checkJava
where java >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ERROR: JAVA_HOME is not set and java.exe not found in PATH
    exit /b 1
)

:execute
@REM Use Spring Boot Maven Plugin directly with downloaded maven
set MAVEN_CMD="%JAVA_EXE%" -jar "%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar" %*
if exist %WRAPPER_JAR% (
    %MAVEN_CMD%
) else (
    @REM Fallback: use mvn directly or download
    echo Downloading Maven wrapper...
    powershell -Command "& { $url='https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar'; Invoke-WebRequest -Uri $url -OutFile '.mvn\wrapper\maven-wrapper.jar' -UseBasicParsing }"
    %MAVEN_CMD%
)
