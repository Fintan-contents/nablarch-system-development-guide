/*
 * Header example
 */
package com.example;

/**
 * CyclomaticComplexityのコード例です。
 */
public class CyclomaticComplexityExample {

    /**
     * CyclomaticComplexityのコード例です。
     *
     * @param a 引数1
     * @param b 引数2
     * @param c 引数3
     * @param str 引数4
     * @return 戻り値
     */
    public int example(int a, int b, int c, String str) {
        if (a == 1) {                       // +1 (1)
            return 1;
        } else if (a == b && a == c) {      // +2 (3)
            if (b < 1) {                    // +1 (4)
                return 2;
            }
        }
        try {
            int d = Integer.parseInt(str);
            if (a == d) {                   // +1 (5)
                return switch (d) {
                    case 2 -> 20;           // +1 (6)
                    case 3 -> 30;           // +1 (7)
                    case 4 -> 40;           // +1 (8)
                    default -> 99;          // +1 (9)
                };
            }
        } catch (NumberFormatException e) { // +1 (10)
            throw new IllegalArgumentException(e);
        }
        return a < 0 ? -1 : 1;              // +1 (11)
    }
}
