package com.callfire.api11.client.auth;

import com.callfire.api11.client.CfApi11ClientException;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * Provides authentication interface to client for different auth types
 */
public interface Authentication {
    /**
     * Apply authentication to http request
     *
     * @param request HTTP request
     * @return updated http request
     * @throws CfApi11ClientException in case error has occurred in client
     */
    HttpUriRequest apply(HttpUriRequest request);
}
