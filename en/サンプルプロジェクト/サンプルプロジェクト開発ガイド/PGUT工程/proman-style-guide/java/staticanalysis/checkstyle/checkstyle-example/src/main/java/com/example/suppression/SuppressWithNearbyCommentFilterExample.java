/*
 * Header example
 */
package com.example.suppression;

/**
 * SuppressWithNearbyCommentFilterの例です。
 *
 * @author example
 * @since 1.0.0
 */
public class SuppressWithNearbyCommentFilterExample {

    private int counter_;  // SUPPRESS CHECKSTYLE #12345

    // javadocコメントがありませんが、該当行にCheckstyle除外コメントを
    // 記載しているので、NGにはなりません。
    public void thisMethodShouldNotBeChecked() {   // SUPPRESS CHECKSTYLE #123

    }

    // javadocコメントが無いのでNGになります。
    public void thisMethodShouldBeChecked() {

    }
}
