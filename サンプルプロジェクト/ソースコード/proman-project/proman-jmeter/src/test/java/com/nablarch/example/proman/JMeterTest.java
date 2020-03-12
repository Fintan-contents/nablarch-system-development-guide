package com.nablarch.example.proman;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class JMeterTest {
    private static Logger log = Logger.getLogger(JMeterTest.class);

    private static ResourceBundle properties;

    @BeforeClass
    public static void readProperties() throws IOException {
        properties = ResourceBundle.getBundle("env");
    }

    @Test
    public void test() throws IOException, URISyntaxException {
        JMeterRunner runner = new JMeterRunner(properties.getString("jmeter.home"));
        runner.initialize();

        List<File> jmxFiles = getScenarioList();

        for (File jmxFile : jmxFiles) {
            // TODO DBセットアップ
//        new DatabaseLoader().loadFromExcel("./DUMP.xlsm");
//        new DatabaseLoader().loadFromCsv("./DUMP.xlsm");

            runner.runJMeter(jmxFile);

            // TODO レスポンスのアサーション

            // TODO DBのアサーション
        }
    }

    private List<File> getScenarioList() throws URISyntaxException {
        // Load existing .jmx Test Plan
        // src/test/resources/jmeter配下のjmxファイルをまるっとさらえてこれないか
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url1 = classLoader.getResource("jmeter/shot_1/test1.jmx");
        File jmxFile1 = new File(url1.toURI());
        URL url2 = classLoader.getResource("jmeter/shot_2/test2.jmx");
        File jmxFile2 = new File(url2.toURI());
        return Arrays.asList(jmxFile1, jmxFile2);
    }
}
