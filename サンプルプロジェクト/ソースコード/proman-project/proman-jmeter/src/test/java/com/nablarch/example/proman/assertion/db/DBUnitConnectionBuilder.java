package com.nablarch.example.proman.assertion.db;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import java.sql.Connection;

/**
 * DBUnitの{@link DatabaseConnection}を生成するためのクラス。
 *
 * @author Tanaka Tomoyuki
 */
public class DBUnitConnectionBuilder {

    /**
     * JDBCコネクションを元に{@link DatabaseConnection}を生成する。
     * @param jdbcConnection JDBCコネクション
     * @param driver JDBCドライバの完全修飾名
     * @return 生成した {@link DatabaseConnection}
     */
    public static DatabaseConnection build(Connection jdbcConnection, String driver) {
        try {
            DatabaseConnection conn = new DatabaseConnection(jdbcConnection);
            DatabaseConfig config = conn.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, createDataTypeFactory(driver));

            return conn;
        } catch (DatabaseUnitException e) {
            throw new RuntimeException(e);
        }
    }

    private static DefaultDataTypeFactory createDataTypeFactory(String driver) {
        // データベース製品に合わせて DataTypeFactory をセットしないと、警告が表示される
        if (driver.equals("org.postgresql.Driver")) {
            return new PostgresqlDataTypeFactory();
        } else if (driver.equals("oracle.jdbc.driver.OracleDriver")) {
            return new OracleDataTypeFactory();
        } else {
            return new DefaultDataTypeFactory();
        }
    }
}
