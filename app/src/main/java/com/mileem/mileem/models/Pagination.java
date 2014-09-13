package com.mileem.mileem.models;

/**
 * Created by ramirodiaz on 13/09/14.
 */
public class Pagination {
    int amount = 0;
    int offset = 0;
    Boolean lastPageReached = false;

    public Pagination(int amount) {
        this.amount = amount;
        this.offset = 0;
    }

    public Boolean getLastPageReached() {
        return lastPageReached;
    }

    public void setLastPageReached(Boolean lastPageReached) {
        this.lastPageReached = lastPageReached;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}

