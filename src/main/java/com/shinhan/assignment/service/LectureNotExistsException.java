package com.shinhan.assignment.service;

public class LectureNotExistsException extends RuntimeException {
    public LectureNotExistsException(String message) {
        super(message);
    }
}
