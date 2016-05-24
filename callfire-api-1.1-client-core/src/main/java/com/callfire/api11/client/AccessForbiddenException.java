package com.callfire.api11.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Exception thrown in case if platform returns HTTP code 403 - Forbidden, insufficient permissions
 *
 * @since 1.0
 */
public class AccessForbiddenException extends CfApi11ApiException {
    public AccessForbiddenException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
