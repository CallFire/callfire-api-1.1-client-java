package com.callfire.api11.client.integration;

import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.api.broadcasts.model.Broadcast;
import com.callfire.api11.client.api.broadcasts.model.BroadcastCommand;
import com.callfire.api11.client.api.broadcasts.model.BroadcastSchedule;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStats;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStatus;
import com.callfire.api11.client.api.broadcasts.model.BroadcastType;
import com.callfire.api11.client.api.broadcasts.model.ContactBatch;
import com.callfire.api11.client.api.broadcasts.model.DayOfWeek;
import com.callfire.api11.client.api.broadcasts.model.request.CreateBatchRequest;
import com.callfire.api11.client.api.broadcasts.model.request.QueryBroadcastsRequest;
import com.callfire.api11.client.api.common.model.LocalTimeZoneRestriction;
import com.callfire.api11.client.api.common.model.ToNumber;
import com.callfire.api11.client.api.common.model.request.QueryByIdRequest;
import com.callfire.api11.client.api.texts.model.BigMessageStrategy;
import com.callfire.api11.client.api.texts.model.TextBroadcastConfig;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.TIME_FORMAT_PATTERN;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.time.DateFormatUtils.format;
import static org.apache.commons.lang3.time.DateFormatUtils.formatUTC;
import static org.apache.commons.lang3.time.DateUtils.isSameDay;
import static org.apache.commons.lang3.time.DateUtils.parseDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Ignore
public class BroadcastsIntegrationTest extends AbstractIntegrationTest {
    private static Calendar startDate = Calendar.getInstance();
    private static Calendar endDate = Calendar.getInstance();
    private static Calendar beginTime = Calendar.getInstance();
    private static Calendar endTime = Calendar.getInstance();
    private static String dialplanXml = "<dialplan name=\"Root\"><play type=\"tts\" voice=\"female1\">Hello Callfire!</play></dialplan>";

    static {
        startDate.set(2000, Calendar.JANUARY, 10, 10, 10, 10);
        endDate.set(2020, Calendar.DECEMBER, 15, 15, 15, 15);
        beginTime.set(1970, Calendar.JANUARY, 1, 10, 10, 10);
        endTime.set(1970, Calendar.JANUARY, 1, 20, 20, 20);

    }

    @Test
    public void createIvrBroadcast() throws Exception {

    }

    @Test
    public void textBroadcastCrudOperations() throws Exception {
        Broadcast broadcast = new Broadcast();
        broadcast.setName("Broadcast 1");
        broadcast.setType(BroadcastType.TEXT);
        // TODO uncomment once fixed
        //        broadcast.setLabels(asList("label1"));
        broadcast.setTextBroadcastConfig(TextBroadcastConfig.create()
            .fromNumber("67076")
            .message("Api test message")
            .strategy(BigMessageStrategy.TRIM)
            .timeZoneRestriction(new LocalTimeZoneRestriction(
                parseDate("10:10:10", TIME_FORMAT_PATTERN),
                parseDate("15:15:15", TIME_FORMAT_PATTERN)))
            .build());

        Long id = client.broadcastsApi().create(broadcast);

        broadcast.setId(id);
        broadcast.setName(broadcast.getName() + " updated");
        broadcast.setLabels(singletonList("broadcast_label"));

        client.broadcastsApi().update(broadcast);
        client.broadcastsApi().control(id, BroadcastCommand.ARCHIVE, 200);
        Broadcast savedBroadcast = client.broadcastsApi().get(id);
        assertEquals(BroadcastStatus.ARCHIVED, savedBroadcast.getStatus());
        assertEquals(savedBroadcast.getName(), savedBroadcast.getName());
        assertEquals(BroadcastType.TEXT, savedBroadcast.getType());
        assertEquals("Api test message", savedBroadcast.getTextBroadcastConfig().getMessage());
        assertEquals("67076", savedBroadcast.getTextBroadcastConfig().getFromNumber());
        // TODO uncomment once fixed
        //        assertEquals(BigMessageStrategy.TRIM, savedBroadcast.getTextBroadcastConfig().getBigMessageStrategy());
        LocalTimeZoneRestriction tzRestriction = savedBroadcast.getTextBroadcastConfig()
            .getLocalTimeZoneRestriction();
        assertEquals("10:10:10", formatUTC(tzRestriction.getBeginTime(), TIME_FORMAT_PATTERN));
        assertEquals("15:15:15", formatUTC(tzRestriction.getEndTime(), TIME_FORMAT_PATTERN));

        QueryBroadcastsRequest request = QueryBroadcastsRequest.create()
            .firstResult(0)
            .maxResults(100)
            .type(BroadcastType.TEXT)
            .running(false)
            .labelName("broadcast_label")
            .build();
        List<Broadcast> broadcasts = client.broadcastsApi().query(request);
        // TODO uncomment once fixed
        //        assertFalse(broadcasts.isEmpty());
        //        assertThat(broadcasts.get(0).getLabels(), hasItem("broadcast_label"));
    }

    @Test
    public void batchCrudOperations() throws Exception {
        Long broadcastId = 11741873003L;
        Date started = new Date();

        CreateBatchRequest createRequest = CreateBatchRequest.create()
            .name("Contact batch")
            .broadcastId(broadcastId)
            .scrubDuplicates(false)
            .start(false)
            .numbers(Arrays.asList(
                new ToNumber("12132212384", ToNumber.attributes("attr1", "val1", "attr2", "val2")),
                new ToNumber("12132212385", ToNumber.attributes("attr5", "val5", "attr6", "val6")),
                new ToNumber("12132212386", ToNumber.attributes("attr8", "val8", "attr9", "val9"))))
            .build();

        Long id = client.broadcastsApi().createBatch(createRequest);
        ContactBatch savedBatch = client.broadcastsApi().getBatch(id);

        assertEquals(Integer.valueOf(3), savedBatch.getSize());
        assertEquals(createRequest.getName(), savedBatch.getName());
        assertEquals(broadcastId.longValue(), savedBatch.getBroadcastId());
        assertEquals(Integer.valueOf(3), savedBatch.getRemaining());
        assertTrue(savedBatch.getCreated().after(started));

        QueryByIdRequest queryRequest = QueryByIdRequest.create().id(broadcastId).build();
        List<ContactBatch> batches = client.broadcastsApi().queryBatches(queryRequest);
        assertTrue(batches.size() > 0);

        client.broadcastsApi().controlBatch(id, false);
    }

    @Test
    public void getStats() throws Exception {
        BroadcastStats stats = client.broadcastsApi().getStats(11741873003L);
        System.out.println(stats);
    }

    @Test
    public void scheduleCrudOperations() throws Exception {
        Long broadcastId = 11741873003L;

        BroadcastSchedule schedule = new BroadcastSchedule();
        schedule.setTimeZone("America/New_York");
        schedule.setDaysOfWeek(asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        schedule.setBeginDate(startDate.getTime());
        schedule.setEndDate(endDate.getTime());
        schedule.setStartTimeOfDay(beginTime.getTime());
        schedule.setStopTimeOfDay(endTime.getTime());

        Long id = client.broadcastsApi().createSchedule(broadcastId, schedule);
        assertNotNull(id);

        QueryByIdRequest request = QueryByIdRequest.create().id(broadcastId).build();
        List<BroadcastSchedule> schedules = client.broadcastsApi().querySchedules(request);
        assertEquals(1, schedules.size());

        BroadcastSchedule savedSchedule = schedules.get(0);
        assertEquals(id, savedSchedule.getId());
        assertEquals(schedule.getTimeZone(), savedSchedule.getTimeZone());
        assertEquals(schedule.getDaysOfWeek(), savedSchedule.getDaysOfWeek());

        assertTrue(isSameDay(schedule.getBeginDate(), savedSchedule.getBeginDate()));
        assertTrue(isSameDay(schedule.getEndDate(), savedSchedule.getEndDate()));
        assertEquals(format(schedule.getStartTimeOfDay(), TIME_FORMAT_PATTERN),
            formatUTC(savedSchedule.getStartTimeOfDay(), TIME_FORMAT_PATTERN));
        assertEquals(format(schedule.getStopTimeOfDay(), TIME_FORMAT_PATTERN),
            formatUTC(savedSchedule.getStopTimeOfDay(), TIME_FORMAT_PATTERN));
        savedSchedule = client.broadcastsApi().getSchedule(id);
        assertEquals(schedule.getDaysOfWeek(), savedSchedule.getDaysOfWeek());

        client.broadcastsApi().deleteSchedule(id);

        ex.expect(ResourceNotFoundException.class);
        ex.expectMessage("Not Found");
        client.broadcastsApi().deleteSchedule(id);
    }
}
