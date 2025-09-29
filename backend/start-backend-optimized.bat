@echo off
echo Starting AI Java Code Reviewer Backend (Optimized)...
echo.

REM Set JAVA_HOME if not already set
if "%JAVA_HOME%"=="" (
    set JAVA_HOME=C:\Program Files\Java\jdk-24
    echo Set JAVA_HOME to: %JAVA_HOME%
)

REM Set optimized JVM parameters for 7GB system
REM Limiting heap to 1GB max, 256MB initial
REM Enabling G1 garbage collector for better memory management
set MAVEN_OPTS=-Xms256m -Xmx1024m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+DisableExplicitGC

echo Using JVM Options: %MAVEN_OPTS%
echo.

REM Clean and compile first
echo Cleaning and compiling project...
call mvnw.cmd clean compile

REM Check if compilation was successful
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Compilation failed. Please check the errors above.
    pause
    exit /b 1
)

echo.
echo Starting Spring Boot application...
echo Backend will be available at: http://localhost:8080
echo H2 Console available at: http://localhost:8080/h2-console
echo.

REM Start the application
call mvnw.cmd spring-boot:run

REM Keep window open if there's an error
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Application failed to start. Check the logs above.
    pause
)