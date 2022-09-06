package com.nablarch.example.proman.common.validation;

import nablarch.common.code.validator.ee.CodeValue;
import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.NumberRange;
import nablarch.core.validation.ee.SystemChar;

/**
 * ドメイン定義を表す列挙型。
 *
 * @author Masaya Seko
 */
@SuppressWarnings("all")
public class ProManDomainType {

    /** 組織ID **/
    @Digits(integer = 4)
    public String organizationId;

    /** ログインID **/
    @Length(max = 20)
    @SystemChar(charsetDef = "半角数字")
    public String loginId;

    /** ユーザパスワード **/
    @Length(max = 44)
    @SystemChar(charsetDef = "ASCII文字")
    public String userPassword;

    /** プロジェクトID **/
    @Digits(integer = 9)
    public String projectId;

    /** プロジェクト名 **/
    @Length(max = 128, message = "{domainType.projectName.message}")
    @SystemChar(charsetDef = "全角文字", message = "{domainType.projectName.message}")
    public String projectName;

    /** プロジェクト種別 **/
    @CodeValue(codeId = "C0300001", pattern = "PATTERN01")
    public String projectType;

    /** プロジェクト分類 **/
    @CodeValue(codeId = "C0200001", pattern = "PATTERN01")
    public String projectClass;

    /** 顧客ID **/
	@Digits(integer = 9)
    public String clientId;

    /** 日付 **/
	@YYYYMMDD()
    public String date;

    /** ユーザ氏名（漢字） **/
    @Length(max = 128, message = "{domainType.userName.message}")
    @SystemChar(charsetDef = "全角文字", message = "{domainType.userName.message}")
    public String userName;

    /** 備考 **/
    @Length(max = 512, message = "{domainType.note.message}")
    @SystemChar(charsetDef = "システム許容文字", allowLineSeparator = true, message = "{domainType.note.message}")
    public String note;

    /** 金額 **/
    @MoneyRange(max = 999999999)
    public String amountOfMoney;

    /** ページ番号 **/
    @NumberRange(min = 1, max = 9999)
    public String pageNumber;

    /** ユーザID **/
    @NumberRange(max = 999999999)
    @SystemChar(charsetDef = "半角数字")
    public String userId;

    /** ユーザID_FK **/
    @NumberRange(max = 999999999)
    @SystemChar(charsetDef = "半角数字")
    public String userIdFk;

    /** 組織ID_FK **/
    @NumberRange(max = 9999)
    @SystemChar(charsetDef = "半角数字")
    public String organizationIdFk;

    /** 組織名 **/
    @Length(max = 120)
    @SystemChar(charsetDef = "全角文字")
    public String organizationName;

    /** プロジェクトID_FK **/
    @NumberRange(max = 999999999)
    @SystemChar(charsetDef = "半角数字")
    public String projectIdFk;

    /** 日時 **/
    @Length(min = 19, max = 19)
    @SystemChar(charsetDef = "半角文字")
    public String dateTime;

    /** 適用開始日 **/
    @Length(min = 8, max = 8)
    @SystemChar(charsetDef = "半角文字")
    public String applyStartDate;

    /** 適用終了日 **/
    @Length(min = 8, max = 8)
    @SystemChar(charsetDef = "半角文字")
    public String applyEndDate;

    /** ユーザ氏名（ふりがな） **/
    @Length(max = 128)
    @SystemChar(charsetDef = "全角文字")
    public String kanaNme;

    /** バージョン番号 **/
    @SystemChar(charsetDef = "半角数字")
    public String versionNo;

    /** 失敗回数 **/
    @SystemChar(charsetDef = "半角数字")
    public String failedCount;

    /** フラグ **/
    @Length(min = 1, max = 1)
    @SystemChar(charsetDef = "半角数字")
    public String flag;

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

    /** セッションID **/
    @Length(max = 100)
    @SystemChar(charsetDef = "半角英数字")
    public String sessionId;

    /** セッションオブジェクト **/
    public String sessionObject;

    /** トークン **/
    @Length(max = 36)
    @SystemChar(charsetDef = "半角英数字")
    public String token;

    /** 区分 **/
    @Length(min = 2, max = 2)
    @SystemChar(charsetDef = "半角英数字")
    public String segmentId;

    /** 業務日付 **/
    @Length(min = 8, max = 8)
    @SystemChar(charsetDef = "半角数字")
    public String bizDate;

}
