/*
 * Header example
 */
package com.example.suppression;

/**
 * SuppressionFilterの例です。
 */
public class SuppressionFilterExample {

    // javadocコメントがありませんが、suppressions.xmlで'JavaDocMethod'チェックを
    // 除外設定しているため、NGにはなりません。
    public void thisMethodShouldNotBeChecked() {
        return;
    }
}
