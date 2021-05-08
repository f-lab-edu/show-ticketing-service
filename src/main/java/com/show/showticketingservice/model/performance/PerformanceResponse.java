package com.show.showticketingservice.model.performance;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceResponse {

    private int id;

    private String title;

    private String imageFilePath;

    private String venueName;

    private String hallName;

    private PerformancePeriod performancePeriod;
}
