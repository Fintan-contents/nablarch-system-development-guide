package com.nablarch.example.climan.rest.client;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import java.io.Serializable;

/**
 * 顧客登録のフォームクラス。
 */
public class ClientForm implements Serializable {

    /** 顧客名 */
    @Required
    @Domain("clientName")
    private String clientName;

    /** 業種コード */
    @Required
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
