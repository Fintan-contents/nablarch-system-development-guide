/*
 * Header example
 */
package com.example;

/**
 * RecordComponentNameのコード例です。
 *
 * @param BadName 先頭が大文字のアルファベットになっています（NG）
 * @param bad_name アンダースコアを使用しています（NG）
 * @param goodName ルールに従った命名です（OK）
 */
public record RecordComponentNameExample(String BadName,
                                         String bad_name,
                                         String goodName) { }
