package com.show.showticketingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberDTO {

    @NotEmpty(message = "id를 입력하세요 (5~10자리)")
    @Size(min = 5, max = 10)
    String id;

    @NotEmpty(message = "비밀번호를 입력하세요 (8~16자리)")
    @Size(min = 8, max = 16)
    String password;

    @NotEmpty(message = "이름을 입력하세요")
    String name;

    @NotEmpty(message = "전화번호를 입력하세요")
    String phoneNum;

    @NotEmpty(message = "이메일을 입력하세요")
    @Email(message = "이메일 형식에 맞게 입력해주세요")
    String email;

    String address;
}
