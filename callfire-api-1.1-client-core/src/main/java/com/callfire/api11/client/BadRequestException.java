package com.callfire.api11.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Exception thrown in case if platform returns HTTP code 400 - Bad request, the request was formatted improperly.
 *
 * @since 1.0
 */
public class BadRequestException extends CfApi11ApiException {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
