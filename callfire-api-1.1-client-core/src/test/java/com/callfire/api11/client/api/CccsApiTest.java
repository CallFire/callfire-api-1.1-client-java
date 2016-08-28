package com.callfire.api11.client.api;

import com.callfire.api11.client.api.broadcasts.model.BroadcastCommand;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStats;
import com.callfire.api11.client.api.broadcasts.model.request.ControlBroadcastRequest;
import com.callfire.api11.client.api.ccc.model.Agent;
import com.callfire.api11.client.api.ccc.model.AgentGroup;
import com.callfire.api11.client.api.ccc.model.AgentInvite;
import com.callfire.api11.client.api.ccc.model.AgentSession;
import com.callfire.api11.client.api.ccc.model.CccBroadcast;
import com.callfire.api11.client.api.ccc.model.Question;
import com.callfire.api11.client.api.ccc.model.QuestionResponseType;
import com.callfire.api11.client.api.ccc.model.TransferNumber;
import com.callfire.api11.client.api.ccc.model.request.AddAgentGroupsRequest;
import com.callfire.api11.client.api.ccc.model.request.AddAgentsRequest;
import com.callfire.api11.client.api.ccc.model.request.QueryAgentGroupsRequest;
import com.callfire.api11.client.api.ccc.model.request.QueryAgentSessionsRequest;
import com.callfire.api11.client.api.ccc.model.request.QueryAgentsRequest;
import com.callfire.api11.client.api.ccc.model.request.QueryCccBroadcastsRequest;
import com.callfire.api11.client.api.ccc.model.request.SendAgentInviteRequest;
import com.callfire.api11.client.api.common.model.Resource;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.Result;
import com.callfire.api11.client.api.common.model.RetryConfig;
import com.callfire.api11.client.api.common.model.RetryPhoneType;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.test.CallfireTestUtils.extractHttpEntity;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.time.DateUtils.setHours;
import static org.apache.commons.lang3.time.DateUtils.setMinutes;
import static org.apache.commons.lang3.time.DateUtils.setSeconds;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CccsApiTest extends AbstractApiTest {
    private static Calendar startDate = Calendar.getInstance();
    private static Calendar endDate = Calendar.getInstance();
    private static Date localBeginTime = setSeconds(setMinutes(setHours(new Date(), 10), 10), 10);
    private static Date localEndTime = setSeconds(setMinutes(setHours(new Date(), 11), 20), 20);

    static {
        startDate.set(2000, Calendar.JANUARY, 10, 10, 10, 10);
        endDate.set(2015, Calendar.DECEMBER, 15, 15, 15, 15);
    }

    @Test
    public void createCccBroadcast() throws Exception {

        String location = "https://www.callfire.com/api/1.1/rest/ccc/1528630003";
        String expectedJson = getJsonPayload("/cccBroadcasts/create.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

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
        t.setName("Transfer");
        t.setAllowAssistedTransfer(true);
        broadcast.setTransferNumbers(Arrays.asList(t));

        broadcast.setAgentGroupId(149740003L);
        broadcast.setAgentGroupName("AgentGroupName");
        broadcast.setAllowAnyTransfer(true);
        broadcast.setRecorded(true);
        broadcast.setMultilineDialingRatio(2);
        broadcast.setMultilineDialingEnabled(true);
        broadcast.setRetryConfig(new RetryConfig(2, 1, asList(Result.BUSY, Result.NO_ANS), asList(RetryPhoneType.MOBILE_PHONE, RetryPhoneType.HOME_PHONE)));

        broadcast.setTransferCallerId("12132212384");
        broadcast.setBeginTime(localBeginTime);
        broadcast.setEndTime(localEndTime);

        broadcast.setSmartDropSoundId(149740003L);
        broadcast.setSmartDropSoundRef("SmartDropRef");

        Long id = client.cccsApi().create(broadcast);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Name=" + encode("Test CCC Broadcast")));
        assertThat(requestBody, containsString("FromNumber=12132212384"));
        assertThat(requestBody, containsString(encode("Question[0][label]") + "=" + encode("testQuestion")));
        assertThat(requestBody, containsString(encode("Question[0][responseType]") + "=" + encode("STRING")));

        assertThat(requestBody, containsString(encode("TransferNumber[0][name]") + "=" + encode("Transfer")));
        assertThat(requestBody, containsString(encode("TransferNumber[0][number]") + "=" + encode("12132212384")));

        assertThat(requestBody, containsString("AgentGroupId=149740003"));
        assertThat(requestBody, containsString("AgentGroupName=AgentGroupName"));
        assertThat(requestBody, containsString("SmartDropSoundId=149740003"));
        assertThat(requestBody, containsString("SmartDropSoundRef=SmartDropRef"));
        assertThat(requestBody, containsString("AllowAnyTransfer=true"));
        assertThat(requestBody, containsString("Recorded=true"));
        assertThat(requestBody, containsString("MultilineDialingRatio=2"));
        assertThat(requestBody, containsString("MultilineDialingEnabled=true"));
        assertThat(requestBody, containsString("RetryResults=BUSY"));
        assertThat(requestBody, containsString("RetryResults=NO_ANS"));
        assertThat(requestBody, containsString("RetryPhoneTypes=MOBILE_PHONE"));
        assertThat(requestBody, containsString("RetryPhoneTypes=HOME_PHONE"));
        assertThat(requestBody, containsString("TransferCallerId=12132212384"));
        assertThat(requestBody, containsString("LocalRestrictBegin=" + encode("10:10:10")));
        assertThat(requestBody, containsString("LocalRestrictEnd=" + encode("11:20:20")));
    }

    @Test
    public void queryCccBroadcasts() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/query.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryCccBroadcastsRequest request = QueryCccBroadcastsRequest.create()
            .name("TestCCCBroadcast")
            .running(true)
            .labelName("TestLabel")
            .maxResults(1)
            .firstResult(0)
            .build();
        List<CccBroadcast> broadcasts = client.cccsApi().query(request);
        ResourceList<CccBroadcast> response = new ResourceList<>(broadcasts, CccBroadcast.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=0"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=1"));
        assertThat(arg.getURI().toString(), containsString("Name=TestCCCBroadcast"));
        assertThat(arg.getURI().toString(), containsString("Running=true"));
        assertThat(arg.getURI().toString(), containsString("LabelName=TestLabel"));
    }

    @Test
    public void getCccBroadcast() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        CccBroadcast broadcast = client.cccsApi().get(1234567L);
        Resource<CccBroadcast> response = new Resource<>(broadcast, CccBroadcast.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/ccc/1234567.json"));
    }

    @Test
    public void getStats() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/getStats.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        BroadcastStats stats = client.cccsApi().getStats(1234567L, startDate.getTime(), endDate.getTime());
        Resource<BroadcastStats> response = new Resource<>(stats, BroadcastStats.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/ccc/1234567/stats.json"));
        assertThat(arg.getURI().toString(), containsString("IntervalBegin=" + encode("2000-01-10T10:10:10")));
        assertThat(arg.getURI().toString(), containsString("IntervalEnd=" + encode("2015-12-15T15:15:15")));
    }

    @Test
    public void updateCccBroadcast() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        CccBroadcast broadcast = new CccBroadcast();
        broadcast.setName("Test CCC Broadcast Updated");
        broadcast.setFromNumber("12132212384");

        Question q = new Question();
        q.setLabel("testQuestion");
        q.setResponseType(QuestionResponseType.STRING);
        q.setChoices(Arrays.asList("TEST1", "TEST2"));
        broadcast.setQuestions(Arrays.asList(q));

        TransferNumber t = new TransferNumber();
        t.setNumber("12132212384");
        t.setName("Transfer");
        t.setAllowAssistedTransfer(true);
        broadcast.setTransferNumbers(Arrays.asList(t));

        broadcast.setAgentGroupId(149740003L);
        broadcast.setAgentGroupName("AgentGroupName");
        broadcast.setAllowAnyTransfer(true);
        broadcast.setRecorded(true);
        broadcast.setMultilineDialingRatio(2);
        broadcast.setMultilineDialingEnabled(true);
        broadcast.setRetryConfig(new RetryConfig(2, 1, asList(Result.BUSY, Result.NO_ANS), asList(RetryPhoneType.MOBILE_PHONE, RetryPhoneType.HOME_PHONE)));

        broadcast.setTransferCallerId("12132212384");
        broadcast.setBeginTime(localBeginTime);
        broadcast.setEndTime(localEndTime);

        broadcast.setSmartDropSoundId(149740003L);
        broadcast.setSmartDropSoundRef("SmartDropRef");
        broadcast.setId(123L);

        client.cccsApi().update(broadcast);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/123.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString("Name=" + encode("Test CCC Broadcast Updated")));
        assertThat(requestBody, containsString("FromNumber=12132212384"));
        assertThat(requestBody, containsString(encode("Question[0][label]") + "=" + encode("testQuestion")));
        assertThat(requestBody, containsString(encode("Question[0][responseType]") + "=" + encode("STRING")));

        assertThat(requestBody, containsString(encode("TransferNumber[0][name]") + "=" + encode("Transfer")));
        assertThat(requestBody, containsString(encode("TransferNumber[0][number]") + "=" + encode("12132212384")));

        assertThat(requestBody, containsString("AgentGroupId=149740003"));
        assertThat(requestBody, containsString("AgentGroupName=AgentGroupName"));
        assertThat(requestBody, containsString("SmartDropSoundId=149740003"));
        assertThat(requestBody, containsString("SmartDropSoundRef=SmartDropRef"));
        assertThat(requestBody, containsString("AllowAnyTransfer=true"));
        assertThat(requestBody, containsString("Recorded=true"));
        assertThat(requestBody, containsString("MultilineDialingRatio=2"));
        assertThat(requestBody, containsString("MultilineDialingEnabled=true"));
        assertThat(requestBody, containsString("RetryResults=BUSY"));
        assertThat(requestBody, containsString("RetryResults=NO_ANS"));
        assertThat(requestBody, containsString("RetryPhoneTypes=MOBILE_PHONE"));
        assertThat(requestBody, containsString("RetryPhoneTypes=HOME_PHONE"));
        assertThat(requestBody, containsString("TransferCallerId=12132212384"));
        assertThat(requestBody, containsString("LocalRestrictBegin=" + encode("10:10:10")));
        assertThat(requestBody, containsString("LocalRestrictEnd=" + encode("11:20:20")));
    }

    @Test
    public void deleteTransferNumbers() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.cccsApi().deleteTransferNumbers(123L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/123/transfer-numbers.json"));

        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
    }

    @Test
    public void deleteQuestions() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.cccsApi().deleteQuestions(123L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/123/questions.json"));

        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
    }

    @Test
    public void control() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        ControlBroadcastRequest request = ControlBroadcastRequest.create()
                .command(BroadcastCommand.START)
                .id(1000L)
                .maxActive(200)
                .build();
        client.cccsApi().control(request);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/1000/control.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString("Command=START"));
        assertThat(requestBody, containsString("MaxActive=200"));
    }

    @Test
    public void deleteCccBroadcast() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.cccsApi().delete(123L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/123.json"));

        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
    }

    @Test
    public void addCampaignAgents() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        AddAgentsRequest request = AddAgentsRequest.create()
            .broadcastId(386074003L)
            .agentIds(Arrays.asList(289020003L, 289020004L))
            .build();
        client.cccsApi().addCampaignAgents(request);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/386074003/agent.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("AgentIds=289020003"));
        assertThat(requestBody, containsString("AgentIds=289020004"));
    }

    @Test
    public void queryCampaignAgents() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/queryAgents.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        List<Agent> agents = client.cccsApi().queryCampaignAgents(13658242003L);
        ResourceList<Agent> response = new ResourceList<>(agents, Agent.class);

        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        assertTrue(response.getTotalResults() == 1);
        assertTrue(response.get().size() == 1);
        assertEquals(response.get().get(0).getEmail(), "name@callfire.com");
        assertEquals(response.get().get(0).getName(), "Name");
        assertEquals(response.get().get(0).isEnabled(), true);
        assertEquals(response.get().get(0).getEmail(), "name@callfire.com");
        assertEquals(response.get().get(0).getLastLogin().toString(), "Tue Jun 23 17:19:59 EEST 2015");
        assertTrue(response.get().get(0).getCampaignIds().get(0).equals(13658242003L));
        assertTrue(response.get().get(0).getGroupIds().get(0).equals(21343421L));
        assertTrue(response.get().get(0).getId().equals(386074003L));

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/ccc/13658242003/agent.json"));
    }

    @Test
    public void queryAgents() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/queryAgents.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryAgentsRequest request = QueryAgentsRequest.create()
            .agentEmail("name@callfire.com")
            .agentGroupId(21343421L)
            .campaignId(13658242003L)
            .enabled(true)
            .build();
        List<Agent> agents = client.cccsApi().queryAgents(request);
        ResourceList<Agent> response = new ResourceList<>(agents, Agent.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        assertTrue(response.getTotalResults() == 1);
        assertTrue(response.get().size() == 1);
        assertEquals(response.get().get(0).getEmail(), "name@callfire.com");
        assertEquals(response.get().get(0).getName(), "Name");
        assertEquals(response.get().get(0).isEnabled(), true);
        assertEquals(response.get().get(0).getEmail(), "name@callfire.com");
        assertEquals(response.get().get(0).getLastLogin().toString(), "Tue Jun 23 17:19:59 EEST 2015");
        assertTrue(response.get().get(0).getCampaignIds().get(0).equals(13658242003L));
        assertTrue(response.get().get(0).getGroupIds().get(0).equals(21343421L));
        assertTrue(response.get().get(0).getId().equals(386074003L));

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=0"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=1000"));
        assertThat(arg.getURI().toString(), containsString("CampaignId=13658242003"));
        assertThat(arg.getURI().toString(), containsString("AgentEmail=" + encode("name@callfire.com")));
        assertThat(arg.getURI().toString(), containsString("Enabled=true"));
        assertThat(arg.getURI().toString(), containsString("AgentGroupId=21343421"));
    }

    @Test
    public void getAgent() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/getAgent.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Agent agent = client.cccsApi().getAgent(386074003L);
        Resource<Agent> response = new Resource<>(agent, Agent.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        assertEquals(response.get().getEmail(), "name@callfire.com");
        assertEquals(response.get().getName(), "Name");
        assertEquals(response.get().isEnabled(), true);
        assertEquals(response.get().getEmail(), "name@callfire.com");
        assertEquals(response.get().getLastLogin().toString(), "Tue Jun 23 17:19:59 EEST 2015");
        assertTrue(response.get().getCampaignIds().get(0).equals(13658242003L));
        assertTrue(response.get().getGroupIds().get(0).equals(21343421L));
        assertTrue(response.get().getId().equals(386074003L));

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/ccc/agent/386074003.json"));
    }

    @Test
    public void removeAgentFromCampaign() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.cccsApi().removeAgentFromCampaign(123L, 1234L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/123/agent/1234.json"));

        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
    }

    @Test
    public void createAgentGroup() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/ccc/agent-group/1528630003";
        String expectedJson = getJsonPayload("/cccBroadcasts/createAgentGroup.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        AgentGroup group = new AgentGroup();
        group.setAgentIds(Arrays.asList(289020003L, 386074003L));
        group.setName("test agent group");

        Long id = client.cccsApi().createAgentGroup(group);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Name=" + encode("test agent group")));
        assertThat(requestBody, containsString("AgentIds=289020003"));
        assertThat(requestBody, containsString("AgentIds=386074003"));
    }

    @Test
    public void updateAgentGroup() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        AgentGroup group = new AgentGroup();
        group.setAgentIds(Arrays.asList(289020003L, 386074003L));
        group.setName("test agent group updated");
        group.setId(289020003L);
        group.setCampaignIds(Arrays.asList(386074003L));

        client.cccsApi().updateAgentGroup(group);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/agent-group/289020003.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Name=" + encode("test agent group updated")));
        assertThat(requestBody, containsString("AgentIds=289020003"));
        assertThat(requestBody, containsString("AgentIds=386074003"));
        assertThat(requestBody, containsString("CampaignIds=386074003"));
    }

    @Test
    public void removeAgentGroup() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.cccsApi().removeAgentGroup(123L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/agent-group/123.json"));

        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
    }

    @Test
    public void queryAgentGroups() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/queryAgentGroups.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryAgentGroupsRequest queryRequest = QueryAgentGroupsRequest.create()
            .agentEmail("agent1@callfire.com")
            .campaignId(13692574003L)
            .agentId(289020003L)
            .name("Name")
            .maxResults(1)
            .firstResult(0)
            .build();
        List<AgentGroup> groups = client.cccsApi().queryAgentGroups(queryRequest);
        ResourceList<AgentGroup> response = new ResourceList<>(groups, AgentGroup.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        assertTrue(response.getTotalResults() == 1);
        assertTrue(response.get().size() == 1);
        assertEquals(response.get().get(0).getName(), "Name");
        assertEquals(response.get().get(0).getAgentEmails().get(0), "agent1@callfire.com");
        assertTrue(response.get().get(0).getCampaignIds().get(0).equals(13692574003L));
        assertTrue(response.get().get(0).getAgentIds().get(0).equals(289020003L));
        assertTrue(response.get().get(0).getId().equals(268008003L));

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=0"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=1"));
        assertThat(arg.getURI().toString(), containsString("CampaignId=13692574003"));
        assertThat(arg.getURI().toString(), containsString("AgentEmail=" + encode("agent1@callfire.com")));
        assertThat(arg.getURI().toString(), containsString("AgentId=289020003"));
    }

    @Test
    public void getAgentGroup() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/getAgentGroup.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        AgentGroup agentGroup = client.cccsApi().getAgentGroup(268008003L);
        Resource<AgentGroup> response = new Resource<>(agentGroup, AgentGroup.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        assertEquals(response.get().getName(), "Name");
        assertEquals(response.get().getAgentEmails().get(0), "agent1@callfire.com");
        assertTrue(response.get().getCampaignIds().get(0).equals(13692574003L));
        assertTrue(response.get().getAgentIds().get(0).equals(289020003L));
        assertTrue(response.get().getId().equals(268008003L));

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/ccc/agent-group/268008003.json"));
    }

    @Test
    public void addCampaignAgentGroups() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        AddAgentGroupsRequest addRequest = AddAgentGroupsRequest.create()
            .agentGroupIds(Arrays.asList(289020003L, 289020004L))
            .campaignId(386074003L)
            .build();

        client.cccsApi().addCampaignAgentGroups(addRequest);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/386074003/agent-group.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("AgentGroupIds=289020003"));
        assertThat(requestBody, containsString("AgentGroupIds=289020004"));
    }

    @Test
    public void queryCampaignAgentGroups() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/queryAgentGroups.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        List<AgentGroup> agentGroups = client.cccsApi().queryCampaignAgentGroups(123L);
        ResourceList<AgentGroup> response = new ResourceList<>(agentGroups, AgentGroup.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        assertTrue(response.getTotalResults() == 1);
        assertTrue(response.get().size() == 1);
        assertEquals(response.get().get(0).getName(), "Name");
        assertEquals(response.get().get(0).getAgentEmails().get(0), "agent1@callfire.com");
        assertTrue(response.get().get(0).getCampaignIds().get(0).equals(13692574003L));
        assertTrue(response.get().get(0).getAgentIds().get(0).equals(289020003L));
        assertTrue(response.get().get(0).getId().equals(268008003L));

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/ccc/123/agent-group.json"));
    }

    @Test
    public void removeAgentGroupFromCampaign() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.cccsApi().removeAgentGroupFromCampaign(123L, 1234L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/123/agent-group/1234.json"));

        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
    }

    @Test
    public void queryAgentSessions() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/queryAgentSessions.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryAgentSessionsRequest request = QueryAgentSessionsRequest.create()
            .agentEmail("name@callfire.com")
            .campaignId(9901983003L)
            .firstResult(0)
            .maxResults(1)
            .agentId(289020003L)
            .active(false)
            .build();

        List<AgentSession> sessions = client.cccsApi().queryAgentSessions(request);
        ResourceList<AgentSession> response = new ResourceList<>(sessions, AgentSession.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);


        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=0"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=1"));
        assertThat(arg.getURI().toString(), containsString("CampaignId=9901983003"));
        assertThat(arg.getURI().toString(), containsString("AgentEmail=" + encode("name@callfire.com")));
        assertThat(arg.getURI().toString(), containsString("AgentId=289020003"));
        assertThat(arg.getURI().toString(), containsString("Active=false"));
    }

    @Test
    public void getAgentSession() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/getAgentSession.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        AgentSession agentSession = client.cccsApi().getAgentSession(268008003L);
        Resource<AgentSession> response = new Resource<>(agentSession, AgentSession.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/ccc/agent-session/268008003.json"));
    }

    @Test
    public void sendCampaignAgentInvite() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        SendAgentInviteRequest request = SendAgentInviteRequest.create()
            .campaignId(9901983003L)
            .agentGroupName("test agent group for invite")
            .agentEmails(Arrays.asList("name@callfire.com"))
            .build();

        client.cccsApi().sendCampaignAgentInvite(request);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/ccc/9901983003/agent-invite.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("AgentGroupName=" + encode("test agent group for invite")));
        assertThat(requestBody, containsString("AgentEmails=" + encode("name@callfire.com")));
    }

    @Test
    public void getCampaignAgentInviteUri() throws Exception {
        String expectedJson = getJsonPayload("/cccBroadcasts/getAgentInvite.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        AgentInvite agentInvite = client.cccsApi().getCampaignAgentInviteUri(268008003L, "name@callfire.com");
        Resource<AgentInvite> response = new Resource<>(agentInvite, AgentInvite.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/ccc/268008003/agent-invite-uri.json"));
        assertThat(arg.getURI().toString(), containsString("AgentEmail=" + encode("name@callfire.com")));
    }


}
