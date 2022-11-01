package com.app.review.service;

import com.app.review.model.dto.LectureOpenRequestDto;
import com.app.review.model.entity.Lecture;

public interface LectureService {
    Long openLecture(LectureOpenRequestDto lectureOpenRequestDto);
    Lecture getLecture(Long lectureId);
}
