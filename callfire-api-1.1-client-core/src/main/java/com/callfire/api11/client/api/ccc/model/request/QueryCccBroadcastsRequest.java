package com.callfire.api11.client.api.ccc.model.request;

import com.callfire.api11.client.api.common.model.request.QueryRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Query ccc broadcasts request
 */
public class QueryCccBroadcastsRequest extends QueryRequest {
    private Boolean running;
    private String labelName;
    private String name;

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public Boolean getRunning() {
        return running;
    }

    public String getLabelName() {
        return labelName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("running", running)
            .append("labelName", labelName)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends QueryRequestBuilder<Builder, QueryCccBroadcastsRequest> {

        public Builder() {
            super(new QueryCccBroadcastsRequest());
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
         * @param labelName broadcast label string
         * @return builder self reference
         */
        public Builder labelName(String labelName) {
            request.labelName = labelName;
            return this;
        }

        /**
         * Filter by broadcast name
         *
         * @param name broadcast name string
         * @return builder self reference
         */
        public Builder name(String name) {
            request.name = name;
            return this;
        }
    }
}
