package com.shinhan.assignment.service.impl;

import com.shinhan.assignment.model.dto.ReviewReactionRequestDto;

import com.shinhan.assignment.model.entity.Review;
import com.shinhan.assignment.model.entity.ReviewReaction;
import com.shinhan.assignment.model.entity.User;
import com.shinhan.assignment.repository.ReviewReactionRepository;
import com.shinhan.assignment.service.ReviewReactionService;
import com.shinhan.assignment.service.ReviewService;
import com.shinhan.assignment.service.UserService;
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
