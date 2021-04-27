package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import com.show.showticketingservice.model.performance.PerformanceUpdateRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PerformanceMapper {

    void insertPerformance(PerformanceRequest performanceRequest);

    boolean isPerformanceExists(String title, ShowType showType);

    void updatePerfImagePath(int performanceId, String filePath);

    String getImagePath(int performanceId);

    boolean isPerformanceIdExists(int performanceId);

    boolean isPerfTitleDuplicated(int performanceId, String title, ShowType showType);

    void updatePerformanceInfo(int performanceId, PerformanceUpdateRequest perfUpdateRequest);
}
