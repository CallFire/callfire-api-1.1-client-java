package com.callfire.api11.client.api.calls.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

import static com.callfire.api11.client.ClientConstants.TIMESTAMP_FORMAT_PATTERN;

public class Note {
    private String text;
    private Date created;

    public String getText() {
        return text;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("text", text)
            .append("created", created)
            .toString();
    }
}
