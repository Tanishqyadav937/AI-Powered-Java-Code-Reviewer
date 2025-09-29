# AI Java Code Reviewer & Lambda Calculator API Documentation

This project now includes two main services:
1. **AI Code Review Service** - Analyze Java code using OpenAI GPT-4 (with demo fallback)
2. **AWS Lambda Calculator Service** - Perform calculations using AWS Lambda API

## 🚀 Quick Start

### Starting the Backend

**Option 1 - PowerShell Script (Recommended):**
```powershell
.\Start-Backend.ps1
```

**Option 2 - Batch Script:**
```cmd
start-backend-optimized.bat
```

**Option 3 - Manual:**
```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-24"
$env:MAVEN_OPTS = "-Xms256m -Xmx1024m -XX:+UseG1GC"
./mvnw.cmd spring-boot:run
```

### Backend URLs
- **Main API**: http://localhost:8080
- **H2 Database Console**: http://localhost:8080/h2-console

---

## 📋 API Endpoints

### 🤖 Code Review API

#### Review Java Code
```
POST /api/reviews/review
Content-Type: application/json

{
    "code": "public class Example { public static void main(String[] args) { System.out.println(\"Hello World\"); } }",
    "aiProvider": "OpenAI GPT-4",
    "fileName": "Example.java"
}
```

**Response:**
```json
{
    "id": 1,
    "summary": "📋 **Code Review Summary**...",
    "errors": [],
    "warnings": ["Direct console output detected..."],
    "suggestions": ["Consider adding input validation..."],
    "goodPractices": ["Class naming follows Java conventions..."],
    "aiProvider": "OpenAI GPT-4 (Demo Mode)",
    "fileName": "Example.java",
    "totalIssues": 1,
    "success": true
}
```

#### Get Available AI Providers
```
GET /api/reviews/providers
```

**Response:**
```json
["OpenAI GPT-4"]
```

#### Get All Reviews
```
GET /api/reviews
```

#### Get Review by ID
```
GET /api/reviews/{id}
```

#### Get Recent Reviews
```
GET /api/reviews/recent
```

---

### 🧮 AWS Lambda Calculator API

#### Method 1: Query Parameters
```
GET /api/calculator/calc?operand1=10&operand2=5&operator=+
```

#### Method 2: JSON Body
```
POST /api/calculator/calc
Content-Type: application/json

{
    "a": 10,
    "b": 5,
    "op": "+"
}
```

#### Method 3: Path Parameters
```
GET /api/calculator/calc/10/5/+
```

**All methods return:**
```json
{
    "result": "10, 5, + => 15",
    "a": "10",
    "b": "5",
    "op": "+",
    "c": "15"
}
```

#### Supported Operations
```
GET /api/calculator/operations
```

**Response:**
```json
{
    "operators": ["+", "-", "*", "/", "add", "sub", "mul", "div"],
    "methods": [
        "GET /calc?operand1=1&operand2=2&operator=+",
        "POST /calc with JSON body",
        "GET /calc/{operand1}/{operand2}/{operator}"
    ],
    "description": "AWS Lambda-powered calculator with multiple input methods"
}
```

#### Health Check
```
GET /api/calculator/health
```

---

## ⚙️ Configuration

### Environment Variables

#### OpenAI API Key (Optional - Demo mode available)
```bash
# Windows (PowerShell)
$env:OPENAI_API_KEY = "sk-your-openai-api-key-here"

# Windows (Command Prompt)
set OPENAI_API_KEY=sk-your-openai-api-key-here

# Linux/Mac
export OPENAI_API_KEY=sk-your-openai-api-key-here
```

### Application Properties
Located at: `src/main/resources/application.properties`

Key configurations:
- `server.port=8080` - Backend server port
- `app.ai.openai.api-key=${OPENAI_API_KEY:}` - OpenAI API key
- `app.lambda.calculator.base-url=https://uojnr9hd57.execute-api.us-east-1.amazonaws.com/test` - Lambda API URL

---

## 🔧 Features

### Code Review Service
- ✅ **Demo Mode**: Works without API keys using intelligent static analysis
- ✅ **OpenAI Integration**: Advanced AI-powered reviews when API key is provided
- ✅ **Comprehensive Analysis**: Errors, warnings, suggestions, and good practices
- ✅ **Database Storage**: All reviews saved with timestamps
- ✅ **Error Handling**: Graceful fallback to demo mode on API failures

### Lambda Calculator Service
- ✅ **Multiple Input Methods**: Query params, JSON body, path params
- ✅ **AWS Lambda Integration**: Real-time calculations via AWS API Gateway
- ✅ **Operator Support**: Basic math (+, -, *, /) and named (add, sub, mul, div)
- ✅ **Validation**: Input validation with helpful error messages
- ✅ **Health Monitoring**: Built-in health checks

---

## 🛠️ Development

### Building the Project
```bash
./mvnw.cmd clean compile
```

### Running Tests
```bash
./mvnw.cmd test
```

### Creating JAR
```bash
./mvnw.cmd clean package
```

### Memory Configuration
The application is optimized for systems with 7-8GB RAM:
- Initial Heap: 256MB
- Maximum Heap: 1GB
- Garbage Collector: G1GC for better performance

---

## 🚀 Deployment Ready

This project is fully deployable with:

1. **No API Key Required**: Demo mode provides realistic code reviews
2. **Production Ready**: Optimized memory settings and error handling
3. **Multiple Services**: Both code review and calculator functionality
4. **Health Checks**: Monitor service status
5. **CORS Configured**: Ready for frontend integration
6. **Database Included**: H2 in-memory database for development

---

## 📊 Example Usage

### Test the Code Review (Demo Mode)
```bash
curl -X POST http://localhost:8080/api/reviews/review \
  -H "Content-Type: application/json" \
  -d '{
    "code": "public class Test { String name = null; public void setName(String name) { this.name = name; } }",
    "aiProvider": "OpenAI GPT-4",
    "fileName": "Test.java"
  }'
```

### Test the Lambda Calculator
```bash
# Method 1: Query parameters
curl "http://localhost:8080/api/calculator/calc?operand1=15&operand2=3&operator=*"

# Method 2: JSON body
curl -X POST http://localhost:8080/api/calculator/calc \
  -H "Content-Type: application/json" \
  -d '{"a": 15, "b": 3, "op": "*"}'

# Method 3: Path parameters
curl "http://localhost:8080/api/calculator/calc/15/3/*"
```

---

## 🔍 Troubleshooting

### Common Issues

1. **Java Version Mismatch**: Ensure you're using Java 21+ compatible with the project
2. **Memory Errors**: Use the provided startup scripts with optimized memory settings
3. **Port Already in Use**: Change `server.port` in application.properties
4. **Lambda API Issues**: Check the health endpoint `/api/calculator/health`

### Getting Help

- Check application logs for detailed error messages
- Use H2 console to inspect database: http://localhost:8080/h2-console
- Test endpoints using the provided curl examples

---

**🎉 Your project is now ready for deployment and demonstration!**