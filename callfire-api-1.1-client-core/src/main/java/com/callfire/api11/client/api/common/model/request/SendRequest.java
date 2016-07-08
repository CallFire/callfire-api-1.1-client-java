package com.callfire.api11.client.api.common.model.request;

import com.callfire.api11.client.api.broadcasts.model.BroadcastType;
import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.common.model.ToNumber;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Generic send request
 */
public abstract class SendRequest extends CfApi11Model {
    protected String requestId;
    protected BroadcastType type;
    protected String broadcastName;
    protected Boolean scrubBroadcastDuplicates;
    @QueryParamName("To")
    protected List<ToNumber> toNumber;
    protected List<String> label;

    public String getRequestId() {
        return requestId;
    }

    public BroadcastType getType() {
        return type;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public Boolean getScrubDuplicates() {
        return scrubBroadcastDuplicates;
    }

    public List<ToNumber> getRecipients() {
        return toNumber;
    }

    public List<String> getLabels() {
        return label;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("requestId", requestId)
            .append("type", type)
            .append("broadcastName", broadcastName)
            .append("scrubDuplicates", scrubBroadcastDuplicates)
            .append("toNumber", toNumber)
            .append("label", label)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class SendRequestBuilder<B extends SendRequestBuilder, R extends SendRequest>
        extends AbstractBuilder<R> {

        public SendRequestBuilder(R request) {
            super(request);
        }

        /**
         * Set broadcast name
         *
         * @param name set broadcast's name
         * @return builder self reference
         */
        public B name(String name) {
            request.broadcastName = name;
            return (B) this;
        }

        /**
         * Set to scrub duplicates, default: false
         *
         * @param scrubDuplicates if set to true contact duplicates will be scrubbed
         * @return builder self reference
         */
        public B scrubDuplicates(boolean scrubDuplicates) {
            request.scrubBroadcastDuplicates = scrubDuplicates;
            return (B) this;
        }

        /**
         * Set list of recipients
         *
         * @param toNumber set list of recipients
         * @return builder self reference
         */
        public B recipients(List<ToNumber> toNumber) {
            request.toNumber = toNumber;
            return (B) this;
        }

        /**
         * Set call/text labels
         *
         * @param labels labels to set
         * @return builder self reference
         */
        public B labels(List<String> labels) {
            request.label = labels;
            return (B) this;
        }
    }
}
