package com.nablarch.example.proman.batch.project;

import com.nablarch.example.proman.test.PromanBatchRequestExtension;
import com.nablarch.example.proman.test.PromanBatchRequestTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test class for {@link ExportProjectsInPeriodAction}.
 *
 * @author Yutaka Kanayama
 */
@ExtendWith(PromanBatchRequestExtension.class)
class ExportProjectsInPeriodActionRequestTest {
    PromanBatchRequestTestSupport support;

    @Test
    void testNormalEnd() {
        support.execute(support.testName.getMethodName());
    }
}
