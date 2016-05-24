package com.callfire.api11.client.api.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class QuestionResponse {
    private String question;
    private String response;

    public String getQuestion() {
        return question;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("question", question)
            .append("response", response)
            .toString();
    }
}
