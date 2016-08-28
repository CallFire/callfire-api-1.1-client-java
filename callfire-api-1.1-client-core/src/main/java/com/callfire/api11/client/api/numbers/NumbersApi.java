package com.callfire.api11.client.api.numbers;

import com.callfire.api11.client.AccessForbiddenException;
import com.callfire.api11.client.BadRequestException;
import com.callfire.api11.client.CfApi11ApiException;
import com.callfire.api11.client.CfApi11ClientException;
import com.callfire.api11.client.InternalServerErrorException;
import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.RestApi11Client;
import com.callfire.api11.client.UnauthorizedException;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.numbers.model.Keyword;
import com.callfire.api11.client.api.numbers.model.Number;
import com.callfire.api11.client.api.numbers.model.NumberOrder;
import com.callfire.api11.client.api.numbers.model.Region;
import com.callfire.api11.client.api.numbers.model.request.ConfigureNumberRequest;
import com.callfire.api11.client.api.numbers.model.request.CreateOrderRequest;
import com.callfire.api11.client.api.numbers.model.request.QueryNumbersRequest;
import com.callfire.api11.client.api.numbers.model.request.QueryRegionsRequest;
import com.callfire.api11.client.api.numbers.model.request.SearchNumbersRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.List;

import static com.callfire.api11.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api11.client.ClientUtils.asParams;
import static com.callfire.api11.client.ModelType.listOf;
import static com.callfire.api11.client.ModelType.of;
import static com.callfire.api11.client.ModelType.resourceOf;

/**
 * Use numbers API for search/purchase/release phone numbers and keywords in Callfire account
 */
public class NumbersApi {
    private static final String NUMBERS_REGIONS_PATH = "/number/regions.json";
    private static final String NUMBERS_PATH = "/number.json";
    private static final String NUMBERS_ITEM_PATH = "/number/{}.json";
    private static final String NUMBERS_SEARCH_PATH = "/number/search.json";
    private static final String NUMBERS_KEYWORD_PATH = "/number/keyword.json";
    private static final String NUMBERS_KEYWORD_SEARCH_PATH = "/number/keyword/search.json";
    private static final String NUMBERS_ORDER_PATH = "/number/order.json";
    private static final String NUMBERS_ORDER_ITEM_PATH = "/number/order/{}.json";
    private static final String NUMBERS_RELEASE_PATH = "/number/release.json";

    private RestApi11Client client;

    public NumbersApi(RestApi11Client client) {
        this.client = client;
    }

    /**
     * Use a know subset of info on a region to query for the full set of info on a region.
     * Example, if you know the city you can query for phone number prefixes, rate-centers, etc.
     * associated with that city.
     *
     * @param request request object with filtering options
     * @return list of regions
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Region> queryRegions(QueryRegionsRequest request) {
        return client.query(NUMBERS_REGIONS_PATH, listOf(Region.class), request).get();
    }

    /**
     * Find numbers available for purchase using either TollFree = true or by region info, such as prefix, city, zipcode, etc.
     * The max count of numbers to return must also be specified.
     *
     * @param request request object with filtering options
     * @return list of available numbers
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Number> search(SearchNumbersRequest request) {
        return client.query(NUMBERS_SEARCH_PATH, listOf(Number.class), request).get();
    }

    /**
     * Get all keywords owned by your account.
     *
     * @param request request object with filtering options
     * @return list of keywords owned by your account
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Keyword> queryKeywords(QueryRequest request) {
        return client.query(NUMBERS_KEYWORD_PATH, listOf(Keyword.class), request).get();
    }

    /**
     * Supply list of keywords to see if they are available for purchase. If keyword is available for purchase
     * it will be returned in result. If not available then it will not be returned in result.
     *
     * @param keywords keywords to search
     * @return list of available keywords
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Keyword> searchKeywords(List<String> keywords) {
        List<NameValuePair> params = asParams("Keywords", StringUtils.join(keywords, " "));
        return client.get(NUMBERS_KEYWORD_SEARCH_PATH, listOf(Keyword.class), params).get();
    }

    /**
     * Search for numbers already purchased and in your account by region info such as prefix, city,
     * state, zipcode, rate center, etc.
     * Returns info on the numbers in your account such as status, lease info, configuration, etc.
     *
     * @param request request object with filtering options
     * @return list of numbers
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Number> query(QueryNumbersRequest request) {
        return client.query(NUMBERS_PATH, listOf(Number.class), request).get();
    }

    /**
     * Select number, using 11 digit E.164 format, already purchased and in your account. Returns info on the number
     * in your account such as status, lease info, configuration, etc...
     *
     * @param number 11 digit E.164 format
     * @return number object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Number get(String number) {
        Validate.notBlank(number, "number cannot be blank");
        String path = NUMBERS_ITEM_PATH.replaceFirst(PLACEHOLDER, number);
        return client.get(path, resourceOf(Number.class)).get();
    }

    /**
     * Update number configuration, such as ENABLE / DISABLE or turn call recording on, using 11 diget E.164 format
     * to select number.
     *
     * @param request request object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void configure(ConfigureNumberRequest request) {
        String path = NUMBERS_ITEM_PATH.replaceFirst(PLACEHOLDER, request.getNumber());
        client.put(path, of(Object.class), request);
    }

    /**
     * Purchase numbers and keywords by creating a number order that includes a list of numbers, list of keywords,
     * region info, or specifying toll-free. OrderId is returned from request.
     * Creating a number order is an asynchronous process. The returned orderId can be used in a getOrder
     * request to see the status of the order. However, there is no guarantee the order will be finished by
     * the time getOrder is called therefore the getOrder operation must be polled (no more than once a second)
     * until the order is in a terminal state (FINISHED or ERRORED).
     *
     * @param request request object
     * @return order id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Long order(CreateOrderRequest request) {
        return client.post(NUMBERS_ORDER_PATH, of(ResourceReference.class), request).getId();
    }

    /**
     * Since CreateNumberOrder is an asynchronous process this GetNumberOrder operation may need to be polled
     * for up to 20 seconds at a rate of no more than once a second to determine when the order is in a terminal
     * state of FINISHED or ERRORED. Order will be in 'PROCESSING' status until finished.
     * Returns information on the order status, cost, and assets acquired.
     *
     * @param id order id
     * @return order object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public NumberOrder getOrder(long id) {
        String path = NUMBERS_ORDER_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(NumberOrder.class)).get();
    }

    /**
     * CallFire's system automatically "renews" your numbers and keywords each month and bills you accordingly.
     * When you no longer need a number or keyword, you can easily 'Release' it and stop your recurring monthly charge.
     * Once you release a keyword, you will not be able to repurchase it for a month after the release becomes effective.
     * For example, if your keyword is slated to auto-renew for another month on April 29th, and you release it
     * on April 15th, you will still be able to use it until April 29th. However, you will not be able
     * to repurchase it until May 29th. Therefore, please be sure of your decision before releasing.
     *
     * @param number  number to release
     * @param keyword keyword to release
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void release(String number, String keyword) {
        client.put(NUMBERS_RELEASE_PATH, of(Object.class), null, asParams("Number", number, "Keyword", keyword));
    }
}
