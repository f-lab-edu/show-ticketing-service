package com.show.showticketingservice.model.user

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UserUpdateRequest(

        @field:NotBlank(message = "새 비밀번호를 입력하세요.")
        @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*+=(),._?\":{}|<>/-])(?=\\S+$).{6,20}$",
                message = "비밀번호는 숫자,특수문자,영문 대/소문자가 모두 포함된 6~20자리로 입력하세요.")
        val newPassword: String,

        @field:NotBlank(message = "전화번호를 입력하세요.")
        @field:Pattern(regexp = "^010-(\\d{4})-(\\d{4})$",
                message = "전화번호는 '-'를 포함하여 13자리로 입력하세요.")
        val newPhoneNum: String,

        @field:NotBlank(message = "주소를 입력하세요.")
        @field:Length(max = 100, message = "주소 입력은 최대 100자까지 가능합니다.")
        val newAddress: String

) {

    fun pwEncryptedUserUpdateRequest(encryptedPw: String) = UserUpdateRequest(
            newPassword = encryptedPw,
            newPhoneNum = newPhoneNum,
            newAddress = newAddress
    )

}