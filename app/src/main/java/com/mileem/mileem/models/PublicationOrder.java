package com.mileem.mileem.models;

import java.util.HashMap;


/**
 * Created by ramirodiaz on 13/09/14.
 */

public class PublicationOrder {

    public enum OrderBy {
        PUBLISH_DATE, PRICE, PRIORITY;
    }

    public enum Order {
        ASC, DES;
    }

    OrderBy orderBy;
    Order order;

    public PublicationOrder (OrderBy orderBy, Order order) {
        this.orderBy = orderBy;
        this.order = order;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
