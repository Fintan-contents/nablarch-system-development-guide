/*
 * Header example
 */
package com.example;

/**
 * Example of JavadocType code.
 *
 * @author example
 * Example of @param <T> type parameter
 * @since 1.0.0
 */
public class JavadocTypeExample<T> {
}

class NgJavadocTypeExample1 { // No javadoc comment (NG).
}

/**
 * There is a Javadoc comment but @param (type parameter T) is missing (incorrect).
 * 
 * @author example
 */
class NgJavadocTypeExample2<T> {
}
