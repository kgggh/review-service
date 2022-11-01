package com.app.review.service;

import com.app.review.model.entity.*;
import com.app.review.repository.LectureRepository;
import com.app.review.repository.UserRepository;
import com.app.review.model.dto.ReviewReactionRequestDto;
import com.shinhan.assignment.model.entity.*;
import com.app.review.repository.ReviewReactionRepository;
import com.app.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ReviewReactionServiceTest {
    @Autowired ReviewReactionService reviewReactionService;
    @Autowired ReviewReactionRepository reviewReactionRepository;
    @Autowired ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LectureRepository lectureRepository;

    @BeforeAll
    void init() {
        int grade = 1;
        for (int i = 65; i < 80; i++) {
            String content = "제목" + Character.toString(i);
            if(grade == 6) {
                grade = 1;
            }
            User user =
                    userRepository.save(User.createUser("사용자" + Character.toString(i),
                            "localhost:8888/profile"));
            Lecture lecture = lectureRepository.save(
                    Lecture.createLecture("스프링강의" + Character.toString(i),"김아무개",
                            Difficulty.BEGINNER, CoursedDeadline.LIMITLESS)
            );
            Review review = Review.createReview(content, grade, user, lecture);
            reviewRepository.save(review);
            grade++;
        }

        for (int i = 1; i < 5; i++) {
            ReviewReactionRequestDto reviewReactionRequestDto = new ReviewReactionRequestDto();
            reviewReactionRequestDto.setReviewId((long) i);
            reviewReactionRequestDto.setUserId((long) i);
            reviewReactionService.reaction(reviewReactionRequestDto);
        }
    }


    @Test
    void 리뷰추천() {
        //given
        ReviewReactionRequestDto reviewReactionRequestDto = new ReviewReactionRequestDto();
        reviewReactionRequestDto.setUserId(10L);
        reviewReactionRequestDto.setReviewId(5L);

        //when
        assertDoesNotThrow(() ->reviewReactionService.reaction(reviewReactionRequestDto));

        //then
        List<ReviewReaction> result = reviewReactionRepository.findAll();
        assertEquals(result.size(), 5);
    }

    @Test
    void 리뷰_추천_취소() {
        //given
        ReviewReactionRequestDto reviewReactionRequestDto = new ReviewReactionRequestDto();
        reviewReactionRequestDto.setUserId(2L);
        reviewReactionRequestDto.setReviewId(2L);

        //when
        assertDoesNotThrow(() -> reviewReactionService.reaction(reviewReactionRequestDto));

        //then
        List<ReviewReaction> result = reviewReactionRepository.findAll();
        assertEquals(result.size(), 3);
    }
}