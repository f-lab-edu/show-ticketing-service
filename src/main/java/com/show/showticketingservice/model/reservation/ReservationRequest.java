package com.show.showticketingservice.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationRequest {

    private final int perfTimeId;

    private final List<Integer> seatIds;

}
