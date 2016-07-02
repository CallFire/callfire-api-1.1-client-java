package com.callfire.api11.client.api;

import com.callfire.api11.client.api.common.model.Label;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

import static com.callfire.api11.client.test.CallfireTestUtils.extractHttpEntity;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class LabelsApiTest extends AbstractApiTest {

    @Test
    public void query() throws Exception {
        String expectedJson = getJsonPayload("/labels/query.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryRequest request = QueryRequest.createNew()
            .firstResult(2)
            .maxResults(100)
            .build();
        List<Label> labels = client.labelsApi().query(request);
        ResourceList<Label> response = new ResourceList<>(labels, Label.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=2"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
    }

    @Test
    public void delete() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.labelsApi().delete("label");

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("LabelName=label"));
    }

    @Test
    public void labelBroadcast() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.labelsApi().labelBroadcast(1L, "label");

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/label/broadcast/1"));
        assertThat(requestBody, containsString("LabelName=label"));
    }

    @Test
    public void unlabelBroadcast() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.labelsApi().unlabelBroadcast(1L, "label");

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/label/broadcast/1"));
        assertThat(arg.getURI().toString(), containsString("LabelName=label"));
    }

    @Test
    public void labelNumber() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.labelsApi().labelNumber("1234567890", "label");

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/label/number/1234567890"));
        assertThat(requestBody, containsString("LabelName=label"));
    }

    @Test
    public void unlabelNumber() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.labelsApi().unlabelNumber("1234567890", "label");

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/label/number/1234567890"));
        assertThat(arg.getURI().toString(), containsString("LabelName=label"));
    }
}
