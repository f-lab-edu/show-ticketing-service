package com.show.showticketingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
public class User {

    @NotBlank(message = "ID를 입력하세요.")
    @Length(max = 20, message = "ID는 20자리 이하로 입력하세요.")
    private final String id;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "((?=.*\\d)(?=.*\\W)(?=.*[a-z])(?=.*[A-Z]).{6,20})", message = "비밀번호는 숫자,특수문자,영문 대,소문자가 포함된 6~20자리로 입력하세요.")
    private final String password;

    @NotBlank(message = "이름을 입력하세요.")
    @Length(max = 15, message = "이름은 15자리 이하로 입력하세요.")
    private final String name;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp="^010[.-]?(\\d{4})[.-]?(\\d{4})$", message = "전화번호는 '-'를 붙여 10자리 이하로 입력하세요.")
    private final String phoneNum;

    @NotBlank(message = "이메일 주소를 입력하세요.")
    @Email(message = "올바른 이메일 형식으로 입력하세요.")
    private final String email;

    @NotBlank(message = "주소를 입력하세요.")
    private final String address;

    public User pwEncryptedUser(String encryptedPw) {
        return builder()
                .id(getId())
                .password(encryptedPw)
                .name(getName())
                .phoneNum(getPhoneNum())
                .email(getEmail())
                .address(getAddress())
                .build();
    }
}
