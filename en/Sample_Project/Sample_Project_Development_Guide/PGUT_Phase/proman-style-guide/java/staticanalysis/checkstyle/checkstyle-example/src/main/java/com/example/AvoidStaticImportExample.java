/*
 * Header example
 */
package com.example;

import static java.lang.System.out;

/**
 * Example of AvoidStaticImport code.
 *
 * @author example
 * @since 1.0.0
 */
public class AvoidStaticImportExample {

    /**
     * Example of AvoidStaticImport code.
     */
    public void example() {
        // static import is used (incorrect)
        out.println("Hello World!");

        // static import is not used (OK)
        System.out.println("Hello World!");
    }
}

