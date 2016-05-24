package com.callfire.api11.client;

import com.callfire.api11.client.test.MockHttpClient;

/**
 * Factory builds Callfire client with mocked http client
 */
public class MockClientFactory {

    /**
     * Creates mock client which just returns stub objects instead doing call to Callfire services.
     * <p>
     * Please note that testing mode is limited to return only stub data and it doesn't work as sandbox account.
     * Lots of validations perform on server-side thus invalid
     * data (e.g. invalid To/From numbers, validation performs on server) will work with mock client
     * but fails with production one.
     *
     * @return Mock version of {@link CfApi11Client}
     */
    public static CfApi11Client newClient() {
        CfApi11Client client = new CfApi11Client("", "");
        client.getRestApiClient().setHttpClient(new MockHttpClient());
        client.getRestApiClient().mockClient = true;
        return client;
    }
}
