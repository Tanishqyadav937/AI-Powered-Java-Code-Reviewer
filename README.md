🤖 AI-Powered Java Code Reviewer

A tool that analyzes Java source code and gives feedback on style, correctness, and best practices. Complete web UI + REST API using pure Java.

GitHub repo: https://github.com/Tanishqyadav937/AI-Powered-Java-Code-Reviewer.git

🚀 Features

Core Java backend (no Spring)

Static + heuristic code analysis

Suggests naming improvements, code formatting, unused imports, etc.

RESTful API with JSON responses

Web UI to paste code and get review suggestions live

Error reporting, health check endpoint

📋 API Endpoints
POST /review

Analyze a block of Java code.

Request:

{
  "code": "public class sample { public void doStuff(){ System.out.println(\"Hello World\"); }}",
  "options": {
    "checkStyle": true,
    "checkNaming": true
  }
}


Response:

{
  "issues": [
    {
      "line": 1,
      "message": "Class name 'sample' should start with an uppercase letter.",
      "type": "NamingConvention"
    },
    {
      "line": 1,
      "message": "Missing space between method name and parenthesis in 'doStuff()'.",
      "type": "Formatting"
    }
  ],
  "suggestions": [
    "Rename class to 'Sample'.",
    "Add a space before '(' in method declaration for readability."
  ],
  "confidence": 0.75,
  "timestamp": "Mon Sep 29 12:15:00 UTC 2025"
}

GET /health

Health status of the service.

Response:

{
  "status": "up",
  "uptime": "2 days 5 hours 23 mins",
  "version": "1.0.2",
  "timestamp": "Mon Sep 29 12:15:00 UTC 2025"
}

🏗 Project Layout
AI-Powered-Java-Code-Reviewer/
├── src/
│   ├── CodeReviewerAPI.java
│   ├── analyzers/
│   │   ├── NamingAnalyzer.java
│   │   ├── StyleAnalyzer.java
│   │   └── ImportAnalyzer.java
├── ui/
│   ├── index.html
│   ├── styles.css
│   └── app.js
├── lib/
├── classes/
├── build.ps1
├── run.ps1
└── README.md

🔧 Prerequisites

Java 11 or newer

Git (to clone)

Powershell / or another terminal for scripts

Modern browser

⚡ Getting Started
git clone https://github.com/Tanishqyadav937/AI-Powered-Java-Code-Reviewer.git
cd AI-Powered-Java-Code-Reviewer
./build.ps1    # or manual build
./run.ps1


Then open ui/index.html in browser.
