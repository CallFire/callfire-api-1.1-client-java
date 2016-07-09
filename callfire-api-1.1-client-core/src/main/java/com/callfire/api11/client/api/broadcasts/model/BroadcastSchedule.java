package com.callfire.api11.client.api.broadcasts.model;

import com.callfire.api11.client.ClientUtils;
import com.callfire.api11.client.JsonConverter.ArrayToStringSerializer;
import com.callfire.api11.client.api.common.QueryParamFormat;
import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.DATE_FORMAT_PATTERN;
import static com.callfire.api11.client.ClientConstants.TIME_FORMAT_PATTERN;
import static com.callfire.api11.client.ClientConstants.ZONED_DATE_FORMAT_PATTERN;
import static com.callfire.api11.client.ClientConstants.ZONED_TIME_FORMAT_PATTERN;

/**
 * Represents schedule which can be assigned to broadcast
 */
public class BroadcastSchedule extends CfApi11Model {
    @QueryParamIgnore
    @JsonProperty("@id")
    private Long id;
    private String timeZone;
    @JsonFormat(pattern = ZONED_TIME_FORMAT_PATTERN)
    @QueryParamFormat(pattern = TIME_FORMAT_PATTERN)
    private Date startTimeOfDay;
    @JsonFormat(pattern = ZONED_TIME_FORMAT_PATTERN)
    @QueryParamFormat(pattern = TIME_FORMAT_PATTERN)
    private Date stopTimeOfDay;
    @JsonFormat(pattern = ZONED_DATE_FORMAT_PATTERN)
    @QueryParamFormat(pattern = DATE_FORMAT_PATTERN)
    private Date beginDate;
    @QueryParamFormat(pattern = DATE_FORMAT_PATTERN)
    @JsonFormat(pattern = ZONED_DATE_FORMAT_PATTERN)
    private Date endDate;
    @JsonSerialize(using = ArrayToStringSerializer.class)
    private List<DayOfWeek> daysOfWeek;

    public Long getId() {
        return id;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Date getStartTimeOfDay() {
        return startTimeOfDay;
    }

    public void setStartTimeOfDay(Date startTimeOfDay) {
        this.startTimeOfDay = startTimeOfDay;
    }

    public Date getStopTimeOfDay() {
        return stopTimeOfDay;
    }

    public void setStopTimeOfDay(Date stopTimeOfDay) {
        this.stopTimeOfDay = stopTimeOfDay;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("timeZone", timeZone)
            .append("startTimeOfDay", startTimeOfDay)
            .append("stopTimeOfDay", stopTimeOfDay)
            .append("beginDate", beginDate)
            .append("endDate", endDate)
            .append("daysOfWeek", daysOfWeek)
            .toString();
    }

    @JsonSetter("DaysOfWeek")
    private void deserializeDaysOfWeek(String input) {
        daysOfWeek = ClientUtils.deserializeEnumString(input, DayOfWeek.class);
    }
}
