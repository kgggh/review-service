package com.app.review.service;

import com.app.review.model.dto.UserJoinRequestDto;
import com.app.review.model.entity.User;
import com.app.review.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    @Test
    void 사용자등록_성공() {
        //given
        UserJoinRequestDto member = new UserJoinRequestDto();
        member.setNickName("aA가입성공ㅎ");
        member.setThumbnailURL("http:localhost:8282/프로필.png");

        //when
        Long memberId = userService.join(member);

        //then
        Assertions.assertEquals(memberId, userRepository.getReferenceById(memberId).getId());
    }

    @Test
    void 사용자등록시_중복된_닉네임_등록못함() {
        //given
        String duplicateNickName = "중복닉네임";
        String thumbnailURL = "http:localhost:8282/프로필.png";
        userRepository.save(User.createUser(duplicateNickName, thumbnailURL));

        UserJoinRequestDto joinUser = new UserJoinRequestDto();
        joinUser.setNickName(duplicateNickName);
        joinUser.setThumbnailURL(thumbnailURL);

        //when
        Exception exception = assertThrows(Exception.class, () -> {
            userService.join(joinUser);
        });

        //then
        assertNotNull(exception);
    }

    @Test
    void 사용자등록시_인적정보_유효성_검사_실패_닉네임() {
        //given
        String url = "http:localhost:8282/프로필.png";
        UserJoinRequestDto userOne = makeMember("", url);
        UserJoinRequestDto userTwo = makeMember(" ", url);
        UserJoinRequestDto userThird = makeMember(null, url);
        UserJoinRequestDto userFour = makeMember("1~숫자+특수+a", url);

        //when
        Exception ex1 = assertThrows(Exception.class, () -> {
            userService.join(userOne);
        });

        Exception ex2 = assertThrows(Exception.class, () -> {
            userService.join(userTwo);
        });

        Exception ex3 = assertThrows(Exception.class, () -> {
            userService.join(userThird);
        });

        Exception ex4 = assertThrows(Exception.class, () -> {
            userService.join(userFour);
        });

        //then
        assertNotNull(ex1);
        assertNotNull(ex2);
        assertNotNull(ex3);
        assertNotNull(ex4);
    }

    @Test
    void 사용자등록_인적정보_유효성_검사_실패_썸네일() {
        //given
        String nickname = "myNick김건";
        StringBuilder url = new StringBuilder();

        for (int i = 0; i <= 50; i++) {
            url.append("a");
        }
        UserJoinRequestDto user = makeMember(nickname, url.toString());

        //when
        Exception exception = assertThrows(Exception.class, () -> {
            userService.join(user);
        });

        //then
        assertNotNull(exception);
        assertEquals(url.length(), 51);
    }

    private UserJoinRequestDto makeMember(String nickname, String url) {
        UserJoinRequestDto member = new UserJoinRequestDto();
        member.setNickName(nickname);
        member.setThumbnailURL(url);
        return member;
    }
}