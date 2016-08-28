package com.callfire.api11.client.api.numbers.model.request;

import com.callfire.api11.client.api.common.QueryParamName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class QueryNumbersRequest extends QueryRegionsRequest {
    @QueryParamName("LabelName")
    private String label;

    private QueryNumbersRequest() {
    }

    public static Builder create() {
        return new Builder();
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("region", region)
            .toString();
    }

    public static class Builder extends QueryRegionsRequestBuilder<Builder, QueryNumbersRequest> {

        private Builder() {
            super(new QueryNumbersRequest());
        }

        public Builder label(String label) {
            request.label = label;
            return this;
        }
    }

}
