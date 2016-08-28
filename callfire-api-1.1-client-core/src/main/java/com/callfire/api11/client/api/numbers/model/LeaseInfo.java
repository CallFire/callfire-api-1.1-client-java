package com.callfire.api11.client.api.numbers.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

import static com.callfire.api11.client.ClientConstants.ZONED_DATE_FORMAT_PATTERN;

public class LeaseInfo {
    @JsonFormat(pattern = ZONED_DATE_FORMAT_PATTERN)
    private Date leaseBegin;
    @JsonFormat(pattern = ZONED_DATE_FORMAT_PATTERN)
    private Date leaseEnd;
    private Boolean autoRenew;

    public Date getLeaseBegin() {
        return leaseBegin;
    }

    public Date getLeaseEnd() {
        return leaseEnd;
    }

    public Boolean getAutoRenew() {
        return autoRenew;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("leaseBegin", leaseBegin)
            .append("leaseEnd", leaseEnd)
            .append("autoRenew", autoRenew)
            .toString();
    }
}
