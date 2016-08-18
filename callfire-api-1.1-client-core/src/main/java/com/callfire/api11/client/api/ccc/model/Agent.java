
package com.callfire.api11.client.api.ccc.model;

import com.callfire.api11.client.api.common.SpacedStringAsObjectList;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

public class Agent extends CfApi11Model {

    private Boolean enabled;
    private String name;
    private String email;
    private Date lastLogin;
    @JsonDeserialize(using = SpacedStringAsObjectList.class)
    private List<Long> campaignIds;
    @JsonDeserialize(using = SpacedStringAsObjectList.class)
    private List<Long> groupIds;
    @JsonProperty("@id")
    private Long id;

    public Boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public List<Long> getCampaignIds() {
        return campaignIds;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public Long getId() {
        return id;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCampaignIds(List<Long> campaignIds) {
        this.campaignIds = campaignIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("enabled", enabled)
            .append("name", name)
            .append("email", email)
            .append("lastLogin", lastLogin)
            .append("campaignIds", campaignIds)
            .append("groupIds", groupIds)
            .append("id", id)
            .toString();
    }

}
