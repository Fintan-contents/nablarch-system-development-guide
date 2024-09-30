/*
 * Header example
 */
package com.example;

/**
 * MethodTypeParameterNameのコード例です。
 *
 * @param <T> 型パラメーター
 */
public class MethodTypeParameterNameExample<T> {

    /**
     * 大文字アルファベット1文字（OK）。
     * 
     * @param <T> 型パラメーター
     */
    public <T> void okMethod() {
    }

    /**
     * 2文字以上の名前（NG）。
     * 
     * @param <FOO> 型パラメーター
     * 
     */
    public <FOO> void ngMethod1() {
    }

    /**
     * 小文字アルファベットの名前（NG）。
     * 
     * @param <t> 型パラメーター
     * 
     */
    public <t> void ngMethod2() {
    }
}
