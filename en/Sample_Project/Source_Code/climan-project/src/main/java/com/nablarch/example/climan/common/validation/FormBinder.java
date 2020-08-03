package com.nablarch.example.climan.common.validation;

import nablarch.common.web.interceptor.InjectForm;
import nablarch.common.web.validator.BeanValidationStrategy;
import nablarch.common.web.validator.ValidationStrategy;
import nablarch.core.message.ApplicationException;
import nablarch.core.repository.SystemRepository;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.servlet.ServletExecutionContext;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * A class for binding request parameters to forms.
 *
 * @author Tsuyoshi Kawasaki
 * @since 1.0
 * @param <T> Type of form class
 */
public class FormBinder<T extends Serializable> {

    /** Converted form class */
    private final Class<T> formClass;

    /** Implementation of validation */
    private final ValidationStrategy strategy;

    /**
     * Set the information to be used as the source for binding.
     * @param request HTTP request
     * @param context Context for execution
     * @return Input source information
     */
    public static BindingSource from(HttpRequest request, ExecutionContext context) {
        return from(request, context, "");
    }

    /**
     * Set the information to be used as the source for binding.
     * @param request HTTP request
     * @param context Context for execution
     * @param prefix Prefix for request parameters
     * @return Input source information
     */

    public static BindingSource from(HttpRequest request, ExecutionContext context, String prefix) {
        return new BindingSource(request, context, prefix);
    }

    /**
     * Constructor.
     * @param formClass Form class
     */
    private FormBinder(Class<T> formClass) {
        this(formClass, getDefaultStrategy());
    }

    /**
     * Constructor.
     * @param formClass Form class
     * @param strategy Implementation of validation
     */
    private FormBinder(Class<T> formClass, ValidationStrategy strategy) {
        this.formClass = formClass;
        this.strategy = strategy;
    }

    /**
     * Class indicating information to be used as an input source for binding.
     *
     * @author Tsuyoshi Kawasaki
     */
    public static class BindingSource {

        /** Prefix for request parameters */
        private final String prefix;

        /** Context for execution */
        private final ServletExecutionContext context;

        /** HTTP request */
        private final HttpRequest request;

        /**
         * Constructor.
         * @param request HTTP request
         * @param context Context for execution
         * @param prefix Prefix for request parameters
         */
        BindingSource(HttpRequest request, ExecutionContext context, String prefix) {
            this.prefix = prefix;
            this.context = (ServletExecutionContext) context;
            this.request = request;
        }

        /**
         * Binds to a specified form.
         * @param formClass Form class
         * @param <T> Type of form class
         * @return form
         */
        public <T extends Serializable> BindingResult<T> to(Class<T> formClass) {
            FormBinder<T> formBinder = new FormBinder<>(formClass);
            return formBinder.bindFrom(this);
        }

    }

    /**
     * Acquires implementation of {@link ValidationStrategy} from {@link SystemRepository}.
     * @return Implementation of {@link ValidationStrategy}
     */
    private static BeanValidationStrategy getDefaultStrategy() {
        return Objects.requireNonNull(SystemRepository.get("validationStrategy"));
    }

    /**
     * Bindings from the source information to a form.
     *
     * @param source Source information
     * @return Binding results
     */
    private BindingResult<T> bindFrom(BindingSource source) {
        InjectForm annotation = createAnnotation(formClass, source.prefix);
        try {
            @SuppressWarnings("unchecked")
            T validForm = (T) strategy.validate(source.request, annotation, false, source.context);
            return new BindingResult.ValidBindingResult<>(validForm);
        } catch (ApplicationException e) {
            return new BindingResult.InvalidBindingResult<>(e);
        }

    }

    /**
     * Create a {@link InjectForm} instance.
     *
     * @param formClass Form class
     * @param prefix Prefix
     * @return {@link InjectForm} Instance
     */
    private static InjectForm createAnnotation(Class<? extends Serializable> formClass, String prefix) {

        return new InjectForm() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return InjectForm.class;
            }

            @Override
            public Class<? extends Serializable> form() {
                return formClass;
            }

            @Override
            public String prefix() {
                return prefix;
            }

            @Override
            public String name() {
                return "form"; // Not used.
            }

            @Override
            public String initialize() {
                return "";  // Not used.
            }

            @Override
            public String validate() {
                return "";  // Not used.
            }
        };
    }

}
