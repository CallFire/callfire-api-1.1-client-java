package com.callfire.api11.client.api.numbers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Number {
    private String number;
    private String nationalFormat;
    private Boolean tollFree;
    private Region region;
    private NumberStatus status;
    private LeaseInfo leaseInfo;
    @JsonProperty("NumberConfiguration")
    private NumberConfig numberConfig;

    public String getNumber() {
        return number;
    }

    public String getNationalFormat() {
        return nationalFormat;
    }

    public Boolean getTollFree() {
        return tollFree;
    }

    public Region getRegion() {
        return region;
    }

    public NumberStatus getStatus() {
        return status;
    }

    public LeaseInfo getLeaseInfo() {
        return leaseInfo;
    }

    public NumberConfig getNumberConfig() {
        return numberConfig;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("number", number)
            .append("nationalFormat", nationalFormat)
            .append("tollFree", tollFree)
            .append("region", region)
            .append("status", status)
            .append("leaseInfo", leaseInfo)
            .append("numberConfig", numberConfig)
            .toString();
    }
}
