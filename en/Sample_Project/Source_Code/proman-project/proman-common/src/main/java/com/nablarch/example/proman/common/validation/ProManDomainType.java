package com.nablarch.example.proman.common.validation;

import nablarch.common.code.validator.ee.CodeValue;
import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.NumberRange;
import nablarch.core.validation.ee.SystemChar;

/**
 * Enumerated type indicating domain definition.
 *
 * @author Masaya Seko
 */
@SuppressWarnings("all")
public class ProManDomainType {

    /** Organization ID */
    @Digits(integer = 4)
    public String organizationId;

    /** Login ID */
    @Length(max = 20)
    @SystemChar(charsetDef = "半角数字") // Single byte numeric characters
    public String loginId;

    /** User Password */
    @Length(max = 44)
    @SystemChar(charsetDef = "ASCII文字")
    public String userPassword;

    /** Project ID */
    @Digits(integer = 9)
    public String projectId;

    /** Project name */
    @Length(max = 128, message = "{domainType.projectName.message}")
    @SystemChar(charsetDef = "システム許容文字", message = "{domainType.projectName.message}") // Characters permitted by system
    public String projectName;

    /** Project type code value */
    @CodeValue(codeId = "C0300001", pattern = "PATTERN01")
    public String projectType;

    /** Project classification code value */
    @CodeValue(codeId = "C0200001", pattern = "PATTERN01")
    public String projectClass;

    /** Client ID **/
	@Digits(integer = 9)
    public String clientId;

    /** Date */
    @YYYYMMDD()
    public String date;

    /** User name (Kanji characters) */
    @Length(max = 128, message = "{domainType.userName.message}")
    @SystemChar(charsetDef = "システム許容文字", message = "{domainType.userName.message}") // Characters permitted by system
    public String userName;

    /** Note */
    @Length(max = 512, message = "{domainType.note.message}")
    @SystemChar(charsetDef = "システム許容文字", allowLineSeparator = true, message = "{domainType.note.message}") // Characters permitted by system
    public String note;

    /** Amount of money */
    @MoneyRange(max = 999999999)
    public String amountOfMoney;

    /** Page number **/
    @NumberRange(min = 1, max = 9999)
    public String pageNumber;

    /** User ID **/
    @NumberRange(max = 999999999)
    @SystemChar(charsetDef = "半角数字")
    public String userId;

    /** User ID_FK **/
    @NumberRange(max = 999999999)
    @SystemChar(charsetDef = "半角数字")
    public String userIdFk;

    /** Organization ID_FK **/
    @NumberRange(max = 9999)
    @SystemChar(charsetDef = "半角数字")
    public String organizationIdFk;

    /** Organization name **/
    @Length(max = 128)
    @SystemChar(charsetDef = "システム許容文字")
    public String organizationName;

    /** Project ID_FK **/
    @NumberRange(max = 999999999)
    @SystemChar(charsetDef = "半角数字")
    public String projectIdFk;

    /** Date time **/
    @Length(min = 19, max = 19)
    @SystemChar(charsetDef = "半角文字")
    public String dateTime;

    /** Apply start date **/
    @Length(min = 8, max = 8)
    @SystemChar(charsetDef = "半角文字")
    public String applyStartDate;

    /** Apply end date **/
    @Length(min = 8, max = 8)
    @SystemChar(charsetDef = "半角文字")
    public String applyEndDate;

    /** User Name(Kana characters) **/
    @Length(max = 128)
    @SystemChar(charsetDef = "システム許容文字")
    public String kanaNme;

    /** Version number */
    @SystemChar(charsetDef = "半角数字")
    public String versionNo;

    /** Failed count **/
    @SystemChar(charsetDef = "半角数字")
    public String failedCount;

    /** Flag **/
    @Length(min = 1, max = 1)
    @SystemChar(charsetDef = "半角数字")
    public String flag;

    /** Code ID **/
    @Length(max = 8)
    @SystemChar(charsetDef = "半角英数字")
    public String codeId;

    /** Code value **/
    @Length(max = 2)
    @SystemChar(charsetDef = "半角英数字")
    public String codeValue;

    /** Code name **/
    @Length(max = 50)
    @SystemChar(charsetDef = "システム許容文字")
    public String codeName;

    /** Option **/
    @Length(max = 40)
    @SystemChar(charsetDef = "システム許容文字")
    public String option;

    /** Pattern **/
    @Length(min = 1, max = 1)
    @SystemChar(charsetDef = "半角英数字")
    public String pattern;

    /** Sort order **/
    @Length(min = 1, max = 1)
    @SystemChar(charsetDef = "半角数字")
    public String sortOrder;

    /** lang **/
    @Length(max = 2)
    @SystemChar(charsetDef = "半角英字")
    public String lang;

    /** Short Name **/
    @Length(max = 50)
    @SystemChar(charsetDef = "システム許容文字")
    public String shortName;

    /** Session ID **/
    @Length(max = 100)
    @SystemChar(charsetDef = "半角英数字")
    public String sessionId;

    /** Session Object **/
    public String sessionObject;

    /** Token **/
    @Length(max = 36)
    @SystemChar(charsetDef = "半角英数字")
    public String token;

    /** Segment ID **/
    @Length(min = 2, max = 2)
    @SystemChar(charsetDef = "半角英数字")
    public String segmentId;

    /** bizDate **/
    @Length(min = 8, max = 8)
    @SystemChar(charsetDef = "半角数字")
    public String bizDate;

}
