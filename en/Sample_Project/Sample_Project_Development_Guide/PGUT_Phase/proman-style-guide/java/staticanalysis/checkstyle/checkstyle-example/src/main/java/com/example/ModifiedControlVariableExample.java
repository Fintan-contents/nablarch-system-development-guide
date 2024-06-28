/*
 * Header example
 */
package com.example;

/**
 * Example of ModifiedControlVariable code.
 */
public class ModifiedControlVariableExample {

    /**
     * Example of ModifiedControlVariable code.
     */
    public void example() {
        int size = 10;
        //Improper change of loop counter (incorrect).
        for (int i = 0; i < size; i++) {
            i++;
            System.out.println(i);
        }
        //Loop counter is changed in the forUpdate area (OK).
        for (int i = 0; i < size; i++) {
            System.out.println(i);
        }
    }
}
