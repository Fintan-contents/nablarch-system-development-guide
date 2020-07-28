/*
 * Header example
 */
package com.example;

/**
 * IllegalCatchのコード例です。
 * 
 * @author example
 * @since 1.0.0
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

