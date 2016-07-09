package com.callfire.api11.client.api.broadcasts;

import com.callfire.api11.client.AccessForbiddenException;
import com.callfire.api11.client.BadRequestException;
import com.callfire.api11.client.CfApi11ApiException;
import com.callfire.api11.client.CfApi11ClientException;
import com.callfire.api11.client.InternalServerErrorException;
import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.RestApi11Client;
import com.callfire.api11.client.UnauthorizedException;
import com.callfire.api11.client.api.broadcasts.model.Broadcast;
import com.callfire.api11.client.api.broadcasts.model.BroadcastCommand;
import com.callfire.api11.client.api.broadcasts.model.BroadcastSchedule;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStats;
import com.callfire.api11.client.api.broadcasts.model.BroadcastType;
import com.callfire.api11.client.api.broadcasts.model.ContactBatch;
import com.callfire.api11.client.api.broadcasts.model.request.CreateBatchRequest;
import com.callfire.api11.client.api.broadcasts.model.request.QueryBroadcastsRequest;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.request.QueryByIdRequest;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api11.client.ClientConstants.TIMESTAMP_FORMAT_PATTERN;
import static com.callfire.api11.client.ClientUtils.addQueryParamIfSet;
import static com.callfire.api11.client.ClientUtils.asParams;
import static com.callfire.api11.client.ModelType.listOf;
import static com.callfire.api11.client.ModelType.of;
import static com.callfire.api11.client.ModelType.resourceOf;

/**
 * Represents /broadcast API endpoint. Used to create and control voice, IVR, text broadcasts
 */
public class BroadcastsApi {
    private static final String BROADCASTS_PATH = "/broadcast.json";
    private static final String BROADCASTS_ITEM_PATH = "/broadcast/{}.json";
    private static final String BROADCASTS_ITEM_STATS_PATH = "/broadcast/{}/stats.json";
    private static final String BROADCASTS_ITEM_CONTROL_PATH = "/broadcast/{}/control.json";
    private static final String BROADCASTS_ITEM_SCHEDULE_PATH = "/broadcast/{}/schedule.json";
    private static final String BROADCASTS_SCHEDULE_ITEM_PATH = "/broadcast/schedule/{}.json";
    private static final String BROADCASTS_ITEM_BATCH_PATH = "/broadcast/{}/batch.json";
    private static final String BROADCASTS_BATCH_ITEM_PATH = "/broadcast/batch/{}.json";
    private static final String BROADCASTS_BATCH_ITEM_CONTROL_PATH = "/broadcast/batch/{}/control.json";

    private RestApi11Client client;

    public BroadcastsApi(RestApi11Client client) {
        this.client = client;
    }

    /**
     * This operation creates a Broadcast campaign and returns a broadcastId. To see the status of this campaign
     * call GetBroadcast with the returned broadcastId.
     * There are 3 types of Broadcast: TEXT, IVR, or VOICE. Select the appropriate config to match the broadcast type,
     * TextBroadcastConfig, IvrBroadcastConfig, or VoiceBroadcastConfig. Prefer the Text Service operations (ex: SendText) and Call Service operations (ex: SendCall) over this operation when managing simple text and call campaigns since those operations are simpler and more concise.
     *
     * @param broadcast broadcast to create
     * @return id of newly created broadcast
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Long create(Broadcast broadcast) {
        Validate.notNull(broadcast, "broadcast cannot be null");
        Validate.notNull(broadcast.getType(), "broadcast.type cannot be null");
        if (broadcast.getVoiceBroadcastConfig() == null && broadcast.getIvrBroadcastConfig() == null
            && broadcast.getTextBroadcastConfig() == null) {
            throw new IllegalStateException("Missing one of " + Arrays.toString(BroadcastType.values()) +
                " configurations");
        }
        Validate.notNull(broadcast.getType(), "broadcast.type cannot be null");
        return client.post(BROADCASTS_PATH, of(ResourceReference.class), broadcast).getId();
    }

    /**
     * Use this operation to find broadcasts in account. Filter by type of campaign whether currently running.
     * Returns a list of Broadcast info such as campaign name, type, status, etc.
     *
     * @param request request object with filtering options
     * @return {@link List} of {@link Broadcast} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Broadcast> query(QueryBroadcastsRequest request) {
        return client.query(BROADCASTS_PATH, listOf(Broadcast.class), request).get();
    }

    /**
     * Get broadcast by id
     *
     * @param id broadcast id
     * @return {@link Broadcast} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Broadcast get(long id) {
        String path = BROADCASTS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(Broadcast.class)).get();
    }

    /**
     * Update existing broadcast's configuration such as time zone restrictions or retry logic
     *
     * @param broadcast broadcast to update
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void update(Broadcast broadcast) {
        Validate.notNull(broadcast, "broadcast cannot be null");
        Validate.notNull(broadcast.getId(), "broadcast.id cannot be null");
        String path = BROADCASTS_ITEM_PATH.replaceFirst(PLACEHOLDER, broadcast.getId().toString());
        client.put(path, of(Object.class), broadcast);
    }

    /**
     * Get broadcast stats by broadcastId or by interval range. Stats include information like billed amount,
     * billed duration, actions count, attempt count, etc.
     *
     * @param id broadcast id
     * @return {@link BroadcastStats} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public BroadcastStats getStats(long id) {
        return getStats(id, null, null);
    }

    /**
     * Get broadcast stats by broadcastId or by interval range. Stats include information like billed amount,
     * billed duration, actions count, attempt count, etc.
     *
     * @param id            broadcast id
     * @param intervalBegin start date/time
     * @param intervalEnd   end date/time
     * @return {@link BroadcastStats} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public BroadcastStats getStats(long id, Date intervalBegin, Date intervalEnd) {
        String path = BROADCASTS_ITEM_STATS_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        List<NameValuePair> params = new ArrayList<>(2);
        if (intervalBegin != null && intervalEnd != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(TIMESTAMP_FORMAT_PATTERN);
            addQueryParamIfSet("IntervalBegin", formatter.format(intervalBegin), params);
            addQueryParamIfSet("IntervalEnd", formatter.format(intervalEnd), params);
        }
        return client.get(path, resourceOf(BroadcastStats.class), params).get();
    }

    /**
     * Apply command START, STOP, or ARCHIVE to Broadcast
     *
     * @param id      broadcast id
     * @param command command
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void control(long id, BroadcastCommand command) {
        Validate.notNull(command, "command cannot be null");
        String path = BROADCASTS_ITEM_CONTROL_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        client.put(path, of(Object.class), null, asParams("Command", command));
    }

    /**
     * Apply command START, STOP, or ARCHIVE to Broadcast. Also can change the max active count of Broadcast.
     *
     * @param id        broadcast id
     * @param command   command
     * @param maxActive max simultaneous calls
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void control(long id, BroadcastCommand command, int maxActive) {
        Validate.notNull(command, "command cannot be null");
        String path = BROADCASTS_ITEM_CONTROL_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        client.put(path, of(Object.class), null, asParams("Command", command, "MaxActive", maxActive));
    }

    /**
     * Contact Batch is a list of contacts to associate with a broadcast. Use this operation to attach
     * a list of contacts to an existing Campaign. A list of ToNumbers or an existing Contact List ID is required
     * to create and attach the Contact List
     *
     * @param request request object
     * @return created batch id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Long createBatch(CreateBatchRequest request) {
        String path = BROADCASTS_ITEM_BATCH_PATH.replaceFirst(PLACEHOLDER, String.valueOf(request.getBroadcastId()));
        return client.post(path, of(ResourceReference.class), request).getId();
    }

    /**
     * Get information about individual Broadcast Schedule attached to a Broadcast.
     *
     * @param request request object
     * @return list of {@link ContactBatch} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<ContactBatch> queryBatches(QueryByIdRequest request) {
        String path = BROADCASTS_ITEM_BATCH_PATH.replaceFirst(PLACEHOLDER, request.getId().toString());
        return client.query(path, listOf(ContactBatch.class), request).get();
    }

    /**
     * Get information about individual Broadcast Schedule attached to a Broadcast.
     *
     * @param id BroadcastSchedule id
     * @return {@link Subscription} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public ContactBatch getBatch(long id) {
        String path = BROADCASTS_BATCH_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(ContactBatch.class)).get();
    }

    /**
     * This operation provides the ability to enable or disable on a Broadcast the list of contacts associated with a ContactBatch.
     *
     * @param id      batch id
     * @param enabled is batch enabled
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void controlBatch(long id, boolean enabled) {
        String path = BROADCASTS_BATCH_ITEM_CONTROL_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        client.put(path, of(Object.class), null, asParams("Enabled", enabled));
    }

    /**
     * Broadcast can be set to run at scheduled times a prescribed by BroadcastSchedule. Can pick start time,
     * stop time, begin date, and day of week.
     *
     * @param id       broadcast id
     * @param schedule schedule to assign
     * @return id of newly created schedule
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Long createSchedule(long id, BroadcastSchedule schedule) {
        Validate.notNull(schedule, "schedule cannot be null");
        String path = BROADCASTS_ITEM_SCHEDULE_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.post(path, of(ResourceReference.class), schedule).getId();
    }

    /**
     * Get information about individual Broadcast Schedule attached to a Broadcast.
     *
     * @param request request object
     * @return list of {@link BroadcastSchedule} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<BroadcastSchedule> querySchedules(QueryByIdRequest request) {
        String path = BROADCASTS_ITEM_SCHEDULE_PATH.replaceFirst(PLACEHOLDER, request.getId().toString());
        return client.query(path, listOf(BroadcastSchedule.class), request).get();
    }

    /**
     * Get information about individual Broadcast Schedule attached to a Broadcast.
     *
     * @param id BroadcastSchedule id
     * @return {@link Subscription} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public BroadcastSchedule getSchedule(long id) {
        String path = BROADCASTS_SCHEDULE_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(BroadcastSchedule.class)).get();
    }

    /**
     * Delete BroadcastSchedule associated with a broadcast.
     *
     * @param id BroadcastSchedule id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void deleteSchedule(long id) {
        client.delete(BROADCASTS_SCHEDULE_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }
}
