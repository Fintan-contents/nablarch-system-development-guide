/*
 * Header example
 */
package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * EmptyCatchBlockのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class EmptyCatchBlockExample {

    /**
     * EmptyCatchBlockのコード例です。
     */
    public void example() {

        try {
            List<String> lines = Files.readAllLines(new File("foo.txt").toPath());
            // 空のcatch節（NG）。
        } catch (IOException e) {
        }

        try {
            List<String> lines = Files.readAllLines(new File("bar.txt").toPath());
        } catch (IOException e) {
            // catch節にコメントを入れた場合、CheckStyle違反になりません（OK）。
        }
    }
}

