package com.callfire.api11.client.api.calls.model.request;

import com.callfire.api11.client.api.calls.model.CallBroadcastConfig;
import com.callfire.api11.client.api.calls.model.IvrBroadcastConfig;
import com.callfire.api11.client.api.calls.model.IvrBroadcastConfig.IvrBroadcastConfigBuilder;
import com.callfire.api11.client.api.calls.model.VoiceBroadcastConfig;
import com.callfire.api11.client.api.calls.model.VoiceBroadcastConfig.VoiceBroadcastConfigBuilder;
import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.model.BroadcastType;
import com.callfire.api11.client.api.common.model.request.SendRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SendCallRequest extends SendRequest {
    private Integer maxActive;
    @QueryParamObject
    private VoiceBroadcastConfig voiceBroadcastConfig;
    @QueryParamObject
    private IvrBroadcastConfig ivrBroadcastConfig;

    private SendCallRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Get max simultaneous calls
     *
     * @return max simultaneous calls
     */
    public Integer getMaxActive() {
        return maxActive;
    }

    /**
     * Get voice broadcast's config
     *
     * @return Get voice broadcast's config
     */
    public VoiceBroadcastConfig getVoiceBroadcastConfig() {
        return voiceBroadcastConfig;
    }

    /**
     * Get IVR broadcast's config
     *
     * @return IVR broadcast's config
     */
    public IvrBroadcastConfig getIvrBroadcastConfig() {
        return ivrBroadcastConfig;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("maxActive", maxActive)
            .append("voiceBroadcastConfig", voiceBroadcastConfig)
            .append("ivrBroadcastConfig", ivrBroadcastConfig)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends SendRequestBuilder<Builder, SendCallRequest> {

        public Builder() {
            super(new SendCallRequest());
        }

        /**
         * Set max simultaneous calls
         *
         * @param maxActive number of max simultaneous calls
         * @return builder self reference
         */
        public Builder maxActive(int maxActive) {
            request.maxActive = maxActive;
            return this;
        }

        /**
         * Set configuration of outbound call,
         *
         * @param config outbound call configuration
         * @return builder self reference
         */
        public Builder config(CallBroadcastConfig config) {
            if (config instanceof VoiceBroadcastConfig) {
                request.voiceBroadcastConfig = (VoiceBroadcastConfig) config;
                request.type = BroadcastType.VOICE;
            } else if (config instanceof IvrBroadcastConfig) {
                request.ivrBroadcastConfig = (IvrBroadcastConfig) config;
                request.type = BroadcastType.IVR;
            }
            return this;
        }

        /**
         * Set IVR configuration for outbound call
         *
         * @param builder configuration build
         * @return builder self reference
         */
        public Builder voiceConfig(VoiceBroadcastConfigBuilder builder) {
            return config(builder.build());
        }

        /**
         * Set IVR configuration for outbound call
         *
         * @param builder configuration builder
         * @return builder self reference
         */
        public Builder ivrConfig(IvrBroadcastConfigBuilder builder) {
            return config(builder.build());
        }

        @Override
        public SendCallRequest build() {
            if (request.voiceBroadcastConfig != null && request.ivrBroadcastConfig != null) {
                throw new IllegalStateException(
                    "VoiceBroadcastConfig and IvrBroadcastConfig mustn't be set at the same time.");
            }
            return super.build();
        }
    }
}
