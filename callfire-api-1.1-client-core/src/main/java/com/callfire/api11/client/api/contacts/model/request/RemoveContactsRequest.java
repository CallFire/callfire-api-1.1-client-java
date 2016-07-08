package com.callfire.api11.client.api.contacts.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Request for remove contacts from a list without deleting the contacts.
 */
public class RemoveContactsRequest extends CfApi11Model {
    @QueryParamIgnore
    private Long contactListId;
    @QueryParamName("ContactId")
    private List<Long> contactIds = new ArrayList<>();
    private List<String> numbers = new ArrayList<>();

    private RemoveContactsRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public Long getContactListId() {
        return contactListId;
    }

    public List<Long> getContactIds() {
        return contactIds;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("contactListId", contactListId)
            .append("contactIds", contactIds)
            .append("numbers", numbers)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends AbstractBuilder<RemoveContactsRequest> {

        public Builder() {
            super(new RemoveContactsRequest());
        }

        @Override
        protected void validate() {
            Validate.notNull(request.contactListId, "request.contactListId cannot be null");
            if (request.contactIds.isEmpty() && request.numbers.isEmpty()) {
                throw new IllegalStateException("request.contactIds or request.numbers mustn't be empty");
            }
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

        /**
         * Set contact ids to remove
         *
         * @param contactIds contact ids to remove
         * @return builder self reference
         */
        public Builder contactIds(List<Long> contactIds) {
            request.contactIds = contactIds;
            return this;
        }

        /**
         * Set contact numbers to remove
         *
         * @param numbers numbers to remove from contact list
         * @return builder self reference
         */
        public Builder numbers(List<String> numbers) {
            request.numbers = numbers;
            return this;
        }
    }
}
