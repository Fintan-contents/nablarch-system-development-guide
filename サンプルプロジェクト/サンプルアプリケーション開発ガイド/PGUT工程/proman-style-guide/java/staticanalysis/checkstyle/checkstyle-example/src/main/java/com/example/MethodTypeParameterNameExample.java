/*
 * Header example
 */
package com.example;

/**
 * MethodTypeParameterNameのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class MethodTypeParameterNameExample<T> {

    /**
     * MethodTypeParameterNameのOK例です。
     * 
     * @param <T> 型パラメーターが大文字アルファベット1文字（OK）
     */
    public <T> void okMethod() {
    }

    /**
     * MethodTypeParameterNameのNG例です。
     * 
     * @param <FOO> 2文字以上の名前（NG）。
     * 
     */
    public <FOO> void ngMethod1() {
    }

    /**
     * MethodTypeParameterNameのNG例です。
     * 
     * @param <t> 小文字アルファベットの名前（NG）。
     * 
     */
    public <t> void ngMethod2() {
    }
}
