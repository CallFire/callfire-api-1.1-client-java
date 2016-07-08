package com.callfire.api11.client;

import com.callfire.api11.client.api.broadcasts.BroadcastsApi;
import com.callfire.api11.client.api.calls.CallsApi;
import com.callfire.api11.client.api.contacts.ContactsApi;
import com.callfire.api11.client.api.labels.LabelsApi;
import com.callfire.api11.client.api.subscriptions.SubscriptionsApi;
import com.callfire.api11.client.api.texts.TextsApi;
import com.callfire.api11.client.auth.BasicAuth;

import java.io.IOException;
import java.util.Properties;

import static com.callfire.api11.client.ClientConstants.CLIENT_CONFIG_FILE;

/**
 * Callfire API v1.1 client
 * <p>
 * <b>Authentication:</b> the CallFire API V1.1 uses HTTP Basic Authentication to verify
 * the user of an endpoint. A generated username/password API credential from your
 * account settings is required.
 * </p>
 * <b>Errors:</b> codes in the 400s range detail all of the errors a CallFire Developer could
 * encounter while using the API. Bad Request, Rate Limit Reached, and Unauthorized
 * are some of the sorts of responses in the 400s block. Codes in the 500s range are
 * error responses from the CallFire system. If an error has occurred anywhere in
 * the execution of a resource that was not due to user input, a 500 response
 * will be returned with a corresponding JSON error body. In that body will contain a message
 * detailing what went wrong.
 * API client methods throw 2 types of exceptions: API and client itself. API exceptions are mapped to
 * HTTP response codes:
 * <ul>
 * <li>{@link BadRequestException} - 400 - Bad request, the request was formatted improperly.</li>
 * <li>{@link UnauthorizedException} - 401 - Unauthorized, API Key missing or invalid.</li>
 * <li>{@link AccessForbiddenException} - 403 - Forbidden, insufficient permissions.</li>
 * <li>{@link ResourceNotFoundException} - 404 - NOT FOUND, the resource requested does not exist.</li>
 * <li>{@link InternalServerErrorException} - 500 - Internal Server Error.</li>
 * <li>{@link CfApi11ApiException} - other error codes mapped to base exception.</li>
 * </ul>
 * Client exceptions:
 * <ul>
 * <li>{@link CfApi11ClientException} - if error occurred inside client</li>
 * </ul>
 *
 * @author Vladimir Mikhailov (email: vmikhailov@callfire.com)
 * @see <a href="https://developers.callfire.com/docs.html">Callfire API documentation</a>
 * @see <a href="https://developers.callfire.com/learn.html">HowTos and examples</a>
 * @see <a href="http://stackoverflow.com/questions/tagged/callfire">Stackoverflow community questions</a>
 * @since 1.0
 */
public class CfApi11Client {
    private static Properties clientConfig = new Properties();

    static {
        loadConfig();
    }

    private RestApi11Client restApiClient;

    // api
    private BroadcastsApi broadcastsApi;
    private ContactsApi contactsApi;
    private CallsApi callsApi;
    private TextsApi textsApi;
    private SubscriptionsApi subscriptionsApi;
    private LabelsApi labelsApi;

    /**
     * Initialize Callfire client
     *
     * @param username API username
     * @param password API password
     */
    public CfApi11Client(String username, String password) {
        restApiClient = new RestApi11Client(new BasicAuth(username, password));
    }

    /**
     * Get client configuration
     *
     * @return configuration properties
     */
    public static Properties getClientConfig() {
        return clientConfig;
    }

    private static void loadConfig() {
        try {
            clientConfig.load(CfApi11Client.class.getResourceAsStream(CLIENT_CONFIG_FILE));
        } catch (IOException e) {
            throw new CfApi11ClientException("Cannot instantiate Callfire Client.", e);
        }
    }

    /**
     * Get REST api client which uses Apache httpclient inside
     *
     * @return rest client
     */
    public RestApi11Client getRestApiClient() {
        return restApiClient;
    }

    /**
     * Get /broadcast api endpoint
     *
     * @return endpoint object
     */
    public BroadcastsApi broadcastsApi() {
        if (broadcastsApi == null) {
            broadcastsApi = new BroadcastsApi(restApiClient);
        }
        return broadcastsApi;
    }

    /**
     * Get /contact api endpoint
     *
     * @return endpoint object
     */
    public ContactsApi contactsApi() {
        if (contactsApi == null) {
            contactsApi = new ContactsApi(restApiClient);
        }
        return contactsApi;
    }

    /**
     * Get /call api endpoint
     *
     * @return endpoint object
     */
    public CallsApi callsApi() {
        if (callsApi == null) {
            callsApi = new CallsApi(restApiClient);
        }
        return callsApi;
    }

    /**
     * Get /text api endpoint
     *
     * @return endpoint object
     */
    public TextsApi textsApi() {
        if (textsApi == null) {
            textsApi = new TextsApi(restApiClient);
        }
        return textsApi;
    }

    /**
     * Get /subscription api endpoint
     *
     * @return endpoint object
     */
    public SubscriptionsApi subscriptionsApi() {
        if (subscriptionsApi == null) {
            subscriptionsApi = new SubscriptionsApi(restApiClient);
        }
        return subscriptionsApi;
    }

    /**
     * Get /label api endpoint
     *
     * @return endpoint object
     */
    public LabelsApi labelsApi() {
        if (labelsApi == null) {
            labelsApi = new LabelsApi(restApiClient);
        }
        return labelsApi;
    }
}
