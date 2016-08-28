package com.callfire.api11.client.api.numbers.model;

import com.callfire.api11.client.ListToStringSerializer;
import com.callfire.api11.client.StringToStringListDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class NumberOrderItem {
    private Integer ordered;
    private Float unitCost;
    @JsonSerialize(using = ListToStringSerializer.class)
    @JsonDeserialize(using = StringToStringListDeserializer.class)
    private List<String> fulfilled;

    public Integer getOrdered() {
        return ordered;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public List<String> getFulfilled() {
        return fulfilled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("ordered", ordered)
            .append("unitCost", unitCost)
            .append("fulfilled", fulfilled)
            .toString();
    }
}
