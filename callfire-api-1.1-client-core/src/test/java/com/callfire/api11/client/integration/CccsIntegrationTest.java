package com.callfire.api11.client.integration;

import com.callfire.api11.client.api.broadcasts.model.BroadcastCommand;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStats;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStatus;
import com.callfire.api11.client.api.broadcasts.model.request.ControlBroadcastRequest;
import com.callfire.api11.client.api.ccc.model.*;
import com.callfire.api11.client.api.ccc.model.request.*;
import com.callfire.api11.client.api.common.model.Result;
import com.callfire.api11.client.api.common.model.RetryConfig;
import com.callfire.api11.client.api.common.model.RetryPhoneType;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.TIME_FORMAT_PATTERN;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@Ignore
public class CccsIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testCccBroadcastCrudOperations() throws Exception {
        CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast");
        broadcast.setFromNumber("12132212384");

        Question q = new Question();
        q.setLabel("testQuestion");
        q.setResponseType(QuestionResponseType.STRING);
        q.setChoices(Arrays.asList("TEST1", "TEST2"));
        broadcast.setQuestions(Arrays.asList(q));

        TransferNumber t = new TransferNumber();
        t.setNumber("12132212384");
        t.setName("Transfer name");
        t.setAllowAssistedTransfer(true);
        broadcast.setTransferNumbers(Arrays.asList(t));

        broadcast.setScript("test script");
        broadcast.setScrubLevel(2);

        broadcast.setBeginTime(DateUtils.parseDate("10:10:10", TIME_FORMAT_PATTERN));
        broadcast.setEndTime(DateUtils.parseDate("15:15:15", TIME_FORMAT_PATTERN));

        broadcast.setSmartDropSoundId(1L);
        broadcast.setAgentGroupId(149740003L);
        broadcast.setAllowAnyTransfer(true);
        broadcast.setRecorded(true);
        broadcast.setMultilineDialingRatio(2);
        broadcast.setMultilineDialingEnabled(true);
        broadcast.setRetryConfig(new RetryConfig(2, 1, asList(Result.BUSY, Result.NO_ANS), asList(RetryPhoneType.MOBILE_PHONE, RetryPhoneType.HOME_PHONE)));

        broadcast.setTransferCallerId("12132212384");

        Long createdBroadcastId = client.cccsApi().create(broadcast);
        assertNotNull(createdBroadcastId);
        System.out.println(createdBroadcastId);

        CccBroadcast savedBroadcast = client.cccsApi().get(createdBroadcastId);
        assertEquals("Test CCC Broadcast", savedBroadcast.getName());
        assertEquals(broadcast.getFromNumber(), "12132212384");
        assertTrue(broadcast.getQuestions().size() == 1);
        assertTrue(broadcast.getTransferNumbers().size() == 1);
        broadcast.setId(createdBroadcastId);
        broadcast.setName(broadcast.getName() + " updated");
        broadcast.setRetryConfig(null);
        client.cccsApi().update(broadcast);

        savedBroadcast = client.cccsApi().get(createdBroadcastId);
        assertEquals("Test CCC Broadcast updated", savedBroadcast.getName());
        // TODO uncomment once fixed
        //assertNull(savedBroadcast.getRetryConfig());

        client.cccsApi().delete(createdBroadcastId);
    }

    @Test
    public void testQueryCccBroadcasts() throws Exception {
        CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast");
        broadcast.setFromNumber("12132212384");
        Question q = new Question();
        q.setLabel("testQuestion");
        q.setResponseType(QuestionResponseType.STRING);
        broadcast.setQuestions(Arrays.asList(q));
        broadcast.setAgentGroupId(149740003L);
        broadcast.setAllowAnyTransfer(true);
        broadcast.setRecorded(true);
        broadcast.setMultilineDialingRatio(2);
        broadcast.setMultilineDialingEnabled(true);

        Long createdBroadcastId = client.cccsApi().create(broadcast);

        QueryCccBroadcastsRequest request = QueryCccBroadcastsRequest.create()
            .name("Test CCC Broadcast")
            .running(false)
            .maxResults(1)
            .firstResult(0)
            .build();
        List<CccBroadcast> broadcasts = client.cccsApi().query(request);
        assertEquals(broadcasts.size(), 1);
        assertEquals(broadcasts.get(0).getName(), "Test CCC Broadcast");
        assertNotNull(broadcasts.get(0).getId());

        client.cccsApi().delete(createdBroadcastId);
    }

    @Test
    public void testControlAndGetStats() throws Exception {
        CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast");
        broadcast.setFromNumber("12132212384");
        Question q = new Question();
        q.setLabel("testQuestion");
        q.setResponseType(QuestionResponseType.STRING);
        broadcast.setQuestions(Arrays.asList(q));
        broadcast.setAgentGroupId(149740003L);
        broadcast.setAllowAnyTransfer(true);
        broadcast.setRecorded(true);
        broadcast.setMultilineDialingRatio(2);
        broadcast.setMultilineDialingEnabled(true);

        Long createdBroadcastId = client.cccsApi().create(broadcast);

        ControlBroadcastRequest archiveRequest = ControlBroadcastRequest.create()
            .command(BroadcastCommand.ARCHIVE)
            .id(createdBroadcastId)
            .build();
        client.cccsApi().control(archiveRequest);

        CccBroadcast savedBroadcast = client.cccsApi().get(createdBroadcastId);
        assertEquals(BroadcastStatus.ARCHIVED, savedBroadcast.getStatus());

        BroadcastStats stats = client.cccsApi().getStats(createdBroadcastId);
        assertNotNull(stats);

        client.cccsApi().delete(createdBroadcastId);
    }

    @Test
    public void testDeleteQuestionsAndTransferNumbers() throws Exception {
        CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast");
        broadcast.setFromNumber("12132212384");
        Question q = new Question();
        q.setLabel("testQuestion");
        q.setResponseType(QuestionResponseType.STRING);
        broadcast.setQuestions(Arrays.asList(q));
        TransferNumber tn = new TransferNumber();
        tn.setName("testNumber");
        tn.setNumber("12132212384");
        broadcast.setTransferNumbers(Arrays.asList(tn));

        Long createdBroadcastId = client.cccsApi().create(broadcast);

        client.cccsApi().deleteQuestions(createdBroadcastId);
        client.cccsApi().deleteTransferNumbers(createdBroadcastId);

        CccBroadcast savedBroadcast = client.cccsApi().get(createdBroadcastId);
        assertNull(savedBroadcast.getQuestions());
        assertNull(savedBroadcast.getTransferNumbers());

        client.cccsApi().delete(createdBroadcastId);
    }

    @Test
    public void testAddRemoveAndGetCampaignAgents() throws Exception {
        CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast");
        broadcast.setFromNumber("12132212384");

        Long createdBroadcastId = client.cccsApi().create(broadcast);

        AddAgentsRequest request = AddAgentsRequest.create()
            .broadcastId(createdBroadcastId)
            .agentIds(Arrays.asList(289020003L))
            .build();
        client.cccsApi().addCampaignAgents(request);

        AddAgentsRequest request2 = AddAgentsRequest.create()
            .broadcastId(createdBroadcastId)
            .agentEmails(Arrays.asList("vmalinovskiy+agent@callfire.com"))
            .build();
        client.cccsApi().addCampaignAgents(request2);

        List<Agent> agents = client.cccsApi().queryCampaignAgents(createdBroadcastId);
        assertEquals(1, agents.size());

        client.cccsApi().removeAgentFromCampaign(createdBroadcastId, agents.get(0).getId());

        agents = client.cccsApi().queryCampaignAgents(createdBroadcastId);
        assertEquals(0, agents.size());

        client.cccsApi().delete(createdBroadcastId);
    }

    @Test
    public void testGetAgents() throws Exception {
        QueryAgentsRequest request = QueryAgentsRequest.create()
            .agentEmail("vmalinovskiy+agent@callfire.com")
            .agentGroupId(149688003L)
            .campaignId(9901121003L)
            .build();

        List<Agent> agents = client.cccsApi().queryAgents(request);
        assertTrue(agents.size() > 0);

        Agent agent = client.cccsApi().getAgent(agents.get(0).getId());
        assertNotNull(agent);
        assertEquals(agent.getEmail(), "vmalinovskiy+agent@callfire.com");
    }

    @Test
    public void testAgentGroupsCRUDOperations() throws Exception {
        /*CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast");
        broadcast.setFromNumber("12132212384");

        Long createdBroadcastId = client.cccsApi().create(broadcast);*/

        CreateAgentGroupRequest createRequest = CreateAgentGroupRequest.create()
            .agentIds(Arrays.asList(289020003L, 386074003L))
            .name("test agent group")
            .build();

        Long createdAgentGroupId = client.cccsApi().createAgentGroup(createRequest);

        AgentGroup group = client.cccsApi().getAgentGroup(createdAgentGroupId);
        assertNotNull(group);
        assertEquals(group.getName(), "test agent group");

        UpdateAgentGroupRequest updateRequest = UpdateAgentGroupRequest.create()
            .id(group.getId())
            //    .campaignIds(Arrays.asList(createdBroadcastId))
            .name(group.getName() + " updated")
            .build();

        client.cccsApi().updateAgentGroup(updateRequest);

        group = client.cccsApi().getAgentGroup(createdAgentGroupId);
        assertEquals(group.getName(), "test agent group updated");
        // TODO uncomment once fixed
        //assertEquals(group.getCampaignIds().size(), 1);
        assertEquals(group.getAgentEmails().size(), 2);

        QueryAgentGroupsRequest queryRequest = QueryAgentGroupsRequest.create()
            .agentEmail("vmalinovskiy+agent@callfire.com")
            .build();
        List<AgentGroup> groups = client.cccsApi().queryAgentGroups(queryRequest);
        assertTrue(groups.size() > 0);

        client.cccsApi().removeAgentGroup(createdAgentGroupId);
        //client.cccsApi().delete(createdBroadcastId);
    }

    @Test
    public void testCampaignAgentGroups() throws Exception {
        CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast");
        broadcast.setFromNumber("12132212384");

        Long createdBroadcastId = client.cccsApi().create(broadcast);

        CreateAgentGroupRequest createRequest = CreateAgentGroupRequest.create()
            .agentIds(Arrays.asList(289020003L))
            .name("test agent group")
            .build();

        Long createdAgentGroupId = client.cccsApi().createAgentGroup(createRequest);

        AddAgentGroupsRequest addRequest = AddAgentGroupsRequest.create()
           .agentGroupIds(Arrays.asList(createdAgentGroupId))
           .campaignId(createdBroadcastId)
           .build();

        client.cccsApi().addCampaignAgentGroups(addRequest);

        List<AgentGroup> agentGroups = client.cccsApi().queryCampaignAgentGroups(createdBroadcastId);
        assertEquals(agentGroups.size(), 1);

        client.cccsApi().removeAgentGroupFromCampaign(createdBroadcastId, createdAgentGroupId);

        agentGroups = client.cccsApi().queryCampaignAgentGroups(createdBroadcastId);
        assertEquals(agentGroups.size(), 0);

        client.cccsApi().removeAgentGroup(createdAgentGroupId);
        client.cccsApi().delete(createdBroadcastId);
    }

    @Test
    public void testAgentSessions() throws Exception {
        QueryAgentSessionsRequest request = QueryAgentSessionsRequest.create()
           .agentEmail("vmalinovskiy+agent@callfire.com")
           .campaignId(9901983003L)
           .build();

        List<AgentSession> sessions = client.cccsApi().queryAgentSessions(request);
        assertTrue(sessions.size() > 0);

        AgentSession session = client.cccsApi().getAgentSession(sessions.get(0).getId());
        assertNotNull(session);
        assertTrue(session.getCampaignId().equals(9901983003L));
    }

    @Test
    public void testCampaignAgentInvites() throws Exception {
        CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast");
        broadcast.setFromNumber("12132212384");

        Long createdBroadcastId = client.cccsApi().create(broadcast);

        CreateAgentGroupRequest createGroupRequest = CreateAgentGroupRequest.create()
            .agentIds(Arrays.asList(289020003L))
            .name("test agent group for invite")
            .build();

        Long createdAgentGroupId = client.cccsApi().createAgentGroup(createGroupRequest);

        SendAgentInviteRequest request = SendAgentInviteRequest.create()
           .campaignId(createdBroadcastId)
           .agentGroupName("test agent group for invite")
           .agentEmails(Arrays.asList("vmalinovskiy+agent@callfire.com"))
           .build();

        client.cccsApi().sendCampaignAgentInvite(request);

        AgentInvite agentInvite = client.cccsApi().getCampaignAgentInviteUri(createdBroadcastId, "vmalinovskiy+agent@callfire.com");
        assertNotNull(agentInvite);
        assertEquals(agentInvite.getCampaignId(), createdBroadcastId);

        client.cccsApi().removeAgentGroup(createdAgentGroupId);
        client.cccsApi().delete(createdBroadcastId);
    }
}
