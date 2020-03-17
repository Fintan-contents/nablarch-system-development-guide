package com.nablarch.example.proman.scenario;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScenarioTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private File scenarioDir;
    private File jmxFile;
    private Scenario sut;

    @Before
    public void setup() throws Exception {
        scenarioDir = new File(tempFolder.getRoot(), Scenario.SCENARIO_DIR_NAME);
        jmxFile = new File(scenarioDir, "sample.jmx");
        sut = new Scenario(jmxFile);
    }

    @Test
    public void testJmxFileGetter() {
        File result = sut.getJmxFile();

        assertThat(result, is(jmxFile));
    }

    @Test
    public void testInsertFileGetter() {
        File result = sut.getInsertFile();

        File expected = new File(tempFolder.getRoot(), Scenario.DB_DUMP_DIR_NAME + "/" + Scenario.INSERT_DB_FILE_NAME);
        assertThat(result, is(expected));
    }

    @Test
    public void testResponseHtmlGetter() throws Exception {
        File responseDir = new File(tempFolder.getRoot(), Scenario.RESPONSE_DIR_NAME);

        File firstInResponse = touchFile(responseDir, "first.html");
        File secondInResponse = touchFile(responseDir, "second.html");
        File thirdInResponse = touchFile(responseDir, "third.html");
        touchFile(responseDir, "no-html.txt");

        File firstInScenarioDir = new File(scenarioDir, "first.html");
        File secondInScenarioDir = new File(scenarioDir, "second.html");
        File thirdInScenarioDir = new File(scenarioDir, "third.html");

        List<Scenario.ResponseHtml> result = sut.getResponseHtmlList();

        assertThat(result.size(), is(3));
        assertThat(result, hasItem(responseOf(firstInScenarioDir, firstInResponse)));
        assertThat(result, hasItem(responseOf(secondInScenarioDir, secondInResponse)));
        assertThat(result, hasItem(responseOf(thirdInScenarioDir, thirdInResponse)));
    }

    private File touchFile(File dir, String name) throws IOException {
        File file = new File(dir, name);
        Files.createDirectories(file.getParentFile().toPath());
        Files.createFile(file.toPath());
        return file;
    }

    private static ResponseOf responseOf(File actualResponse, File expectedResponse) {
        return new ResponseOf(actualResponse, expectedResponse);
    }

    private static class ResponseOf extends BaseMatcher<Scenario.ResponseHtml> {
        private final File actualResponse;
        private final File expectedResponse;
        private Scenario.ResponseHtml actual;

        private ResponseOf(File actualResponse, File expectedResponse) {
            this.actualResponse = actualResponse;
            this.expectedResponse = expectedResponse;
        }

        @Override
        public boolean matches(Object item) {
            if (!(item instanceof Scenario.ResponseHtml)) {
                return false;
            }

            actual = (Scenario.ResponseHtml) item;
            return Objects.equals(actual.getActual(), actualResponse)
                    && Objects.equals(actual.getExpected(), expectedResponse);
        }

        @Override
        public void describeTo(Description description) {
            String message = String.format("{actual=%s, expected=%s} but actual is {actual=%s, expected=%s}",
                    actualResponse, expectedResponse,
                    (actual != null ? actual.getActual() : null), (actual != null ? actual.getExpected() : null));

            description.appendText(message);
        }
    }
}
