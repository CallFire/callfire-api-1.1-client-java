package com.callfire.api11.client.api.ccc.model;

import com.callfire.api11.client.api.common.model.CfApi11Model;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class TransferNumber extends CfApi11Model {

    private static final int PROPERTIES_COUNT = 3;

    private String name;
    private String number;
    private Boolean allowAssistedTransfer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getAllowAssistedTransfer() {
        return allowAssistedTransfer;
    }

    public void setAllowAssistedTransfer(Boolean allowAssistedTransfer) {
        this.allowAssistedTransfer = allowAssistedTransfer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("name", name)
            .append("number", number)
            .append("allowAssistedTransfer", allowAssistedTransfer)
            .toString();
    }

    public List<NameValuePair> serializeToMap() {
        return serializeToMap(0);
    }

    public List<NameValuePair> serializeToMap(int index) {
        List<NameValuePair> params = new ArrayList<>(PROPERTIES_COUNT);
        String prefix = String.format("TransferNumber[%d]", index);
        if (name != null) {
            params.add(new BasicNameValuePair(prefix + "[name]", name));
        }
        if (number != null) {
            params.add(new BasicNameValuePair(prefix + "[number]", number));
        }
        if (allowAssistedTransfer != null) {
            params.add(new BasicNameValuePair(prefix + "[allowAssistedTransfer]", allowAssistedTransfer.toString()));
        }
        return params;
    }
}
