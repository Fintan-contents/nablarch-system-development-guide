package com.nablarch.example.climan.rest.client;

import nablarch.core.date.SystemTimeUtil;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.RestMockHttpRequest;
import nablarch.fw.web.RestMockHttpRequestBuilder;
import nablarch.test.core.http.RestTestSupport;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.fail;

public class ClientActionTest extends RestTestSupport {
    /**
     * 条件なしで顧客一覧を取得した場合、全件取得されること。
     */
    @Test
    public void testFindClientAll() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();

        String message = "顧客一覧取得";
        HttpResponse response = sendRequest(builder.get("/client"));
        assertStatusCode(message, HttpResponse.Status.OK.getStatusCode(), response);

        try {
            JSONAssert.assertEquals(message, readTextResource("client-list.json")
                    , response.getBodyString(), JSONCompareMode.LENIENT);
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    /**
     * 業種コードをパラメータに含めた場合、業種コードに一致する顧客だけが取得されること。
     */
    @Test
    public void testFindClientByIndustryCode() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();

        String message = "顧客一覧取得";
        HttpResponse response = sendRequest(builder.get("/client?industryCode=01"));
        assertStatusCode(message, HttpResponse.Status.OK.getStatusCode(), response);

        try {
            JSONAssert.assertEquals(message, readTextResource("client-list-industry-code-01.json")
                    , response.getBodyString(), JSONCompareMode.LENIENT);
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    /**
     * 顧客名をパラメータに含めた場合、顧客名に一致する顧客だけが取得されること。
     */
    @Test
    public void testFindClientByClientName() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();

        String message = "顧客一覧取得";
        HttpResponse response = sendRequest(builder.get("/client?clientName=テスト会社３"));
        assertStatusCode(message, HttpResponse.Status.OK.getStatusCode(), response);

        try {
            JSONAssert.assertEquals(message, readTextResource("client-list-client-name-3.json")
                    , response.getBodyString(), JSONCompareMode.LENIENT);
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    /**
     * 業種コードと顧客名を同時にパラメータに含めた場合、
     * 業種コードと顧客名に一致する顧客だけが取得されること。
     */
    @Test
    public void testFindClientByIndustryCodeAndClientName() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();

        String message = "顧客一覧取得";
        HttpResponse response = sendRequest(builder.get("/client?industryCode=01&clientName=テスト会社３"));
        assertStatusCode(message, HttpResponse.Status.OK.getStatusCode(), response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1))
                .assertThat("$[0].client_id", is(4))
                .assertThat("$[0].client_name", is("テスト会社３（農業）"))
                .assertThat("$[0].industry_code", is("01"));
    }

    /**
     * パスパラメータに顧客IDを指定して顧客が1件だけ取得されること。
     */
    @Test
    public void testShowClient() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();

        String message = "顧客詳細取得";
        HttpResponse response = sendRequest(builder.get("/client/1"));
        assertStatusCode(message, HttpResponse.Status.OK.getStatusCode(), response);
        with(response.getBodyString())
                .assertThat("$.client_id", is(1))
                .assertThat("$.client_name", is("テスト会社１（農業）"))
                .assertThat("$.industry_code", is("01"));
    }

    /**
     * JSONをPOSTすることで顧客を新規登録できること。
     */
    @Test
    public void testRegisterClient() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();

        String clientName = "新規テスト会社" + SystemTimeUtil.getDateTimeMillisString();
        String queryString = "clientName=" + clientName;

        ClientForm client = new ClientForm();
        client.setClientName(clientName);
        client.setIndustryCode("03");

        String beforeRegister = "登録する顧客名の顧客が存在しないこと";
        HttpResponse response = sendRequest(builder.get("/client?" + queryString));
        assertStatusCode(beforeRegister, HttpResponse.Status.OK.getStatusCode(), response);
        with(response.getBodyString()).assertThat("$", empty());

        String register = "新規登録";
        HttpResponse registerResponse = sendRequest(builder.post("/client/").setBody(client));
        assertStatusCode(register, HttpResponse.Status.CREATED.getStatusCode(), registerResponse);

        String afterRegister = "登録した顧客名の顧客が取得できること";
        HttpResponse afterRegisterResponse = sendRequest(builder.get("/client?" + queryString));
        assertStatusCode(afterRegister, HttpResponse.Status.OK.getStatusCode(), afterRegisterResponse);
        with(afterRegisterResponse.getBodyString())
                .assertThat("$", hasSize(1))
                .assertThat("$..client_name", hasItem(clientName))
                .assertThat("$..industry_code", hasItem("03"));
    }

    public static final String INVALID_CLIENT_NAME = "129文字abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrst";
    public static final String INVALID_INDUSTRY_CODE = "04";

    /**
     * リクエストパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientSearchForm() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();
        RestMockHttpRequest request = builder.get("/client");
        request.setParam("clientName", INVALID_CLIENT_NAME);
        request.setParam("industryCode", INVALID_INDUSTRY_CODE);

        String message = "顧客一覧取得";
        HttpResponse response = sendRequest(request);
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST.getStatusCode(), response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(2))
                .assertThat("$.messages", hasItems(
                        "clientName:128文字以下の値を入力してください。"
                        , "industryCode:不正な値が指定されました。"));
    }

    /**
     * パスパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientGetForm() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();

        String message = "顧客詳細取得";
        HttpResponse response = sendRequest(builder.get("/client/number"));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST.getStatusCode(), response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(1))
                .assertThat("$.messages", hasItem("clientId:数値を入力してください。"));
    }

    /**
     * リクエストパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientForm() {
        RestMockHttpRequestBuilder builder = getHttpRequestBuilder();

        Map<String, String> form = new HashMap<>();
        form.put("client_name", INVALID_CLIENT_NAME);
        form.put("industry_code", INVALID_INDUSTRY_CODE);

        String message = "新規登録";
        HttpResponse response = sendRequest(builder.post("/client/").setBody(form));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST.getStatusCode(), response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(2))
                .assertThat("$.messages", hasItems(
                        "clientName:128文字以下の値を入力してください。"
                        , "industryCode:不正な値が指定されました。"));
    }

}