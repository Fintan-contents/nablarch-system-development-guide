/*
 * Header example
 */
package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Example of EmptyCatchBlock code.
 *
 * @author example
 * @since 1.0.0
 */
public class EmptyCatchBlockExample {

    /**
     * Example of EmptyCatchBlock code.
     */
    public void example() {

        try {
            List<String> lines = Files.readAllLines(new File("foo.txt").toPath());
            // Blank catch node (incorrect).
        } catch (IOException e) {
        }

        try {
            List<String> lines = Files.readAllLines(new File("bar.txt").toPath());
        } catch (IOException e) {
            // CheckStyle is not violated when a comment is entered in the catch node (OK).
        }
    }
}

