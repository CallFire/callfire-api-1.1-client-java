package com.callfire.api11.client.api.common.model.request;

import com.callfire.api11.client.api.common.model.ActionState;

import java.util.Date;
import java.util.List;

/**
 * Generic query request for fetching calls and texts entities
 */
public abstract class ActionQueryRequest extends QueryRequest {
    protected Long broadcastId;
    protected Long batchId;
    protected Boolean inbound;
    protected Date intervalBegin;
    protected Date intervalEnd;
    protected String fromNumber;
    protected String toNumber;
    protected String labelName;
    protected List<ActionState> state;

    public Long getBroadcastId() {
        return broadcastId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public List<ActionState> getState() {
        return state;
    }

    public Boolean getInbound() {
        return inbound;
    }

    public Date getIntervalBegin() {
        return intervalBegin;
    }

    public Date getIntervalEnd() {
        return intervalEnd;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public String getLabelName() {
        return labelName;
    }

    @SuppressWarnings("unchecked")
    public abstract static class ActionQueryRequestBuilder<B extends ActionQueryRequestBuilder,
        R extends ActionQueryRequest> extends QueryRequestBuilder<B, R> {

        public ActionQueryRequestBuilder(R request) {
            super(request);
        }

        /**
         * Set broadcastId to query on
         *
         * @param broadcastId broadcastId to query on
         * @return builder self reference
         */
        public B broadcastId(long broadcastId) {
            request.broadcastId = broadcastId;
            return (B) this;
        }

        /**
         * Set batchId to query on
         *
         * @param batchId batchId to query on
         * @return builder self reference
         */
        public B batchId(long batchId) {
            request.batchId = batchId;
            return (B) this;
        }

        /**
         * Set is call inbound
         *
         * @param inbound is call inbound
         * @return builder self reference
         */
        public B inbound(boolean inbound) {
            request.inbound = inbound;
            return (B) this;
        }

        /**
         * Set beginning of DateTime interval to search on
         *
         * @param intervalBegin beginning of DateTime interval to search on
         * @return builder self reference
         */
        public B intervalBegin(Date intervalBegin) {
            request.intervalBegin = intervalBegin;
            return (B) this;
        }

        /**
         * Set end of DateTime interval to search on
         *
         * @param intervalEnd end of DateTime interval to search on
         * @return builder self reference
         */
        public B intervalEnd(Date intervalEnd) {
            request.intervalEnd = intervalEnd;
            return (B) this;
        }

        /**
         * Set fromNumber to search on. E.164 11 digit number format
         *
         * @param fromNumber fromNumber to search on.
         * @return builder self reference
         */
        public B fromNumber(String fromNumber) {
            request.fromNumber = fromNumber;
            return (B) this;
        }

        /**
         * Set toNumber to search on. E.164 11 digit number format
         *
         * @param toNumber toNumber to search on.
         * @return builder self reference
         */
        public B toNumber(String toNumber) {
            request.toNumber = toNumber;
            return (B) this;
        }

        /**
         * Set label that result must have to be included
         *
         * @param labelName label that result must have to be included
         * @return builder self reference
         */
        public B labelName(String labelName) {
            request.labelName = labelName;
            return (B) this;
        }

        /**
         * Set list of Action States to query on
         *
         * @param states list of Action States to query on
         * @return builder self reference
         */
        public B states(List<ActionState> states) {
            request.state = states;
            return (B) this;
        }
    }
}
