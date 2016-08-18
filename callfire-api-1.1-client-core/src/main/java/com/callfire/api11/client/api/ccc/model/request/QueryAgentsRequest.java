package com.callfire.api11.client.api.ccc.model.request;

import com.callfire.api11.client.api.common.model.request.QueryRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Query agents request
 */
public class QueryAgentsRequest extends QueryRequest {
    private Long campaignId;
    private String agentEmail;
    private Long agentGroupId;
    private String agentGroupName;
    private Boolean enabled;

    private QueryAgentsRequest() {
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public Long getAgentGroupId() {
        return agentGroupId;
    }

    public String getAgentGroupName() {
        return agentGroupName;
    }

    public Boolean getEnabled() {
        return enabled;
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
            .append("agentEmail", agentEmail)
            .append("agentGroupId", agentGroupId)
            .append("agentGroupName", agentGroupName)
            .append("enabled", enabled)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends QueryRequestBuilder<Builder, QueryAgentsRequest> {

        public Builder() {
            super(new QueryAgentsRequest());
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
         * Filter by agent email
         *
         * @param agentEmail agent email string
         * @return builder self reference
         */
        public Builder agentEmail(String agentEmail) {
            request.agentEmail = agentEmail;
            return this;
        }

        /**
         * Filter by agent group id
         *
         * @param agentGroupId agent group id Long
         * @return builder self reference
         */
        public Builder agentGroupId(Long agentGroupId) {
            request.agentGroupId = agentGroupId;
            return this;
        }

        /**
         * Filter by agent group name
         *
         * @param agentGroupName agent group name string
         * @return builder self reference
         */
        public Builder agentGroupName(String agentGroupName) {
            request.agentGroupName = agentGroupName;
            return this;
        }

        /**
         * Filter by enabled flag
         *
         * @param enabled enabled Boolean flag
         * @return builder self reference
         */
        public Builder enabled(Boolean enabled) {
            request.enabled = enabled;
            return this;
        }
    }
}
