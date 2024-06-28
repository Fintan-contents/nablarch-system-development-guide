/*
 * Header example
 */
package com.example;

/**
 * Example of StringLiteralEquality code.
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
        if ("something".equals(s)) {
            
        }

    }
}
