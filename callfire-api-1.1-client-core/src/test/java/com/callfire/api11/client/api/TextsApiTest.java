package com.callfire.api11.client.api;

import com.callfire.api11.client.api.common.model.ActionState;
import com.callfire.api11.client.api.common.model.LocalTimeZoneRestriction;
import com.callfire.api11.client.api.common.model.Resource;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.ToNumber;
import com.callfire.api11.client.api.texts.model.AutoReply;
import com.callfire.api11.client.api.texts.model.BigMessageStrategy;
import com.callfire.api11.client.api.texts.model.Text;
import com.callfire.api11.client.api.texts.model.TextBroadcastConfig;
import com.callfire.api11.client.api.texts.model.TextResult;
import com.callfire.api11.client.api.texts.model.request.QueryAutoRepliesRequest;
import com.callfire.api11.client.api.texts.model.request.QueryTextsRequest;
import com.callfire.api11.client.api.texts.model.request.SendTextRequest;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.test.CallfireTestUtils.extractHttpEntity;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.time.DateUtils.setDays;
import static org.apache.commons.lang3.time.DateUtils.setHours;
import static org.apache.commons.lang3.time.DateUtils.setMinutes;
import static org.apache.commons.lang3.time.DateUtils.setMonths;
import static org.apache.commons.lang3.time.DateUtils.setSeconds;
import static org.apache.commons.lang3.time.DateUtils.setYears;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TextsApiTest extends AbstractApiTest {

    @Test
    public void send() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/text/596584003";
        String expectedJson = getJsonPayload("/texts/send.json");
        Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 10), 10), 10);
        Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 11), 20), 20);

        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        SendTextRequest request = SendTextRequest.create()
            .name("Send Text Java API Client")
            .defaultBroadcast(true)
            .scrubDuplicates(true)
            .labels(asList("label1", "label2"))
            .recipients(Arrays.asList(
                new ToNumber("12132212384", ToNumber.attributes("attr1", "val1", "attr2", "val2")),
                new ToNumber("12132212384", ToNumber.attributes("attr5", "val5", "attr6", "val6")),
                new ToNumber("12132212384", ToNumber.attributes("attr8", "val8", "attr9", "val9")))
            )
            .config(TextBroadcastConfig.create()
                .fromNumber("12132212384")
                .message("Api test message")
                .strategy(BigMessageStrategy.SEND_MULTIPLE)
                .timeZoneRestriction(new LocalTimeZoneRestriction(localBeginTime, localEndTime))
            )
            .build();

        Long id = client.textsApi().send(request);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("BroadcastName=" + encode("Send Text Java API Client")));
        assertThat(requestBody, containsString("Type=TEXT"));
        assertThat(requestBody, containsString("UseDefaultBroadcast=true"));
        assertThat(requestBody, containsString("ScrubBroadcastDuplicates=true"));
        assertThat(requestBody, containsString("Label=label1"));
        assertThat(requestBody, containsString("Label=label2"));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr2=val2&attr1=val1")));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr6=val6&attr5=val5")));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr9=val9&attr8=val8")));
        assertThat(requestBody, containsString("From=12132212384"));
        assertThat(requestBody, containsString("Message=" + encode("Api test message")));
        assertThat(requestBody, containsString("BigMessageStrategy=SEND_MULTIPLE"));
        assertThat(requestBody, containsString("LocalRestrictBegin=" + encode("10:10:10")));
        assertThat(requestBody, containsString("LocalRestrictEnd=" + encode("11:20:20")));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/texts/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Text text = client.textsApi().get(1234567L);
        Resource<Text> response = new Resource<>(text, Text.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/text/1234567.json"));
    }

    @Test
    public void query() throws Exception {
        String expectedJson = getJsonPayload("/texts/query.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Date intervalBegin = setYears(setMonths(setDays(
            setSeconds(setMinutes(setHours(new Date(), 10), 10), 10), 10), 10), 2016);
        Date intervalEnd = setYears(setMonths(setDays(
            setSeconds(setMinutes(setHours(new Date(), 20), 20), 20), 20), 11), 2016);

        QueryTextsRequest request = QueryTextsRequest.create()
            .broadcastId(1L)
            .batchId(2L)
            .inbound(true)
            .intervalBegin(intervalBegin)
            .intervalEnd(intervalEnd)
            .fromNumber("1234567890")
            .toNumber("111222333")
            .labelName("labelName")
            .states(asList(ActionState.FINISHED))
            .result(asList(TextResult.RECEIVED, TextResult.SENT))
            .maxResults(10)
            .firstResult(1)
            .build();

        List<Text> texts = client.textsApi().query(request);
        ResourceList<Text> response = new ResourceList<>(texts, Text.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=1"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=10"));
        assertThat(arg.getURI().toString(), containsString("BroadcastId=1"));
        assertThat(arg.getURI().toString(), containsString("FromNumber=1234567890"));
        assertThat(arg.getURI().toString(), containsString("ToNumber=111222333"));
        assertThat(arg.getURI().toString(), containsString("BatchId=2"));
        assertThat(arg.getURI().toString(), containsString("Inbound=true"));
        assertThat(arg.getURI().toString(), containsString("State=FINISHED"));
        assertThat(arg.getURI().toString(), containsString("Result=RECEIVED"));
        assertThat(arg.getURI().toString(), containsString("Result=SENT"));
        assertThat(arg.getURI().toString(), containsString("IntervalBegin=" + encode("2016-11-10T10:10:10")));
        assertThat(arg.getURI().toString(), containsString("IntervalEnd=" + encode("2016-12-20T20:20:20")));
        assertThat(arg.getURI().toString(), containsString("LabelName=labelName"));
    }

    @Test
    public void createAutoReply() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/text/auto-reply/596584003";
        String expectedJson = getJsonPayload("/texts/createAutoReply.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        AutoReply autoReply = new AutoReply();
        autoReply.setKeyword("KEYWORD");
        autoReply.setNumber("12345678901");
        autoReply.setMatch("test");
        autoReply.setMessage("Hello auto-reply");

        Long id = client.textsApi().createAutoReply(autoReply);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Keyword=KEYWORD"));
        assertThat(requestBody, containsString("Number=12345678901"));
        assertThat(requestBody, containsString("Message=" + encode("Hello auto-reply")));
        assertThat(requestBody, containsString("Match=test"));
    }

    @Test
    public void getAutoReply() throws Exception {
        String expectedJson = getJsonPayload("/texts/getAutoReply.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        AutoReply autoReply = client.textsApi().getAutoReply(1234567L);
        Resource<AutoReply> response = new Resource<>(autoReply, AutoReply.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/text/auto-reply/1234567.json"));
    }

    @Test
    public void queryAutoReply() throws Exception {
        String expectedJson = getJsonPayload("/texts/queryAutoReplies.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryAutoRepliesRequest request = QueryAutoRepliesRequest.create()
            .firstResult(2)
            .number("12345678901")
            .build();
        List<AutoReply> autoReplies = client.textsApi().queryAutoReplies(request);
        ResourceList<AutoReply> response = new ResourceList<>(autoReplies, AutoReply.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("Number=12345678901"));
        assertThat(arg.getURI().toString(), containsString("FirstResult=2"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=1000"));
    }

    @Test
    public void delete() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.textsApi().deleteAutoReply(1234567L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/text/auto-reply/1234567.json"));
    }
}
