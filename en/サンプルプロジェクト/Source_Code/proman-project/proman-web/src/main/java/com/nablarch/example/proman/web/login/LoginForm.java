package com.nablarch.example.proman.web.login;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import java.io.Serializable;

/**
 * Login input form.
 *
 * @author Nabu Rakutaro
 */
public class LoginForm implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** Login ID */
    @Required
    @Domain("loginId")
    private String loginId;

    /** Password */
    @Required
    @Domain("password")
    private String userPassword;

    /**
     * Acquires login ID.
     * @return: Login ID
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Set login ID.
     * @param loginId: Login ID
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * Acquires password.
     * @return: Password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Set password.
     * @param userPassword: Password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
