/*
 * Header example
 */
package com.example;

/**
<<<<<<< HEAD
 * EqualsAvoidNullのコード例です。
=======
 * Example of EqualsAvoidNull code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
public class EqualsAvoidNullExample {

    /**
<<<<<<< HEAD
     * EqualsAvoidNullのコード例です。
     *
     * @param value1 コード例に使用する値
     * @param value2 コード例に使用する値
     */
    public void example(String value1, String value2) {

        //リテラルがequalsメソッドのレシーバなのでOK
=======
     * Example of EqualsAvoidNull code.
     *
     * @param value1: Value used in code example
     * @param value2: Value used in code example
     */
    public void example(String value1, String value2) {

        //OK as literal is a receiver of an equals method
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        if ("literal".equals(value1)) {
            System.out.println("equality");
        }

<<<<<<< HEAD
        //変数がequalsのレシーバなのでNG
=======
        //Incorrect as variable is an equals receiver
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        if (value1.equals("literal")) {
            System.out.println("equality");
        }

<<<<<<< HEAD
        //変数同士ならOK
=======
        //OK if both values are variables
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        if (value1.equals(value2)) {
            System.out.println("equality");
        }
    }
}
