package com.callfire.api11.client.api.contacts.model.request;

import com.callfire.api11.client.api.common.model.request.QueryRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Query for existing contacts using optional filters such as ContactListId, Field, etc.
 */
public class QueryContactsRequest extends QueryRequest {
    private String field;
    private Long contactListId;
    private String string;

    private QueryContactsRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Returns contact field name to filter by
     *
     * @return field name
     */
    public String getField() {
        return field;
    }

    /**
     * Returns contact list id to filter by
     *
     * @return contact list id
     */
    public Long getContactListId() {
        return contactListId;
    }

    /**
     * Returns contact field value to filter by
     *
     * @return field value
     */
    public String getString() {
        return string;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("field", field)
            .append("contactListId", contactListId)
            .append("string", string)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends QueryRequestBuilder<Builder, QueryContactsRequest> {

        public Builder() {
            super(new QueryContactsRequest());
        }

        /**
         * Set contact list id
         *
         * @param id contact list id
         * @return builder self reference
         */
        public Builder contactListId(Long id) {
            request.contactListId = id;
            return this;
        }

        /**
         * Set field name to filter by
         *
         * @param field contact field name to filter by
         * @return builder self reference
         */
        public Builder field(String field) {
            request.field = field;
            return this;
        }

        /**
         * Set field value to filter by
         *
         * @param string contact field value to filter by
         * @return builder self reference
         */
        public Builder string(String string) {
            request.string = string;
            return this;
        }
    }
}
