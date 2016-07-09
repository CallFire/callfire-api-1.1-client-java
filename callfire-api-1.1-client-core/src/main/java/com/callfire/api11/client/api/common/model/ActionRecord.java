package com.callfire.api11.client.api.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

public class ActionRecord extends CfApi11Model {
    @JsonProperty("@id")
    private Long id;
    private Date finishTime;
    private Double billedAmount;
    private List<QuestionResponse> questionResponse;
    private String switchId;
    private String callerName;

    public long getId() {
        return id;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public Double getBilledAmount() {
        return billedAmount;
    }

    public List<QuestionResponse> getQuestionResponse() {
        return questionResponse;
    }

    public String getSwitchId() {
        return switchId;
    }

    public String getCallerName() {
        return callerName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("finishTime", finishTime)
            .append("billedAmount", billedAmount)
            .append("questionResponse", questionResponse)
            .append("switchId", switchId)
            .append("callerName", callerName)
            .toString();
    }
}
