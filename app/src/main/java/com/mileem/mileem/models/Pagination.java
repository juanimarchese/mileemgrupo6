package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 13/09/14.
 */
public class Pagination {
    Number amount;
    Number offset;
    Boolean lastPageReached;

    public Pagination(Number amount, Number offset) {
        this.amount = amount;
        this.offset = offset;
    }

    public Boolean getLastPageReached() {
        return lastPageReached;
    }

    public void setLastPageReached(Boolean lastPageReached) {
        this.lastPageReached = lastPageReached;
    }
}

