package com.callfire.api11.client.api;

import com.callfire.api11.client.CfApi11Client;
import com.callfire.api11.client.JsonConverter;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Base api test class
 */
public class AbstractApiTest {
    protected static final String BASE_PATH = "/com/callfire/api11/client/api";

    @Rule
    public ExpectedException ex = ExpectedException.none();

    protected CfApi11Client client;
    protected JsonConverter jsonConverter;

    @Spy
    protected HttpClient mockHttpClient;
    @Mock
    protected CloseableHttpResponse mockHttpResponse;

    public AbstractApiTest() {
        client = new CfApi11Client("login", "password");
        mockHttpClient = client.getRestApiClient().getHttpClient();
        jsonConverter = client.getRestApiClient().getJsonConverter();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        client.getRestApiClient().setHttpClient(mockHttpClient);
    }

    protected String getJsonPayload(String path) {
        try {
            StringBuilder result = new StringBuilder();
            Path resource = Paths.get(this.getClass().getResource(BASE_PATH + path).toURI());
            List<String> lines = Files.readAllLines(resource, Charsets.UTF_8);
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

    protected String extractHttpEntity(HttpUriRequest request) throws IOException {
        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntityEnclosingRequest entityRequest = (HttpEntityEnclosingRequest) request;
            return EntityUtils.toString(entityRequest.getEntity());
        }
        return null;
    }

    protected ArgumentCaptor<HttpUriRequest> mockHttpResponse() throws Exception {
        return mockHttpResponse(null);
    }

    protected ArgumentCaptor<HttpUriRequest> mockHttpResponse(String responseJson) throws Exception {
        return mockHttpResponse(responseJson, 200);
    }

    protected ArgumentCaptor<HttpUriRequest> mockHttpResponse(String responseJson, Integer responseCode)
        throws Exception {
        when(mockHttpResponse.getStatusLine()).thenReturn(getStatusForCode(responseCode));
        if (responseJson != null) {
            when(mockHttpResponse.getEntity()).thenReturn(EntityBuilder.create().setText(responseJson).build());
        }

        ArgumentCaptor<HttpUriRequest> captor = ArgumentCaptor.forClass(HttpUriRequest.class);
        doReturn(mockHttpResponse).when(mockHttpClient).execute(captor.capture());

        return captor;
    }

    protected void assertUriContainsQueryParams(URI uri, String... params) {
        for (String param : params) {
            assertThat(uri.toString(), containsString(param));
        }
    }

    protected StatusLine getStatusForCode(int code) {
        return new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), code, "OK");
    }

    protected String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
