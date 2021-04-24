package com.show.showticketingservice.model.performance;

import com.show.showticketingservice.model.enumerations.ShowType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class PerformanceUpdateRequest {

    @NotBlank(message = "공연 제목을 입력하세요.")
    @Length(max = 50, message = "제목은 50자 내로 입력하세요.")
    private final String title;

    @Nullable
    @Length(max = 500, message = "공연 설명은 500자 내로 입력하세요.")
    private final String detail;

    @Nullable
    private final int ageLimit;

    @NotNull(message = "공연 타입을 입력하세요.")
    private final ShowType showType;

}
