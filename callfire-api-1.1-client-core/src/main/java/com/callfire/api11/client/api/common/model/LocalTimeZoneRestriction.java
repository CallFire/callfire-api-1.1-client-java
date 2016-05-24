package com.callfire.api11.client.api.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

import static com.callfire.api11.client.ClientConstants.LOCALTIME_RESTRICTION_FORMAT_PATTERN;

public class LocalTimeZoneRestriction {
    @JsonProperty("LocalRestrictBegin")
    @JsonFormat(pattern = LOCALTIME_RESTRICTION_FORMAT_PATTERN)
    private Date beginTime;
    @JsonProperty("LocalRestrictEnd")
    @JsonFormat(pattern = LOCALTIME_RESTRICTION_FORMAT_PATTERN)
    private Date endTime;

    public LocalTimeZoneRestriction() {
    }

    public LocalTimeZoneRestriction(Date beginTime, Date endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("beginTime", beginTime)
            .append("endTime", endTime)
            .toString();
    }
}
