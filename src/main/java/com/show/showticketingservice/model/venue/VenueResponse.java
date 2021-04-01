package com.show.showticketingservice.model.venue;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VenueResponse {

    private int id;

    private String name;

    private String address;

    private String tel;

    private String homepage;

}
