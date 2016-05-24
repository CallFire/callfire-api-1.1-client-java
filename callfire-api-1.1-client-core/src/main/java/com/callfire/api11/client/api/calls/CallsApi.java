package com.callfire.api11.client.api.calls;

import com.callfire.api11.client.AccessForbiddenException;
import com.callfire.api11.client.BadRequestException;
import com.callfire.api11.client.CfApi11ApiException;
import com.callfire.api11.client.CfApi11ClientException;
import com.callfire.api11.client.InternalServerErrorException;
import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.RestApi11Client;
import com.callfire.api11.client.UnauthorizedException;
import com.callfire.api11.client.api.calls.model.Call;
import com.callfire.api11.client.api.calls.model.request.QueryCallsRequest;
import com.callfire.api11.client.api.calls.model.request.SendCallRequest;
import com.callfire.api11.client.api.common.model.ResourceReference;

import java.util.List;

import static com.callfire.api11.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api11.client.ModelType.listOf;
import static com.callfire.api11.client.ModelType.of;
import static com.callfire.api11.client.ModelType.resourceOf;

/**
 * Represents /call API endpoint
 */
public class CallsApi {
    private static final String CALLS_PATH = "/call.json";
    private static final String CALLS_ITEM_PATH = "/call/{}.json";

    private RestApi11Client client;

    public CallsApi(RestApi11Client client) {
        this.client = client;
    }

    /**
     * Send a call
     * <p>
     * Start sending calls using VoiceBroadcastConfig or by setting up an IVR using IvrBroadcastConfig.
     * Sending a call requires at least a ToNumber and sound id or an IVR. The returned broadcastId can be passed
     * to QueryCalls to get state of call actions in campaign and get list of individual callIds for use
     * in GetCall request. The broadcastId can also be passed to GetBroadcastStats to get information about the
     * call campaign, such as BilledAmount, Duration, State, etc...
     *
     * @param request request object
     * @return broadcast id which was used to send a call
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public long send(SendCallRequest request) {
        return client.post(CALLS_PATH, of(ResourceReference.class), request).getId();
    }

    /**
     * Query for calls using standard ActionQuery which filters on batchId, broadcastId, toNumber, etc...
     * Returns a list of calls and all associated info. See GetCall to return just a single call action by id.
     *
     * @param request request object with filtering options
     * @return {@link List} of {@link Call} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Call> query(QueryCallsRequest request) {
        return client.query(CALLS_PATH, listOf(Call.class), request).get();
    }

    /**
     * Return individual call action. See QueryCalls to return a list of call actions and determine individual callIds.
     * General usage is to start a call campaign using SendCall, then to pass returned broadcastId to QueryCalls
     * to determine callIds of call actions created. This GetCall can then be called with unique callId to determine
     * info and state of call action.
     *
     * @param id call id
     * @return {@link List} of {@link Call} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Call get(long id) {
        String path = CALLS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(Call.class)).get();
    }
}
