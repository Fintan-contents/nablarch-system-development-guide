package com.nablarch.example.climan.common.validation;

import nablarch.common.code.validator.ee.CodeValue;
import nablarch.core.validation.ee.Digits;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.SystemChar;

/**
 * Domain definition.
 */
public class ClimanDomainType {

    /** Client ID */
    @Digits(integer = 9)
    private String clientId;

    /** Client name */
    @Length(max = 128)
    @SystemChar(charsetDef = "システム許容文字") // Characters permitted by the system
    private String clientName;

    /** Industry code */
    @CodeValue(codeId = "C0100001")
    private String industryCode;

    /** Code ID **/
    @Length(max = 8)
    @SystemChar(charsetDef = "半角英数字") // Alphanumeric characters
    public String codeId;

    /** Code Value **/
    @Length(max = 2)
    @SystemChar(charsetDef = "半角英数字") // Alphanumeric characters
    public String codeValue;

    /** Code Name **/
    @Length(max = 50)
    @SystemChar(charsetDef = "システム許容文字") // Characters permitted by the system
    public String codeName;

    /** Option **/
    @Length(max = 40)
    @SystemChar(charsetDef = "システム許容文字") // Characters permitted by the system
    public String option;

    /** Pattern **/
    @Length(min = 1, max = 1)
    @SystemChar(charsetDef = "半角英数字") // Alphanumeric characters
    public String pattern;

    /** Sort Order **/
    @Length(min = 1, max = 1)
    @SystemChar(charsetDef = "半角数字") // Numbers
    public String sortOrder;

    /** Lang **/
    @Length(max = 2)
    @SystemChar(charsetDef = "半角英字") // Half-width alphabetic characters    
    public String lang;

    /** Shor tName **/
    @Length(max = 50)
    @SystemChar(charsetDef = "システム許容文字") // Characters permitted by the system
    public String shortName;
}
