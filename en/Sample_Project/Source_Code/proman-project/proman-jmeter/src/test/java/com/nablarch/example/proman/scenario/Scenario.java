package com.nablarch.example.proman.scenario;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A class to access information for one test scenario.
 * <p/>
 * The structure of the test scenario directory is as follows
 * <pre>
 *  [TestNo]
 *    |-db_dump/   : INSERT data and previous results are stored in the DB.
 *    |-scenario/  : The jmx file is stored in
 *    |-response/  : It contains the previous HTML response file.
 *    :
 * </pre>
 *
 * @author Tanaka Tomoyuki
 */
public class Scenario {
    static final String SCENARIO_DIR_NAME = "scenario";
    static final String DB_DUMP_DIR_NAME = "db_dump";
    static final String RESPONSE_DIR_NAME = "response";
    static final String INSERT_DB_FILE_NAME = "INSERT.xlsm";
    static final String EXPECTED_DB_FILE_NAME = "EXPECTED.xlsx";

    private static final Pattern RESPONSE_FILE_NAME_PATTERN = Pattern.compile("^.*\\.(html|js|css)$");

    private final File rootDir;
    private final File dbDumpDir;
    private final File scenarioDir;
    private final File responseDir;

    private final File jmxFile;
    private final File insertFile;
    private final File expectedDatabaseFile;

    public Scenario(File jmxFile) {
        this.rootDir = jmxFile.getParentFile().getParentFile();
        this.scenarioDir = new File(rootDir, SCENARIO_DIR_NAME);
        this.dbDumpDir = new File(rootDir, DB_DUMP_DIR_NAME);
        this.responseDir = new File(rootDir, RESPONSE_DIR_NAME);

        this.jmxFile = jmxFile;
        this.insertFile = new File(dbDumpDir, INSERT_DB_FILE_NAME);
        this.expectedDatabaseFile = new File(dbDumpDir, EXPECTED_DB_FILE_NAME);
    }


    /**
     * Get the JMX file for this scenario.
     * @return JMX file
     */
    public File getJmxFile() {
        return jmxFile;
    }

    /**
     * In this scenario, we get a file that defines the data to be inserted in the DB beforehand.
     * @return insert file
     */
    public File getInsertFile() {
        return insertFile;
    }

    /**
     * Get a file representing the expected value of the database.
     * @return A file representing the expected value of the database
     */
    public File getExpectedDatabaseFile() {
        return expectedDatabaseFile;
    }

    /**
     * Check for the existence of a file representing the expected value of the database.
     * @return If the file exists, then {@code true}
     */
    public boolean existsExpectedDatabaseFile() {
        return expectedDatabaseFile.exists()
                && expectedDatabaseFile.isFile();
    }

    /**
     * Get the response file as a list.
     * @return List of response files
     */
    public List<ResponseFile> getResponseFileList() {
        File[] responseFiles = responseDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return RESPONSE_FILE_NAME_PATTERN.matcher(name).matches();
            }
        });

        if (responseFiles == null) {
            return Collections.emptyList();
        }

        List<ResponseFile> result = new ArrayList<>();

        for (File responseFile : responseFiles) {
            String fileName = responseFile.getName();
            ResponseFile response = new ResponseFile(fileName);
            result.add(response);
        }

        return result;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "rootDir=" + rootDir +
                '}';
    }

    /**
     * Get the name of this test scenario.
     * <p/>
     * The name of the scenario will be the directory name of the root directory of the scenario.
     *
     * @return Test Scenario Name
     */
    public String getName() {
        return rootDir.getName();
    }

    /**
     * Class for accessing the response file.
     * @author Tanaka Tomoyuki
     */
    public class ResponseFile {
        private final String fileName;
        private final File actual;
        private final File expected;

        private ResponseFile(String fileName) {
            this.fileName = fileName;
            this.actual = new File(scenarioDir, fileName);
            this.expected = new File(responseDir, fileName);
        }

        /**
         * Get the name of the file.
         * @return file-name
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * Get the file of the test result.
         * @return test results
         */
        public File getActual() {
            return actual;
        }

        /**
         * Get the file that is the expected value.
         * @return The expected file
         */
        public File getExpected() {
            return expected;
        }

        @Override
        public String toString() {
            return "ResponseFile{" +
                    "fileName='" + fileName + '\'' +
                    ", actual=" + actual +
                    ", expected=" + expected +
                    '}';
        }
    }
}
