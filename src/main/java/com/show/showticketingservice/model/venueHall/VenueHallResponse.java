package com.show.showticketingservice.model.venueHall;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VenueHallResponse {

    private int id;

    private int venueId;

    private String name;

    private int columnSeats;

    private int rowSeats;

    private int seatingCapacity;

}
