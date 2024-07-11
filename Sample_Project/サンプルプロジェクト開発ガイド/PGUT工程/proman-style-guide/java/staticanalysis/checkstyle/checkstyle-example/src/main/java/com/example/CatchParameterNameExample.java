/*
 * Header example
 */
package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * CatchParameterNameのコード例です。
 */
public class CatchParameterNameExample {

    /**
     * CatchParameterNameのコード例です。
     */
    public void example() {
        SimpleDateFormat formatter = new SimpleDateFormat();
        try {
            formatter.parse("2000/1/1");
        } catch (ParseException E) {
            // 先頭が大文字のアルファベットになっています（NG）
        }
        try {
            formatter.parse("2000/1/1");
        } catch (ParseException e_x) {
            // アンダースコアを使用しています（NG）。
        }
        try {
            formatter.parse("2000/1/1");
        } catch (ParseException e) {
            // ルールに従った命名です（OK）。
        }
        try {
            formatter.parse("2000/1/1");
        } catch (ParseException ex) {
            // ルールに従った命名です（OK）。
        }
    }
}
