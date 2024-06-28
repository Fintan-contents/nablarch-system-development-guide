/*
 * Header example
 */
package com.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Example of CatchParameterName code.
 */
public class CatchParameterNameExample {

    /**
     * Example of CatchParameterName code.
     */
    public void example() {
        SimpleDateFormat formatter = new SimpleDateFormat();
        try {
            formatter.parse("2000/1/1");
        } catch (ParseException E) {
            // First character is an upper case letter (incorrect).
        }
        try {
            formatter.parse("2000/1/1");
        } catch (ParseException e_x) {
            // An underscore is used (incorrect).
        }
        try {
            formatter.parse("2000/1/1");
        } catch (ParseException e) {
            // Name follows the rules (OK).
        }
        try {
            formatter.parse("2000/1/1");
        } catch (ParseException ex) {
            // Name follows the rules (OK).
        }
    }
}
