package com.app.review.model.dto;

import lombok.Data;

@Data
public class ReviewReactionRequestDto {
    private Long reviewId;
    private Long userId;
}
