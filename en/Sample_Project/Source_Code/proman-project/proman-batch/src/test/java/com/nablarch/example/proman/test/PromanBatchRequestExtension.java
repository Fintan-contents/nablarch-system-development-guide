package com.nablarch.example.proman.test;

import nablarch.test.event.TestEventDispatcher;
import nablarch.test.junit5.extension.batch.BatchRequestTestExtension;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Extension to enable the use of {@link PromanBatchRequestTestSupport} in tests.
 */
public class PromanBatchRequestExtension extends BatchRequestTestExtension {
    @Override
    protected TestEventDispatcher createSupport(Object testInstance, ExtensionContext context) {
        return new PromanBatchRequestTestSupport(testInstance.getClass());
    }
}
