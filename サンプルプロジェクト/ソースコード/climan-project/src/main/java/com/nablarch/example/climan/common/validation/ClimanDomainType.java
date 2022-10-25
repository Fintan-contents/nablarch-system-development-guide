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
    @SystemChar(charsetDef = "全角文字")
    private String clientName;

    /** 業種コード */
    @CodeValue(codeId = "C0100001")
    private String industryCode;

    /** コードID **/
    @Length(max = 8)
    @SystemChar(charsetDef = "半角英数字")
    public String codeId;

    /** コード値 **/
    @Length(max = 2)
    @SystemChar(charsetDef = "半角英数字")
    public String codeValue;

    /** コード名称 **/
    @Length(max = 50)
    @SystemChar(charsetDef = "システム許容文字")
    public String codeName;

    /** オプション名称 **/
    @Length(max = 40)
    @SystemChar(charsetDef = "システム許容文字")
    public String option;

    /** パターン **/
    @Length(min = 1, max = 1)
    @SystemChar(charsetDef = "半角英数字")
    public String pattern;

    /** ソート順 **/
    @Length(min = 1, max = 1)
    @SystemChar(charsetDef = "半角数字")
    public String sortOrder;

    /** 言語 **/
    @Length(max = 2)
    @SystemChar(charsetDef = "半角英字")
    public String lang;

    /** 略称 **/
    @Length(max = 50)
    @SystemChar(charsetDef = "システム許容文字")
    public String shortName;
}
