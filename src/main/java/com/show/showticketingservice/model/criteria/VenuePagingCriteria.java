package com.show.showticketingservice.model.criteria;

import lombok.Getter;

@Getter
public class VenuePagingCriteria {

    private final int page;

    private final int amount = 10;

    private final int startPage;

    public VenuePagingCriteria(int page) {
        this.page = page;
        this.startPage = (page - 1) * amount;
    }

}
