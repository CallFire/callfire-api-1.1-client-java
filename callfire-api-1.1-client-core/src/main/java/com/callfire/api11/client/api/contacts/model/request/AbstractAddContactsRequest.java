package com.callfire.api11.client.api.contacts.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.SerializeAsSingleString;
import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.common.model.request.FileAttachment;
import com.callfire.api11.client.api.contacts.model.Contact;
import com.callfire.api11.client.api.contacts.model.NumbersField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Common request to add contacts to list
 */
public abstract class AbstractAddContactsRequest extends CfApi11Model implements FileAttachment {
    @QueryParamName("Validate")
    protected Boolean validateContacts;
    @QueryParamName("ContactId")
    protected List<Long> contactIds = new ArrayList<>();
    protected List<Contact> contacts = new ArrayList<>();
    @SerializeAsSingleString
    protected List<String> numbers = new ArrayList<>();
    @QueryParamName("Numbers[fieldName]")
    protected NumbersField numbersField;
    @QueryParamIgnore
    protected File csvFile;

    protected AbstractAddContactsRequest() {
    }

    public Boolean getValidateContacts() {
        return validateContacts;
    }

    public List<Long> getContactIds() {
        return contactIds;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public NumbersField getNumbersField() {
        return numbersField;
    }

    public File getCsvFile() {
        return csvFile;
    }

    @Override
    public File getFile() {
        return csvFile;
    }

    @Override
    public String getFileParamName() {
        return "File";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("validateContacts", validateContacts)
            .append("contactIds", contactIds)
            .append("contacts", contacts)
            .append("numbers", numbers)
            .append("numbersField", numbersField)
            .append("csvFile", csvFile)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class AbstractAddContactsRequestBuilder<B extends AbstractAddContactsRequestBuilder,
        R extends AbstractAddContactsRequest> extends AbstractBuilder<R> {

        public AbstractAddContactsRequestBuilder(R request) {
            super(request);
        }

        @Override
        protected void validate() {
            if (request.contactIds.isEmpty() && request.contacts.isEmpty() && request.numbers.isEmpty()
                && request.csvFile == null) {
                throw new IllegalStateException("one of the following fields mustn't be empty or null: " +
                    "request.contactIds / request.contacts / request.numbers / csvFile");
            }
        }

        /**
         * Turns off list validation (default: true)
         *
         * @param validateContacts if false turns off list validation
         * @return builder self reference
         */
        public B validateContacts(boolean validateContacts) {
            request.validateContacts = validateContacts;
            return (B) this;
        }

        /**
         * Set list of existing contact ids
         *
         * @param contactIds list of existing contact ids
         * @return builder self reference
         */
        public B contactIds(List<Long> contactIds) {
            request.contactIds = contactIds;
            return (B) this;
        }

        /**
         * Set list of contact objects
         *
         * @param contacts list of contacts
         * @return builder self reference
         */
        public B contacts(List<Contact> contacts) {
            request.contacts = contacts;
            return (B) this;
        }

        /**
         * Set list of plain numbers, see numbersField
         *
         * @param numbers list of numbers to add
         * @return builder self reference
         */
        public B numbers(List<String> numbers) {
            request.numbers = numbers;
            return (B) this;
        }

        /**
         * Set field number should be assigned to homePhone, workPhone or mobilePhone (default: homePhone)
         *
         * @param numbersField type of phone
         * @return builder self reference
         */
        public B numbersField(NumbersField numbersField) {
            request.numbersField = numbersField;
            return (B) this;
        }

        /**
         * Csv file attachment containing list of contacts or numbers
         *
         * @param csvFile csv file with contacts
         * @return builder self reference
         */
        public B csvFile(File csvFile) {
            request.csvFile = csvFile;
            return (B) this;
        }
    }
}
