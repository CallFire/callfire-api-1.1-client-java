package com.callfire.api11.client.api.contacts.model;

/**
 * Phone number fields
 */
public enum NumbersField {
    HOME_PHONE("homePhone"),
    WORK_PHONE("workPhone"),
    MOBILE_PHONE("mobilePhone");

    private String fieldName;

    NumbersField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return fieldName;
    }
}
