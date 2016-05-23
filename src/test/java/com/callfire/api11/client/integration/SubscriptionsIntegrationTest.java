package com.callfire.api11.client.integration;

import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.api.subscriptions.SubscriptionsApi;
import com.callfire.api11.client.api.subscriptions.model.NotificationFormat;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import com.callfire.api11.client.api.subscriptions.model.SubscriptionFilter;
import com.callfire.api11.client.api.subscriptions.model.TriggerEvent;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Ignore
public class SubscriptionsIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void crudOperations() throws Exception {
        SubscriptionsApi api = client.subscriptionsApi();

        String endpoint = "test_endpoint";
        Subscription subscription = new Subscription();
        subscription.setEndpoint(endpoint);
        subscription.setTriggerEvent(TriggerEvent.CAMPAIGN_STARTED);
        subscription.setNotificationFormat(NotificationFormat.JSON);
        subscription.setFilter(new SubscriptionFilter(1L, null, "123", "321"));
        Long id = api.create(subscription);
        assertNotNull(id);

        subscription.setId(id);
        subscription.getFilter().setToNumber("123");

        api.update(subscription);

        Subscription stored = api.get(id);
        assertEquals(endpoint, stored.getEndpoint());
        assertEquals(TriggerEvent.CAMPAIGN_STARTED, stored.getTriggerEvent());
        assertEquals(NotificationFormat.JSON, stored.getNotificationFormat());
        assertEquals(Long.valueOf(1), stored.getFilter().getBroadcastId());
        assertNull(stored.getFilter().getBatchId());
        assertEquals("123", stored.getFilter().getFromNumber());
        assertEquals("123", stored.getFilter().getToNumber());

        List<Subscription> subscriptions = api.query(0, 1);
        assertEquals(1, subscriptions.size());

        api.delete(id);

        ex.expect(ResourceNotFoundException.class);
        ex.expectMessage("Not Found");
        api.get(id);
    }
}
