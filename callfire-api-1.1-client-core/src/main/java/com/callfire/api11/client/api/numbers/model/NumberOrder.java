package com.callfire.api11.client.api.numbers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class NumberOrder {
    @JsonProperty("@id")
    private Long id;
    private OrderStatus status;
    private Date created;
    private Float totalCost;
    private NumberOrderItem localNumbers;
    private NumberOrderItem tollFreeNumbers;
    private NumberOrderItem keywords;

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Date getCreated() {
        return created;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public NumberOrderItem getLocalNumbers() {
        return localNumbers;
    }

    public NumberOrderItem getTollFreeNumbers() {
        return tollFreeNumbers;
    }

    public NumberOrderItem getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("status", status)
            .append("created", created)
            .append("totalCost", totalCost)
            .append("localNumbers", localNumbers)
            .append("tollFreeNumbers", tollFreeNumbers)
            .append("keywords", keywords)
            .toString();
    }
}
