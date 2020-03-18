package com.nablarch.example.proman.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.SearchByClass;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public class JMeterRunner {
    private static final String USER_DEFINED_VAR_KEY_SERVER_HOST = "server.host";
    private static final String USER_DEFINED_VAR_KEY_SERVER_PORT = "server.port";

    private final String jmeterHome;
    private final String serverHost;
    private final String serverPort;


    public JMeterRunner(String jmeterHome, String serverHost, String serverPort) {
        this.jmeterHome = jmeterHome;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void initialize() throws IOException {
        JMeterUtils.loadJMeterProperties(jmeterHome + "/bin/jmeter.properties");
        JMeterUtils.setJMeterHome(jmeterHome);
        JMeterUtils.initLocale();
        // Initialize JMeter SaveService
        SaveService.loadProperties();
    }

    public void runJMeter(File jmxFile) throws URISyntaxException, IOException {
        HashTree testPlanTree = SaveService.loadTree(jmxFile);

        final FileServer fileServer = FileServer.getFileServer();
        fileServer.setBasedir(jmxFile.getParent());
        fileServer.setScriptName(jmxFile.getName());

        // ユーザ定義変数のサーバーホストとポートを変更
        SearchByClass<Arguments> search = new SearchByClass<>(Arguments.class);
        testPlanTree.traverse(search);
        Collection<Arguments> searchResults = search.getSearchResults();
        Arguments arguments = searchResults.iterator().next();
        arguments.removeArgument(USER_DEFINED_VAR_KEY_SERVER_HOST);
        arguments.addArgument(USER_DEFINED_VAR_KEY_SERVER_HOST, serverHost);
        arguments.removeArgument(USER_DEFINED_VAR_KEY_SERVER_PORT);
        arguments.addArgument(USER_DEFINED_VAR_KEY_SERVER_PORT, serverPort);

        // Run JMeter Test
        StandardJMeterEngine jmeter = new StandardJMeterEngine();
        jmeter.configure(testPlanTree);
        jmeter.run();
    }
}
