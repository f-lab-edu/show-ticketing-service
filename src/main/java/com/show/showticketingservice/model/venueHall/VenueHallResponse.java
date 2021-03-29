package com.show.showticketingservice.model.venueHall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class VenueHallResponse {

    private final int id;

    private final int venueId;

    private final String name;

    private final int columSeats;

    private final int rowSeats;

    private final int seatingCapacity;

}
