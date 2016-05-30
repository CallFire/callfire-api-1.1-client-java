package com.callfire.api11.client;

import com.callfire.api11.client.api.calls.model.Call;
import com.callfire.api11.client.api.calls.model.request.QueryCallsRequest;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import com.callfire.api11.client.api.texts.model.request.SendTextRequest;
import com.callfire.api11.client.test.CallfireTestUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

public class MockSubscriptionsTest {
    @Rule
    public ExpectedException ex = ExpectedException.none();
    private CfApi11Client client = MockClientFactory.newClient();

    @Before
    public void setUp() throws Exception {
        client = MockClientFactory.newClient();
    }

    @Test
    public void customResponse() throws Exception {
        Map<String, Pair<Integer, String>> responses = CallfireTestUtils.getJsonResponseMapping(client);
        responses.put(ModelType.listOf(Subscription.class).getType().toString(),
            new MutablePair<>(400, "/common/errorResponse.json"));

        ex.expect(BadRequestException.class);
        ex.expectMessage("TriggerEvent is required");
        client.subscriptionsApi().query(0, 1);
    }

    @Test
    public void querySubscriptions() throws Exception {
        List<Subscription> subscriptions = client.subscriptionsApi().query(0, 1);
        System.out.println(subscriptions);
    }
}
