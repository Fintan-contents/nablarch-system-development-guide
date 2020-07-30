/*
 * Header example
 */
package com.example;

/**
 * Example of StringLiteralEquality code.
 *
 * @author example
 * @since 1.0.0
 */
public class StringLiteralEqualityExample {

    /**
     * Example of StringLiteralEquality code.
     *
     * @param s String
     */
    public void example(String s) {
        // Character string is compared with == (incorrect).
        if (s == "something") {

        }

        // Character string is compared with equals (OK).
        if (s.equals("something")) {
            
        }

    }
}

