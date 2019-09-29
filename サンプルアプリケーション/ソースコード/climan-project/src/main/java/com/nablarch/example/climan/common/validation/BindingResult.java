package com.nablarch.example.climan.common.validation;

import nablarch.core.message.ApplicationException;

import java.io.Serializable;

/**
 * リクエストパラメータからformへのバインド結果を表すインタフェース。
 *
 * @param <T> フォームクラスの型
 * @author Tsuyoshi Kawasaki
 */
public interface BindingResult<T extends Serializable> {

    /**
     * バリデーション結果が妥当であるか判定する。
     * @return バリデーションに成功している場合、真
     */
    boolean isValid();

    /**
     * バリデーション結果が妥当でない場合、例外を送出する。
     * {@link nablarch.fw.web.interceptor.OnError}で画面遷移させる用途等に使用する。
     * バリデーション結果が妥当である場合、何も起こらない。
     *
     * このメソッドの呼び出しは以下のコードと等価である。
     * <pre>
     *     if (!bindingResult.isValid()) {
     *         throwApplicationException();
     *     }
     * </pre>
     *
     * @throws ApplicationException バリデーション結果を格納した例外
     */
    void abortIfInvalid();

    /**
     * バリデーションエラーのメッセージを格納した{@link ApplicationException}を送出する。
     * {@link nablarch.fw.web.interceptor.OnError}で画面遷移させる等の用途に使用する。
     * バリデーション成功時に本メソッドを起動すると例外が発生する(通常プログラムバグ)。
     *
     * @throws ApplicationException バリデーションが失敗している場合、必ず送出される
     * @throws IllegalStateException バリデーションが成功しているにもかかわらず本メソッドを起動した場合
     */
    void throwApplicationException();

    /**
     * バリデーション結果が妥当であるフォームを取得する。
     * バリデーション失敗時に本メソッドを起動すると例外が発生する(通常プログラムバグ)。
     *
     * @return フォーム
     * @throws IllegalStateException バリデーション結果が妥当でない場合
     */
    T getValidForm();

    /**
     * バインディング結果を表すクラス。
     * @param <T> フォームクラスの型
     * @author Tsuyoshi Kawasaki
     */
    class InvalidBindingResult<T extends Serializable> implements BindingResult<T> {

        /** バリデーションで発生した例外 */
        private final ApplicationException originalException;

        /**
         * コンストラクタ。
         * @param exception バリデーションで発生した例外
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
            // スタックトレースを作り直すため、例外を生成しなおす。
            throw new ApplicationException(originalException.getMessages());
        }

        @Override
        public T getValidForm() {
            throw new IllegalStateException("getValidForm method must not be called when validation failed. " +
                    "error messages=[" + originalException.getMessage() + "]");
        }

    }

    /**
     * バインディング結果を表すクラス。
     * @param <T> フォームクラスの型
     * @author Tsuyoshi Kawasaki
     */
    class ValidBindingResult<T extends Serializable> implements BindingResult<T> {

        /** フォーム */
        private final T validForm;

        /**
         * コンストラクタ。
         * @param form validなフォーム
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
            // バリデーションに成功しているため何もしない。
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
