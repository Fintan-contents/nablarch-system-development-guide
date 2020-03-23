package com.nablarch.example.proman.scenario;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * １つのテストシナリオの情報にアクセスするためのクラス。
 * <p/>
 * テストシナリオのディレクトリは、次のような構造になっています。
 * <pre>
 *  [TestNo]
 *    |-db_dump/   : DBにINSERTするデータや、前回の結果を格納している
 *    |-scenario/  : jmx ファイルを格納している
 *    |-response/  : 前回の HTML レスポンスファイルを格納している
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
     * このシナリオの JMX ファイルを取得する。
     * @return JMX ファイル
     */
    public File getJmxFile() {
        return jmxFile;
    }

    /**
     * このシナリオで事前に DB にインサートするデータを定義したファイルを取得する。
     * @return インサートファイル
     */
    public File getInsertFile() {
        return insertFile;
    }

    /**
     * データベースの期待値を表すファイルを取得する。
     * @return データベースの期待値を表すファイル
     */
    public File getExpectedDatabaseFile() {
        return expectedDatabaseFile;
    }

    /**
     * レスポンスファイルをリストで取得する。
     * @return レスポンスファイルのリスト
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
     * このテストシナリオの名前を取得する。
     * <p/>
     * シナリオの名前は、シナリオのルートディレクトリのディレクトリ名になります。
     *
     * @return テストシナリオの名前
     */
    public String getName() {
        return rootDir.getName();
    }

    /**
     * レスポンスファイルにアクセスするためのクラス。
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
         * ファイル名を取得する。
         * @return ファイル名
         */
        public String getFileName() {
            return fileName;
        }

        /**
         * テスト結果のファイルを取得する。
         * @return テスト結果
         */
        public File getActual() {
            return actual;
        }

        /**
         * 期待値となるファイルを取得する。
         * @return 期待値となるファイル
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
