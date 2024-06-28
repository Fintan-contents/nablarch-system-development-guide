/*
 * Header example
 */
package com.example;


/**
 * Example of NoFinalizer code.
 */
public class NoFinalizerExample {

    // finalize method is overwritten (incorrect).
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        // (resource deallocation process)
    }
}
