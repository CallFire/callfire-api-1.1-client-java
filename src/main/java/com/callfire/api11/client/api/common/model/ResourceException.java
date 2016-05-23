package com.callfire.api11.client.api.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Information about occurred exception on server-side
 */
public class ResourceException {
    private Integer httpStatus;
    private String message;

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("httpStatus", httpStatus)
            .append("message", message)
            .toString();
    }
}
