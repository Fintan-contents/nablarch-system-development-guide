package com.nablarch.example.proman.assertion.db;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import java.sql.Connection;

/**
 * Class for creating {@link DatabaseConnection} of DBUnit.
 *
 * @author Tanaka Tomoyukiスキーマ
 */
public class DBUnitConnectionBuilder {

    /**
     * Creates {@link DatabaseConnection} based on a JDBC connection.
     * @param jdbcConnection JDBC Connection
     * @param schema Schema
     * @param driver The fully qualified name of the JDBC driver
     * @return The generated {@link DatabaseConnection}
     */
    public static DatabaseConnection build(Connection jdbcConnection, String schema, String driver) {
        try {
            DatabaseConnection conn = new DatabaseConnection(jdbcConnection, schema);
            DatabaseConfig config = conn.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, createDataTypeFactory(driver));

            return conn;
        } catch (DatabaseUnitException e) {
            throw new RuntimeException(e);
        }
    }

    private static DefaultDataTypeFactory createDataTypeFactory(String driver) {
        // A warning appears if the DataTypeFactory is not set to match the database product
        if (driver.equals("org.postgresql.Driver")) {
            return new PostgresqlDataTypeFactory();
        } else if (driver.equals("oracle.jdbc.driver.OracleDriver")) {
            return new OracleDataTypeFactory();
        } else {
            return new DefaultDataTypeFactory();
        }
    }
}
