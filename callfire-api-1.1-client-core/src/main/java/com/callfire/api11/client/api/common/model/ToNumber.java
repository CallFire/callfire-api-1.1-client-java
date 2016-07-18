package com.callfire.api11.client.api.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model represents phone number with associated key=value parameters
 */
public class ToNumber {
    private String value;
    private Map<String, String> attributes = new HashMap<String, String>();

    public ToNumber() {
    }

    public ToNumber(String value) {
        this.value = value;
    }

    public ToNumber(String value, Map<String, String> attributes) {
        this.value = value;
        this.attributes = attributes;
    }

    public static Map<String, String> attributes(String... values) {
        Map<String, String> attributes = new HashMap<String, String>();
        Validate.isTrue(values.length % 2 == 0, "The number of keys should correspond to a number of values");
        for (int i = 0; i < values.length; i += 2) {
            attributes.put(values[i], values[i + 1]);
        }
        return attributes;
    }

    public static List<ToNumber> asList(List<String> phoneNumbers) {
        return asList(phoneNumbers.toArray(new String[] {}));
    }

    public static List<ToNumber> asList(String... phoneNumbers) {
        List<ToNumber> numbers = new ArrayList<ToNumber>(phoneNumbers.length);
        for (String phoneNumber : phoneNumbers) {
            numbers.add(new ToNumber(phoneNumber));
        }
        return numbers;
    }

    @JsonProperty("value")
    public String toQueryString() {
        StringBuilder sb = new StringBuilder(value);
        if (!attributes.isEmpty()) {
            sb.append("?");
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Get phone number
     *
     * @return phone number
     */
    public String getValue() {
        return value;
    }

    /**
     * Set phone number
     *
     * @param value phone number
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get custom attributes
     *
     * @return query string like parameters
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Set custom attributes
     *
     * @param attributes custom parameters string
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("value", value)
            .append("attributes", attributes)
            .toString();
    }
}
