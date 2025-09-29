package com.javacodereviewer.backend.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.javacodereviewer.backend.model.CodeReviewResponse;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Service for integrating with AI APIs for code review
 */
@Service
public class AIReviewService {
    
    private final DemoAIService demoAIService;
    
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    
    private final OkHttpClient httpClient;
    private final Gson gson;
    
    @Value("${app.ai.openai.api-key:}")
    private String openaiApiKey;
    
    public AIReviewService(DemoAIService demoAIService) {
        this.demoAIService = demoAIService;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }
    
    /**
     * Reviews Java code using the specified AI provider
     */
    public CodeReviewResponse reviewCode(String code, String provider, String fileName) throws Exception {
        // Check if API key is available for the provider
        String apiKey = getAPIKey(provider);
        if (apiKey == null || apiKey.trim().isEmpty()) {
            // Use demo service when API key is not available
            return demoAIService.reviewCodeDemo(code, provider, fileName);
        }
        
        try {
            String response = callAIAPI(code, provider);
            return parseAIResponse(response, provider, fileName, code);
        } catch (Exception e) {
            // Fallback to demo service if API call fails
            CodeReviewResponse demoResponse = demoAIService.reviewCodeDemo(code, provider, fileName);
            demoResponse.setSummary("⚠️ **API Error - Demo Mode Activated**\n\n" + 
                "API call failed: " + e.getMessage() + "\n\n" + demoResponse.getSummary());
            return demoResponse;
        }
    }
    
    private String callAIAPI(String code, String provider) throws IOException {
        String apiKey = getAPIKey(provider);
        
        if ("OpenAI GPT-4".equals(provider)) {
            return callOpenAI(code, apiKey);
        } else {
            throw new IllegalArgumentException("Unsupported AI provider: " + provider + ". Only OpenAI GPT-4 is supported.");
        }
    }
    
    private String callOpenAI(String code, String apiKey) throws IOException {
        String prompt = buildPrompt(code);
        
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4");
        requestBody.addProperty("temperature", 0.3);
        requestBody.addProperty("max_tokens", 2000);
        
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);
        requestBody.add("messages", gson.toJsonTree(new JsonObject[]{message}));
        
        RequestBody body = RequestBody.create(
            requestBody.toString(),
            MediaType.get("application/json; charset=utf-8")
        );
        
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("OpenAI API call failed: " + response.code() + " " + response.message());
            }
            
            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            return jsonResponse.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();
        }
    }
    
    private String buildPrompt(String code) {
        return String.format("""
            Please review the following Java code and provide a comprehensive analysis. 
            Format your response as JSON with the following structure:
            
            {
                "summary": "Brief overview of the code quality and main issues",
                "errors": ["List of actual errors or bugs"],
                "warnings": ["List of potential issues or code smells"],
                "suggestions": ["List of improvement suggestions"],
                "goodPractices": ["List of good practices already followed"]
            }
            
            Focus on:
            - Syntax errors and compilation issues
            - Security vulnerabilities
            - Performance issues
            - Code style and best practices
            - Design patterns and architecture
            - Error handling
            - Documentation and comments
            
            Java Code to Review:
            ```java
            %s
            ```
            
            Please provide a detailed analysis in the JSON format specified above.
            """, code);
    }
    
    private CodeReviewResponse parseAIResponse(String response, String provider, String fileName, String originalCode) {
        CodeReviewResponse result = new CodeReviewResponse();
        result.setAiProvider(provider);
        result.setFileName(fileName);
        result.setSuccess(true);
        
        try {
            // Try to parse as JSON first
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            
            result.setSummary(jsonResponse.get("summary").getAsString());
            
            if (jsonResponse.has("errors")) {
                List<String> errors = new ArrayList<>();
                jsonResponse.getAsJsonArray("errors").forEach(item -> 
                    errors.add(item.getAsString()));
                result.setErrors(errors);
            }
            
            if (jsonResponse.has("warnings")) {
                List<String> warnings = new ArrayList<>();
                jsonResponse.getAsJsonArray("warnings").forEach(item -> 
                    warnings.add(item.getAsString()));
                result.setWarnings(warnings);
            }
            
            if (jsonResponse.has("suggestions")) {
                List<String> suggestions = new ArrayList<>();
                jsonResponse.getAsJsonArray("suggestions").forEach(item -> 
                    suggestions.add(item.getAsString()));
                result.setSuggestions(suggestions);
            }
            
            if (jsonResponse.has("goodPractices")) {
                List<String> goodPractices = new ArrayList<>();
                jsonResponse.getAsJsonArray("goodPractices").forEach(item -> 
                    goodPractices.add(item.getAsString()));
                result.setGoodPractices(goodPractices);
            }
            
        } catch (Exception e) {
            // If JSON parsing fails, treat the entire response as summary
            result.setSummary("AI Response (Raw):\n" + response);
            List<String> warnings = new ArrayList<>();
            warnings.add("Could not parse structured response from AI. Raw response provided above.");
            result.setWarnings(warnings);
        }
        
        result.calculateTotalIssues();
        return result;
    }
    
    private String getAPIKey(String provider) {
        if ("OpenAI GPT-4".equals(provider)) {
            return openaiApiKey;
        }
        return null;
    }
}
