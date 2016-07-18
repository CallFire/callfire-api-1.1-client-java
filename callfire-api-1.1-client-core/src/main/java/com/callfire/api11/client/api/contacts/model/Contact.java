package com.callfire.api11.client.api.contacts.model;

import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents contact object in Callfire system
 */
public class Contact extends CfApi11Model {
    private static final int PROPERTIES_COUNT = 9;

    @JsonProperty("@id")
    private Long id;
    @JsonProperty("@firstName")
    private String firstName;
    @JsonProperty("@lastName")
    private String lastName;
    @JsonProperty("@zipcode")
    private String zipcode;
    @JsonProperty("@homePhone")
    private String homePhone;
    @JsonProperty("@workPhone")
    private String workPhone;
    @JsonProperty("@mobilePhone")
    private String mobilePhone;
    @JsonProperty("@externalId")
    private String externalId;
    @JsonProperty("@externalSystem")
    private String externalSystem;
    @JsonIgnore
    private Map<String, String> attributes = new HashMap<String, String>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getExternalSystem() {
        return externalSystem;
    }

    @JsonAnyGetter
    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @JsonAnySetter
    protected void addCustomAttribute(String name, Object value) {
        name = StringUtils.removeStart(name, "@");
        attributes.put(name, value.toString());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("firstName", firstName)
            .append("lastName", lastName)
            .append("zipcode", zipcode)
            .append("homePhone", homePhone)
            .append("workPhone", workPhone)
            .append("mobilePhone", mobilePhone)
            .append("externalId", externalId)
            .append("externalSystem", externalSystem)
            .append("attributes", attributes)
            .toString();
    }

    public List<NameValuePair> serializeToMap() {
        return serializeToMap(0);
    }

    public List<NameValuePair> serializeToMap(int index) {
        List<NameValuePair> params = new ArrayList<NameValuePair>(PROPERTIES_COUNT + attributes.size());
        String prefix = String.format("Contact[%d]", index);
        if (id != null) {
            params.add(new BasicNameValuePair(prefix + "[id]", id.toString()));
        }
        if (firstName != null) {
            params.add(new BasicNameValuePair(prefix + "[firstName]", firstName));
        }
        if (lastName != null) {
            params.add(new BasicNameValuePair(prefix + "[lastName]", lastName));
        }
        if (zipcode != null) {
            params.add(new BasicNameValuePair(prefix + "[zipcode]", zipcode));
        }
        if (homePhone != null) {
            params.add(new BasicNameValuePair(prefix + "[homePhone]", homePhone));
        }
        if (workPhone != null) {
            params.add(new BasicNameValuePair(prefix + "[workPhone]", workPhone));
        }
        if (mobilePhone != null) {
            params.add(new BasicNameValuePair(prefix + "[mobilePhone]", mobilePhone));
        }
        if (externalId != null) {
            params.add(new BasicNameValuePair(prefix + "[externalId]", externalId));
        }
        if (externalSystem != null) {
            params.add(new BasicNameValuePair(prefix + "[externalSystem]", externalSystem));
        }
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String name = prefix + "[" + entry.getKey() + "]";
            params.add(new BasicNameValuePair(name, entry.getValue()));
        }
        return params;
    }
}
