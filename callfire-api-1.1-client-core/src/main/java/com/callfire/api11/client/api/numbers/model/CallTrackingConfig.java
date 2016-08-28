package com.callfire.api11.client.api.numbers.model;

import com.callfire.api11.client.ListToStringSerializer;
import com.callfire.api11.client.StringToStringListDeserializer;
import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.SerializeAsSingleString;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Inbound call configuration model
 */
public class CallTrackingConfig {
    @JsonProperty("@id")
    @QueryParamName("CallTrackingConfig[id]")
    private Long id;
    @JsonSerialize(using = ListToStringSerializer.class)
    @JsonDeserialize(using = StringToStringListDeserializer.class)
    @SerializeAsSingleString
    private List<String> transferNumber;
    private Boolean screen;
    private Boolean record;
    private Long introSoundId;
    private Long whisperSoundId;

    public Long getId() {
        return id;
    }

    public List<String> getTransferNumber() {
        return transferNumber;
    }

    public void setTransferNumber(List<String> transferNumber) {
        this.transferNumber = transferNumber;
    }

    public Boolean getScreen() {
        return screen;
    }

    public void setScreen(Boolean screen) {
        this.screen = screen;
    }

    public Boolean getRecord() {
        return record;
    }

    public void setRecord(Boolean record) {
        this.record = record;
    }

    public Long getIntroSoundId() {
        return introSoundId;
    }

    public void setIntroSoundId(Long introSoundId) {
        this.introSoundId = introSoundId;
    }

    public Long getWhisperSoundId() {
        return whisperSoundId;
    }

    public void setWhisperSoundId(Long whisperSoundId) {
        this.whisperSoundId = whisperSoundId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("transferNumber", transferNumber)
            .append("screen", screen)
            .append("record", record)
            .append("introSoundId", introSoundId)
            .append("whisperSoundId", whisperSoundId)
            .toString();
    }
}
