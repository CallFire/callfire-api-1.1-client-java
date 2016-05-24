package com.callfire.api11.client;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Exception thrown in case if platform returns HTTP code 404 - NOT FOUND, the resource requested does not exist
 *
 * @since 1.0
 */
public class ResourceNotFoundException extends CfApi11ApiException {
    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .toString();
    }
}
