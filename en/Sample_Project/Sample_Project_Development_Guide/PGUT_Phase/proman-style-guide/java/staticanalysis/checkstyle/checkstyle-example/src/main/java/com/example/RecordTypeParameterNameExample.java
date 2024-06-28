/*
 * Header example
 */
package com.example;

/**
 * Example of RecordTypeParameterName code.
 *
 * Type parameter is one upper case letter (OK).
 *
 * @param x record component
 * @param <T> type parameter
 */
public record RecordTypeParameterNameExample<T>(String x) { }

/**
 * Name consists of 2 or more characters (incorrect).
 *
 * @param x record component
 * @param <FOO> type parameter
 */
record NgRecordTypeParameterNameExample1<FOO>(String x) { }

/**
 * Name consists of lower-case letter(s) (incorrect).
 *
 * @param x record component
 * @param <t> type parameter
 */
record NgRecordTypeParameterNameExample2<t>(String x) { }
