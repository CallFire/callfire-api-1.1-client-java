package com.callfire.api11.client.api.subscriptions.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class SubscriptionFilter {
    private Long broadcastId;
    private Long batchId;
    private String fromNumber;
    private String toNumber;

    public SubscriptionFilter() {
    }

    public SubscriptionFilter(Long broadcastId, Long batchId) {
        this.broadcastId = broadcastId;
        this.batchId = batchId;
    }

    public SubscriptionFilter(String fromNumber, String toNumber) {
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
    }

    public SubscriptionFilter(Long broadcastId, Long batchId, String fromNumber, String toNumber) {
        this.broadcastId = broadcastId;
        this.batchId = batchId;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
    }

    public Long getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(Long broadcastId) {
        this.broadcastId = broadcastId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("broadcastId", broadcastId)
            .append("batchId", batchId)
            .append("fromNumber", fromNumber)
            .append("toNumber", toNumber)
            .toString();
    }
}
