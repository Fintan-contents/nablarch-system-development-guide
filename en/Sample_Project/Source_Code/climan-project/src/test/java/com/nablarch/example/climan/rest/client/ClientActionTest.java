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
     * When the industry code and client name are included in the parameter at the same time,
     * 1000 clients matching the industry code and client name shall be retrieved.
     */
    @Test
    void testFindCodeAndNameUpperLimit() {
        String message = "Obtain clients list (1000 items)";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=01&clientName=Test%20Company"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1000), message + "[Number of results]");
    }

    /**
     * When a clients list is retrieved with the condition (client name) that the search result is 0, a normal response should be returned.
     */
    @Test
    void testFindClientNameNoClients() {
        String message = "0 results";
        HttpResponse response = support.sendRequest(support.get(PATH + "?clientName=does_not_exist"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[Number of results]");
    }

    /**
     * When a clients list is retrieved with the condition (industry code) that the search result is 0, a normal response should be returned.
     */
    @Test
    void testFindIndustryCodeNoClients() {
        String message = "0 results";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=03"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[Number of results]");
    }

    /**
     * When a clients list is retrieved with the condition (industry code and client name) that the search result is 0, a normal response should be returned.
     */
    @Test
    void testFindIndustryCodeAndClientNameNoClients() {
        String message = "0 results";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=03&clientName=does_not_exist"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[Number of results]");
    }

    /**
     * When the number of registered data in the DB is 0 and a customer list with a search result of 0 is retrieved, a normal response should be returned.
     */
    @Test
    void testFindNoClients() {
        String message = "0 results";
        HttpResponse response = support.sendRequest(support.get(PATH));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[Number of results]");
    }

    /**
     * When the client name are included in the parameter at the same time,
     * only the clients matching the client name shall be retrieved.
     */
    @Test
    void testFindByClientName() {
        String message = "Search by client name";
        HttpResponse response = support.sendRequest(support.get(PATH + "?clientName=Test%20Company%202"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(2), message + "[Number of results]")
                .assertThat("$[0].clientId", equalTo(2), message + "[Client ID]")
                .assertThat("$[0].clientName", equalTo("Test Company 2 (Agriculture)"), message + "[Client name]")
                .assertThat("$[0].industryCode", equalTo("01"), message + "[Industry Code]")
                .assertThat("$[1].clientId", equalTo(3), message + "[Client ID]")
                .assertThat("$[1].clientName", equalTo("Test Company 2 (Construction)"), message + "[Client name]")
                .assertThat("$[1].industryCode", equalTo("02"), message + "[Industry Code]");
    }

    /**
     * When the industry code are included in the parameter at the same time,
     * only the clients matching the industry code shall be retrieved.
     */
    @Test
    void testFindByIndustryCode() {
        String message = "Search by industry codee";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=01"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(3), message + "[Number of results]")
                .assertThat("$[0].clientId", equalTo(1), message + "[Client ID]")
                .assertThat("$[0].clientName", equalTo("Test Company 1 (Agriculture)"), message + "[Client name]")
                .assertThat("$[0].industryCode", equalTo("01"), message + "[Industry Code]")
                .assertThat("$[1].clientId", equalTo(2), message + "[Client ID]")
                .assertThat("$[1].clientName", equalTo("Test Company 2 (Agriculture)"), message + "[Client name]")
                .assertThat("$[1].industryCode", equalTo("01"), message + "[Industry Code]")
                .assertThat("$[2].clientId", equalTo(4), message + "[Client ID]")
                .assertThat("$[2].clientName", equalTo("Test Company 3 (Agriculture)"), message + "[Client name]")
                .assertThat("$[2].industryCode", equalTo("01"), message + "[Industry Code]");
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
        with(response.getBodyString())
                .assertThat("$", hasSize(1), message + "[Number of results]")
                .assertThat("$[0].clientId", equalTo(4), message + "[Client ID]")
                .assertThat("$[0].clientName", equalTo("Test Company 3 (Agriculture)"), message + "[Client name]")
                .assertThat("$[0].industryCode", equalTo("01"), message + "[Industry Code]");
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
        with(response.getBodyString())
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
     * When specifying a non-existent client ID, a 404 status and error message should be returned.
     */
    @Test
    void testShowWithEmptyClientTable() {
        String message = "Get non-existent client details";
        HttpResponse response = support.sendRequest(support.get(PATH + "/2"));
        support.assertStatusCode(message, HttpResponse.Status.NOT_FOUND, response);
        support.assertFaultMessages(message, response
                , "FB1999903"
                , 1
                , "The specified data does not exist.");
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
     * When the required field is left blank in the parameter, a status of 400 and an error message should be returned.
     */
    @Test
    void testRegisterEmptyParameter() {
        ClientForm client = new ClientForm();
        client.setClientName("");
        client.setIndustryCode("");

        String message = "Registration required fields is left blank";
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
        with(response.getBodyString()).assertThat("$", empty(), beforeRegister + "[Number of results]");

        String register = "Registration";
        HttpResponse registerResponse = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(register, HttpResponse.Status.OK, registerResponse);
        with(registerResponse.getBodyString())
                .assertNotNull("$..clientId", register + "[Client ID]")
                .assertThat("$..clientName", hasItem(clientName), register + "[Client Name]")
                .assertThat("$..industryCode", hasItem(industryCode), register + "[Industry Code]");

        String afterRegister = "Retrieve the client with the client name registered earlier";
        HttpResponse afterRegisterResponse = support.sendRequest(support.get(PATH + queryString));
        support.assertStatusCode(afterRegister, HttpResponse.Status.OK, afterRegisterResponse);
        with(afterRegisterResponse.getBodyString())
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
        with(findResponse.getBodyString())
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