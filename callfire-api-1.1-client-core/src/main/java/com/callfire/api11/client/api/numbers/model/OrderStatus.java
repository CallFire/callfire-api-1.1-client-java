package com.callfire.api11.client.api.numbers.model;

public enum OrderStatus {
    NEW,
    PROCESSING,
    FINISHED,
    ERRORED,
    VOID,
    WAIT_FOR_PAYMENT,
    ADJUSTED,
    APPROVE_TIER_ONE,
    APPROVE_TIER_TWO,
    REJECTED;
}
