package com.callfire.api11.client.api.numbers.model;

import com.callfire.api11.client.api.common.QueryParamName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Inbound ivr call configuration model
 */
public class IvrInboundConfig {
    @JsonProperty("@id")
    @QueryParamName("IvrInboundConfig[id]")
    private Long id;
    private String dialplanXml;

    public IvrInboundConfig() {
    }

    public IvrInboundConfig(String dialplanXml) {
        this.dialplanXml = dialplanXml;
    }

    public Long getId() {
        return id;
    }

    public String getDialplanXml() {
        return dialplanXml;
    }

    public void setDialplanXml(String dialplanXml) {
        this.dialplanXml = dialplanXml;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("dialplanXml", dialplanXml)
            .toString();
    }
}
