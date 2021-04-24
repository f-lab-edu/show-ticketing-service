package com.show.showticketingservice.model.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SeatRequest {

    private final int perfTimeId;

    private final int hallId;

    private final int priceId;

    private final int colNum;

    private final int rowNum;

    private final int reserved;
}
