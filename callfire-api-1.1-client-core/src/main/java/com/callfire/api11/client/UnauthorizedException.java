package com.callfire.api11.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Exception thrown in case if platform returns HTTP code 401 - Unauthorized, API Key missing or invalid
 *
 * @since 1.0
 */
public class UnauthorizedException extends CfApi11ApiException {
    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
