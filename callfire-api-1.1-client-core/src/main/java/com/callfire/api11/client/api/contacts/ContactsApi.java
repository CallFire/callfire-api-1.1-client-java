package com.callfire.api11.client.api.contacts;

import com.callfire.api11.client.AccessForbiddenException;
import com.callfire.api11.client.BadRequestException;
import com.callfire.api11.client.CfApi11ApiException;
import com.callfire.api11.client.CfApi11ClientException;
import com.callfire.api11.client.InternalServerErrorException;
import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.RestApi11Client;
import com.callfire.api11.client.UnauthorizedException;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.request.QueryByIdRequest;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.contacts.model.Contact;
import com.callfire.api11.client.api.contacts.model.ContactHistory;
import com.callfire.api11.client.api.contacts.model.ContactList;
import com.callfire.api11.client.api.contacts.model.request.AddContactsRequest;
import com.callfire.api11.client.api.contacts.model.request.CreateContactListRequest;
import com.callfire.api11.client.api.contacts.model.request.QueryContactsRequest;
import com.callfire.api11.client.api.contacts.model.request.RemoveContactsRequest;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api11.client.ClientUtils.asParams;
import static com.callfire.api11.client.ModelType.listOf;
import static com.callfire.api11.client.ModelType.of;
import static com.callfire.api11.client.ModelType.resourceOf;

/**
 * Contacts API provides different methods to manipulate Contacts and Contact Lists in your account
 */
public class ContactsApi {
    private static final String CONTACTS_PATH = "/contact.json";
    private static final String CONTACTS_ITEM_PATH = "/contact/{}.json";
    private static final String CONTACTS_HISTORY_ITEM_PATH = "/contact/{}/history.json";
    private static final String CONTACT_LISTS_PATH = "/contact/list.json";
    private static final String CONTACT_LISTS_ITEM_PATH = "/contact/list/{}.json";
    private static final String CONTACT_LISTS_ITEM_ADD_PATH = "/contact/list/{}/add.json";
    private static final String CONTACT_LISTS_ITEM_REMOVE_PATH = "/contact/list/{}/remove.json";

    private RestApi11Client client;

    public ContactsApi(RestApi11Client client) {
        this.client = client;
    }

    /**
     * Query for existing contacts using optional filters such as ContactListId, Field, etc.
     * Returns a list of contacts and all associated info. See GetContact to return just a single contact by id.
     *
     * @param request request object with filtering options
     * @return {@link List} of {@link Subscription} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<Contact> query(QueryContactsRequest request) {
        return client.query(CONTACTS_PATH, listOf(Contact.class), request).get();
    }

    /**
     * Return individual contact by ID. See QueryContacts to return a list of contacts and determine individual contactIds.
     *
     * @param id contact id
     * @return {@link Contact} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Contact get(long id) {
        String path = CONTACTS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(Contact.class)).get();
    }

    /**
     * List all calls and texts associated with a contact.
     *
     * @param request request object with filtering options
     * @return {@link Contact} object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public ContactHistory getHistory(QueryByIdRequest request) {
        String path = CONTACTS_HISTORY_ITEM_PATH.replaceFirst(PLACEHOLDER, request.getId().toString());
        return client.query(path, resourceOf(ContactHistory.class), request).get();
    }

    /**
     * Update existing contact
     *
     * @param contact contact to update
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void update(Contact contact) {
        update(Collections.singletonList(contact));
    }

    /**
     * Update existing contacts
     *
     * @param contacts contacts to update
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void update(List<Contact> contacts) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            Validate.notNull(contact.getId(), "contact.id cannot be null");
            params.addAll(contact.serializeToMap(i));
        }
        client.put(CONTACTS_PATH, of(Object.class), null, params);
    }

    /**
     * Delete single contact identified by id from system. If id points to non-existent contact the id will be
     * ignored and processing will continue.
     *
     * @param id contact id to delete
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void delete(long id) {
        delete(Collections.singletonList(id));
    }

    /**
     * Delete contacts identified by id from system. If id points to non-existent contact the id will be
     * ignored and processing will continue.
     *
     * @param ids contact ids to delete
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void delete(List<Long> ids) {
        client.delete(CONTACTS_PATH, asParams("ContactId", ids));
    }

    /**
     * Add contact list to account using 1 of 4 inputs: list of contacts, numbers string, list of contactIds, or
     * csv file containing contacts or numbers. If more then one ContactSource specified then only load
     * from 1 source with precedence as listed above.
     * On import contact lists go through seven system safeguards that check the accuracy of the list.
     * For example, our system checks if a number is formatted correctly, is invalid, is duplicated in another
     * contact list, or is on your Do Not Contact list. API calls have their default validation error
     * resolutions set differently then the defaults set on the CallFire web site under Settings | List Validation.
     * <p>
     * The API validation defaults are:
     * LIST_COLUMNS_UNMAPPED - Resolution USE_DEFAULT_COLUMNS
     * LIST_HAS_DUPLICATE_NUMBERS - Resolution SCRUB
     * LIST_HAS_DNC_CONTACTS - Resolution SCRUB
     * LIST_HAS_CONTACT_CONFLICTS - Resolution MERGE
     * LIST_HAS_INVALID_NUMBERS - Resolution SCRUB
     *
     * @param request request object
     * @return contact list id
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public Long createContactList(CreateContactListRequest request) {
        return client.post(CONTACT_LISTS_PATH, of(ResourceReference.class), request).getId();
    }

    /**
     * Query for existing contact lists. Currently does no filtering and returns all contact lists.
     *
     * @param request request object with filtering options
     * @return {@link List} of {@link ContactList} objects
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public List<ContactList> queryContactLists(QueryRequest request) {
        return client.query(CONTACT_LISTS_PATH, listOf(ContactList.class), request).get();
    }

    /**
     * Return individual contact list by ID. See QueryContactLists to return a list of contact lists and determine
     * individual contactListIds.
     *
     * @param id contact list id to fetch
     * @return Contact list
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public ContactList getContactList(long id) {
        String path = CONTACT_LISTS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id));
        return client.get(path, resourceOf(ContactList.class)).get();
    }

    /**
     * Delete contact list identified by id.
     *
     * @param id contact list id to delete
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void deleteContactList(long id) {
        client.delete(CONTACT_LISTS_ITEM_PATH.replaceFirst(PLACEHOLDER, String.valueOf(id)));
    }

    /**
     * Add contacts to contact list in account using 1 of 4 inputs: list of contacts, numbers string, list of contactIds, or
     * csv file containing contacts or numbers. If more then one ContactSource specified then only load
     * from 1 source with precedence as listed above.
     * On import contact lists go through seven system safeguards that check the accuracy of the list.
     * For example, our system checks if a number is formatted correctly, is invalid, is duplicated in another
     * contact list, or is on your Do Not Contact list. API calls have their default validation error
     * resolutions set differently then the defaults set on the CallFire web site under Settings | List Validation.
     * <p>
     * The API validation defaults are:
     * LIST_COLUMNS_UNMAPPED - Resolution USE_DEFAULT_COLUMNS
     * LIST_HAS_DUPLICATE_NUMBERS - Resolution SCRUB
     * LIST_HAS_DNC_CONTACTS - Resolution SCRUB
     * LIST_HAS_CONTACT_CONFLICTS - Resolution MERGE
     * LIST_HAS_INVALID_NUMBERS - Resolution SCRUB
     *
     * @param request request object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void addContacts(AddContactsRequest request) {
        String path = CONTACT_LISTS_ITEM_ADD_PATH.replaceFirst(PLACEHOLDER, request.getContactListId().toString());
        client.post(path, of(Object.class), request);
    }

    /**
     * Removes contacts from a list without deleting the contacts.
     *
     * @param request request object
     * @throws BadRequestException          in case HTTP response code is 400 - Bad request, the request was formatted improperly.
     * @throws UnauthorizedException        in case HTTP response code is 401 - Unauthorized, API Key missing or invalid.
     * @throws AccessForbiddenException     in case HTTP response code is 403 - Forbidden, insufficient permissions.
     * @throws ResourceNotFoundException    in case HTTP response code is 404 - NOT FOUND, the resource requested does not exist.
     * @throws InternalServerErrorException in case HTTP response code is 500 - Internal Server Error.
     * @throws CfApi11ApiException          in case HTTP response code is something different from codes listed above.
     * @throws CfApi11ClientException       in case error has occurred in client.
     */
    public void removeContacts(RemoveContactsRequest request) {
        String path = CONTACT_LISTS_ITEM_REMOVE_PATH.replaceFirst(PLACEHOLDER, request.getContactListId().toString());
        client.post(path, of(Object.class), request);
    }
}
