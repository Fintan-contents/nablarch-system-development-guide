/*
 * Header example
 */
package com.example;

/**
 * LocalFinalVariableNameのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class LocalFinalVariableNameExample {

    /**
     * LocalFinalVariableNameのコード例です。
     */
    public void example() {
        // final修飾されたローカル変数をUPPER_SNAKE_CASEで命名しています（NG）。
        final String VERY_IMPORTANT_MESSAGE = "こんにちは";

        // final修飾されたローカル変数をlowerCamelCaseで命名しています（NG）。
        final String veryImportantMessage = "さようなら";
    }

}

