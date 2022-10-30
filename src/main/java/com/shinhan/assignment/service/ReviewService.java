package com.shinhan.assignment.service;

import com.shinhan.assignment.model.dto.ReviewModifyRequestDto;
import com.shinhan.assignment.model.dto.ReviewSearchRequestDto;
import com.shinhan.assignment.model.dto.ReviewWriteRequestDto;
import com.shinhan.assignment.model.entity.Review;
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
