package com.callfire.api11.client.integration;

import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.numbers.model.IvrInboundConfig;
import com.callfire.api11.client.api.numbers.model.Keyword;
import com.callfire.api11.client.api.numbers.model.Number;
import com.callfire.api11.client.api.numbers.model.NumberFeature;
import com.callfire.api11.client.api.numbers.model.NumberOrder;
import com.callfire.api11.client.api.numbers.model.Region;
import com.callfire.api11.client.api.numbers.model.request.ConfigureNumberRequest;
import com.callfire.api11.client.api.numbers.model.request.CreateOrderRequest;
import com.callfire.api11.client.api.numbers.model.request.QueryNumbersRequest;
import com.callfire.api11.client.api.numbers.model.request.QueryRegionsRequest;
import com.callfire.api11.client.api.numbers.model.request.SearchNumbersRequest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Ignore
public class NumbersIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void queryRegions() throws Exception {
        QueryRegionsRequest request = QueryRegionsRequest.createRegionsQuery()
            .prefix("888")
            .city("LOS ANGELES")
            .state("CA")
            .zipcode("11111")
            .country("USA")
            .lata("12345")
            .rateCenter("R_CENTER")
            .latitude(10.1f)
            .longitude(20.2f)
            .timeZone("America/Los_Angeles")
            //                        .firstResult(1)
            //            .maxResults(100)
            .build();
        List<Region> regions = client.numbersApi().queryRegions(request);
        System.out.println("regions: " + regions);
    }

    @Test
    public void query() throws Exception {
        QueryNumbersRequest request = QueryNumbersRequest.create()
            .prefix("888")
            .city("LOS ANGELES")
            .state("CA")
            .zipcode("11111")
            .country("USA")
            .lata("12345")
            .rateCenter("R_CENTER")
            .latitude(10.1f)
            .longitude(20.2f)
            .timeZone("America/Los_Angeles")
            .firstResult(1)
            .maxResults(100)
            .build();
        List<Number> numbers = client.numbersApi().query(request);
        System.out.println("account numbers: " + numbers);
    }

    @Test
    public void get() throws Exception {
        Number number = client.numbersApi().get("14242700222");
        System.out.println("get number: " + number);
    }

    @Test
    public void configure() throws Exception {
        IvrInboundConfig config = new IvrInboundConfig();
        config.setDialplanXml("<dialplan name=\"Root\"><play type=\"tts\">Hello Callfire!</play></dialplan>");

        ConfigureNumberRequest request = ConfigureNumberRequest.create()
            .number("14242700222")
            .callFeature(NumberFeature.ENABLED)
            .textFeature(NumberFeature.ENABLED)
            .ivrInboundConfig(config)
            .build();

        client.numbersApi().configure(request);
    }

    @Test
    public void search() throws Exception {
        SearchNumbersRequest request = SearchNumbersRequest.create()
            .prefix("888")
            .city("LOS ANGELES")
            .state("CA")
            .zipcode("11111")
            .country("USA")
            .lata("12345")
            .rateCenter("R_CENTER")
            .latitude(10.1f)
            .longitude(20.2f)
            .timeZone("America/Los_Angeles")
            .tollFree(true)
            .count(5)
            .firstResult(1)
            .maxResults(100)
            .build();
        List<Number> numbers = client.numbersApi().search(request);
        System.out.println("search numbers: " + numbers);
    }

    @Test
    public void queryKeywords() throws Exception {
        QueryRequest request = QueryRequest.createSimpleQuery()
            .maxResults(100)
            .build();
        List<Keyword> keywords = client.numbersApi().queryKeywords(request);
        System.out.println("account keywords: " + keywords);
    }

    @Test
    public void searchKeywords() throws Exception {
        List<Keyword> keywords = client.numbersApi().searchKeywords(Arrays.asList("SUN", "MOON"));
        System.out.println("search keywords: " + keywords);
    }

    @Test
    public void order() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.create()
            .country("US")
            .build();
        Long id = client.numbersApi().order(request);
        System.out.println(id);
    }

    @Test
    public void release() throws Exception {
        client.numbersApi().release("14242700222", null);
    }

    @Test
    public void getOrder() throws Exception {
        NumberOrder order = client.numbersApi().getOrder(4169269003L);
        System.out.println(order);
    }
}
