package com.callfire.api11.client.api.broadcasts.model;

import com.callfire.api11.client.api.common.model.Result;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Attempted to placed calls/texts statistics aggregated by {@link Result} type
 */
public class ResultStats {
    private Result result;
    private int attempts;
    private int actions;

    /**
     * Get statistics result type
     *
     * @return result type
     */
    public Result getResult() {
        return result;
    }

    /**
     * Returns total number of attempts
     *
     * @return total number of attempts
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * Returns total number of actions
     *
     * @return total number of actions
     */
    public int getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("result", result)
            .append("attempts", attempts)
            .append("actions", actions)
            .toString();
    }
}
