/*
 * Header example
 */
package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * IllegalTypeのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class IllegalTypeExample {

    /**
     * IllegalTypeのコード例です。
     */
    public void example() {

        // 変数の型にArrayListは使っています（NG）。
        ArrayList<String> ng = new ArrayList<>();

        // 変数の型はListを使っています（OK）。
        // 右辺にArrayListを使うのはOKです。
        List<String> ok = new ArrayList<>();

        System.out.println(ok);
        System.out.println(ng);
    }
}

