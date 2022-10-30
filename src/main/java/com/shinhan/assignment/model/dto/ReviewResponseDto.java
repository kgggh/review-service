package com.shinhan.assignment.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shinhan.assignment.model.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private String content;
    private int grade;
    private Long userId;
    private int totalReaction;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/seoul")
    private LocalDateTime createdDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/seoul")
    private LocalDateTime modifiedDateTime;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.grade = review.getGrade();
        this.userId = review.getUser().getId();
        this.createdDateTime = review.getCreatedDateTime();
        this.modifiedDateTime = review.getModifiedDateTime();
        this.totalReaction = review.getTotalRecommended();
    }
}
