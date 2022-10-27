package com.shinhan.assignment.service;

import com.shinhan.assignment.model.dto.UserJoinRequestDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface UserService {
    Long join(@Valid UserJoinRequestDto userJoinRequestDto);
}
