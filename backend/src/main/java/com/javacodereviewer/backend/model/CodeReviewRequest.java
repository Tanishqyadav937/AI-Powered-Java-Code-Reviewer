package com.javacodereviewer.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request model for code review
 */
public class CodeReviewRequest {
    
    @NotBlank(message = "Code content is required")
    @Size(max = 10000, message = "Code content must not exceed 10000 characters")
    private String code;
    
    @NotBlank(message = "AI provider is required")
    private String aiProvider;
    
    private String fileName;
    
    // Constructors
    public CodeReviewRequest() {}
    
    public CodeReviewRequest(String code, String aiProvider, String fileName) {
        this.code = code;
        this.aiProvider = aiProvider;
        this.fileName = fileName;
    }
    
    // Getters and Setters
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
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
}
