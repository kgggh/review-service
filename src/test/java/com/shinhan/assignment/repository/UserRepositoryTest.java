package com.shinhan.assignment.repository;

import com.shinhan.assignment.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired private UserRepository userRepository;

    @Test
    void 사용자_엔티티_유효성검사_실패() {
        //given
        String url = "http:localhost:8282/프로필.png";
        User userOne = makeUser("", url);
        User userTwo = makeUser(" ", url);
        User userThird = makeUser(null, url);
        User userFour = makeUser("1~숫자+특수+a", url);

        //when
        Exception ex1 = assertThrows(Exception.class, () -> {
            userRepository.save(userOne);
        });

        Exception ex2 = assertThrows(Exception.class, () -> {
            userRepository.save(userTwo);
        });

        Exception ex3 = assertThrows(Exception.class, () -> {
            userRepository.save(userThird);
        });

        Exception ex4 = assertThrows(Exception.class, () -> {
            userRepository.save(userFour);
        });

        System.out.println(ex4.getMessage());

        //then
        assertNotNull(ex1);
        assertNotNull(ex2);
        assertNotNull(ex3);
        assertNotNull(ex4);

    }

    private User makeUser(String nickname, String url) {
        return User.createUser(nickname, url);
    }
}