/*
 * Header example
 */
package com.example;

// 使用されないimport文です（NG）。
import java.math.BigDecimal;

// 使用されているimport文です（OK）。
import java.util.regex.Pattern;

/**
 * UnusedImportsのコード例です。
 */
public class UnusedImportsExample {

    /** 英数字にマッチする正規表現 */
    private static Pattern alphabet = Pattern.compile("^[a-zA-Z]+$");
}
