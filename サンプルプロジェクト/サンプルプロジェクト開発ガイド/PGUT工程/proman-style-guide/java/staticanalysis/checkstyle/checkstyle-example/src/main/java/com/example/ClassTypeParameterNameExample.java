/*
 * Header example
 */
package com.example;

/**
 * ClassTypeParameterNameのコード例です。
 * 
 * 型パラメーターが大文字アルファベット1文字（OK）。
 *
 * @param <T> 型パラメーター
 */
public class ClassTypeParameterNameExample<T> {
}

/**
 * 2文字以上の名前（NG）。
 *
 * @param <FOO> 型パラメーター
 */
class NgClassTypeParameterNameExample1<FOO> {
}

/**
 * 小文字アルファベットの名前（NG）。
 *
 * @param <t> 型パラメーター
 */
class NgClassTypeParameterNameExample2<t> {
}
