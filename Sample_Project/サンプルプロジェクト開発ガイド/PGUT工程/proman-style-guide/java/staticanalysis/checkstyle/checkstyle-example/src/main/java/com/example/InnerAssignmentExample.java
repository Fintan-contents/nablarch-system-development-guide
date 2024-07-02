/*
 * Header example
 */
package com.example;

/**
 * InnerAssignmentのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class InnerAssignmentExample {

    /**
     * InnerAssignmentのコード例です。
     */
    public void example() {

        int i;
        String s = Integer.toString(i = 2);   // 式内部で代入をしています（NG）。

        System.out.println(String.valueOf(i));
        System.out.println(s);
    }
}

