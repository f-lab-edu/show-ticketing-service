package com.show.showticketingservice.model.performance;

import com.show.showticketingservice.model.enumerations.RatingType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemainingSeatsResponse {

    private RatingType ratingType;

    private int remainingSeats;
}
