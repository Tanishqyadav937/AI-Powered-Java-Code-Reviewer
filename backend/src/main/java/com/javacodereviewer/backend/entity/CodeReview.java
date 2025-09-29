package com.javacodereviewer.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA Entity for storing code review results
 */
@Entity
@Table(name = "code_reviews")
public class CodeReview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "code_content", columnDefinition = "TEXT")
    private String codeContent;
    
    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;
    
    @ElementCollection
    @CollectionTable(name = "review_errors", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "error_message", columnDefinition = "TEXT")
    private List<String> errors;
    
    @ElementCollection
    @CollectionTable(name = "review_warnings", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "warning_message", columnDefinition = "TEXT")
    private List<String> warnings;
    
    @ElementCollection
    @CollectionTable(name = "review_suggestions", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "suggestion_message", columnDefinition = "TEXT")
    private List<String> suggestions;
    
    @ElementCollection
    @CollectionTable(name = "review_good_practices", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "practice_message", columnDefinition = "TEXT")
    private List<String> goodPractices;
    
    @Column(name = "ai_provider")
    private String aiProvider;
    
    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "review_time")
    private LocalDateTime reviewTime;
    
    @Column(name = "total_issues")
    private Integer totalIssues;
    
    // Constructors
    public CodeReview() {
        this.reviewTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodeContent() {
        return codeContent;
    }
    
    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
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
    
    public Integer getTotalIssues() {
        return totalIssues;
    }
    
    public void setTotalIssues(Integer totalIssues) {
        this.totalIssues = totalIssues;
    }
}
