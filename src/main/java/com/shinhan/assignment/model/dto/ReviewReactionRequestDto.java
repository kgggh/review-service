package com.shinhan.assignment.model.dto;

import lombok.Data;

@Data
public class ReviewReactionRequestDto {
    private Long reviewId;
    private Long userId;
}
