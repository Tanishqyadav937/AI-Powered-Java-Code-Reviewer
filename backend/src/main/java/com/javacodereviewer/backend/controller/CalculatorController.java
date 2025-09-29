package com.javacodereviewer.backend.controller;

import com.javacodereviewer.backend.service.LambdaCalculatorService;
import com.javacodereviewer.backend.service.LambdaCalculatorService.CalculatorResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for AWS Lambda Calculator operations
 */
@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class CalculatorController {
    
    @Autowired
    private LambdaCalculatorService calculatorService;
    
    /**
     * Calculate using query parameters (GET method)
     */
    @GetMapping("/calc")
    public ResponseEntity<Object> calculateWithQuery(
            @RequestParam String operand1,
            @RequestParam String operand2,
            @RequestParam String operator) {
        try {
            // Validate operator
            if (!calculatorService.isValidOperator(operator)) {
                return ResponseEntity.badRequest().body(
                    createErrorResponse("Invalid operator: " + operator + ". Supported: +, -, *, /, add, sub, mul, div"));
            }
            
            CalculatorResult result = calculatorService.calculateWithQuery(operand1, operand2, operator);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Calculation failed: " + e.getMessage()));
        }
    }
    
    /**
     * Calculate using JSON body (POST method)
     */
    @PostMapping("/calc")
    public ResponseEntity<Object> calculateWithPost(@RequestBody CalculationRequest request) {
        try {
            // Validate input
            if (request.getA() == null || request.getB() == null || request.getOp() == null) {
                return ResponseEntity.badRequest().body(
                    createErrorResponse("Missing required fields: a, b, op"));
            }
            
            // Validate operator
            if (!calculatorService.isValidOperator(request.getOp())) {
                return ResponseEntity.badRequest().body(
                    createErrorResponse("Invalid operator: " + request.getOp() + ". Supported: +, -, *, /, add, sub, mul, div"));
            }
            
            CalculatorResult result = calculatorService.calculateWithPost(
                request.getA(), request.getB(), request.getOp());
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Calculation failed: " + e.getMessage()));
        }
    }
    
    /**
     * Calculate using path parameters
     */
    @GetMapping("/calc/{operand1}/{operand2}/{operator}")
    public ResponseEntity<Object> calculateWithPath(
            @PathVariable String operand1,
            @PathVariable String operand2,
            @PathVariable String operator) {
        try {
            // Validate operator
            String normalizedOperator = calculatorService.normalizeOperator(operator);
            if (!calculatorService.isValidOperator(normalizedOperator) && !calculatorService.isValidOperator(operator)) {
                return ResponseEntity.badRequest().body(
                    createErrorResponse("Invalid operator: " + operator + ". Supported: +, -, *, /, add, sub, mul, div"));
            }
            
            CalculatorResult result = calculatorService.calculateWithPath(operand1, operand2, operator);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Calculation failed: " + e.getMessage()));
        }
    }
    
    /**
     * Get supported operations
     */
    @GetMapping("/operations")
    public ResponseEntity<Map<String, Object>> getSupportedOperations() {
        Map<String, Object> operations = new HashMap<>();
        operations.put("operators", new String[]{"+", "-", "*", "/", "add", "sub", "mul", "div"});
        operations.put("methods", new String[]{"GET /calc?operand1=1&operand2=2&operator=+", 
                                              "POST /calc with JSON body", 
                                              "GET /calc/{operand1}/{operand2}/{operator}"});
        operations.put("description", "AWS Lambda-powered calculator with multiple input methods");
        return ResponseEntity.ok(operations);
    }
    
    /**
     * Health check for Lambda service
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        try {
            // Test with a simple calculation
            CalculatorResult testResult = calculatorService.calculateWithQuery("1", "1", "+");
            health.put("status", "UP");
            health.put("service", "AWS Lambda Calculator");
            health.put("testResult", testResult != null ? "PASS" : "FAIL");
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            health.put("status", "DOWN");
            health.put("service", "AWS Lambda Calculator");
            health.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(health);
        }
    }
    
    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        error.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return error;
    }
    
    /**
     * Request class for POST calculations
     */
    public static class CalculationRequest {
        private Double a;
        private Double b;
        private String op;
        
        public Double getA() { return a; }
        public void setA(Double a) { this.a = a; }
        
        public Double getB() { return b; }
        public void setB(Double b) { this.b = b; }
        
        public String getOp() { return op; }
        public void setOp(String op) { this.op = op; }
    }
}