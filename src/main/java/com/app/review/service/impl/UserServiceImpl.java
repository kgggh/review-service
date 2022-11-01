package com.app.review.service.impl;

import com.app.review.model.dto.UserJoinRequestDto;
import com.app.review.service.UserExistsException;
import com.app.review.service.UserService;
import com.app.review.model.entity.User;
import com.app.review.repository.UserRepository;
import com.app.review.service.UserNotExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Value("${dummy-data.enable}")
    private boolean enableDummyData = false;

    @PostConstruct
    void init () {
        if(enableDummyData) {
            UserJoinRequestDto user = new UserJoinRequestDto();
            user.setNickName("테스터");
            user.setThumbnailURL("http:localhost:8282/프로필.png");
            this.join(user);
        }
    }

    @Transactional
    @Override
    public Long join(UserJoinRequestDto userJoinRequestDto) {
        log.info(String.format("[USER JOIN] user-info=%s", userJoinRequestDto.toString()));
        validateDuplicateNickName(userJoinRequestDto.getNickName());
        User user = User.createUser(userJoinRequestDto.getNickName(), userJoinRequestDto.getThumbnailURL());
        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotExistsException(String.format("No such id %d user found ", userId)));
    }

    @Transactional(readOnly = true)
    private void validateDuplicateNickName(String nickName) {
        boolean isDuplicateNickName = userRepository.existsByNickName(nickName);
        if(isDuplicateNickName) {
            throw new UserExistsException(String.format("This %s nickname is already using", nickName));
        }
    }
}
