package com.callfire.api11.client.api.numbers.model.request;

import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.numbers.model.Region;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class QueryRegionsRequest extends QueryRequest {
    @QueryParamObject
    protected Region region;

    protected QueryRegionsRequest() {
        region = new Region();
    }

    public static QueryRegionsRequestBuilder<QueryRegionsRequestBuilder, QueryRegionsRequest> createRegionsQuery() {
        return new QueryRegionsRequestBuilder<>();
    }

    public Region getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("region", region)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class QueryRegionsRequestBuilder<B extends QueryRegionsRequestBuilder, R extends QueryRegionsRequest>
        extends QueryRequestBuilder<B, R> {

        protected QueryRegionsRequestBuilder() {
            super((R) new QueryRegionsRequest());
        }

        protected QueryRegionsRequestBuilder(R request) {
            super(request);
        }

        public B prefix(String prefix) {
            request.region.setPrefix(prefix);
            return (B) this;
        }

        public B city(String city) {
            request.region.setCity(city);
            return (B) this;
        }

        public B state(String state) {
            request.region.setState(state);
            return (B) this;
        }

        public B zipcode(String zipcode) {
            request.region.setZipcode(zipcode);
            return (B) this;
        }

        public B country(String country) {
            request.region.setCountry(country);
            return (B) this;
        }

        public B lata(String lata) {
            request.region.setLata(lata);
            return (B) this;
        }

        public B rateCenter(String rateCenter) {
            request.region.setRateCenter(rateCenter);
            return (B) this;
        }

        public B latitude(Float latitude) {
            request.region.setLatitude(latitude);
            return (B) this;
        }

        public B longitude(Float longitude) {
            request.region.setLongitude(longitude);
            return (B) this;
        }

        public B timeZone(String timeZone) {
            request.region.setTimeZone(timeZone);
            return (B) this;
        }

        @Override
        public R build() {
            return super.build();
        }
    }

}
