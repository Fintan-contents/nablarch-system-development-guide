package com.nablarch.example.climan.rest.client;

import nablarch.core.validation.ee.Domain;

import java.io.Serializable;

/**
 * 顧客検索のフォームクラス。
 */
public class ClientSearchForm implements Serializable {

    /** 顧客名 */
    @Domain("clientName")
    private String clientName;

    /** 業種コード */
    @Domain("industryCode")
    private String industryCode;

    /**
     * 顧客名を返す。
     * @return 顧客名
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 顧客名を設定する。
     * @param clientName 顧客名
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 業種コードを返す。
     * @return 業種コード
     */
    public String getIndustryCode() {
        return industryCode;
    }

    /**
     * 業種コードを設定する。
     * @param industryCode 業種コード
     */
    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }
}
