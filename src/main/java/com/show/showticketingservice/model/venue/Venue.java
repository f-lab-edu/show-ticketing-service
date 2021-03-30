package com.show.showticketingservice.model.venue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class Venue {

    private final int id;

    @NotBlank(message = "공연장 이름을 입력하세요.")
    @Length(max = 45, message = "공연장 이름 입력은 최대 45자까지 가능합니다.")
    private final String name;

    @NotBlank(message = "공연장 주소를 입력하세요.")
    @Length(max = 100, message = "주소 입력은 최대 100자까지 가능합니다.")
    private final String address;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Length(max = 20, message = "전화번호 입력은 최대 20자까지 가능합니다.")
    private final String tel;

    @NotNull
    @Length(max = 100, message = "홈페이지 입력은 최대 100자까지 가능합니다.")
    private final String homepage;

    @Getter
    public class VenueHallRequest {

        @NotBlank(message = "공연홀 이름을 입력하세요.")
        private final String name;

        @Min(1)
        @NotNull(message = "공연홀 좌석 최소 1 이상 행을 입력하세요.")
        private final int columnSeats;

        @Min(1)
        @NotNull(message = "공연홀 좌석 최소 1 이상 열을 입력하세요.")
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

}
