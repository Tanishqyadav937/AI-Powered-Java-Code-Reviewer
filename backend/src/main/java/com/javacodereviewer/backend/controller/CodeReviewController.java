package com.javacodereviewer.backend.controller;

import com.javacodereviewer.backend.entity.CodeReview;
import com.javacodereviewer.backend.model.CodeReviewRequest;
import com.javacodereviewer.backend.model.CodeReviewResponse;
import com.javacodereviewer.backend.repository.CodeReviewRepository;
import com.javacodereviewer.backend.service.AIReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

/**
 * REST Controller for code review operations
 */
@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class CodeReviewController {
    
    @Autowired
    private AIReviewService aiReviewService;
    
    @Autowired
    private CodeReviewRepository codeReviewRepository;
    
    /**
     * Review Java code using AI
     */
    @PostMapping("/review")
    public ResponseEntity<CodeReviewResponse> reviewCode(@Valid @RequestBody CodeReviewRequest request) {
        try {
            // Perform AI review
            CodeReviewResponse response = aiReviewService.reviewCode(
                request.getCode(), 
                request.getAiProvider(), 
                request.getFileName()
            );
            
            // Save to database
            CodeReview review = new CodeReview();
            review.setCodeContent(request.getCode());
            review.setSummary(response.getSummary());
            review.setErrors(response.getErrors());
            review.setWarnings(response.getWarnings());
            review.setSuggestions(response.getSuggestions());
            review.setGoodPractices(response.getGoodPractices());
            review.setAiProvider(request.getAiProvider());
            review.setFileName(request.getFileName());
            review.setTotalIssues(response.getTotalIssues());
            
            CodeReview savedReview = codeReviewRepository.save(review);
            response.setId(savedReview.getId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            CodeReviewResponse errorResponse = new CodeReviewResponse();
            errorResponse.setSuccess(false);
            errorResponse.setErrorMessage("Error during code review: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Get all reviews
     */
    @GetMapping
    public ResponseEntity<List<CodeReviewResponse>> getAllReviews() {
        List<CodeReview> reviews = codeReviewRepository.findAll();
        List<CodeReviewResponse> responses = reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Get review by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CodeReviewResponse> getReviewById(@PathVariable Long id) {
        Optional<CodeReview> review = codeReviewRepository.findById(id);
        if (review.isPresent()) {
            return ResponseEntity.ok(convertToResponse(review.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get recent reviews
     */
    @GetMapping("/recent")
    public ResponseEntity<List<CodeReviewResponse>> getRecentReviews() {
        List<CodeReview> reviews = codeReviewRepository.findRecentReviews();
        List<CodeReviewResponse> responses = reviews.stream()
                .limit(10) // Limit to 10 most recent
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Get reviews by AI provider
     */
    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<CodeReviewResponse>> getReviewsByProvider(@PathVariable String provider) {
        List<CodeReview> reviews = codeReviewRepository.findByAiProvider(provider);
        List<CodeReviewResponse> responses = reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Search reviews by summary
     */
    @GetMapping("/search")
    public ResponseEntity<List<CodeReviewResponse>> searchReviews(@RequestParam String keyword) {
        List<CodeReview> reviews = codeReviewRepository.searchBySummary(keyword);
        List<CodeReviewResponse> responses = reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Delete review by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        if (codeReviewRepository.existsById(id)) {
            codeReviewRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get available AI providers
     */
    @GetMapping("/providers")
    public ResponseEntity<List<String>> getAvailableProviders() {
        List<String> providers = List.of(
            "OpenAI GPT-4"
        );
        return ResponseEntity.ok(providers);
    }
    
    /**
     * Get statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Object> getStatistics() {
        long totalReviews = codeReviewRepository.count();
        List<Object[]> providerStats = codeReviewRepository.getStatisticsByProvider();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalReviews", totalReviews);
        stats.put("providerStatistics", providerStats);
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Convert CodeReview entity to CodeReviewResponse
     */
    private CodeReviewResponse convertToResponse(CodeReview review) {
        CodeReviewResponse response = new CodeReviewResponse();
        response.setId(review.getId());
        response.setSummary(review.getSummary());
        response.setErrors(review.getErrors());
        response.setWarnings(review.getWarnings());
        response.setSuggestions(review.getSuggestions());
        response.setGoodPractices(review.getGoodPractices());
        response.setAiProvider(review.getAiProvider());
        response.setFileName(review.getFileName());
        response.setReviewTime(review.getReviewTime());
        response.setTotalIssues(review.getTotalIssues());
        response.setSuccess(true);
        return response;
    }
}
