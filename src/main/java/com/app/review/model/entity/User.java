package com.app.review.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣]{1,10}$", message = "한글/영문 10자리")
    @Column(length = 10, nullable = false, unique = true)
    private String nickName;

    @Size(max = 50, message = "최대 50자리")
    @Column(length = 50)
    private String thumbnailUrl;

    private User(String nickName, String thumbnailUrl) {
        this.nickName = nickName;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static User createUser(String nickName, String thumbnailURL) {
        return new User(nickName, thumbnailURL);
    }
}
