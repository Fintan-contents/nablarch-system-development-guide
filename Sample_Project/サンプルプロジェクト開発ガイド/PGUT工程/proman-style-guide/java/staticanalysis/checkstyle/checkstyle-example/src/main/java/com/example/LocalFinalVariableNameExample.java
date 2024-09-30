/*
 * Header example
 */
package com.example;

/**
 * LocalFinalVariableNameのコード例です。
 */
public class LocalFinalVariableNameExample {

    /**
     * LocalFinalVariableNameのコード例です。
     */
    public void example() {
        // final修飾されたローカル変数をUPPER_SNAKE_CASEで命名しています（NG）。
        final String VERY_IMPORTANT_MESSAGE = "こんにちは";

        // final修飾されたローカル変数をlowerCamelCaseで命名しています（OK）。
        final String veryImportantMessage = "さようなら";
    }

}
