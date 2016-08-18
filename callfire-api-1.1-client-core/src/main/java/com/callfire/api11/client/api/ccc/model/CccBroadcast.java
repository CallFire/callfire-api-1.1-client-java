package com.callfire.api11.client.api.ccc.model;

import com.callfire.api11.client.api.broadcasts.model.BroadcastStatus;
import com.callfire.api11.client.api.common.QueryParamFormat;
import com.callfire.api11.client.api.common.QueryParamName;
import com.callfire.api11.client.api.common.QueryParamObject;
import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.callfire.api11.client.api.common.model.RetryConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

import static com.callfire.api11.client.ClientConstants.TIME_FORMAT_PATTERN;
import static com.callfire.api11.client.ClientConstants.ZONED_TIME_FORMAT_PATTERN;

/**
 * Represents ccc broadcast object in Callfire system
 */
public class CccBroadcast extends CfApi11Model {

    @JsonProperty("@id")
    private Long id;
    private String name;
    @QueryParamObject
    private RetryConfig retryConfig;
    private BroadcastStatus status;
    private String fromNumber;
    private Date lastModified;
    private Date created;
    private Date configUpdated;
    @QueryParamName("LocalRestrictBegin")
    @JsonProperty("LocalRestrictBegin")
    @QueryParamFormat(pattern = TIME_FORMAT_PATTERN)
    @JsonFormat(pattern = ZONED_TIME_FORMAT_PATTERN)
    private Date beginTime;
    @QueryParamName("LocalRestrictEnd")
    @JsonProperty("LocalRestrictEnd")
    @QueryParamFormat(pattern = TIME_FORMAT_PATTERN)
    @JsonFormat(pattern = ZONED_TIME_FORMAT_PATTERN)
    private Date endTime;
    private String script;
    @JsonProperty("Question")
    @QueryParamName("Question")
    private List<Question> questions;
    @JsonProperty("TransferNumber")
    @QueryParamName("TransferNumber")
    private List<TransferNumber> transferNumbers;
    private Long agentGroupId;
    private String agentGroupName;
    private Long smartDropSoundId;
    private String smartDropSoundRef;
    private Boolean allowAnyTransfer;
    private String transferCallerId;
    private Boolean recorded;
    private Integer multilineDialingRatio;
    private Boolean multilineDialingEnabled;
    private Integer scrubLevel;

    public String getFromNumber() {
        return fromNumber;
    }

    public RetryConfig getRetryConfig() {
        return retryConfig;
    }

    public Long getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    public BroadcastStatus getStatus() {
        return status;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public Date getConfigUpdated() {
        return configUpdated;
    }

    public String getScript() {
        return script;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<TransferNumber> getTransferNumbers() {
        return transferNumbers;
    }

    public Long getAgentGroupId() {
        return agentGroupId;
    }

    public String getAgentGroupName() {
        return agentGroupName;
    }

    public Long getSmartDropSoundId() {
        return smartDropSoundId;
    }

    public String getSmartDropSoundRef() {
        return smartDropSoundRef;
    }

    public Boolean getAllowAnyTransfer() {
        return allowAnyTransfer;
    }

    public String getTransferCallerId() {
        return transferCallerId;
    }

    public Boolean getRecorded() {
        return recorded;
    }

    public Integer getMultilineDialingRatio() {
        return multilineDialingRatio;
    }

    public Boolean getMultilineDialingEnabled() {
        return multilineDialingEnabled;
    }

    public Integer getScrubLevel() {
        return scrubLevel;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public void setRetryConfig(RetryConfig retryConfig) {
        this.retryConfig = retryConfig;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setTransferNumbers(List<TransferNumber> transferNumbers) {
        this.transferNumbers = transferNumbers;
    }

    public void setAgentGroupId(Long agentGroupId) {
        this.agentGroupId = agentGroupId;
    }

    public void setAgentGroupName(String agentGroupName) {
        this.agentGroupName = agentGroupName;
    }

    public void setSmartDropSoundId(Long smartDropSoundId) {
        this.smartDropSoundId = smartDropSoundId;
    }

    public void setSmartDropSoundRef(String smartDropSoundRef) {
        this.smartDropSoundRef = smartDropSoundRef;
    }

    public void setAllowAnyTransfer(Boolean allowAnyTransfer) {
        this.allowAnyTransfer = allowAnyTransfer;
    }

    public void setTransferCallerId(String transferCallerId) {
        this.transferCallerId = transferCallerId;
    }

    public void setRecorded(Boolean recorded) {
        this.recorded = recorded;
    }

    public void setMultilineDialingRatio(Integer multilineDialingRatio) {
        this.multilineDialingRatio = multilineDialingRatio;
    }

    public void setMultilineDialingEnabled(Boolean multilineDialingEnabled) {
        this.multilineDialingEnabled = multilineDialingEnabled;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setScrubLevel(Integer scrubLevel) {
        this.scrubLevel = scrubLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("name", name)
            .append("status", status)
            .append("lastModified", lastModified)
            .append("fromNumber", fromNumber)
            .append("retryConfig", retryConfig)
            .append("beginTime", beginTime)
            .append("endTime", endTime)
            .append("created", created)
            .append("configUpdated", configUpdated)
            .append("script", script)
            .append("questions", questions)
            .append("transferNumbers", transferNumbers)
            .append("agentGroupId", agentGroupId)
            .append("agentGroupName", agentGroupName)
            .append("smartDropSoundId", smartDropSoundId)
            .append("smartDropSoundRef", smartDropSoundRef)
            .append("allowAnyTransfer", allowAnyTransfer)
            .append("transferCallerId", transferCallerId)
            .append("multilineDialingRatio", multilineDialingRatio)
            .append("multilineDialingEnabled", multilineDialingEnabled)
            .append("scrubLevel", scrubLevel)
            .toString();
    }
}
