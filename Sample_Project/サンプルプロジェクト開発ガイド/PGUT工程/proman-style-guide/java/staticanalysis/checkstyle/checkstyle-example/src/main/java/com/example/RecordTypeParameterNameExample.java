/*
 * Header example
 */
package com.example;

/**
 * RecordTypeParameterNameのコード例です。
 * 
 * 型パラメーターが大文字アルファベット1文字（OK）。
 *
 * @param x レコードコンポーネント
 * @param <T> 型パラメーター
 */
public record RecordTypeParameterNameExample<T>(String x) { }

/**
 * 2文字以上の名前（NG）。
 *
 * @param x レコードコンポーネント
 * @param <FOO> 型パラメーター
 */
record NgRecordTypeParameterNameExample1<FOO>(String x) { }

/**
 * 小文字アルファベットの名前（NG）。
 *
 * @param x レコードコンポーネント
 * @param <t> 型パラメーター
 */
record NgRecordTypeParameterNameExample2<t>(String x) { }
