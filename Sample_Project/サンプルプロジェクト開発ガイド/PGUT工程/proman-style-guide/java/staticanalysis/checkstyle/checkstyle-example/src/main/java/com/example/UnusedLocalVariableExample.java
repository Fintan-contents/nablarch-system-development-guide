/*
 * Header example
 */
package com.example;

/**
 * UnusedLocalVariable のコード例です。
 */
public class UnusedLocalVariableExample {

    /**
     * メソッド。
     *
     * @return 戻り値
     */
    public String example() {
        // 使用されないローカル変数です（NG）。
        String unused = "unused";

        // 使用されているローカル変数です（OK）。
        String used = "used";
        return used.trim();
    }

}
