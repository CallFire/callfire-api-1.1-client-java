package com.callfire.api11.client.api.calls.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class IvrBroadcastConfig extends CallBroadcastConfig {
    private String dialplanXml;

    /**
     * Create request builder
     *
     * @return request build
     */
    public static IvrBroadcastConfigBuilder create() {
        return new IvrBroadcastConfigBuilder();
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
            .appendSuper(super.toString())
            .append("dialplanXml", dialplanXml)
            .toString();
    }

    public static class IvrBroadcastConfigBuilder
        extends BroadcastConfigBuilder<IvrBroadcastConfigBuilder, IvrBroadcastConfig> {
        public IvrBroadcastConfigBuilder() {
            super(new IvrBroadcastConfig());
        }

        /**
         * Set IVR XML, for more info goto https://answers.callfire.com/hc/en-us/sections/200187096-CallFire-XML
         *
         * @param dialplanXml IVR XML string
         * @return builder self reference
         */
        public IvrBroadcastConfigBuilder dialplanXml(String dialplanXml) {
            request.dialplanXml = dialplanXml;
            return this;
        }
    }
}
