package com.app.review.model.entity;

import lombok.Getter;

@Getter
public enum Difficulty {
    BEGINNER("초급"),
    INTERMEDIATE("중급"),
    ADVANCED("고급");

    private String description;

    Difficulty(String description) {
        this.description = description;
    }
}
