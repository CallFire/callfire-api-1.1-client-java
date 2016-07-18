package com.callfire.api11.client.api;

import com.callfire.api11.client.api.common.model.Resource;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.subscriptions.model.NotificationFormat;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import com.callfire.api11.client.api.subscriptions.model.SubscriptionFilter;
import com.callfire.api11.client.api.subscriptions.model.TriggerEvent;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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

public class SubscriptionsApiTest extends AbstractApiTest {

    @Test
    public void create() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/subscription/596584003";
        String expectedJson = getJsonPayload("/subscriptions/create.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Subscription subscription = new Subscription();
        subscription.setEndpoint("http://endpoint");
        subscription.setEnabled(true);
        subscription.setNonStrictSsl(true);
        subscription.setTriggerEvent(TriggerEvent.CAMPAIGN_STARTED);
        subscription.setNotificationFormat(NotificationFormat.JSON);
        subscription.setFilter(new SubscriptionFilter(1L, 2L, "123", "321"));

        Long id = client.subscriptionsApi().create(subscription);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Enabled=true"));
        assertThat(requestBody, containsString("NonStrictSsl=true"));
        assertThat(requestBody, containsString("Endpoint=" + encode("http://endpoint")));
        assertThat(requestBody, containsString("NotificationFormat=JSON"));
        assertThat(requestBody, containsString("TriggerEvent=CAMPAIGN_STARTED"));
        assertThat(requestBody, containsString("BroadcastId=1"));
        assertThat(requestBody, containsString("BatchId=2"));
        assertThat(requestBody, containsString("FromNumber=123"));
        assertThat(requestBody, containsString("ToNumber=321"));
    }

    @Test
    public void update() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        Subscription subscription = new Subscription();
        subscription.setId(123L);
        subscription.setEndpoint("http://endpoint");
        subscription.setEnabled(true);
        subscription.setNonStrictSsl(true);
        subscription.setTriggerEvent(TriggerEvent.CAMPAIGN_STARTED);
        subscription.setNotificationFormat(NotificationFormat.JSON);
        subscription.setFilter(new SubscriptionFilter(1L, 2L, "123", "321"));

        client.subscriptionsApi().update(subscription);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/subscription/123.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString("Enabled=true"));
        assertThat(requestBody, containsString("NonStrictSsl=true"));
        assertThat(requestBody, containsString("Endpoint=" + encode("http://endpoint")));
        assertThat(requestBody, containsString("NotificationFormat=JSON"));
        assertThat(requestBody, containsString("TriggerEvent=CAMPAIGN_STARTED"));
        assertThat(requestBody, containsString("BroadcastId=1"));
        assertThat(requestBody, containsString("BatchId=2"));
        assertThat(requestBody, containsString("FromNumber=123"));
        assertThat(requestBody, containsString("ToNumber=321"));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/subscriptions/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Subscription subscription = client.subscriptionsApi().get(1234567L);
        Resource<Subscription> response = new Resource<Subscription>(subscription, Subscription.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/subscription/1234567.json"));
    }

    @Test
    public void query() throws Exception {
        String expectedJson = getJsonPayload("/subscriptions/query.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryRequest request = QueryRequest.createSimpleQuery()
            .firstResult(2)
            .maxResults(100)
            .build();
        List<Subscription> subscriptions = client.subscriptionsApi().query(request);
        ResourceList<Subscription> response = new ResourceList<Subscription>(subscriptions, Subscription.class);
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
        client.subscriptionsApi().delete(1234567L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/subscription/1234567.json"));
    }
}
