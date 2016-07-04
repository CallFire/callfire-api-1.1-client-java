package com.callfire.api11.client.api.common.model;

/**
 * Common builder for request objects
 *
 * @param <R> request type
 */
public class AbstractBuilder<R extends CfApi11Model> {
    protected final R request;

    protected AbstractBuilder(R request) {
        this.request = request;
    }

    /**
     * Build request
     *
     * @return find request pojo
     */
    public R build() {
        validate();
        return request;
    }

    /**
     * Validates request
     */
    protected void validate() {
    }
}
