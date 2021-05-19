package com.show.showticketingservice.model.performance;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PerformancePeriod {

    private String firstDay;

    private String lastDay;
}
