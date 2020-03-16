package com.nablarch.example.proman;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

@RunWith(Parameterized.class)
public class JMeterTest {
    private static Logger log = LoggerFactory.getLogger(JMeterTest.class);

    private final Scenario scenario;
    private static ResourceBundle properties;

    public JMeterTest(Scenario scenario) {
        this.scenario = scenario;
    }

    @BeforeClass
    public static void readProperties() {
        properties = ResourceBundle.getBundle("env");
    }

    @Test
    public void test() throws Exception {
        log.debug("scenario jmx={}", scenario.getJmxFile());

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
        // TODO DB のアサーション
    }

    @Parameters
    public static Collection<Object[]> findScenarios() {
        List<Object[]> result = new ArrayList<>();

        for (Scenario scenario : ScenarioFinder.createOnClasspathRoot().find()) {
            result.add(new Object[] {scenario});
        }

        return result;
    }
}
