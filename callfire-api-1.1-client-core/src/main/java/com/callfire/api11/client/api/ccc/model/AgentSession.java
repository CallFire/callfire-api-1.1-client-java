
package com.callfire.api11.client.api.ccc.model;

import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class AgentSession extends CfApi11Model {

    private Long agentId;
    private Long campaignId;
    private AgentState agentState;
    private Integer callCount;
    private Date start;
    private Date lastUpdate;
    @JsonProperty("@id")
    private Long id;

    public Long getAgentId() {
        return agentId;
    }

    public Long getId() {
        return id;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public Date getStart() {
        return start;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public AgentState getAgentState() {
        return agentState;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("agentId", agentId)
            .append("campaignId", campaignId)
            .append("agentState", agentState)
            .append("callCount", callCount)
            .append("start", start)
            .append("lastUpdate", lastUpdate)
            .append("id", id)
            .toString();
    }

}
