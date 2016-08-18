package com.callfire.api11.client.api.ccc.model;

import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Question extends CfApi11Model {
    private static final int PROPERTIES_COUNT = 3;

    private String label;
    private QuestionResponseType responseType;
    private List<String> choices;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public QuestionResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(QuestionResponseType responseType) {
        this.responseType = responseType;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("label", label)
            .append("responseType", responseType)
            .append("choices", choices)
            .toString();
    }

    public List<NameValuePair> serializeToMap() {
        return serializeToMap(0);
    }

    public List<NameValuePair> serializeToMap(int index) {
        List<NameValuePair> params = new ArrayList<>(PROPERTIES_COUNT);
        String prefix = String.format("Question[%d]", index);
        if (label != null) {
            params.add(new BasicNameValuePair(prefix + "[label]", label));
        }
        if (responseType != null) {
            params.add(new BasicNameValuePair(prefix + "[responseType]", responseType.toString()));
        }
        if (choices != null) {
            params.add(new BasicNameValuePair(prefix + "[choices]", StringUtils.join(choices, " ")));
        }
        return params;
    }
}
