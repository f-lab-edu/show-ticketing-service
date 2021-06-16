package com.show.showticketingservice.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationInfoToCancelRequest {

    @NotNull(message = "공연 시간 id를 입력해 주세요.")
    private final int perfTimeId;

    @NotNull(message = "공연 id를 입력해 주세요.")
    public final int performanceId;

    @Size(min = 1, message = "예약한 id를 입력해 주세요.")
    @NotNull(message = "예약한 id를 입력해 주세요.")
    private final List<Integer> reservationIds;
}
