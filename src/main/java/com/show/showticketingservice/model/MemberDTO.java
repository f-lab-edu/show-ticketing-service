package com.show.showticketingservice.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @ToString @AllArgsConstructor
public class MemberDTO {

    @NotNull(message = "id를 입력하세요 (5~10자리)")
    @Size(min = 5, max = 10)
    private final String id;

    @NotNull(message = "비밀번호를 입력하세요 (8~16자리)")
    @Size(min = 8, max = 16)
    private final String password;

    @NotNull(message = "이름을 입력하세요")
    private final String name;

    @NotNull(message = "전화번호를 입력하세요")
    private final String phoneNum;

    @NotNull(message = "이메일을 입력하세요")
    private final String email;

}
