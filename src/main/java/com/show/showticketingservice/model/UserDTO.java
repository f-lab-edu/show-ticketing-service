package com.show.showticketingservice.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class UserDTO {

    @NotBlank(message = "id를 입력하세요")
    @Length(max = 20, message = "id는 20자리 이하로 입력하세요")
    private final String id;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "((?=.*\\d)(?=.*\\W)(?=.*[a-z])(?=.*[A-Z]).{6,20})", message = "비밀번호는 숫자,특수문자,영문 대,소문자가 포함된 6~20자리로 입력하세요.")
    private final String password;

    @NotBlank(message = "이름을 입력하세요.")
    @Length(max = 15, message = "이름은 15자리 이하로 입력하세요.")
    private final String name;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp="^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$")
    private final String phoneNum;

    @NotBlank(message = "이메일 주소를 입력하세요.")
    @Email(message = "올바른 이메일 형식으로 입력하세요.")
    private final String email;

    @NotBlank(message = "주소를 입력하세요.")
    private final String address;
}
