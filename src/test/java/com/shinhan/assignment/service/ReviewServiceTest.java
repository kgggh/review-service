package com.shinhan.assignment.service;

import com.shinhan.assignment.model.dto.ReviewModifyRequestDto;
import com.shinhan.assignment.model.dto.ReviewReactionRequestDto;
import com.shinhan.assignment.model.dto.ReviewSearchRequestDto;
import com.shinhan.assignment.model.dto.ReviewWriteRequestDto;
import com.shinhan.assignment.model.entity.*;
import com.shinhan.assignment.repository.LectureRepository;
import com.shinhan.assignment.repository.ReviewRepository;
import com.shinhan.assignment.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReviewServiceTest {
    @Autowired ReviewService reviewService;
    @Autowired ReviewRepository reviewRepository;
    @Autowired UserRepository userRepository;
    @Autowired LectureRepository lectureRepository;
    @Autowired ReviewReactionService reviewReactionService;

    @Test
    void 리뷰작성() {
        //given
        String content = "제목";
        int grade = 2;
        User user = userRepository.save(User.createUser("사용자", "localhost:8888/profile"));
        Lecture lecture = lectureRepository.save(lectureRepository.save(
                Lecture.createLecture("스프링강의", "김아무개", Difficulty.BEGINNER, CoursedDeadline.LIMITLESS)));

        ReviewWriteRequestDto reviewWriteRequestDto = new ReviewWriteRequestDto();
        reviewWriteRequestDto.setContent(content);
        reviewWriteRequestDto.setGrade(grade);
        reviewWriteRequestDto.setLectureId(lecture.getId());
        reviewWriteRequestDto.setUserId(user.getId());

        //when
        Long reviewId = reviewService.writeReview(reviewWriteRequestDto);
        Review review = reviewRepository.findById(reviewId).get();

        //then
        assertEquals(review.getId(), reviewRepository.getReferenceById(review.getId()).getId());
        assertEquals(review.getGrade(), 2);

    }

    @Test
    void 리뷰작성시_본문_1000자_이상일시_작성실패() {
        //given
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 1001; i++) {
            content.append("a");
        }
        int grade = 2;
        String errorMessage = "길이 1000자까지";
        User user = userRepository.save(User.createUser("사용자", "localhost:8888/profile"));
        Lecture lecture = lectureRepository.save(lectureRepository.save(
                Lecture.createLecture("스프링강의", "김아무개", Difficulty.BEGINNER, CoursedDeadline.LIMITLESS)));

        ReviewWriteRequestDto reviewWriteRequestDto = new ReviewWriteRequestDto();
        reviewWriteRequestDto.setContent(content.toString());
        reviewWriteRequestDto.setGrade(grade);
        reviewWriteRequestDto.setLectureId(lecture.getId());
        reviewWriteRequestDto.setUserId(user.getId());

        //when
        ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> reviewService.writeReview(reviewWriteRequestDto));

        //then
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void 리뷰작성시_부여할수있는_평점영역에서_벗어날시_작성실패() {
        //given
        String content = "본문";
        int grade = 6;
        String errorMessage = "양수 1 ~ 5까지";
        User user = userRepository.save(User.createUser("사용자", "localhost:8888/profile"));
        Lecture lecture = lectureRepository.save(lectureRepository.save(
                Lecture.createLecture("스프링강의", "김아무개", Difficulty.BEGINNER, CoursedDeadline.LIMITLESS)));

        ReviewWriteRequestDto reviewWriteRequestDto = new ReviewWriteRequestDto();
        reviewWriteRequestDto.setContent(content);
        reviewWriteRequestDto.setGrade(grade);
        reviewWriteRequestDto.setLectureId(user.getId());
        reviewWriteRequestDto.setUserId(lecture.getId());

        //when
        ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> reviewService.writeReview(reviewWriteRequestDto));

        //then
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void 리뷰작성시_사용자정보가_없을시_작성실패() {
        //given
        String content = "본문";
        int grade = 5;
        Long notExistUserId = 100L;
        userRepository.save(User.createUser("사용자", "localhost:8888/profile"));
        ReviewWriteRequestDto reviewWriteRequestDto = new ReviewWriteRequestDto();
        reviewWriteRequestDto.setContent(content);
        reviewWriteRequestDto.setGrade(grade);
        reviewWriteRequestDto.setLectureId(1L);
        reviewWriteRequestDto.setUserId(notExistUserId);

        //when
        UserNotExistsException exception =
                assertThrows(UserNotExistsException.class, () -> reviewService.writeReview(reviewWriteRequestDto));

        //then
        assertNotNull(exception);
    }

    @Test
    void 리뷰작성시_강의정보가_없을시_작성실패() {
        //given
        String content = "본문";
        int grade = 5;
        Long notExistLecture = 1000L;
        User user = userRepository.save(User.createUser("사용자", "localhost:8888/profile"));
        ReviewWriteRequestDto reviewWriteRequestDto = new ReviewWriteRequestDto();
        reviewWriteRequestDto.setContent(content);
        reviewWriteRequestDto.setGrade(grade);
        reviewWriteRequestDto.setLectureId(notExistLecture);
        reviewWriteRequestDto.setUserId(user.getId());

        //when
        LectureNotExistsException exception =
                assertThrows(LectureNotExistsException.class, () -> reviewService.writeReview(reviewWriteRequestDto));

        //then
        assertNotNull(exception);
    }

    @Test
    void 리뷰삭제() {
        //given
        Review review = makeReview();

        //when
        reviewService.removeReview(review.getId());

        //then
        assertFalse(reviewRepository.findById(review.getId()).isPresent());

    }

    @Test
    void 리뷰삭제시_존재하지않은_리뷰삭제시_실패() {
        //given
        Long notExistReviewId = 1000L;

        //when
        ReviewNotExistsException exception =
                assertThrows(ReviewNotExistsException.class, () -> reviewService.removeReview(notExistReviewId));

        //then
        assertNotNull(exception);
    }

    @Test
    void 리뷰수정() {
        //given
        Review review = makeReview();
        String modifiedContent = "새롭게 내용을 채워보자";
        int modifiedGrade = 3;
        ReviewModifyRequestDto reviewModifyRequestDto = new ReviewModifyRequestDto();
        reviewModifyRequestDto.setContent(modifiedContent);
        reviewModifyRequestDto.setGrade(modifiedGrade);
        reviewModifyRequestDto.setUserId(1L);

        //when
        reviewService.modifyReview(review.getId(), reviewModifyRequestDto);

        //then
        Review modifiedReview = reviewRepository.findById(review.getId()).get();
        assertEquals(modifiedReview.getGrade(), modifiedGrade);
        assertEquals(modifiedReview.getContent(), modifiedContent);
    }

    @Test
    void 리뷰수정시_부여할수있는_평점영역에서_벗어날시_수정실패() {
        //given
        Review review = makeReview();
        String modifiedContent = "새롭게 내용을 채워보자";
        int modifiedGrade = 8;
        ReviewModifyRequestDto reviewModifyRequestDto = new ReviewModifyRequestDto();
        reviewModifyRequestDto.setContent(modifiedContent);
        reviewModifyRequestDto.setGrade(modifiedGrade);
        reviewModifyRequestDto.setUserId(1L);

        //when
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> reviewService.modifyReview(review.getId(), reviewModifyRequestDto));

        //then
        assertNotNull(exception);
    }

    @Test
    void 리뷰수정시_본문_1000자_이상일시_작성실패() {
        //given
        Review review = makeReview();
        StringBuilder modifiedContent = new StringBuilder();
        for (int i = 0; i < 1001; i++) {
            modifiedContent.append("a");
        }
        int modifiedGrade = 3;
        ReviewModifyRequestDto reviewModifyRequestDto = new ReviewModifyRequestDto();
        reviewModifyRequestDto.setContent(modifiedContent.toString());
        reviewModifyRequestDto.setGrade(modifiedGrade);
        reviewModifyRequestDto.setUserId(1L);

        //when
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () -> reviewService.modifyReview(review.getId(), reviewModifyRequestDto));

        //then
        assertNotNull(exception);
    }

    @Test
    void 리뷰_단건조회() {
        //given
        Review review = makeReview();
        String content = "제목";

        //when
        Review result = reviewService.getReview(review.getId());

        //then
        assertEquals(review.getId(), result.getId());
        assertEquals(review.getContent(), content);

    }

    @Test
    void 리뷰_단건조회시_작성된_리뷰가_없을시_조회실패() {
        //given
        Long notExistReviewId = 1000L;

        //when
        ReviewNotExistsException exception =
                assertThrows(ReviewNotExistsException.class, () -> reviewService.getReview(notExistReviewId));

        //then
        assertNotNull(exception);
    }

    private void makeDummy() {
        int grade = 1;
        for (int i = 65; i < 80; i++) {
            String content = "제목"+Character.toString(i);
            if(grade == 6) {
                grade = 1;
            }
            User user =
                    userRepository.save(User.createUser("사용"+Character.toString(i),
                            "localhost:8888/profile"));
            Lecture lecture = lectureRepository.save(
                    Lecture.createLecture("스프링강의"+Character.toString(i),"김아무개",
                            Difficulty.BEGINNER, CoursedDeadline.LIMITLESS)
            );
            Review review = Review.createReview(content, grade, user, lecture);
            reviewRepository.save(review);
            grade++;
        }

        for (int i = 1; i < 10; i++) {
            ReviewReactionRequestDto reviewReactionRequestDto = new ReviewReactionRequestDto();
            reviewReactionRequestDto.setReviewId((long)i);
            reviewReactionRequestDto.setUserId((long)i);
            reviewReactionService.reaction(reviewReactionRequestDto);
        }

        for (int i = 1; i < 10; i++) {
            ReviewReactionRequestDto reviewReactionRequestDto = new ReviewReactionRequestDto();
            reviewReactionRequestDto.setReviewId((long)i);
            reviewReactionRequestDto.setUserId((long)i);
            reviewReactionService.reaction(reviewReactionRequestDto);
        }
    }

    @Disabled
    @Test
    void 리뷰_전체조회시_정렬할_파라미터가없으면_좋아요_개수가_많은순으로_정렬된다() {
        //given
        makeDummy();
        ReviewSearchRequestDto searchRequestDto = new ReviewSearchRequestDto();

        //when
        Page<Review> result = reviewRepository.findAll(searchRequestDto);

        //then
        assertTrue(result.getContent().get(0).getTotalRecommended() > result.getContent().get(1).getTotalRecommended() );
        assertTrue(result.getContent().get(0).getTotalRecommended() > result.getContent().get(2).getTotalRecommended() );
        assertTrue(result.getContent().get(0).getTotalRecommended() > result.getContent().get(3).getTotalRecommended() );

    }

    @Test
    void 리뷰_전체조회시_높은평점순으로_정렬된다() {
        //given
        makeDummy();
        ReviewSearchRequestDto searchRequestDto = new ReviewSearchRequestDto();
        searchRequestDto.setSort("grade,DESC");

        //when
        Page<Review> result = reviewRepository.findAll(searchRequestDto);

        //then
        assertEquals(result.getContent().get(0).getGrade(), 5);
    }

    @Test
    void 리뷰_전체조회시_낮은평점순으로_정렬된다() {
        //given
        makeDummy();
        ReviewSearchRequestDto searchRequestDto = new ReviewSearchRequestDto();
        searchRequestDto.setSort("grade,ASC");

        //when
        Page<Review> result = reviewRepository.findAll(searchRequestDto);

        //then
        assertEquals(result.getContent().get(0).getGrade(), 1);
    }

    private Review makeReview() {
        String content = "제목";
        int grade = 2;
        User user = userRepository.save(User.createUser("사용자", "localhost:8888/profile"));
        Lecture lecture = lectureRepository.save(
                Lecture.createLecture("스프링강의","김아무개", Difficulty.BEGINNER, CoursedDeadline.LIMITLESS)
        );
        Review review = Review.createReview(content, grade, user, lecture);
        return reviewRepository.save(review);
    }
}