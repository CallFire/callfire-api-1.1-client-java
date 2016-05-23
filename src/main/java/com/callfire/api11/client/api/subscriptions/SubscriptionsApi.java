package com.callfire.api11.client.api.subscriptions;

import com.callfire.api11.client.AccessForbiddenException;
import com.callfire.api11.client.BadRequestException;
import com.callfire.api11.client.CfApi11ApiException;
import com.callfire.api11.client.CfApi11ClientException;
import com.callfire.api11.client.InternalServerErrorException;
import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.RestApi11Client;
import com.callfire.api11.client.UnauthorizedException;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.List;

import static com.callfire.api11.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api11.client.ClientUtils.asParams;
import static com.callfire.api11.client.ModelType.listOf;
import static com.callfire.api11.client.ModelType.of;
import static com.callfire.api11.client.ModelType.resourceOf;

/**
 * Represents /subscription API endpoint
 */
public class SubscriptionsApi {
    private static final String SUBSCRIPTIONS_PATH = "/subscription.json";
    private static final String SUBSCRIPTIONS_ITEM_PATH = "/subscription/{}.json";

    private RestApi11Client client;

    public SubscriptionsApi(RestApi11Client client) {
        this.client = client;
    }

    /**
     * Creates a new subscription for CallFire event notifications
     * <p>
     * Method registers a URI endpoint to start receiving CallFire notification events on. Returned is the id that
     * can be used later to query, update, or delete the subscription. The subscriptionId is also returned as part
     * of all notification events as 'subscriptionId'. A URI endpoint will need to be provided that can handle
     * the HTTP notification events coming from CallFire.com.
     *
     * @param subscription subscription to create
     * @return {@link Subscription} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Long create(Subscription subscription) {
        Validate.notNull(subscription, "subscription cannot be null");
        return client.post(SUBSCRIPTIONS_PATH, of(ResourceReference.class), subscription).getId();
    }

    /**
     * Get a list of registered subscriptions
     * Return a list of all subscriptions registered to an account. Subscriptions returned contain info like id,
     * enabled, endpoint, filter, etc...
     *
     * @param firstResult Start of next result set
     * @param maxResults  Max number of results to return limited to 1000
     * @return {@link List} of {@link Subscription} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Subscription> query(long firstResult, long maxResults) {
        List<NameValuePair> params = asParams("FirstResult", firstResult, "MaxResults", maxResults);
        return client.get(SUBSCRIPTIONS_PATH, listOf(Subscription.class), params).get();
    }

    /**
     * Get subscription by id
     *
     * @param id subscription id
     * @return {@link Subscription} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Subscription get(long id) {
        String path = SUBSCRIPTIONS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(Subscription.class)).get();
    }

    /**
     * Update subscription
     * Use this to enable or disable notification events, change the notification endpoint URI, or change
     * the filtering so only receive notification for a subset of events.
     *
     * @param subscription subscription to update
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void update(Subscription subscription) {
        Validate.notNull(subscription, "subscription cannot be null");
        Validate.notNull(subscription.getId(), "subscription.id cannot be null");
        String path = SUBSCRIPTIONS_ITEM_PATH.replaceFirst(PLACEHOLDER, subscription.getId().toString());
        client.put(path, of(Object.class), subscription);
    }

    /**
     * Delete subscription to stop receiving CallFire notification events at the registered URI postback endpoint.
     *
     * @param id subscription to create
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void delete(long id) {
        client.delete(SUBSCRIPTIONS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }
}
