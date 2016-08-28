package com.callfire.api11.client.api.numbers.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SearchNumbersRequest extends QueryRegionsRequest {
    private Boolean tollFree;
    private Integer count;

    private SearchNumbersRequest() {
    }

    public static Builder create() {
        return new Builder();
    }

    public Boolean getTollFree() {
        return tollFree;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("region", region)
            .toString();
    }

    public static class Builder extends QueryRegionsRequestBuilder<Builder, SearchNumbersRequest> {

        private Builder() {
            super(new SearchNumbersRequest());
        }

        public Builder tollFree(boolean tollFree) {
            request.tollFree = tollFree;
            return this;
        }

        public Builder count(int count) {
            request.count = count;
            return this;
        }
    }

}
