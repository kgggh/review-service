package com.app.review.repository.custom;

import com.app.review.model.dto.ReviewSearchRequestDto;
import com.app.review.model.entity.Review;
import org.springframework.data.domain.Page;

public interface ReviewCustomRepository {
    Page<Review> findAll(ReviewSearchRequestDto reviewSearchRequestDto);
}
