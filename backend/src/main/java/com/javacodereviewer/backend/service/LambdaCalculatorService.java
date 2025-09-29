package com.javacodereviewer.backend.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Service for interacting with AWS Lambda Calculator API
 */
@Service
public class LambdaCalculatorService {
    
    private static final String LAMBDA_BASE_URL = "https://uojnr9hd57.execute-api.us-east-1.amazonaws.com/test";
    
    private final OkHttpClient httpClient;
    private final Gson gson;
    
    public LambdaCalculatorService() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }
    
    /**
     * Calculate using GET method with query parameters
     */
    public CalculatorResult calculateWithQuery(String operand1, String operand2, String operator) throws IOException {
        String url = String.format("%s/calc?operand1=%s&operand2=%s&operator=%s", 
            LAMBDA_BASE_URL, operand1, operand2, operator);
        
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
                
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Lambda API call failed: " + response.code() + " " + response.message());
            }
            
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, CalculatorResult.class);
        }
    }
    
    /**
     * Calculate using POST method with JSON body
     */
    public CalculatorResult calculateWithPost(double operand1, double operand2, String operator) throws IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("a", operand1);
        requestBody.addProperty("b", operand2);
        requestBody.addProperty("op", operator);
        
        RequestBody body = RequestBody.create(
            requestBody.toString(),
            MediaType.get("application/json; charset=utf-8")
        );
        
        Request request = new Request.Builder()
                .url(LAMBDA_BASE_URL + "/calc")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();
                
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Lambda API call failed: " + response.code() + " " + response.message());
            }
            
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, CalculatorResult.class);
        }
    }
    
    /**
     * Calculate using path parameters
     */
    public CalculatorResult calculateWithPath(String operand1, String operand2, String operator) throws IOException {
        // Handle division operator encoding
        String encodedOperator = operator.equals("/") ? "%2F" : operator;
        
        String url = String.format("%s/calc/%s/%s/%s", 
            LAMBDA_BASE_URL, operand1, operand2, encodedOperator);
        
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
                
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Lambda API call failed: " + response.code() + " " + response.message());
            }
            
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, CalculatorResult.class);
        }
    }
    
    /**
     * Validate operator
     */
    public boolean isValidOperator(String operator) {
        return operator.matches("^[+\\-*/]$|^(add|sub|mul|div)$|^%2F$");
    }
    
    /**
     * Normalize operator to standard format
     */
    public String normalizeOperator(String operator) {
        switch (operator.toLowerCase()) {
            case "add": return "+";
            case "sub": return "-";
            case "mul": return "*";
            case "div": case "%2f": return "/";
            default: return operator;
        }
    }
    
    /**
     * Result class for calculator responses
     */
    public static class CalculatorResult {
        private String result;
        private Input input;
        private Output output;
        private String a;
        private String b;
        private String op;
        private String c;
        
        // Getters and setters
        public String getResult() { return result; }
        public void setResult(String result) { this.result = result; }
        
        public Input getInput() { return input; }
        public void setInput(Input input) { this.input = input; }
        
        public Output getOutput() { return output; }
        public void setOutput(Output output) { this.output = output; }
        
        public String getA() { return a; }
        public void setA(String a) { this.a = a; }
        
        public String getB() { return b; }
        public void setB(String b) { this.b = b; }
        
        public String getOp() { return op; }
        public void setOp(String op) { this.op = op; }
        
        public String getC() { return c; }
        public void setC(String c) { this.c = c; }
        
        public static class Input {
            private double a;
            private double b;
            private String op;
            
            public double getA() { return a; }
            public void setA(double a) { this.a = a; }
            
            public double getB() { return b; }
            public void setB(double b) { this.b = b; }
            
            public String getOp() { return op; }
            public void setOp(String op) { this.op = op; }
        }
        
        public static class Output {
            private double c;
            
            public double getC() { return c; }
            public void setC(double c) { this.c = c; }
        }
    }
}