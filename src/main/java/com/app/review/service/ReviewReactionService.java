package com.app.review.service;

import com.app.review.model.dto.ReviewReactionRequestDto;

public interface ReviewReactionService {
    void reaction(ReviewReactionRequestDto reviewReactionRequestDto);
}
