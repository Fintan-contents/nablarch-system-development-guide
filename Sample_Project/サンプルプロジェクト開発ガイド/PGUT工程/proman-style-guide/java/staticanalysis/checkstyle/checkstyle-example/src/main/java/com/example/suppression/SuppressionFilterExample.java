/*
 * Header example
 */
package com.example.suppression;

/**
 * SuppressionFilterの例です。
 *
 * @author example
 * @since 1.0.0
 */
public class SuppressionFilterExample {

    // javadocコメントがありませんが、suppressions.xmlで'JavaDocMethod'チェックを
    // 除外設定しているため、NGにはなりません。
    public void thisMethodShouldNotBeChecked() {
        return;
    }

    // javadocコメントが無いのでNGになります。
    public void thisMethodShouldBeChecked() {
    }
}
