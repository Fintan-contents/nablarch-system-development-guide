/*
 * Header example
 */
package com.example;

import java.util.Arrays;
import java.util.List;

/**
 * NoWhitespaceBeforeのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class NoWhitespaceBeforeExample {

    /**
     * NoWhitespaceBeforeのコード例です。
     */
    public void example() {
        // セミコロン前に不要な空白があります（NG）。
        int i = 0 ;

        // カンマの前に不要な空白があります（NG）。
        List<String> list = Arrays.asList("foo", "bar", "buz");
    }
}

