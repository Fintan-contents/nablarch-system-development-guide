package com.nablarch.example.proman.web.common.validation;

import nablarch.core.message.ApplicationException;
import nablarch.core.message.Message;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.Required;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.MockHttpRequest;
import nablarch.fw.web.servlet.ServletExecutionContext;
import nablarch.test.junit5.extension.NablarchTest;
import nablarch.test.support.web.servlet.MockServletContext;
import nablarch.test.support.web.servlet.MockServletResponse;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nablarch.test.Assertion.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@NablarchTest
class FormBinderTest {
    private Input input = new Input();

    @Test
    void Formへの変換ができること() {
        input.setParam("loginId", "aaa");
        input.setParam("loginPassword", "bbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext()).to(TestForm.class);
        result.abortIfInvalid();  // validなので例外は発生しない

        TestForm form = result.getValidForm();
        assertThat(form.getLoginId(), is("aaa"));
        assertThat(form.getLoginPassword(), is("bbbb"));
    }

    @Test
    void prefix付きでFormへの変換ができること() {
        input.setParam("prefix.loginId", "aaa");
        input.setParam("prefix.loginPassword", "bbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext(), "prefix")
                .to(TestForm.class);
        result.abortIfInvalid();  // validなので例外は発生しない

        TestForm form = result.getValidForm();
        assertThat(form.getLoginId(), is("aaa"));
        assertThat(form.getLoginPassword(), is("bbbb"));
    }

    @Test
    void バリデーションエラーになること() {
        input.setParam("loginId", (String) null);
        input.setParam("loginPassword", "bbbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext()).to(TestForm.class);
        assertThat(result.isValid(), is(false));

        try {
            result.abortIfInvalid();
            fail();
        } catch (ApplicationException e) {
            List<Message> messages = e.getMessages();
            assertThat(messages.get(0).formatMessage(), is("パスワードの長さは最大4です!"));
            assertThat(messages.get(1).formatMessage(), is("ログインIDは必ず入力してください!"));
        }

    }

    @Test
    void バリデーションエラーの場合formは取得できないこと() {
        input.setParam("loginId", (String) null);
        input.setParam("loginPassword", "bbbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext()).to(TestForm.class);
        assertThat(result.isValid(), is(false));

        // バリデーションに成功していない状態でフォームを取得しようとする
        assertThrows(IllegalStateException.class, result::getValidForm);
    }

    private static class Input {
        private final Map<String, String[]> paramMap = new HashMap<>();

        void setParam(String key, String value) {
            paramMap.put(key, new String[] { value });
        }

        void setParam(String key, String[] values) {
            paramMap.put(key, values);
        }

        HttpRequest getHttpRequest() {
            HttpRequest request = new MockHttpRequest();
            request.setParamMap(paramMap);
            return request;
        }

        @SuppressWarnings("unchecked")
        ServletExecutionContext getContext() {
            PromanMockServletRequest servletReq = new PromanMockServletRequest();
            servletReq.getParameterMap().putAll(paramMap);
            return new ServletExecutionContext(servletReq, new MockServletResponse(), new MockServletContext());
        }

    }


    public static class TestForm implements Serializable {

        /**
         * ログインID
         */
        @Required(message = "ログインIDは必ず入力してください!")
        private String loginId;

        /**
         * パスワード
         */
        @Length(max = 4, message = "パスワードの長さは最大4です!")
        private String loginPassword;

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }

        public String getLoginPassword() {
            return loginPassword;
        }

        public void setLoginPassword(String loginPassword) {
            this.loginPassword = loginPassword;
        }
    }
}