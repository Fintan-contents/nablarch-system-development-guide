/*
 * Header example
 */
package com.example;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
<<<<<<< HEAD
 * GenericWhitespaceのコード例です。
=======
 * Example of GenericWhitespace code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 *
 * @author example
 * @since 1.0.0
 */
public class GenericWhitespaceExample {

    /**
     * GenericWhitespaceのコード例です。
     */
    public void example() {
<<<<<<< HEAD
        // ジェネリクスの<>に不要な空白があります（NG）。
        List< String > bad = new ArrayList<>();
        Map< String, Integer > badToo = new HashMap<>();

        // ジェネリクスの<>に不要な空白がありません（OK）。
=======
        // Unnecessary white space in <> for generics (incorrect).
        List< String > bad = new ArrayList<>();
        Map< String, Integer > badToo = new HashMap<>();

        // Unnecessary white space in <> for generics (correct).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        List<String> good = new ArrayList<>();
        Map<String, Integer> goodToo = new HashMap<>();
    }
}

