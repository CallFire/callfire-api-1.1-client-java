package com.callfire.api11.client.api.ccc;

import com.callfire.api11.client.*;
import com.callfire.api11.client.api.broadcasts.model.Broadcast;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStats;
import com.callfire.api11.client.api.broadcasts.model.request.ControlBroadcastRequest;
import com.callfire.api11.client.api.ccc.model.*;
import com.callfire.api11.client.api.ccc.model.request.*;
import com.callfire.api11.client.api.common.model.ResourceReference;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api11.client.ClientConstants.TIMESTAMP_FORMAT_PATTERN;
import static com.callfire.api11.client.ClientUtils.addQueryParamIfSet;
import static com.callfire.api11.client.ClientUtils.asParams;
import static com.callfire.api11.client.ModelType.*;

/**
 * Represents /ccc API endpoint. Used to create and control CCC broadcasts, work with agents, agent groups and sessions
 */
public class CccsApi {

    private static final String BROADCASTS_PATH = "/ccc.json";
    private static final String BROADCASTS_ITEM_PATH = "/ccc/{}.json";
    private static final String BROADCASTS_ITEM_STATS_PATH = "/ccc/{}/stats.json";
    private static final String BROADCASTS_ITEM_TRANSFER_NUMBERS_PATH = "/ccc/{}/transfer-numbers.json";
    private static final String BROADCASTS_ITEM_QUESTIONS_PATH = "/ccc/{}/questions.json";
    private static final String BROADCASTS_ITEM_CONTROL_PATH = "/ccc/{}/control.json";

    private static final String CCC_AGENT_PATH = "/ccc/agent.json";
    private static final String CCC_AGENT_ITEM_PATH = "/ccc/agent/{}.json";
    private static final String BROADCASTS_ITEM_AGENT_ITEM_PATH = "/ccc/{}/agent/{}.json";
    private static final String BROADCASTS_ITEM_AGENT_PATH = "/ccc/{}/agent.json";

    private static final String CCC_AGENT_GROUP_PATH = "/ccc/agent-group.json";
    private static final String CCC_AGENT_GROUP_ITEM_PATH = "/ccc/agent-group/{}.json";
    private static final String BROADCASTS_ITEM_AGENT_GROUP_ITEM_PATH = "/ccc/{}/agent-group/{}.json";
    private static final String BROADCASTS_ITEM_AGENT_GROUP_PATH = "/ccc/{}/agent-group.json";

    private static final String CCC_AGENT_SESSION_PATH = "/ccc/agent-session.json";
    private static final String CCC_AGENT_SESSION_ITEM_PATH = "/ccc/agent-session/{}.json";

    private static final String BROADCASTS_ITEM_AGENT_INVITE_PATH = "/ccc/{}/agent-invite.json";
    private static final String BROADCASTS_ITEM_AGENT_INVITE_URI_PATH = "/ccc/{}/agent-invite-uri.json";

    private RestApi11Client client;

    public CccsApi(RestApi11Client client) {
        this.client = client;
    }

    /**
     * This operation creates a CCC Broadcast campaign and returns a broadcastId. To see the status of this campaign
     * call GetCccBroadcast with the returned broadcastId.
     *
     * @param broadcast CCC broadcast to create
     * @return id of newly created broadcast
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Long create(CccBroadcast broadcast) {
        Validate.notNull(broadcast, "broadcast cannot be null");
        return client.post(BROADCASTS_PATH, of(ResourceReference.class), broadcast).getId();
    }

    /**
     * Get CCC broadcast by id
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
    public CccBroadcast get(long id) {
        String path = BROADCASTS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(CccBroadcast.class)).get();
    }

    /**
     * Use this operation to find ccc broadcasts in account. Filter by Name, LabelName, Running attributes etc.
     * Returns a list of CccBroadcast info such as campaign name, type, status, etc.
     *
     * @param request request object with filtering options
     * @return {@link List} of {@link CccBroadcast} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<CccBroadcast> query(QueryCccBroadcastsRequest request) {
        return client.query(BROADCASTS_PATH, listOf(CccBroadcast.class), request).get();
    }

    /**
     * Get CCC broadcast stats by broadcastId or by interval range. Stats include information like billed amount,
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
     * Get CCC broadcast stats by broadcastId or by interval range. Stats include information like billed amount,
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
     * Update existing CCC broadcast's configuration such as retry logic etc
     *
     * @param broadcast ccc broadcast to update
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void update(CccBroadcast broadcast) {
        Validate.notNull(broadcast, "broadcast cannot be null");
        Validate.notNull(broadcast.getId(), "broadcast.id cannot be null");
        String path = BROADCASTS_ITEM_PATH.replaceFirst(PLACEHOLDER, broadcast.getId().toString());
        client.put(path, of(Object.class), broadcast);
    }

    /**
     * Delete all transfer numbers from existing CCC Campaign
     *
     * @param id CCC Broadcast id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void deleteTransferNumbers(long id) {
        client.delete(BROADCASTS_ITEM_TRANSFER_NUMBERS_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }

    /**
     * Delete all questions from existing CCC Campaign
     *
     * @param id CCC Broadcast id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void deleteQuestions(long id) {
        client.delete(BROADCASTS_ITEM_QUESTIONS_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }

    /**
     * Apply command START, STOP, or ARCHIVE to CCC Broadcast. Also can change the max active count of Broadcast.
     *
     * @param request request with broadcast id, command etc
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void control(ControlBroadcastRequest request) {
        String path = BROADCASTS_ITEM_CONTROL_PATH.replaceFirst(PLACEHOLDER, String.valueOf(request.getId()));
        client.put(path, of(Object.class), request);
    }

    /**
     * DELETE Ccc Campaign by ID
     *
     * @param id CCC Broadcast id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void delete(long id) {
        client.delete(BROADCASTS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }

    /**
     * Add Agents to Ccc Campaign
     *
     * @param request add agents request with broadcast id and agents data
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void addCampaignAgents(AddAgentsRequest request) {
        String path = BROADCASTS_ITEM_AGENT_PATH.replaceFirst(PLACEHOLDER, request.getBroadcastId().toString());
        client.post(path, of(Object.class), request);
    }

    /**
     * Get Agents attached with Campaign
     *
     * @param broadcastId CCC Broadcast id
     * @return {@link List} of {@link Agent} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Agent> queryCampaignAgents(long broadcastId) {
        String path = BROADCASTS_ITEM_AGENT_PATH.replaceFirst(PLACEHOLDER, String.valueOf(broadcastId));
        return client.query(path, listOf(Agent.class)).get();
    }

    /**
     * Get all existing Agents
     *
     * @param request query request with parameters like CampaignId, AgentMail, AgentGroupId etc
     * @return {@link List} of {@link Agent} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Agent> queryAgents(QueryAgentsRequest request) {
        return client.query(CCC_AGENT_PATH, listOf(Agent.class), request).get();
    }

    /**
     * Get Agent by id
     *
     * @param id Agent id
     * @return {@link Agent} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Agent getAgent(long id) {
        return client.get(CCC_AGENT_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)), resourceOf(Agent.class)).get();
    }

    /**
     * DELETE Agent from Ccc Campaign
     *
     * @param broadcastId CCC Broadcast id
     * @param id CCC Agent id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void removeAgentFromCampaign(long broadcastId, long id) {
        client.delete(BROADCASTS_ITEM_AGENT_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(broadcastId)).replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }

    /**
     * Create CCC AgentGroup
     *
     * @param request request with parameters to create agent group
     * @return id of newly created agent group
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Long createAgentGroup(CreateAgentGroupRequest request) {
        return client.post(CCC_AGENT_GROUP_PATH, of(ResourceReference.class), request).getId();
    }

    /**
     * Update existing CCC Agent Group
     *
     * @param request request with parameters to update agent group (name etc)
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void updateAgentGroup(UpdateAgentGroupRequest request) {
        Validate.notNull(request.getId(), "id cannot be null");
        String path = CCC_AGENT_GROUP_ITEM_PATH.replaceFirst(PLACEHOLDER, request.getId().toString());
        client.put(path, of(Object.class), request);
    }

    /**
     * Get Agent Group by id
     *
     * @param id Agent Group id
     * @return {@link AgentGroup} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public AgentGroup getAgentGroup(long id) {
        return client.get(CCC_AGENT_GROUP_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)), resourceOf(AgentGroup.class)).get();
    }

    /**
     * Remove Agent Group by id
     *
     * @param id Agent Group id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void removeAgentGroup(long id) {
        client.delete(CCC_AGENT_GROUP_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }

    /**
     * Get all existing Agents
     *
     * @param request request with parameters like CampaignId, Name etc
     * @return {@link List} of {@link AgentGroup} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<AgentGroup> queryAgentGroups(QueryAgentGroupsRequest request) {
        return client.query(CCC_AGENT_GROUP_PATH, listOf(AgentGroup.class), request).get();
    }

    /**
     * Add Agent Groups to Campaign
     *
     * @param request add agents groups request with broadcast id and agent groups data
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void addCampaignAgentGroups(AddAgentGroupsRequest request) {
        String path = BROADCASTS_ITEM_AGENT_GROUP_PATH.replaceFirst(PLACEHOLDER, request.getCampaignId().toString());
        client.post(path, of(Object.class), request);
    }

    /**
     * Get Agent Groups attached with Campaign
     *
     * @param broadcastId CCC Broadcast id
     * @return {@link List} of {@link AgentGroup} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<AgentGroup> queryCampaignAgentGroups(long broadcastId) {
        return client.get(BROADCASTS_ITEM_AGENT_GROUP_PATH.replaceFirst(PLACEHOLDER, String.valueOf(broadcastId)), listOf(AgentGroup.class)).get();
    }

    /**
     * DELETE Agent Group from Ccc Campaign
     *
     * @param broadcastId CCC Broadcast id
     * @param id CCC Agent Group id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void removeAgentGroupFromCampaign(long broadcastId, long id) {
        client.delete(BROADCASTS_ITEM_AGENT_GROUP_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(broadcastId)).replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }

    /**
     * Get Agent Session by id
     *
     * @param id Agent Session id
     * @return {@link AgentSession} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public AgentSession getAgentSession(long id) {
        return client.get(CCC_AGENT_SESSION_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)), resourceOf(AgentSession.class)).get();
    }

    /**
     * Get Existing Agent Sessions
     *
     * @param request Request with filter parameters
     * @return {@link List} of {@link AgentGroup} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<AgentSession> queryAgentSessions(QueryAgentSessionsRequest request) {
        return client.query(CCC_AGENT_SESSION_PATH, listOf(AgentSession.class), request).get();
    }

    /**
     * Send ccc agent invite
     *
     * @param request request with data for sending agent invite
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void sendCampaignAgentInvite(SendAgentInviteRequest request) {
        String path = BROADCASTS_ITEM_AGENT_INVITE_PATH.replaceFirst(PLACEHOLDER, request.getCampaignId().toString());
        client.post(path, of(Object.class), request);
    }

    /**
     * Get CCC AgentInvite URI
     *
     * @param campaignId CCC Campaign to invite agents to
     * @param agentEmail Agent email to send invite to
     * @return {@link String} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public AgentInvite getCampaignAgentInviteUri(long campaignId, String agentEmail) {
        return client.get(BROADCASTS_ITEM_AGENT_INVITE_URI_PATH.replaceFirst(PLACEHOLDER, String.valueOf(campaignId)), resourceOf(AgentInvite.class), asParams("AgentEmail", agentEmail)).get();
    }

}
