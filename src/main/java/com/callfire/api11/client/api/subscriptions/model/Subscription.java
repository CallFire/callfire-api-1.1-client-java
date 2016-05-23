package com.callfire.api11.client.api.subscriptions.model;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Subscription extends CfApi11Model {
    @QueryParamIgnore
    @JsonProperty("@id")
    private Long id;
    private Boolean enabled;
    private Boolean nonStrictSsl;
    private String endpoint;
    private NotificationFormat notificationFormat;
    private TriggerEvent triggerEvent;
    @QueryParamObject
    @JsonProperty("SubscriptionFilter")
    private SubscriptionFilter filter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getNonStrictSsl() {
        return nonStrictSsl;
    }

    public void setNonStrictSsl(Boolean nonStrictSsl) {
        this.nonStrictSsl = nonStrictSsl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public NotificationFormat getNotificationFormat() {
        return notificationFormat;
    }

    public void setNotificationFormat(NotificationFormat notificationFormat) {
        this.notificationFormat = notificationFormat;
    }

    public TriggerEvent getTriggerEvent() {
        return triggerEvent;
    }

    public void setTriggerEvent(TriggerEvent triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    public SubscriptionFilter getFilter() {
        return filter;
    }

    public void setFilter(SubscriptionFilter filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("enabled", enabled)
            .append("nonStrictSsl", nonStrictSsl)
            .append("endpoint", endpoint)
            .append("notificationFormat", notificationFormat)
            .append("triggerEvent", triggerEvent)
            .append("filter", filter)
            .toString();
    }
}
