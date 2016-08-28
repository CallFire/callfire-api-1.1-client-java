package com.callfire.api11.client.api.numbers.model;

import com.callfire.api11.client.api.common.QueryParamObject;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Inbound common call configuration model
 */
public class InboundCallConfig {
    @QueryParamObject
    private CallTrackingConfig inboundCallConfig;
    @QueryParamObject
    private IvrInboundConfig ivrInboundConfig;

    public InboundCallConfig() {
    }

    public InboundCallConfig(IvrInboundConfig ivrInboundConfig) {
        this.ivrInboundConfig = ivrInboundConfig;
    }

    public InboundCallConfig(CallTrackingConfig inboundCallConfig) {

        this.inboundCallConfig = inboundCallConfig;
    }

    public CallTrackingConfig getInboundCallConfig() {
        return inboundCallConfig;
    }

    public void setInboundCallConfig(CallTrackingConfig inboundCallConfig) {
        this.inboundCallConfig = inboundCallConfig;
    }

    public IvrInboundConfig getIvrInboundConfig() {
        return ivrInboundConfig;
    }

    public void setIvrInboundConfig(IvrInboundConfig ivrInboundConfig) {
        this.ivrInboundConfig = ivrInboundConfig;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("inboundCallConfig", inboundCallConfig)
            .append("ivrInboundConfig", ivrInboundConfig)
            .toString();
    }
}
