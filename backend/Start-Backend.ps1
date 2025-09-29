# AI Java Code Reviewer - Backend Startup Script (PowerShell)
Write-Host "Starting AI Java Code Reviewer Backend (Optimized)..." -ForegroundColor Green
Write-Host ""

# Set JAVA_HOME if not already set
if (-not $env:JAVA_HOME) {
    $env:JAVA_HOME = "C:\Program Files\Java\jdk-24"
    Write-Host "Set JAVA_HOME to: $env:JAVA_HOME" -ForegroundColor Yellow
}

# Set optimized JVM parameters for 7GB system
# Limiting heap to 1GB max, 256MB initial
# Enabling G1 garbage collector for better memory management
$env:MAVEN_OPTS = "-Xms256m -Xmx1024m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+DisableExplicitGC"

Write-Host "Using JVM Options: $env:MAVEN_OPTS" -ForegroundColor Cyan
Write-Host ""

# Clean and compile first
Write-Host "Cleaning and compiling project..." -ForegroundColor Yellow
$compileResult = & ./mvnw.cmd clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "[ERROR] Compilation failed. Please check the errors above." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "Starting Spring Boot application..." -ForegroundColor Green
Write-Host "Backend will be available at: http://localhost:8080" -ForegroundColor Cyan
Write-Host "H2 Console available at: http://localhost:8080/h2-console" -ForegroundColor Cyan
Write-Host ""
Write-Host "Press Ctrl+C to stop the application" -ForegroundColor Yellow
Write-Host ""

# Start the application
& ./mvnw.cmd spring-boot:run

# Keep window open if there's an error
if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "[ERROR] Application failed to start. Check the logs above." -ForegroundColor Red
    Read-Host "Press Enter to exit"
}