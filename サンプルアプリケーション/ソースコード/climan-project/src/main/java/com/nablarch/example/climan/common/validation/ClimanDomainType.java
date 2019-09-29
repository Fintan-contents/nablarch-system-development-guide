package com.nablarch.example.climan.common.validation;

import nablarch.common.code.validator.ee.CodeValue;
import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.SystemChar;

/**
 * ドメイン定義。
 */
public class ClimanDomainType {


    /** 顧客ID */
    @Digits(integer = 10)
    private String clientId;

    /** 顧客名 */
    @Length(max = 128)
    @SystemChar(charsetDef = "システム許容文字")
    private String clientName;

    /** 業種コード */
    @CodeValue(codeId = "C0100001")
    private String industryCode;
}
