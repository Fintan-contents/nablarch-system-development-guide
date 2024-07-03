/*
 * Header example
 */
package com.example;

/**
 * Example of JavadocMethod code.
 */
public class JavadocMethodExample {

    /**
     * Double the assigned number.
     *
     * There is a Javadoc comment but @param and @return are missing (incorrect).
     *
     * The @params tag indicating an unchecked exception can be written even if the method is not a throws declaration
     * (this is not an incorrect Checkstyle setting)
     *
     * @throws IllegalArgumentException When the argument is a negative number
     */
    public int noParamAndReturnTag(int number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
        return number * 2;
    }
}
