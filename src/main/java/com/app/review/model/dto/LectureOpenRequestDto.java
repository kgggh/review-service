package com.app.review.model.dto;

import com.app.review.model.entity.CoursedDeadline;
import com.app.review.model.entity.Difficulty;
import lombok.Data;

@Data
public class LectureOpenRequestDto {
    private String title;
    private String instructorName;
    private Difficulty difficulty;
    private CoursedDeadline coursedDeadline;
}
