package com.callfire.api11.client.api.numbers.model.request;

import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.numbers.model.CallTrackingConfig;
import com.callfire.api11.client.api.numbers.model.InboundCallConfig;
import com.callfire.api11.client.api.numbers.model.IvrInboundConfig;
import com.callfire.api11.client.api.numbers.model.NumberConfig;
import com.callfire.api11.client.api.numbers.model.NumberFeature;
import org.apache.commons.lang3.Validate;

public class ConfigureNumberRequest extends CfApi11Model {
    private String number;
    @QueryParamObject
    private NumberConfig numberConfig;

    private ConfigureNumberRequest() {
        numberConfig = new NumberConfig();
    }

    public static Builder create() {
        return new Builder();
    }

    public String getNumber() {
        return number;
    }

    public NumberConfig getNumberConfig() {
        return numberConfig;
    }

    public static class Builder extends AbstractBuilder<ConfigureNumberRequest> {

        private Builder() {
            super(new ConfigureNumberRequest());
        }

        public Builder number(String number) {
            request.number = number;
            return this;
        }

        public Builder callFeature(NumberFeature callFeature) {
            request.numberConfig.setCallFeature(callFeature);
            return this;
        }

        public Builder textFeature(NumberFeature textFeature) {
            request.numberConfig.setTextFeature(textFeature);
            return this;
        }

        public Builder ivrInboundConfig(IvrInboundConfig config) {
            request.numberConfig.setInboundCallConfig(new InboundCallConfig(config));
            return this;
        }

        public Builder callTrackingConfig(CallTrackingConfig config) {
            request.numberConfig.setInboundCallConfig(new InboundCallConfig(config));
            return this;
        }

        @Override
        protected void validate() {
            Validate.notBlank(request.number, "number is required");
        }
    }
}
