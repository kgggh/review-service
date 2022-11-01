package com.app.review.controller;

import com.app.review.global.ResponseVO;
import com.app.review.model.dto.*;
import com.app.review.model.entity.Review;
import com.app.review.service.ReviewReactionService;
import com.app.review.service.ReviewService;
import com.app.review.model.dto.base.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewReactionService reviewReactionService;

    @GetMapping(path = "/api/v1/reviews")
    public ResponseEntity<Object> getReview(@ParameterObject ReviewSearchRequestDto reviewSearchRequestDto) {
        Page<Review> reviews = reviewService.searchReview(reviewSearchRequestDto);
        SearchResponse<ReviewResponseDto> result = new SearchResponse<>(reviews.map(ReviewResponseDto::new));
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/api/v1/reviews/{review_id}")
    public ResponseEntity<Object> getReviews(@PathVariable("review_id") Long reviewId) {
        Review review = reviewService.getReview(reviewId);
        ReviewResponseDto result = new ReviewResponseDto(review);
        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/api/v1/reviews")
    public ResponseEntity<Void> writeReview(@RequestBody ReviewWriteRequestDto reviewWriteRequestDto) {
        Long reviewId = reviewService.writeReview(reviewWriteRequestDto);
        return ResponseEntity.created(URI.create(String.format("/api/v1/reviews/%d", reviewId)))
                .build();
    }

    @PatchMapping(path = "/api/v1/reviews/{review_id}")
    public ResponseEntity<Object> modifyReview(@PathVariable("review_id") Long reviewId,
                                               @RequestBody ReviewModifyRequestDto reviewModifyRequestDto) {
        reviewService.modifyReview(reviewId, reviewModifyRequestDto);
        return ResponseEntity.ok(ResponseVO.success());
    }

    @DeleteMapping(path = "/api/v1/reviews/{review_id}")
    public ResponseEntity<Void> removeReview(@PathVariable("review_id") Long reviewId) {
        reviewService.removeReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/api/v1/review-reactions")
    public ResponseEntity<Object> reactionToReview(@RequestBody ReviewReactionRequestDto reviewReactionRequestDto) {
        reviewReactionService.reaction(reviewReactionRequestDto);
        return ResponseEntity.ok(ResponseVO.success());
    }
}
