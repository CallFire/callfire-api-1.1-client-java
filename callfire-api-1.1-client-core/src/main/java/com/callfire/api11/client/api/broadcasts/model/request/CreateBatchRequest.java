package com.callfire.api11.client.api.broadcasts.model.request;

import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.common.model.ToNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Request is used to create new contact batch for broadcast
 */
public class CreateBatchRequest extends CfApi11Model {
    private long broadcastId;
    private String name;
    private Long contactListId;
    @QueryParamName("To")
    private List<ToNumber> numbers;
    @QueryParamName("ScrubBroadcastDuplicates")
    private Boolean scrubDuplicates;
    private Boolean start;

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public long getBroadcastId() {
        return broadcastId;
    }

    public String getName() {
        return name;
    }

    public Long getContactListId() {
        return contactListId;
    }

    public List<ToNumber> getNumbers() {
        return numbers;
    }

    public Boolean getScrubDuplicates() {
        return scrubDuplicates;
    }

    public Boolean getStart() {
        return start;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("broadcastId", broadcastId)
            .append("name", name)
            .append("contactListId", contactListId)
            .append("numbers", numbers)
            .append("scrubDuplicates", scrubDuplicates)
            .append("start", start)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends AbstractBuilder<CreateBatchRequest> {

        public Builder() {
            super(new CreateBatchRequest());
        }

        @Override
        protected void validate() {
            Validate.notNull(request.broadcastId, "request.contactListId cannot be null");
            if (request.contactListId == null && request.numbers.isEmpty()) {
                throw new IllegalStateException("request.contactListId or request.numbers mustn't be empty");
            }
        }

        /**
         * Set broadcast id
         *
         * @param broadcastId broadcast id
         * @return builder self reference
         */
        public Builder broadcastId(Long broadcastId) {
            request.broadcastId = broadcastId;
            return this;
        }

        /**
         * Set batch name
         *
         * @param name contact batch name
         * @return builder self reference
         */
        public Builder name(String name) {
            request.name = name;
            return this;
        }

        /**
         * Set contact list id as a contact source
         *
         * @param contactListId contact list id
         * @return builder self reference
         */
        public Builder contactListId(Long contactListId) {
            request.contactListId = contactListId;
            return this;
        }

        /**
         * Set contact numbers as a source for contact batch
         *
         * @param numbers numbers to add to broadcast
         * @return builder self reference
         */
        public Builder numbers(List<ToNumber> numbers) {
            request.numbers = numbers;
            return this;
        }

        /**
         * Set scrub contact duplicates (default: false)
         *
         * @param scrubDuplicates if true duplicates will be scrubbed
         * @return builder self reference
         */
        public Builder scrubDuplicates(boolean scrubDuplicates) {
            request.scrubDuplicates = scrubDuplicates;
            return this;
        }

        /**
         * Starts broadcast immediately (default: false)
         *
         * @param start if true starts broadcast immediately
         * @return builder self reference
         */
        public Builder start(boolean start) {
            request.start = start;
            return this;
        }
    }
}
