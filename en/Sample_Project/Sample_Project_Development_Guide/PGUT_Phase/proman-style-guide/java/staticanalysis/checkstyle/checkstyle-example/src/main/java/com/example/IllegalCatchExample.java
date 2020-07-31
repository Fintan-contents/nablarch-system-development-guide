/*
 * Header example
 */
package com.example;

/**
 * Example of IllegalCatch code.
 * 
 * @author example
 * @since 1.0.0
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

