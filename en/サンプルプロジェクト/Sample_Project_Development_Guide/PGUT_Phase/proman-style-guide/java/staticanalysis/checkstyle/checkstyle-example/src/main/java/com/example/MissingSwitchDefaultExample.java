/*
 * Header example
 */
package com.example;

/**
 * Example of MissingSwitchDefault code.
 * 
 * @author example
 * @since 1.0.0
 */
public class MissingSwitchDefaultExample {

    /**
     * Example of MissingSwitchDefault code.
     *
     * @param value Value used in code example
     */
    public void example(int value) {

        //There is a default (OK)
        switch (value) {
        case 0:
            System.out.println("0");
            break;
        case 1:
            System.out.println("1");
            break;
        default:
            System.out.println("other");
            break;
        }

        //There is not default (NG)
        switch (value) {
        case 0:
            System.out.println("0");
            break;
        case 1:
            System.out.println("1");
            break;
        case 2:
            System.out.println("2");
            break;
        }
    }
}
