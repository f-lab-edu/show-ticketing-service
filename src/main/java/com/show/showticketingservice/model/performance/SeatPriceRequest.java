package com.show.showticketingservice.model.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class SeatPriceRequest {

    @NotNull(message = "가격을 입력하세요.")
    private final int price;

    @NotNull(message = "등급을 입력하세요.")
    private final int rating;

    @Min(value = 1, message = "첫 좌석 번호를 1이상 입력하세요.")
    @NotNull(message = "첫 좌석 번호를 입력하세요.")
    private final int startSeat;

    @Min(value = 1, message = "마지막 좌석 번호를 1이상 입력하세요.")
    @NotNull(message = "마지막 좌석 번호를 입력하세요.")
    private final int endSeat;
}
