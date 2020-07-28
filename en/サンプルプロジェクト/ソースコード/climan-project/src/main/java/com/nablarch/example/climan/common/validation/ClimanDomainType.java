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
    @Digits(integer = 10)
    private String clientId;

    /** Client name */
    @Length(max = 128)
    @SystemChar(charsetDef = "システム許容文字") // Characters permitted by the system
    private String clientName;

    /** Industry code */
    @CodeValue(codeId = "C0100001")
    private String industryCode;
}
