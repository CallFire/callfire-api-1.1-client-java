package com.callfire.api11.client.api.texts.model.request;

import com.callfire.api11.client.api.common.model.request.QueryRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Query for auto-replies using optional number
 */
public class QueryAutoRepliesRequest extends QueryRequest {
    private String number;

    private QueryAutoRepliesRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("number", number)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends QueryRequestBuilder<Builder, QueryAutoRepliesRequest> {

        public Builder() {
            super(new QueryAutoRepliesRequest());
        }

        /**
         * Set E.164 11 digit number
         *
         * @param number E.164 11 digit number
         * @return builder self reference
         */
        public Builder number(String number) {
            request.number = number;
            return this;
        }
    }
}
