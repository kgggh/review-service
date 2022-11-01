package com.app.review.service.impl;

import com.app.review.model.dto.ReviewReactionRequestDto;
import com.app.review.model.entity.Review;
import com.app.review.model.entity.ReviewReaction;
import com.app.review.model.entity.User;
import com.app.review.repository.ReviewReactionRepository;
import com.app.review.service.UserService;

import com.app.review.service.ReviewReactionService;
import com.app.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewReactionServiceImpl implements ReviewReactionService {
    private final ReviewService reviewService;
    private final UserService userService;
    private final ReviewReactionRepository reviewReactionRepository;

    @Transactional
    @Override
    public void reaction(ReviewReactionRequestDto reviewReactionRequestDto) {
        User user = userService.getUser(reviewReactionRequestDto.getUserId());
        Review review = reviewService.getReview(reviewReactionRequestDto.getReviewId());
        ReviewReaction reviewReaction = ReviewReaction.createReviewReaction(user, review);

        reviewReactionRepository.findByReviewAndUser(review, user)
                .ifPresentOrElse(
                        reviewReactionRepository::delete,
                        () -> reviewReactionRepository.save(reviewReaction)
                );
    }
}
