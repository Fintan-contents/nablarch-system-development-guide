package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.test.ClimanRestTestExtension;
import com.nablarch.example.climan.test.ClimanRestTestSupport;
import nablarch.core.date.SystemTimeUtil;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.RestMockHttpRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * {@link ClientAction} test class.
 */
@ExtendWith(ClimanRestTestExtension.class)
class ClientActionTest {
    /**
     * Request path to be the test target
     */
    public static final String PATH = "/client";

    /** Invalid client name (more than 128 characters) */
    public static final String INVALID_CLIENT_NAME = RandomStringUtils.randomAlphanumeric(129);
    /** Incorrect industry code (does not exist in the code master) */
    public static final String INVALID_INDUSTRY_CODE = "04";

    ClimanRestTestSupport support;

    /**
     * When there is a validation error for a request parameter, a status of 400 and an error message should be returned.
     */
    @Test
    void testInvalidClientSearchForm() {
        RestMockHttpRequest request = support.get(PATH);
        request.setParam("clientName", INVALID_CLIENT_NAME);
        request.setParam("industryCode", INVALID_INDUSTRY_CODE);

        String message = "Get client list (invalid parameter)";
        HttpResponse response = support.sendRequest(request);
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:Enter a value up to 128 characters.","industryCode:Invalid value has been specified.");
    }

    /**
     * When executing the acquisition of the list of clients without any conditions, all the clients should be acquired.
     */
    @Test
    void testFindAll() throws JSONException {
        String message = "Get all client list";
        HttpResponse response = support.sendRequest(support.get(PATH));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        support.assertJsonEquals(message, response, "client-list.json");
    }

    /**
     * When retrieving a list of clients with the condition that the search result is 0, a normal response should be returned.
     */
    @Test
    void testFindNoClients() {
        String message = "Search results 0";
        HttpResponse response = support.sendRequest(support.get(PATH + "?clientName=does_not_exist"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(support.getBodyString(response))
                .assertThat("$", empty(), message + "[Number of results]");
    }

    /**
     * When the industry code and client name are included in the parameter at the same time,
     * only the clients matching the industry code and client name shall be retrieved.
     */
    @Test
    void testFindByIndustryCodeAndClientName() {
        String message = "Search by industry code + client name";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=01&clientName=Test%20Company%203"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(support.getBodyString(response))
                .assertThat("$", hasSize(1), message + "[Number of results]")
                .assertThat("$[0].clientId", equalTo(4), message + "[Client ID]")
                .assertThat("$[0].clientName", equalTo("Test Company 3 (Agriculture)"), message + "[Client name]")
                .assertThat("$[0].industryCode", equalTo("01"), message + "[Industry Code]");
    }

    /**
     * A normal response should be returned when the number of items retrieved from the client list is equal to the upper limit.
     */
    @Test
    void testFindUnderUpperLimit() {
        String message = "upper boundary value";
        HttpResponse response = support.sendRequest(support.get(PATH));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(support.getBodyString(response))
                .assertThat("$", hasSize(1000), message + "[Number of results]");
    }

    /**
     * When the number of client list retrievals exceeds the limit, a status of 400 and an error message should be returned.
     */
    @Test
    void testFindOverUpperLimit() {
        String message = "excess of the upper limit";
        HttpResponse response = support.sendRequest(support.get(PATH));
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999902"
                , 1
                , "Search has exceeded the limit of 1,000 results. Narrow down your criteria.");
    }

    /**
     * When validation of the path parameter results in an error, a status of 400 and an error message should be returned.
     */
    @Test
    void testInvalidClientGetForm() {
        String message = "Get client details (invalid parameter)";
        HttpResponse response = support.sendRequest(support.get(PATH + "/number"));
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999901"
                , 1
                , "clientId:Enter a numeric value.");
    }

    /**
     * When a client ID is specified as a path parameter, only one client shall be retrieved.
     */
    @Test
    void testShow() {
        String message = "Get client details";
        HttpResponse response = support.sendRequest(support.get(PATH + "/1"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(support.getBodyString(response))
                .assertThat("$.clientId", equalTo(1), message + "[Client ID]")
                .assertThat("$.clientName", equalTo("Test Company 1 (Agriculture)"), message + "[Client Name]")
                .assertThat("$.industryCode", equalTo("01"), message + "[Industry Code]");
    }

    /**
     * When specifying a non-existent client ID, a 404 status and error message should be returned.
     */
    @Test
    void testShowNotExistsClient() {
        String message = "Get non-existent client details";
        HttpResponse response = support.sendRequest(support.get(PATH + "/9999"));
        support.assertStatusCode(message, HttpResponse.Status.NOT_FOUND, response);
        support.assertFaultMessages(message, response
                , "FB1999903"
                , 1
                , "The specified data does not exist.");
    }

    /**
     * When the validation result of a request parameter is an error, a status of 400 and an error message should be returned.
     */
    @Test
    void testInvalidClientForm() {
        ClientForm client = new ClientForm();
        client.setClientName(INVALID_CLIENT_NAME);
        client.setIndustryCode(INVALID_INDUSTRY_CODE);

        String message = "registration (invalid parameter)";
        HttpResponse response = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:Enter a value up to 128 characters.","industryCode:Invalid value has been specified.");
    }

    /**
     * When the required field is not included in the parameter, a status of 400 and an error message should be returned.
     */
    @Test
    void testRegisterNoParameter() {
        ClientForm client = new ClientForm();

        String message = "Registration without required fields";
        HttpResponse response = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:Enter this.","industryCode:Enter this.");
    }

    /**
     * Ensure that the client registration.
     */
    @Test
    void testRegisterClient() {
        String clientName = "NewTestingCompany" + SystemTimeUtil.getDateTimeMillisString();
        String industryCode = "03";

        ClientForm client = new ClientForm();
        client.setClientName(clientName);
        client.setIndustryCode(industryCode);

        String queryString = "?clientName=" + clientName;

        String beforeRegister = "The client whose name you are going to register does not exist";
        HttpResponse response = support.sendRequest(support.get(PATH + queryString));
        support.assertStatusCode(beforeRegister, HttpResponse.Status.OK, response);
        with(support.getBodyString(response)).assertThat("$", empty(), beforeRegister + "[Number of results]");

        String register = "Registration";
        HttpResponse registerResponse = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(register, HttpResponse.Status.OK, registerResponse);
        with(support.getBodyString(registerResponse))
                .assertNotNull("$..clientId", register + "[Client ID]")
                .assertThat("$..clientName", hasItem(clientName), register + "[Client Name]")
                .assertThat("$..industryCode", hasItem(industryCode), register + "[Industry Code]");

        String afterRegister = "Retrieve the client with the client name registered earlier";
        HttpResponse afterRegisterResponse = support.sendRequest(support.get(PATH + queryString));
        support.assertStatusCode(afterRegister, HttpResponse.Status.OK, afterRegisterResponse);
        with(support.getBodyString(afterRegisterResponse))
                .assertThat("$", hasSize(1), afterRegister + "[Number of results]")
                .assertThat("$..clientName", hasItem(clientName), afterRegister + "[Client Name]")
                .assertThat("$..industryCode", hasItem(industryCode), afterRegister + "[Industry Code]");
    }

    /**
     * When duplicate client names are specified, a 409 status and error message should be returned.
     */
    @Test
    void testRegisterDuplicatedClient() {
        String findMessage = "Search by client name";
        HttpResponse findResponse = support.sendRequest(support.get(PATH + "?clientName=Test%20Company%201%20(Agriculture)"));
        support.assertStatusCode(findMessage, HttpResponse.Status.OK, findResponse);
        with(support.getBodyString(findResponse))
                .assertThat("$", hasSize(1), findMessage + "[Number of results]");

        ClientForm client = new ClientForm();
        client.setClientName("Test Company 1 (Agriculture)");
        client.setIndustryCode("03");

        String registerMessage = "Registration";
        HttpResponse registerResponse = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(registerMessage, HttpResponse.Status.CONFLICT, registerResponse);
        support.assertFaultMessages(registerMessage, registerResponse
                , "FB1999904"
                , 1
                , "The specified data cannot be registered as it overlaps with existing data.");
    }
}