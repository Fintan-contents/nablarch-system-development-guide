package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.test.ClimanRestTestSupport;
import nablarch.core.date.SystemTimeUtil;
import nablarch.core.util.StringUtil;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.RestMockHttpRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.junit.Test;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

/**
 * {@link ClientAction} test class.
 */
public class ClientActionTest extends ClimanRestTestSupport {
    /**
     * Request path to be the test target
     */
    public static final String PATH = "/client";

    /** Invalid client name (more than 128 characters) */
    public static final String INVALID_CLIENT_NAME = RandomStringUtils.randomAlphanumeric(129);
    /** Incorrect industry code (does not exist in the code master) */
    public static final String INVALID_INDUSTRY_CODE = "04";

    /**
     * When there is a validation error for a request parameter, a status of 400 and an error message should be returned.
     */
    @Test
    public void testInvalidClientSearchForm() {
        RestMockHttpRequest request = get(PATH);
        request.setParam("clientName", INVALID_CLIENT_NAME);
        request.setParam("industryCode", INVALID_INDUSTRY_CODE);

        String message = "Get client list (invalid parameter)";
        HttpResponse response = sendRequest(request);
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:Enter a value up to 128 characters.","industryCode:Invalid value has been specified.");
    }

    /**
     * When executing the acquisition of the list of clients without any conditions, all the clients should be acquired.
     */
    @Test
    public void testFindAll() throws JSONException {
        String message = "Get all client list";
        HttpResponse response = sendRequest(get(PATH));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        assertJsonEquals(message, response, "client-list.json");
    }

    /**
     * When retrieving a list of clients with the condition that the search result is 0, a normal response should be returned.
     */
    @Test
    public void testFindNoClients() {
        String message = "Search results 0";
        HttpResponse response = sendRequest(get(PATH + "?clientName=does_not_exist"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[Number of results]");
    }

    /**
     * When the industry code and client name are included in the parameter at the same time,
     * only the clients matching the industry code and client name shall be retrieved.
     */
    @Test
    public void testFindByIndustryCodeAndClientName() {
        String message = "Search by industry code + client name";
        HttpResponse response = sendRequest(get(PATH + "?industryCode=01&clientName=Test%20Company%203"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1), message + "[Number of results]")
                .assertThat("$[0].client_id", equalTo(4), message + "[Client ID]")
                .assertThat("$[0].client_name", equalTo("Test Company 3 (Agriculture)"), message + "[Client name]")
                .assertThat("$[0].industry_code", equalTo("01"), message + "[Industry Code]");
    }

    /**
     * A normal response should be returned when the number of items retrieved from the client list is equal to the upper limit.
     */
    @Test
    public void testFindUnderUpperLimit() {
        String message = "upper boundary value";
        HttpResponse response = sendRequest(get(PATH));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1000), message + "[Number of results]");
    }

    /**
     * When the number of client list retrievals exceeds the limit, a status of 400 and an error message should be returned.
     */
    @Test
    public void testFindOverUpperLimit() {
        String message = "excess of the upper limit";
        HttpResponse response = sendRequest(get(PATH));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999902"
                , 1
                , "Search has exceeded the limit of 1,000 results. Narrow down your criteria.");
    }

    /**
     * When validation of the path parameter results in an error, a status of 400 and an error message should be returned.
     */
    @Test
    public void testInvalidClientGetForm() {
        String message = "Get client details (invalid parameter)";
        HttpResponse response = sendRequest(get(PATH + "/number"));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999901"
                , 1
                , "clientId:Enter a numeric value.");
    }

    /**
     * When a client ID is specified as a path parameter, only one client shall be retrieved.
     */
    @Test
    public void testShow() {
        String message = "Get client details";
        HttpResponse response = sendRequest(get(PATH + "/1"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$.client_id", equalTo(1), message + "[Client ID]")
                .assertThat("$.client_name", equalTo("Test Company 1 (Agriculture)"), message + "[Client Name]")
                .assertThat("$.industry_code", equalTo("01"), message + "[Industry Code]");
    }

    /**
     * When specifying a non-existent client ID, a 404 status and error message should be returned.
     */
    @Test
    public void testShowNotExistsClient() {
        String message = "Get non-existent client details";
        HttpResponse response = sendRequest(get(PATH + "/9999"));
        assertStatusCode(message, HttpResponse.Status.NOT_FOUND, response);
        assertFaultMessages(message, response
                , "FB1999903"
                , 1
                , "The specified data does not exist.");
    }

    /**
     * When the validation result of a request parameter is an error, a status of 400 and an error message should be returned.
     */
    @Test
    public void testInvalidClientForm() {
        ClientForm client = new ClientForm();
        client.setClientName(INVALID_CLIENT_NAME);
        client.setIndustryCode(INVALID_INDUSTRY_CODE);

        String message = "registration (invalid parameter)";
        HttpResponse response = sendRequest(post(PATH).setBody(client));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:Enter a value up to 128 characters.","industryCode:Invalid value has been specified.");
    }

    /**
     * When the required field is not included in the parameter, a status of 400 and an error message should be returned.
     */
    @Test
    public void testRegisterNoParameter() {
        ClientForm client = new ClientForm();

        String message = "Registration without required fields";
        HttpResponse response = sendRequest(post(PATH).setBody(client));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:Enter this.","industryCode:Enter this.");
    }

    /**
     * Ensure that the client registration.
     */
    @Test
    public void testRegisterClient() {
        String clientName = "NewTestingCompany" + SystemTimeUtil.getDateTimeMillisString();
        String industryCode = "03";

        ClientForm client = new ClientForm();
        client.setClientName(clientName);
        client.setIndustryCode(industryCode);

        String queryString = "?clientName=" + clientName;

        String beforeRegister = "The client whose name you are going to register does not exist";
        HttpResponse response = sendRequest(get(PATH + queryString));
        assertStatusCode(beforeRegister, HttpResponse.Status.OK, response);
        with(response.getBodyString()).assertThat("$", empty(), beforeRegister + "[Number of results]");

        String register = "Registration";
        HttpResponse registerResponse = sendRequest(post(PATH).setBody(client));
        assertStatusCode(register, HttpResponse.Status.CREATED, registerResponse);
        assertTrue(register + "[Response body]", StringUtil.isNullOrEmpty(registerResponse.getBodyString()));

        String afterRegister = "Retrieve the client with the client name registered earlier";
        HttpResponse afterRegisterResponse = sendRequest(get(PATH + queryString));
        assertStatusCode(afterRegister, HttpResponse.Status.OK, afterRegisterResponse);
        with(afterRegisterResponse.getBodyString())
                .assertThat("$", hasSize(1), afterRegister + "[Number of results]")
                .assertThat("$..client_name", hasItem(clientName), afterRegister + "[Client Name]")
                .assertThat("$..industry_code", hasItem(industryCode), afterRegister + "[Industry Code]");
    }

    /**
     * When duplicate client names are specified, a 409 status and error message should be returned.
     */
    @Test
    public void testRegisterDuplicatedClient() {
        String findMessage = "Search by client name";
        HttpResponse findResponse = sendRequest(get(PATH + "?clientName=Test%20Company%201%20(Agriculture)"));
        assertStatusCode(findMessage, HttpResponse.Status.OK, findResponse);
        with(findResponse.getBodyString())
                .assertThat("$", hasSize(1), findMessage + "[Number of results]");

        ClientForm client = new ClientForm();
        client.setClientName("Test Company 1 (Agriculture)");
        client.setIndustryCode("03");

        String registerMessage = "Registration";
        HttpResponse registerResponse = sendRequest(post(PATH).setBody(client));
        assertStatusCode(registerMessage, HttpResponse.Status.CONFLICT, registerResponse);
        assertFaultMessages(registerMessage, registerResponse
                , "FB1999904"
                , 1
                , "The specified data cannot be registered as it overlaps with existing data.");
    }
}