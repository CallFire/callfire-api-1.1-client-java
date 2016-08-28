package com.callfire.api11.client.api.numbers.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Keyword {
    private String shortCode;
    private String keyword;
    private NumberStatus status;
    private LeaseInfo leaseInfo;

    public String getShortCode() {
        return shortCode;
    }

    public String getKeyword() {
        return keyword;
    }

    public NumberStatus getStatus() {
        return status;
    }

    public LeaseInfo getLeaseInfo() {
        return leaseInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("shortCode", shortCode)
            .append("keyword", keyword)
            .append("status", status)
            .append("leaseInfo", leaseInfo)
            .toString();
    }
}
