package com.callfire.api11.client.api.broadcasts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Action (call/text) statistics
 */
public class ActionStats {
    @JsonProperty("Unattempted")
    private int unAttempted;
    private int retryWait;
    private int finished;

    /**
     * Returns number of actions which weren't attempted
     *
     * @return number of actions which weren't attempted
     */
    public int getUnAttempted() {
        return unAttempted;
    }

    /**
     * Returns number of actions which were retried
     *
     * @return number of actions which were retried
     */
    public int getRetryWait() {
        return retryWait;
    }

    /**
     * Returns number of actions which were successfully finished
     *
     * @return number of actions which were successfully finished
     */
    public int getFinished() {
        return finished;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("unAttempted", unAttempted)
            .append("retryWait", retryWait)
            .append("finished", finished)
            .toString();
    }
}
