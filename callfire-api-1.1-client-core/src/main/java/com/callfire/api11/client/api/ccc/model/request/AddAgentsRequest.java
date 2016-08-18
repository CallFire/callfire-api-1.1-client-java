package com.callfire.api11.client.api.ccc.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Request adding agents to campaign
 */
public class AddAgentsRequest extends CfApi11Model {
    @QueryParamIgnore
    private Long broadcastId;
    private List<Long> agentIds = new ArrayList<>();
    private List<String> agentEmails = new ArrayList<>();

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
    public static class Builder extends AbstractBuilder<AddAgentsRequest> {

        public Builder() {
            super(new AddAgentsRequest());
        }

        @Override
        protected void validate() {
            Validate.notNull(request.broadcastId, "broadcastId cannot be null");
            if (request.agentIds.isEmpty() && request.agentEmails.isEmpty()) {
                throw new IllegalStateException("request.agentIds or request.agentEmails mustn't be empty");
            }
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

        /**
         * Set agent ids
         *
         * @param agentIds agent ids list
         * @return builder self reference
         */
        public Builder agentIds(List<Long> agentIds) {
            request.agentIds = agentIds;
            return this;
        }

        /**
         * Set agent emails
         *
         * @param agentEmails agent emails list
         * @return builder self reference
         */
        public Builder agentEmails(List<String> agentEmails) {
            request.agentEmails = agentEmails;
            return this;
        }
    }
}
