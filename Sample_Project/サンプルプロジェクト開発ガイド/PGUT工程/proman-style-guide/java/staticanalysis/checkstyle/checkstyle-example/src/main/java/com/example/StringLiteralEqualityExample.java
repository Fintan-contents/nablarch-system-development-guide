/*
 * Header example
 */
package com.example;

/**
 * StringLiteralEqualityのコード例です。
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
        if ("something".equals(s)) {
            
        }

    }
}
