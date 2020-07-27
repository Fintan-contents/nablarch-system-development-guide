/*
 * Header example
 */
package com.example;

<<<<<<< HEAD
// 実際はListとArrayListしか使っていないのにjava.utilパッケージをまるごとimportしています（NG）。
import java.util.*;

/**
 * AvoidStarImportのコード例です。
=======
// The whole java.util package is imported even though only List and ArrayList are used (incorrect).
import java.util.*;

/**
 * Example of AvoidStarImport code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 *
 * @author example
 * @since 1.0.0
 */
public class AvoidStarImportExample {

    /**
<<<<<<< HEAD
     * AvoidStarImportのコード例です。
=======
     * Example of AvoidStarImport code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     */
    public void example() {
        List<String> list = new ArrayList<>();
    }
}

