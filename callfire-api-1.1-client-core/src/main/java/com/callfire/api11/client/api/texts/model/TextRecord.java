package com.callfire.api11.client.api.texts.model;

import com.callfire.api11.client.api.common.model.ActionRecord;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextRecord extends ActionRecord {
    private TextResult result;
    private String message;

    public String getMessage() {
        return message;
    }

    public TextResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("result", result)
            .append("message", message)
            .toString();
    }
}
