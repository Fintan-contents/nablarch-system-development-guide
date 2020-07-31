package com.nablarch.example.climan.common.validation;

import nablarch.core.message.ApplicationException;

import java.io.Serializable;

/**
 * Interface indicating resulting of binding from request parameters to forms.
 *
 * @param <T> Type of form class
 * @author Tsuyoshi Kawasaki
 */
public interface BindingResult<T extends Serializable> {

    /**
     * Indicates whether validation results are suitable.
     * @return True when validation is successful
     */
    boolean isValid();

    /**
     * An exception is sent when the validation results are unsuitable.
     * Used for purposes such as screen transitions for {@link nablarch.fw.web.interceptor.OnError}.
     * If the validation results are suitable, nothing happens.
     *
     * Calling with this method is equivalent to the following code.
     * <pre>
     *     if (!bindingResult.isValid()) {
     *         throwApplicationException();
     *     }
     * </pre>
     *
     * @throws ApplicationException Exception where validation results are stored
     */
    void abortIfInvalid();

    /**
     * {@link ApplicationException} is sent, containing a validation error message.
     * Used for purposes such as screen transitions for {@link nablarch.fw.web.interceptor.OnError}.
     * An exception occurs when this method is started on successful validation (standard program bug).
     *
     * @throws ApplicationException Always sent when validation fails
     * @throws IllegalStateException When this method is started regardless of successful validation
     */
    void throwApplicationException();

    /**
     * Acquires forms with suitable validation results.
     * An exception occurs when this method is started on unsuccessful validation (standard program bug).
     *
     * @return form
     * @throws IllegalStateException When validation results are unsuitable
     */
    T getValidForm();

    /**
     * Class indicating binding results.
     * @param <T> Type of form class
     * @author Tsuyoshi Kawasaki
     */
    class InvalidBindingResult<T extends Serializable> implements BindingResult<T> {

        /** Exception occurring in validation */
        private final ApplicationException originalException;

        /**
         * Constructor.
         * @param exception Exception occurring in validation
         */
        InvalidBindingResult(ApplicationException exception) {
            this.originalException = exception;
        }

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public void abortIfInvalid() {
            throwApplicationException();
        }

        @Override
        public void throwApplicationException() {
            // Exception is generated again to recreate stack tracing.
            throw new ApplicationException(originalException.getMessages());
        }

        @Override
        public T getValidForm() {
            throw new IllegalStateException("getValidForm method must not be called when validation failed. " +
                    "error messages=[" + originalException.getMessage() + "]");
        }

    }

    /**
     * Class indicating binding results.
     * @param <T> Type of form class
     * @author Tsuyoshi Kawasaki
     */
    class ValidBindingResult<T extends Serializable> implements BindingResult<T> {

        /** Form */
        private final T validForm;

        /**
         * Constructor.
         * @param form Valid form
         */
        ValidBindingResult(T form) {
            this.validForm = form;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public void abortIfInvalid() {
            // No action is needed, as validation is successful.
        }

        @Override
        public void throwApplicationException() {
            throw new IllegalStateException("throwApplicationException method must not be called when validation succeeded.");
        }

        @Override
        public T getValidForm() {
            return validForm;
        }

    }
}
