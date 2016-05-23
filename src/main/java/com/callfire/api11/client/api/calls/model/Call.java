package com.callfire.api11.client.api.calls.model;

import com.callfire.api11.client.api.common.model.Action;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Call extends Action {
    private CallResult finalResult;
    private List<CallRecord> callRecord;
    private List<Note> note;
    private Boolean agentCall;

    public List<CallRecord> getCallRecord() {
        return callRecord;
    }

    public List<Note> getNote() {
        return note;
    }

    public boolean isAgentCall() {
        return agentCall;
    }

    public CallResult getFinalResult() {
        return finalResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("finalResult", finalResult)
            .append("callRecord", callRecord)
            .append("note", note)
            .append("agentCall", agentCall)
            .toString();
    }
}
