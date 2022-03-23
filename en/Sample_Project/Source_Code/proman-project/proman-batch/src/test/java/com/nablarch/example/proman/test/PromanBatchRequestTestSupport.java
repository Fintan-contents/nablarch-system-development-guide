package com.nablarch.example.proman.test;

import nablarch.test.core.batch.BatchRequestTestSupport;

/**
 * Dedicated {@link BatchRequestTestSupport} inheritance class for this project. <br/>
 * Provides a common process specific to the project.
 * When creating a request unit test using batch processing in the project,
 * use this class instead of directly using {@link BatchRequestTestSupport}.
 */
public class PromanBatchRequestTestSupport extends BatchRequestTestSupport {

    /**
     * Constructor.
     *
     * @param testClass Test class
     */
    public PromanBatchRequestTestSupport(Class<?> testClass) {
        super(testClass);
    }
}
