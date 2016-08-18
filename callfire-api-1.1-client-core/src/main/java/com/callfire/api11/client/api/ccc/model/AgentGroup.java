
package com.callfire.api11.client.api.ccc.model;

import com.callfire.api11.client.api.common.SpacedStringAsListDeserializer;
import com.callfire.api11.client.api.common.SpacedStringAsListOfLongDeserializer;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class AgentGroup extends CfApi11Model {

    @JsonProperty("@id")
    private Long id;
    private String name;
    @JsonDeserialize(using = SpacedStringAsListOfLongDeserializer.class)
    private List<Long> campaignIds;
    @JsonDeserialize(using = SpacedStringAsListOfLongDeserializer.class)
    private List<Long> agentIds;
    @JsonDeserialize(using = SpacedStringAsListDeserializer.class)
    private List<String> agentEmails;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("campaignIds", campaignIds)
            .append("agentIds", agentIds)
            .append("campaignIds", campaignIds)
            .append("agentEmails", agentEmails)
            .toString();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Long> getCampaignIds() {
        return campaignIds;
    }

    public List<Long> getAgentIds() {
        return agentIds;
    }

    public List<String> getAgentEmails() {
        return agentEmails;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCampaignIds(List<Long> campaignIds) {
        this.campaignIds = campaignIds;
    }

    public void setAgentIds(List<Long> agentIds) {
        this.agentIds = agentIds;
    }

    public void setAgentEmails(List<String> agentEmails) {
        this.agentEmails = agentEmails;
    }
}
