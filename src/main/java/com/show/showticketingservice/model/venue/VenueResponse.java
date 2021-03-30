package com.show.showticketingservice.model.venue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VenueResponse {

    private final int id;

    private final String name;

    private final String address;

    private final String tel;

    private final String homepage;

}
