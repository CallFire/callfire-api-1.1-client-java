package com.callfire.api11.client.integration;

import com.callfire.api11.client.ResourceNotFoundException;
import com.callfire.api11.client.api.common.model.Label;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.labels.LabelsApi;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class LabelsIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void crudOperations() throws Exception {
        LabelsApi api = client.labelsApi();

        api.labelNumber("14246528111", "number_label");
        api.labelBroadcast(12610243003L, "broadcast_label");

        List<Label> labels = api.query(QueryRequest.createSimpleQuery().build());
        assertEquals(2, labels.size());

        api.unlabelNumber("14246528111", "number_label");
        api.unlabelBroadcast(12610243003L, "broadcast_label");

        ex.expect(ResourceNotFoundException.class);
        ex.expectMessage("no label");
        api.unlabelNumber("14246528111", "number_label");

        ex.expect(ResourceNotFoundException.class);
        ex.expectMessage("no label");
        api.unlabelBroadcast(12610243003L, "broadcast_label");

        labels = api.query(QueryRequest.createSimpleQuery().build());
        assertEquals(2, labels.size());

        api.delete("number_label");
        api.delete("broadcast_label");

        ex.expect(ResourceNotFoundException.class);
        ex.expectMessage("no label");
        api.delete("number_label");
    }
}
