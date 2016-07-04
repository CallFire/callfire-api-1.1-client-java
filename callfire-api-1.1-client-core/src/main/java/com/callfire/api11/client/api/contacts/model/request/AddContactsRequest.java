package com.callfire.api11.client.api.contacts.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Common request to add contacts to list
 */
public class AddContactsRequest extends AbstractAddContactsRequest {
    @QueryParamIgnore
    private Long contactListId;

    private AddContactsRequest() {
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
     * Returns contact list id
     *
     * @return contact list id
     */
    public Long getContactListId() {
        return contactListId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("contactListId", contactListId)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends AbstractAddContactsRequestBuilder<Builder, AddContactsRequest> {

        public Builder() {
            super(new AddContactsRequest());
        }

        /**
         * Set contact list id
         *
         * @param contactListId contact list id
         * @return builder self reference
         */
        public Builder contactListId(Long contactListId) {
            request.contactListId = contactListId;
            return this;
        }

        @Override
        protected void validate() {
            super.validate();
            Validate.notNull(request.contactListId, "request.contactListId cannot be null");
        }
    }
}
