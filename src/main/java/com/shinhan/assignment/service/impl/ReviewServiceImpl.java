package com.shinhan.assignment.service.impl;

import com.shinhan.assignment.model.dto.ReviewModifyRequestDto;
import com.shinhan.assignment.model.dto.ReviewSearchRequestDto;
import com.shinhan.assignment.model.dto.ReviewWriteRequestDto;
import com.shinhan.assignment.model.entity.Lecture;
import com.shinhan.assignment.model.entity.Review;
import com.shinhan.assignment.model.entity.User;
import com.shinhan.assignment.repository.ReviewRepository;
import com.shinhan.assignment.service.LectureService;
import com.shinhan.assignment.service.ReviewNotExistsException;
import com.shinhan.assignment.service.ReviewService;
import com.shinhan.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final LectureService lectureService;

    @Transactional
    @Override
    public Long writeReview(ReviewWriteRequestDto reviewWriteRequestDto) {
        User user = userService.getUser(reviewWriteRequestDto.getUserId());
        Lecture lecture = lectureService.getLecture(reviewWriteRequestDto.getLectureId());
        Review newReview = Review.createReview(
                reviewWriteRequestDto.getContent(),
                reviewWriteRequestDto.getGrade(),
                user,
                lecture
        );
        return reviewRepository.save(newReview).getId();
    }

    @Transactional
    @Override
    public void removeReview(Long reviewId) {
        Review review = getReview(reviewId);
        reviewRepository.deleteById(review.getId());
    }

    @Transactional
    @Override
    public void modifyReview(Long reviewId, ReviewModifyRequestDto modifyRequestDto) {
        Review review = getReview(reviewId);
        review.modify(modifyRequestDto.getContent(), modifyRequestDto.getGrade());
    }

    @Transactional(readOnly = true)
    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotExistsException(String.format("No such id %d review found ", reviewId)));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Review> searchReview(ReviewSearchRequestDto reviewSearchRequestDto) {
        return null;
    }
}
