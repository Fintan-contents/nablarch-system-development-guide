package com.example;

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
