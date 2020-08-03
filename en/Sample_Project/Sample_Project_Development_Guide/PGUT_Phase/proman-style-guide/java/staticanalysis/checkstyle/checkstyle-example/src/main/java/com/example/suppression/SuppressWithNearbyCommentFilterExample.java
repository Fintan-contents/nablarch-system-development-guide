/*
 * Header example
 */
package com.example.suppression;

/**
 * Example of SuppressWithNearbyCommentFilter.
 *
 * @author example
 * @since 1.0.0
 */
public class SuppressWithNearbyCommentFilterExample {

    private int counter_;  // SUPPRESS CHECKSTYLE #12345

    // No javadoc comment, but this is not incorrect
    // as a comment is used to remove Checkstyle in this line.
    public void thisMethodShouldNotBeChecked() {   // SUPPRESS CHECKSTYLE #123

    }

    // Incorrect as there is no javadoc comment.
    public void thisMethodShouldBeChecked() {

    }
}
