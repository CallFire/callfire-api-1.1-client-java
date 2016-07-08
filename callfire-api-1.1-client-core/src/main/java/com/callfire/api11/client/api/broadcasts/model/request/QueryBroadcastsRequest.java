package com.callfire.api11.client.api.broadcasts.model.request;

import com.callfire.api11.client.api.broadcasts.model.BroadcastType;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Query broadcasts
 */
public class QueryBroadcastsRequest extends QueryRequest {
    private BroadcastType type;
    private Boolean running;
    private String labelName;

    private QueryBroadcastsRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public BroadcastType getType() {
        return type;
    }

    public Boolean getRunning() {
        return running;
    }

    public String getLabelName() {
        return labelName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("type", type)
            .append("running", running)
            .append("labelName", labelName)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends QueryRequestBuilder<Builder, QueryBroadcastsRequest> {

        public Builder() {
            super(new QueryBroadcastsRequest());
        }

        /**
         * Filter by broadcast type
         *
         * @param type broadcast type
         * @return builder self reference
         */
        public Builder type(BroadcastType type) {
            request.type = type;
            return this;
        }

        /**
         * Filter by running state
         *
         * @param running true to filter broadcasts only in running state
         * @return builder self reference
         */
        public Builder running(boolean running) {
            request.running = running;
            return this;
        }

        /**
         * Filter by broadcast label
         *
         * @param labelName broadcast label
         * @return builder self reference
         */
        public Builder labelName(String labelName) {
            request.labelName = labelName;
            return this;
        }
    }
}
