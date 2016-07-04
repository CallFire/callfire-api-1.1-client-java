package com.callfire.api11.client.api.common.model.request;

import com.callfire.api11.client.api.common.model.AbstractBuilder;
import com.callfire.api11.client.api.common.model.CfApi11Model;

/**
 * Base query request
 */
public class QueryRequest extends CfApi11Model {
    protected int firstResult;
    protected int maxResults = 1000;

    protected QueryRequest() {
    }

    /**
     * Create base query request builder
     *
     * @return request build
     */
    public static QueryRequestBuilder createSimpleQuery() {
        return new QueryRequestBuilder<>();
    }

    @SuppressWarnings("unchecked")
    public static class QueryRequestBuilder<B extends QueryRequestBuilder, R extends QueryRequest>
        extends AbstractBuilder<R> {

        public QueryRequestBuilder() {
            super((R) new QueryRequest());
        }

        public QueryRequestBuilder(R request) {
            super(request);
        }

        /**
         * Set start of next result set (default: 0)
         *
         * @param firstResult start of next result set
         * @return builder self reference
         */
        public B firstResult(int firstResult) {
            request.firstResult = firstResult;
            return (B) this;
        }

        /**
         * Set max number of results to return limited to 1000 (default: 1000)
         *
         * @param maxResults max number of results to return
         * @return builder self reference
         */
        public B maxResults(int maxResults) {
            request.maxResults = maxResults;
            return (B) this;
        }

        @Override
        public R build() {
            return super.build();
        }
    }
}
