/*
 * Header example
 */
package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example of GenericWhitespace code.
 *
 * @author example
 * @since 1.0.0
 */
public class GenericWhitespaceExample {

    /**
     * Example of GenericWhitespace code.
     */
    public void example() {
        // Unnecessary white space in <> for generics (incorrect).
        List< String > bad = new ArrayList<>();
        Map< String, Integer > badToo = new HashMap<>();

        // No unnecessary white space in <> for generics (OK).
        List<String> good = new ArrayList<>();
        Map<String, Integer> goodToo = new HashMap<>();
    }
}
