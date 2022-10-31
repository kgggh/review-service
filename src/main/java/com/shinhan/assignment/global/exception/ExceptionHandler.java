package com.shinhan.assignment.global.exception;

import com.shinhan.assignment.global.ResponseVO;
import com.shinhan.assignment.service.LectureNotExistsException;
import com.shinhan.assignment.service.ReviewNotExistsException;
import com.shinhan.assignment.service.UserExistsException;
import com.shinhan.assignment.service.UserNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> exceptionHandler(final HttpMessageNotReadableException e) {
        log.error(String.format("[HttpMessageNotReadableException] message=%s", e.getMessage()));
        return ResponseEntity.badRequest()
                .body(ResponseVO.fail(ErrorCode.BAD_REQUEST, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UserNotExistsException.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final UserNotExistsException e) {
        log.error(String.format("[UserNotExistsException] message=%s", e.getMessage()));
        return ResponseEntity
                .status(ErrorCode.NOT_EXIST_USER.getStatus())
                .body(ResponseVO.fail(ErrorCode.NOT_EXIST_USER, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({UserExistsException.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final UserExistsException e) {
        log.error(String.format("[UserExistsException] message=%s", e.getMessage()));
        return ResponseEntity
                .status(ErrorCode.EXIST_USER.getStatus())
                .body(ResponseVO.fail(ErrorCode.EXIST_USER, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({LectureNotExistsException.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final LectureNotExistsException e) {
        log.error(String.format("[LectureNotExistsException] message=%s", e.getMessage()));
        return ResponseEntity
                .status(ErrorCode.NOT_EXIST_LECTURE.getStatus())
                .body(ResponseVO.fail(ErrorCode.NOT_EXIST_LECTURE, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ReviewNotExistsException.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final ReviewNotExistsException e) {
        log.error(String.format("[ReviewNotExistsException] message=%s", e.getMessage()));
        return ResponseEntity
                .status(ErrorCode.NOT_EXIST_REVIEW.getStatus())
                .body(ResponseVO.fail(ErrorCode.NOT_EXIST_REVIEW, e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> exceptionHandler(final ConstraintViolationException e) {
        log.error(String.format("[ConstraintViolationException] message=%s", e.getMessage()));
        Map<Object, Object> result = makeViolationResponse(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    private Map<Object, Object> makeViolationResponse(Exception e) {
        Map<Object, Object> result = new LinkedHashMap<>();
        result.put("message", "Fix the bad request parameter and try again...");
        result.put("code", ErrorCode.BAD_REQUEST.getCode());
        Map<String, Object> violationField = new LinkedHashMap<>();
        if( e instanceof ValidationException) {
            for (ConstraintViolation<?> c : ((ConstraintViolationException)e).getConstraintViolations()) {
                String field = ((PathImpl) c.getPropertyPath())
                        .getLeafNode().getName();
                violationField.put(field, c.getMessage());
            }
        } else {
            for (FieldError v : ((BindException)e).getBindingResult().getFieldErrors()) {
                violationField.put(v.getField(), v.getDefaultMessage());
            }
        }
        result.put("parameter", violationField);
        return result;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({BindException.class})
    public ResponseEntity<Object> exceptionHandler(final BindException e) {
        log.error(String.format("[BindException] message=%s", e.getMessage()));
        Map<Object, Object> result = makeViolationResponse(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseVO> exceptionHandler(final Exception e) {
        log.info(e.toString());
        log.error(String.format("[Exception] message=%s", e.getMessage()));
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ResponseVO.fail(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
