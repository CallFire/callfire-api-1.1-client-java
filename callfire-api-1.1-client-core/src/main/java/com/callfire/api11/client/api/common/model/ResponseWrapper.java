package com.callfire.api11.client.api.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Each response object is wrapped by additional object
 */
public class ResponseWrapper<T> {
    private T response;

    public T getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("response", response)
            .toString();
    }
}
