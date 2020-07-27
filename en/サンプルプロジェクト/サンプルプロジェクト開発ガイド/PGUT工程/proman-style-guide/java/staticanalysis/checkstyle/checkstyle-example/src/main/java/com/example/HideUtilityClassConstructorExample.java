/*
 * Header example
 */
package com.example;

/**
<<<<<<< HEAD
 * HideUtilityClassConstructorのコード例です。
=======
 * Example of HideUtilityClassConstructor code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
public class HideUtilityClassConstructorExample {

    /**
<<<<<<< HEAD
     * コンストラクタが同一パッケージのクラスに対して公開されている（NG）
=======
     * Constructor is published for the same package class (incorrect)
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     *
     */
    HideUtilityClassConstructorExample() {
        //nop
    }

    /**
<<<<<<< HEAD
     * staticユーティリティメソッドの例です。
     *
     * @param c カウントしたい文字
     * @param s カウント対象の文字列
     * @return カウント数
=======
     * Example of static utility method.
     *
     * @param c Characters to be counted
     * @param s Character strings to be counted
     * @return Count
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     */
    public static long count(final char c, final String s) {
        return s.chars().filter(a -> a == c).count();
    }
}
