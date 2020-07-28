/*
 * Header example
 */
package com.example;

/**
 * ClassTypeParameterNameのコード例です。
 * 
 * 型パラメーターが大文字アルファベット1文字（OK）。
 * 
 * @author example
 * @since 1.0.0
 */
public class ClassTypeParameterNameExample<T> {
}

/**
 * 2文字以上の名前（NG）。
 * 
 * @author example
 * @since 1.0.0
 */
class NgClassTypeParameterNameExample1<FOO> {
}

/**
 * 小文字アルファベットの名前（NG）。
 * 
 * @author example
 * @since 1.0.0
 */
class NgClassTypeParameterNameExample2<t> {
}
