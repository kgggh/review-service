package com.app.review.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserJoinRequestDto {
    @NotBlank(message = "필수입력값")
    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣]{1,10}$", message = "한글/영문 10자리")
    private String nickName;

    @Size(max = 50, message = "최대 50자리")
    private String thumbnailURL;
}
