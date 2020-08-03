package com.nablarch.example.proman.assertion.response;

import com.nablarch.example.proman.Configurations;
import org.junit.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class that supports testing of response-recorded files.
 * @author Yutaka Kanayama
 */
public class ResponseTestSupport {

    private final Configurations config;

    public ResponseTestSupport(Configurations config) {
        this.config = config;
    }

    /**
     * Assert the file containing the response.
     * @param message Message to be displayed when the test fails
     * @param expectedFilePath The path to the file to use as an expectation
     * @param actualFilePath The path to the resulting file
     */
    public void assertResponse(String message, String expectedFilePath, String actualFilePath) {
        Charset charset = Charset.forName(config.getResponseEncoding());
        try(BufferedReader expectedReader = Files.newBufferedReader(Paths.get(expectedFilePath), charset);
            BufferedReader actualReader = Files.newBufferedReader(Paths.get(actualFilePath), charset)) {
            // Always read the file once.
            String expectedLine = "";
            String actualLine = "";
            int rowNum = 0;
            while(expectedLine != null && actualLine != null) {
                // Be sure to read both lines.
                expectedLine = expectedReader.readLine();
                actualLine = actualReader.readLine();
                rowNum++;

                Assert.assertEquals(rowNum + "行目の内容が異なります。" + message, expectedLine, actualLine);
            }
        } catch(IOException e) {
            // You will reach this block when either file is missing, for example.
            Assert.fail(e.getMessage());
        }
    }
}
