package com.callfire.api11.client;

import com.callfire.api11.client.api.calls.model.CallResult;
import com.callfire.api11.client.api.calls.model.IvrBroadcastConfig;
import com.callfire.api11.client.api.calls.model.request.QueryCallsRequest;
import com.callfire.api11.client.api.calls.model.request.SendCallRequest;
import com.callfire.api11.client.api.common.model.ActionState;
import com.callfire.api11.client.api.common.model.LocalTimeZoneRestriction;
import com.callfire.api11.client.api.common.model.Result;
import com.callfire.api11.client.api.common.model.RetryConfig;
import com.callfire.api11.client.api.common.model.RetryPhoneType;
import com.callfire.api11.client.api.common.model.ToNumber;
import com.callfire.api11.client.api.contacts.model.NumbersField;
import com.callfire.api11.client.api.contacts.model.request.CreateContactListRequest;
import com.callfire.api11.client.api.subscriptions.model.NotificationFormat;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import com.callfire.api11.client.api.subscriptions.model.SubscriptionFilter;
import com.callfire.api11.client.api.subscriptions.model.TriggerEvent;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ClientUtilsTest {
    @Test
    public void buildQueryParams() throws Exception {
        String endpoint = "test_endpoint";
        Subscription subscription = new Subscription();
        subscription.setEndpoint(endpoint);
        subscription.setTriggerEvent(TriggerEvent.CAMPAIGN_STARTED);
        subscription.setNotificationFormat(NotificationFormat.JSON);
        subscription.setFilter(new SubscriptionFilter(1L, null, "123", "321"));
        String queryParams = ClientUtils.buildQueryParams(subscription).toString();

        System.out.println("params: " + queryParams);

        assertThat(queryParams, containsString("Endpoint=" + endpoint));
        assertThat(queryParams, containsString("TriggerEvent=CAMPAIGN_STARTED"));
        assertThat(queryParams, containsString("NotificationFormat=JSON"));
        assertThat(queryParams, containsString("BroadcastId=1"));
        assertThat(queryParams, not(containsString("BatchId")));
        assertThat(queryParams, containsString("FromNumber=123"));
        assertThat(queryParams, containsString("ToNumber=321"));
    }

    @Test
    public void toNumberToQueryString() throws Exception {
        assertEquals("12132212384?attr2=val2&attr1=val1",
            new ToNumber("12132212384", ToNumber.attributes("attr1", "val1", "attr2", "val2")).toQueryString());
        assertEquals("12132212384?attr1=val1",
            new ToNumber("12132212384", ToNumber.attributes("attr1", "val1")).toQueryString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void toNumberAttributesValidate() throws Exception {
        ToNumber.attributes("attr1", "val1", "test");
    }

    @Test
    public void buildQueryParamsComplexRequest() throws Exception {
        Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 10), 10), 10);
        Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 11), 20), 20);
        String dialplanXml = "<dialplan name=\"Root\"><play type=\"tts\" voice=\"female1\">Hello Callfire!</play></dialplan>";
        RetryConfig retryConfig = new RetryConfig(2, 1, asList(Result.BUSY, Result.NO_ANS),
            asList(RetryPhoneType.FIRST_NUMBER, RetryPhoneType.HOME_PHONE));

        List<ToNumber> recipients = asList(
            new ToNumber("12132212384", ToNumber.attributes("attr1", "val1", "attr2", "val2")),
            new ToNumber("12132212384", ToNumber.attributes("attr5", "val5", "attr6", "val6")),
            new ToNumber("12132212384", ToNumber.attributes("attr8", "val8", "attr9", "val9")));

        SendCallRequest request = SendCallRequest.create()
            .name("Send Call Java API Client")
            .maxActive(2)
            .scrubDuplicates(true)
            .labels(asList("label1", "label2"))
            .recipients(recipients)
            .ivrConfig(IvrBroadcastConfig.create()
                .fromNumber("12132212384")
                .retryConfig(retryConfig)
                .timeZoneRestriction(new LocalTimeZoneRestriction(localBeginTime, localEndTime))
                .dialplanXml(dialplanXml)
            )
            .build();
        String queryParams = ClientUtils.buildQueryParams(request).toString();

        System.out.println("params: " + queryParams);

        assertThat(queryParams, containsString("BroadcastName=Send Call Java API Client"));
        assertThat(queryParams, containsString("Type=IVR"));
        assertThat(queryParams, containsString("MaxActive=2"));
        assertThat(queryParams, containsString("Label=label1"));
        assertThat(queryParams, containsString("Label=label2"));
        assertThat(queryParams, containsString("From=12132212384"));
        assertThat(queryParams, containsString("To=12132212384?attr2=val2&attr1=val1"));
        assertThat(queryParams, containsString("To=12132212384?attr6=val6&attr5=val5"));
        assertThat(queryParams, containsString("To=12132212384?attr9=val9&attr8=val8"));
        assertThat(queryParams, containsString("MaxAttempts=2"));
        assertThat(queryParams, containsString("MinutesBetweenAttempts=1"));
        assertThat(queryParams, containsString("RetryResults=BUSY"));
        assertThat(queryParams, containsString("RetryResults=NO_ANS"));
        assertThat(queryParams, containsString("RetryPhoneTypes=FIRST_NUMBER"));
        assertThat(queryParams, containsString("RetryPhoneTypes=HOME_PHONE"));
        assertThat(queryParams, containsString("LocalRestrictBegin=10:10:10"));
        assertThat(queryParams, containsString("LocalRestrictEnd=11:20:20"));
        assertThat(queryParams, containsString("DialplanXml=" + dialplanXml));
    }

    @Test
    public void buildQueryParamsFromQueryRequest() throws Exception {
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
        String queryParams = ClientUtils.buildQueryParams(request).toString();

        assertThat(queryParams, containsString("MaxResults=10"));
        assertThat(queryParams, containsString("FirstResult=1"));
        assertThat(queryParams, containsString("BroadcastId=1"));
        assertThat(queryParams, containsString("BatchId=2"));
        assertThat(queryParams, containsString("Inbound=true"));
        assertThat(queryParams, containsString("FromNumber=1234567890"));
        assertThat(queryParams, containsString("ToNumber=111222333"));
        assertThat(queryParams, containsString("LabelName=labelName"));
        assertThat(queryParams, containsString("State=FINISHED"));
        assertThat(queryParams, containsString("Result=LA"));
        assertThat(queryParams, containsString("Result=NO_ANS"));
        assertThat(queryParams, containsString("IntervalBegin=2016-11-10T10:10:10"));
        assertThat(queryParams, containsString("IntervalEnd=2016-12-20T20:20:20"));

        System.out.println("query request: " + queryParams);
    }

    @Test
    public void serializeAsSingleString() throws Exception {
        CreateContactListRequest request = CreateContactListRequest.create()
            .name("new api")
            .validateContacts(false)
            .numbers(Arrays.asList("14112222555", "14112222666", "14112222777"))
            .numbersField(NumbersField.MOBILE_PHONE)
            .build();
        String queryParams = ClientUtils.buildQueryParams(request).toString();

        assertThat(queryParams, containsString("Name=new api"));
        assertThat(queryParams, containsString("Validate=false"));
        assertThat(queryParams, containsString("Numbers[fieldName]=" + NumbersField.MOBILE_PHONE.getFieldName()));
        assertThat(queryParams, containsString("Numbers=14112222555 14112222666 14112222777"));

        System.out.println("query request: " + queryParams);
    }
}
