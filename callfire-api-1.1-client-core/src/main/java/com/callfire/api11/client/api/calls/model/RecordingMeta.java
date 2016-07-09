package com.callfire.api11.client.api.calls.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class RecordingMeta {
    private String name;
    private Date created;
    private Integer lengthInSeconds;
    private String link;
    private long id;

    public String getName() {
        return name;
    }

    public Date getCreated() {
        return created;
    }

    public Integer getLengthInSeconds() {
        return lengthInSeconds;
    }

    public String getLink() {
        return link;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .append("created", created)
            .append("lengthInSeconds", lengthInSeconds)
            .append("link", link)
            .append("id", id)
            .toString();
    }
}
