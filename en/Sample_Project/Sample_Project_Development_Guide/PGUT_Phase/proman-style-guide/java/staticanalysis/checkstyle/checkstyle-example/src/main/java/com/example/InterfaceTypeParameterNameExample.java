/*
 * Header example
 */
package com.example;

/**
 * Example of InterfaceTypeParameterName code.
 *
 * Type parameter is one upper case letter (OK).
 *
 * @param <T> type parameter
 */
public interface InterfaceTypeParameterNameExample<T> {
}

/**
 * Name consists of 2 or more characters (incorrect).
 *
 * @param <FOO> type parameter
 */
interface NgInterfaceTypeParameterNameExample1<FOO> {
}

/**
 * Name consists of lower-case letter(s) (incorrect).
 *
 * @param <t> type parameter
 */
interface NgInterfaceTypeParameterNameExample2<t> {
}
