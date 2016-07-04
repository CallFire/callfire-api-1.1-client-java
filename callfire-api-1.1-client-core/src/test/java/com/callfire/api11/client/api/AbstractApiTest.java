package com.callfire.api11.client.api;

import com.callfire.api11.client.CfApi11Client;
import com.callfire.api11.client.JsonConverter;
import com.callfire.api11.client.test.CallfireTestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.callfire.api11.client.test.MockHttpClient.JSON_BASE_PATH;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * Base api test class
 */
public class AbstractApiTest {

    @Rule
    public ExpectedException ex = ExpectedException.none();

    protected CfApi11Client client;
    protected JsonConverter jsonConverter;

    protected URLCodec codec = new URLCodec();

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
        return CallfireTestUtils.getJsonPayload(JSON_BASE_PATH + path);
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
