package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.test.ClimanRestTestExtension;
import com.nablarch.example.climan.test.ClimanRestTestSupport;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.web.RestMockHttpRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * {@link ClientAction}のテストクラス。
 */
@ExtendWith(ClimanRestTestExtension.class)
class ClientActionTest {
    /**
     * テスト対象のリクエストパス
     */
    public static final String PATH = "/client";

    /** 不正な顧客名（128文字を超えている） */
    public static final String INVALID_CLIENT_NAME = RandomStringUtils.random(129, "１２３４５６７８９０");
    /** 不正な業種コード（コードマスタに存在しない） */
    public static final String INVALID_INDUSTRY_CODE = "04";

    ClimanRestTestSupport support;

    /**
     * リクエストパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    void testInvalidClientSearchForm() throws UnsupportedEncodingException {
        RestMockHttpRequest request = support.get(PATH);
        String encodedName = URLEncoder.encode("invalid parameter", "UTF-8");
        request.setParam("clientName", encodedName);
        request.setParam("industryCode", INVALID_INDUSTRY_CODE);

        String message = "顧客一覧取得(パラメータ不正)";
        HttpResponse response = support.sendRequest(request);
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:不正な文字種の値が指定されました。", "industryCode:不正な値が指定されました。");
    }

    /**
     * 条件なしで顧客一覧を取得した場合、全件取得されること。
     */
    @Test
    void testFindAll() throws JSONException {
        String message = "顧客一覧全件取得";
        HttpResponse response = support.sendRequest(support.get(PATH));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        support.assertJsonEquals(message, response, "client-list.json");
    }

    /**
     * 業種コードと顧客名を同時にパラメータに含めた場合、
     * 業種コードと顧客名に一致する顧客1000件が取得されること。
     */
    @Test
    void testFindCodeAndNameUpperLimit() {
        String message = "顧客一覧(1000件)取得";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=01&clientName=テスト会社"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1000), message + "[結果件数]");
    }

    /**
     * 検索結果が0件となる条件(顧客名)で顧客一覧を取得した場合、正常レスポンスが返ってくること。
     */
    @Test
    void testFindClientNameNoClients() {
        String message = "検索結果０件";
        HttpResponse response = support.sendRequest(support.get(PATH + "?clientName=存在しない会社"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[結果件数]");
    }

    /**
     * 検索結果が0件となる条件(業種コード)で顧客一覧を取得した場合、正常レスポンスが返ってくること。
     */
    @Test
    void testFindIndustryCodeNoClients() {
        String message = "検索結果０件";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=03"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[結果件数]");
    }

    /**
     * 検索結果が0件となる条件(業種コード、顧客名)で顧客一覧を取得した場合、正常レスポンスが返ってくること。
     */
    @Test
    void testFindIndustryCodeAndClientNameNoClients() {
        String message = "検索結果０件";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=03&clientName=存在しない会社"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[結果件数]");
    }

    /**
     * 登録件数0件で検索結果が0件となる顧客一覧を取得した場合、正常レスポンスが返ってくること。
     */
    @Test
    void testFindNoClients() {
        String message = "検索結果０件";
        HttpResponse response = support.sendRequest(support.get(PATH));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", empty(), message + "[結果件数]");
    }

    /**
     * 顧客名をパラメータに含めた場合、
     * 顧客名に前方一致する顧客だけが取得されること。
     */
    @Test
    void testFindByClientName() {
        String message = "顧客名検索";
        HttpResponse response = support.sendRequest(support.get(PATH + "?clientName=テスト会社２"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(2), message + "[結果件数]")
                .assertThat("$[0].clientId", equalTo(3), message + "[顧客ID]")
                .assertThat("$[0].clientName", equalTo("テスト会社２（建設業）"), message + "[顧客名]")
                .assertThat("$[0].industryCode", equalTo("02"), message + "[業種コード]")
                .assertThat("$[1].clientId", equalTo(2), message + "[顧客ID]")
                .assertThat("$[1].clientName", equalTo("テスト会社２（農業）"), message + "[顧客名]")
                .assertThat("$[1].industryCode", equalTo("01"), message + "[業種コード]");
    }

    /**
     * 業種コードをパラメータに含めた場合、
     * 業種コードに一致する顧客だけが取得されること。
     */
    @Test
    void testFindByIndustryCode() {
        String message = "業種コード検索";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=01"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(3), message + "[結果件数]")
                .assertThat("$[0].clientId", equalTo(1), message + "[顧客ID]")
                .assertThat("$[0].clientName", equalTo("テスト会社１（農業）"), message + "[顧客名]")
                .assertThat("$[0].industryCode", equalTo("01"), message + "[業種コード]")
                .assertThat("$[1].clientId", equalTo(2), message + "[顧客ID]")
                .assertThat("$[1].clientName", equalTo("テスト会社２（農業）"), message + "[顧客名]")
                .assertThat("$[1].industryCode", equalTo("01"), message + "[業種コード]")
                .assertThat("$[2].clientId", equalTo(4), message + "[顧客ID]")
                .assertThat("$[2].clientName", equalTo("テスト会社３（農業）"), message + "[顧客名]")
                .assertThat("$[2].industryCode", equalTo("01"), message + "[業種コード]");
    }

    /**
     * 業種コードと顧客名を同時にパラメータに含めた場合、
     * 業種コードと顧客名に一致する顧客だけが取得されること。
     */
    @Test
    void testFindByIndustryCodeAndClientName() {
        String message = "業種コード＋顧客名検索";
        HttpResponse response = support.sendRequest(support.get(PATH + "?industryCode=01&clientName=テスト会社３"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$", hasSize(1), message + "[結果件数]")
                .assertThat("$[0].clientId", equalTo(4), message + "[顧客ID]")
                .assertThat("$[0].clientName", equalTo("テスト会社３（農業）"), message + "[顧客名]")
                .assertThat("$[0].industryCode", equalTo("01"), message + "[業種コード]");
    }

    /**
     * 顧客一覧の取得件数が上限を超えた場合、400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    void testFindOverUpperLimit() {
        String message = "上限超過";
        HttpResponse response = support.sendRequest(support.get(PATH));
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999902"
                , 1
                , "検索結果が上限値1,000件を超えました。検索条件を絞り込んで下さい。");
    }

    /**
     * パスパラメータのバリデーションエラー時は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    void testInvalidClientGetForm() {
        String message = "顧客詳細取得(パラメータ不正)";
        HttpResponse response = support.sendRequest(support.get(PATH + "/test"));
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999901"
                , 1
                , "clientId:数値を入力してください。");
    }

    /**
     * パスパラメータに顧客IDを指定して顧客が1件だけ取得されること。
     */
    @Test
    void testShow() {
        String message = "顧客詳細取得";
        HttpResponse response = support.sendRequest(support.get(PATH + "/1"));
        support.assertStatusCode(message, HttpResponse.Status.OK, response);
        with(response.getBodyString())
                .assertThat("$.clientId", equalTo(1), message + "[顧客ID]")
                .assertThat("$.clientName", equalTo("テスト会社１（農業）"), message + "[顧客名]")
                .assertThat("$.industryCode", equalTo("01"), message + "[業種コード]");
    }

    /**
     * 存在しない顧客IDを指定した場合は404のステータスとエラーメッセージが返ってくること。
     */
    @Test
    void testShowNotExistsClient() {
        String message = "存在しない顧客詳細取得";
        HttpResponse response = support.sendRequest(support.get(PATH + "/9999"));
        support.assertStatusCode(message, HttpResponse.Status.NOT_FOUND, response);
        support.assertFaultMessages(message, response
                , "FB1999903"
                , 1
                , "指定されたデータは存在しません。");
    }

    /**
     * 存在しない顧客IDを指定した場合は404のステータスとエラーメッセージが返ってくること。
     */
    @Test
    void testShowWithEmptyClientTable() {
        String message = "DB０件で存在しない顧客詳細取得";
        HttpResponse response = support.sendRequest(support.get(PATH + "/2"));
        support.assertStatusCode(message, HttpResponse.Status.NOT_FOUND, response);
        support.assertFaultMessages(message, response
                , "FB1999903"
                , 1
                , "指定されたデータは存在しません。");
    }

    /**
     * 必須項目をパラメータに含まない場合は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    void testRegisterNoParameter() {
        ClientForm client = new ClientForm();

        String message = "必須項目なし新規登録";
        HttpResponse response = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:入力してください。", "industryCode:入力してください。");
    }

    /**
     * 必須項目を空文字にした場合は400のステータスとエラーメッセージが返ってくること。
     */
    @Test
    void testRegisterEmptyParameter() {
        ClientForm client = new ClientForm();
        client.setClientName("");
        client.setIndustryCode("");

        String message = "必須項目を空文字にした場合の新規登録";
        HttpResponse response = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(message, HttpResponse.Status.BAD_REQUEST, response);
        support.assertFaultMessages(message, response
                , "FB1999901"
                , 2
                , "clientName:入力してください。", "industryCode:入力してください。");
    }

    /**
     * 顧客を新規登録できること。
     */
    @Test
    void testRegisterClient() {
        String clientName = RandomStringUtils.random(128, "１２３４５６７８９０");
        String industryCode = "03";

        ClientForm client = new ClientForm();
        client.setClientName(clientName);
        client.setIndustryCode(industryCode);

        String queryString = "?clientName=" + clientName;

        String beforeRegister = "登録する顧客名の顧客が存在しないこと";
        HttpResponse response = support.sendRequest(support.get(PATH + queryString));
        support.assertStatusCode(beforeRegister, HttpResponse.Status.OK, response);
        with(response.getBodyString()).assertThat("$", empty(), beforeRegister + "[結果件数]");

        String register = "新規登録";
        HttpResponse registerResponse = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(register, HttpResponse.Status.OK, registerResponse);
        with(registerResponse.getBodyString())
                .assertNotNull("$..clientId", register + "[顧客ID]")
                .assertThat("$..clientName", hasItem(clientName), register + "[顧客名]")
                .assertThat("$..industryCode", hasItem(industryCode), register + "[業種コード]");

        String afterRegister = "登録した顧客名の顧客が取得できること";
        HttpResponse afterRegisterResponse = support.sendRequest(support.get(PATH + queryString));
        support.assertStatusCode(afterRegister, HttpResponse.Status.OK, afterRegisterResponse);
        with(afterRegisterResponse.getBodyString())
                .assertThat("$", hasSize(1), afterRegister + "[結果件数]")
                .assertThat("$..clientName", hasItem(clientName), afterRegister + "[顧客名]")
                .assertThat("$..industryCode", hasItem(industryCode), afterRegister + "[業種コード]");
    }

    /**
     * 重複する顧客名の場合は409のステータスとエラーメッセージが返ってくること。
     */
    @Test
    void testRegisterDuplicatedClient() {
        String findMessage = "顧客名検索";
        HttpResponse findResponse = support.sendRequest(support.get(PATH + "?clientName=テスト会社１（農業）"));
        support.assertStatusCode(findMessage, HttpResponse.Status.OK, findResponse);
        with(findResponse.getBodyString())
                .assertThat("$", hasSize(1), findMessage + "[結果件数]");

        ClientForm client = new ClientForm();
        client.setClientName("テスト会社１（農業）");
        client.setIndustryCode("03");

        String registerMessage = "新規登録";
        HttpResponse registerResponse = support.sendRequest(support.post(PATH).setBody(client));
        support.assertStatusCode(registerMessage, HttpResponse.Status.CONFLICT, registerResponse);
        support.assertFaultMessages(registerMessage, registerResponse
                , "FB1999904"
                , 1
                , "指定されたデータは既存データと重複するため登録できません。");
    }
}