/*
 * Header example
 */
package com.example;

/**
 * Example of HiddenField code.
 * 
 * @author example
 * @since 1.0.0
 */
public class HiddenFieldExample {

    private String foo;

    /**
     * Constructor.
     *
     * @param foo OK even if constructor argument has the same name as the field.
     *
     */
    public HiddenFieldExample(String foo) {
        this.foo = foo;
    }

    /**
     * Setter.
     * 
     * @param foo OK even if setter argument has the same name as the field.
     *
     */
    public void setFoo(String foo) {
        this.foo = foo;
    }

    /**
     * Example of incorrect argument.
     *
     * @param foo Incorrect if an argument for a method other than setter has the same name as the field.
     */
    public void method1(String foo) {
        System.out.println(foo);
    }

    /**
     * Example of incorrect local variable.
     *
     */
    public void method2() {
        //Incorrect as local variable has the same name as the field
        String foo = "NG";
        System.out.println(foo);
    }
}
