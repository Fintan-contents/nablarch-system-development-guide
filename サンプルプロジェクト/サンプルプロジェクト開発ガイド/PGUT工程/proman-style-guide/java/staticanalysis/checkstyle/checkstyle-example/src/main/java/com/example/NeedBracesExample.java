/*
 * Header example
 */
package com.example;

/**
 * NeedBracesのコード例です。
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
