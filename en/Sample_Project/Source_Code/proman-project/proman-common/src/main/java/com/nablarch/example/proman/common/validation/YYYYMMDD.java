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
 * A class for validating whether an input value is a date string in the specified format.
 * <p/>
 * The default date format is "yyyyMMdd".
 *
 * @author Masaya Seko
 */
@Documented
@Constraint(validatedBy = YYYYMMDDValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface YYYYMMDD {

    /**
     * Acquires group.
     *
     * @return Group
     */
    Class<?>[] groups() default {};

    /**
     * Message set when a validation error occurs.
     *
     * @return Message
     */
    String message() default "{com.nablarch.example.app.entity.core.validation.validator.YYYYMMDD.message}";

    /**
     * Acquires payload.
     *
     * @return Payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Permitted format
     *
     * @return Specified format
     */
    String allowFormat() default "yyyy/MM/dd";

    /**
     * Annotation to specify multiple values
     *
     * @author Masaya Seko
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * Acquires YYYYMMDD array.
         *
         * @return Specified YYYYMMDD array
         */
        YYYYMMDD[] value();
    }

}
