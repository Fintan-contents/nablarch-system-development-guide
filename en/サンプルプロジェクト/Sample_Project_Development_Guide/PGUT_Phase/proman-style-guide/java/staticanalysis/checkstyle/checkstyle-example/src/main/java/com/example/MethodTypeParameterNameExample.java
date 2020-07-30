/*
 * Header example
 */
package com.example;

/**
 * Example of MethodTypeParameterName code.
 * 
 * @author example
 * @since 1.0.0
 */
public class MethodTypeParameterNameExample<T> {

    /**
     * Example of correct MethodTypeParameterName.
     *
     * @param <T> type parameter is one upper case letter (OK)
     */
    public <T> void okMethod() {
    }

    /**
     * Example of incorrect MethodTypeParameterName.
     *
     * @param <FOO> Name consists of 2 or more characters (incorrect).
     * 
     */
    public <FOO> void ngMethod1() {
    }

    /**
     * Example of incorrect MethodTypeParameterName.
     *
     * @param <t> Name consists of lower-case letter(s) (incorrect).
     * 
     */
    public <t> void ngMethod2() {
    }
}
