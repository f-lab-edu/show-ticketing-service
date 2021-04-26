package com.show.showticketingservice.model.performance;

import com.show.showticketingservice.model.enumerations.RatingType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatPriceResponse {

    private int price;

    private RatingType ratingType;
}
