package com.show.showticketingservice.model.criteria;

import lombok.Getter;

@Getter
public class VenuePagingCriteria {

    private int page;

    private int startPage;

    private final int amount = 10;

    public VenuePagingCriteria(int page) {
        this.page = page;
        this.startPage = (page - 1) * amount;
    }
    public void setPage(int page) {
        this.page = page;
        this.startPage = (page - 1) * amount;
    }
}
