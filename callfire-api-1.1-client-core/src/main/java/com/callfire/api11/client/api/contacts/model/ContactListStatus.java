package com.callfire.api11.client.api.contacts.model;

/**
 * Status of existing contact list in Callfire system
 */
public enum ContactListStatus {
    ACTIVE,
    VALIDATING,
    IMPORTING,
    IMPORT_FAILED,
    ERRORS,
    DELETED,
    PARSE_FAILED,
    COLUMN_TOO_LARGE;
}
