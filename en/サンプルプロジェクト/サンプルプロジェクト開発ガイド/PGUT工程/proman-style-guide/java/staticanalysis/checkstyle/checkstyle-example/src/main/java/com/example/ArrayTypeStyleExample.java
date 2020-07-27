/*
 * Header example
 */
package com.example;

import java.util.Arrays;

/**
<<<<<<< HEAD
 * ArrayTypeStyleのコード例です。
=======
 * Example of ArrayTypeStyle code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
public class ArrayTypeStyleExample {

    /**
<<<<<<< HEAD
     * ArrayTypeStyleのコード例です。
     */
    public void example() {
        // Javaスタイルによる配列宣言（OK）。
        String[] javaStyleArray = { "foo", "bar", "baz" };
        // C言語スタイルによる配列宣言（NG）。
=======
     * Example of ArrayTypeStyle code.
     */
    public void example() {
        // Array declared in Java style (OK).
        String[] javaStyleArray = { "foo", "bar", "baz" };
        // Array declared in Java style (OK).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        String clangStyleArray[] = { "foo", "bar", "baz" };

        System.out.println(Arrays.toString(javaStyleArray));
        System.out.println(Arrays.toString(clangStyleArray));
    }
}
