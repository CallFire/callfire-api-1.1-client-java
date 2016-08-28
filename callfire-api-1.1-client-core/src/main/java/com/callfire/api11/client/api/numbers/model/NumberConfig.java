package com.callfire.api11.client.api.numbers.model;

import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.QueryParamObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.callfire.api11.client.api.numbers.model.InboundType.IVR;
import static com.callfire.api11.client.api.numbers.model.InboundType.TRACKING;

public class NumberConfig {
    private NumberFeature callFeature;
    private NumberFeature textFeature;
    @QueryParamName("InboundCallConfigurationType")
    @JsonProperty("InboundCallConfigurationType")
    private InboundType inboundCallConfigType;
    @QueryParamObject
    @JsonProperty("InboundCallConfiguration")
    private InboundCallConfig inboundCallConfig;

    public NumberFeature getCallFeature() {
        return callFeature;
    }

    public void setCallFeature(NumberFeature callFeature) {
        this.callFeature = callFeature;
    }

    public NumberFeature getTextFeature() {
        return textFeature;
    }

    public void setTextFeature(NumberFeature textFeature) {
        this.textFeature = textFeature;
    }

    public InboundType getInboundCallConfigType() {
        return inboundCallConfigType;
    }

    public void setInboundCallConfigType(InboundType inboundCallConfigType) {
        this.inboundCallConfigType = inboundCallConfigType;
    }

    public InboundCallConfig getInboundCallConfig() {
        return inboundCallConfig;
    }

    public void setInboundCallConfig(InboundCallConfig config) {
        this.inboundCallConfig = config;
        if (config != null) {
            inboundCallConfigType = config.getIvrInboundConfig() != null ? IVR : inboundCallConfigType;
            inboundCallConfigType = config.getInboundCallConfig() != null ? TRACKING : inboundCallConfigType;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("callFeature", callFeature)
            .append("textFeature", textFeature)
            .append("inboundCallConfigType", inboundCallConfigType)
            .append("inboundCallConfig", inboundCallConfig)
            .toString();
    }
}
