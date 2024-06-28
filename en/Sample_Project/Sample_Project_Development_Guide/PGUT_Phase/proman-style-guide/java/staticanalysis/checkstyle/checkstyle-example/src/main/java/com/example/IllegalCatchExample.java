/*
 * Header example
 */
package com.example;

/**
 * Example of IllegalCatch code.
 */
public class IllegalCatchExample {

    /**
     * Example of IllegalCatch code.
     */
    public void example() {
        try {
            System.out.println("example");
        } catch (Exception e) { // Unpermitted exception (java.lang.Exception) is caught (incorrect).
            e.printStackTrace();
        }
    }
}
