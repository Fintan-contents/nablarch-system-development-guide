/*
 * Header example
 */
package com.example;

/**
 * Example of HideUtilityClassConstructor code.
 * 
 * @author example
 * @since 1.0.0
 */
public class HideUtilityClassConstructorExample {

    /**
     * Constructor is published for the same package class (incorrect)
     *
     */
    HideUtilityClassConstructorExample() {
        //nop
    }

    /**
     * Example of static utility method.
     *
     * @param c Characters to be counted
     * @param s Character strings to be counted
     * @return Count
     */
    public static long count(final char c, final String s) {
        return s.chars().filter(a -> a == c).count();
    }
}
