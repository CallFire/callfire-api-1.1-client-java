package com.callfire.api11.client;

/**
 * Client constants
 *
 * @since 1.0
 */
public interface ClientConstants {
    String BASE_PATH_PROPERTY = "com.callfire.api11.client.path";
    String USER_AGENT_PROPERTY = "com.callfire.api11.client.version";
    String PROXY_ADDRESS_PROPERTY = "com.callfire.api11.client.proxy.address";
    String PROXY_CREDENTIALS_PROPERTY = "com.callfire.api11.client.proxy.credentials";

    int DEFAULT_PROXY_PORT = 8080;

    String CLIENT_CONFIG_FILE = "/com/callfire/api11/client/callfire.properties";

    String PLACEHOLDER = "\\{\\}";

    String TIMESTAMP_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    String LOCALTIME_RESTRICTION_FORMAT_PATTERN = "HH:mm:ss";
}
