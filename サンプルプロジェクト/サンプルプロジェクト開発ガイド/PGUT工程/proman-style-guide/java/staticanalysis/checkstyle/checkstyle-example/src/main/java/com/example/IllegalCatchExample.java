/*
 * Header example
 */
package com.example;

/**
 * IllegalCatchのコード例です。
 */
public class IllegalCatchExample {

    /**
     * IllegalCatchのコード例です。
     */
    public void example() {
        try {
            System.out.println("example");
        } catch (Exception e) { // 許可されていない例外（java.lang.Exception）をcatchしています（NG）。
            e.printStackTrace();
        }
    }
}
