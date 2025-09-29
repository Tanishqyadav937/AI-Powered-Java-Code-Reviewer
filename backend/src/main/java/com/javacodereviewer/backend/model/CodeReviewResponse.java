package com.javacodereviewer.backend.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response model for code review results
 */
public class CodeReviewResponse {
    
    private Long id;
    private String summary;
    private List<String> errors;
    private List<String> warnings;
    private List<String> suggestions;
    private List<String> goodPractices;
    private String aiProvider;
    private String fileName;
    private LocalDateTime reviewTime;
    private int totalIssues;
    private boolean success;
    private String errorMessage;
    
    // Constructors
    public CodeReviewResponse() {
        this.reviewTime = LocalDateTime.now();
    }
    
    public CodeReviewResponse(String summary, String aiProvider, String fileName) {
        this();
        this.summary = summary;
        this.aiProvider = aiProvider;
        this.fileName = fileName;
        this.success = true;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public List<String> getErrors() {
        return errors;
    }
    
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
    public List<String> getWarnings() {
        return warnings;
    }
    
    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
    
    public List<String> getSuggestions() {
        return suggestions;
    }
    
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
    
    public List<String> getGoodPractices() {
        return goodPractices;
    }
    
    public void setGoodPractices(List<String> goodPractices) {
        this.goodPractices = goodPractices;
    }
    
    public String getAiProvider() {
        return aiProvider;
    }
    
    public void setAiProvider(String aiProvider) {
        this.aiProvider = aiProvider;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public LocalDateTime getReviewTime() {
        return reviewTime;
    }
    
    public void setReviewTime(LocalDateTime reviewTime) {
        this.reviewTime = reviewTime;
    }
    
    public int getTotalIssues() {
        return totalIssues;
    }
    
    public void setTotalIssues(int totalIssues) {
        this.totalIssues = totalIssues;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    // Helper methods
    public void calculateTotalIssues() {
        int total = 0;
        if (errors != null) total += errors.size();
        if (warnings != null) total += warnings.size();
        if (suggestions != null) total += suggestions.size();
        this.totalIssues = total;
    }
}
