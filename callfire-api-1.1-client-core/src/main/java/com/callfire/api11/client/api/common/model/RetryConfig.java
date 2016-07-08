package com.callfire.api11.client.api.common.model;

import com.callfire.api11.client.ClientUtils;
import com.callfire.api11.client.JsonConverter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class RetryConfig {
    private Integer maxAttempts;
    private Integer minutesBetweenAttempts;
    @JsonSerialize(using = JsonConverter.ArrayToStringSerializer.class)
    private List<Result> retryResults;
    @JsonSerialize(using = JsonConverter.ArrayToStringSerializer.class)
    private List<RetryPhoneType> retryPhoneTypes;

    public RetryConfig() {
    }

    public RetryConfig(Integer maxAttempts, Integer minutesBetweenAttempts,
        List<Result> retryResults, List<RetryPhoneType> retryPhoneTypes) {
        this.maxAttempts = maxAttempts;
        this.minutesBetweenAttempts = minutesBetweenAttempts;
        this.retryResults = retryResults;
        this.retryPhoneTypes = retryPhoneTypes;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Integer getMinutesBetweenAttempts() {
        return minutesBetweenAttempts;
    }

    public void setMinutesBetweenAttempts(Integer minutesBetweenAttempts) {
        this.minutesBetweenAttempts = minutesBetweenAttempts;
    }

    public List<Result> getRetryResults() {
        return retryResults;
    }

    public void setRetryResults(List<Result> retryResults) {
        this.retryResults = retryResults;
    }

    public List<RetryPhoneType> getRetryPhoneTypes() {
        return retryPhoneTypes;
    }

    public void setRetryPhoneTypes(List<RetryPhoneType> retryPhoneTypes) {
        this.retryPhoneTypes = retryPhoneTypes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("maxAttempts", maxAttempts)
            .append("minutesBetweenAttempts", minutesBetweenAttempts)
            .append("retryResults", retryResults)
            .append("retryPhoneTypes", retryPhoneTypes)
            .toString();
    }

    @JsonSetter("RetryResults")
    private void deserializeRetryResults(String input) {
        retryResults = ClientUtils.deserializeEnumString(input, Result.class);
    }

    @JsonSetter("RetryPhoneTypes")
    private void deserializeRetryPhoneTypes(String input) {
        retryPhoneTypes = ClientUtils.deserializeEnumString(input, RetryPhoneType.class);
    }
}
