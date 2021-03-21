package com.show.showticketingservice.model.venue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class VenueRequest {

    @NotBlank(message = "공연장 이름을 입력하세요.")
    @Length(max = 45, message = "공연장 이름 입력은 최대 45자까지 가능합니다.")
    private final String name;

    @NotBlank(message = "공연장 주소를 입력하세요.")
    @Length(max = 100, message = "주소 입력은 최대 100자까지 가능합니다.")
    private final String address;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Length(max = 20, message = "전화번호 입력은 최대 100자까지 가능합니다.")
    private final String telNum;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Length(max = 100, message = "홈페이지 입력은 최대 100자까지 가능합니다.")
    private final String homepage;

}
