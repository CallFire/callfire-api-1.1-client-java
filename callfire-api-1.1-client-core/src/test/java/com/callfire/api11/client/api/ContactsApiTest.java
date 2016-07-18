package com.callfire.api11.client.api;

import com.callfire.api11.client.api.common.model.Resource;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.request.QueryByIdRequest;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.contacts.model.Contact;
import com.callfire.api11.client.api.contacts.model.ContactHistory;
import com.callfire.api11.client.api.contacts.model.ContactList;
import com.callfire.api11.client.api.contacts.model.NumbersField;
import com.callfire.api11.client.api.contacts.model.request.AddContactsRequest;
import com.callfire.api11.client.api.contacts.model.request.CreateContactListRequest;
import com.callfire.api11.client.api.contacts.model.request.QueryContactsRequest;
import com.callfire.api11.client.api.contacts.model.request.RemoveContactsRequest;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.callfire.api11.client.test.CallfireTestUtils.extractHttpEntity;
import static com.callfire.api11.client.test.CallfireTestUtils.extractHttpMultipartEntity;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class ContactsApiTest extends AbstractApiTest {

    @Test
    public void query() throws Exception {
        String expectedJson = getJsonPayload("/contacts/query.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryContactsRequest request = QueryContactsRequest.create()
            .firstResult(2)
            .maxResults(100)
            .contactListId(5L)
            .field("A")
            .string("data")
            .build();
        List<Contact> contacts = client.contactsApi().query(request);
        ResourceList<Contact> response = new ResourceList<Contact>(contacts, Contact.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("ContactListId=5"));
        assertThat(arg.getURI().toString(), containsString("Field=A"));
        assertThat(arg.getURI().toString(), containsString("String=data"));
        assertThat(arg.getURI().toString(), containsString("FirstResult=2"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/contacts/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Contact contact = client.contactsApi().get(1234567L);
        Resource<Contact> response = new Resource<Contact>(contact, Contact.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/contact/1234567.json"));
    }

    @Test
    public void getHistory() throws Exception {
        String expectedJson = getJsonPayload("/contacts/getHistory.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryByIdRequest request = QueryByIdRequest.create()
            .id(5L)
            .build();
        ContactHistory contactHistory = client.contactsApi().getHistory(request);
        Resource<ContactHistory> response = new Resource<ContactHistory>(contactHistory, ContactHistory.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/contact/5/history.json"));
    }

    @Test
    public void delete() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.contactsApi().delete(Arrays.asList(1L, 2L, 3L));

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/contact.json"));
        assertThat(arg.getURI().toString(), containsString("ContactId=1"));
        assertThat(arg.getURI().toString(), containsString("ContactId=2"));
        assertThat(arg.getURI().toString(), containsString("ContactId=3"));
    }

    @Test
    public void update() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setFirstName("Alice");
        contact1.setLastName("Moore");
        contact1.setZipcode("12345");
        contact1.setHomePhone("111");
        contact1.setWorkPhone("112");
        contact1.setMobilePhone("113");
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("A", "data1");
        attrs.put("B", "data2");
        contact1.setAttributes(attrs);
        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setFirstName("Bob");
        contact2.setLastName("Smith");
        contact2.setZipcode("22233");
        contact2.setHomePhone("222");
        contact2.setWorkPhone("333");
        contact2.setMobilePhone("444");
        attrs = new HashMap<String, String>();
        attrs.put("C", "data 3");
        attrs.put("D", "data 4");
        contact2.setAttributes(attrs);

        client.contactsApi().update(Arrays.asList(contact1, contact2));

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/contact.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString(encode("Contact[0][id]") + "=1"));
        assertThat(requestBody, containsString(encode("Contact[0][firstName]") + "=Alice"));
        assertThat(requestBody, containsString(encode("Contact[0][lastName]") + "=Moore"));
        assertThat(requestBody, containsString(encode("Contact[0][zipcode]") + "=12345"));
        assertThat(requestBody, containsString(encode("Contact[0][homePhone]") + "=111"));
        assertThat(requestBody, containsString(encode("Contact[0][workPhone]") + "=112"));
        assertThat(requestBody, containsString(encode("Contact[0][mobilePhone]") + "=113"));
        assertThat(requestBody, containsString(encode("Contact[0][A]") + "=data1"));
        assertThat(requestBody, containsString(encode("Contact[0][B]") + "=data2"));
        //
        assertThat(requestBody, containsString(encode("Contact[1][id]") + "=2"));
        assertThat(requestBody, containsString(encode("Contact[1][firstName]") + "=Bob"));
        assertThat(requestBody, containsString(encode("Contact[1][lastName]") + "=Smith"));
        assertThat(requestBody, containsString(encode("Contact[1][zipcode]") + "=22233"));
        assertThat(requestBody, containsString(encode("Contact[1][homePhone]") + "=222"));
        assertThat(requestBody, containsString(encode("Contact[1][workPhone]") + "=333"));
        assertThat(requestBody, containsString(encode("Contact[1][mobilePhone]") + "=444"));
        assertThat(requestBody, containsString(encode("Contact[1][C]") + "=" + encode("data 3")));
        assertThat(requestBody, containsString(encode("Contact[1][D]") + "=" + encode("data 4")));
    }

    @Test
    public void createContactList() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/contact/list/596584003";
        String expectedJson = getJsonPayload("/contacts/createContactList.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Contact contact1 = new Contact();
        contact1.setFirstName("Alice");
        contact1.setLastName("Moore");
        contact1.setHomePhone("111");
        contact1.setWorkPhone("112");
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("A", "data1");
        attrs.put("B", "data2");
        contact1.setAttributes(attrs);
        Contact contact2 = new Contact();
        contact2.setFirstName("Bob");
        contact2.setLastName("Smith");
        contact2.setMobilePhone("444");

        CreateContactListRequest request = CreateContactListRequest.create()
            .name("Api contact list")
            .validateContacts(false)
            .contactIds(Arrays.asList(1L, 2L, 3L))
            .numbers(Arrays.asList("111", "222", "333"))
            .numbersField(NumbersField.MOBILE_PHONE)
            .contacts(Arrays.asList(contact1, contact2))
            .csvFile(new File("src/test/resources/contacts.csv"))
            .build();
        Long id = client.contactsApi().createContactList(request);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpMultipartEntity(arg);
        System.out.println(requestBody);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/contact/list.json"));
        assertThat(requestBody, containsString("name=\"Name\"\r\n\r\nApi contact list"));
        assertThat(requestBody, containsString("name=\"Validate\"\r\n\r\nfalse"));
        assertThat(requestBody, containsString("name=\"ContactId\"\r\n\r\n1"));
        assertThat(requestBody, containsString("name=\"ContactId\"\r\n\r\n2"));
        assertThat(requestBody, containsString("name=\"ContactId\"\r\n\r\n3"));
        assertThat(requestBody, containsString("name=\"Numbers\"\r\n\r\n111 222 333"));
        assertThat(requestBody, containsString("name=\"Numbers[fieldName]\"\r\n\r\nmobilePhone"));
        assertThat(requestBody, containsString("name=\"Contact[0][firstName]\"\r\n\r\nAlice"));
        assertThat(requestBody, containsString("name=\"Contact[0][lastName]\"\r\n\r\nMoore"));
        assertThat(requestBody, containsString("name=\"Contact[0][homePhone]\"\r\n\r\n111"));
        assertThat(requestBody, containsString("name=\"Contact[0][workPhone]\"\r\n\r\n112"));
        assertThat(requestBody, containsString("name=\"Contact[0][A]\"\r\n\r\ndata1"));
        assertThat(requestBody, containsString("name=\"Contact[0][B]\"\r\n\r\ndata2"));
        assertThat(requestBody, containsString("name=\"Contact[1][firstName]\"\r\n\r\nBob"));
        assertThat(requestBody, containsString("name=\"Contact[1][lastName]\"\r\n\r\nSmith"));
        assertThat(requestBody, containsString("name=\"Contact[1][mobilePhone]\"\r\n\r\n444"));
        assertThat(requestBody, containsString("name=\"File\"; filename=\"contacts.csv\""));
    }

    @Test
    public void addContacts() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        Contact contact1 = new Contact();
        contact1.setFirstName("Alice");
        contact1.setLastName("Moore");
        contact1.setHomePhone("111");
        contact1.setWorkPhone("112");
        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("A", "data1");
        attrs.put("B", "data2");
        contact1.setAttributes(attrs);
        Contact contact2 = new Contact();
        contact2.setFirstName("Bob");
        contact2.setLastName("Smith");
        contact2.setMobilePhone("444");

        AddContactsRequest request = AddContactsRequest.create()
            .contactListId(5L)
            .validateContacts(false)
            .contactIds(Arrays.asList(1L, 2L, 3L))
            .numbers(Arrays.asList("111", "222", "333"))
            .numbersField(NumbersField.MOBILE_PHONE)
            .contacts(Arrays.asList(contact1, contact2))
            .csvFile(new File("src/test/resources/contacts.csv"))
            .build();
        client.contactsApi().addContacts(request);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpMultipartEntity(arg);
        System.out.println(requestBody);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/contact/list/5/add.json"));
        assertThat(requestBody, containsString("name=\"Validate\"\r\n\r\nfalse"));
        assertThat(requestBody, containsString("name=\"ContactId\"\r\n\r\n1"));
        assertThat(requestBody, containsString("name=\"ContactId\"\r\n\r\n2"));
        assertThat(requestBody, containsString("name=\"ContactId\"\r\n\r\n3"));
        assertThat(requestBody, containsString("name=\"Numbers\"\r\n\r\n111 222 333"));
        assertThat(requestBody, containsString("name=\"Numbers[fieldName]\"\r\n\r\nmobilePhone"));
        assertThat(requestBody, containsString("name=\"Contact[0][firstName]\"\r\n\r\nAlice"));
        assertThat(requestBody, containsString("name=\"Contact[0][lastName]\"\r\n\r\nMoore"));
        assertThat(requestBody, containsString("name=\"Contact[0][homePhone]\"\r\n\r\n111"));
        assertThat(requestBody, containsString("name=\"Contact[0][workPhone]\"\r\n\r\n112"));
        assertThat(requestBody, containsString("name=\"Contact[0][A]\"\r\n\r\ndata1"));
        assertThat(requestBody, containsString("name=\"Contact[0][B]\"\r\n\r\ndata2"));
        assertThat(requestBody, containsString("name=\"Contact[1][firstName]\"\r\n\r\nBob"));
        assertThat(requestBody, containsString("name=\"Contact[1][lastName]\"\r\n\r\nSmith"));
        assertThat(requestBody, containsString("name=\"Contact[1][mobilePhone]\"\r\n\r\n444"));
        assertThat(requestBody, containsString("name=\"File\"; filename=\"contacts.csv\""));
    }

    @Test
    public void removeContacts() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        RemoveContactsRequest request = RemoveContactsRequest.create()
            .contactListId(5L)
            .contactIds(Arrays.asList(5L, 6L, 7L))
            .numbers(Arrays.asList("123", "456", "789"))
            .build();
        client.contactsApi().removeContacts(request);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/contact/list/5/remove.json"));
        assertThat(requestBody, containsString("ContactId=5"));
        assertThat(requestBody, containsString("ContactId=6"));
        assertThat(requestBody, containsString("ContactId=7"));
        assertThat(requestBody, containsString("Numbers=123"));
        assertThat(requestBody, containsString("Numbers=456"));
        assertThat(requestBody, containsString("Numbers=789"));
    }

    @Test
    public void queryContactLists() throws Exception {
        String expectedJson = getJsonPayload("/contacts/queryContactLists.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryRequest request = QueryRequest.createSimpleQuery()
            .firstResult(2)
            .maxResults(100)
            .build();
        List<ContactList> contactLists = client.contactsApi().queryContactLists(request);
        ResourceList<ContactList> response = new ResourceList<ContactList>(contactLists, ContactList.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=2"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
    }

    @Test
    public void getContactList() throws Exception {
        String expectedJson = getJsonPayload("/contacts/getContactList.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        ContactList contactList = client.contactsApi().getContactList(1234567L);
        Resource<ContactList> response = new Resource<ContactList>(contactList, ContactList.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/contact/list/1234567.json"));
    }

    @Test
    public void deleteContactList() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();
        client.contactsApi().deleteContactList(5L);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpDelete.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);
        assertThat(arg.getURI().toString(), containsString("/contact/list/5.json"));
    }
}
