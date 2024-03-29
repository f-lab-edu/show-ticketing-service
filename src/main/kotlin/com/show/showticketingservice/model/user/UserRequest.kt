package com.show.showticketingservice.model.user

import com.show.showticketingservice.model.enumerations.UserType
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class UserRequest(

        @field:NotBlank(message = "ID를 입력하세요.")
        @field:Pattern(regexp = "^[\\w]{6,20}$", message = "ID는 6~20자 이내, 영문 대/소문자 또는 숫자로 입력하세요.")
        val userId: String,

        @field:NotBlank(message = "비밀번호를 입력하세요.")
        @field:Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*+=(),._?\":{}|<>/-])(?=\\S+$).{6,20}$",
                message = "비밀번호는 숫자,특수문자,영문 대/소문자가 모두 포함된 6~20자리로 입력하세요.")
        val password: String,

        @field:NotBlank(message = "이름을 입력하세요.")
        @field:Length(max = 15, message = "이름은 15자리 이하로 입력하세요.")
        val name: String,

        @field:NotBlank(message = "전화번호를 입력하세요.")
        @field:Pattern(regexp = "^010-(\\d{4})-(\\d{4})$", message = "전화번호는 '-'를 포함하여 13자리로 입력하세요.")
        val phoneNum: String,

        @field:NotBlank(message = "이메일 주소를 입력하세요.")
        @field:Email(message = "올바른 이메일 형식으로 입력하세요.")
        val email: String,

        @field:NotBlank(message = "주소를 입력하세요.")
        @field:Length(max = 100, message = "주소 입력은 최대 100자까지 가능합니다.")
        val address: String,

        @field:NotNull(message = "회원 타입을 입력하세요.")
        val userType: UserType

) {

    fun pwEncryptedUser(encryptedPw: String) = UserRequest(
            userId = userId,
            password = encryptedPw,
            name = name,
            phoneNum = phoneNum,
            email = email,
            address = address,
            userType = userType
    )

}