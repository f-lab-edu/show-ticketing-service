package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.reservation.ReservationInfoToCancelRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {

    void reserveSeats(int userId, List<Integer> seatIds);

    void cancelSeats(List<Integer> reservationIds);

    int getSeatNumToCancel(int userId, @Param("reservationInfo") ReservationInfoToCancelRequest reservationInfoToCancelRequest);
}
