/*
 * Header example
 */
package com.example;


/**
 * Example of NoFinalizer code.
 *
 * @author example
 * @since 1.0.0
 */
public class NoFinalizerExample {

    // finalize method is overwritten (incorrect).
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        // (resource deallocation process)
    }
}

