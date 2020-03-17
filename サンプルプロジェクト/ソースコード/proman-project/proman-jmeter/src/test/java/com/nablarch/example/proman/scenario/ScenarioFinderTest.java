package com.nablarch.example.proman.scenario;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ScenarioFinderTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    ScenarioFinder sut;

    @Before
    public void setup() {
        sut = new ScenarioFinder(tempFolder.getRoot().toPath());
    }

    @Test
    public void testFindScenario() throws Exception {
        /*
         root/
         |-test01/
         | `-scenario/
         |   `-test01.jmx
         `-test02/
           |-test02-1/
           | |-foo/
           | | `-ignore.jmx
           | `-scenario/
           |   `-test02-1.jmx
           `-test02-2/
             `-scenario/
               `-test02-2.jmx
         */
        File test01Jmx = touchFile("test01/scenario/test01.jmx");
        File test02_1Jmx = touchFile("test02/test02-1/scenario/test02-1.jmx");
        touchFile("test02/test02-1/foo/ignore.jmx");
        File test02_2Jmx = touchFile("test02/test02-2/scenario/test02-2.jmx");

        List<Scenario> result = sut.find();

        assertThat(result.size(), is(3));
        assertThat(result, hasItem(scenarioOf(test01Jmx)));
        assertThat(result, hasItem(scenarioOf(test02_1Jmx)));
        assertThat(result, hasItem(scenarioOf(test02_2Jmx)));
    }

    @Test
    public void testExceptionIsThrownIfMultipleJmxFileAreFoundInScenarioDirectory() throws Exception {
        exception.expect(RuntimeException.class);
        exception.expectMessage(startsWith("Multiple jmx files are found."));

        /*
         root/
         `-test01/
           `-scenario/
             |-test01.jmx
             `-duplication.jmx
         */
        touchFile("test01/scenario/test01.jmx");
        touchFile("test01/scenario/duplication.jmx");

        sut.find();
    }

    private File touchFile(String path) throws IOException {
        File file = new File(tempFolder.getRoot(), path);
        Files.createDirectories(file.getParentFile().toPath());
        Files.createFile(file.toPath());
        return file;
    }

    private static ScenarioOf scenarioOf(File expectedRootDir) {
        return new ScenarioOf(expectedRootDir);
    }

    private static class ScenarioOf extends BaseMatcher<Scenario> {
        private final File expectedJmxFile;
        private Scenario actual;

        private ScenarioOf(File expectedJmxFile) {
            this.expectedJmxFile = expectedJmxFile;
        }

        @Override
        public boolean matches(Object item) {
            if (!(item instanceof Scenario)) {
                return false;
            }

            actual = ((Scenario) item);
            return expectedJmxFile.equals(actual.getJmxFile());
        }

        @Override
        public void describeTo(Description description) {
            String message = String.format("'%s' but actual is '%s'", expectedJmxFile, (actual == null ? null : actual.getJmxFile()));
            description.appendText(message);
        }
    }
}
