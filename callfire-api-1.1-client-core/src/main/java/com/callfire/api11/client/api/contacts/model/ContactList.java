package com.callfire.api11.client.api.contacts.model;

import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Represents contact list in Callfire system
 */
public class ContactList extends CfApi11Model {
    @JsonProperty("@id")
    private Long id;
    private String name;
    private Long size;
    private ContactListStatus status;
    private Date created;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }

    public ContactListStatus getStatus() {
        return status;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("name", name)
            .append("size", size)
            .append("status", status)
            .append("created", created)
            .toString();
    }
}
