package com.callfire.api11.client.api.ccc.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Common request for agent group creation
 */
public abstract class CommonAgentGroupRequest extends AgentsDataRequest {
    protected String requestId;
    protected String name;

    public String getName() {
        return name;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .append("requestId", requestId)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static abstract class CommonAgentGroupBuilder<B extends CommonAgentGroupBuilder, R extends CommonAgentGroupRequest>
        extends AgentsDataBuilder<B, R> {

        public CommonAgentGroupBuilder(R request) {
            super(request);
        }

        /**
         * Set agent group name
         *
         * @param name agent group name string
         * @return builder self reference
         */
        public B name(String name) {
            request.name = name;
            return (B) this;
        }

        /**
         * Set request id
         *
         * @param requestId request id
         * @return builder self reference
         */
        public B requestId(String requestId) {
            request.requestId = requestId;
            return (B) this;
        }
    }
}
