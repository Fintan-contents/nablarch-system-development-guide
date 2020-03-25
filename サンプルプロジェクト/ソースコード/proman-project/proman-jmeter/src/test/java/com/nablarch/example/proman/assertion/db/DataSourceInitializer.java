package com.nablarch.example.proman.assertion.db;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * テストで使用する{@link DataSource}の初期化と取得を行うクラス。
 *
 * @author Tanaka Tomoyuki
 */
public class DataSourceInitializer {

    private final DataSource ds;

    /**
     * コンストラクタ。
     * @param driver JDBCドライバ
     * @param url JDBC接続URL
     * @param username ユーザ名
     * @param password パスワード
     */
    public DataSourceInitializer(String driver, String url, String username, String password) {
        ds = create(driver, url, username, password);
    }

    private DataSource create(String driver, String url, String username, String password) {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(driver);
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    /**
     * このインスタンスが管理するデータソースから、コネクションを取得する。
     * @return コネクション
     */
    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
