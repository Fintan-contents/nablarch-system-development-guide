package com.nablarch.example.proman.assertion.response;

import com.nablarch.example.proman.Configurations;
import org.junit.Ignore;
import org.junit.Test;

/**
 * {@link ResponseTestSupport} が想定通りテスト可能なことを確認する。
 * <p/>
 * パスが実行環境に依存しているので、テンポラリディレクトリなどを使う方法に修正が必要。
 */
@Ignore
public class FileTest {

    private static final String EXPECTED_PATH = "C:\\shaft\\jmeter\\nablarch-development-guide\\Sample_Project\\Source_Code\\proman-project\\proman-jmeter\\src\\test\\resources\\jmeter\\shot_1\\resultHtml\\";
    private static final String ACTUAL_PATH = "C:\\shaft\\jmeter\\nablarch-development-guide\\Sample_Project\\Source_Code\\proman-project\\proman-jmeter\\src\\test\\resources\\jmeter\\shot_1\\";
    private final ResponseTestSupport sut = new ResponseTestSupport(new Configurations());

    /**
     * 成功することを確認する
     */
    @Test
    public void test_差分がない() {
        String fileName = "no_diff.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * 成功することを確認する
     */
    @Test
    public void test_どちらも0byte() {
        String fileName = "zero_byte_no_diff.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * 失敗することを確認する
     */
    @Test
    public void test_差分がある() {
        String fileName = "the_diff.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * 失敗することを確認する
     */
    @Test
    public void test_expectedが多い() {
        String fileName = "expected_tooMany.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * 失敗することを確認する
     */
    @Test
    public void test_actualが多い() {
        String fileName = "actual_tooMany.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * 失敗することを確認する
     */
    @Test
    public void test_ファイルがexpectedしかない() {
        String fileName = "only_expected.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * 失敗することを確認する
     */
    @Test
    public void test_ファイルがactualしかない() {
        String fileName = "only_actual.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
}
