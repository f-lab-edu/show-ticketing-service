package com.show.showticketingservice.model.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatPriceRowNumData {

    private final int id;

    private final int startRowNum;

    private final int endRowNum;
}
