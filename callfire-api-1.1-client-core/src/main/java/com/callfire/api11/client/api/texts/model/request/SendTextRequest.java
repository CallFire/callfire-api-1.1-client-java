package com.callfire.api11.client.api.texts.model.request;

import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.broadcasts.model.BroadcastType;
import com.callfire.api11.client.api.common.model.request.SendRequest;
import com.callfire.api11.client.api.texts.model.TextBroadcastConfig;
import com.callfire.api11.client.api.texts.model.TextBroadcastConfig.TextBroadcastConfigBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SendTextRequest extends SendRequest {
    private Long broadcastId;
    private Boolean useDefaultBroadcast = false;
    @QueryParamObject
    private TextBroadcastConfig textBroadcastConfig;

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public Long getBroadcastId() {
        return broadcastId;
    }

    public Boolean getUseDefaultBroadcast() {
        return useDefaultBroadcast;
    }

    public TextBroadcastConfig getTextBroadcastConfig() {
        return textBroadcastConfig;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("broadcastId", broadcastId)
            .append("useDefaultBroadcast", useDefaultBroadcast)
            .append("textBroadcastConfig", textBroadcastConfig)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends SendRequestBuilder<SendTextRequest.Builder, SendTextRequest> {

        public Builder() {
            super(new SendTextRequest());
        }

        /**
         * Set broadcastId to send message from
         *
         * @param broadcastId broadcastId to send message from
         * @return builder self reference
         */
        public Builder broadcastId(long broadcastId) {
            request.broadcastId = broadcastId;
            return this;
        }

        /**
         * If true send text through existing default broadcast (default: false)
         *
         * @param useDefaultBroadcast number of max simultaneous calls
         * @return builder self reference
         */
        public Builder defaultBroadcast(boolean useDefaultBroadcast) {
            request.useDefaultBroadcast = useDefaultBroadcast;
            return this;
        }

        /**
         * Set text configuration
         *
         * @param config text configuration
         * @return builder self reference
         */
        public Builder config(TextBroadcastConfig config) {
            request.type = BroadcastType.TEXT;
            request.textBroadcastConfig = config;
            return this;
        }

        /**
         * Set text configuration
         *
         * @param builder configuration builder
         * @return builder self reference
         */
        public Builder config(TextBroadcastConfigBuilder builder) {
            return config(builder.build());
        }
    }
}
