package com.callfire.api11.client.api.common.model;

import com.callfire.api11.client.ClientConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.TIMESTAMP_FORMAT_PATTERN;

public class Action extends CfApi11Model {
    @JsonProperty("@id")
    private Long id;
    private String fromNumber;
    private ToNumber toNumber;
    private ActionState state;
    private Long batchId;
    private Long broadcastId;
    private Long contactId;
    private Boolean inbound;
    private Date created;
    private Date modified;
    private List<Label> label;

    public long getId() {
        return id;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public ToNumber getToNumber() {
        return toNumber;
    }

    public ActionState getState() {
        return state;
    }

    public Long getBatchId() {
        return batchId;
    }

    public Long getBroadcastId() {
        return broadcastId;
    }

    public long getContactId() {
        return contactId;
    }

    public boolean isInbound() {
        return inbound;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("fromNumber", fromNumber)
            .append("toNumber", toNumber)
            .append("state", state)
            .append("batchId", batchId)
            .append("broadcastId", broadcastId)
            .append("contactId", contactId)
            .append("inbound", inbound)
            .append("created", created)
            .append("modified", modified)
            .append("label", label)
            .toString();
    }
}
