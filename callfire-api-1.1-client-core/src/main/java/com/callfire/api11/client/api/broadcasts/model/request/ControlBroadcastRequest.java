package com.callfire.api11.client.api.broadcasts.model.request;

import com.callfire.api11.client.api.broadcasts.model.BroadcastCommand;
import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Common request to add agent groups to ccc campaign
 */
public class ControlBroadcastRequest extends CfApi11Model {
    @QueryParamIgnore
    private Long id;
    private BroadcastCommand command;
    private Integer maxActive;
    protected String requestId;

    protected ControlBroadcastRequest() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("command", command)
            .append("maxActive", maxActive)
            .append("requestId", requestId)
            .toString();
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static ControlBroadcastRequestBuilder create() {
        return new ControlBroadcastRequestBuilder();
    }

    public Long getId() {
        return id;
    }

    public BroadcastCommand getCommand() {
        return command;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public String getRequestId() {
        return requestId;
    }

    @SuppressWarnings("unchecked")
    public static class ControlBroadcastRequestBuilder extends AbstractBuilder<ControlBroadcastRequest> {

        public ControlBroadcastRequestBuilder() {
            super(new ControlBroadcastRequest());
        }

        @Override
        protected void validate() {
            if (request.id == null) {
                throw new IllegalStateException("id should be specified");
            }
            if (request.command == null)
                throw new IllegalStateException("command should be specified");
        }

        /**
         * Set ccc campaign id
         *
         * @param id campaign id
         * @return builder self reference
         */
        public ControlBroadcastRequestBuilder id(Long id) {
            request.id = id;
            return this;
        }

        /**
         * Set command to perform for broadcast
         *
         * @param command command to perform
         * @return builder self reference
         */
        public ControlBroadcastRequestBuilder command(BroadcastCommand command) {
            request.command = command;
            return this;
        }

        /**
         * Set campaign max active parameter
         *
         * @param maxActive max active to set
         * @return builder self reference
         */
        public ControlBroadcastRequestBuilder maxActive(Integer maxActive) {
            request.maxActive = maxActive;
            return this;
        }

        /**
         * Set request id
         *
         * @param requestId request id
         * @return builder self reference
         */
        public ControlBroadcastRequestBuilder requestId(String requestId) {
            request.requestId = requestId;
            return this;
        }

    }
}
