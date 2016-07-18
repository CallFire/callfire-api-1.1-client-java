package com.callfire.api11.client.api.common.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic list response
 */
public class ResourceList<T> {
    // name of entity's type
    @JsonIgnore
    private String name;
    @JsonProperty("@totalResults")
    private Integer totalResults;
    @JsonIgnore
    private List<T> items = new ArrayList<T>();

    public ResourceList() {
    }

    public ResourceList(List<T> items, Class<T> itemsType) {
        this.items = items;
        this.name = itemsType.getSimpleName();
        this.totalResults = items.size();
    }

    public List<T> get() {
        return items;
    }

    @JsonAnySetter
    private void setItems(String name, List<T> items) {
        this.name = name;
        this.items = items;
    }

    @JsonAnyGetter
    private Map<String, List<T>> getItems() {
        Map<String, List<T>> mapping = new HashMap<String, List<T>>();
        mapping.put(name, items);
        return mapping;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("totalResults", totalResults)
            .append("items", items)
            .toString();
    }
}
