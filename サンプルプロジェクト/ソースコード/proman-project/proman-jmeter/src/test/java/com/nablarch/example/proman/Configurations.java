package com.nablarch.example.proman;

import java.util.ResourceBundle;

/**
 * 設定ファイル({@code env.properties})の情報にアクセスするためのクラス。
 * @author Tanaka Tomoyuki
 */
public class Configurations {
    private final ResourceBundle env = ResourceBundle.getBundle("env");

    /**
     * 設定値 {@code "jmeter.home"} の値を取得する。
     * @return {@code "jmeter.home"} の値
     */
    public String getJmeterHome() {
        return env.getString("jmeter.home");
    }

    /**
     * 設定値 {@code "database.driver"} の値を取得する。
     * @return {@code "database.driver"} の値
     */
    public String getDatabaseDriver() {
        return env.getString("database.driver");
    }

    /**
     * 設定値 {@code "database.url"} の値を取得する。
     * @return {@code "database.url"} の値
     */
    public String getDatabaseUrl() {
        return env.getString("database.url");
    }

    /**
     * 設定値 {@code "database.username"} の値を取得する。
     * @return {@code "database.username"} の値
     */
    public String getDatabaseUsername() {
        return env.getString("database.username");
    }

    /**
     * 設定値 {@code "database.password"} の値を取得する。
     * @return {@code "database.password"} の値
     */
    public String getDatabasePassword() {
        return env.getString("database.password");
    }
}
