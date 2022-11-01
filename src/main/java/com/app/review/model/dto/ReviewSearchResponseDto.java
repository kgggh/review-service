package com.app.review.model.dto;

import com.app.review.model.entity.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewSearchResponseDto {
    private Long reviewId;
    private String content;
    private int grade;
    private Long userId;
    private int totalReaction;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/seoul")
    private LocalDateTime createdDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/seoul")
    private LocalDateTime modifiedDateTime;

    public ReviewSearchResponseDto(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.grade = review.getGrade();
        this.userId = review.getUser().getId();
        this.createdDateTime = review.getCreatedDateTime();
        this.modifiedDateTime = review.getModifiedDateTime();
        this.totalReaction = review.getTotalRecommended();
    }
}
