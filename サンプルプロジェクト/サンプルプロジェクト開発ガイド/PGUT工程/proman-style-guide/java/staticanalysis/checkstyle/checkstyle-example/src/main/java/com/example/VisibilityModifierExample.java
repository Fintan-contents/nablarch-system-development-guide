/*
 * Header example
 */
package com.example;

/**
 * VisibilityModifierのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class VisibilityModifierExample {

    /**
     * publicなインスタンスフィールドを宣言しています（NG）。
     */
    public int ng1;

    /**
     * protectedなインスタンスフィールドを宣言しています（NG）。
     */
    protected int ng2;

    /**
     * package privateなインスタンスフィールドを宣言しています（NG）。
     */
    int ng3;

    /**
     * privateなインスタンスフィールドを宣言しています（OK）。
     */
    private int ok1;

    /**
     * static finalならスコープはpublicでもOKです（OK）。
     */
    public static final int OK2 = 0;
}

