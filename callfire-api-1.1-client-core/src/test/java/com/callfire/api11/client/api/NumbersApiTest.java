package com.callfire.api11.client.api;

import com.callfire.api11.client.api.common.model.Resource;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.common.model.request.QueryRequest;
import com.callfire.api11.client.api.numbers.model.CallTrackingConfig;
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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.List;

import static com.callfire.api11.client.test.CallfireTestUtils.extractHttpEntity;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class NumbersApiTest extends AbstractApiTest {

    @Test
    public void order() throws Exception {
        String location = "https://www.callfire.com/api/1.1/rest/number/order/1528630003";
        String expectedJson = getJsonPayload("/numbers/order.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        CreateOrderRequest request = CreateOrderRequest.create()
            .numbers(Arrays.asList("12345678800", "12345678900"))
            .keywords(Arrays.asList("SUN", "MOON"))
            .localCount(3)
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
            .tollFreeCount(3)
            .build();
        Long id = client.numbersApi().order(request);
        ResourceReference response = new ResourceReference(id, location);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPost.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);

        assertThat(requestBody, containsString("Numbers=" + encode("12345678800 12345678900")));
        assertThat(requestBody, containsString("Keywords=" + encode("SUN MOON")));
        assertThat(requestBody, containsString("localCount=3"));
        assertThat(requestBody, containsString("Prefix=888"));
        assertThat(requestBody, containsString("City=" + encode("LOS ANGELES")));
        assertThat(requestBody, containsString("State=CA"));
        assertThat(requestBody, containsString("Zipcode=11111"));
        assertThat(requestBody, containsString("Country=USA"));
        assertThat(requestBody, containsString("Lata=12345"));
        assertThat(requestBody, containsString("RateCenter=R_CENTER"));
        assertThat(requestBody, containsString("Latitude=10.1"));
        assertThat(requestBody, containsString("Longitude=20.2"));
        assertThat(requestBody, containsString("TimeZone=" + encode("America/Los_Angeles")));
        assertThat(requestBody, containsString("tollFreeCount=3"));
    }

    @Test
    public void release() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        client.numbersApi().release("1234567890", "SUN");

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/number/release.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString("Number=1234567890"));
        assertThat(requestBody, containsString("Keyword=SUN"));
    }

    @Test
    public void getOrder() throws Exception {
        String expectedJson = getJsonPayload("/numbers/getOrder.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        NumberOrder order = client.numbersApi().getOrder(1234567L);
        Resource<NumberOrder> response = new Resource<>(order, NumberOrder.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/number/order/1234567.json"));
    }

    @Test
    public void queryRegions() throws Exception {
        String expectedJson = getJsonPayload("/numbers/queryRegions.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

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
        ResourceList<Region> response = new ResourceList<>(regions, Region.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

//        assertThat(arg.getURI().toString(), containsString("FirstResult=2"));
//        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
        assertThat(arg.getURI().toString(), containsString("Prefix=888"));
        assertThat(arg.getURI().toString(), containsString("City=" + encode("LOS ANGELES")));
        assertThat(arg.getURI().toString(), containsString("State=CA"));
        assertThat(arg.getURI().toString(), containsString("Zipcode=11111"));
        assertThat(arg.getURI().toString(), containsString("Country=USA"));
        assertThat(arg.getURI().toString(), containsString("Lata=12345"));
        assertThat(arg.getURI().toString(), containsString("RateCenter=R_CENTER"));
        assertThat(arg.getURI().toString(), containsString("Latitude=10.1"));
        assertThat(arg.getURI().toString(), containsString("Longitude=20.2"));
        assertThat(arg.getURI().toString(), containsString("TimeZone=" + encode("America/Los_Angeles")));
    }

    @Test
    public void query() throws Exception {
        String expectedJson = getJsonPayload("/numbers/query.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

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
            .label("Label")
            .firstResult(1)
            .maxResults(100)
            .build();
        List<Number> regions = client.numbersApi().query(request);
        ResourceList<Number> response = new ResourceList<>(regions, Number.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=1"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
        assertThat(arg.getURI().toString(), containsString("LabelName=Label"));
        assertThat(arg.getURI().toString(), containsString("Prefix=888"));
        assertThat(arg.getURI().toString(), containsString("City=" + encode("LOS ANGELES")));
        assertThat(arg.getURI().toString(), containsString("State=CA"));
        assertThat(arg.getURI().toString(), containsString("Zipcode=11111"));
        assertThat(arg.getURI().toString(), containsString("Country=USA"));
        assertThat(arg.getURI().toString(), containsString("Lata=12345"));
        assertThat(arg.getURI().toString(), containsString("RateCenter=R_CENTER"));
        assertThat(arg.getURI().toString(), containsString("Latitude=10.1"));
        assertThat(arg.getURI().toString(), containsString("Longitude=20.2"));
        assertThat(arg.getURI().toString(), containsString("TimeZone=" + encode("America/Los_Angeles")));
    }

    @Test
    public void search() throws Exception {
        String expectedJson = getJsonPayload("/numbers/search.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

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
        ResourceList<Number> response = new ResourceList<>(numbers, Number.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=1"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
        assertThat(arg.getURI().toString(), containsString("Prefix=888"));
        assertThat(arg.getURI().toString(), containsString("City=" + encode("LOS ANGELES")));
        assertThat(arg.getURI().toString(), containsString("State=CA"));
        assertThat(arg.getURI().toString(), containsString("Zipcode=11111"));
        assertThat(arg.getURI().toString(), containsString("Country=USA"));
        assertThat(arg.getURI().toString(), containsString("Lata=12345"));
        assertThat(arg.getURI().toString(), containsString("RateCenter=R_CENTER"));
        assertThat(arg.getURI().toString(), containsString("Latitude=10.1"));
        assertThat(arg.getURI().toString(), containsString("Longitude=20.2"));
        assertThat(arg.getURI().toString(), containsString("TimeZone=" + encode("America/Los_Angeles")));
        assertThat(arg.getURI().toString(), containsString("TollFree=true"));
        assertThat(arg.getURI().toString(), containsString("Count=5"));
    }

    @Test
    public void get() throws Exception {
        String expectedJson = getJsonPayload("/numbers/get.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        Number number = client.numbersApi().get("1234567890");
        Resource<Number> response = new Resource<>(number, Number.class);
        JSONAssert.assertEquals(expectedJson, jsonConverter.serialize(response), true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("/number/1234567890.json"));
    }

    @Test
    public void queryKeywords() throws Exception {
        String expectedJson = getJsonPayload("/numbers/queryKeywords.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        QueryRequest request = QueryRequest.createSimpleQuery()
            .firstResult(1)
            .maxResults(100)
            .build();
        List<Keyword> keywords = client.numbersApi().queryKeywords(request);
        ResourceList<Keyword> response = new ResourceList<>(keywords, Keyword.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("FirstResult=1"));
        assertThat(arg.getURI().toString(), containsString("MaxResults=100"));
    }

    @Test
    public void searchKeywords() throws Exception {
        String expectedJson = getJsonPayload("/numbers/searchKeywords.json");
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse(expectedJson);

        List<Keyword> keywords = client.numbersApi().searchKeywords(Arrays.asList("SUN", "MOON"));
        ResourceList<Keyword> response = new ResourceList<>(keywords, Keyword.class);
        String serialize = jsonConverter.serialize(response);
        System.out.println(serialize);
        JSONAssert.assertEquals(expectedJson, serialize, true);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpGet.METHOD_NAME, arg.getMethod());
        String requestBody = extractHttpEntity(arg);
        assertNull(requestBody);

        assertThat(arg.getURI().toString(), containsString("Keywords=" + encode("SUN MOON")));
    }

    @Test
    public void configure() throws Exception {
        ArgumentCaptor<HttpUriRequest> captor = mockHttpResponse();

        CallTrackingConfig trackingConfig = new CallTrackingConfig();
        trackingConfig.setIntroSoundId(22L);
        trackingConfig.setWhisperSoundId(33L);
        trackingConfig.setRecord(true);
        trackingConfig.setScreen(true);
        trackingConfig.setTransferNumber(Arrays.asList("12345678800", "12345678900"));

        ConfigureNumberRequest request = ConfigureNumberRequest.create()
            .number("1234567890")
            .callFeature(NumberFeature.ENABLED)
            .textFeature(NumberFeature.ENABLED)
            .callTrackingConfig(trackingConfig)
            .build();
        client.numbersApi().configure(request);

        HttpUriRequest arg = captor.getValue();
        assertEquals(HttpPut.METHOD_NAME, arg.getMethod());

        assertThat(arg.getURI().toString(), containsString("/number/1234567890.json"));

        String requestBody = extractHttpEntity(arg);
        assertNotNull(requestBody);
        assertThat(requestBody, containsString("CallFeature=ENABLED"));
        assertThat(requestBody, containsString("TextFeature=ENABLED"));
        assertThat(requestBody, containsString("InboundCallConfigurationType=TRACKING"));
        assertThat(requestBody, containsString("TransferNumber=" + encode("12345678800 12345678900")));
        assertThat(requestBody, containsString("Screen=true"));
        assertThat(requestBody, containsString("Record=true"));
        assertThat(requestBody, containsString("IntroSoundId=22"));
        assertThat(requestBody, containsString("WhisperSoundId=33"));
    }
}
