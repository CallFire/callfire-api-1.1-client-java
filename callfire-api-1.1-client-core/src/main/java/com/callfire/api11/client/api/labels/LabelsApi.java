package com.callfire.api11.client.api.labels;

import com.callfire.api11.client.AccessForbiddenException;
import com.callfire.api11.client.BadRequestException;
import com.callfire.api11.client.CfApi11ApiException;
import com.callfire.api11.client.CfApi11ClientException;
import com.callfire.api11.client.InternalServerErrorException;
import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.RestApi11Client;
import com.callfire.api11.client.UnauthorizedException;
import com.callfire.api11.client.api.common.model.Label;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import org.apache.commons.lang3.Validate;

import java.util.List;

import static com.callfire.api11.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api11.client.ClientUtils.asParams;
import static com.callfire.api11.client.ModelType.listOf;
import static com.callfire.api11.client.ModelType.of;

/**
 * Using labels API you can assign or remove label to/from broadcast or number
 */
public class LabelsApi {
    private static final String LABELS_PATH = "/label.json";
    private static final String LABELS_BROADCAST_PATH = "/label/broadcast/{}.json";
    private static final String LABELS_NUMBER_PATH = "/label/number/{}.json";

    private RestApi11Client client;

    public LabelsApi(RestApi11Client client) {
        this.client = client;
    }

    /**
     * Return list of all defined label names. The labels may be associated with broadcasts or numbers.
     *
     * @param request request object with filtering options
     * @return {@link List} of {@link Label} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Label> query(QueryRequest request) {
        return client.query(LABELS_PATH, listOf(Label.class), request).get();
    }

    /**
     * Delete label identified by name. All broadcasts and numbers currently containing the label will have the label
     * association removed.
     *
     * @param labelName label to remove
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void delete(String labelName) {
        Validate.notBlank(labelName, "labelName cannot be blank");
        client.delete(LABELS_PATH, asParams("LabelName", labelName));
    }

    /**
     * Label broadcast (Voice, Text, or IVR) by specifying broadcastId and label name. If label name doesn't currently
     * exist on system it will be created and saved.
     *
     * @param id        broadcast id
     * @param labelName label to add
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void labelBroadcast(long id, String labelName) {
        Validate.notBlank(labelName, "labelName cannot be blank");
        String path = LABELS_BROADCAST_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        client.post(path, of(Object.class), null, asParams("LabelName", labelName, "Id", id));
    }

    /**
     * Remove label from broadcast. This doesn't remove label from system, it just removes association between broadcast and label.
     *
     * @param id        broadcast id
     * @param labelName label to remove
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void unlabelBroadcast(long id, String labelName) {
        Validate.notBlank(labelName, "labelName cannot be blank");
        String path = LABELS_BROADCAST_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        client.delete(path, asParams("LabelName", labelName, "Id", id));
    }

    /**
     * Label number by specifying E.164 11 digit number identifier and label name. If label name doesn't currently
     * exist on system it will be created and saved.
     *
     * @param number    number to add label
     * @param labelName label to add
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void labelNumber(String number, String labelName) {
        Validate.notBlank(number, "number cannot be blank");
        Validate.notBlank(labelName, "labelName cannot be blank");
        String path = LABELS_NUMBER_PATH.replaceFirst(PLACEHOLDER, number);
        client.post(path, of(Object.class), null, asParams("LabelName", labelName, "Number", number));
    }

    /**
     * Remove label from number. This doesn't remove label from system, it just removes association between number and label.
     *
     * @param number    label to remove
     * @param labelName label to remove
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void unlabelNumber(String number, String labelName) {
        Validate.notBlank(number, "number cannot be blank");
        Validate.notBlank(labelName, "labelName cannot be blank");
        String path = LABELS_NUMBER_PATH.replaceFirst(PLACEHOLDER, number);
        client.delete(path, asParams("LabelName", labelName, "Number", number));
    }
}
