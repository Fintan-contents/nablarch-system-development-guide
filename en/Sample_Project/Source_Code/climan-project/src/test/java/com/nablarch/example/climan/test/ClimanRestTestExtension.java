package com.nablarch.example.climan.test;

import nablarch.test.core.http.RestTestSupport;
import nablarch.test.junit5.extension.http.RestTestExtension;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Extension to enable the use of {@link ClimanRestTestSupport} in tests.
 */
public class ClimanRestTestExtension extends RestTestExtension {
    @Override
    protected RestTestSupport createSupport(Object testInstance, ExtensionContext context) {
        return new ClimanRestTestSupport(testInstance.getClass());
    }
}
