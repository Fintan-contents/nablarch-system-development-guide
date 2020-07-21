/*
 * Header example
 */
package com.example;

/**
 * MissingSwitchDefaultのコード例です。
 * 
 * @author example
 * @since 1.0.0
 */
public class MissingSwitchDefaultExample {

    /**
     * MissingSwitchDefaultのコード例です。
     *
     * @param value コード例に使用する値
     */
    public void example(int value) {

        //defaultがある（OK）
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

        //defaultがない（NG）
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
