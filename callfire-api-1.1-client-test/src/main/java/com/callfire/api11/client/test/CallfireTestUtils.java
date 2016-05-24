package com.callfire.api11.client.test;

import com.callfire.api11.client.CfApi11Client;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.HttpClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class
 */
public class CallfireTestUtils {
    private CallfireTestUtils() {
    }

    public static String getJsonPayload(String path) {
        try {
            StringBuilder result = new StringBuilder();
            String json = IOUtils.toString(MockHttpClient.class.getResourceAsStream(path), Charsets.UTF_8);
            List<String> lines = Arrays.asList(StringUtils.split(json, "\n"));
            for (String line : lines) {
                line = StringUtils.trim(line);
                line = line.replaceAll("\": ", "\":");
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Pair<Integer, String>> getJsonResponseMapping(CfApi11Client client) {
        HttpClient httpClient = client.getRestApiClient().getHttpClient();
        if (httpClient instanceof MockHttpClient) {
            return ((MockHttpClient) httpClient).getJsonResponses();
        }
        return new HashMap<>();
    }
}
