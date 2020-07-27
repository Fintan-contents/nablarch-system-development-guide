/*
 * Header example
 */
package com.example;

import static java.lang.System.out;

/**
<<<<<<< HEAD
 * AvoidStaticImportのコード例です。
=======
 * Example of AvoidStaticImport code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 *
 * @author example
 * @since 1.0.0
 */
public class AvoidStaticImportExample {

    /**
<<<<<<< HEAD
     * AvoidStaticImportのコード例です。
     */
    public void example() {
        // static importを使用しています（OK）
        out.println("Hello World!");

        // static importを使用していません（OK）
=======
     * Example of AvoidStaticImport code.
     */
    public void example() {
        // static import is used (incorrect)
        out.println("Hello World!");

        // static import is not used (OK)
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        System.out.println("Hello World!");
    }
}

