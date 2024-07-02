/*
 * Header example
 */
package com.example;

/**
 * InterfaceTypeParameterNameのコード例です。
 * 
 * 型パラメーターが大文字アルファベット1文字（OK）。
 * 
 * @author example
 * @since 1.0.0
 */
public interface InterfaceTypeParameterNameExample<T> {
}

/**
 * 2文字以上の名前（NG）。
 * 
 * @author example
 * @since 1.0.0
 */
interface NgInterfaceTypeParameterNameExample1<FOO> {
}

/**
 * 小文字アルファベットの名前（NG）。
 * 
 * @author example
 * @since 1.0.0
 */
interface NgInterfaceTypeParameterNameExample2<t> {
}
