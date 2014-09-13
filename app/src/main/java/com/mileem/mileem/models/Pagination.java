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

    public Number getAmount() {
        return amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }

    public Number getOffset() {
        return offset;
    }

    public void setOffset(Number offset) {
        this.offset = offset;
    }
}

