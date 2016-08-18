package com.callfire.api11.client.api.ccc.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Request for agent group update
 */
public class UpdateAgentGroupRequest extends CommonAgentGroupRequest {
    @QueryParamIgnore
    private Long id;
    private List<Long> campaignIds = new ArrayList<>();

    private UpdateAgentGroupRequest() {
    }

    public Long getId() {
        return id;
    }

    public List<Long> getCampaignIds() {
        return campaignIds;
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
            .append("id", id)
            .append("campaignIds", campaignIds)
            .toString();
    }

    /**
     * Builder class
     */
    public static class Builder extends CommonAgentGroupBuilder<Builder, UpdateAgentGroupRequest> {

        private Builder() {
            super(new UpdateAgentGroupRequest());
        }

        @Override
        protected void validate() {
        }

        /**
         * Set agent group id
         *
         * @param id agent group id Long
         * @return builder self reference
         */
        public Builder id(Long id) {
            request.id = id;
            return this;
        }

        /**
         * Set campaign ids
         *
         * @param campaignIds list of campaign ids
         * @return builder self reference
         */
        public Builder campaignIds(List<Long> campaignIds) {
            request.campaignIds = campaignIds;
            return this;
        }
    }
}
