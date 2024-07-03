/*
 * Header example
 */
package com.example;

/**
 * Example of MethodTypeParameterName code.
 *
 * @param <T> type parameter
 */
public class MethodTypeParameterNameExample<T> {

    /**
     * one upper case letter (OK).
     *
     * @param <T> type parameter
     */
    public <T> void okMethod() {
    }

    /**
     * Name consists of 2 or more characters (incorrect).
     *
     * @param <FOO> type parameter
     * 
     */
    public <FOO> void ngMethod1() {
    }

    /**
     * Name consists of lower-case letter(s) (incorrect).
     *
     * @param <t> type parameter
     *
     */
    public <t> void ngMethod2() {
    }
}
