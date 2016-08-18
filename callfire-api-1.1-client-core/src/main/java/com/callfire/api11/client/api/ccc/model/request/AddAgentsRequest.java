package com.callfire.api11.client.api.ccc.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Request adding agents to campaign
 */
public class AddAgentsRequest extends AgentsDataRequest {
    @QueryParamIgnore
    private Long broadcastId;

    private AddAgentsRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public Long getBroadcastId() {
        return broadcastId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("broadcastId", broadcastId)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends AgentsDataBuilder<Builder, AddAgentsRequest> {

        public Builder() {
            super(new AddAgentsRequest());
        }

        @Override
        protected void validate() {
            Validate.notNull(request.broadcastId, "broadcastId cannot be null");
            super.validate();
        }

        /**
         * Set broadcast id to add agents to
         *
         * @param broadcastId broadcast id
         * @return builder self reference
         */
        public Builder broadcastId(Long broadcastId) {
            request.broadcastId = broadcastId;
            return this;
        }
    }
}
