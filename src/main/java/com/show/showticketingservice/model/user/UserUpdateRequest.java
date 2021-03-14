package com.show.showticketingservice.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "새 비밀번호를 입력하세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*+=(),._?\":{}|<>/-])(?=\\S+$).{6,20}$", message = "비밀번호는 숫자,특수문자,영문 대/소문자가 모두 포함된 6~20자리로 입력하세요.")
    private final String newPassword;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp="^010-(\\d{4})-(\\d{4})$", message = "전화번호는 '-'를 포함하여 13자리로 입력하세요.")
    private final String newPhoneNum;

    @NotBlank(message = "주소를 입력하세요.")
    @Length(max = 100, message = "주소 입력은 최대 100자까지 가능합니다.")
    private final String newAddress;

    public UserUpdateRequest pwEncryptedUserUpdateRequest(String encryptedPw) {
        return builder()
                .newPassword(encryptedPw)
                .newPhoneNum(getNewPhoneNum())
                .newAddress(getNewAddress())
                .build();
    }
}
