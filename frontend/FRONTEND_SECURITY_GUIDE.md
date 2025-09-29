# 🔒 Frontend Security & Usage Guide

## 🛡️ **API Key Security**

### ✅ **Current Security Implementation:**

1. **Local Storage Only**
   - API keys are stored in your browser's `localStorage`
   - Never transmitted to our servers
   - Only used for direct API calls to OpenAI

2. **Secure Transmission**
   - API keys sent only to OpenAI's official API endpoints
   - All communications use HTTPS encryption
   - No intermediate servers or proxies

3. **Demo Mode Available**
   - Works without any API keys
   - No external API calls required
   - Complete functionality offline

### 🔐 **Best Practices for API Key Security:**

#### ✅ **DO:**
- Keep your API keys private and secure
- Use environment variables in production deployments
- Regularly rotate your API keys
- Monitor your API usage in OpenAI dashboard
- Use demo mode for testing and presentations

#### ❌ **DON'T:**
- Never commit API keys to version control (Git)
- Don't share API keys publicly or in screenshots
- Avoid hardcoding keys in source code
- Don't use production keys in development

### 🚀 **Demo Mode (Recommended for Testing)**

```javascript
// Demo mode automatically activated when no API key is provided
const settings = {
  openaiApiKey: '', // Leave empty for demo mode
  demoMode: true    // Enables intelligent static analysis
};
```

**Demo Mode Features:**
- 🤖 Intelligent Java code analysis
- 📋 Realistic feedback (errors, warnings, suggestions)
- 💾 Full database functionality
- ⚡ Fast response times (no API calls)
- 💰 Zero costs

---

## 🧮 **Calculator Integration**

### **AWS Lambda Calculator Service**
- **Endpoint:** `/api/calculator/calc`
- **Methods:** GET (query/path) and POST (JSON)
- **No API keys required**
- **Real-time AWS Lambda integration**

**Usage Examples:**
```javascript
import { CalculatorService } from './services/CalculatorService';

// Method 1: Query Parameters
const result1 = await CalculatorService.calculateWithQuery('10', '5', '+');

// Method 2: JSON Body
const result2 = await CalculatorService.calculateWithPost(10, 5, '*');

// Method 3: Path Parameters  
const result3 = await CalculatorService.calculateWithPath('10', '5', '/');
```

---

## 📱 **Frontend Architecture**

### **Components:**
- `CodeReviewer.js` - Main code review interface
- `Settings.js` - API key and configuration management
- `ReviewCard.js` - Display individual review results
- `ReviewHistory.js` - Browse past reviews
- `Header.js` - Navigation and branding

### **Services:**
- `CodeReviewService.js` - Handle all code review API calls
- `CalculatorService.js` - AWS Lambda calculator integration

### **Security Features:**
- 🔒 LocalStorage encryption ready
- 🛡️ Input validation and sanitization  
- 🔐 Secure API key handling
- 🚫 No server-side key storage
- ✅ HTTPS enforced for all API calls

---

## ⚙️ **Configuration Options**

### **Environment Variables (.env):**
```bash
# Optional - Backend API URL
REACT_APP_API_URL=http://localhost:8080/api

# Optional - Enable development mode
REACT_APP_DEV_MODE=true

# Optional - Default demo mode
REACT_APP_DEMO_MODE=true
```

### **Settings Panel Options:**
1. **OpenAI API Key** (Optional)
   - Used for advanced AI analysis
   - Falls back to demo mode if empty
   - Stored locally in browser

2. **Demo Mode Toggle**
   - Enable/disable intelligent demo analysis
   - Works completely offline
   - No API costs

3. **Auto-save Reviews**
   - Automatically save all reviews to history
   - Stored in local database

4. **Theme Selection**
   - Light, Dark, Auto modes available

---

## 🚀 **Getting Started**

### **Quick Start (Demo Mode):**
1. Start the application
2. Navigate to Code Review
3. Paste your Java code
4. Click "Review Code" 
5. Get instant intelligent analysis!

### **With OpenAI API:**
1. Get API key from [OpenAI Platform](https://platform.openai.com/api-keys)
2. Go to Settings
3. Enter your API key
4. Save settings
5. Enjoy advanced AI-powered reviews!

### **Calculator Usage:**
1. Navigate to Calculator (if UI available)
2. Enter numbers and operator
3. Choose method (Query/JSON/Path)
4. Get real-time AWS Lambda results

---

## 🔍 **Privacy & Data Handling**

### **What We Store Locally:**
- ✅ Your API keys (localStorage)
- ✅ Application settings
- ✅ Code review history
- ✅ User preferences

### **What We DON'T Store:**
- ❌ Your actual code on our servers
- ❌ API keys on our servers  
- ❌ Personal information
- ❌ Usage analytics (unless enabled)

### **External Services:**
- **OpenAI API** - Only when API key is provided
- **AWS Lambda** - For calculator functionality
- **Local Backend** - Your own Spring Boot server

---

## 🛠️ **Development & Deployment**

### **Local Development:**
```bash
npm install
npm start
```

### **Production Build:**
```bash
npm run build
```

### **Security Considerations for Deployment:**
1. Use environment variables for configuration
2. Enable HTTPS in production
3. Implement proper CSP headers
4. Regular security audits of dependencies
5. Consider API key encryption for enterprise use

---

## 📊 **Feature Status**

### ✅ **Production Ready:**
- Demo mode analysis
- LocalStorage security
- Calculator integration  
- Review history
- Settings management
- Responsive design

### 🔄 **Enhanced Security (Optional):**
- API key encryption at rest
- Secure key exchange protocols
- Enterprise authentication integration
- Audit logging capabilities

---

## 🆘 **Troubleshooting**

### **Common Issues:**

1. **API Key Not Working:**
   - Check key format (starts with `sk-`)
   - Verify OpenAI account has credits
   - Try demo mode as fallback

2. **Calculator Not Responding:**
   - Check backend is running on port 8080
   - Verify network connectivity
   - Use health check endpoint

3. **Settings Not Saving:**
   - Check browser localStorage permissions
   - Clear browser cache if needed
   - Verify no browser extensions blocking storage

### **Support:**
- Check browser console for error messages
- Verify backend API is accessible
- Use demo mode for offline testing

---

**🎯 Your frontend is secure, feature-rich, and ready for deployment!**