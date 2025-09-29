#!/bin/bash

echo "AI-Powered Java Code Reviewer"
echo "============================="
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven 3.6 or higher"
    exit 1
fi

echo "Starting the application..."
echo
echo "Note: Make sure you have set your API keys as environment variables:"
echo "- OPENAI_API_KEY (for OpenAI GPT-4)"
echo "- HUGGINGFACE_API_KEY (for Hugging Face Code Llama)"
echo "- CLAUDE_API_KEY (for Anthropic Claude)"
echo

mvn javafx:run
