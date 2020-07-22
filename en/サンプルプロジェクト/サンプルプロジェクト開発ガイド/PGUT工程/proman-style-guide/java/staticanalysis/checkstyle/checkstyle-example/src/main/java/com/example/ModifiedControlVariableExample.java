/*
 * Header example
 */
package com.example;

/**
 * ModifiedControlVariableのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class ModifiedControlVariableExample {

    /**
     * ModifiedControlVariableのコード例です。
     */
    public void example() {
        int size = 10;
        //ループカウンタの不正な変更をしています（NG）。
        for (int i = 0; i < size; i++) {
            i++;
            System.out.println(i);
        }
        //ループカウンタはforUpdate部分で変更します（OK）。
        for (int i = 0; i < size; i++) {
            System.out.println(i);
        }
    }
}

