package com.show.showticketingservice.model.venueHall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class VenueHall {

    @NotBlank(message = "공연홀 이름을 입력하세요.")
    private final String name;

    @NotBlank(message = "공연홀 좌석 열을 입력하세요.")
    private final String rowSeats;

    @NotBlank(message = "공연홀 좌석 행을 입력하세요.")
    private final String columSeats;

    @NotBlank(message = "공연홀 좌석 수를 입력하세요.")
    private final String seatingCapacity;
}
