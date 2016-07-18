package com.callfire.api11.client.api;

import com.callfire.api11.client.api.broadcasts.model.Broadcast;
import com.callfire.api11.client.api.broadcasts.model.BroadcastCommand;
import com.callfire.api11.client.api.broadcasts.model.BroadcastSchedule;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStats;
import com.callfire.api11.client.api.broadcasts.model.BroadcastType;
import com.callfire.api11.client.api.broadcasts.model.ContactBatch;
import com.callfire.api11.client.api.broadcasts.model.DayOfWeek;
import com.callfire.api11.client.api.broadcasts.model.request.CreateBatchRequest;
import com.callfire.api11.client.api.broadcasts.model.request.QueryBroadcastsRequest;
import com.callfire.api11.client.api.calls.model.IvrBroadcastConfig;
import com.callfire.api11.client.api.common.model.LocalTimeZoneRestriction;
import com.callfire.api11.client.api.common.model.Resource;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.Result;
import com.callfire.api11.client.api.common.model.RetryConfig;
import com.callfire.api11.client.api.common.model.RetryPhoneType;
import com.callfire.api11.client.api.common.model.ToNumber;
import com.callfire.api11.client.api.common.model.request.QueryByIdRequest;
import com.callfire.api11.client.api.texts.model.BigMessageStrategy;
import com.callfire.api11.client.api.texts.model.TextBroadcastConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.test.CallfireTestUtils.extractHttpEntity;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.time.DateUtils.setHours;
import static org.apache.commons.lang3.time.DateUtils.setMinutes;
import static org.apache.commons.lang3.time.DateUtils.setSeconds;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class BroadcastsApiTest extends AbstractApiTest {
    private static Calendar startDate = Calendar.getInstance();
    private static Calendar endDate = Calendar.getInstance();
    private static Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 10), 10), 10);
    private static Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 11), 20), 20);
    private static String dialplanXml = "<dialplan name=\"Root\"><play type=\"tts\" voice=\"female1\">Hello Callfire!</play></dialplan>";

    static {
        startDate.set(2000, Calendar.JANUARY, 10, 10, 10, 10);
        endDate.set(2015, Calendar.DECEMBER, 15, 15, 15, 15);
    }

    @Test
    public void createIvrBroadcast() throws Exception {

        String location = "https://www.callfire.com/api/1.1/rest/broadcast/1528630003";
        String expectedJson = getJsonPayload("/broadcasts/create.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Broadcast broadcast = new Broadcast();
        broadcast.setName("Broadcast 1");
        broadcast.setType(BroadcastType.IVR);
        broadcast.setLabels(asList("label1", "label2"));
        broadcast.setIvrBroadcastConfig(IvrBroadcastConfig.create()
            .fromNumber("12132212384")
            .retryConfig(new RetryConfig(2, 1, asList(Result.BUSY, Result.NO_ANS),
                asList(RetryPhoneType.FIRST_NUMBER, RetryPhoneType.HOME_PHONE)))
            .timeZoneRestriction(new LocalTimeZoneRestriction(localBeginTime, localEndTime))
            .dialplanXml(dialplanXml)
            .build());

        Long id = client.broadcastsApi().create(broadcast);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Name=" + encode("Broadcast 1")));
        assertThat(requestBody, containsString("Type=IVR"));
        assertThat(requestBody, containsString("Label=label1"));
        assertThat(requestBody, containsString("Label=label2"));
        assertThat(requestBody, containsString("From=12132212384"));
        assertThat(requestBody, containsString("MaxAttempts=2"));
        assertThat(requestBody, containsString("MinutesBetweenAttempts=1"));
        assertThat(requestBody, containsString("RetryResults=BUSY"));
        assertThat(requestBody, containsString("RetryResults=NO_ANS"));
        assertThat(requestBody, containsString("RetryPhoneTypes=FIRST_NUMBER"));
        assertThat(requestBody, containsString("RetryPhoneTypes=HOME_PHONE"));
        assertThat(requestBody, containsString("LocalRestrictBegin=" + encode("10:10:10")));
        assertThat(requestBody, containsString("LocalRestrictEnd=" + encode("11:20:20")));
        assertThat(requestBody, containsString("DialplanXml=" + encode(dialplanXml)));
    }

    @Test
    public void createTextBroadcast() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/broadcast/1528630003";
        String expectedJson = getJsonPayload("/broadcasts/create.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Broadcast broadcast = new Broadcast();
        broadcast.setName("Broadcast 1");
        broadcast.setType(BroadcastType.TEXT);
        broadcast.setLabels(asList("label1", "label2"));
        broadcast.setTextBroadcastConfig(TextBroadcastConfig.create()
            .fromNumber("12132212384")
            .message("Api test message")
            .strategy(BigMessageStrategy.SEND_MULTIPLE)
            .timeZoneRestriction(new LocalTimeZoneRestriction(localBeginTime, localEndTime))
            .build());

        Long id = client.broadcastsApi().create(broadcast);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Name=" + encode("Broadcast 1")));
        assertThat(requestBody, containsString("Type=TEXT"));
        assertThat(requestBody, containsString("Label=label1"));
        assertThat(requestBody, containsString("Label=label2"));
        assertThat(requestBody, containsString("From=12132212384"));
        assertThat(requestBody, containsString("Message=" + encode("Api test message")));
        assertThat(requestBody, containsString("BigMessageStrategy=SEND_MULTIPLE"));
        assertThat(requestBody, containsString("LocalRestrictBegin=" + encode("10:10:10")));
        assertThat(requestBody, containsString("LocalRestrictEnd=" + encode("11:20:20")));
    }

    @Test
    public void query() throws Exception {
        String expectedJson = getJsonPayload("/broadcasts/query.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryBroadcastsRequest request = QueryBroadcastsRequest.create()
            .firstResult(2)
            .maxResults(100)
            .type(BroadcastType.TEXT)
            .running(true)
            .labelName("label")
            .build();
        List<Broadcast> broadcasts = client.broadcastsApi().query(request);
        ResourceList<Broadcast> response = new ResourceList<Broadcast>(broadcasts, Broadcast.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=2"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
        assertThat(arg.getURI().toString(), containsString("Type=TEXT"));
        assertThat(arg.getURI().toString(), containsString("Running=true"));
        assertThat(arg.getURI().toString(), containsString("LabelName=label"));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/broadcasts/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Broadcast broadcast = client.broadcastsApi().get(1234567L);
        Resource<Broadcast> response = new Resource<Broadcast>(broadcast, Broadcast.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/broadcast/1234567.json"));
    }

    @Test
    public void update() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        Broadcast broadcast = new Broadcast();
        broadcast.setId(123L);
        broadcast.setName("Broadcast 1 updated");
        broadcast.setType(BroadcastType.IVR);
        broadcast.setLabels(asList("label1", "label2"));
        broadcast.setIvrBroadcastConfig(IvrBroadcastConfig.create()
            .fromNumber("12132212384")
            .retryConfig(new RetryConfig(2, 1, asList(Result.LA, Result.NO_ANS),
                asList(RetryPhoneType.FIRST_NUMBER, RetryPhoneType.HOME_PHONE)))
            .timeZoneRestriction(new LocalTimeZoneRestriction(localBeginTime, localEndTime))
            .dialplanXml(dialplanXml)
            .build());

        client.broadcastsApi().update(broadcast);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/broadcast/123.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString("Name=" + encode("Broadcast 1 updated")));
        assertThat(requestBody, containsString("Type=IVR"));
        assertThat(requestBody, containsString("Label=label1"));
        assertThat(requestBody, containsString("Label=label2"));
        assertThat(requestBody, containsString("From=12132212384"));
        assertThat(requestBody, containsString("MaxAttempts=2"));
        assertThat(requestBody, containsString("MinutesBetweenAttempts=1"));
        assertThat(requestBody, containsString("RetryResults=LA"));
        assertThat(requestBody, containsString("RetryResults=NO_ANS"));
        assertThat(requestBody, containsString("RetryPhoneTypes=FIRST_NUMBER"));
        assertThat(requestBody, containsString("RetryPhoneTypes=HOME_PHONE"));
        assertThat(requestBody, containsString("LocalRestrictBegin=" + encode("10:10:10")));
        assertThat(requestBody, containsString("LocalRestrictEnd=" + encode("11:20:20")));
        assertThat(requestBody, containsString("DialplanXml=" + encode(dialplanXml)));
    }

    @Test
    public void getStats() throws Exception {
        String expectedJson = getJsonPayload("/broadcasts/getStats.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        BroadcastStats stats = client.broadcastsApi().getStats(1234567L, startDate.getTime(), endDate.getTime());
        Resource<BroadcastStats> response = new Resource<BroadcastStats>(stats, BroadcastStats.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/broadcast/1234567/stats.json"));
        assertThat(arg.getURI().toString(), containsString("IntervalBegin=" + encode("2000-01-10T10:10:10")));
        assertThat(arg.getURI().toString(), containsString("IntervalEnd=" + encode("2015-12-15T15:15:15")));
    }

    @Test
    public void control() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.broadcastsApi().control(1000L, BroadcastCommand.START, 200);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/broadcast/1000/control.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString("Command=START"));
        assertThat(requestBody, containsString("MaxActive=200"));
    }

    @Test
    public void createBatch() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/broadcast/batch/10771759003";
        String expectedJson = getJsonPayload("/broadcasts/createBatch.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        CreateBatchRequest request = CreateBatchRequest.create()
            .name("Contact batch")
            .contactListId(100L)
            .broadcastId(1000L)
            .scrubDuplicates(true)
            .start(true)
            .numbers(Arrays.asList(
                new ToNumber("12132212384", ToNumber.attributes("attr1", "val1", "attr2", "val2")),
                new ToNumber("12132212385", ToNumber.attributes("attr5", "val5", "attr6", "val6")),
                new ToNumber("12132212386", ToNumber.attributes("attr8", "val8", "attr9", "val9"))))
            .build();

        Long id = client.broadcastsApi().createBatch(request);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Name=" + encode("Contact batch")));
        assertThat(requestBody, containsString("ContactListId=100"));
        assertThat(requestBody, containsString("BroadcastId=1000"));
        assertThat(requestBody, containsString("ScrubBroadcastDuplicates=true"));
        assertThat(requestBody, containsString("Start=true"));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr2=val2&attr1=val1")));
        assertThat(requestBody, containsString("To=" + encode("12132212385?attr6=val6&attr5=val5")));
        assertThat(requestBody, containsString("To=" + encode("12132212386?attr9=val9&attr8=val8")));
    }

    @Test
    public void queryBatches() throws Exception {
        String expectedJson = getJsonPayload("/broadcasts/queryBatches.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryByIdRequest request = QueryByIdRequest.create()
            .firstResult(2)
            .maxResults(100)
            .id(1000L)
            .build();
        List<ContactBatch> batches = client.broadcastsApi().queryBatches(request);
        ResourceList<ContactBatch> response = new ResourceList<ContactBatch>(batches, ContactBatch.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        assertThat(arg.getURI().toString(), containsString("/broadcast/1000/batch.json"));

        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=2"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
    }

    @Test
    public void getBatch() throws Exception {
        String expectedJson = getJsonPayload("/broadcasts/getBatch.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        ContactBatch batch = client.broadcastsApi().getBatch(1234567L);
        Resource<ContactBatch> response = new Resource<ContactBatch>(batch, ContactBatch.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/broadcast/batch/1234567.json"));
    }

    @Test
    public void controlBatch() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.broadcastsApi().controlBatch(1000L, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/broadcast/batch/1000/control.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString("Enabled=true"));
    }

    @Test
    public void createSchedule() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/broadcast/schedule/1528630003";
        String expectedJson = getJsonPayload("/broadcasts/createSchedule.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        BroadcastSchedule schedule = new BroadcastSchedule();
        schedule.setTimeZone("America/New_York");
        schedule.setDaysOfWeek(asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        schedule.setBeginDate(startDate.getTime());
        schedule.setEndDate(endDate.getTime());
        schedule.setStartTimeOfDay(localBeginTime);
        schedule.setStopTimeOfDay(localEndTime);

        Long id = client.broadcastsApi().createSchedule(1000L, schedule);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("TimeZone=" + encode("America/New_York")));
        assertThat(requestBody, containsString("DaysOfWeek=MONDAY"));
        assertThat(requestBody, containsString("DaysOfWeek=TUESDAY"));
        assertThat(requestBody, containsString("DaysOfWeek=WEDNESDAY"));
        assertThat(requestBody, containsString("BeginDate=2000-01-10"));
        assertThat(requestBody, containsString("EndDate=2015-12-15"));
        assertThat(requestBody, containsString("StopTimeOfDay=" + encode("11:20:20")));
        assertThat(requestBody, containsString("StopTimeOfDay=" + encode("11:20:20")));
    }

    @Test
    public void querySchedules() throws Exception {
        String expectedJson = getJsonPayload("/broadcasts/querySchedules.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryByIdRequest request = QueryByIdRequest.create()
            .firstResult(2)
            .maxResults(100)
            .id(1000L)
            .build();
        List<BroadcastSchedule> schedules = client.broadcastsApi().querySchedules(request);
        ResourceList<BroadcastSchedule> response = new ResourceList<BroadcastSchedule>(schedules, BroadcastSchedule.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        assertThat(arg.getURI().toString(), containsString("/broadcast/1000/schedule.json"));

        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=2"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
    }

    @Test
    public void getSchedule() throws Exception {
        String expectedJson = getJsonPayload("/broadcasts/getSchedule.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        BroadcastSchedule schedule = client.broadcastsApi().getSchedule(1234567L);
        Resource<BroadcastSchedule> response = new Resource<BroadcastSchedule>(schedule, BroadcastSchedule.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/broadcast/schedule/1234567.json"));
    }

    @Test
    public void deleteSchedule() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.broadcastsApi().deleteSchedule(1234567L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/broadcast/schedule/1234567.json"));
    }
}
