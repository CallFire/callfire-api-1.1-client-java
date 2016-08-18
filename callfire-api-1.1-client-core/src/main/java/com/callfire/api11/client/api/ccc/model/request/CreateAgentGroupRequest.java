package com.callfire.api11.client.api.ccc.model.request;

/**
 * Request for agent group creation
 */
public class CreateAgentGroupRequest extends CommonAgentGroupRequest {
    private CreateAgentGroupRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Builder class
     */
    public static class Builder extends CommonAgentGroupBuilder<Builder, CreateAgentGroupRequest> {
        private Builder() {
            super(new CreateAgentGroupRequest());
        }
    }
}
