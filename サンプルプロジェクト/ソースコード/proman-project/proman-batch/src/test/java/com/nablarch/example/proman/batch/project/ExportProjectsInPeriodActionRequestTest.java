package com.nablarch.example.proman.batch.project;

import com.nablarch.example.proman.test.BatchRequestTestBase;
import org.junit.Test;

/**
 * {@link ExportProjectsInPeriodAction} のテストクラス。
 *
 * @author Yutaka Kanayama
 */
public class ExportProjectsInPeriodActionRequestTest extends BatchRequestTestBase {

    @Test
    public void testNormalEnd() {
        execute();
    }
}
