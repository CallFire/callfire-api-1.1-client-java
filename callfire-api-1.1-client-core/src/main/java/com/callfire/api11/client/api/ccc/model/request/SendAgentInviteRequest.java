package com.callfire.api11.client.api.ccc.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Request for sending agent invite
 */
public class SendAgentInviteRequest extends CfApi11Model {
    @QueryParamIgnore
    private Long campaignId;
    private String requestId;
    private String agentGroupName;
    private List<String> agentEmails = new ArrayList<>();

    protected SendAgentInviteRequest() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("campaignId", campaignId)
            .append("requestId", requestId)
            .append("agentGroupName", agentGroupName)
            .append("agentEmails", agentEmails)
            .toString();
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static SendAgentInviteRequestBuilder create() {
        return new SendAgentInviteRequestBuilder();
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getAgentGroupName() {
        return agentGroupName;
    }

    public List<String> getAgentEmails() {
        return agentEmails;
    }

    @SuppressWarnings("unchecked")
    public static class SendAgentInviteRequestBuilder extends AbstractBuilder<SendAgentInviteRequest> {

        public SendAgentInviteRequestBuilder() {
            super(new SendAgentInviteRequest());
        }

        @Override
        protected void validate() {
            if (request.campaignId == null) {
                throw new IllegalStateException("campaign id should be specified");
            }
            if (request.agentEmails.isEmpty()) {
                throw new IllegalStateException("agent emails should be specified");
            }
        }

        /**
         * Set Unique ID of web request to de-dup on
         *
         * @param requestId request id string
         * @return builder self reference
         */
        public SendAgentInviteRequestBuilder requestId(String requestId) {
            request.requestId = requestId;
            return this;
        }

        /**
         * Set agent group name to add agents to
         *
         * @param agentGroupName agent group name string
         * @return builder self reference
         */
        public SendAgentInviteRequestBuilder agentGroupName(String agentGroupName) {
            request.agentGroupName = agentGroupName;
            return this;
        }

        /**
         * Set CCC Campaign to invite agents to
         *
         * @param campaignId campaign id Long
         * @return builder self reference
         */
        public SendAgentInviteRequestBuilder campaignId(Long campaignId) {
            request.campaignId = campaignId;
            return this;
        }

        /**
         * Set list of agent emails to send invites to
         *
         * @param agentEmails list of agent emails
         * @return builder self reference
         */
        public SendAgentInviteRequestBuilder agentEmails(List<String> agentEmails) {
            request.agentEmails = agentEmails;
            return this;
        }

    }
}
