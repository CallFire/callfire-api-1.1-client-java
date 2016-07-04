package com.callfire.api11.client.api.contacts.model.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Request is used to create a new contact list with contacts
 */
public class CreateContactListRequest extends AbstractAddContactsRequest {
    private String name;

    private CreateContactListRequest() {
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
     * Returns contact list name
     *
     * @return id
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends AbstractAddContactsRequestBuilder<Builder, CreateContactListRequest> {

        public Builder() {
            super(new CreateContactListRequest());
        }

        /**
         * Set contact list name
         *
         * @param name contact list name
         * @return builder self reference
         */
        public Builder name(String name) {
            request.name = name;
            return this;
        }
    }
}
