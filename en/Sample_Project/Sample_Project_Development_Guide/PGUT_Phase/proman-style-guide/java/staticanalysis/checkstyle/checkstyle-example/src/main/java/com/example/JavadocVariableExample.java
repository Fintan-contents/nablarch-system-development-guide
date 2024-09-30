/*
 * Header example
 */
package com.example;

/**
 * Example of JavadocVariable code.
 */
public class JavadocVariableExample {

    /**
     * Constructor.
     */
    private JavadocVariableExample() {
    }

    public static final String HELLO = "Hello";    // No javadoc comment (incorrect).

    /** Signoff */
    public static final String GOODBYE = "Goodbye";  // javadoc comment included （OK）.

}
