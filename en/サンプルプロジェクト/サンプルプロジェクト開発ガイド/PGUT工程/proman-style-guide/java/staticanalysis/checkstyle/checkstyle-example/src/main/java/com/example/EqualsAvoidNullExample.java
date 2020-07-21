/*
 * Header example
 */
package com.example;

/**
 * EqualsAvoidNullのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class EqualsAvoidNullExample {

    /**
     * EqualsAvoidNullのコード例です。
     *
     * @param value1 コード例に使用する値
     * @param value2 コード例に使用する値
     */
    public void example(String value1, String value2) {

        //リテラルがequalsメソッドのレシーバなのでOK
        if ("literal".equals(value1)) {
            System.out.println("equality");
        }

        //変数がequalsのレシーバなのでNG
        if (value1.equals("literal")) {
            System.out.println("equality");
        }

        //変数同士ならOK
        if (value1.equals(value2)) {
            System.out.println("equality");
        }
    }
}
