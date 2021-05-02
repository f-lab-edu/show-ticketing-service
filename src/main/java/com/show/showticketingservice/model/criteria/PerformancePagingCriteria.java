package com.show.showticketingservice.model.criteria;

import lombok.Getter;

@Getter
public class PerformancePagingCriteria {

    private final int amount = 10;

    private final Integer lastPerfId;

    public PerformancePagingCriteria(Integer lastPerfId) {
        this.lastPerfId = lastPerfId;
    }
}
