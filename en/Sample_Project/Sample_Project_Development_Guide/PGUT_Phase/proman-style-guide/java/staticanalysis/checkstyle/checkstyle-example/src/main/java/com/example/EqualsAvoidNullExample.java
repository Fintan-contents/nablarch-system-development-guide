/*
 * Header example
 */
package com.example;

/**
 * Example of EqualsAvoidNull code.
 * 
 * @author example
 * @since 1.0.0
 */
public class EqualsAvoidNullExample {

    /**
     * Example of EqualsAvoidNull code.
     *
     * @param value1 Value used in code example
     * @param value2 Value used in code example
     */
    public void example(String value1, String value2) {

        //OK as literal is a receiver of an equals method
        if ("literal".equals(value1)) {
            System.out.println("equality");
        }

        //Incorrect as variable is an equals receiver
        if (value1.equals("literal")) {
            System.out.println("equality");
        }

        //OK if all values are variables
        if (value1.equals(value2)) {
            System.out.println("equality");
        }
    }
}
