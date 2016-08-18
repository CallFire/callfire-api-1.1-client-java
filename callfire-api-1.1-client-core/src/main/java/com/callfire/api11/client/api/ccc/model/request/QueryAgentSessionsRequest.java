package com.callfire.api11.client.api.ccc.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.callfire.api11.client.api.common.model.request.QueryRequest;

/**
 * Query agent sessions request
 */
public class QueryAgentSessionsRequest extends QueryRequest {
    private Long campaignId;
    private Long agentId;
    private String agentEmail;
    private Boolean active;

    private QueryAgentSessionsRequest() {
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public Boolean getActive() {
        return active;
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
            .append("active", active)
            .append("agentId", agentId)
            .append("agentEmail", agentEmail)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends QueryRequestBuilder<Builder, QueryAgentSessionsRequest> {

        public Builder() {
            super(new QueryAgentSessionsRequest());
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
         * Filter by active flag
         *
         * @param active active Boolean flag
         * @return builder self reference
         */
        public Builder active(Boolean active) {
            request.active = active;
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
