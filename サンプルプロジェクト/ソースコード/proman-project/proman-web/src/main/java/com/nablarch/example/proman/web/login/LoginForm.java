package com.nablarch.example.proman.web.login;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.ee.Required;

import java.io.Serializable;

/**
 * ログイン入力フォーム。
 *
 * @author Masaya Seko
 */
public class LoginForm implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** ログインID */
    @Required
    @Domain("loginId")
    private String loginId;

    /** パスワード */
    @Required
    @Domain("userPassword")
    private String userPassword;

    /**
     * ログインIDを取得する。
     * @return ログインID
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * ログインIDを設定する。
     * @param loginId ログインID
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * パスワードを取得する。
     * @return パスワード
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * パスワードを設定する。
     * @param userPassword パスワード
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
