package com.callfire.api11.client.api.broadcasts.model;

import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.common.model.LocalTimeZoneRestriction;
import com.callfire.api11.client.api.common.model.RetryConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Common broadcast config
 */
public abstract class BroadcastConfig extends CfApi11Model {
    @QueryParamName("From")
    protected String fromNumber;
    @QueryParamObject
    protected RetryConfig retryConfig;
    @QueryParamObject
    protected LocalTimeZoneRestriction localTimeZoneRestriction;
    private Long id;
    private Date created;

    public Long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public RetryConfig getRetryConfig() {
        return retryConfig;
    }

    public LocalTimeZoneRestriction getLocalTimeZoneRestriction() {
        return localTimeZoneRestriction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("created", created)
            .append("fromNumber", fromNumber)
            .append("retryConfig", retryConfig)
            .append("localTimeZoneRestriction", localTimeZoneRestriction)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static abstract class BroadcastConfigBuilder<B extends BroadcastConfigBuilder, R extends BroadcastConfig>
        extends AbstractBuilder<R> {

        public BroadcastConfigBuilder(R request) {
            super(request);
        }

        /**
         * Set fromNumber. This is number in your account, it should be verified.
         *
         * @param fromNumber fromNumber
         * @return builder self reference
         */
        public B fromNumber(String fromNumber) {
            request.fromNumber = fromNumber;
            return (B) this;
        }

        /**
         * Set retry config, it defines when contact will be attempted again
         *
         * @param retryConfig retryConfig
         * @return builder self reference
         */
        public B retryConfig(RetryConfig retryConfig) {
            request.retryConfig = retryConfig;
            return (B) this;
        }

        /**
         * Set time frame when a client can be contacted in the timezone associated with the number's NPA/NXX
         *
         * @param timeZoneRestriction timeZoneRestriction restriction time range
         * @return builder self reference
         */
        public B timeZoneRestriction(LocalTimeZoneRestriction timeZoneRestriction) {
            request.localTimeZoneRestriction = timeZoneRestriction;
            return (B) this;
        }
    }
}
