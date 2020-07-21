package com.nablarch.example.climan.rest.client;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import java.io.Serializable;

/**
 * 顧客詳細取得のフォームクラス。
 */
public class ClientGetForm implements Serializable {

    /** 顧客ID */
    @Required
    @Domain("clientId")
    private String clientId;

    /**
     * 顧客IDを返す。
     * @return 顧客ID
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 顧客IDを設定する。
     * @param clientId 顧客ID
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
