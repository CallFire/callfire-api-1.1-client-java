package com.callfire.api11.client.api.broadcasts.model;

import com.callfire.api11.client.api.calls.model.IvrBroadcastConfig;
import com.callfire.api11.client.api.calls.model.VoiceBroadcastConfig;
import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.texts.model.TextBroadcastConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

/**
 * Represents broadcast object in Callfire system
 */
public class Broadcast extends CfApi11Model {
    @JsonProperty("@id")
    private Long id;
    private String name;
    private BroadcastStatus status;
    private Date lastModified;
    private BroadcastType type;
    @JsonProperty("Label")
    @QueryParamName("Label")
    private List<String> labels;
    @QueryParamObject
    private VoiceBroadcastConfig voiceBroadcastConfig;
    @QueryParamObject
    private TextBroadcastConfig textBroadcastConfig;
    @QueryParamObject
    private IvrBroadcastConfig ivrBroadcastConfig;
    // not implemented yet
    //    private CccBroadcastConfig cccBroadcastConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BroadcastStatus getStatus() {
        return status;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public BroadcastType getType() {
        return type;
    }

    public void setType(BroadcastType type) {
        this.type = type;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public VoiceBroadcastConfig getVoiceBroadcastConfig() {
        return voiceBroadcastConfig;
    }

    public void setVoiceBroadcastConfig(VoiceBroadcastConfig voiceBroadcastConfig) {
        this.voiceBroadcastConfig = voiceBroadcastConfig;
    }

    public TextBroadcastConfig getTextBroadcastConfig() {
        return textBroadcastConfig;
    }

    public void setTextBroadcastConfig(TextBroadcastConfig textBroadcastConfig) {
        this.textBroadcastConfig = textBroadcastConfig;
    }

    public IvrBroadcastConfig getIvrBroadcastConfig() {
        return ivrBroadcastConfig;
    }

    public void setIvrBroadcastConfig(IvrBroadcastConfig ivrBroadcastConfig) {
        this.ivrBroadcastConfig = ivrBroadcastConfig;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("name", name)
            .append("status", status)
            .append("lastModified", lastModified)
            .append("type", type)
            .append("labels", labels)
            .append("voiceBroadcastConfig", voiceBroadcastConfig)
            .append("textBroadcastConfig", textBroadcastConfig)
            .append("ivrBroadcastConfig", ivrBroadcastConfig)
            .toString();
    }
}
