package com.show.showticketingservice.model.seat;

import com.show.showticketingservice.model.enumerations.RatingType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatAndPriceResponse {

    private int id;

    private RatingType ratingType;

    private int price;

    private int rowNum;

    private int colNum;

    private boolean reserved;

}
