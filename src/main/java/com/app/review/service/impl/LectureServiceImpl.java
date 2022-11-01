package com.app.review.service.impl;

import com.app.review.model.dto.LectureOpenRequestDto;
import com.app.review.model.entity.CoursedDeadline;
import com.app.review.repository.LectureRepository;
import com.app.review.service.LectureNotExistsException;
import com.app.review.model.entity.Difficulty;
import com.app.review.model.entity.Lecture;
import com.app.review.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Service
public class LectureServiceImpl implements LectureService {
    private final LectureRepository lectureRepository;

    @Value("${dummy-data.enable}")
    private boolean enableDummyData = false;

    @PostConstruct
    void init() {
        if(enableDummyData) {
            String title = "스프링 부트로 리뷰 기능 개발하기";
            String instructorName = "김아무개";
            Difficulty difficulty = Difficulty.BEGINNER;
            CoursedDeadline coursedDeadline = CoursedDeadline.LIMITLESS;
            Lecture lecture = Lecture.createLecture(title, instructorName, difficulty, coursedDeadline);
            lectureRepository.save(lecture);
        }
    }

    @Override
    public Long openLecture(LectureOpenRequestDto lectureOpenRequestDto) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Lecture getLecture(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(
                () -> new LectureNotExistsException(
                        String.format("The lecture of the corresponding id %d could not be found", lectureId)));
    }
}
