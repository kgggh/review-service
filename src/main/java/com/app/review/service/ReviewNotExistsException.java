package com.app.review.service;

public class ReviewNotExistsException extends RuntimeException {
    public ReviewNotExistsException(String message) {
        super(message);
    }
}
