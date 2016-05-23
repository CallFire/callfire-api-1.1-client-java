package com.callfire.api11.client.api.texts;

import com.callfire.api11.client.AccessForbiddenException;
import com.callfire.api11.client.BadRequestException;
import com.callfire.api11.client.CfApi11ApiException;
import com.callfire.api11.client.CfApi11ClientException;
import com.callfire.api11.client.InternalServerErrorException;
import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.RestApi11Client;
import com.callfire.api11.client.UnauthorizedException;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.texts.model.Text;
import com.callfire.api11.client.api.texts.model.request.QueryTextsRequest;
import com.callfire.api11.client.api.texts.model.request.SendTextRequest;

import java.util.List;

import static com.callfire.api11.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api11.client.ModelType.listOf;
import static com.callfire.api11.client.ModelType.of;
import static com.callfire.api11.client.ModelType.resourceOf;

/**
 * Represents /text API endpoint
 */
public class TextsApi {
    private static final String TEXTS_PATH = "/text.json";
    private static final String TEXTS_ITEM_PATH = "/text/{}.json";

    private RestApi11Client client;

    public TextsApi(RestApi11Client client) {
        this.client = client;
    }

    /**
     * Send text message
     * <p>
     * Sending a text message requires at least a toNumber and a message. SendText starts a text campaign and returns
     * the broadcastId if campaign is successful started. This returned broadcastId can be passed to QueryTexts
     * to get state of text messages in campaign and get list of individual textId for use in GetText calls.
     * The broadcastId can also be passed to GetBroadcastStats to get information about the text campaign,
     * such as BilledAmount, Duration, State, etc...
     * The industry standard is for text messages to be limited to 160 characters or less.
     * If the message is over 160 characters then a BigMessageStrategy should be selected in the TextBroadcastConfig.
     *
     * @param request request object
     * @return broadcast id which was used to send text
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public long send(SendTextRequest request) {
        return client.post(TEXTS_PATH, of(ResourceReference.class), request).getId();
    }

    /**
     * Query for text messages using standard ActionQuery which filters on batchId, broadcastId, toNumber, etc...
     * Returns a list of text messages and all associated info. See GetText to return just a single text action by id.
     *
     * @param request request object with filtering options
     * @return {@link List} of {@link Text} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Text> query(QueryTextsRequest request) {
        return client.query(TEXTS_PATH, listOf(Text.class), request).get();
    }

    /**
     * Return individual text message. See QueryTexts to return a list of text messages and determine individual textIds.
     * General usage is to start a text campaign using SendText, then to pass returned broadcastId to QueryTexts to
     * determine textIds of text messages created. This GetText can then be called with unique textId to determine
     * info and state of text message.
     *
     * @param id text id
     * @return {@link List} of {@link Text} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Text get(long id) {
        return client.get(TEXTS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)), resourceOf(Text.class)).get();
    }
}
