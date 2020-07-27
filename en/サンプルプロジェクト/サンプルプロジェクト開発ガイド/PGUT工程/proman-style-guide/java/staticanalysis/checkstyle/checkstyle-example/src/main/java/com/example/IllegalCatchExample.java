/*
 * Header example
 */
package com.example;

/**
<<<<<<< HEAD
 * IllegalCatchのコード例です。
=======
 * Example of IllegalCatch code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
 * 
 * @author example
 * @since 1.0.0
 */
public class IllegalCatchExample {

    /**
<<<<<<< HEAD
     * IllegalCatchのコード例です。
=======
     * Example of IllegalCatch code.
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
     */
    public void example() {
        try {
            System.out.println("example");
<<<<<<< HEAD
        } catch (Exception e) { // 許可されていない例外（java.lang.Exception）をcatchしています（NG）。
=======
        } catch (Exception e) { // Unpermitted exception (java.lang.Exception) is caught (incorrect).
>>>>>>> 2aecddaa5a1529732d5207d5a08823b5737bb34a
            e.printStackTrace();
        }
    }
}

