package com.callfire.api11.client.integration;

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
import com.callfire.api11.client.api.common.model.Result;
import com.callfire.api11.client.api.common.model.RetryConfig;
import com.callfire.api11.client.api.common.model.RetryPhoneType;
import com.callfire.api11.client.api.common.model.ToNumber;
import org.junit.Ignore;
import org.junit.Test;

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

@Ignore
public class CallsApiIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void sendIvrCall() throws Exception {
        Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 4), 10), 10);
        Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 22), 20), 20);

        SendCallRequest request = SendCallRequest.create()
            .name("Send Call Java API Client")
            .maxActive(2)
            .scrubDuplicates(false)
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
                .dialplanXml(
                    "<dialplan name=\"Root\"><play type=\"tts\" voice=\"female1\">Hello Callfire!</play></dialplan>")
            )
            .build();

        long id = client.callsApi().send(request);
        System.out.println("send call id: " + id);
    }

    @Test
    public void sendVoiceCall() throws Exception {
        Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 4), 10), 10);
        Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 22), 20), 20);

        SendCallRequest request = SendCallRequest.create()
            .name("Send Call Java API Client")
            .maxActive(2)
            .scrubDuplicates(false)
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
//                .liveSound(1)
                .liveSound("this is LA TTS", Voice.MALE1)
                // answering machine
                .amConfig(AmConfig.LIVE_WITH_AMD)
//                .amSound(2)
                .amSound("this is AM TTS", Voice.FEMALE1)
                // transfer
//                .transferSound(3)
                .transferSound("this is transfer TTS")
                .transferDigit("7")
                .transferNumber("12132212384")
//                .maxActiveTransfers(2)
                // dnc
//                .dncSound(4)
                .dncSound("this is DNC TTS")
                .dncDigit("9")
            )
            .build();

        long id = client.callsApi().send(request);
        System.out.println("send call id: " + id);
    }

    @Test
    public void query() throws Exception {
        Date intervalBegin = setYears(setMonths(setDays(
            setSeconds(setMinutes(setHours(new Date(), 10), 10), 10), 20), 4), 2016);
        Date intervalEnd = setYears(setMonths(setDays(
            setSeconds(setMinutes(setHours(new Date(), 20), 20), 20), 20), 11), 2016);

        QueryCallsRequest request = QueryCallsRequest.create()
            //            .broadcastId(1L)
            //            .batchId(2L)
            //            .inbound(true)
            .intervalBegin(intervalBegin)
            //            .intervalEnd(intervalEnd)
            .fromNumber("12132212384")
            .toNumber("12132212384")
            //            .labelName("labelName")
            .states(asList(ActionState.FINISHED))
            .result(asList(CallResult.LA, CallResult.NO_ANS))
            .maxResults(10)
            .firstResult(1)
            .build();
        List<Call> calls = client.callsApi().query(request);
        System.out.println(calls);
    }

    @Test
    public void get() throws Exception {
        Call call = client.callsApi().get(882959551003L);
        System.out.println(call);
    }
}
