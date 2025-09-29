package com.javacodereviewer.backend.repository;

import com.javacodereviewer.backend.entity.CodeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for CodeReview entity
 */
@Repository
public interface CodeReviewRepository extends JpaRepository<CodeReview, Long> {
    
    /**
     * Find reviews by AI provider
     */
    List<CodeReview> findByAiProvider(String aiProvider);
    
    /**
     * Find reviews by file name
     */
    List<CodeReview> findByFileNameContainingIgnoreCase(String fileName);
    
    /**
     * Find reviews created after a specific date
     */
    List<CodeReview> findByReviewTimeAfter(LocalDateTime dateTime);
    
    /**
     * Find reviews with issues count greater than specified
     */
    List<CodeReview> findByTotalIssuesGreaterThan(Integer issues);
    
    /**
     * Get recent reviews (last 10)
     */
    @Query("SELECT cr FROM CodeReview cr ORDER BY cr.reviewTime DESC")
    List<CodeReview> findRecentReviews();
    
    /**
     * Get statistics by AI provider
     */
    @Query("SELECT cr.aiProvider, COUNT(cr), AVG(cr.totalIssues) FROM CodeReview cr GROUP BY cr.aiProvider")
    List<Object[]> getStatisticsByProvider();
    
    /**
     * Search reviews by summary content
     */
    @Query("SELECT cr FROM CodeReview cr WHERE LOWER(cr.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CodeReview> searchBySummary(@Param("keyword") String keyword);
}
