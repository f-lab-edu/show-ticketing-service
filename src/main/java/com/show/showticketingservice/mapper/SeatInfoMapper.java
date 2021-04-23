package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.performance.SeatRequest;
import org.apache.ibatis.annotations.Mapper;
import java.util.*;

@Mapper
public interface SeatInfoMapper {

    void insertSeatInfo(List<SeatRequest> seatRequest);
}
