package com.nablarch.example.proman.common.validation;

import nablarch.core.util.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator to verify that the specified integer is a monetary value within the range.
 *
 * @author Nabu Rakutaro
 */
public class MoneyRangeValidator implements ConstraintValidator<MoneyRange, String> {

    /**
     * Minimum value
     */
    private long min;

    /**
     * Maximum value
     */
    private long max;

    /**
     * Initializes minimum and maximum value.
     *
     * @param constraintAnnotation: Annotation assigned to target property
     */
    @Override
    public void initialize(MoneyRange constraintAnnotation) {
        max = constraintAnnotation.max();
        min = constraintAnnotation.min();
    }

    /**
     * Verifies whether the target value is within the specified range ({@code min} - {@code max}).
     *
     * @param value: Target value
     * @param context: Validation context
     * @return: {@code true} if the value is within the range
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtil.isNullOrEmpty(value)) {
            return true;
        }

        long number;
        try {
            number = Long.parseLong(value);
        } catch (NumberFormatException ignored) {
            return false;
        }

        return number >= min && number <= max;
    }
}
