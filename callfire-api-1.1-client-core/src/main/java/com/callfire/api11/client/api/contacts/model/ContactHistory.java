package com.callfire.api11.client.api.contacts.model;

import com.callfire.api11.client.api.calls.model.Call;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.texts.model.Text;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Object holds all calls and texts associated with a contact.
 */
public class ContactHistory extends CfApi11Model {
    @JsonProperty("Call")
    private List<Call> calls = new ArrayList<Call>();
    @JsonProperty("Text")
    private List<Text> texts = new ArrayList<Text>();

    public List<Call> getCalls() {
        return calls;
    }

    public List<Text> getTexts() {
        return texts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("calls", calls)
            .append("texts", texts)
            .toString();
    }
}
