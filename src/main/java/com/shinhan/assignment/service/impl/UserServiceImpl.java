package com.shinhan.assignment.service.impl;

import com.shinhan.assignment.model.dto.MemberJoinRequestDto;
import com.shinhan.assignment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public Long join(MemberJoinRequestDto memberJoinRequestDto) {
        log.info(String.format("[USER JOIN] user-info=%s", memberJoinRequestDto.toString()));
        return 1L;
    }
}
