package com.nablarch.example.proman;

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
 * シナリオディレクトリを収集するクラス。
 * <p/>
 * クラスパス配下を再帰的に検索して、 {@code *.jmx} にマッチするファイルを見つけたら、
 * そのディレクトリをシナリオディレクトリを判断して収集します。<br/>
 * {@code *.jmx} ファイルが見つかったディレクトリは、それより下は検索しません。<br/>
 * 検索は、兄弟ディレクトリに移り、引き続け残りのディレクトリが再帰的に検索されます。
 *
 * @author Tanaka Tomoyuki
 */
public class ScenarioFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioFinder.class);

    private final Path rootPath;

    /**
     * クラスパスのルートを検索の起点にしてインスタンスを生成する。
     * @return クラスパスルートから下を検索するインスタンス
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
     * 検索の起点を指定してインスタンスを生成する。
     * @param rootPath 検索の起点となるパス
     */
    ScenarioFinder(Path rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * クラスパス配下に存在するすべてのシナリオディレクトリを検索して、リストで返却する。
     * @return 全シナリオディレクトリのリスト
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
