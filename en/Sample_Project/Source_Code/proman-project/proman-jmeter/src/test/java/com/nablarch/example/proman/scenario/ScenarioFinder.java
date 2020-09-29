package com.nablarch.example.proman.scenario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class to collect the scenario directory.
 * <p/>
 * Recursively search under the classpath for files that match {@code *.jmx}
 * and collect them to determine the scenario directory. <br/>
 * The directory where the {@code *.jmx} file is found will not be searched below it. <br/>
 * The search moves to the sibling directories, and the rest of the directories are searched recursively.
 *
 * @author Tanaka Tomoyuki
 */
public class ScenarioFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioFinder.class);

    private final Path rootPath;

    /**
     * Create an instance using the classpath root as the search starting point.
     * @return Instance to search down from the classpath root.
     */
    public static ScenarioFinder createOnClasspathRoot() {
        try {
            URL classpathRoot = ScenarioFinder.class.getResource("/");
            Path rootPath = Paths.get(classpathRoot.toURI());
            return new ScenarioFinder(rootPath);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an instance with the origin of the search.
     * @param rootPath The path from which the search originates
     */
    ScenarioFinder(Path rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Search for all scenario directories under the classpath and return a list of them.
     * @return List of all scenario directories
     */
    public List<Scenario> find() {
        try {
            ScenarioFileVisitor visitor = new ScenarioFileVisitor();
            Files.walkFileTree(rootPath, visitor);

            return visitor.scenarioList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ScenarioFileVisitor extends SimpleFileVisitor<Path> {
        private final ScenarioJmxFilter scenarioJmxFilter = new ScenarioJmxFilter();
        private List<Scenario> scenarioList = new ArrayList<>();

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            LOGGER.debug("visit dir = {}", dir);
            File[] jmxFiles = dir.toFile().listFiles(scenarioJmxFilter);

            if (jmxFiles == null || jmxFiles.length == 0) {
                LOGGER.debug("no jmx file.");
                return FileVisitResult.CONTINUE;

            } else if (jmxFiles.length == 1) {
                File jmxFile = jmxFiles[0];
                LOGGER.debug("found jmx file. ({})", jmxFile);
                scenarioList.add(new Scenario(jmxFile));
                return FileVisitResult.SKIP_SUBTREE;

            } else {
                throw new RuntimeException("Multiple jmx files are found. (" + Arrays.toString(jmxFiles) + ")");
            }
        }
    }

    private static class ScenarioJmxFilter implements FilenameFilter {
        private static final Pattern JXM_FILE_NAME_PATTERN = Pattern.compile("^.*\\.jmx$");

        @Override
        public boolean accept(File dir, String name) {
            return dir.getName().equals(Scenario.SCENARIO_DIR_NAME)
                    && JXM_FILE_NAME_PATTERN.matcher(name).matches();
        }
    }
}
