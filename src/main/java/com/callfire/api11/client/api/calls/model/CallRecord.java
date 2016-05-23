package com.callfire.api11.client.api.calls.model;

import com.callfire.api11.client.api.common.model.ActionRecord;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.TIMESTAMP_FORMAT_PATTERN;

public class CallRecord extends ActionRecord {
    private CallResult result;
    private Date originateTime;
    private Date answerTime;
    private Integer duration;
    private List<RecordingMeta> recordingMeta;
    private List<Note> note;

    public CallResult getResult() {
        return result;
    }

    public Date getOriginateTime() {
        return originateTime;
    }

    public Date getAnswerTime() {
        return answerTime;
    }

    public int getDuration() {
        return duration;
    }

    public List<RecordingMeta> getRecordingMeta() {
        return recordingMeta;
    }

    public List<Note> getNote() {
        return note;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("result", result)
            .append("originateTime", originateTime)
            .append("answerTime", answerTime)
            .append("duration", duration)
            .append("recordingMeta", recordingMeta)
            .append("note", note)
            .toString();
    }
}
