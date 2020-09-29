/*
 * Header example
 */
package com.example;

// The whole java.util package is imported even though only List and ArrayList are used (incorrect).
import java.util.*;

/**
 * Example of AvoidStarImport code.
 *
 * @author example
 * @since 1.0.0
 */
public class AvoidStarImportExample {

    /**
     * Example of AvoidStarImport code.
     */
    public void example() {
        List<String> list = new ArrayList<>();
    }
}

