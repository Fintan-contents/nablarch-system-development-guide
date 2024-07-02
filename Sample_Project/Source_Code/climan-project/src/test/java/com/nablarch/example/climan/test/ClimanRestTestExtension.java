package com.nablarch.example.climan.test;

import nablarch.test.core.http.RestTestSupport;
import nablarch.test.junit5.extension.http.RestTestExtension;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * {@link ClimanRestTestSupport}をテストで使用できるようにするためのExtension。
 */
public class ClimanRestTestExtension extends RestTestExtension {
    @Override
    protected RestTestSupport createSupport(Object testInstance, ExtensionContext context) {
        return new ClimanRestTestSupport(testInstance.getClass());
    }
}
