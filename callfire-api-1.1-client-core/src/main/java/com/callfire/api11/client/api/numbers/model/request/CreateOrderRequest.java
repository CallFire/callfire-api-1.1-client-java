package com.callfire.api11.client.api.numbers.model.request;

import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.SerializeAsSingleString;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.numbers.model.Region;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Use this request to purchase numbers and keywords, it includes a list of numbers, list of keywords, region info,
 * or specifying toll-free. OrderId is returned from request.
 */
public class CreateOrderRequest extends CfApi11Model {
    @QueryParamName("tollFreeCount")
    private Integer tollFreeCount;
    @QueryParamName("localCount")
    private Integer localCount;
    @QueryParamObject
    private Region region = new Region();
    @SerializeAsSingleString
    private List<String> numbers = new ArrayList<>();
    @SerializeAsSingleString
    private List<String> keywords = new ArrayList<>();

    private CreateOrderRequest() {
    }

    public static Builder create() {
        return new Builder();
    }

    public Region getRegion() {
        return region;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public Integer getTollFreeCount() {
        return tollFreeCount;
    }

    public Integer getLocalCount() {
        return localCount;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("tollFreeCount", tollFreeCount)
            .append("localCount", localCount)
            .append("region", region)
            .append("numbers", numbers)
            .append("keywords", keywords)
            .toString();
    }

    public static class Builder extends AbstractBuilder<CreateOrderRequest> {

        private Builder() {
            super(new CreateOrderRequest());
        }

        public Builder tollFreeCount(Integer tollFreeCount) {
            request.tollFreeCount = tollFreeCount;
            return this;
        }

        public Builder numbers(List<String> numbers) {
            request.numbers = numbers;
            return this;
        }

        public Builder keywords(List<String> keywords) {
            request.keywords = keywords;
            return this;
        }

        public Builder rateCenter(String rateCenter) {
            request.region.setRateCenter(rateCenter);
            return this;
        }

        public Builder localCount(Integer localCount) {
            request.localCount = localCount;
            return this;
        }

        public Builder prefix(String prefix) {
            request.region.setPrefix(prefix);
            return this;
        }

        public Builder city(String city) {
            request.region.setCity(city);
            return this;
        }

        public Builder country(String country) {
            request.region.setCountry(country);
            return this;
        }

        public Builder lata(String lata) {
            request.region.setLata(lata);
            return this;
        }

        public Builder zipcode(String zipcode) {
            request.region.setZipcode(zipcode);
            return this;
        }

        public Builder state(String state) {
            request.region.setState(state);
            return this;
        }

        public Builder latitude(Float latitude) {
            request.region.setLatitude(latitude);
            return this;
        }

        public Builder longitude(Float longitude) {
            request.region.setLongitude(longitude);
            return this;
        }

        public Builder timeZone(String timeZone) {
            request.region.setTimeZone(timeZone);
            return this;
        }
    }
}
