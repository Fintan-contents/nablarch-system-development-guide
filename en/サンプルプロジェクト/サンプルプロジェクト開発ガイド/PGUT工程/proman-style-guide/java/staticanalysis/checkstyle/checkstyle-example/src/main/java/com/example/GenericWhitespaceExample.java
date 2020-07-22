/*
 * Header example
 */
package com.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GenericWhitespaceのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class GenericWhitespaceExample {

    /**
     * GenericWhitespaceのコード例です。
     */
    public void example() {
        // ジェネリクスの<>に不要な空白があります（NG）。
        List< String > bad = new ArrayList<>();
        Map< String, Integer > badToo = new HashMap<>();

        // ジェネリクスの<>に不要な空白がありません（OK）。
        List<String> good = new ArrayList<>();
        Map<String, Integer> goodToo = new HashMap<>();
    }
}

