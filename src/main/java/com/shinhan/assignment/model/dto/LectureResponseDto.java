package com.shinhan.assignment.model.dto;

import com.shinhan.assignment.model.entity.CoursedDeadline;
import com.shinhan.assignment.model.entity.Difficulty;
import lombok.Data;

@Data
public class LectureResponseDto {
    private String title;
    private String instructorName;
    private Difficulty difficulty;
    private CoursedDeadline coursedDeadline;
}
