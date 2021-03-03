package com.show.showticketingservice.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Builder
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "ID를 입력하세요.")
    @Pattern(regexp = "^[\\w]{6,20}$", message = "ID는 6~20자 이내, 영문 대/소문자 또는 숫자로 입력하세요.")
    private final String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*+=(),._?\":{}|<>/-])(?=\\S+$).{6,20}$", message = "비밀번호는 숫자,특수문자,영문 대/소문자가 모두 포함된 6~20자리로 입력하세요.")
    private final String password;

    @NotBlank(message = "이름을 입력하세요.")
    @Length(max = 15, message = "이름은 15자리 이하로 입력하세요.")
    private final String name;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp="^010-(\\d{4})-(\\d{4})$", message = "전화번호는 '-'를 포함하여 13자리로 입력하세요.")
    private final String phoneNum;

    @NotBlank(message = "이메일 주소를 입력하세요.")
    @Email(message = "올바른 이메일 형식으로 입력하세요.")
    private final String email;

    @NotBlank(message = "주소를 입력하세요.")
    @Length(max = 100, message = "주소 입력은 최대 100자까지 가능합니다.")
    private final String address;

    @NotNull(message = "회원 타입을 입력하세요.")
    @DecimalMax(value = "2", inclusive = true)
    @DecimalMin(value = "1", inclusive = true)
    private final int userType;

    public UserRequest pwEncryptedUser(String encryptedPw) {
        return builder()
                .userId(getUserId())
                .password(encryptedPw)
                .name(getName())
                .phoneNum(getPhoneNum())
                .email(getEmail())
                .address(getAddress())
                .userType(getUserType())
                .build();
    }
}
