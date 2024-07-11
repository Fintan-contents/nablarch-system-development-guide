package com.example.api;

/**
 * 使用不許可APIチェックのコード例です。
 */
public class ClassABCExample {

    public void run() {

        ClassA a = new ClassA();
        a.methodA(); //NG
        a.methodB(); //NG

        ClassB b = new ClassB();
        b.methodA(); //OK
        b.methodB(); //NG

        ClassC c = new ClassC();
        c.methodA(); //OK
        c.methodB(); //NG
    }
}
