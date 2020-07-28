/*
 * Header example
 */
package com.example;

import java.util.Arrays;

/**
 * ArrayTypeStyleのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class ArrayTypeStyleExample {

    /**
     * ArrayTypeStyleのコード例です。
     */
    public void example() {
        // Javaスタイルによる配列宣言（OK）。
        String[] javaStyleArray = { "foo", "bar", "baz" };
        // C言語スタイルによる配列宣言（NG）。
        String clangStyleArray[] = { "foo", "bar", "baz" };

        System.out.println(Arrays.toString(javaStyleArray));
        System.out.println(Arrays.toString(clangStyleArray));
    }
}
