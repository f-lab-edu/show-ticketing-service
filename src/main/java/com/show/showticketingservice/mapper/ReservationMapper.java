package com.show.showticketingservice.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationMapper {

    void reserveSeats(int userId, List<Integer> seatIds);

}
