/*
 * Header example
 */
package com.example;

/**
 * NeedBracesのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class NeedBracesExample {

    /**
     * NeedBracesのコード例です。
     */
    public void example() {
        boolean foo = true;
        // ブロックを { と } で囲っています（OK）。
        if (foo) {
            System.out.println("Good example");
        }

        // ブロックを { と } で囲っていません（NG）。
        if (foo)
            System.out.println("Bad example");
    }
}

