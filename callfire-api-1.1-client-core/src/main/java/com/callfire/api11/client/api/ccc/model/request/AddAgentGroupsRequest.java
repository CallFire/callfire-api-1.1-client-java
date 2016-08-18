package com.callfire.api11.client.api.ccc.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Common request to add agent groups to ccc campaign
 */
public class AddAgentGroupsRequest extends CfApi11Model {
    @QueryParamIgnore
    private Long campaignId;
    private List<Long> agentGroupIds = new ArrayList<>();

    protected AddAgentGroupsRequest() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("campaignId", campaignId)
            .append("agentGroupIds", agentGroupIds)
            .toString();
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static AddAgentGroupsRequestBuilder create() {
        return new AddAgentGroupsRequestBuilder();
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public List<Long> getAgentGroupIds() {
        return agentGroupIds;
    }

    @SuppressWarnings("unchecked")
    public static class AddAgentGroupsRequestBuilder extends AbstractBuilder<AddAgentGroupsRequest> {

        public AddAgentGroupsRequestBuilder() {
            super(new AddAgentGroupsRequest());
        }

        @Override
        protected void validate() {
            Validate.notNull(request.campaignId, "ccc campaign id should be specified");
            Validate.notEmpty(request.agentGroupIds, "agent group ids should be specified");
        }

        /**
         * Set ccc campaign id to add agent groups to
         *
         * @param campaignId campaign id
         * @return builder self reference
         */
        public AddAgentGroupsRequestBuilder campaignId(Long campaignId) {
            request.campaignId = campaignId;
            return this;
        }

        /**
         * Set list of existing agent group ids to add to campaign
         *
         * @param agentGroupIds list of existing agent group ids
         * @return builder self reference
         */
        public AddAgentGroupsRequestBuilder agentGroupIds(List<Long> agentGroupIds) {
            request.agentGroupIds = agentGroupIds;
            return this;
        }

    }
}
