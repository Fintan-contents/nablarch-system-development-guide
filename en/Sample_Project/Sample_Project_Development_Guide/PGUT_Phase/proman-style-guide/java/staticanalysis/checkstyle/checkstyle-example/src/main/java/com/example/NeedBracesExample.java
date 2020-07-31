/*
 * Header example
 */
package com.example;

/**
 * Example of NeedBraces code.
 * 
 * @author example
 * @since 1.0.0
 */
public class NeedBracesExample {

    /**
     * Example of NeedBraces code.
     */
    public void example() {
        boolean foo = true;
        // Block is surrounded with { and } (OK).
        if (foo) {
            System.out.println("Good example");
        }

        // Block is not surrounded with { and } (incorrect).
        if (foo)
            System.out.println("Bad example");
    }
}

