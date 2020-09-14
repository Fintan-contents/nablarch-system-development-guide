/*
 * Header example
 */
package com.example;

import java.util.function.Function;

/**
 * Example of LambdaParameterName code.
 *
 * @author example
 * @since 1.0.0
 */
public class LambdaParameterNameExample {

    /**
     * Example of LambdaParameterName code.
     */
    public void example() {
        // First character is an upper case letter (incorrect).
        Function<String, String> ng1 = BadName -> "NG";

        // An underscore is used (incorrect).
        Function<String, String> ng2 = bad_name -> "NG";

        // Name follows the rules (OK).
        Function<String, String> ok = goodName -> "OK";
    }
}

