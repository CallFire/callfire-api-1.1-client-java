package com.callfire.api11.client.auth;

import com.callfire.api11.client.CfApi11ClientException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.auth.BasicScheme;

/**
 * Implementation of basic auth scheme
 */
public class BasicAuth implements Authentication {
    private UsernamePasswordCredentials credentials;

    /**
     * Constructs basic auth from provided credentials
     *
     * @param username api username
     * @param password api password
     */
    public BasicAuth(String username, String password) {
        credentials = new UsernamePasswordCredentials(username, password);
    }

    @Override
    public HttpUriRequest apply(HttpUriRequest request) {
        try {
            request.addHeader(new BasicScheme().authenticate(credentials, request, null));
            return request;
        } catch (AuthenticationException e) {
            throw new CfApi11ClientException(e);
        }
    }
}
