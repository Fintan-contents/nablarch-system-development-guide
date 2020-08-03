/*
 * Header example
 */
package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Example of IllegalType code.
 * 
 * @author example
 * @since 1.0.0
 */
public class IllegalTypeExample {

    /**
     * Example of IllegalType code.
     */
    public void example() {

        // ArrayList is used as the variable type (incorrect).
        ArrayList<String> ng = new ArrayList<>();

        // List is used as the variable type (OK).
        // OK to use ArrayList on right side.
        List<String> ok = new ArrayList<>();

        System.out.println(ok);
        System.out.println(ng);
    }
}

