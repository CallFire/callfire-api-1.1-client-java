package com.callfire.api11.client.api.calls.model.request;

import com.callfire.api11.client.api.calls.model.CallResult;
import com.callfire.api11.client.api.common.model.request.ActionQueryRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class QueryCallsRequest extends ActionQueryRequest {
    private List<CallResult> result;

    private QueryCallsRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public List<CallResult> getResult() {
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
    public static class Builder extends ActionQueryRequestBuilder<Builder, QueryCallsRequest> {

        public Builder() {
            super(new QueryCallsRequest());
        }

        /**
         * Set list of Results to query on
         *
         * @param results list of Results to query on
         * @return builder self reference
         */
        public Builder result(List<CallResult> results) {
            request.result = results;
            return this;
        }
    }
}
