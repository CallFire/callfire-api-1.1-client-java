package com.callfire.api11.client.api.calls.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class CccBroadcastConfig extends CallBroadcastConfig {
    private Long agentGroupId;
    private Long smartDropSoundId;
    private Long scriptId;
    private List<Long> transferNumberIdList;
    private Boolean allowAnyTransfer;
    private String transferCallerId;
    private Boolean recorded;

    public Long getAgentGroupId() {
        return agentGroupId;
    }

    public void setAgentGroupId(Long agentGroupId) {
        this.agentGroupId = agentGroupId;
    }

    public Long getSmartDropSoundId() {
        return smartDropSoundId;
    }

    public void setSmartDropSoundId(Long smartDropSoundId) {
        this.smartDropSoundId = smartDropSoundId;
    }

    public Long getScriptId() {
        return scriptId;
    }

    public void setScriptId(Long scriptId) {
        this.scriptId = scriptId;
    }

    public List<Long> getTransferNumberIdList() {
        return transferNumberIdList;
    }

    public void setTransferNumberIdList(List<Long> transferNumberIdList) {
        this.transferNumberIdList = transferNumberIdList;
    }

    public Boolean getAllowAnyTransfer() {
        return allowAnyTransfer;
    }

    public void setAllowAnyTransfer(Boolean allowAnyTransfer) {
        this.allowAnyTransfer = allowAnyTransfer;
    }

    public String getTransferCallerId() {
        return transferCallerId;
    }

    public void setTransferCallerId(String transferCallerId) {
        this.transferCallerId = transferCallerId;
    }

    public Boolean getRecorded() {
        return recorded;
    }

    public void setRecorded(Boolean recorded) {
        this.recorded = recorded;
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static CccBroadcastConfigBuilder create() {
        return new CccBroadcastConfigBuilder();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("agentGroupId", agentGroupId)
            .append("smartDropSoundId", smartDropSoundId)
            .append("scriptId", scriptId)
            .append("transferNumberIdList", transferNumberIdList)
            .append("allowAnyTransfer", allowAnyTransfer)
            .append("transferCallerId", transferCallerId)
            .append("recorded", recorded)
            .toString();
    }

    public static class CccBroadcastConfigBuilder
        extends BroadcastConfigBuilder<CccBroadcastConfigBuilder, CccBroadcastConfig> {
        public CccBroadcastConfigBuilder() {
            super(new CccBroadcastConfig());
        }

        /**
         * Set Unique ID of AgentGroup
         *
         * @param agentGroupId AgentGroup ID Long
         * @return builder self reference
         */
        public CccBroadcastConfigBuilder agentGroupId(Long agentGroupId) {
            request.agentGroupId = agentGroupId;
            return this;
        }

        /**
         * Set Unique ID of AgentGroup
         *
         * @param smartDropSoundId SmartDropSound ID Long
         * @return builder self reference
         */
        public CccBroadcastConfigBuilder smartDropSoundId(Long smartDropSoundId) {
            request.smartDropSoundId = smartDropSoundId;
            return this;
        }

        /**
         * Set Unique ID of Script
         *
         * @param scriptId Script ID Long
         * @return builder self reference
         */
        public CccBroadcastConfigBuilder scriptId(Long scriptId) {
            request.scriptId = scriptId;
            return this;
        }

        /**
         * Set ID List of TransferNumbers
         *
         * @param transferNumberIdList List of Long values
         * @return builder self reference
         */
        public CccBroadcastConfigBuilder transferNumberIdList(List<Long> transferNumberIdList) {
            request.transferNumberIdList = transferNumberIdList;
            return this;
        }

        /**
         * Set any transfer flag
         *
         * @param allowAnyTransfer Boolean
         * @return builder self reference
         */
        public CccBroadcastConfigBuilder allowAnyTransfer(Boolean allowAnyTransfer) {
            request.allowAnyTransfer = allowAnyTransfer;
            return this;
        }

        /**
         * Set Transfer caller Id
         *
         * @param transferCallerId String
         * @return builder self reference
         */
        public CccBroadcastConfigBuilder transferCallerId(String transferCallerId) {
            request.transferCallerId = transferCallerId;
            return this;
        }

        /**
         * Set Recorded
         *
         * @param recorded Boolean
         * @return builder self reference
         */
        public CccBroadcastConfigBuilder recorded(Boolean recorded) {
            request.recorded = recorded;
            return this;
        }

    }
}
