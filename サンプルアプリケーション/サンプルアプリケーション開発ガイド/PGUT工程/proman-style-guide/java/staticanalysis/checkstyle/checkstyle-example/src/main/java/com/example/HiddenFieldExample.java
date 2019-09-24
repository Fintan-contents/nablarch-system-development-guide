/*
 * Header example
 */
package com.example;

/**
 * HiddenFieldのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class HiddenFieldExample {

    private String foo;

    /**
     * コンストラクタです。
     * 
     * @param foo コンストラクタ引数はフィールドと同名でもOK
     *
     */
    public HiddenFieldExample(String foo) {
        this.foo = foo;
    }

    /**
     * setterです。
     * 
     * @param foo setterの引数はフィールドと同名でもOK
     *
     */
    public void setFoo(String foo) {
        this.foo = foo;
    }

    /**
     * 引数がNGになる例です。
     *
     * @param foo setter以外のメソッドはフィールドと同名の引数はNG
     *
     */
    public void method1(String foo) {
        System.out.println(foo);
    }

    /**
     * ローカル変数がNGになる例です。
     *
     */
    public void method2() {
        //フィールドと同名のローカル変数はNG
        String foo = "NG";
        System.out.println(foo);
    }
}
