
package com.callfire.api11.client.api.ccc.model;

import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AgentInvite extends CfApi11Model {
    private String agentInviteUri;
    private Long campaignId;

    public String getAgentInviteUri() {
        return agentInviteUri;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("agentInviteUri", agentInviteUri)
            .append("campaignId", campaignId)
            .toString();
    }

}
