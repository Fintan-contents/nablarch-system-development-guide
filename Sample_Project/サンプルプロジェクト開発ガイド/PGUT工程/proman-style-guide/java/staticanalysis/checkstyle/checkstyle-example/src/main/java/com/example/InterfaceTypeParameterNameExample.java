/*
 * Header example
 */
package com.example;

/**
 * InterfaceTypeParameterNameのコード例です。
 * 
 * 型パラメーターが大文字アルファベット1文字（OK）。
 *
 * @param <T> 型パラメーター
 */
public interface InterfaceTypeParameterNameExample<T> {
}

/**
 * 2文字以上の名前（NG）。
 *
 * @param <FOO> 型パラメーター
 */
interface NgInterfaceTypeParameterNameExample1<FOO> {
}

/**
 * 小文字アルファベットの名前（NG）。
 *
 * @param <t> 型パラメーター
 */
interface NgInterfaceTypeParameterNameExample2<t> {
}
