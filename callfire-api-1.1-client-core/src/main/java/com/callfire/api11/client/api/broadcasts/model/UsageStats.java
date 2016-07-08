package com.callfire.api11.client.api.broadcasts.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Broadcast usage statistics
 */
public class UsageStats {
    private int duration;
    private int billedDuration;
    private float billedAmount;
    private int attempts;
    private int actions;

    /**
     * Returns duration of calls in seconds
     *
     * @return duration of calls in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns duration of calls billed in seconds
     *
     * @return duration of calls billed in seconds
     */
    public int getBilledDuration() {
        return billedDuration;
    }

    /**
     * Returns billed credits
     *
     * @return billed credits
     */
    public float getBilledAmount() {
        return billedAmount;
    }

    /**
     * Returns attempted Texts and Calls
     *
     * @return attempted Texts and Calls
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Returns number of Text and Calls placed
     *
     * @return number Text and Calls placed
     */
    public int getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("duration", duration)
            .append("billedDuration", billedDuration)
            .append("billedAmount", billedAmount)
            .append("attempts", attempts)
            .append("actions", actions)
            .toString();
    }
}
