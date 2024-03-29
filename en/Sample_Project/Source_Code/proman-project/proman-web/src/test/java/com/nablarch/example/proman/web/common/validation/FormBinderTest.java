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
    void canBeConvertedToTestFormClass() {
        input.setParam("loginId", "aaa");
        input.setParam("loginPassword", "bbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext()).to(TestForm.class);
        result.abortIfInvalid();  // Exception does not occur as this is valid

        TestForm form = result.getValidForm();
        assertThat(form.getLoginId(), is("aaa"));
        assertThat(form.getLoginPassword(), is("bbbb"));
    }

    @Test
    void canBeConvertedToTestFormClassWithPrifix() {
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
    void tryCatchApplicationException() {
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

    @Test
    void cannnotGetFormIfValidationFails() {
        input.setParam("loginId", (String) null);
        input.setParam("loginPassword", "bbbbb");

        BindingResult<TestForm> result = FormBinder.from(input.getHttpRequest(), input.getContext()).to(TestForm.class);
        assertThat(result.isValid(), is(false));

        // An attempt to acquire form when validation is unsuccessful
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
         * Login ID
         */
        @Required(message = "Login ID must be entered!")
        private String loginId;

        /**
         * Password
         */
        @Length(max = 4, message = "Maximum password length is 4 characters!")
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
