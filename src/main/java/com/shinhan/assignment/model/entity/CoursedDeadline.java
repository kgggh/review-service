package com.shinhan.assignment.model.entity;

import lombok.Getter;

@Getter
public enum CoursedDeadline {
    THREE_MONTH("3개월"),
    LIMITLESS("무제한");

    private String description;

    CoursedDeadline(String description) {
        this.description = description;
    }
}
