package com.callfire.api11.client;

import com.callfire.api11.client.api.calls.model.Call;
import com.callfire.api11.client.api.calls.model.Voice;
import com.callfire.api11.client.api.calls.model.VoiceBroadcastConfig;
import com.callfire.api11.client.api.calls.model.request.QueryCallsRequest;
import com.callfire.api11.client.api.calls.model.request.SendCallRequest;
import com.callfire.api11.client.api.common.model.ActionState;
import com.callfire.api11.client.api.common.model.ToNumber;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test calls api with mocked client
 */
public class MockCallsTest {
    @Rule
    public ExpectedException ex = ExpectedException.none();
    private CfApi11Client client = MockClientFactory.newClient();

    @Before
    public void setUp() throws Exception {
        client = MockClientFactory.newClient();
    }

    @Test
    public void queryCalls() throws Exception {
        List<Call> calls = client.callsApi().query(QueryCallsRequest.create().build());

        for (Call call : calls) {
            System.out.println(call);
        }
    }

    @Test
    public void getCall() throws Exception {
        Call call = client.callsApi().get(1L);
        System.out.println(call);
    }

    @Test
    public void sendCallFromInvalidNumber() throws Exception {
        SendCallRequest request = SendCallRequest.create()
            .name("Send Call Java API Client")
            .recipients(Collections.singletonList(new ToNumber("12132212384")))
            .voiceConfig(VoiceBroadcastConfig.create()
                .fromNumber("1234")
                .liveSound("this is LA TTS", Voice.MALE1)
                .amSound("this is AM TTS", Voice.FEMALE1)
            )
            .build();

        ex.expect(BadRequestException.class);
        ex.expectMessage("1234 is not a valid from number");
        client.callsApi().send(request);
    }

    @Test
    public void sendCallToInvalidNumber() throws Exception {
        SendCallRequest request = SendCallRequest.create()
            .name("Send Call Java API Client")
            .recipients(Collections.singletonList(new ToNumber("1234")))
            .voiceConfig(VoiceBroadcastConfig.create()
                .fromNumber("12132212384")
                .liveSound("this is LA TTS", Voice.MALE1)
                .amSound("this is AM TTS", Voice.FEMALE1)
            )
            .build();

        long id = client.callsApi().send(request);
        System.out.println("send call id: " + id);
        assertEquals(1000, id);

        QueryCallsRequest queryRequest = QueryCallsRequest.create()
            .broadcastId(id)
            .build();
        List<Call> calls = client.callsApi().query(queryRequest);
        System.out.println(calls);

        assertEquals(1, calls.size());
        assertEquals(ActionState.INVALID, calls.get(0).getState());
    }
}
