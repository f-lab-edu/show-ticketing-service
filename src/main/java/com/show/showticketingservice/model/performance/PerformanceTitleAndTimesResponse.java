package com.show.showticketingservice.model.performance;

import lombok.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceTitleAndTimesResponse {

    private int id;

    private String title;

    private List<PerformanceTimeResponse> performanceTimeResponses;
}