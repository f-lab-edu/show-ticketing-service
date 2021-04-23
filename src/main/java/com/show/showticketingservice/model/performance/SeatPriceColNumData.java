package com.show.showticketingservice.model.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatPriceColNumData {

    private final int id;

    private final int startColNum;

    private final int endColNum;
}
