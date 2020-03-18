package com.nablarch.example.proman;

import com.nablarch.example.proman.assertion.db.DBUnitConnectionBuilder;
import com.nablarch.example.proman.assertion.db.DataSourceInitializer;
import com.nablarch.example.proman.assertion.db.DatabaseComparator;
import com.nablarch.example.proman.assertion.response.ResponseTestSupport;
import com.nablarch.example.proman.jmeter.JMeterRunner;
import com.nablarch.example.proman.loader.DatabaseLoader;
import com.nablarch.example.proman.scenario.Scenario;
import com.nablarch.example.proman.scenario.ScenarioFinder;
import org.dbunit.database.DatabaseConnection;
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

@RunWith(Parameterized.class)
public class JMeterTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JMeterTest.class);
    private static final Configurations CONFIG = new Configurations();
    private static DataSourceInitializer DATA_SOURCE;

    private final String name;
    private final Scenario scenario;

    public JMeterTest(String name, Scenario scenario) {
        this.name = name;
        this.scenario = scenario;
    }

    @BeforeClass
    public static void readProperties() {
        DATA_SOURCE = new DataSourceInitializer(
            CONFIG.getDatabaseDriver(),
            CONFIG.getDatabaseUrl(),
            CONFIG.getDatabaseUsername(),
            CONFIG.getDatabasePassword()
        );
    }

    @Test
    public void test() throws Exception {
        LOGGER.info("Scenario name={} jmx={}", name, scenario.getJmxFile());

        LOGGER.debug("Setup Database");
        DatabaseLoader loader = new DatabaseLoader();
        loader.loadFromExcel(scenario.getInsertFile());

        LOGGER.debug("Run JMeter");
        JMeterRunner runner = new JMeterRunner(CONFIG.getJmeterHome(), CONFIG.getServerHost(), CONFIG.getServerPort());
        runner.initialize();
        runner.runJMeter(scenario.getJmxFile());

        LOGGER.debug("Assert Response");
        List<Scenario.ResponseHtml> responseHtmlList = scenario.getResponseHtmlList();
        for (Scenario.ResponseHtml responseHtml : responseHtmlList) {
            ResponseTestSupport.assertResponse(
                    responseHtml.getActual().getAbsolutePath(),
                    responseHtml.getExpected().toString(),
                    responseHtml.getActual().toString());
        }

        LOGGER.debug("Assert Database");
        try (Connection jdbcConn = DATA_SOURCE.getConnection()) {
            DatabaseConnection conn = DBUnitConnectionBuilder.build(jdbcConn, CONFIG.getDatabaseDriver());
            DatabaseComparator comparator = new DatabaseComparator(conn);
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
