package com.nablarch.example.proman.common.validation;

import nablarch.core.util.DateUtil;
import nablarch.core.util.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Internal class in which a verification process is implemented.
 *
 * @author Masaya Seko
 */
public class YYYYMMDDValidator implements ConstraintValidator<YYYYMMDD, String> {

    /**
     * Permitted format
     */
    private String allowFormat;

    /**
     * Initializes the verification format.
     *
     * @param constraintAnnotation Annotation assigned to target property
     */
    @Override
    public void initialize(YYYYMMDD constraintAnnotation) {
        allowFormat = constraintAnnotation.allowFormat();
    }

    /**
     * Verifies whether the target value complies with the format specified in {@code allowFormat}.
     *
     * @param value Target value
     * @param context Validation context
     * @return {@code true} if the value complies with the format
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtil.isNullOrEmpty(value)) {
            return true;
        }
        try {
            return DateUtil.getParsedDate(value, allowFormat) != null;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }
}
