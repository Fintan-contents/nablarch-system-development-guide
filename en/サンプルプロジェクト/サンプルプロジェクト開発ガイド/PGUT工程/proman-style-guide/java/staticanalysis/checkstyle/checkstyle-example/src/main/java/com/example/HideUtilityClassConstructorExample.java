/*
 * Header example
 */
package com.example;

/**
 * HideUtilityClassConstructorのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class HideUtilityClassConstructorExample {

    /**
     * コンストラクタが同一パッケージのクラスに対して公開されている（NG）
     *
     */
    HideUtilityClassConstructorExample() {
        //nop
    }

    /**
     * staticユーティリティメソッドの例です。
     *
     * @param c カウントしたい文字
     * @param s カウント対象の文字列
     * @return カウント数
     */
    public static long count(final char c, final String s) {
        return s.chars().filter(a -> a == c).count();
    }
}
