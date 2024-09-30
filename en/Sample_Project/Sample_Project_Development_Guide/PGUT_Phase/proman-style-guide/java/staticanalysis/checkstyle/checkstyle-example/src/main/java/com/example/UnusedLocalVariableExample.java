/*
 * Header example
 */
package com.example;

/**
 * Example of UnusedLocalVariable code.
 */
public class UnusedLocalVariableExample {

    /**
     * method.
     *
     * @return return value.
     */
    public String example() {
        // Unused local variable (incorrect).
        String unused = "unused";

        // Used local variable (OK).
        String used = "used";
        return used.trim();
    }

}
