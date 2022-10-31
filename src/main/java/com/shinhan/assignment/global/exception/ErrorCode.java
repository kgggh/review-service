package com.shinhan.assignment.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /* common */
    NOT_FOUND(400004, 404, "Not found resource"),
    BAD_REQUEST(400000, 400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500000, 500, "Internal server error"),

    /* lecture */
    NOT_EXIST_LECTURE(410004, 404,""),

    /* review */
    NOT_EXIST_REVIEW(420004, 404,""),

    /* user */
    NOT_EXIST_USER(430014, 404,""),
    EXIST_USER(430020, 400,"");

    private int code;
    private int status;
    private String reason;

    ErrorCode(int code, int status, String reason) {
        this.code = code;
        this.status = status;
        this.reason = reason;
    }
}
