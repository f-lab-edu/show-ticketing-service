package com.show.showticketingservice.model.venue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class VenueRequest {

    private final int id;

    @NotBlank(message = "공연장 이름을 입력하세요.")
    @Length(max = 45, message = "공연장 이름 입력은 최대 45자까지 가능합니다.")
    private final String name;

    @NotBlank(message = "공연장 주소를 입력하세요.")
    @Length(max = 100, message = "주소 입력은 최대 100자까지 가능합니다.")
    private final String address;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Length(max = 20, message = "전화번호 입력은 최대 20자까지 가능합니다.")
    private final String tel;

    @NotNull
    @Length(max = 100, message = "홈페이지 입력은 최대 100자까지 가능합니다.")
    private final String homepage;

}
