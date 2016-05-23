package com.callfire.api11.client.api.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Common response for 'create' operations
 */
public class ResourceReference {
    private Long id;
    private String location;

    public ResourceReference() {
    }

    public ResourceReference(Long id, String location) {
        this.id = id;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("location", location)
            .toString();
    }
}
