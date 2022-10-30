package com.shinhan.assignment.service;

public class ReviewNotExistsException extends RuntimeException {
    public ReviewNotExistsException(String message) {
        super(message);
    }
}
