package com.shinhan.assignment.service;

import com.shinhan.assignment.model.dto.LectureOpenRequestDto;
import com.shinhan.assignment.model.entity.Lecture;

public interface LectureService {
    Long openLecture(LectureOpenRequestDto lectureOpenRequestDto);
    Lecture getLecture(Long lectureId);
}
