package com.callfire.api11.client.api.common.model.request;

import com.callfire.api11.client.api.common.QueryParamIgnore;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Request for querying any resources by id
 */
public class QueryByIdRequest extends QueryRequest {
    @QueryParamIgnore
    private Long id;

    private QueryByIdRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Returns contact id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .toString();
    }

    @SuppressWarnings("unchecked")
    public static class Builder extends QueryRequestBuilder<Builder, QueryByIdRequest> {

        public Builder() {
            super(new QueryByIdRequest());
        }

        /**
         * Set contact id
         *
         * @param id contact id
         * @return builder self reference
         */
        public Builder id(Long id) {
            request.id = id;
            return this;
        }

        @Override
        protected void validate() {
            Validate.notNull(request.id, "request.id cannot be null");
        }
    }
}
