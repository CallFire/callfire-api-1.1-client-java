package com.callfire.api11.client.api.texts.model;

import com.callfire.api11.client.api.broadcasts.model.BroadcastConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextBroadcastConfig extends BroadcastConfig {
    private String message;
    private BigMessageStrategy bigMessageStrategy;

    /**
     * Create request builder
     *
     * @return request build
     */
    public static TextBroadcastConfigBuilder create() {
        return new TextBroadcastConfigBuilder();
    }

    public String getMessage() {
        return message;
    }

    public BigMessageStrategy getBigMessageStrategy() {
        return bigMessageStrategy;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBigMessageStrategy(BigMessageStrategy bigMessageStrategy) {
        this.bigMessageStrategy = bigMessageStrategy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("message", message)
            .append("bigMessageStrategy", bigMessageStrategy)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class TextBroadcastConfigBuilder
        extends BroadcastConfigBuilder<TextBroadcastConfigBuilder, TextBroadcastConfig> {
        public TextBroadcastConfigBuilder() {
            super(new TextBroadcastConfig());
        }

        /**
         * Set 160 char or less message to be sent in text broadcast. Use rented 'keyword' in message if need
         * response
         *
         * @param message message
         * @return builder self reference
         */
        public TextBroadcastConfigBuilder message(String message) {
            request.message = message;
            return this;
        }

        /**
         * Set strategy if message is over 160 chars (default: SEND_MULTIPLE)
         *
         * @param strategy strategy to set
         * @return builder self reference
         */
        public TextBroadcastConfigBuilder strategy(BigMessageStrategy strategy) {
            request.bigMessageStrategy = strategy;
            return this;
        }
    }
}
