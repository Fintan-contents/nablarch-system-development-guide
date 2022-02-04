package com.nablarch.example.climan.test;

import nablarch.fw.web.HttpResponse;
import nablarch.test.core.http.RestTestSupport;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * RESTful web service test support class for client management system.
 * It has response assertions for use in client management systems.
 */
public class ClimanRestTestSupport extends RestTestSupport {

    /**
     * Constructor.
     * @param testClass Test class
     */
    public ClimanRestTestSupport(Class<?> testClass) {
        super(testClass);
    }

    /**
     * Test that the body of the response matches the expected value file.
     *
     * @param message          Message
     * @param response         Response
     * @param expectedFileName Expected file name
     * @throws JSONException Exception when JSON parsing fails
     */
    public void assertJsonEquals(String message, HttpResponse response, String expectedFileName) throws JSONException {
        JSONAssert.assertEquals(message, readTextResource(expectedFileName)
                , response.getBodyString(), JSONCompareMode.LENIENT);
    }

    /**
     * Test fault code and fault message.
     *
     * @param message   Message
     * @param response  Response
     * @param faultCode Expected fault code
     * @param size      Expected fault message size
     * @param messages  Expected fault message
     */
    public void assertFaultMessages(String message, HttpResponse response, String faultCode, int size, String... messages) {
        with(response.getBodyString())
                .assertThat("$.fault_code", equalTo(faultCode), message + "[fault code]")
                .assertThat("$.messages", hasSize(size), message + "[fault message size]")
                .assertThat("$.messages", hasItems(messages), message + "[fault message]");
    }
}
