package com.callfire.api11.client.api.ccc.model.request;

import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class AgentsDataRequest extends CfApi11Model {
    protected List<Long> agentIds = new ArrayList<>();
    protected List<String> agentEmails = new ArrayList<>();

    public List<Long> getAgentIds() {
        return agentIds;
    }

    public List<String> getAgentEmails() {
        return agentEmails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("agentIds", agentIds)
            .append("agentEmails", agentEmails)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static abstract class AgentsDataBuilder<B extends AgentsDataBuilder, R extends AgentsDataRequest> extends AbstractBuilder<R> {

        public AgentsDataBuilder(R request) {
            super(request);
        }

        @Override
        protected void validate() {
            if (request.agentIds.isEmpty() && request.agentEmails.isEmpty()) {
                throw new IllegalStateException("request.agentIds or request.agentEmails mustn't be empty");
            }
        }

        /**
         * Set agent ids
         *
         * @param agentIds agent ids list
         * @return builder self reference
         */
        public B agentIds(List<Long> agentIds) {
            request.agentIds = agentIds;
            return (B) this;
        }

        /**
         * Set agent emails
         *
         * @param agentEmails agent emails list
         * @return builder self reference
         */
        public B agentEmails(List<String> agentEmails) {
            request.agentEmails = agentEmails;
            return (B) this;
        }
    }
}
