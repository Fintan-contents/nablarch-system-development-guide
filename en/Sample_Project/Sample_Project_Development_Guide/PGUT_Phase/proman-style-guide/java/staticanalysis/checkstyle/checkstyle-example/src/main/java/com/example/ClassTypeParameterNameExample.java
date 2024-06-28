/*
 * Header example
 */
package com.example;

/**
 * Example of ClassTypeParameterName code.
 *
 * Type parameter is one upper case letter (OK).
 *
 * @param <T> type parameter
 */
public class ClassTypeParameterNameExample<T> {
}

/**
 * Name consists of 2 or more characters (incorrect).
 *
 * @param <FOO> type parameter
 */
class NgClassTypeParameterNameExample1<FOO> {
}

/**
 * Name consists of lower-case letter(s) (incorrect).
 *
 * @param <t> type parameter
 */
class NgClassTypeParameterNameExample2<t> {
}
