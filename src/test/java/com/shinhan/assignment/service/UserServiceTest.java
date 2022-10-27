package com.shinhan.assignment.service;

import com.shinhan.assignment.model.dto.MemberJoinRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void 사용자등록_성공() {
        //given
        MemberJoinRequestDto member = new MemberJoinRequestDto();
        member.setNickName("aA가입성공ㅎ");
        member.setThumbnailURL("http:localhost:8282/프로필.png");

        //when
        Long memberId = userService.join(member);

        //then
        assertEquals(memberId, 1L);
    }

    @Test
    void 사용자등록시_인적정보_유효성_검사_실패_닉네임() {
        //given
        String url = "http:localhost:8282/프로필.png";
        MemberJoinRequestDto memOne = makeMember("", url);
        MemberJoinRequestDto memTwo = makeMember(" ", url);
        MemberJoinRequestDto memThird = makeMember(null, url);
        MemberJoinRequestDto memFour = makeMember("1~숫자+특수+a", url);

        //when
        Exception ex1 = assertThrows(Exception.class, () -> {
            userService.join(memOne);
        });

        Exception ex2 = assertThrows(Exception.class, () -> {
            userService.join(memTwo);
        });

        Exception ex3 = assertThrows(Exception.class, () -> {
            userService.join(memThird);
        });

        Exception ex4 = assertThrows(Exception.class, () -> {
            userService.join(memFour);
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
        MemberJoinRequestDto member = makeMember(nickname, url.toString());

        //when
        Exception exception = assertThrows(Exception.class, () -> {
            userService.join(member);
        });

        //then
        assertNotNull(exception);
        assertEquals(url.length(), 51);
    }

    private MemberJoinRequestDto makeMember(String nickname, String url) {
        MemberJoinRequestDto member = new MemberJoinRequestDto();
        member.setNickName(nickname);
        member.setThumbnailURL(url);
        return member;
    }
}