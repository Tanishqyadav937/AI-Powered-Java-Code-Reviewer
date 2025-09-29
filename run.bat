@echo off
echo AI-Powered Java Code Reviewer
echo =============================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Maven wrapper will be used instead of global Maven installation

echo Starting the application...
echo.
echo Note: Make sure you have set your API keys as environment variables:
echo - OPENAI_API_KEY (for OpenAI GPT-4)
echo - HUGGINGFACE_API_KEY (for Hugging Face Code Llama)
echo - CLAUDE_API_KEY (for Anthropic Claude)
echo.

mvnw.cmd javafx:run

pause
