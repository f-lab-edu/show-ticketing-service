package com.show.showticketingservice.model.performance;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformanceTimeResponse {

    private String startTime;

    private String endTime;
}
