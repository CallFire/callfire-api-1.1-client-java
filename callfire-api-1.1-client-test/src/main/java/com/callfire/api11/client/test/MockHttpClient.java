package com.callfire.api11.client.test;

import com.callfire.api11.client.api.calls.model.Call;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import com.callfire.api11.client.api.texts.model.Text;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.callfire.api11.client.ModelType.listOf;
import static com.callfire.api11.client.ModelType.of;
import static com.callfire.api11.client.ModelType.resourceOf;
import static com.callfire.api11.client.test.CallfireTestUtils.extractHttpEntity;
import static com.callfire.api11.client.test.CallfireTestUtils.getJsonPayload;
import static com.callfire.api11.client.test.MockHttpClient.CaseKey.QUERY_CALL_INVALID_TO_NUMBER;
import static com.callfire.api11.client.test.MockHttpClient.CaseKey.QUERY_TEXT_INVALID_TO_NUMBER;
import static com.callfire.api11.client.test.MockHttpClient.CaseKey.SEND_CALL_INVALID_FROM_NUMBER;
import static com.callfire.api11.client.test.MockHttpClient.CaseKey.SEND_CALL_INVALID_TO_NUMBER;
import static com.callfire.api11.client.test.MockHttpClient.CaseKey.SEND_TEXT_INVALID_FROM_NUMBER;
import static com.callfire.api11.client.test.MockHttpClient.CaseKey.SEND_TEXT_INVALID_TO_NUMBER;

/**
 * Mock http client which return json stubs
 */
public class MockHttpClient implements HttpClient {
    public static final String HEADER_RETURN_TYPE = "ReturnType";
    public static final String JSON_BASE_PATH = "/com/callfire/api11/client/api";

    private static final ProtocolVersion PV = new ProtocolVersion("HTTP", 1, 1);

    public static final BasicStatusLine OK_200 = new BasicStatusLine(PV, 200, "OK");
    public static final BasicStatusLine BAD_REQUEST_400 = new BasicStatusLine(PV, 400, "Bad Request");
    public static final BasicStatusLine NOT_FOUND_404 = new BasicStatusLine(PV, 404, "Not Found");
    private Map<String, Pair<Integer, String>> jsonResponses = new HashMap<>();

    public MockHttpClient() {
        initJsonResponses();
    }

    public Map<String, Pair<Integer, String>> getJsonResponses() {
        return jsonResponses;
    }

    public void setJsonResponses(Map<String, Pair<Integer, String>> jsonResponses) {
        this.jsonResponses = jsonResponses;
    }

    @Override
    public HttpResponse execute(HttpUriRequest request) throws IOException {
        Header typeHeader = request.getFirstHeader(HEADER_RETURN_TYPE);
        Validate.notNull(typeHeader, "mockClient variable isn't set in Cf11ApiRestClient class");

        String key = getJsonResponseKey(request);
        Pair<Integer, String> pair = jsonResponses.get(key);
        String jsonPayload = "";
        if (pair != null) {
            jsonPayload = getJsonPayload(JSON_BASE_PATH + pair.getValue());
        } else {
            // return empty response if there is no mapping for key
            pair = new MutablePair<>(200, "");
        }

        BasicHttpResponse response = new BasicHttpResponse(asStatusLine(pair.getKey()));
        response.setEntity(EntityBuilder.create().setText(jsonPayload).build());
        return response;
    }

    public String getJsonResponseKey(HttpUriRequest request) {
        // calls
        if (matchRequest(request, HttpPost.METHOD_NAME, "/call.json", "From=1234")) {
            return SEND_CALL_INVALID_FROM_NUMBER.name();
        } else if (matchRequest(request, HttpPost.METHOD_NAME, "/call.json", "To=1234")) {
            return SEND_CALL_INVALID_TO_NUMBER.name();
        } else if (matchRequest(request, HttpGet.METHOD_NAME, "/call.json", "BroadcastId=1000")) {
            return QUERY_CALL_INVALID_TO_NUMBER.name();
        } else if (matchRequest(request, HttpPost.METHOD_NAME, "/text.json", "From=1234")) {
            return SEND_TEXT_INVALID_FROM_NUMBER.name();
        } else if (matchRequest(request, HttpPost.METHOD_NAME, "/text.json", "To=1234")) {
            return SEND_TEXT_INVALID_TO_NUMBER.name();
        } else if (matchRequest(request, HttpGet.METHOD_NAME, "/text.json", "BroadcastId=2000")) {
            return QUERY_TEXT_INVALID_TO_NUMBER.name();
        }
        // texts

        return request.getFirstHeader(HEADER_RETURN_TYPE).getValue();
    }

    public boolean matchRequest(HttpUriRequest request, String expectedMethod, String expectedUrlPart,
        String... expectedParameters) {
        URI uri = request.getURI();
        String method = request.getMethod();
        String requestBody = extractHttpEntity(request);
        boolean match = expectedMethod.equals(method) && uri.getPath().contains(expectedUrlPart);
        for (String parameter : expectedParameters) {
            if (HttpPost.METHOD_NAME.equals(method)) {
                match = match && StringUtils.contains(requestBody, parameter);
            } else {
                match = match && StringUtils.contains(uri.getQuery(), parameter);
            }
        }

        return match;
    }

    private StatusLine asStatusLine(Integer key) {
        switch (key) {
            case 200:
                return OK_200;
            case 400:
                return BAD_REQUEST_400;
            case 404:
                return NOT_FOUND_404;
            default:
                return OK_200;
        }
    }

    private void initJsonResponses() {
        // @formatter:off
        jsonResponses.put(of(ResourceReference.class).getType().toString(), new MutablePair<>(204, "/calls/send.json"));
        jsonResponses.put(SEND_CALL_INVALID_FROM_NUMBER.name(), new MutablePair<>(400, "/calls/sendInvalidFromNumber.json"));
        jsonResponses.put(SEND_CALL_INVALID_TO_NUMBER.name(), new MutablePair<>(204, "/calls/sendInvalidToNumber.json"));
        jsonResponses.put(QUERY_CALL_INVALID_TO_NUMBER.name(), new MutablePair<>(200, "/calls/queryInvalidToNumber.json"));
        jsonResponses.put(listOf(Call.class).getType().toString(), new MutablePair<>(200, "/calls/query.json"));
        jsonResponses.put(resourceOf(Call.class).getType().toString(), new MutablePair<>(200, "/calls/get.json"));

        jsonResponses.put(of(ResourceReference.class).getType().toString(), new MutablePair<>(204, "/texts/send.json"));
        jsonResponses.put(SEND_TEXT_INVALID_FROM_NUMBER.name(), new MutablePair<>(400, "/texts/sendInvalidFromNumber.json"));
        jsonResponses.put(SEND_TEXT_INVALID_TO_NUMBER.name(), new MutablePair<>(204, "/texts/sendInvalidToNumber.json"));
        jsonResponses.put(QUERY_TEXT_INVALID_TO_NUMBER.name(), new MutablePair<>(200, "/texts/queryInvalidToNumber.json"));
        jsonResponses.put(listOf(Text.class).getType().toString(), new MutablePair<>(200, "/texts/query.json"));
        jsonResponses.put(resourceOf(Text.class).getType().toString(), new MutablePair<>(200, "/texts/get.json"));

        jsonResponses.put(resourceOf(Subscription.class).getType().toString(), new MutablePair<>(200, "/subscriptions/get.json"));
        jsonResponses.put(listOf(Subscription.class).getType().toString(), new MutablePair<>(200, "/subscriptions/query.json"));
        jsonResponses.put(of(ResourceReference.class).getType().toString(), new MutablePair<>(204, "/subscriptions/create.json"));
        // @formatter:on
    }

    @Override
    public HttpParams getParams() {
        throw new NotImplementedException("mock client. not implemented");
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        throw new NotImplementedException("mock client. not implemented");
    }

    @Override
    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
        throw new NotImplementedException("mock client. not implemented");
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException {
        throw new NotImplementedException("mock client. not implemented");
    }

    @Override
    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
        throw new NotImplementedException("mock client. not implemented");
    }

    @Override
    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
        throw new NotImplementedException("mock client. not implemented");
    }

    @Override
    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context)
        throws IOException {
        throw new NotImplementedException("mock client. not implemented");
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler)
        throws IOException {
        throw new NotImplementedException("mock client. not implemented");
    }

    @Override
    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler,
        HttpContext context) throws IOException {
        throw new NotImplementedException("mock client. not implemented");
    }

    public enum CaseKey {
        SEND_CALL_INVALID_FROM_NUMBER,
        SEND_CALL_INVALID_TO_NUMBER,
        QUERY_CALL_INVALID_TO_NUMBER,
        SEND_TEXT_INVALID_FROM_NUMBER,
        SEND_TEXT_INVALID_TO_NUMBER,
        QUERY_TEXT_INVALID_TO_NUMBER
    }
}
