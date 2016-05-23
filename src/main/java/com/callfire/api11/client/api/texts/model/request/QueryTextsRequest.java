package com.callfire.api11.client.api.texts.model.request;

import com.callfire.api11.client.api.common.model.request.ActionQueryRequest;
import com.callfire.api11.client.api.texts.model.TextResult;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class QueryTextsRequest extends ActionQueryRequest {
    private List<TextResult> result;

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public List<TextResult> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("result", result)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends ActionQueryRequestBuilder<Builder, QueryTextsRequest> {

        public Builder() {
            super(new QueryTextsRequest());
        }

        /**
         * Set list of results to query on
         *
         * @param results list of results to query on
         * @return builder self reference
         */
        public Builder result(List<TextResult> results) {
            request.result = results;
            return this;
        }
    }
}
