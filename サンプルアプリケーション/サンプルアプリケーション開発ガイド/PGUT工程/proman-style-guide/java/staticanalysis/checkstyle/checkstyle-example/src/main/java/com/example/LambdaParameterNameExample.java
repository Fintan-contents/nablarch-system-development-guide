/*
 * Header example
 */
package com.example;

import java.util.function.Function;

/**
 * LambdaParameterNameのコード例です。
 *
 * @author example
 * @since 1.0.0
 */
public class LambdaParameterNameExample {

    /**
     * LambdaParameterNameのコード例です。
     */
    public void example() {
        // 先頭が大文字のアルファベットになっています（NG）。
        Function<String, String> ng1 = BadName -> "NG";

        // アンダースコアを使用しています（NG）。
        Function<String, String> ng2 = bad_name -> "NG";

        // ルールに従った命名です（OK）。
        Function<String, String> ok = goodName -> "OK";
    }
}

