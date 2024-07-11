/*
 * Header example
 */
package com.example;

/**
 * JavadocVariableのコード例です。
 */
public class JavadocVariableExample {

    /**
     *コンストラクタ。
     */
    private JavadocVariableExample() {
    }

    public static final String HELLO = "こんにちは";    // javadocコメントがありません（NG）。

    /** 別れの挨拶 */
    public static final String GOODBYE = "さようなら";  // javadocコメントがあります（OK）。

}
