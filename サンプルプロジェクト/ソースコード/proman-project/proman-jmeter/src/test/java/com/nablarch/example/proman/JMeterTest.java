package com.nablarch.example.proman;

import com.nablarch.example.proman.assertion.db.DataSourceInitializer;
import com.nablarch.example.proman.assertion.db.DatabaseComparator;
import com.nablarch.example.proman.assertion.response.ResponseTestSupport;
import com.nablarch.example.proman.jmeter.JMeterRunner;
import com.nablarch.example.proman.loader.DatabaseLoader;
import com.nablarch.example.proman.scenario.Scenario;
import com.nablarch.example.proman.scenario.ScenarioFinder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

@RunWith(Parameterized.class)
public class JMeterTest {
    private static final Logger log = LoggerFactory.getLogger(JMeterTest.class);

    private static ResourceBundle properties;
    private static DataSourceInitializer dataSource;

    private final String name;
    private final Scenario scenario;

    public JMeterTest(String name, Scenario scenario) {
        this.name = name;
        this.scenario = scenario;
    }

    @BeforeClass
    public static void readProperties() {
        properties = ResourceBundle.getBundle("env");

        dataSource = new DataSourceInitializer(
            properties.getString("database.driver"),
            properties.getString("database.url"),
            properties.getString("database.username"),
            properties.getString("database.password")
        );
    }

    @Test
    public void test() throws Exception {
        log.debug("scenario name={} jmx={}", name, scenario.getJmxFile());

        log.debug("Setup Database...");
        DatabaseLoader loader = new DatabaseLoader();
        loader.loadFromExcel(scenario.getInsertFile());

        log.debug("Run JMeter");
        JMeterRunner runner = new JMeterRunner(properties.getString("jmeter.home"));
        runner.initialize();
        runner.runJMeter(scenario.getJmxFile());

        log.debug("Assert Response");
        List<Scenario.ResponseHtml> responseHtmlList = scenario.getResponseHtmlList();
        for (Scenario.ResponseHtml responseHtml : responseHtmlList) {
            ResponseTestSupport.assertResponse(
                    responseHtml.getActual().getAbsolutePath(),
                    responseHtml.getExpected().toString(),
                    responseHtml.getActual().toString());
        }

        log.debug("Assert Database");
        try (Connection jdbcConn = dataSource.getConnection()) {
            DatabaseComparator comparator = new DatabaseComparator(jdbcConn);
            comparator.compare(scenario.getExpectedDatabaseFile());
        }
    }

    @Parameters(name="Scenario={0}")
    public static Collection<Object[]> findScenarios() {
        List<Object[]> result = new ArrayList<>();

        for (Scenario scenario : ScenarioFinder.createOnClasspathRoot().find()) {
            result.add(new Object[] {scenario.getName(), scenario});
        }

        return result;
    }
}
