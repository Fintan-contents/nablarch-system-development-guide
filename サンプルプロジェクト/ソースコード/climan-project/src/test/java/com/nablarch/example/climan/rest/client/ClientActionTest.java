package com.nablarch.example.climan.rest.client;

import nablarch.core.date.SystemTimeUtil;
import nablarch.core.util.StringUtil;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.RestMockHttpRequest;
import nablarch.test.core.http.RestTestSupport;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

/**
 * {@link ClientAction}のテストクラス。
 */
public class ClientActionTest extends RestTestSupport {
    /**
     * テスト対象のリクエストパス
     */
    public static final String PATH = "/client/";

    /** 不正な顧客名（128文字を超えている） */
    public static final String INVALID_CLIENT_NAME = "129文字abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrst";
    /** 不正な業種コード（コードマスタに存在しない） */
    public static final String INVALID_INDUSTRY_CODE = "04";

    /**
     * リクエストパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientSearchForm() {
        RestMockHttpRequest request = get(PATH);
        request.setParam("clientName", INVALID_CLIENT_NAME);
        request.setParam("industryCode", INVALID_INDUSTRY_CODE);

        String message = "顧客一覧取得(パラメータ不正)";
        HttpResponse response = sendRequest(request);
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:128文字以下の値を入力してください。", "industryCode:不正な値が指定されました。");
    }

    /**
     * 条件なしで顧客一覧を取得した場合、全件取得されること。
     */
    @Test
    public void testFindAll() throws JSONException {
        String message = "顧客一覧全件取得";
        HttpResponse response = sendRequest(get(PATH));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        assertJsonEquals(message, response, "client-list.json");
    }

    /**
     * 検索結果が0件となる条件で顧客一覧を取得した場合、正常レスポンスが返ってくること。
     */
    @Test
    public void testFindNoClients() {
        String message = "検索結果０件";
        HttpResponse response = sendRequest(get(PATH + "?clientName=存在しない会社"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[結果件数]");
    }

    /**
     * 業種コードをパラメータに含めた場合、業種コードに一致する顧客だけが取得されること。
     */
    @Test
    public void testFindByIndustryCode() throws JSONException {
        String message = "業種コード検索";
        HttpResponse response = sendRequest(get(PATH + "?industryCode=01"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        assertJsonEquals(message, response, "client-list-industry-code-01.json");
    }

    /**
     * 顧客名をパラメータに含めた場合、顧客名に一致する顧客だけが取得されること。
     */
    @Test
    public void testFindByClientName() throws JSONException {
        String message = "顧客名検索";
        HttpResponse response = sendRequest(get(PATH + "?clientName=テスト会社３"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        assertJsonEquals(message, response, "client-list-client-name-3.json");
    }

    /**
     * 業種コードと顧客名を同時にパラメータに含めた場合、
     * 業種コードと顧客名に一致する顧客だけが取得されること。
     */
    @Test
    public void testFindByIndustryCodeAndClientName() {
        String message = "業種コード＋顧客名検索";
        HttpResponse response = sendRequest(get(PATH + "?industryCode=01&clientName=テスト会社３"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1), message + "[結果件数]")
                .assertThat("$[0].client_id", equalTo(4), message + "[顧客ID]")
                .assertThat("$[0].client_name", equalTo("テスト会社３（農業）"), message + "[顧客名]")
                .assertThat("$[0].industry_code", equalTo("01"), message + "[業種コード]");
    }

    /**
     * 顧客一覧の取得件数が上限と同じ場合、正常レスポンスが返ってくること。
     */
    @Test
    public void testFindUnderUpperLimit() {
        String message = "上限境界値";
        HttpResponse response = sendRequest(get(PATH));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1000), message + "[結果件数]");
    }

    /**
     * 顧客一覧の取得件数が上限を超えた場合、400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testFindOverUpperLimit() {
        String message = "上限超過";
        HttpResponse response = sendRequest(get(PATH));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999902"
                , 1
                , "検索結果が上限値1,000件を超えました。検索条件を絞り込んで下さい。");
    }

    /**
     * パスパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientGetForm() {
        String message = "顧客詳細取得(パラメータ不正)";
        HttpResponse response = sendRequest(get(PATH + "number"));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999901"
                , 1
                , "clientId:数値を入力してください。");
    }

    /**
     * パスパラメータに顧客IDを指定して顧客が1件だけ取得されること。
     */
    @Test
    public void testShow() {
        String message = "顧客詳細取得";
        HttpResponse response = sendRequest(get(PATH + "1"));
        assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$.client_id", equalTo(1), message + "[顧客ID]")
                .assertThat("$.client_name", equalTo("テスト会社１（農業）"), message + "[顧客名]")
                .assertThat("$.industry_code", equalTo("01"), message + "[業種コード]");
    }

    /**
     * 存在しない顧客IDを指定した場合は404のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testShowNotExistsClient() {
        String message = "存在しない顧客詳細取得";
        HttpResponse response = sendRequest(get(PATH + "9999"));
        assertStatusCode(message, HttpResponse.Status.NOT_FOUND, response);
        assertFaultMessages(message, response
                , "FB1999903"
                , 1
                , "指定されたデータは存在しません。");
    }

    /**
     * リクエストパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testInvalidClientForm() {
        ClientForm client = new ClientForm();
        client.setClientName(INVALID_CLIENT_NAME);
        client.setIndustryCode(INVALID_INDUSTRY_CODE);

        String message = "新規登録(パラメータ不正)";
        HttpResponse response = sendRequest(post(PATH).setBody(client));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:128文字以下の値を入力してください。", "industryCode:不正な値が指定されました。");
    }

    /**
     * 必須項目をパラメータに含まない場合は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testRegisterNoParameter() {
        ClientForm client = new ClientForm();

        String message = "必須項目なし新規登録";
        HttpResponse response = sendRequest(post(PATH).setBody(client));
        assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:入力してください。", "industryCode:入力してください。");
    }

    /**
     * 顧客を新規登録できること。
     */
    @Test
    public void testRegisterClient() {
        String clientName = "新規テスト会社" + SystemTimeUtil.getDateTimeMillisString();
        String industryCode = "03";

        ClientForm client = new ClientForm();
        client.setClientName(clientName);
        client.setIndustryCode(industryCode);

        String queryString = "?clientName=" + clientName;

        String beforeRegister = "登録する顧客名の顧客が存在しないこと";
        HttpResponse response = sendRequest(get(PATH + queryString));
        assertStatusCode(beforeRegister, HttpResponse.Status.OK, response);
        with(response.getBodyString()).assertThat("$", empty(), beforeRegister + "[結果件数]");

        String register = "新規登録";
        HttpResponse registerResponse = sendRequest(post(PATH).setBody(client));
        assertStatusCode(register, HttpResponse.Status.CREATED, registerResponse);
        assertTrue(register + "[レスポンスボディ]", StringUtil.isNullOrEmpty(registerResponse.getBodyString()));

        String afterRegister = "登録した顧客名の顧客が取得できること";
        HttpResponse afterRegisterResponse = sendRequest(get(PATH + queryString));
        assertStatusCode(afterRegister, HttpResponse.Status.OK, afterRegisterResponse);
        with(afterRegisterResponse.getBodyString())
                .assertThat("$", hasSize(1), afterRegister + "[結果件数]")
                .assertThat("$..client_name", hasItem(clientName), afterRegister + "[顧客名]")
                .assertThat("$..industry_code", hasItem(industryCode), afterRegister + "[業種コード]");
    }

    /**
     * 重複する顧客名の場合は409のステータスとエラーメッセージが返ってくること。
     */
    @Test
    public void testRegisterDuplicatedClient() {
        String findMessage = "顧客名検索";
        HttpResponse findResponse = sendRequest(get(PATH + "?clientName=テスト会社１（農業）"));
        assertStatusCode(findMessage, HttpResponse.Status.OK, findResponse);
        with(findResponse.getBodyString())
                .assertThat("$", hasSize(1), findMessage + "[結果件数]");

        ClientForm client = new ClientForm();
        client.setClientName("テスト会社１（農業）");
        client.setIndustryCode("03");

        String registerMessage = "新規登録";
        HttpResponse registerResponse = sendRequest(post(PATH).setBody(client));
        assertStatusCode(registerMessage, HttpResponse.Status.CONFLICT, registerResponse);
        assertFaultMessages(registerMessage, registerResponse
                , "FB1999904"
                , 1
                , "指定されたデータは既存データと重複するため登録できません。");
    }

    /**
     * レスポンスのボディが期待値ファイルに一致することを確認する。
     *
     * @param message          メッセージ
     * @param response         レスポンス
     * @param expectedFileName 期待値のファイル名
     * @throws JSONException JSONのパース失敗時例外
     */
    private void assertJsonEquals(String message, HttpResponse response, String expectedFileName) throws JSONException {
        JSONAssert.assertEquals(message, readTextResource(expectedFileName)
                , response.getBodyString(), JSONCompareMode.LENIENT);
    }

    /**
     * 障害コードと障害メッセージを確認する。
     *
     * @param message   メッセージ
     * @param response  レスポンス
     * @param faultCode 期待される障害コード
     * @param size      期待される障害メッセージサイズ
     * @param messages  期待される障害メッセージ
     */
    private void assertFaultMessages(String message, HttpResponse response, String faultCode, int size, String... messages) {
        with(response.getBodyString())
                .assertThat("$.fault_code", equalTo(faultCode), message + "[障害コード]")
                .assertThat("$.messages", hasSize(size), message + "[障害メッセージサイズ]")
                .assertThat("$.messages", hasItems(messages), message + "[障害メッセージ]");
    }
}