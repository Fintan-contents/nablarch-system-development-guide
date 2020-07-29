package com.nablarch.example.proman.test;

import nablarch.test.core.batch.BatchRequestTestSupport;

/**
 * Dedicated {@link BatchRequestTestSupport} inheritance class for this project. <br/>
 * Provides a common process specific to the project.
 * When creating a request unit test using batch processing in the project,
 * this class is inherited instead of directly inheriting
 * {@link BatchRequestTestSupport}.
 */
public abstract class BatchRequestTestBase extends BatchRequestTestSupport {

    /** Constructor. */
    protected BatchRequestTestBase() {
        super();
    }

    /**
     * Constructor.
     *
     * @param testClass: Test class
     */
    public BatchRequestTestBase(Class<?> testClass) {
        super(testClass);
    }
}
