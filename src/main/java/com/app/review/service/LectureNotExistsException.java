package com.app.review.service;

public class LectureNotExistsException extends RuntimeException {
    public LectureNotExistsException(String message) {
        super(message);
    }
}
