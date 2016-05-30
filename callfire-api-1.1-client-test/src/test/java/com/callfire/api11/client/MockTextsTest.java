package com.callfire.api11.client;

import com.callfire.api11.client.api.common.model.ActionState;
import com.callfire.api11.client.api.common.model.ToNumber;
import com.callfire.api11.client.api.texts.model.Text;
import com.callfire.api11.client.api.texts.model.TextBroadcastConfig;
import com.callfire.api11.client.api.texts.model.request.QueryTextsRequest;
import com.callfire.api11.client.api.texts.model.request.SendTextRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test texts api with mocked client
 */
public class MockTextsTest {
    @Rule
    public ExpectedException ex = ExpectedException.none();
    private CfApi11Client client = MockClientFactory.newClient();

    @Before
    public void setUp() throws Exception {
        client = MockClientFactory.newClient();
    }

    @Test
    public void sendText() throws Exception {
        long id = client.textsApi().send(SendTextRequest.create().build());
        System.out.println(id);
    }

    @Test
    public void sendTextFromInvalidNumber() throws Exception {
        SendTextRequest request = SendTextRequest.create()
            .name("Send Text Java API Client")
            .recipients(Collections.singletonList(new ToNumber("12132212384")))
            .config(TextBroadcastConfig.create()
                .fromNumber("1234")
                .message("Api test message")
            )
            .build();

        ex.expect(BadRequestException.class);
        ex.expectMessage("invalid from number");
        client.textsApi().send(request);
    }

    @Test
    public void sendTextToInvalidNumber() throws Exception {
        SendTextRequest request = SendTextRequest.create()
            .name("Send Text Java API Client")
            .recipients(Collections.singletonList(new ToNumber("1234")))
            .config(TextBroadcastConfig.create()
                .fromNumber("12132212384")
                .message("Api test message")
            )
            .build();

        long id = client.textsApi().send(request);
        System.out.println("send text id: " + id);
        assertEquals(2000, id);

        QueryTextsRequest queryRequest = QueryTextsRequest.create()
            .broadcastId(id)
            .build();
        List<Text> texts = client.textsApi().query(queryRequest);
        System.out.println(texts);

        assertEquals(1, texts.size());
        assertEquals(ActionState.INVALID, texts.get(0).getState());
    }
}
