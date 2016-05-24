package com.callfire.api11.client.api.subscriptions.model;

/**
 * Subscription trigger event
 */
public enum TriggerEvent {
    UNDEFINED_EVENT,
    INBOUND_CALL_FINISHED,
    INBOUND_TEXT_FINISHED,
    OUTBOUND_CALL_FINISHED,
    OUTBOUND_TEXT_FINISHED,
    CAMPAIGN_STARTED,
    CAMPAIGN_STOPPED,
    CAMPAIGN_FINISHED;
}
