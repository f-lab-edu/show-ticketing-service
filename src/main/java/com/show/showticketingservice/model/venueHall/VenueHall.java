package com.show.showticketingservice.model.venueHall;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class VenueHall {

    @NotBlank(message = "공연홀 이름을 입력하세요.")
    private final String name;

    @Min(1)
    @NotNull(message = "공연홀 좌석 최소 1 이상 행을 입력하세요.")
    private final int columSeats;

    @Min(1)
    @NotNull(message = "공연홀 좌석 최소 1 이상 열을 입력하세요.")
    private final int rowSeats;

    @JsonIgnore
    private final int seatingCapacity;

    public VenueHall(String name, int columSeats, int rowSeats) {
        this.name = name;
        this.columSeats = columSeats;
        this.rowSeats = rowSeats;
        this.seatingCapacity = columSeats * rowSeats;
    }
}
