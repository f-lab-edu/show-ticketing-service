package com.show.showticketingservice.mapper;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.performance.PerformanceRequest;
import com.show.showticketingservice.model.performance.PerformanceDetailInfoResponse;
import com.show.showticketingservice.model.performance.PerformanceUpdateRequest;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PerformanceMapper {

    @DatabaseSource(DatabaseServer.MASTER)
    void insertPerformance(PerformanceRequest performanceRequest);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isPerformanceExists(String title, ShowType showType);

    @DatabaseSource(DatabaseServer.MASTER)
    void updatePerfImagePath(int performanceId, String filePath);

    @DatabaseSource(DatabaseServer.SLAVE)
    String getImagePath(int performanceId);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isPerformanceIdExists(int performanceId);

    @DatabaseSource(DatabaseServer.SLAVE)
    boolean isPerfTitleDuplicated(int performanceId, String title, ShowType showType);

    @DatabaseSource(DatabaseServer.MASTER)
    void updatePerformanceInfo(int performanceId, PerformanceUpdateRequest perfUpdateRequest);

    @DatabaseSource(DatabaseServer.MASTER)
    void deletePerformance(int performanceId);

    @DatabaseSource(DatabaseServer.SLAVE)
    PerformanceDetailInfoResponse getPerformanceDetailInfo(int performanceId);

}
