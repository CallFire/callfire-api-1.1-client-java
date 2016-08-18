package com.callfire.api11.client.api.ccc.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.callfire.api11.client.api.common.model.request.QueryRequest;

/**
 * Query agent groups request
 */
public class QueryAgentGroupsRequest extends QueryRequest {
    private Long campaignId;
    private String name;
    private Long agentId;
    private String agentEmail;

    private QueryAgentGroupsRequest() {
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public String getName() {
        return name;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("campaignId", campaignId)
            .append("name", name)
            .append("agentId", agentId)
            .append("agentEmail", agentEmail)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends QueryRequestBuilder<Builder, QueryAgentGroupsRequest> {

        public Builder() {
            super(new QueryAgentGroupsRequest());
        }

        /**
         * Filter by campaign id
         *
         * @param campaignId broadcast id Long
         * @return builder self reference
         */
        public Builder campaignId(Long campaignId) {
            request.campaignId = campaignId;
            return this;
        }

        /**
         * Filter by name
         *
         * @param name agent group name string
         * @return builder self reference
         */
        public Builder name(String name) {
            request.name = name;
            return this;
        }

        /**
         * Filter by agent id
         *
         * @param agentId agent id Long
         * @return builder self reference
         */
        public Builder agentId(Long agentId) {
            request.agentId = agentId;
            return this;
        }

        /**
         * Filter by agent email
         *
         * @param agentEmail agent email string
         * @return builder self reference
         */
        public Builder agentEmail(String agentEmail) {
            request.agentEmail = agentEmail;
            return this;
        }

    }
}
