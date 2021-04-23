package com.show.showticketingservice.model.performance;

import com.show.showticketingservice.model.enumerations.RatingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class SeatPriceRequest {

    @NotNull(message = "가격을 입력하세요.")
    private final int price;

    @NotNull(message = "등급을 입력하세요.")
    private final RatingType ratingType;

    @Min(value = 1, message = "좌석 행을 1이상 입력하세요.")
    @NotNull(message = "좌석 행을 입력하세요.")
    private final int startRowNum;

    @Min(value = 1, message = "좌석 행을 1이상 입력하세요.")
    @NotNull(message = "좌석 행을 입력하세요.")
    private final int endRowNum;
}
