package com.nablarch.example.proman;

import org.junit.Assert;

import java.io.*;

/**
 * レスポンスを記録したファイルのテストをサポートするクラス。
 * @author Yutaka Kanayama
 */
public class ResponseTestSupport {

    /**
     * レスポンスを記録したファイルのアサートを実行する。
     * @param message テスト失敗時に表示するメッセージ
     * @param expectedFilePath 期待値として使用するファイルのパス
     * @param actualFilePath　結果として使用するファイルのパス
     */
    public static void assertResponse(String message, String expectedFilePath, String actualFilePath) {
        try(BufferedReader expectedReader = new BufferedReader(new FileReader(expectedFilePath));
            BufferedReader actualReader = new BufferedReader(new FileReader(actualFilePath))) {
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
