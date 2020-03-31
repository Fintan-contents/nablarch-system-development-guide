package com.nablarch.example.proman.assertion.response;

import com.nablarch.example.proman.Configurations;
import org.junit.Assert;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * レスポンスを記録したファイルのテストをサポートするクラス。
 * @author Yutaka Kanayama
 */
public class ResponseTestSupport {

    private final Configurations config;

    public ResponseTestSupport(Configurations config) {
        this.config = config;
    }

    /**
     * レスポンスを記録したファイルのアサートを実行する。
     * @param message テスト失敗時に表示するメッセージ
     * @param expectedFilePath 期待値として使用するファイルのパス
     * @param actualFilePath 結果として使用するファイルのパス
     */
    public void assertResponse(String message, String expectedFilePath, String actualFilePath) {
        Charset charset = Charset.forName(config.getResponseEncoding());
        try(BufferedReader expectedReader = Files.newBufferedReader(Paths.get(expectedFilePath), charset);
            BufferedReader actualReader = Files.newBufferedReader(Paths.get(actualFilePath), charset)) {
            // 必ず一度ファイルを読む
            String expectedLine = "";
            String actualLine = "";
            int rowNum = 0;
            while(expectedLine != null && actualLine != null) {
                // 必ずどちらの行も読み込む
                expectedLine = expectedReader.readLine();
                actualLine = actualReader.readLine();
                rowNum++;

                Assert.assertEquals(rowNum + "行目の内容が異なります。" + message, expectedLine, actualLine);
            }
        } catch(IOException e) {
            // どちらかのファイルがない時などはこのブロックに到達する。
            Assert.fail(e.getMessage());
        }
    }
}
