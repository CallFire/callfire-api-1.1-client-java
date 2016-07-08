package com.callfire.api11.client.api.broadcasts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Represents broadcast statistics
 */
public class BroadcastStats {
    private UsageStats usageStats;
    @JsonProperty("ResultStat")
    private List<ResultStats> resultStats;
    @JsonProperty("ActionStatistics")
    private ActionStats actionStats;

    public UsageStats getUsageStats() {
        return usageStats;
    }

    public List<ResultStats> getResultStats() {
        return resultStats;
    }

    public ActionStats getActionStats() {
        return actionStats;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("usageStats", usageStats)
            .append("resultStats", resultStats)
            .append("actionStats", actionStats)
            .toString();
    }
}
