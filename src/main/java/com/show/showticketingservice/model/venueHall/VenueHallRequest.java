package com.show.showticketingservice.model.venueHall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class VenueHallRequest {

    @NotBlank(message = "공연홀 이름을 입력하세요.")
    private final String name;

    @Min(1)
    @NotBlank(message = "공연홀 좌석 최소 1 이상 행을 입력하세요.")
    private final int columnSeats;

    @Min(1)
    @NotBlank(message = "공연홀 좌석 최소 1 이상 열을 입력하세요.")
    private final int rowSeats;

    @JsonIgnore
    private final int seatingCapacity;

    public VenueHallRequest(String name, int columnSeats, int rowSeats) {
        this.name = name;
        this.columnSeats = columnSeats;
        this.rowSeats = rowSeats;
        this.seatingCapacity = columnSeats * rowSeats;
    }
}
