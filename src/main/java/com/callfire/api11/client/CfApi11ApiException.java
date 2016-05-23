package com.callfire.api11.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Callfire API exception is thrown by client in case of 4xx or 5xx HTTP code
 * response
 *
 * @since 1.0
 */
public class CfApi11ApiException extends RuntimeException {
    protected String message;

    public CfApi11ApiException(String message) {
        this.message = message;
    }

    /**
     * Get detailed error message with HTTP code, help link, etc.
     *
     * @return detailed message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Set detailed error message with HTTP code, help link, etc.
     *
     * @param message detailed message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("message", message)
            .toString();
    }
}
