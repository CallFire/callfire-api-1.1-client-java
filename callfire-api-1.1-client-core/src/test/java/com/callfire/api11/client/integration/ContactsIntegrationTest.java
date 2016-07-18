package com.callfire.api11.client.integration;

import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.contacts.model.Contact;
import com.callfire.api11.client.api.contacts.model.ContactHistory;
import com.callfire.api11.client.api.contacts.model.ContactList;
import com.callfire.api11.client.api.contacts.model.ContactListStatus;
import com.callfire.api11.client.api.contacts.model.NumbersField;
import com.callfire.api11.client.api.contacts.model.request.AddContactsRequest;
import com.callfire.api11.client.api.contacts.model.request.CreateContactListRequest;
import com.callfire.api11.client.api.common.model.request.QueryByIdRequest;
import com.callfire.api11.client.api.contacts.model.request.QueryContactsRequest;
import com.callfire.api11.client.api.contacts.model.request.RemoveContactsRequest;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@Ignore
public class ContactsIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void getHistory() throws Exception {
        ContactHistory history = client.contactsApi().getHistory(QueryByIdRequest.create().id(742136314003L).build());
        System.out.println(history);

        assertEquals(2, history.getCalls().size());
        assertEquals(1, history.getTexts().size());
    }

    @Test
    public void createContactList() throws Exception {
        // Contact Lists
        // create from csv
        CreateContactListRequest request = CreateContactListRequest.create()
            .name("contact list from csv")
            .validateContacts(false)
            .csvFile(new File("src/test/resources/contacts.csv"))
            .build();
        Long id = client.contactsApi().createContactList(request);
        ContactList csvList = client.contactsApi().getContactList(id);
        System.out.println(csvList);
        assertEquals(id, csvList.getId());
        assertEquals("contact list from csv", csvList.getName());
        assertEquals(Long.valueOf(2), csvList.getSize());
        assertEquals(ContactListStatus.ACTIVE, csvList.getStatus());

        List<ContactList> contactLists = client.contactsApi()
            .queryContactLists(QueryRequest.createSimpleQuery().build());
        assertThat(contactLists, hasItem(Matchers.<ContactList>hasProperty("name", is("contact list from csv"))));

        // delete-query contacts
        QueryContactsRequest queryContactsRequest = QueryContactsRequest.create().contactListId(id).build();
        List<Contact> contacts = client.contactsApi().query(queryContactsRequest);
        System.out.println(contacts);
        assertEquals(2, contacts.size());
        List<Long> contactIds = new ArrayList<Long>(contacts.size());
        for (Contact contact : contacts) {
            contactIds.add(contact.getId());
        }
        client.contactsApi().delete(contactIds);
        contacts = client.contactsApi().query(queryContactsRequest);
        assertEquals(0, contacts.size());

        // delete contact list
        client.contactsApi().deleteContactList(id);
        ContactList contactList = client.contactsApi().getContactList(id);
        assertEquals(ContactListStatus.DELETED, contactList.getStatus());

        // create from Contact objects
        Contact contact1 = new Contact();
        contact1.setFirstName("first_name1");
        contact1.setLastName("last_name1");
        contact1.setHomePhone("111");
        contact1.setWorkPhone("112");
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("A", "data1");
        attrs.put("B", "data2");
        contact1.setAttributes(attrs);
        Contact contact2 = new Contact();
        contact2.setFirstName("first_name2");
        contact2.setLastName("last_name2");
        contact2.setMobilePhone("444");
        attrs = new HashMap<String, String>();
        attrs.put("C", "data 3");
        attrs.put("D", "data 4");
        contact2.setAttributes(attrs);

        request = CreateContactListRequest.create()
            .name("contact list from contact object")
            .validateContacts(false)
            .contacts(Arrays.asList(contact1, contact2))
            .build();
        id = client.contactsApi().createContactList(request);
        queryContactsRequest = QueryContactsRequest.create().contactListId(id).build();
        contacts = client.contactsApi().query(queryContactsRequest);
        System.out.println(contacts);
        assertEquals(2, contacts.size());

        System.out.println(client.contactsApi().get(contacts.get(0).getId()));
        System.out.println(client.contactsApi().get(contacts.get(1).getId()));
    }

    @Test
    public void createListAddContactsAndThenUpdate() throws Exception {
        // create from plain numbers then add more contacts, then update contacts
        CreateContactListRequest request = CreateContactListRequest.create()
            .name("contact list from numbers")
            .validateContacts(false)
            .numbers(Collections.singletonList("111222333444"))
            .numbersField(NumbersField.MOBILE_PHONE)
            .build();
        Long id = client.contactsApi().createContactList(request);
        QueryContactsRequest queryContactsRequest = QueryContactsRequest.create().contactListId(id).build();
        List<Contact> contacts = client.contactsApi().query(queryContactsRequest);
        System.out.println(contacts);
        assertEquals(1, contacts.size());
        Contact contact = contacts.get(0);
        assertEquals("111222333444", contact.getMobilePhone());
        assertNotNull(contact.getId());
        assertNull(contact.getFirstName());
        assertNull(contact.getLastName());
        assertNull(contact.getZipcode());
        assertNull(contact.getWorkPhone());
        assertNull(contact.getHomePhone());
        assertEquals(0, contact.getAttributes().size());

        AddContactsRequest addContactsRequest = AddContactsRequest.create()
            .contactListId(id)
            .validateContacts(false)
            .numbers(Collections.singletonList("555000"))
            .build();
        client.contactsApi().addContacts(addContactsRequest);

        ContactList contactList = client.contactsApi().getContactList(id);
        assertEquals(Long.valueOf(2), contactList.getSize());

        queryContactsRequest = QueryContactsRequest.create()
            .contactListId(id)
            .field("homePhone")
            .string("555000")
            .build();
        contacts = client.contactsApi().query(queryContactsRequest);
        assertEquals(1, contacts.size());
        assertEquals("555000", contacts.get(0).getHomePhone());
        contacts.get(0).setFirstName("UpdatedName");
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("A", "custom data");
        contacts.get(0).setAttributes(attrs);

        // update contacts
        contact.setFirstName("FirstName");
        contact.setLastName("LastName");
        contact.setZipcode("1234");
        contact.setWorkPhone("12132212380");
        contact.setHomePhone("12132212381");
        contact.setMobilePhone("12132212382");
        attrs = new HashMap<String, String>();
        attrs.put("A", "data 1");
        attrs.put("B", "data 2");
        contact.setAttributes(attrs);

        client.contactsApi().update(Arrays.asList(contact, contacts.get(0)));
        Contact updatedContact1 = client.contactsApi().get(contact.getId());
        System.out.println("updatedContact1: " + updatedContact1);
        assertEquals(contact.getFirstName(), updatedContact1.getFirstName());
        assertEquals(contact.getLastName(), updatedContact1.getLastName());
        assertEquals(contact.getZipcode(), updatedContact1.getZipcode());
        assertEquals(contact.getHomePhone(), updatedContact1.getHomePhone());
        assertEquals(contact.getWorkPhone(), updatedContact1.getWorkPhone());
        assertEquals(contact.getMobilePhone(), updatedContact1.getMobilePhone());
        assertEquals(contact.getAttributes(), updatedContact1.getAttributes());

        Contact updatedContact2 = client.contactsApi().get(contacts.get(0).getId());
        System.out.println("updatedContact2: " + updatedContact2);
        assertEquals(contacts.get(0).getFirstName(), updatedContact2.getFirstName());
        assertEquals("custom data", updatedContact2.getAttributes().get("A"));

        RemoveContactsRequest removeContactsRequest = RemoveContactsRequest.create()
            .contactListId(id)
            .contactIds(Arrays.asList(updatedContact1.getId(), updatedContact2.getId()))
            .build();
        client.contactsApi().removeContacts(removeContactsRequest);
        contactList = client.contactsApi().getContactList(id);
        assertEquals(Long.valueOf(0), contactList.getSize());
    }
}
