package com.app.review.service;

import com.app.review.model.dto.ReviewModifyRequestDto;
import com.app.review.model.entity.Review;
import com.app.review.model.dto.ReviewSearchRequestDto;
import com.app.review.model.dto.ReviewWriteRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface ReviewService {
    Long writeReview(@Valid ReviewWriteRequestDto reviewWriteRequestDto);
    void removeReview(Long reviewId);
    void modifyReview(Long reviewId, @Valid ReviewModifyRequestDto modifyRequestDto);
    Review getReview(Long reviewId);
    Page<Review> searchReview(ReviewSearchRequestDto reviewSearchRequestDto);
}
