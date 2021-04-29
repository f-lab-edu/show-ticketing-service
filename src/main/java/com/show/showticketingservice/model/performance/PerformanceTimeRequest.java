package com.show.showticketingservice.model.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class PerformanceTimeRequest {

    private final int id;

    @NotBlank
    @Pattern(regexp = "^(\\d{14})$", message = "14자리의 숫자로만 입력하세요. 입력 예(2021년01월31일 14시00분00초) -> 20210131140000")
    private final String startTime;

    @NotBlank
    @Pattern(regexp = "^(\\d{14})$", message = "14자리의 숫자로만 입력하세요. 입력 예(2021년01월31일 14시00분00초) -> 20210131140000")
    private final String endTime;

}
