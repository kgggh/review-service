package com.app.review.service;

import com.app.review.model.dto.UserJoinRequestDto;
import com.app.review.model.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UserService {
    Long join(@Valid UserJoinRequestDto userJoinRequestDto);
    User getUser(Long userId);
}
