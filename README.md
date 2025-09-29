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

Agar chaho, toh do alag versions bana sakta ho — ek basic version, ek full version — aur sample data bhi alag karunga. Kaunsa version chahiye pehle?

You said:
wasa bana kr do jisse direct copy paste kr saku
ChatGPT said:

Samajh gaya 👍 Tumhe ek ready-to-use README.md chahiye jo repo me seedha paste kar sako. Maine updated version bana diya hai jisme alage sample data hai aur tumhare GitHub repo link bhi add hai.

Bas copy-paste kar ke repo me daal do 👇

# 🤖 AI-Powered Java Code Reviewer

A lightweight **Core Java project** that reviews Java source code, detects issues, and provides suggestions for improvement.  
It includes a **REST API** and a clean **Vanilla JS frontend**.  

🔗 **GitHub Repo:** [AI-Powered-Java-Code-Reviewer](https://github.com/Tanishqyadav937/AI-Powered-Java-Code-Reviewer.git)

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![Frontend](https://img.shields.io/badge/Frontend-Vanilla%20JS-yellow.svg)
![API](https://img.shields.io/badge/API-REST-blue.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

---

## 🚀 Features

- ✅ Pure **Core Java Backend** (no Spring Boot)
- ✅ **RESTful API** with JSON requests & responses
- ✅ **Heuristic + Rule-Based Checks** (naming, style, unused imports)
- ✅ **Responsive Web UI** (paste Java code, get review instantly)
- ✅ **Error Handling** with descriptive messages
- ✅ **Health Endpoint** for monitoring
- ✅ **CORS Support** for cross-origin access

---

## 📋 API Endpoints

### 🔍 `POST /review`

Analyze a Java code snippet and return issues + suggestions.

**Request:**

```json
{
  "code": "class demo{public void test(){int x=5;System.out.println(x);}}"
}


Response:

{
  "issues": [
    {
      "line": 1,
      "message": "Class name 'demo' should be capitalized.",
      "type": "Naming"
    },
    {
      "line": 1,
      "message": "Missing spaces around '=' operator.",
      "type": "Formatting"
    }
  ],
  "suggestions": [
    "Rename class to 'Demo'.",
    "Use proper indentation for better readability."
  ],
  "confidence": 0.81,
  "timestamp": "Mon Sep 29 12:45:00 UTC 2025"
}

💡 GET /health

Check API server status.

Response:

{
  "status": "healthy",
  "uptime": "1 day 4 hours 12 minutes",
  "version": "1.0.0",
  "timestamp": "Mon Sep 29 12:45:00 UTC 2025"
}

🏗️ Project Structure
AI-Powered-Java-Code-Reviewer/
├── src/
│   ├── CodeReviewerAPI.java      # Main server
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

Java 11+ (Java 17 recommended)

Git (to clone repo)

PowerShell (for Windows scripts)

Modern Browser (Chrome / Firefox / Edge)

⚡ Quick Start
Clone the repo
git clone https://github.com/Tanishqyadav937/AI-Powered-Java-Code-Reviewer.git
cd AI-Powered-Java-Code-Reviewer

Build the project
.\build.ps1

Run the server
.\run.ps1

Open the Web UI
start ui\index.html

🌐 Usage
Web UI

Start the server

Open ui/index.html in browser

Paste Java code in textarea

Click “Review Code”

Get issues + suggestions instantly

API via curl
curl -X POST http://localhost:8080/review \
  -H "Content-Type: application/json" \
  -d '{"code":"public class Test{public void run(){System.out.println(\"hi\");}}"}'

🚀 Future Enhancements

 AST Parsing with Java Parser

 Machine Learning based recommendations

 Code Syntax Highlighting in UI

 Export Reports as PDF/CSV

 Dark Mode frontend

Built with ❤️ using Core Java + Vanilla JS

Happy coding & cleaner Java! 🚀
