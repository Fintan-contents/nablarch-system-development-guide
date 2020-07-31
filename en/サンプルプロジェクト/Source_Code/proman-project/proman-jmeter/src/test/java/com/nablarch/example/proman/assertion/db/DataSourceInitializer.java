package com.nablarch.example.proman.assertion.db;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for initializing and retrieving {@link DataSource} for use in tests.
 *
 * @author Tanaka Tomoyuki
 */
public class DataSourceInitializer {

    private final DataSource ds;

    /**
     * Constructor.
     * @param driver JDBC Driver
     * @param url JDBC Connection URL
     * @param username username
     * @param password password
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
     * Get a connection from the data source managed by this instance.
     * @return connection
     */
    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
