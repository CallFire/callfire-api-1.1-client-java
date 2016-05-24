package com.callfire.api11.client.api.texts.model;

import com.callfire.api11.client.api.common.model.Action;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Text extends Action {
    private TextResult finalResult;
    private String message;
    private List<TextRecord> textRecord;

    public String getMessage() {
        return message;
    }

    public List<TextRecord> getTextRecord() {
        return textRecord;
    }

    public TextResult getFinalResult() {
        return finalResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("finalResult", finalResult)
            .append("message", message)
            .append("textRecord", textRecord)
            .toString();
    }
}
