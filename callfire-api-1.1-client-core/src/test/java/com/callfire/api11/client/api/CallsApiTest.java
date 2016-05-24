package com.callfire.api11.client.api;

import com.callfire.api11.client.api.calls.model.AmConfig;
import com.callfire.api11.client.api.calls.model.Call;
import com.callfire.api11.client.api.calls.model.CallResult;
import com.callfire.api11.client.api.calls.model.IvrBroadcastConfig;
import com.callfire.api11.client.api.calls.model.Voice;
import com.callfire.api11.client.api.calls.model.VoiceBroadcastConfig;
import com.callfire.api11.client.api.calls.model.request.QueryCallsRequest;
import com.callfire.api11.client.api.calls.model.request.SendCallRequest;
import com.callfire.api11.client.api.common.model.ActionState;
import com.callfire.api11.client.api.common.model.LocalTimeZoneRestriction;
import com.callfire.api11.client.api.common.model.Resource;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.Result;
import com.callfire.api11.client.api.common.model.RetryConfig;
import com.callfire.api11.client.api.common.model.RetryPhoneType;
import com.callfire.api11.client.api.common.model.ToNumber;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

public class CallsApiTest extends AbstractApiTest {
    @Test
    public void sendIvrCall() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/call/596584003";
        String expectedJson = getJsonPayload("/calls/send.json");
        Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 10), 10), 10);
        Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 11), 20), 20);
        String dialplanXml = "<dialplan name=\"Root\"><play type=\"tts\" voice=\"female1\">Hello Callfire!</play></dialplan>";

        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        SendCallRequest request = SendCallRequest.create()
            .name("Send Ivr Call Java API Client")
            .maxActive(2)
            .scrubDuplicates(true)
            .labels(asList("label1", "label2"))
            .recipients(Arrays.asList(
                new ToNumber("12132212384", ToNumber.attributes("attr1", "val1", "attr2", "val2")),
                new ToNumber("12132212384", ToNumber.attributes("attr5", "val5", "attr6", "val6")),
                new ToNumber("12132212384", ToNumber.attributes("attr8", "val8", "attr9", "val9")))
            )
            .ivrConfig(IvrBroadcastConfig.create()
                .fromNumber("12132212384")
                .retryConfig(new RetryConfig(2, 1, asList(Result.BUSY, Result.NO_ANS),
                    asList(RetryPhoneType.FIRST_NUMBER, RetryPhoneType.HOME_PHONE)))
                .timeZoneRestriction(new LocalTimeZoneRestriction(localBeginTime, localEndTime))
                .dialplanXml(dialplanXml)
            )
            .build();

        Long id = client.callsApi().send(request);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("BroadcastName=" + encode("Send Ivr Call Java API Client")));
        assertThat(requestBody, containsString("Type=IVR"));
        assertThat(requestBody, containsString("MaxActive=2"));
        assertThat(requestBody, containsString("ScrubBroadcastDuplicates=true"));
        assertThat(requestBody, containsString("Label=label1"));
        assertThat(requestBody, containsString("Label=label2"));
        assertThat(requestBody, containsString("From=12132212384"));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr2=val2&attr1=val1")));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr6=val6&attr5=val5")));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr9=val9&attr8=val8")));
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
    public void sendVoiceCall() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/call/596584003";
        String expectedJson = getJsonPayload("/calls/send.json");
        Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 10), 10), 10);
        Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 11), 20), 20);

        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        SendCallRequest request = SendCallRequest.create()
            .name("Send Voice Call Java API Client")
            .maxActive(2)
            .scrubDuplicates(true)
            .labels(asList("label1", "label2"))
            .recipients(Arrays.asList(
                new ToNumber("12132212384", ToNumber.attributes("attr1", "val1", "attr2", "val2")),
                new ToNumber("12132212384", ToNumber.attributes("attr5", "val5", "attr6", "val6")),
                new ToNumber("12132212384", ToNumber.attributes("attr8", "val8", "attr9", "val9")))
            )
            .voiceConfig(VoiceBroadcastConfig.create()
                .fromNumber("12132212384")
                .retryConfig(new RetryConfig(2, 1, asList(Result.BUSY, Result.NO_ANS),
                    asList(RetryPhoneType.FIRST_NUMBER, RetryPhoneType.HOME_PHONE)))
                .timeZoneRestriction(new LocalTimeZoneRestriction(localBeginTime, localEndTime))
                // live answer
                .liveSound(1)
                .liveSound("this is LA TTS", Voice.MALE1)
                // answering machine
                .amConfig(AmConfig.AM_ONLY)
                .amSound(2)
                .amSound("this is AM TTS", Voice.FEMALE1)
                // transfer
                .transferSound(3)
                .transferSound("this is transfer TTS")
                .transferDigit("7")
                .transferNumber("1234567890")
                .maxActiveTransfers(2)
                // dnc
                .dncSound(4)
                .dncSound("this is DNC TTS")
                .dncDigit("9")
            )
            .build();

        Long id = client.callsApi().send(request);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("BroadcastName=" + encode("Send Voice Call Java API Client")));
        assertThat(requestBody, containsString("Type=VOICE"));
        assertThat(requestBody, containsString("MaxActive=2"));
        assertThat(requestBody, containsString("ScrubBroadcastDuplicates=true"));
        assertThat(requestBody, containsString("Label=label1"));
        assertThat(requestBody, containsString("Label=label2"));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr2=val2&attr1=val1")));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr6=val6&attr5=val5")));
        assertThat(requestBody, containsString("To=" + encode("12132212384?attr9=val9&attr8=val8")));
        assertThat(requestBody, containsString("From=12132212384"));
        assertThat(requestBody, containsString("MaxAttempts=2"));
        assertThat(requestBody, containsString("MinutesBetweenAttempts=1"));
        assertThat(requestBody, containsString("RetryResults=BUSY"));
        assertThat(requestBody, containsString("RetryResults=NO_ANS"));
        assertThat(requestBody, containsString("RetryPhoneTypes=FIRST_NUMBER"));
        assertThat(requestBody, containsString("RetryPhoneTypes=HOME_PHONE"));
        assertThat(requestBody, containsString("LocalRestrictBegin=" + encode("10:10:10")));
        assertThat(requestBody, containsString("LocalRestrictEnd=" + encode("11:20:20")));

        assertThat(requestBody, containsString("LiveSoundId=1"));
        assertThat(requestBody, containsString("LiveSoundText=" + encode("this is LA TTS")));
        assertThat(requestBody, containsString("LiveSoundTextVoice=MALE1"));

        assertThat(requestBody, containsString("AnsweringMachineConfig=AM_ONLY"));
        assertThat(requestBody, containsString("MachineSoundId=2"));
        assertThat(requestBody, containsString("MachineSoundText=" + encode("this is AM TTS")));
        assertThat(requestBody, containsString("MachineSoundTextVoice=FEMALE1"));

        assertThat(requestBody, containsString("TransferSoundId=3"));
        assertThat(requestBody, containsString("TransferSoundText=" + encode("this is transfer TTS")));
        assertThat(requestBody, containsString("TransferSoundTextVoice=FEMALE1"));
        assertThat(requestBody, containsString("TransferDigit=7"));
        assertThat(requestBody, containsString("TransferNumber=1234567890"));

        assertThat(requestBody, containsString("DncSoundId=4"));
        assertThat(requestBody, containsString("DncSoundText=" + encode("this is DNC TTS")));
        assertThat(requestBody, containsString("DncSoundTextVoice=FEMALE1"));
        assertThat(requestBody, containsString("DncDigit=9"));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/calls/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Call call = client.callsApi().get(1234567L);
        Resource<Call> response = new Resource<>(call, Call.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/call/1234567.json"));
    }

    @Test
    public void query() throws Exception {
        String expectedJson = getJsonPayload("/calls/query.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Date intervalBegin = setYears(setMonths(setDays(
            setSeconds(setMinutes(setHours(new Date(), 10), 10), 10), 10), 10), 2016);
        Date intervalEnd = setYears(setMonths(setDays(
            setSeconds(setMinutes(setHours(new Date(), 20), 20), 20), 20), 11), 2016);

        QueryCallsRequest request = QueryCallsRequest.create()
            .broadcastId(1L)
            .batchId(2L)
            .inbound(true)
            .intervalBegin(intervalBegin)
            .intervalEnd(intervalEnd)
            .fromNumber("1234567890")
            .toNumber("111222333")
            .labelName("labelName")
            .states(asList(ActionState.FINISHED))
            .result(asList(CallResult.LA, CallResult.NO_ANS))
            .maxResults(10)
            .firstResult(1)
            .build();

        List<Call> calls = client.callsApi().query(request);
        ResourceList<Call> response = new ResourceList<>(calls, Call.class);
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
        assertThat(arg.getURI().toString(), containsString("Result=LA"));
        assertThat(arg.getURI().toString(), containsString("Result=NO_ANS"));
        assertThat(arg.getURI().toString(), containsString("IntervalBegin=" + encode("2016-11-10T10:10:10+02:00")));
        assertThat(arg.getURI().toString(), containsString("IntervalEnd=" + encode("2016-12-20T20:20:20+02:00")));
        assertThat(arg.getURI().toString(), containsString("LabelName=labelName"));
    }
}
