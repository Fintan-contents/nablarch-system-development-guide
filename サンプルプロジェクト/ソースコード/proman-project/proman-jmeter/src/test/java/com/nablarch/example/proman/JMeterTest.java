package com.nablarch.example.proman;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class JMeterTest {
    private static Logger log = Logger.getLogger(JMeterTest.class);

    /* ===========================================
     * インストールしたJMeterの場所を設定しないといけない。ローカル実行とCI実行できりかえる必要がある
     * jmeter-maven-pluginを使えば mvn jmeter:configure で
     * target配下にJMeterのモジュールがダウンロードされて実行可能になる
     * なんとかしてこれ使えないかな？クラスパスには入らないから難しいか。。。
     * あとダウンロードされるディレクトリも実行IDみたいなのがふられて毎回変わる。
     * target/config.jsonにjmeterDirectoryPathという名前で出力されるからそこから引っ張ってこれれば。
     * ===========================================*/
    private static final String JMETER_HOME = "C:\\PJ\\01.Nablarch\\repo\\nablarch-system-development-guide\\サンプルプロジェクト\\ソースコード\\proman-project\\proman-jmeter\\target\\3a927a0e-fed0-4839-817f-c1a447f541f8\\jmeter";

    @Test
    public void test() throws IOException, URISyntaxException {
        JMeterUtils.loadJMeterProperties(JMETER_HOME + "/bin/jmeter.properties");
        JMeterUtils.setJMeterHome(JMETER_HOME);
        JMeterUtils.initLocale();
        // Initialize JMeter SaveService
        SaveService.loadProperties();

        // Load existing .jmx Test Plan
        // src/test/resources/jmeter配下のjmxファイルをまるっとさらえてこれないか
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL jmxFile = classLoader.getResource("jmeter/shot_1/test1.jmx");

        // TODO DBセットアップ

        runJMeter(jmxFile);

        // TODO レスポンスのアサーション

        // TODO DBのアサーション
    }

    private void runJMeter(URL jmxFileUrl) throws URISyntaxException, IOException {
        File jmxFile = new File(jmxFileUrl.toURI());
        HashTree testPlanTree = SaveService.loadTree(jmxFile);

        final FileServer fileServer = FileServer.getFileServer();
        fileServer.setBasedir(jmxFile.getParent());
        fileServer.setScriptName("test1.jmx");

        // Run JMeter Test
        StandardJMeterEngine jmeter = new StandardJMeterEngine();
        jmeter.configure(testPlanTree);
        // jmxのユーザ定義変数の設定エレメントで接続先ホストを変数化しているが
        // 実行時にプロファイルによって動的に変えられないか？
        // 帰られればCIとローカルで設定ファイルに記載するだけで切り替えられるし
        // 操作記録した人が修正しなくてもすむ
        jmeter.run();
    }

}
