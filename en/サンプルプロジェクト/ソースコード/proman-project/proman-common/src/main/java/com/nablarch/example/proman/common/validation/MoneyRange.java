package com.nablarch.example.proman.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Class for validation of range of monetary values.
 *
 * @author Nabu Rakutaro
 */
@Documented
@Constraint(validatedBy = MoneyRangeValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface MoneyRange {

    /**
     * Acquires group.
     *
     * @return: Group
     */
    Class<?>[] groups() default {};

    /**
     * Message set when a validation error occurs.
     *
     * @return: Message
     */
    String message() default "{com.nablarch.example.app.entity.core.validation.validator.MoneyRange.message}";

    /**
     * Acquires payload.
     *
     * @return Payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Acquires minimum value.
     *
     * @return: Minimum value
     */
    long min() default 0;

    /**
     * Acquires maximum value.
     *
     * @return: Maximum value
     */
    long max() default Long.MAX_VALUE;

}
