/*
 * Header example
 */
package com.example;

/**
<<<<<<< HEAD
 * HiddenFieldのコード例です。
=======
 * Example of HiddenField code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
public class HiddenFieldExample {

    private String foo;

    /**
<<<<<<< HEAD
     * コンストラクタです。
     * 
     * @param foo コンストラクタ引数はフィールドと同名でもOK
=======
     * Constructor.
     *
     * @param foo OK even if constructor argument has the same name as the field.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     *
     */
    public HiddenFieldExample(String foo) {
        this.foo = foo;
    }

    /**
<<<<<<< HEAD
     * setterです。
     * 
     * @param foo setterの引数はフィールドと同名でもOK
=======
     * Setter.
     *
     * @param foo OK even if setter argument has the same name as the field.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     *
     */
    public void setFoo(String foo) {
        this.foo = foo;
    }

    /**
<<<<<<< HEAD
     * 引数がNGになる例です。
     *
     * @param foo setter以外のメソッドはフィールドと同名の引数はNG
=======
     * Example of incorrect argument.
     *
     * @param foo Incorrect if an argument for a method other than foo setter has the same name as the field.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     *
     */
    public void method1(String foo) {
        System.out.println(foo);
    }

    /**
<<<<<<< HEAD
     * ローカル変数がNGになる例です。
     *
     */
    public void method2() {
        //フィールドと同名のローカル変数はNG
=======
     * Example of incorrect local variable.
     *
     */
    public void method2() {
        //Incorrect as local variable has the same name as the field
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
        String foo = "NG";
        System.out.println(foo);
    }
}
