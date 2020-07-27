/*
 * Header example
 */
package com.example;

import java.util.ArrayList;
import java.util.List;

/**
<<<<<<< HEAD
 * IllegalTypeのコード例です。
=======
 * Example of IllegalType code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
public class IllegalTypeExample {

    /**
<<<<<<< HEAD
     * IllegalTypeのコード例です。
     */
    public void example() {

        // 変数の型にArrayListは使っています（NG）。
        ArrayList<String> ng = new ArrayList<>();

        // 変数の型はListを使っています（OK）。
        // 右辺にArrayListを使うのはOKです。
=======
     * Example of IllegalType code.
     */
    public void example() {

        // ArrayList is used as the variable type (incorrect).
        ArrayList<String> ng = new ArrayList<>();

        // List is used as the variable type (OK).
        // OK to use ArrayList on right side.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        List<String> ok = new ArrayList<>();

        System.out.println(ok);
        System.out.println(ng);
    }
}

