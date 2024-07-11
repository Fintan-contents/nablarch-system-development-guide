/*
 * Header example
 */
package com.example;

/**
 * TypeNameのコード例です。
 */
public class TypeNameExample {

}

/**
 * 先頭が小文字のアルファベットのクラス名（NG）。
 */
class ngClassName {
}

/**
 * 先頭が小文字のアルファベットのインターフェース名（NG）。
 */
interface ngInterfaceName {
}

/**
 * 先頭が小文字のアルファベットの列挙名（NG）。
 */
enum ngEnumName {
}

/**
 * 先頭が小文字のアルファベットのアノテーション名（NG）。
 */
@interface ngAnnotationName {
}

/**
 * 先頭が小文字のアルファベットのレコード名（NG）。
 *
 * @param x レコードコンポーネント
 */
record ngRecordComponentName(String x) { }
