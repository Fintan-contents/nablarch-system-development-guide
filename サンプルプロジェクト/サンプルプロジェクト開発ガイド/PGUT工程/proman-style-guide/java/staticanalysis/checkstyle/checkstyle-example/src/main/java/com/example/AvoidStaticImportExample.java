/*
 * Header example
 */
package com.example;

import static java.lang.System.out;

/**
 * AvoidStaticImportのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class AvoidStaticImportExample {

    /**
     * AvoidStaticImportのコード例です。
     */
    public void example() {
        // static importを使用しています（OK）
        out.println("Hello World!");

        // static importを使用していません（OK）
        System.out.println("Hello World!");
    }
}

