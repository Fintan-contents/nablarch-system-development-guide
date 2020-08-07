package com.nablarch.example.proman.common.validation;

import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.NumberRange;
import nablarch.core.validation.ee.SystemChar;
import nablarch.common.code.validator.ee.CodeValue;

/**
 * Enumerated type indicating domain definition.
 *
 * @author Masaya Seko
 */
@SuppressWarnings("all")
public class ProManDomainType {

    /**
     * ID
     */
    @Digits(integer = 9)
    private String id;

    /**
     * Organization ID
     */
    @Digits(integer = 4)
    private String organizationId;

    /**
     * Login ID
     */
    @Length(max = 20)
    @SystemChar(charsetDef = "半角数字") // Single byte numeric characters
    private String loginId;

    /**
     * Password
     */
    @Length(max = 44)
    private String password;

    /**
     * Project name
     */
    @Length(max = 128, message = "{domainType.projectName.message}")
    @SystemChar(charsetDef = "全角文字", message = "{domainType.projectName.message}") // Double-byte characters
    private String projectName;

    /**
     * Project type code value
     */
    @CodeValue(codeId = "C0300001", pattern = "PATTERN01")
    private String projectType;

    /**
     * Project classification code value
     */
    @CodeValue(codeId = "C0200001", pattern = "PATTERN01")
    private String projectClass;

    /**
     * Date
     */
    @YYYYMMDD()
    private String date;

    /**
     * Date (with slashes)
     */
    @YYYYMMDD(allowFormat = "yyyy/MM/dd")
    private String dateWithSlash;

    /**
     * User name
     */
    @Length(max = 128, message = "{domainType.userName.message}")
    @SystemChar(charsetDef = "全角文字", message = "{domainType.userName.message}") // Double-byte characters
    private String userName;

    /**
     * Note
     */
    @Length(max = 512, message = "{domainType.note.message}")
    @SystemChar(charsetDef = "システム許容文字", allowLineSeparator = true, message = "{domainType.note.message}") // Characters permitted by system
    private String note;

    /**
     * Amount of money
     */
    @MoneyRange(max = 999999999)
    private String amountOfMoney;

    /**
     * Version number
     */
    @Digits(integer = 4)
    private String version;

    /**
     * Page number
     */
    @NumberRange(min = 1, max = 9999)
    private String pageNumber;
}
