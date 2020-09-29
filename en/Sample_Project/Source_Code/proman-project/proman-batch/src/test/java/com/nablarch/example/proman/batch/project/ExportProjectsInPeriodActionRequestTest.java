package com.nablarch.example.proman.batch.project;

import com.nablarch.example.proman.test.BatchRequestTestBase;
import org.junit.Test;

/**
 * Test class for {@link ExportProjectsInPeriodAction}.
 *
 * @author Yutaka Kanayama
 */
public class ExportProjectsInPeriodActionRequestTest extends BatchRequestTestBase {

    @Test
    public void testNormalEnd() {
        execute();
    }
}
