package com.shinhan.assignment.service.impl;

import com.shinhan.assignment.model.dto.UserJoinRequestDto;
import com.shinhan.assignment.model.entity.User;
import com.shinhan.assignment.repository.UserRepository;
import com.shinhan.assignment.service.UserExistsException;
import com.shinhan.assignment.service.UserNotExistsException;
import com.shinhan.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Long join(UserJoinRequestDto userJoinRequestDto) {
        log.info(String.format("[USER JOIN] user-info=%s", userJoinRequestDto.toString()));
        validateDuplicateNickName(userJoinRequestDto.getNickName());
        User user = User.createUser(userJoinRequestDto.getNickName(), userJoinRequestDto.getThumbnailURL());
        return userRepository.save(user).getId();
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistsException(String.format("No such id %d user found ", userId)));
    }

    private void validateDuplicateNickName(String nickName) {
        boolean isDuplicateNickName = userRepository.existsByNickName(nickName);
        if(isDuplicateNickName) {
            throw new UserExistsException(String.format("This %s nickname is already using", nickName));
        }
    }
}
