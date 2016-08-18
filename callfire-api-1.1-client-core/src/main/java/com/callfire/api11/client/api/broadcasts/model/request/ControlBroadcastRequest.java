package com.callfire.api11.client.api.broadcasts.model.request;

import com.callfire.api11.client.api.broadcasts.model.BroadcastCommand;
import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Common request to add agent groups to ccc campaign
 */
public class ControlBroadcastRequest extends CfApi11Model {
    @QueryParamIgnore
    private Long id;
    private BroadcastCommand command;
    private Integer maxActive;

    protected ControlBroadcastRequest() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("command", command)
            .append("maxActive", maxActive)
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

    @SuppressWarnings("unchecked")
    public static class ControlBroadcastRequestBuilder extends AbstractBuilder<ControlBroadcastRequest> {

        public ControlBroadcastRequestBuilder() {
            super(new ControlBroadcastRequest());
        }

        @Override
        protected void validate() {
            Validate.notNull(request.id, "id should be specified");
            Validate.notNull(request.command, "command should be specified");
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
    }
}
