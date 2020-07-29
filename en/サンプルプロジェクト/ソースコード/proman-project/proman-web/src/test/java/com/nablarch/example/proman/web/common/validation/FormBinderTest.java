package com.nablarch.example.proman.web.common.validation;

import nablarch.test.support.web.servlet.MockServletRequest;

import nablarch.core.message.ApplicationException;
import nablarch.core.message.Message;
import nablarch.core.validation.ee.Length;
import nablarch.core.validation.ee.Required;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.MockHttpRequest;
import nablarch.fw.web.servlet.ServletExecutionContext;
import nablarch.test.TestSupport;
import nablarch.test.support.web.servlet.MockServletContext;
import nablarch.test.support.web.servlet.MockServletResponse;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nablarch.test.Assertion.fail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FormBinderTest extends TestSupport {

    private Input input = new Input();

    /** Constructor */
    public FormBinderTest() {
        super(FormBinderTest.class);
    }

    @Test
    public void canBeConvertedToTestFormClass() {
        input.setParam("loginId", "aaa");
        input.setParam("loginPassword", "bbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext()).to(TestForm.class);
        result.abortIfInvalid();  // Exception does not occur as this is valid

        TestForm form = result.getValidForm();
        assertThat(form.getLoginId(), is("aaa"));
        assertThat(form.getLoginPassword(), is("bbbb"));
    }

    @Test
    public void canBeConvertedToTestFormClassWithPrifix() {
        input.setParam("prefix.loginId", "aaa");
        input.setParam("prefix.loginPassword", "bbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext(), "prefix")
                .to(TestForm.class);
        result.abortIfInvalid();  // Exception does not occur as this is valid

        TestForm form = result.getValidForm();
        assertThat(form.getLoginId(), is("aaa"));
        assertThat(form.getLoginPassword(), is("bbbb"));
    }

    @Test
    public void tryCatchApplicationException() {
        input.setParam("loginId", (String) null);
        input.setParam("loginPassword", "bbbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext()).to(TestForm.class);
        assertThat(result.isValid(), is(false));

        try {
            result.abortIfInvalid();
            fail();
        } catch (ApplicationException e) {
            List<Message> messages = e.getMessages();
            assertThat(messages.get(0).formatMessage(), is("Maximum password length is 4 characters!"));
            assertThat(messages.get(1).formatMessage(), is("Login ID must be entered!"));
        }

    }

    @Test(expected = IllegalStateException.class)
    public void バリデーションエラーの場合formは取得できないこと() {
        input.setParam("loginId", (String) null);
        input.setParam("loginPassword", "bbbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext()).to(TestForm.class);
        assertThat(result.isValid(), is(false));

        // バリデーションに成功していない状態でフォームを取得しようとする
        result.getValidForm();
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
