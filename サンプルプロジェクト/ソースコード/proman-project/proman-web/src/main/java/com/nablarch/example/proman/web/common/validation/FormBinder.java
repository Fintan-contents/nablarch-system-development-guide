package com.nablarch.example.proman.web.common.validation;

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
 * リクエストパラメータをformにバインドするクラス。
 *
 * @author Tsuyoshi Kawasaki
 * @since 1.0
 * @param <T> フォームクラスの型
 */
public class FormBinder<T extends Serializable> {

    /** 変換後のフォームクラス */
    private final Class<T> formClass;

    /** バリデーション実装 */
    private final ValidationStrategy strategy;

    /**
     * バインド元になる情報を設定する。
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return 入力元情報
     */
    public static BindingSource from(HttpRequest request, ExecutionContext context) {
        return from(request, context, "");
    }

    /**
     * バインド元になる情報を設定する。
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @param prefix リクエストパラメータのプレフィックス
     * @return 入力元情報
     */

    public static BindingSource from(HttpRequest request, ExecutionContext context, String prefix) {
        return new BindingSource(request, context, prefix);
    }

    /**
     * コンストラクタ。
     * @param formClass フォームクラス
     */
    private FormBinder(Class<T> formClass) {
        this(formClass, getDefaultStrategy());
    }

    /**
     * コンストラクタ。
     * @param formClass フォームクラス
     * @param strategy バリデーション実装
     */
    private FormBinder(Class<T> formClass, ValidationStrategy strategy) {
        this.formClass = formClass;
        this.strategy = strategy;
    }

    /**
     * バインドの入力元情報を表すクラス。
     *
     * @author Tsuyoshi Kawasaki
     */
    public static class BindingSource {

        /** リクエストパラメータのプレフィックス */
        private final String prefix;

        /** 実行コンテキスト */
        private final ServletExecutionContext context;

        /** HTTPリクエスト */
        private final HttpRequest request;

        /**
         * コンストラクタ。
         * @param request HTTPリクエスト
         * @param context 実行コンテキスト
         * @param prefix リクエストパラメータのプレフィックス
         */
        BindingSource(HttpRequest request, ExecutionContext context, String prefix) {
            this.prefix = prefix;
            this.context = (ServletExecutionContext) context;
            this.request = request;
        }

        /**
         * 指定されたフォームへバインドを行う。
         * @param formClass フォームクラス
         * @param <T> フォームクラスの型
         * @return フォーム
         */
        public <T extends Serializable> BindingResult<T> to(Class<T> formClass) {
            FormBinder<T> formBinder = new FormBinder<>(formClass);
            return formBinder.bindFrom(this);
        }

    }

    /**
     * {@link SystemRepository}から{@link ValidationStrategy}の実装を取得する。
     * @return {@link ValidationStrategy}の実装
     */
    private static BeanValidationStrategy getDefaultStrategy() {
        return Objects.requireNonNull(SystemRepository.get("validationStrategy"));
    }

    /**
     * 元情報からフォームへのバインディングを行う。
     *
     * @param source 元情報
     * @return バインディング結果
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
     * {@link InjectForm}インスタンスを生成する。
     *
     * @param formClass フォームクラス
     * @param prefix プレフィックス
     * @return {@link InjectForm}インスタンス
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
                return "form"; // 使用しない。
            }

            @Override
            public String initialize() {
                return "";  // 使用しない。
            }

            @Override
            public String validate() {
                return "";  // 使用しない。
            }

            @Override
            public Class<?>[] validationGroup() {
                return new Class<?>[0];  // 使用しない。 }
            }
        };
    }

}
