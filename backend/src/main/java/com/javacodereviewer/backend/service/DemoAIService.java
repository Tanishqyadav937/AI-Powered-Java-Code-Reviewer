package com.javacodereviewer.backend.service;

import com.javacodereviewer.backend.model.CodeReviewResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Demo AI Service that provides realistic code review responses without requiring API keys.
 * This is perfect for development, testing, and demonstrations.
 */
@Service
public class DemoAIService {
    
    private final Random random = new Random();
    
    /**
     * Provides a demo code review with realistic feedback based on code analysis
     */
    public CodeReviewResponse reviewCodeDemo(String code, String provider, String fileName) {
        CodeReviewResponse response = new CodeReviewResponse();
        response.setAiProvider(provider + " (Demo Mode)");
        response.setFileName(fileName);
        response.setSuccess(true);
        
        // Analyze the code to provide realistic feedback
        List<String> errors = analyzeForErrors(code);
        List<String> warnings = analyzeForWarnings(code);
        List<String> suggestions = analyzeForSuggestions(code);
        List<String> goodPractices = analyzeGoodPractices(code);
        
        response.setErrors(errors);
        response.setWarnings(warnings);
        response.setSuggestions(suggestions);
        response.setGoodPractices(goodPractices);
        
        // Generate a comprehensive summary
        String summary = generateSummary(code, errors, warnings, suggestions, goodPractices);
        response.setSummary(summary);
        
        response.calculateTotalIssues();
        return response;
    }
    
    private List<String> analyzeForErrors(String code) {
        List<String> errors = new ArrayList<>();
        
        // Check for common Java errors
        if (code.contains("System.out.println") && !code.contains("import java.lang.System")) {
            // This is actually fine, but let's simulate real analysis
        }
        
        if (code.contains("null;") || code.contains("= null") && !code.contains("!=")) {
            errors.add("Potential null pointer risk detected. Consider null checks before using objects.");
        }
        
        if (code.contains("catch(Exception e)") && !code.contains("e.printStackTrace") && !code.contains("log")) {
            errors.add("Empty or inadequate exception handling. Consider proper logging or handling of exceptions.");
        }
        
        if (code.contains("String ") && code.contains("==")) {
            errors.add("String comparison using '==' instead of '.equals()'. This can cause unexpected behavior.");
        }
        
        return errors;
    }
    
    private List<String> analyzeForWarnings(String code) {
        List<String> warnings = new ArrayList<>();
        
        if (code.contains("public class") && !code.contains("@")) {
            warnings.add("Consider adding appropriate annotations like @Component, @Service, or @Controller if this is a Spring component.");
        }
        
        if (code.contains("System.out.println")) {
            warnings.add("Direct console output detected. Consider using a proper logging framework like SLF4J or Logback.");
        }
        
        if (code.contains("Thread.sleep")) {
            warnings.add("Thread.sleep() usage detected. Consider using proper concurrency utilities or async patterns.");
        }
        
        if (code.length() > 1000 && !code.contains("//") && !code.contains("/**")) {
            warnings.add("Large code block with minimal comments. Consider adding documentation for better maintainability.");
        }
        
        if (code.contains("public ") && code.contains("static ") && !code.contains("main")) {
            warnings.add("Static method detected. Ensure this is intentional as it may impact testability.");
        }
        
        return warnings;
    }
    
    private List<String> analyzeForSuggestions(String code) {
        List<String> suggestions = new ArrayList<>();
        
        if (code.contains("ArrayList") && !code.contains("List<")) {
            suggestions.add("Consider using List<T> interface instead of ArrayList for better flexibility and maintainability.");
        }
        
        if (code.contains("for(") && !code.contains("enhanced for")) {
            suggestions.add("Consider using enhanced for loops (for-each) where possible for better readability.");
        }
        
        if (code.contains("if") && code.contains("return") && !code.contains("else")) {
            suggestions.add("Consider using early returns or guard clauses to reduce nesting and improve readability.");
        }
        
        if (!code.contains("final") && code.contains("private")) {
            suggestions.add("Consider making private fields final where appropriate to improve immutability.");
        }
        
        if (code.contains("String") && code.contains("StringBuilder")) {
            suggestions.add("Good use of StringBuilder for string manipulation. This improves performance over string concatenation.");
        }
        
        // Add some random helpful suggestions
        String[] generalSuggestions = {
            "Consider adding input validation to improve robustness.",
            "Think about extracting long methods into smaller, more focused methods.",
            "Consider implementing proper equals() and hashCode() methods if this class will be used in collections.",
            "Add unit tests to ensure code reliability and facilitate refactoring.",
            "Consider using dependency injection for better testability and modularity."
        };
        
        if (random.nextBoolean()) {
            suggestions.add(generalSuggestions[random.nextInt(generalSuggestions.length)]);
        }
        
        return suggestions;
    }
    
    private List<String> analyzeGoodPractices(String code) {
        List<String> goodPractices = new ArrayList<>();
        
        if (code.contains("private ")) {
            goodPractices.add("Good use of private access modifiers for encapsulation.");
        }
        
        if (code.contains("@Override")) {
            goodPractices.add("Proper use of @Override annotation improves code reliability.");
        }
        
        if (code.contains("try") && code.contains("catch")) {
            goodPractices.add("Appropriate exception handling implemented.");
        }
        
        if (code.contains("/**") || code.contains("//")) {
            goodPractices.add("Code documentation present, which aids maintainability.");
        }
        
        if (code.contains("final ")) {
            goodPractices.add("Good use of final keyword for immutability.");
        }
        
        if (code.contains("List<") || code.contains("Map<") || code.contains("Set<")) {
            goodPractices.add("Proper use of generics for type safety.");
        }
        
        if (code.contains("public class") && Character.isUpperCase(code.charAt(code.indexOf("public class") + 13))) {
            goodPractices.add("Class naming follows Java conventions (PascalCase).");
        }
        
        return goodPractices;
    }
    
    private String generateSummary(String code, List<String> errors, List<String> warnings, List<String> suggestions, List<String> goodPractices) {
        StringBuilder summary = new StringBuilder();
        
        // Analyze code complexity
        int lines = code.split("\n").length;
        String complexity = lines < 20 ? "low" : lines < 50 ? "moderate" : "high";
        
        summary.append("📋 **Code Review Summary**\n\n");
        summary.append(String.format("**File Analysis:** %d lines of code with %s complexity\n\n", lines, complexity));
        
        if (errors.isEmpty() && warnings.isEmpty()) {
            summary.append("✅ **Overall Assessment:** Excellent! No critical issues found.\n\n");
        } else if (errors.isEmpty()) {
            summary.append("⚠️ **Overall Assessment:** Good code with minor areas for improvement.\n\n");
        } else {
            summary.append("🔍 **Overall Assessment:** Code requires attention to resolve identified issues.\n\n");
        }
        
        if (!errors.isEmpty()) {
            summary.append(String.format("❌ **Critical Issues:** %d error(s) found that need immediate attention.\n", errors.size()));
        }
        
        if (!warnings.isEmpty()) {
            summary.append(String.format("⚠️ **Warnings:** %d warning(s) identified for potential improvement.\n", warnings.size()));
        }
        
        if (!suggestions.isEmpty()) {
            summary.append(String.format("💡 **Suggestions:** %d improvement suggestion(s) provided.\n", suggestions.size()));
        }
        
        if (!goodPractices.isEmpty()) {
            summary.append(String.format("✨ **Good Practices:** %d positive practice(s) identified.\n", goodPractices.size()));
        }
        
        summary.append("\n📊 **Recommendation:** ");
        if (errors.size() > 2) {
            summary.append("Focus on resolving critical errors first, then address warnings and suggestions.");
        } else if (warnings.size() > 3) {
            summary.append("Consider addressing the warnings to improve code quality and maintainability.");
        } else {
            summary.append("Code is in good shape! Consider implementing the suggestions for even better quality.");
        }
        
        summary.append("\n\n💻 **Note:** This analysis was generated in demo mode. ");
        summary.append("For more detailed AI-powered reviews, configure your preferred AI provider API keys.");
        
        return summary.toString();
    }
}