package com.callfire.api11.client.api.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Label {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .toString();
    }
}
