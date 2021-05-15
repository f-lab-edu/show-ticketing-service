package com.show.showticketingservice.model.performance;

import com.show.showticketingservice.model.enumerations.RatingType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeatResponse {

    private int id;

    private RatingType ratingType;

    private int rowNum;

    private int colNum;

    private boolean reserved;

}
