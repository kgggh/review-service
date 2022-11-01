package com.app.review.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class ReviewModifyRequestDto {
    private Long userId;

    @Size(max = 1000, message = "길이 1000자까지")
    private String content;

    @Min(value = 1, message = "양수 1 ~ 5까지")
    @Max(value = 5, message = "양수 1 ~ 5까지")
    private int grade;
}
