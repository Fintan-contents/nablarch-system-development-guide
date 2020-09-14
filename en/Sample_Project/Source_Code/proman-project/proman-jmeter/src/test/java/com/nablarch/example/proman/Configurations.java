package com.nablarch.example.proman;

import java.util.ResourceBundle;

/**
 * Class for accessing information in the configuration file ({@code env.properties}).
 * @author Tanaka Tomoyuki
 */
public class Configurations {
    private final ResourceBundle env = ResourceBundle.getBundle("env");

    /**
     * Get the value of {@code "jmeter.home"}.
     * @return The value of {@code "jmeter.home"}
     */
    public String getJmeterHome() {
        return env.getString("jmeter.home");
    }

    /**
     * Get the value of {@code "server.host"}.
     * @return The value of {@code "server.host"}
     */
    public String getServerHost() {
        return env.getString("server.host");
    }

    /**
     * Get the value of {@code "server.port"}.
     * @return The value of {@code "server.port"}
     */
    public String getServerPort() {
        return env.getString("server.port");
    }

    /**
     * Get the value of {@code "response.encoding"}.
     * @return The value of {@code "response.encoding"}
     */
    public String getResponseEncoding() {
        return env.getString("response.encoding");
    }

    /**
     * Get the value of {@code "database.driver"}.
     * @return The value of {@code "database.driver"}
     */
    public String getDatabaseDriver() {
        return env.getString("database.driver");
    }

    /**
     * Get the value of {@code "database.url"}.
     * @return The value of {@code "database.url"}
     */
    public String getDatabaseUrl() {
        return env.getString("database.url");
    }

    /**
     * Get the value of {@code "database.schema"}.
     * @return The value of {@code "database.schema"}
     */
    public String getDatabaseSchema() {
        if (env.containsKey("database.schema")) {
            return env.getString("database.schema");
        } else {
            return null;
        }
    }

    /**
     * Get the value of {@code "database.username"}.
     * @return The value of {@code "database.username"}
     */
    public String getDatabaseUsername() {
        return env.getString("database.username");
    }

    /**
     * Get the value of {@code "database.password"}.
     * @return The value of {@code "database.password"}
     */
    public String getDatabasePassword() {
        return env.getString("database.password");
    }
}
