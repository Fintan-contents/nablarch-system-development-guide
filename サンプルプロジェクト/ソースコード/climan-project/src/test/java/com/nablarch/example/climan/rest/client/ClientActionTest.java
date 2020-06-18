package com.nablarch.example.climan.rest.client;

import nablarch.core.date.SystemTimeUtil;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.RestMockHttpRequest;
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

/**
 * {@link ClientAction}のテストクラス。
 */
public class ClientActionTest extends RestTestSupport {
    public static final String INVALID_CLIENT_NAME = "129文字abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrst";
    public static final String INVALID_INDUSTRY_CODE = "04";

    /**
     * リクエストパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientSearchForm() {
        RestMockHttpRequest request = get("/client");
        request.setParam("clientName", INVALID_CLIENT_NAME);
        request.setParam("industryCode", INVALID_INDUSTRY_CODE);

        String message = "顧客一覧取得(パラメータ不正)";
        HttpResponse response = sendRequest(request);
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(2))
                .assertThat("$.messages", hasItems(
                        "clientName:128文字以下の値を入力してください。"
                        , "industryCode:不正な値が指定されました。"));
    }

    /**
     * 条件なしで顧客一覧を取得した場合、全件取得されること。
     */
    @Test
    public void testFindClientAll() throws JSONException {
        String message = "顧客一覧全件取得";
        HttpResponse response = sendRequest(get("/client"));
        assertStatusCode(message, HttpResponse.Status.OK, response);

        JSONAssert.assertEquals(message, readTextResource("client-list.json")
                , response.getBodyString(), JSONCompareMode.LENIENT);
    }

    /**
     * 検索結果が0件となる条件で顧客一覧を取得した場合、正常レスポンスが返ってくること。
     */
    @Test
    public void testFindNoClient() {
        String message = "検索結果０件";
        HttpResponse response = sendRequest(get("/client?clientName=存在しない会社"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty());
    }

    /**
     * 業種コードをパラメータに含めた場合、業種コードに一致する顧客だけが取得されること。
     */
    @Test
    public void testFindClientByIndustryCode() throws JSONException {
        String message = "業種コード検索";
        HttpResponse response = sendRequest(get("/client?industryCode=01"));
        assertStatusCode(message, HttpResponse.Status.OK, response);

        JSONAssert.assertEquals(message, readTextResource("client-list-industry-code-01.json")
                , response.getBodyString(), JSONCompareMode.LENIENT);
    }

    /**
     * 顧客名をパラメータに含めた場合、顧客名に一致する顧客だけが取得されること。
     */
    @Test
    public void testFindClientByClientName() throws JSONException {
        String message = "顧客名検索";
        HttpResponse response = sendRequest(get("/client?clientName=テスト会社３"));
        assertStatusCode(message, HttpResponse.Status.OK, response);

        JSONAssert.assertEquals(message, readTextResource("client-list-client-name-3.json")
                , response.getBodyString(), JSONCompareMode.LENIENT);
    }

    /**
     * 業種コードと顧客名を同時にパラメータに含めた場合、
     * 業種コードと顧客名に一致する顧客だけが取得されること。
     */
    @Test
    public void testFindClientByIndustryCodeAndClientName() {
        String message = "業種コード＋顧客名検索";
        HttpResponse response = sendRequest(get("/client?industryCode=01&clientName=テスト会社３"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1))
                .assertThat("$[0].client_id", is(4))
                .assertThat("$[0].client_name", is("テスト会社３（農業）"))
                .assertThat("$[0].industry_code", is("01"));
    }

    /**
     * 顧客一覧の取得件数が上限と同じ場合、正常レスポンスが返ってくること。
     */
    @Test
    public void testFindUnderUpperLimit() {
        String message = "上限境界値";
        HttpResponse response = sendRequest(get("/client"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1000));
    }

    /**
     * 顧客一覧の取得件数が上限を超えた場合、400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testFindOverUpperLimit() {
        String message = "上限超過";
        HttpResponse response = sendRequest(get("/client"));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(1))
                .assertThat("$.messages", hasItem("検索結果が上限値1,000件を超えました。検索条件を絞り込んで下さい。"));
    }

    /**
     * パスパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientGetForm() {
        String message = "顧客詳細取得(パラメータ不正)";
        HttpResponse response = sendRequest(get("/client/number"));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(1))
                .assertThat("$.messages", hasItem("clientId:数値を入力してください。"));
    }

    /**
     * パスパラメータに顧客IDを指定して顧客が1件だけ取得されること。
     */
    @Test
    public void testShowClient() {
        String message = "顧客詳細取得";
        HttpResponse response = sendRequest(get("/client/1"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$.client_id", is(1))
                .assertThat("$.client_name", is("テスト会社１（農業）"))
                .assertThat("$.industry_code", is("01"));
    }

    /**
     * 存在しない顧客IDを指定した場合は404のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testShowNotExistsClient() {
        String message = "存在しない顧客詳細取得";
        HttpResponse response = sendRequest(get("/client/9999"));
        assertStatusCode(message, HttpResponse.Status.NOT_FOUND, response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(1))
                .assertThat("$.messages", hasItem("指定されたデータは存在しません。"));
    }

    /**
     * リクエストパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientForm() {
        Map<String, String> form = new HashMap<>();
        form.put("client_name", INVALID_CLIENT_NAME);
        form.put("industry_code", INVALID_INDUSTRY_CODE);

        String message = "新規登録(パラメータ不正)";
        HttpResponse response = sendRequest(post("/client/").setBody(form));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(2))
                .assertThat("$.messages", hasItems(
                        "clientName:128文字以下の値を入力してください。"
                        , "industryCode:不正な値が指定されました。"));
    }

    /**
     * 必須項目をパラメータに含まない場合は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testRegisterNoParameter() {
        Map<String, String> form = new HashMap<>();

        String message = "必須項目なし新規登録";
        HttpResponse response = sendRequest(post("/client/").setBody(form));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(2))
                .assertThat("$.messages", hasItems(
                        "clientName:入力してください。"
                        , "industryCode:入力してください。"));
    }

    /**
     * 顧客を新規登録できること。
     */
    @Test
    public void testRegisterClient() {
        String clientName = "新規テスト会社" + SystemTimeUtil.getDateTimeMillisString();
        String queryString = "clientName=" + clientName;

        ClientForm client = new ClientForm();
        client.setClientName(clientName);
        client.setIndustryCode("03");

        String beforeRegister = "登録する顧客名の顧客が存在しないこと";
        HttpResponse response = sendRequest(get("/client?" + queryString));
        assertStatusCode(beforeRegister, HttpResponse.Status.OK, response);
        with(response.getBodyString()).assertThat("$", empty());

        String register = "新規登録";
        HttpResponse registerResponse = sendRequest(post("/client/").setBody(client));
        assertStatusCode(register, HttpResponse.Status.CREATED, registerResponse);

        String afterRegister = "登録した顧客名の顧客が取得できること";
        HttpResponse afterRegisterResponse = sendRequest(get("/client?" + queryString));
        assertStatusCode(afterRegister, HttpResponse.Status.OK, afterRegisterResponse);
        with(afterRegisterResponse.getBodyString())
                .assertThat("$", hasSize(1))
                .assertThat("$..client_name", hasItem(clientName))
                .assertThat("$..industry_code", hasItem("03"));
    }

    /**
     * 重複する顧客名の場合は409のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testRegisterDuplicatedClient() {
        ClientForm client = new ClientForm();
        client.setClientName("テスト会社１（農業）");
        client.setIndustryCode("03");

        String message = "新規登録";
        HttpResponse response = sendRequest(post("/client/").setBody(client));
        assertStatusCode(message, HttpResponse.Status.CONFLICT, response);
        with(response.getBodyString())
                .assertThat("$.messages", hasSize(1))
                .assertThat("$.messages", hasItem("指定されたデータは既存データと重複するため登録できません。"));
    }
}