package com.app.review.service;

import com.app.review.model.entity.Difficulty;
import com.app.review.model.entity.Lecture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class LectureServiceTest {
    @Autowired LectureService lectureService;

    @Test
    void 존재하는_강의를_가져온다() {
        //given
        Long lectureId = 1L; /* 미리 생성해둠.. */

        //when
        Lecture lecture = lectureService.getLecture(lectureId);

        //then
        assertNotNull(lecture);
        assertEquals(lectureId, 1L);
        assertEquals(lecture.getDifficulty(), Difficulty.BEGINNER);
    }

    @Test
    void 존재하지_않는_강의일시_에러를_발생() {
        //given
        Long lectureId = 99L;
        String errorMessage = String.format("The lecture of the corresponding id %d could not be found", lectureId);

        //when
        LectureNotExistsException exception = assertThrows(LectureNotExistsException.class, () -> {
            lectureService.getLecture(lectureId);
        });

        //then
        assertNotNull(exception);
        assertEquals(exception.getMessage(), errorMessage);
    }
}