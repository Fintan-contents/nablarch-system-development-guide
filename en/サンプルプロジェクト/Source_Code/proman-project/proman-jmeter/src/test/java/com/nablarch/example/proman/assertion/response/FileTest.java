package com.nablarch.example.proman.assertion.response;

import com.nablarch.example.proman.Configurations;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Verify that {@link ResponseTestSupport} can be tested as expected.
 * <p/>
 * The path is dependent on the execution environment, so the method of using temporary directories etc. needs to be modified.
 */
@Ignore
public class FileTest {

    private static final String EXPECTED_PATH = "C:\\shaft\\jmeter\\nablarch-development-guide\\サンプルプロジェクト\\ソースコード\\proman-project\\proman-jmeter\\src\\test\\resources\\jmeter\\shot_1\\resultHtml\\";
    private static final String ACTUAL_PATH = "C:\\shaft\\jmeter\\nablarch-development-guide\\サンプルプロジェクト\\ソースコード\\proman-project\\proman-jmeter\\src\\test\\resources\\jmeter\\shot_1\\";
    private final ResponseTestSupport sut = new ResponseTestSupport(new Configurations());

    /**
     * Make sure you succeed.
     */
    @Test
    public void test_差分がない() {
        String fileName = "no_diff.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * Make sure you succeed.
     */
    @Test
    public void test_どちらも0byte() {
        String fileName = "zero_byte_no_diff.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * Make sure it fails.
     */
    @Test
    public void test_差分がある() {
        String fileName = "the_diff.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * Make sure it fails.
     */
    @Test
    public void test_expectedが多い() {
        String fileName = "expected_tooMany.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * Make sure it fails.
     */
    @Test
    public void test_actualが多い() {
        String fileName = "actual_tooMany.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * Make sure it fails.
     */
    @Test
    public void test_ファイルがexpectedしかない() {
        String fileName = "only_expected.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
    /**
     * Make sure it fails.
     */
    @Test
    public void test_ファイルがactualしかない() {
        String fileName = "only_actual.html";
        sut.assertResponse("",EXPECTED_PATH + fileName, ACTUAL_PATH + fileName);
    }
}
