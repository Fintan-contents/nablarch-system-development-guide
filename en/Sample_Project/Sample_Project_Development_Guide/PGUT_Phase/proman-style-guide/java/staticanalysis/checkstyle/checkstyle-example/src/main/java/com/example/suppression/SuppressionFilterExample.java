/*
 * Header example
 */
package com.example.suppression;

/**
 * Example of SuppressionFilter.
 *
 * @author example
 * @since 1.0.0
 */
public class SuppressionFilterExample {

    // No javadoc comment, but this is not incorrect
    // as suppressions.xml is used to set removal of the 'JavaDocMethod' check.
    public void thisMethodShouldNotBeChecked() {
        return;
    }

    // Incorrect as there is no javadoc comment.
    public void thisMethodShouldBeChecked() {
    }
}
