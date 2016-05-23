package com.callfire.api11.client.api.common.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Generic resource holder
 */
public class Resource<T> {
    // name of entity's type
    @JsonIgnore
    private String name;
    @JsonIgnore
    private T resource;

    public Resource() {
    }

    public Resource(T resource, Class<T> resourceType) {
        this.resource = resource;
        this.name = resourceType.getSimpleName();
    }

    public T get() {
        return resource;
    }

    @JsonAnySetter
    private void setResource(String name, T resource) {
        this.name = name;
        this.resource = resource;
    }

    @JsonAnyGetter
    private Map<String, T> getResource() {
        Map<String, T> mapping = new HashMap<>();
        mapping.put(name, resource);
        return mapping;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("resource", resource)
            .toString();
    }
}
