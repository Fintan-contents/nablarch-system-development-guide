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
 * 顧客管理システム向けRESTfulウェブサービステストサポートクラス。
 * 顧客管理システムで使用するレスポンスのアサーションをもつ。
 */
public class ClimanRestTestSupport extends RestTestSupport {
    /**
     * レスポンスのボディが期待値ファイルに一致することを確認する。
     *
     * @param message          メッセージ
     * @param response         レスポンス
     * @param expectedFileName 期待値のファイル名
     * @throws JSONException JSONのパース失敗時例外
     */
    protected void assertJsonEquals(String message, HttpResponse response, String expectedFileName) throws JSONException {
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
    protected void assertFaultMessages(String message, HttpResponse response, String faultCode, int size, String... messages) {
        with(response.getBodyString())
                .assertThat("$.fault_code", equalTo(faultCode), message + "[障害コード]")
                .assertThat("$.messages", hasSize(size), message + "[障害メッセージサイズ]")
                .assertThat("$.messages", hasItems(messages), message + "[障害メッセージ]");
    }
}
