/*
 * Header example
 */
package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
<<<<<<< HEAD
 * EmptyCatchBlockのコード例です。
=======
 * Example of EmptyCatchBlock code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 *
 * @author example
 * @since 1.0.0
 */
public class EmptyCatchBlockExample {

    /**
<<<<<<< HEAD
     * EmptyCatchBlockのコード例です。
=======
     * Example of EmptyCatchBlock code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     */
    public void example() {

        try {
            List<String> lines = Files.readAllLines(new File("foo.txt").toPath());
<<<<<<< HEAD
            // 空のcatch節（NG）。
=======
            // Blank catch node (incorrect).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        } catch (IOException e) {
        }

        try {
            List<String> lines = Files.readAllLines(new File("bar.txt").toPath());
        } catch (IOException e) {
<<<<<<< HEAD
            // catch節にコメントを入れた場合、CheckStyle違反になりません（OK）。
=======
            // CheckStyle is not violated when a comment is entered in the catch node (OK).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        }
    }
}

