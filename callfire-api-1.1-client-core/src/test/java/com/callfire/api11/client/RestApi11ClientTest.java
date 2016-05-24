package com.callfire.api11.client;

import com.callfire.api11.client.api.AbstractApiTest;
import com.callfire.api11.client.auth.BasicAuth;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class RestApi11ClientTest extends AbstractApiTest {
    private RestApi11Client client = new RestApi11Client(new BasicAuth("", ""));
    private String expectedJson = getJsonPayload("/common/errorResponse.json");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        client.setHttpClient(mockHttpClient);
    }

    @Test
    public void expectBadRequestWhen400() throws Exception {
        mockHttpResponse(expectedJson, 400);

        String msg = "TriggerEvent is required";
        ex.expect(BadRequestException.class);
        ex.expectMessage(msg);

        client.delete("/");
    }

    @Test(expected = UnauthorizedException.class)
    public void expectUnauthorizedWhen401() throws Exception {
        mockHttpResponse(expectedJson, 401);
        client.delete("/");
    }

    @Test(expected = AccessForbiddenException.class)
    public void expectAccessForbiddenWhen403() throws Exception {
        mockHttpResponse(expectedJson, 403);
        client.delete("/");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void expectResourceNotFoundWhen404() throws Exception {
        mockHttpResponse(expectedJson, 404);
        client.delete("/");
    }

    @Test(expected = InternalServerErrorException.class)
    public void expectInternalServerErrorWhen500() throws Exception {
        mockHttpResponse(expectedJson, 500);
        client.delete("/");
    }

    @Test(expected = CfApi11ApiException.class)
    public void expectCallfireApiExceptionInOtherCodeReturned() throws Exception {
        mockHttpResponse(expectedJson, 499);
        client.delete("/");
    }
}
