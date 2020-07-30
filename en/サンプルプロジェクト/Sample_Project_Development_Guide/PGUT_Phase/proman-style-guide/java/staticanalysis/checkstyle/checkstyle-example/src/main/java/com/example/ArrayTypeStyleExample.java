/*
 * Header example
 */
package com.example;

import java.util.Arrays;

/**
 * Example of ArrayTypeStyle code.
 * 
 * @author example
 * @since 1.0.0
 */
public class ArrayTypeStyleExample {

    /**
     * Example of ArrayTypeStyle code.
     */
    public void example() {
        // Array declared in Java style (OK).
        String[] javaStyleArray = { "foo", "bar", "baz" };
        // Array declared in C language style (incorrect).
        String clangStyleArray[] = { "foo", "bar", "baz" };

        System.out.println(Arrays.toString(javaStyleArray));
        System.out.println(Arrays.toString(clangStyleArray));
    }
}
