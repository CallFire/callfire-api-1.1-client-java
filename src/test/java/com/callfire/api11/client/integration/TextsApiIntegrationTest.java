package com.callfire.api11.client.integration;

import com.callfire.api11.client.api.common.model.ActionState;
import com.callfire.api11.client.api.common.model.LocalTimeZoneRestriction;
import com.callfire.api11.client.api.common.model.ToNumber;
import com.callfire.api11.client.api.texts.model.BigMessageStrategy;
import com.callfire.api11.client.api.texts.model.Text;
import com.callfire.api11.client.api.texts.model.TextBroadcastConfig;
import com.callfire.api11.client.api.texts.model.TextResult;
import com.callfire.api11.client.api.texts.model.request.QueryTextsRequest;
import com.callfire.api11.client.api.texts.model.request.SendTextRequest;
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
public class TextsApiIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void send() throws Exception {
        Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 10), 10), 10);
        Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 11), 20), 20);

        SendTextRequest request = SendTextRequest.create()
            .name("Send Text Java API Client")
            .defaultBroadcast(true)
            .scrubDuplicates(true)
            .labels(asList("label1", "label2"))
            .recipients(Arrays.asList(
                new ToNumber("12132212384", ToNumber.attributes("attr1", "val1", "attr2", "val2")),
                new ToNumber("16505339974", ToNumber.attributes("attr11", "val11", "attr22", "val22")))
            )
            .config(TextBroadcastConfig.create()
                .fromNumber("67076")
                .message("Api test message")
                .strategy(BigMessageStrategy.SEND_MULTIPLE)
                .timeZoneRestriction(new LocalTimeZoneRestriction(localBeginTime, localEndTime))
            )
            .build();

        long id = client.textsApi().send(request);

        System.out.println("send text id: " + id);
    }

    @Test
    public void query() throws Exception {
        Date intervalBegin = setYears(setMonths(setDays(
            setSeconds(setMinutes(setHours(new Date(), 0), 0), 0), 27), 0), 2016);
        Date intervalEnd = setYears(setMonths(setDays(
            setSeconds(setMinutes(setHours(new Date(), 20), 20), 20), 20), 11), 2016);

        QueryTextsRequest request = QueryTextsRequest.create()
//            .broadcastId(1L)
//            .batchId(2L)
//            .inbound(true)
            .intervalBegin(intervalBegin)
//            .intervalEnd(intervalEnd)
            .fromNumber("67076")
            .toNumber("14243876936")
//            .labelName("labelName")
            .states(asList(ActionState.FINISHED))
            .result(asList(TextResult.RECEIVED, TextResult.SENT))
            .maxResults(10)
            .firstResult(1)
            .build();

        List<Text> texts = client.textsApi().query(request);
        System.out.println(texts);
    }

    @Test
    public void get() throws Exception {
        Text text = client.textsApi().get(731402020003L);
        System.out.println(text);
    }
}
