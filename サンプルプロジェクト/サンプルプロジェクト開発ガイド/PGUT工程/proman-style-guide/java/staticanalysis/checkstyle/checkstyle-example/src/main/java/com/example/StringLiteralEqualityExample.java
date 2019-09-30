/*
 * Header example
 */
package com.example;

/**
 * StringLiteralEqualityのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class StringLiteralEqualityExample {

    /**
     * StringLiteralEqualityのコード例です。
     *
     * @param s 文字列
     */
    public void example(String s) {
        // 文字列を==で比較しています（NG）。
        if (s == "something") {

        }

        // 文字列をequalsで比較しています（OK）。
        if (s.equals("something")) {
            
        }

    }
}

