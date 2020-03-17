package com.nablarch.example.proman.jmeter;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class JMeterRunner {
    private final String JMETER_HOME;

    public JMeterRunner(String jmeterHome) {
        JMETER_HOME = jmeterHome;
    }

    public void initialize() throws IOException {
        JMeterUtils.loadJMeterProperties(JMETER_HOME + "/bin/jmeter.properties");
        JMeterUtils.setJMeterHome(JMETER_HOME);
        JMeterUtils.initLocale();
        // Initialize JMeter SaveService
        SaveService.loadProperties();
    }

    public void runJMeter(File jmxFile) throws URISyntaxException, IOException {
        HashTree testPlanTree = SaveService.loadTree(jmxFile);

        final FileServer fileServer = FileServer.getFileServer();
        fileServer.setBasedir(jmxFile.getParent());
        fileServer.setScriptName(jmxFile.getName());

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
